package com.it.hybrid.weatherapi.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.Weather;
import com.it.hybrid.weatherapi.models.owm.ForecastResponse;
import com.it.hybrid.weatherapi.models.owm.ListWeatherItem;
import com.it.hybrid.weatherapi.util.OwmDataUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OwmConverterTest {

    private OwmConverter owmConverter;

    @BeforeEach
    void initialize() {
        owmConverter = new OwmConverter();
    }
    
    @Test
    public void convertOwmResponseToCityEntityWithWeathers() {
        
        ForecastResponse forecast = OwmDataUtil.createForecastResponse();

        City convertedCity = owmConverter.convertToCityEntity(forecast);

        assertEquals(forecast.getCity().getName(), convertedCity.getName());
        assertEquals(forecast.getCity().getCountry(), convertedCity.getCountryCode());
        assertEquals(forecast.getCity().getId(), convertedCity.getExternalId());
        assertEquals(forecast.getCity().getCoord().getLat(), convertedCity.getLatitude());
        assertEquals(forecast.getCity().getCoord().getLon(), convertedCity.getLongitude());
        assertEquals(forecast.getCity().getTimezone(), convertedCity.getSecondsTimezoneOffset());
        
        assertEquals(forecast.getList().size(), convertedCity.getWeathers().size());
        
        for(int i = 0; i < forecast.getList().size(); i++) {
            assertWeatherItem(forecast.getList().get(i), convertedCity.getWeathers().get(i));
        }
        
    }

    private void assertWeatherItem(ListWeatherItem weatherItem, Weather actualWeather) {
        assertEquals(weatherItem.getDt(), actualWeather.getUnixTime());
        assertEquals(weatherItem.getMain().getTemp(), actualWeather.getTemperature());
    }

}
