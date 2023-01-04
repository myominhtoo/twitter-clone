package com.lio.api.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources(
        value = {
                @PropertySource("classpath:/api-routes.yml")
        }
)
public class ResourceConfig {
}
