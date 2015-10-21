package com.mobica.airscannerws.resources;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Strings;
import com.mobica.airscannerws.api.GcmMessage;
import com.mobica.airscannerws.api.GcmRequest;
import com.mobica.airscannerws.api.GcmUpstreamRequest;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.core.User;
import com.mobica.airscannerws.core.Users;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.impl.provider.entity.StringProvider;
import org.glassfish.jersey.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/gcm")
@Produces(MediaType.APPLICATION_JSON)
public class WificrackResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WificrackResource.class);
    private static final String GCM_SERVER = "https://gcm-http.googleapis.com/gcm/send";

    private final String gcmToken;

    public WificrackResource(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveGcmRequest(@Valid GcmRequest request) {
        LOGGER.info("Received a request: {}", request);

        if (Strings.isNullOrEmpty(request.getAddress())) {
            return new Response(Response.Status.error, "address must be specified");
        }

        final User user = Users.getInstance().getUser(request.getAddress());
        if (user == null) {
            return new Response(Response.Status.error, "address '" + request.getAddress() + "' is not registered");
        }

        ClientConfig cc = new DefaultClientConfig();
//        cc.getClasses().add(JacksonJsonProvider.class);
        cc.getClasses().add(StringProvider.class);
        final Client client = Client.create();
try {

    final GcmUpstreamRequest gcmMsg =
            new GcmUpstreamRequest(new String[]{user.gcmId}, new GcmMessage());

    client.resource(GCM_SERVER)
            .accept(MediaType.APPLICATION_JSON_TYPE)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .header("Authorization", "key=" + gcmToken)
            .post(gcmMsg);
//    response.bufferEntity();
}catch (Exception ex) {
    ex.printStackTrace();
}
        return new Response(Response.Status.success, "");
    }
}
