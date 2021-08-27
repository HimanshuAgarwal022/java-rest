package com.example.demo;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class JsonbConfig extends ResourceConfig {
    public JsonbConfig() {
        register(TestService.class);
    }
}