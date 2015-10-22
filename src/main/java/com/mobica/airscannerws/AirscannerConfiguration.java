package com.mobica.airscannerws;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AirscannerConfiguration extends Configuration {
    @NotEmpty
    private String gcm_server;

    @JsonProperty
    public String getGcm_server() {
        return gcm_server;
    }

    @JsonProperty
    public void setGcm_server(String gcm_server) {
        this.gcm_server = gcm_server;
    }
}
