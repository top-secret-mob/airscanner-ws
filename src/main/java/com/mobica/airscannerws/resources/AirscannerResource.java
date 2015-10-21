package com.mobica.airscannerws.resources;

import com.codahale.metrics.annotation.Timed;
import com.mobica.airscannerws.api.RegistrationConfirmation;
import com.mobica.airscannerws.api.Saying;
import com.google.common.base.Optional;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.jersey.params.DateTimeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class AirscannerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirscannerResource.class);

    private final String defaultValue;
    private final AtomicLong counter;

    public AirscannerResource(String defaultValue) {
        this.defaultValue = defaultValue;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed(name = "get-requests")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public RegistrationConfirmation sayHello(@QueryParam("name") Optional<String> name) {
        return new RegistrationConfirmation(RegistrationConfirmation.Status.success);
    }

    @POST
    public void receiveHello(@Valid Saying saying) {
        LOGGER.info("Received a saying: {}", saying);
    }

    @GET
    @Path("/date")
    @Produces(MediaType.TEXT_PLAIN)
    public String receiveDate(@QueryParam("date") Optional<DateTimeParam> dateTimeParam) {
        if (dateTimeParam.isPresent()) {
            final DateTimeParam actualDateTimeParam = dateTimeParam.get();
            LOGGER.info("Received a date: {}", actualDateTimeParam);
            return actualDateTimeParam.get().toString();
        } else {
            LOGGER.warn("No received date");
            return null;
        }
    }
}
