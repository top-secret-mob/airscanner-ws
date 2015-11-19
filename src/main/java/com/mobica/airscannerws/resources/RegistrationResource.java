package com.mobica.airscannerws.resources;

import com.google.common.base.Strings;
import com.mobica.airscannerws.api.LoginRequest;
import com.mobica.airscannerws.api.RegisterResponse;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.core.StationsManager;
import com.mobica.airscannerws.core.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.HeaderParam;
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
    public RegisterResponse receiveRegisterRequest(@Valid @NotNull LoginRequest user) {
        LOGGER.info("Received login request: {}", user);

        final String token = StationsManager.registerStationToken(user.getAddress(), user.getGcmRegId());

        return new RegisterResponse(token);
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveUnregisterRequest(@HeaderParam("Authorization") String token) {
        LOGGER.info("Received logout request: {}", token);

        final String authKey = TokenParser.parseToken(token);
        if (Strings.isNullOrEmpty(authKey) || !StationsManager.isRegistered(authKey)) {
            return new Response(Response.Status.error, "Not authorized");
        }

        StationsManager.removeStationToken(authKey);

        return new Response(Response.Status.success);
    }
}
