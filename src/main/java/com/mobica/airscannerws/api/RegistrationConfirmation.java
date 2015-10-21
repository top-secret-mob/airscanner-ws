package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Created by wojtek on 21.10.15.
 */
public class RegistrationConfirmation {
    public enum Status {
        success,
        error
    }

    private Status status;


    public RegistrationConfirmation() {
        // Jackson deserialization
    }

    public RegistrationConfirmation(Status status) {
        this.status = status;
    }

    @JsonProperty
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .toString();
    }
}
