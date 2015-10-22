package com.mobica.airscannerws.resources;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.mobica.airscannerws.api.*;
import com.mobica.airscannerws.core.User;
import com.mobica.airscannerws.core.Users;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.impl.provider.entity.StringProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/gcm")
@Produces(MediaType.APPLICATION_JSON)
public class WificrackResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WificrackResource.class);
    private final String gcmServer;

    public WificrackResource(String gcmServer) {
        this.gcmServer = gcmServer;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveGcmRequest(@Valid @NotNull GcmRequest request) {
        LOGGER.info("Received a request: {}", request);

        final User user = Users.getInstance().getUser(request.getAddress());
        if (user == null) {
            return new Response(Response.Status.error, "address '" + request.getAddress() + "' is not registered");
        }

        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().clear();
        cc.getClasses().add(JacksonJsonProvider.class);
        cc.getClasses().add(StringProvider.class);
        final Client client = Client.create(cc);

        final GcmUpstreamRequest gcmMsg =
                new GcmUpstreamRequest(new String[]{user.gcmId}, request.getData());

        try {
            final ClientResponse response = client.resource(gcmServer)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "key=" + request.getGcm_token())
                    .post(ClientResponse.class, gcmMsg);

            if (response == null) {
                LOGGER.error("Received a null response");
                return new Response(Response.Status.error, "Unrecognized response");
            }

            response.bufferEntity();
            final GcmResponse gcmResponse;
            try {
                gcmResponse = response.getEntity(GcmResponse.class);
            } catch (Exception ex) {
                return new Response(Response.Status.error, "Failed to parse JSON response from GCM");
            }

            LOGGER.info("Response: {}", gcmResponse);

            if (gcmResponse.getSuccess() > 0 && gcmResponse.getFailure() == 0) {
                return new Response(Response.Status.success);
            }

            final GcmResult[] results = gcmResponse.getResults();
            if (results != null && results.length > 0) {
                return new Response(Response.Status.error, results[0].getError());
            }

            return new Response(Response.Status.error, "Unrecognized response");

        } catch (Exception ex) {
            LOGGER.error("Internal error", ex);
            return new Response(Response.Status.error, "WS internal error");
        }
    }
}
