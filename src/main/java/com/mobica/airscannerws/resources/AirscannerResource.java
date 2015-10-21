package com.mobica.airscannerws.resources;

import com.google.common.base.Strings;
import com.mobica.airscannerws.api.RegisterRequest;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.core.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class AirscannerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirscannerResource.class);

    private final String defaultValue;
    private final AtomicLong counter;

    public AirscannerResource(String defaultValue) {
        this.defaultValue = defaultValue;
        this.counter = new AtomicLong();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveRegisterRequest(@Valid @NotNull RegisterRequest user) {
        LOGGER.info("Received register request: {}", user);
        if (Strings.isNullOrEmpty(user.getAddress())) {
            return new Response(Response.Status.error, "address must be specified");
        }

        if (Strings.isNullOrEmpty(user.getGcmRegId())) {
            return new Response(Response.Status.error, "gcmRegId must be specified");
        }

        Users.getInstance().addUser(user.getAddress(), user.getGcmRegId());

        return new Response(Response.Status.success);
    }
}
