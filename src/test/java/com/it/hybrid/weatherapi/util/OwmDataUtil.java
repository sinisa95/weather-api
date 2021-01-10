package com.it.hybrid.weatherapi.util;

import java.math.BigDecimal;
import java.util.Arrays;

import com.it.hybrid.weatherapi.models.owm.City;
import com.it.hybrid.weatherapi.models.owm.Coord;
import com.it.hybrid.weatherapi.models.owm.ForecastResponse;
import com.it.hybrid.weatherapi.models.owm.ListWeatherItem;
import com.it.hybrid.weatherapi.models.owm.Main;

public class OwmDataUtil {
    
    public static ForecastResponse createForecastResponse() {
        ForecastResponse forecast = new ForecastResponse();
        
        City berlin = new City();
        berlin.setName("Oslo");
        berlin.setCountry("NO");
        berlin.setId(1235L);
        berlin.setTimezone((short)3600);
        
        Coord coord = new Coord();
        coord.setLat(BigDecimal.valueOf(59.91));
        coord.setLon(BigDecimal.valueOf(10.75));
        berlin.setCoord(coord);

        forecast.setCity(berlin);

        ListWeatherItem weatherItem1 = new ListWeatherItem();
        weatherItem1.setDt(1609859867L); 
        Main main1 = new Main();
        main1.setTemp(BigDecimal.valueOf(291));
        weatherItem1.setMain(main1); 
        
        ListWeatherItem weatherItem2 = new ListWeatherItem();
        weatherItem2.setDt(1609863467L); 
        Main main2 = new Main();
        main2.setTemp(BigDecimal.valueOf(292));
        weatherItem2.setMain(main2);

        ListWeatherItem weatherItem3 = new ListWeatherItem();
        weatherItem3.setDt(1609864467L); 
        Main main3 = new Main();
        main3.setTemp(BigDecimal.valueOf(292));
        weatherItem3.setMain(main3); 
        
        forecast.setList(Arrays.asList(weatherItem1, weatherItem2, weatherItem3));

        return forecast;
    }
}
