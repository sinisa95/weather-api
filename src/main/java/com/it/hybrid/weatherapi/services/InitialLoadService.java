package com.it.hybrid.weatherapi.services;

import java.util.List;

import com.it.hybrid.weatherapi.models.City;

import reactor.core.publisher.Flux;

public interface InitialLoadService {
    
    /**
     * Load specified cities with weather information from API. 
     * @param cityNames List of city names used for loading.
     * @return Flux object of City entities.
     */
    Flux<City> loadCitiesWithWeather(List<String> cityNames);
}
