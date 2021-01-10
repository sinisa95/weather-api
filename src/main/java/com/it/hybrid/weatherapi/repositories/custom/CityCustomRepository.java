package com.it.hybrid.weatherapi.repositories.custom;

import java.util.List;
import java.util.Optional;

import com.it.hybrid.weatherapi.models.persistance.projection.CityAverageTemperature;

/**
 * Custom repository interface of city entity
 */
public interface CityCustomRepository {
    
    List<CityAverageTemperature> findCitiesWithAvgTemp(
        Optional<List<Long>> optionalCityIds, 
        Optional<Boolean> optionalSortByAvgTemp
    );
}