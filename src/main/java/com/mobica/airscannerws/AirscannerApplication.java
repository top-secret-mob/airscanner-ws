package com.mobica.airscannerws;

import com.mobica.airscannerws.resources.AirscannerResource;
import com.mobica.airscannerws.resources.WificrackResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AirscannerApplication extends Application<AirscannerConfiguration> {
    public static void main(String[] args) throws Exception {
        new AirscannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<AirscannerConfiguration> bootstrap) {
    }

    @Override
    public void run(AirscannerConfiguration configuration, Environment environment) {
        environment.jersey().register(new AirscannerResource(configuration.getDefaultName()));
        environment.jersey().register(new WificrackResource(configuration.getGcmAppToken()));
    }
}
