package com.mobica.airscannerws.core;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Strings;
import com.mobica.airscannerws.api.GcmMessage;
import com.mobica.airscannerws.api.GcmResponse;
import com.mobica.airscannerws.api.GcmUpstreamRequest;
import com.mobica.airscannerws.storage.Station;
import com.mobica.airscannerws.storage.StationDao;
import com.mobica.airscannerws.storage.StorageConnector;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.impl.provider.entity.StringProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wojtek on 22.10.15.
 */
public class StationsManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationsManager.class);
    private static long stationTtl;
    private static StationDao dao;
    private static String gcmServer;
    private static String apiToken;
    private static ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

    public static void init(String gcmServerUrl, String apiToken, long stationTtl) {
        StationsManager.stationTtl = stationTtl;
        StationsManager.gcmServer = gcmServerUrl;
        StationsManager.apiToken = apiToken;
        StationsManager.dao = StorageConnector.open();
        threadPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                updateDetectionStates();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public static synchronized String registerStationToken(String address, String gcmToken) {
        final String authToken = UUID.randomUUID().toString();
        Station station = dao.getStationByAddress(address);
        if (station == null) {
            station = new Station(address, authToken);
            station.setGcmId(gcmToken);
            station.setLastUpdateTime(System.currentTimeMillis());
            dao.addStation(station);
        } else {
            station.setGcmId(gcmToken);
            station.setLastUpdateTime(System.currentTimeMillis());
            dao.updateStation(station);
        }

        LOGGER.info("Station token updated {} {}", station.getAddress(), new Date(station.getLastUpdateTime()));
        return station.getAuthToken();
    }

    public static synchronized void removeStationToken(String authToken) {
        Station station = dao.getStationByToken(authToken);
        if (station != null) {
            station.setGcmId(null);
            station.setLastUpdateTime(System.currentTimeMillis());
            dao.updateStation(station);

            LOGGER.info("Station token removed {} {}", station.getAddress(), new Date(station.getLastUpdateTime()));
        }
    }

    public static synchronized boolean isRegistered(String authToken) {
        Station station = dao.getStationByToken(authToken);
        return station != null && !Strings.isNullOrEmpty(station.getGcmId());
    }

    public static synchronized String getStationAddress(String authToken) {
        Station station = dao.getStationByToken(authToken);
        if (station != null) {
            return station.getAddress();
        }

        return null;
    }

    public static synchronized boolean subscribeForDiscovery(String authToken) {
        Station station = dao.getStationByToken(authToken);
        if (station == null || Strings.isNullOrEmpty(station.getGcmId())) {
            // user not registered
            return false;
        }

        station.setDiscoveryEnabled(true);
        station.setLastUpdateTime(System.currentTimeMillis());
        dao.updateStation(station);
        sendGcmMessage(station, false);

        return true;
    }

    public static synchronized boolean unsubscribeFromDiscovery(String authToken) {
        Station station = dao.getStationByToken(authToken);
        if (station == null) {
            // user not registered
            return false;
        }

        if (!station.isDiscoveryEnabled()) {
            // not subscribed for discovery
            return false;
        }

        station.setDiscoveryEnabled(false);
        station.setLastUpdateTime(System.currentTimeMillis());
        dao.updateStation(station);
        return true;
    }

    public static synchronized List<String> updateConnectedState(String[] detected) {
        final List<String> active = new ArrayList<>();

        for (String activeAddress : detected) {
            final Station station = dao.getStationByAddress(activeAddress);
            if (station != null) {
                if (!Strings.isNullOrEmpty(station.getGcmId())) {
                    active.add(activeAddress);

                    if (!station.isInRange()) {
                        station.setInRange(true);

                        if (station.isDiscoveryEnabled()) {
                            sendGcmMessage(station, true);
                        }
                    }
                }

                station.setInRange(true);
                station.setLastUpdateTime(System.currentTimeMillis());
                dao.updateStation(station);
            }
        }

        return active;
    }

    public static synchronized void resetConnectionStates() {
        for (Station station : dao.getStations()) {
            station.setInRange(false);
            dao.updateStation(station);
        }
    }

    public static synchronized List<Station> getStations(long lastUpdateTime) {
        return dao.getStations(lastUpdateTime);
    }

    public static synchronized void updateDetectionStates() {
        for (Station station : dao.getStations()) {
            if (station.isInRange() && station.getLastUpdateTime() < (System.currentTimeMillis() - stationTtl)) {
                LOGGER.info("Station TTL expired: {}", station.getAddress());

                station.setInRange(false);
                dao.updateStation(station);

                if (station.isDiscoveryEnabled()) {
                    sendGcmMessage(station, true);
                }
            }
        }
    }

    private static void sendGcmMessage(Station station, boolean statusChanged) {
        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().clear();
        cc.getClasses().add(JacksonJsonProvider.class);
        cc.getClasses().add(StringProvider.class);
        final Client client = Client.create(cc);

        final GcmUpstreamRequest gcmMsg =
                new GcmUpstreamRequest(new String[]{station.getGcmId()},
                        new GcmMessage(GcmMessage.Type.discovery_status, station.isInRange(), statusChanged));

        LOGGER.info("Sending GCM message: " + gcmMsg);

        try {
            final ClientResponse response = client.resource(gcmServer)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "key=" + apiToken)
                    .post(ClientResponse.class, gcmMsg);

            if (response == null) {
                LOGGER.error("Received a null response");
                return;
            }

            response.bufferEntity();
            final GcmResponse gcmResponse;
            try {
                gcmResponse = response.getEntity(GcmResponse.class);
            } catch (Exception ex) {
                LOGGER.error("Failed to parse JSON response from GCM");
                return;
            }

            LOGGER.info("Response: {}", gcmResponse);
        } catch (Exception ex) {
            LOGGER.error("Internal error: {}", ex.getMessage());
        }
    }
}
