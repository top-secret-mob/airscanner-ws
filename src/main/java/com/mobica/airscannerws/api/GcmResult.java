package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Single result of GCM message
 */
public class GcmResult {
    private String message_id;
    private String error;
    private String registration_id;

    public GcmResult() {
    }

    public GcmResult(String message_id, String error, String registration_id) {
        this.message_id = message_id;
        this.error = error;
        this.registration_id = registration_id;
    }

    @JsonProperty("message_id")
    public String getMessageId() {
        return message_id;
    }

    public void setMessageId(String message_id) {
        this.message_id = message_id;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonProperty("registration_id")
    public String getRegistrationId() {
        return registration_id;
    }

    public void setRegistrationId(String registration_id) {
        this.registration_id = registration_id;
    }

    @Override
    public String toString() {
        return "GcmResult{" +
                "message_id='" + message_id + '\'' +
                ", error='" + error + '\'' +
                ", registration_id='" + registration_id + '\'' +
                '}';
    }
}
