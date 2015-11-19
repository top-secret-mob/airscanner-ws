package com.mobica.airscannerws.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by woos on 2015-11-19.
 */
public class RegisterResponse extends Response {
    private String token;

    public RegisterResponse() {
    }

    public RegisterResponse(String token) {
        this.token = token;
    }

    public RegisterResponse(Status status, String error) {
        super(status, error);
    }

    @JsonProperty
    public String getToken() {
        return token;
    }

    @JsonProperty
    public void setToken(String token) {
        this.token = token;
    }
}
