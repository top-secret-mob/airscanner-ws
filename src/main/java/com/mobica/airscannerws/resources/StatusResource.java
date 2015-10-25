package com.mobica.airscannerws.resources;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.api.StatusRequest;
import com.mobica.airscannerws.api.StatusResponse;
import com.mobica.airscannerws.api.SyncRequest;
import com.mobica.airscannerws.core.StationsManager;
import com.mobica.airscannerws.storage.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusResource.class);

    public StatusResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public StatusResponse receiveStatusRequest(@Valid @NotNull StatusRequest request) {
        LOGGER.info("Received status request: {}", request);

        final String[] detected = request.getDiscovered();
        final StatusResponse response = new StatusResponse();

        if (detected != null) {
            final List<String> online = StationsManager.updateConnectedState(detected);
            response.setActive(online.toArray(new String[online.size()]));
        }

        return response;
    }

    @POST
    @Path("/reset")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveResetRequest() {
        LOGGER.info("Received reset request");

        StationsManager.resetConnectionStates();

        return new Response(Response.Status.success);
    }

    @POST
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusResponse receiveSyncRequest(@Valid @NotNull SyncRequest request) {
        LOGGER.info("Received sync request: {}", request);

        final StatusResponse response = new StatusResponse();
        List<Station> online = StationsManager.getStations(request.getLastSync());

        if (online != null) {
            String[] onlineMac = Iterables.toArray(Iterables.transform(online,
                    new Function<Station, String>() {
                        @Nullable
                        @Override
                        public String apply(Station input) {
                            return input.getAddress();
                        }
                    }), String.class);
            response.setActive(onlineMac);
        }
        return response;
    }
}
