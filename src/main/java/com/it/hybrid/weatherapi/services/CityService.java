package com.it.hybrid.weatherapi.services;

import java.util.List;
import java.util.Optional;

import com.it.hybrid.weatherapi.models.persistance.projection.CityDTO;
import com.it.hybrid.weatherapi.models.persistance.projection.CityAverageTemperature;

public interface CityService {

    /**
     * Get all available cities. 
     * @return List of object with details about city.
     */
	List<CityDTO> getCities();

    /**
     * Get cities with average temperatures. 
     * If cityIds are provided, cities will be filtered by those cityIds.
     * If sort is present and true, cities will be sorted by average temperature.
     * @param optionalSort Optional for sorting by average temperature
     * @param optionalCityIds Optional for list of cityIds for filtering.
     * @return List of cities with their average temperatures.
     */
    List<CityAverageTemperature> getAverageTemperatures(        
        Optional<Boolean> optionalSort,
        Optional<List<Long>> optionalCityIds
    );

}
