package com.it.hybrid.weatherapi.configurations;


import com.it.hybrid.weatherapi.configurations.properties.WeatherCityConfiguration;
import com.it.hybrid.weatherapi.repositories.CityRepository;
import com.it.hybrid.weatherapi.services.InitialLoadService;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * Configuration for startup of application 
 */
@Component
@RequiredArgsConstructor
public class StartupConfiguration {

    private final WeatherCityConfiguration weatherCityConfiguration;

    private final InitialLoadService initialLoadService;
    
    private final CityRepository cityRepository;

    /**
     * When spring application is ready, initilaze data. 
     * @param event 
     */
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loadAndSaveData();
    }

    /**
     * Load data from api and save it to database.
     */
    @Transactional
    private void loadAndSaveData(){
        initialLoadService
            .loadCitiesWithWeather(weatherCityConfiguration.getCityNames())
            .subscribe(cityRepository::save);
    }

}

