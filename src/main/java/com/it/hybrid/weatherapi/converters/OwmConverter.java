package com.it.hybrid.weatherapi.converters;

import java.util.List;
import java.util.stream.Collectors;

import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.Weather;
import com.it.hybrid.weatherapi.models.owm.ForecastResponse;
import com.it.hybrid.weatherapi.models.owm.ListWeatherItem;

import org.springframework.stereotype.Service;

/**
 * Converter from OpenWeather entities to application entities.
 */
@Service
public class OwmConverter {
   
    public City convertToCityEntity(ForecastResponse forecastResponse){
        City city = new City();

        city.setExternalId(forecastResponse.getCity().getId());
        city.setCountryCode(forecastResponse.getCity().getCountry());
        city.setName(forecastResponse.getCity().getName());
        city.setLatitude(forecastResponse.getCity().getCoord().getLat());
        city.setLongitude(forecastResponse.getCity().getCoord().getLon());
        city.setSecondsTimezoneOffset(forecastResponse.getCity().getTimezone());

        List<Weather> weathers = forecastResponse.getList().stream()
            .map(weatherItem -> this.convertToWeatherEntity(weatherItem, city))
            .collect(Collectors.toList());
                
        city.setWeathers(weathers);

        return city;
    }

    private Weather convertToWeatherEntity(ListWeatherItem weatherItem, City city) {
        Weather weather = new Weather();

        weather.setUnixTime(weatherItem.getDt()); 
        weather.setTemperature(weatherItem.getMain().getTemp());
        weather.setCity(city);
        
        return weather;
    }
}
