package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wojtek on 21.10.15.
 */
public class GcmMessage {
    private boolean bt_enabled;

    public GcmMessage() {
    }

    public GcmMessage(boolean bt_enabled) {
        this.bt_enabled = bt_enabled;
    }

    @JsonProperty
    public boolean isBt_enabled() {
        return bt_enabled;
    }

    @Override
    public String toString() {
        return "GcmMessage{" +
                "bt_enabled='" + bt_enabled + '\'' +
                '}';
    }
}
