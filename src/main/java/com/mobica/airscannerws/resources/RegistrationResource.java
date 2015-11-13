package com.mobica.airscannerws.resources;

import com.mobica.airscannerws.api.DiscoveryRegisterRequest;
import com.mobica.airscannerws.api.LoginRequest;
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

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);

    public RegistrationResource() {
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveRegisterRequest(@Valid @NotNull LoginRequest user) {
        LOGGER.info("Received login request: {}", user);

        StationsManager.updateStationToken(user.getAddress(), user.getGcmRegId());

        return new Response(Response.Status.success);
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveUnregisterRequest(@Valid @NotNull LogoutRequest user) {
        LOGGER.info("Received logout request: {}", user);

        StationsManager.removeStationToken(user.getAddress());

        return new Response(Response.Status.success);
    }
}
