package com.it.hybrid.weatherapi.controllers;

import java.util.List;
import java.util.Optional;

import com.it.hybrid.weatherapi.models.persistance.projection.CityAverageTemperature;
import com.it.hybrid.weatherapi.services.CityService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cities-average-temperature")
@RequiredArgsConstructor
public class AverageWeatherController {
    
    private final CityService cityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Get cities with average temperature. " + 
            "Cities can be filtered by multiple ids and sorted by average temprature."
    )
    public List<CityAverageTemperature> getAverageTemperatures(
        @RequestParam(value = "sort-avg-temp", required = false)
        @ApiParam("Flag for sorting cities by average temparature.") 
        Optional<Boolean> optionalSortAvgTemp,

        @RequestParam(value = "city-id", required = false) 
        @ApiParam("List of city ids used for filtering.")
        Optional<List<Long>> cityIds
    ) {
        return cityService.getAverageTemperatures(optionalSortAvgTemp, cityIds);
    }

}
