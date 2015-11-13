package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wojtek on 21.10.15.
 */
public class GcmMessage {
    /**
     * Message type
     */
    public enum Type {
        discovery_status
    }

    private Type message_type;
    private boolean in_range;
    private boolean status_change;

    public GcmMessage() {
    }

    public GcmMessage(Type messageType, boolean in_range, boolean status_change) {
        this.message_type = messageType;
        this.in_range = in_range;
        this.status_change = status_change;
    }

    @JsonProperty("message_type")
    public Type getMessageType() {
        return message_type;
    }

    @JsonProperty("in_range")
    public boolean isInRange() {
        return in_range;
    }

    @JsonProperty("status_change")
    public boolean isStatusChange() {
        return status_change;
    }

    @Override
    public String toString() {
        return "GcmMessage{" +
                "message_type=" + message_type +
                ", in_range=" + in_range +
                ", status_change=" + status_change +
                '}';
    }
}
