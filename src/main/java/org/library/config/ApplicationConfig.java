package org.library.config;

import org.library.service.JerseyRest;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(JerseyRest.class);
//        packages("org.evil.service");
    }
}