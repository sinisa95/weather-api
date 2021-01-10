package com.it.hybrid.weatherapi.repositories;

import com.it.hybrid.weatherapi.models.Weather;

import org.springframework.stereotype.Repository;


/**
 * Repository for weather entity.
 */
@Repository
public interface WeatherRepository extends BaseRepository<Weather>{
    
}
