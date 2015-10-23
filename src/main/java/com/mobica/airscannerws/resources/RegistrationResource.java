package com.mobica.airscannerws.resources;

import com.mobica.airscannerws.api.RegisterRequest;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.core.StationsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResource.class);

    public RegistrationResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveRegisterRequest(@Valid @NotNull RegisterRequest user) {
        LOGGER.info("Received register request: {}", user);

        StationsManager.updateStationToken(user.getAddress(), user.getGcmRegId());

        return new Response(Response.Status.success);
    }
}
