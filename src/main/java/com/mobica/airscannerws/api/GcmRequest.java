package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class GcmRequest {
    private String address;
    private String data;

    public GcmRequest() {
        // Jackson deserialization
    }

    public GcmRequest(String address) {
        this.address = address;
    }

    public GcmRequest(String address, String data) {
        this.address = address;
        this.data = data;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("data", data)
                .toString();
    }
}
