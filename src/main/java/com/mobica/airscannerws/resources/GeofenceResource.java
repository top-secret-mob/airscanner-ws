package com.mobica.airscannerws.resources;

import com.google.common.base.Strings;
import com.mobica.airscannerws.api.GeoFence;
import com.mobica.airscannerws.api.GeoFenceResponse;
import com.mobica.airscannerws.api.GeoFenceUpdateRequest;
import com.mobica.airscannerws.api.Response;
import com.mobica.airscannerws.core.GeofenceManager;
import com.mobica.airscannerws.core.StationsManager;
import com.mobica.airscannerws.core.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/geofence")
@Produces(MediaType.APPLICATION_JSON)
public class GeofenceResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeofenceResource.class);

    public GeofenceResource() {
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public GeoFenceResponse receiveGeofencesListRequest(@QueryParam("latitude") double latitude,
                                                        @QueryParam("longitude") double longitude,
                                                        @HeaderParam("Authorization") String token) {
        LOGGER.info("Received geofence request: {}-{} auth:{}", latitude, longitude, token);

        final String authKey = TokenParser.parseToken(token);
        if (Strings.isNullOrEmpty(authKey) || !StationsManager.isRegistered(authKey)) {
            return new GeoFenceResponse(Response.Status.error, "Not authorized");
        }

        final List<GeoFence> fences = GeofenceManager.getGeofences(authKey, latitude, longitude);

        return new GeoFenceResponse(fences);
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveGeofencesListRequest(@Valid @NotNull GeoFenceUpdateRequest request,
                                                        @HeaderParam("Authorization") String token) {
        LOGGER.info("Received geofence request: {}-{} auth:{}", request, token);

        final String authKey = TokenParser.parseToken(token);
        if (Strings.isNullOrEmpty(authKey) || !StationsManager.isRegistered(authKey)) {
            return new GeoFenceResponse(Response.Status.error, "Not authorized");
        }

        GeofenceManager.setGeofences(authKey, request.getGeofences());

        return new Response(Response.Status.success);
    }
}
