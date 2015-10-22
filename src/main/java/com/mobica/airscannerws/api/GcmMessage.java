package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wojtek on 21.10.15.
 */
public class GcmMessage {

    /**
     * bluetooth state
     */
    public enum BTState {
        enabled,
        disabled
    }

    @NotEmpty
    private BTState bt_state;

    public GcmMessage() {
    }

    public GcmMessage(BTState bt_state) {
        this.bt_state = bt_state;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public BTState getBt_state() {
        return bt_state;
    }

    @Override
    public String toString() {
        return "GcmMessage{" +
                "bt_state='" + bt_state + '\'' +
                '}';
    }
}
