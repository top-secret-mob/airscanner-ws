package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wojtek on 21.10.15.
 */
public class GcmMessage {

    private String message;

    public GcmMessage() {
    }

    public GcmMessage(String message) {
        this.message = message;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMessage() {
        return message;
    }
}
