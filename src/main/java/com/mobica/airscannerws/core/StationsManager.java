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

/**
 * Created by wojtek on 22.10.15.
 */
public class StationsManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationsManager.class);
    private static StationDao dao;
    private static String gcmServer;
    private static String apiToken;

    public static void init(String gcmServerUrl, String apiToken) {
        StationsManager.gcmServer = gcmServerUrl;
        StationsManager.apiToken = apiToken;
        StationsManager.dao = StorageConnector.open();
    }

    public static synchronized void updateStationToken(String address, String token) {
        Station station = dao.getStation(address);
        if (station == null) {
            station = new Station(address);
            station.setGcmId(token);
            dao.addStation(station);
        } else {
            station.setGcmId(token);
            dao.updateStation(station);
        }

        sendGcmMessage(station, false);
    }

    public static synchronized void updateConnectedState(String address, boolean connected) {
        Station station = dao.getStation(address);
        if (station == null) {
            station = new Station(address);
            station.setOnline(connected);
            dao.addStation(station);
        } else {
            station.setOnline(connected);
            dao.updateStation(station);
        }

        if (!Strings.isNullOrEmpty(station.getGcmId())) {
            sendGcmMessage(station, true);
        }
    }

    public static synchronized void resetConnectionStates() {
        for (Station station : dao.getStations()) {
            station.setOnline(false);
            dao.updateStation(station);
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
                        new GcmMessage(station.isOnline(), statusChanged));

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
