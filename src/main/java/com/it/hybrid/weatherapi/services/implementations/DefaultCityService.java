package com.it.hybrid.weatherapi.services.implementations;

import java.util.List;
import java.util.Optional;

import com.it.hybrid.weatherapi.models.persistance.projection.CityDTO;
import com.it.hybrid.weatherapi.models.persistance.projection.CityAverageTemperature;
import com.it.hybrid.weatherapi.repositories.CityRepository;
import com.it.hybrid.weatherapi.services.CityService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Default implementation of CityService using database as persitance.
 */
@Service
@RequiredArgsConstructor
public class DefaultCityService implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<CityDTO> getCities() {
        return cityRepository.findAllProjectedBy();
    }

    @Override
    public List<CityAverageTemperature> getAverageTemperatures(
        Optional<Boolean> optionalSortAvgTemp,
        Optional<List<Long>> optionalCityIds
    ) {
        return cityRepository.findCitiesWithAvgTemp(optionalCityIds, optionalSortAvgTemp);
    }
    
}
