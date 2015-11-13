package com.mobica.airscannerws.resources;

import com.mobica.airscannerws.api.DiscoveryRegisterRequest;
import com.mobica.airscannerws.api.DiscoveryUnregisterRequest;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.api.LogoutRequest;
import com.mobica.airscannerws.core.StationsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/discovery")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryRegistrationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryRegistrationResource.class);

    public DiscoveryRegistrationResource() {
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveRegisterRequest(@Valid @NotNull DiscoveryRegisterRequest user) {
        LOGGER.info("Received register request: {}", user);

        if (!StationsManager.registerForDiscovery(user.getAddress())) {
            return new Response(Response.Status.error, "Not registered");
        }

        return new Response(Response.Status.success);
    }

    @POST
    @Path("/unregister")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveUnregisterRequest(@Valid @NotNull DiscoveryUnregisterRequest user) {
        LOGGER.info("Received unregister request: {}", user);

        if (!StationsManager.unregisterFromDiscovery(user.getAddress())) {
            return new Response(Response.Status.error, "Not registered");
        }

        return new Response(Response.Status.success);
    }
}
