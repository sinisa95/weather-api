package com.it.hybrid.weatherapi.configurations.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.RequiredArgsConstructor;

/**
 * Configuration for cities that will be used in application.
 */
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "weather")
public class WeatherCityConfiguration {
 
    private final List<String> cities;   

    public List<String> getCityNames(){
        return new ArrayList<String>(cities);
    }
}
