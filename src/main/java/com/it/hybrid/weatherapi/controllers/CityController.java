package com.it.hybrid.weatherapi.controllers;

import java.util.List;

import com.it.hybrid.weatherapi.models.persistance.projection.CityDTO;
import com.it.hybrid.weatherapi.services.CityService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * Controller for city
 */
@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {
    
    private final CityService cityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get list of all cities.")
    public List<CityDTO> getCities() {
        return cityService.getCities();
    }
    
}
