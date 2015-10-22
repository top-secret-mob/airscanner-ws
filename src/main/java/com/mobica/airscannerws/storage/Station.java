package com.mobica.airscannerws.storage;

/**
 * Created by wojtek on 22.10.15.
 */
public class Station {
    // MAC address
    private final String address;
    // gcm registration id
    private String gcmId;
    // if detected by wifi scanner
    private boolean online;

    public Station(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
