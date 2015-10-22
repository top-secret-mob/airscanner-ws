package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wojtek on 21.10.15.
 */
public class RegisterRequest {
    @NotEmpty
    private String address;
    @NotEmpty
    private String gcmRegId;

    public RegisterRequest() {
        // Jackson deserialization
    }

    public RegisterRequest(String address, String gcmRegId) {
        this.address = address;
        this.gcmRegId = gcmRegId;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    public String getGcmRegId() {
        return gcmRegId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("gcmRegId", gcmRegId)
                .toString();
    }
}
