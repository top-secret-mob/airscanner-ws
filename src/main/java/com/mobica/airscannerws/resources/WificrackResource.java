package com.mobica.airscannerws.resources;

import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.api.StatusRequest;
import com.mobica.airscannerws.core.StationsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class WificrackResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WificrackResource.class);

    public WificrackResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveStatusRequest(@Valid @NotNull StatusRequest request) {
        LOGGER.info("Received status request: {}", request);

        StationsManager.updateConnectedState(request.getAddress(), request.isOnline());

        return new Response(Response.Status.success);
    }
}
