package com.it.hybrid.weatherapi.services.implementations;

import java.util.List;

import com.it.hybrid.weatherapi.configurations.properties.OwmConfiguration;
import com.it.hybrid.weatherapi.converters.OwmConverter;
import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.owm.ForecastResponse;
import com.it.hybrid.weatherapi.services.InitialLoadService;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Implemention of initial load service using OpenWeatherMap API.
 */
@Service
public class OwmInitialLoadService implements InitialLoadService {

    private final OwmConverter owmConverter;

    private final OwmConfiguration owmConfiguration;

    private final WebClient webClient;

	public OwmInitialLoadService(
        OwmConfiguration owmConfiguration, 
        OwmConverter owmConverter, 
        WebClient.Builder webClientBuilder
    ) {
        this.owmConverter = owmConverter;
        this.owmConfiguration = owmConfiguration;

		this.webClient = webClientBuilder.baseUrl(owmConfiguration.getEndpointUrl()).build();
    }
    
    @Override
    public Flux<City> loadCitiesWithWeather(List<String> cityNames) {
        return Flux.fromIterable(cityNames)
            .parallel()
            .runOn(Schedulers.boundedElastic())
            .flatMap(this::loadOneCityWithWeather)  
            .ordered((city1, city2) -> city1.getName().compareTo(city2.getName()));
    }

    /**
     * Load one city with weather information
     * @param cityName Name of city
     * @return Mono object of city
     */
    private Mono<City> loadOneCityWithWeather(String cityName){
        return webClient
            .get()
            .uri("?q={cityName}&appid={apiKey}", cityName, owmConfiguration.getApiKey())
            .retrieve()
            .bodyToMono(ForecastResponse.class)
            .map(owmConverter::convertToCityEntity);
    }

}
