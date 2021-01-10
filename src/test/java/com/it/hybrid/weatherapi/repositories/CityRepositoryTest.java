package com.it.hybrid.weatherapi.repositories;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.Weather;
import com.it.hybrid.weatherapi.models.persistance.projection.CityAverageTemperature;
import com.it.hybrid.weatherapi.models.persistance.projection.CityDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CityRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    private List<City> cities;

    @BeforeEach
    public void init() {
        cities = createCityList()
            .stream()
            .map(entityManager::persistAndFlush)
            .collect(Collectors.toList());
    }  

    private List<City> createCityList() {
        City berlin = new City();
        berlin.setName("Berlin");
        berlin.setCountryCode("DE");
        berlin.setExternalId(1234L);
        berlin.setLatitude(BigDecimal.valueOf(45.45));
        berlin.setLongitude(BigDecimal.valueOf(55.45));
        berlin.setSecondsTimezoneOffset((short)3600);

        Weather berlinWeather1 = new Weather();
        berlinWeather1.setUnixTime(1609859867L); 
        berlinWeather1.setTemperature(BigDecimal.valueOf(291));
        berlinWeather1.setCity(berlin);
        Weather berlinWeather2 = new Weather();
        berlinWeather2.setUnixTime(1609863467L); 
        berlinWeather2.setTemperature(BigDecimal.valueOf(292));
        berlinWeather2.setCity(berlin);
        Weather berlinWeather3 = new Weather();
        berlinWeather3.setUnixTime(1609867067L); 
        berlinWeather3.setTemperature(BigDecimal.valueOf(294));
        berlinWeather3.setCity(berlin);

        berlin.setWeathers(Arrays.asList(berlinWeather1, berlinWeather2, berlinWeather3));
        
        City noviSad = new City();
        noviSad.setName("Novi Sad");
        noviSad.setCountryCode("RS");
        noviSad.setExternalId(4321L);
        noviSad.setLatitude(BigDecimal.valueOf(44.45));
        noviSad.setLongitude(BigDecimal.valueOf(44.56));
        noviSad.setSecondsTimezoneOffset((short)3600);

        Weather noviSadWeather1 = new Weather();
        noviSadWeather1.setUnixTime(1609859867L); 
        noviSadWeather1.setTemperature(BigDecimal.valueOf(281));
        noviSadWeather1.setCity(noviSad);
        Weather noviSadWeather2 = new Weather();
        noviSadWeather2.setUnixTime(1609863467L); 
        noviSadWeather2.setTemperature(BigDecimal.valueOf(292));
        noviSadWeather2.setCity(noviSad);
        Weather noviSadWeather3 = new Weather();
        noviSadWeather3.setUnixTime(1609867067L); 
        noviSadWeather3.setTemperature(BigDecimal.valueOf(284));
        noviSadWeather3.setCity(noviSad);
        
        noviSad.setWeathers(Arrays.asList(noviSadWeather1, noviSadWeather2, noviSadWeather3));

        City london = new City();
        london.setName("London");
        london.setCountryCode("GB");
        london.setExternalId(1111L);
        london.setLatitude(BigDecimal.valueOf(30.45));
        london.setLongitude(BigDecimal.valueOf(40.56));
        london.setSecondsTimezoneOffset((short)0);

        Weather londonWeather1 = new Weather();
        londonWeather1.setUnixTime(1609859867L); 
        londonWeather1.setTemperature(BigDecimal.valueOf(288));
        londonWeather1.setCity(london);
        Weather londonWeather2 = new Weather();
        londonWeather2.setUnixTime(1609863467L); 
        londonWeather2.setTemperature(BigDecimal.valueOf(291));
        londonWeather2.setCity(london);
        Weather londonWeather3 = new Weather();
        londonWeather3.setUnixTime(1609867067L); 
        londonWeather3.setTemperature(BigDecimal.valueOf(290));
        londonWeather3.setCity(london);

        london.setWeathers(Arrays.asList(londonWeather1, londonWeather2, londonWeather3));

        return Arrays.asList(berlin, noviSad, london);
    }

    @Test
    public void findAllProjectedCities() {
        
        List<CityDTO> projectedCities = cityRepository.findAllProjectedBy();

        assertArrayEquals(
            cities.stream().map(City::getId).toArray(), 
            projectedCities.stream().map(CityDTO::getId).toArray()
        );
        assertArrayEquals(
            cities.stream().map(City::getName).toArray(), 
            projectedCities.stream().map(CityDTO::getName).toArray()
        );
    }

    @Test
    public void findAllCitiesWithAvgTemp() {

        List<CityAverageTemperature> citiesWithAverageTemp = cityRepository.findCitiesWithAvgTemp(
            Optional.empty(), Optional.empty()
        );

        assertArrayEquals(
            cities.stream().map(City::getAverageTemperature).toArray(), 
            citiesWithAverageTemp.stream().map(CityAverageTemperature::getAverageTemperature).toArray()
        );
        assertArrayEquals(
            cities.stream().map(City::getId).toArray(), 
            citiesWithAverageTemp.stream().map(CityAverageTemperature::getId).toArray()
        );   
    }

    @Test
    public void findCitiesFilteredWithAvgTemp() {
        
        List<Long> filterCityIds = cities    
            .stream() 
            .map(City::getId)           
            .filter(id -> id % 2 == 1)
            .collect(Collectors.toList());

        List<CityAverageTemperature> citiesWithAverageTemp = cityRepository.findCitiesWithAvgTemp(
            Optional.of(filterCityIds), Optional.empty()
        );

        List<Long> filteredCityIds = citiesWithAverageTemp
            .stream()
            .map(CityAverageTemperature::getId)
            .collect(Collectors.toList());

        assertEquals(filterCityIds.size(), filteredCityIds.size());
        assertTrue(filterCityIds.containsAll(filteredCityIds));     
    }

    @Test
    public void findCitiesFilteredNonExistingWithAvgTemp() {
        
        Long maxId = cities.stream()
            .mapToLong(City::getId)
            .max()
            .orElse(0);

        List<Long> filterCityIds = Arrays.asList(maxId + 1, maxId + 2);

        List<CityAverageTemperature> citiesWithAverageTemp = cityRepository.findCitiesWithAvgTemp(
            Optional.of(filterCityIds), Optional.empty()
        );

        List<Long> filteredCityIds = citiesWithAverageTemp
            .stream()
            .map(CityAverageTemperature::getId)
            .collect(Collectors.toList());

        assertEquals(0, filteredCityIds.size());     
    }
  
    @Test
    public void findCitiesFilteredCombinedWithAvgTemp() {
        
        List<Long> filterCityIds = cities    
            .stream() 
            .map(City::getId)           
            .filter(id -> id % 2 == 1)
            .collect(Collectors.toList());
        
        Long maxId = cities.stream()
            .mapToLong(City::getId)
            .max()
            .orElse(0);

        filterCityIds.add(maxId + 1);

        List<CityAverageTemperature> citiesWithAverageTemp = cityRepository.findCitiesWithAvgTemp(
            Optional.of(filterCityIds), Optional.empty()
        );

        List<Long> filteredCityIds = citiesWithAverageTemp
            .stream()
            .map(CityAverageTemperature::getId)
            .collect(Collectors.toList());

        assertTrue(filterCityIds.containsAll(filteredCityIds));   
    }
    
    @Test
    public void sortedCitiesByAvgTemp() {
            
        List<CityAverageTemperature> citiesWithAverageTemp = cityRepository.findCitiesWithAvgTemp(
            Optional.empty(), Optional.of(true)
        );

        assertArrayEquals(
            citiesWithAverageTemp.stream()
                .map(CityAverageTemperature::getAverageTemperature).sorted().toArray(), 
            citiesWithAverageTemp.stream()
                .map(CityAverageTemperature::getAverageTemperature).toArray()
        );    
    }

    @Test
    public void findCitiesFilteredAndSortedByAvgTemp() {
    
        List<Long> filterCityIds = cities    
            .stream() 
            .map(City::getId)           
            .filter(id -> id % 2 == 1)
            .collect(Collectors.toList());
            
        List<CityAverageTemperature> citiesWithAverageTemp = cityRepository.findCitiesWithAvgTemp(
            Optional.of(filterCityIds), Optional.of(true)
        );

        assertArrayEquals(
            citiesWithAverageTemp.stream()
                .map(CityAverageTemperature::getAverageTemperature).sorted().toArray(), 
            citiesWithAverageTemp.stream()
                .map(CityAverageTemperature::getAverageTemperature).toArray()
        );     
       
        List<Long> filteredCityIds = citiesWithAverageTemp
            .stream()
            .map(CityAverageTemperature::getId)
            .collect(Collectors.toList());

        assertEquals(filterCityIds.size(), filteredCityIds.size());
        assertTrue(filterCityIds.containsAll(filteredCityIds));
    }

}
