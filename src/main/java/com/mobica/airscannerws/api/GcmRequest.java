package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

public class GcmRequest {
    @NotEmpty
    private String gcm_token;
    @NotEmpty
    private String address;
    private GcmMessage data;

    public GcmRequest() {
        // Jackson deserialization
    }

    public GcmRequest(String gcm_token, String address, GcmMessage data) {
        this.gcm_token = gcm_token;
        this.address = address;
        this.data = data;
    }


    @JsonProperty
    public String getGcm_token() {
        return gcm_token;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public GcmMessage getData() {
        return data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gcm_token", gcm_token)
                .add("address", address)
                .add("data", data)
                .toString();
    }
}
