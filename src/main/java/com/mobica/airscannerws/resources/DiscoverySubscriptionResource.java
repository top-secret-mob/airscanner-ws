package com.mobica.airscannerws.resources;

import com.google.common.base.Strings;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.core.StationsManager;
import com.mobica.airscannerws.core.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/discovery")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoverySubscriptionResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverySubscriptionResource.class);

    public DiscoverySubscriptionResource() {
    }

    @POST
    @Path("/subscribe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveSubscribeRequest(@HeaderParam("Authorization") String token) {
        LOGGER.info("Received register request: {}", token);

        final String authKey = TokenParser.parseToken(token);
        if (Strings.isNullOrEmpty(authKey) || !StationsManager.isRegistered(authKey)) {
            return new Response(Response.Status.error, "Not authorized");
        }

        if (!StationsManager.subscribeForDiscovery(authKey)) {
            return new Response(Response.Status.error, "Failed to register");
        }

        return new Response(Response.Status.success);
    }

    @POST
    @Path("/unsubscribe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveUnsubscribeRequest(@HeaderParam("Authorization") String token) {
        LOGGER.info("Received unregister request: {}", token);

        final String authKey = TokenParser.parseToken(token);
        if (Strings.isNullOrEmpty(authKey) || !StationsManager.isRegistered(authKey)) {
            return new Response(Response.Status.error, "Not authorized");
        }

        if (!StationsManager.unsubscribeFromDiscovery(authKey)) {
            return new Response(Response.Status.error, "Not subscribed for discovery");
        }

        return new Response(Response.Status.success);
    }
}
