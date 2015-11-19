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

    private Type message_id;
    private boolean in_range;
    private boolean status_change;

    public GcmMessage() {
    }

    public GcmMessage(Type messageId, boolean in_range, boolean status_change) {
        this.message_id = messageId;
        this.in_range = in_range;
        this.status_change = status_change;
    }

    @JsonProperty("message_id")
    public Type getMessageId() {
        return message_id;
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
        return "message_id{" +
                "message_type=" + message_id +
                ", in_range=" + in_range +
                ", status_change=" + status_change +
                '}';
    }
}
