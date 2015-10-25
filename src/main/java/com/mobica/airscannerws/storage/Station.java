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
    private boolean inRange;
    // last status update time
    private long lastUpdateTime;


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

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean online) {
        this.inRange = online;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
