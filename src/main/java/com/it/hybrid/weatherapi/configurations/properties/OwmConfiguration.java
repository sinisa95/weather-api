package com.it.hybrid.weatherapi.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Configuration for OpenWeatherMap API.
 */
@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "owm")
public class OwmConfiguration {
 
    private String apiKey;

    private String endpointUrl; 
}
