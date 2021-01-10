package com.it.hybrid.weatherapi.models.persistance.projection;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(
    description = "Base details about city with average temperature"
)
public class CityAverageTemperature {
    
    private Long id;

    private String name;

    private String countryCode;
    
    private BigDecimal averageTemperature;
}
