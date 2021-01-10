package com.it.hybrid.weatherapi.models.persistance.projection;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;

@ApiModel(
    description = "Details about city"
)
public interface CityDTO {
    
    Long getId();

    String getName();

    BigDecimal getLongitude();

    BigDecimal getLatitude();

    String getCountryCode();

    Short getSecondsTimezoneOffset();

}
