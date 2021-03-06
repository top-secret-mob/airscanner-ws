package com.mobica.airscannerws.storage;

/**
 * Created by wojtek on 22.10.15.
 */
public class Station {
    // MAC address
    private final String address;
    // auth token
    private final String authToken;
    // gcm registration id
    private String gcmId;
    // if detected by wifi scanner
    private boolean inRange;
    // if detecting is enabled
    private boolean discoveryEnabled;
    // last status update time
    private long lastUpdateTime;

    public Station(String address, String authToken) {
        this.address = address;
        this.authToken = authToken;
    }

    public String getAddress() {
        return address;
    }

    public String getAuthToken() {
        return authToken;
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

    public boolean isDiscoveryEnabled() {
        return discoveryEnabled;
    }

    public void setDiscoveryEnabled(boolean discoveryEnabled) {
        this.discoveryEnabled = discoveryEnabled;
    }
}
