package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class GcmUpstreamRequest {
    private String[] registration_ids;
    private GcmMessage data;

    public GcmUpstreamRequest() {
        // Jackson deserialization
    }

    public GcmUpstreamRequest(String[] registration_ids) {
        this.registration_ids = registration_ids;
    }

    public GcmUpstreamRequest(String[] registration_ids, GcmMessage data) {
        this.registration_ids = registration_ids;
        this.data = data;
    }

    @JsonProperty("registration_ids")
    public String[] getRegistrationIds() {
        return registration_ids;
    }

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public GcmMessage getData() {
        return data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("registration_ids", registration_ids)
                .add("data", data)
                .toString();
    }
}
