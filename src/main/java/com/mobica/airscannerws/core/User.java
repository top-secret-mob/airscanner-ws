package com.mobica.airscannerws.core;

/**
 * Created by wojtek on 21.10.15.
 */
public class User {
    // MAC address
    public String address;
    // gcm registration id
    public String gcmId;

    public User(String address, String gcmId) {
        this.address = address;
        this.gcmId = gcmId;
    }
}
