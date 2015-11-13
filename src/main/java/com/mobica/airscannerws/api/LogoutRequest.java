package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wojtek on 21.10.15.
 */
public class LogoutRequest {
    @NotEmpty
    private String address;

    public LogoutRequest() {
        // Jackson deserialization
    }

    public LogoutRequest(String address) {
        this.address = address;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .toString();
    }
}
