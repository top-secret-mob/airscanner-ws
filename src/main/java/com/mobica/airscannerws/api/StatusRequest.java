package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

public class StatusRequest {
    @NotEmpty
    private String address;
    private boolean online;

    public StatusRequest() {
        // Jackson deserialization
    }

    public StatusRequest(String address, boolean online) {
        this.address = address;
        this.online = online;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    public boolean isOnline() {
        return online;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("online", online)
                .toString();
    }
}
