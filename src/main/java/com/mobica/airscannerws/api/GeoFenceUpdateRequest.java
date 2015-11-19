package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by woos on 2015-11-18.
 */
public class GeoFenceUpdateRequest {
    @NotNull
    private List<GeoFence> geofences;

    public GeoFenceUpdateRequest() {
        // Jackson deserialization
    }

    public GeoFenceUpdateRequest(List<GeoFence> geofences) {
        this.geofences = geofences;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<GeoFence> getGeofences() {
        return geofences;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("geofences", geofences)
                .toString();
    }
}
