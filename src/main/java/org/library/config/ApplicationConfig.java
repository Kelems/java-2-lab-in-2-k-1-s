package org.library.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.library.service.JerseyRest;

public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(JerseyRest.class);
    }
}