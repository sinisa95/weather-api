
package com.it.hybrid.weatherapi.models.owm;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastResponse {

    private List<ListWeatherItem> list;
    
    private City city;

}
