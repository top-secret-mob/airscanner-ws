package com.mobica.airscannerws;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AirscannerConfiguration extends Configuration {
    @NotEmpty
    private String gcm_server;
    @NotEmpty
    private String gcm_api_token;
    private long station_ttl;

    @JsonProperty
    public String getGcm_server() {
        return gcm_server;
    }

    @JsonProperty
    public void setGcm_server(String gcm_server) {
        this.gcm_server = gcm_server;
    }

    @JsonProperty
    public String getGcm_api_token() {
        return gcm_api_token;
    }

    @JsonProperty
    public void setGcm_api_token(String gcm_api_token) {
        this.gcm_api_token = gcm_api_token;
    }

    @JsonProperty
    public long getStation_ttl() {
        return station_ttl;
    }

    @JsonProperty
    public void setStation_ttl(long station_ttl) {
        this.station_ttl = station_ttl;
    }
}
