package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by woos on 2015-11-17.
 */
public class GeoFence {
    private double latitude;
    private double longitude;
    private float radius;
    private String id;

    public GeoFence() {
    }

    public GeoFence(String id, double latitude, double longitude, float radius) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty
    public double getLongitude() {
        return longitude;
    }

    @JsonProperty
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty
    public float getRadius() {
        return radius;
    }

    @JsonProperty
    public void setRadius(float radius) {
        this.radius = radius;
    }
}
