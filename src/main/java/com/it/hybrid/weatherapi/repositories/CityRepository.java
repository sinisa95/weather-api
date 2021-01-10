package com.it.hybrid.weatherapi.repositories;

import java.util.List;

import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.persistance.projection.CityDTO;
import com.it.hybrid.weatherapi.repositories.custom.CityCustomRepository;

/**
 * Repository for city entity.
 */
public interface CityRepository extends BaseRepository<City>, CityCustomRepository {

    List<CityDTO> findAllProjectedBy();
}
