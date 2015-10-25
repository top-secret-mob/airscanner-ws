package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wojtek on 25.10.15.
 */
public class StatusResponse extends Response {
    private String[] active;

    public StatusResponse() {
        super(Status.success);
    }

    /**
     * Active MAC addresses that should be monitored
     *
     * @param active
     */
    public StatusResponse(String[] active) {
        super(Status.success);
        this.active = active;
    }

    public StatusResponse(String error) {
        super(Status.error, error);
    }

    @JsonProperty
    public String[] getActive() {
        return active;
    }

    public void setActive(String[] active) {
        this.active = active;
    }
}
