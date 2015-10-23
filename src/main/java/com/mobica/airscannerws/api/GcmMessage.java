package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wojtek on 21.10.15.
 */
public class GcmMessage {
    private boolean bt_enabled;
    private boolean status_change;

    public GcmMessage() {
    }

    public GcmMessage(boolean bt_enabled, boolean status_change) {
        this.bt_enabled = bt_enabled;
        this.status_change = status_change;
    }

    @JsonProperty
    public boolean isBt_enabled() {
        return bt_enabled;
    }

    @JsonProperty
    public boolean isStatus_change() {
        return status_change;
    }

    @Override
    public String toString() {
        return "GcmMessage{" +
                "bt_enabled=" + bt_enabled +
                ", status_change=" + status_change +
                '}';
    }
}
