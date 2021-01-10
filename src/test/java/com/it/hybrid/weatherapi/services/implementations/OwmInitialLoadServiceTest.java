package com.it.hybrid.weatherapi.services.implementations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.hybrid.weatherapi.configurations.properties.OwmConfiguration;
import com.it.hybrid.weatherapi.converters.OwmConverter;
import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.owm.ForecastResponse;
import com.it.hybrid.weatherapi.util.OwmDataUtil;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@TestInstance(Lifecycle.PER_METHOD)
public class OwmInitialLoadServiceTest {

    private static MockWebServer mockBackEnd;

    private ObjectMapper objectMapper = new ObjectMapper();

    public OwmInitialLoadService loadService;

    private int initialRequestCount = 0;

    @BeforeAll
    static void setup() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void destroy() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void init() throws IOException {

        String url = String.format("http://localhost:%s", mockBackEnd.getPort());
        loadService = new OwmInitialLoadService(new OwmConfiguration("apiKey", url), new OwmConverter(),
                WebClient.builder());

        initialRequestCount = mockBackEnd.getRequestCount();
    }

    @Test
    public void loadCitiesWithWeatherFromApi() throws JsonProcessingException, InterruptedException {

        ForecastResponse forecast = OwmDataUtil.createForecastResponse();

        mockBackEnd.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(forecast))
            .addHeader("Content-Type", "application/json"));
            

        List<String> cityNames = Arrays.asList(forecast.getCity().getName());
        Flux<City> fluxCity = loadService.loadCitiesWithWeather(cityNames);
        StepVerifier.create(fluxCity)
            .expectNextMatches(
                city -> city.getName().equals(forecast.getCity().getName())
            )
        .verifyComplete();
        
        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(cityNames.size(), getRequestCount());

    }

    @Test
    public void loadCitiesWithWeatherFromApiNotFound() throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.NOT_FOUND.value())
        );

        mockBackEnd.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.NOT_FOUND.value())
        );
            
        List<String> cityNames = Arrays.asList("Novi Sad", "Beograd");
        Flux<City> fluxCity = loadService.loadCitiesWithWeather(cityNames);

        StepVerifier.create(fluxCity)
            .verifyError();
        
        assertEquals(cityNames.size(), getRequestCount());
    }

    @Test
    public void loadCitiesWithWeatherFromApiInternalServerError() throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        );
            
        
        List<String> cityNames = Arrays.asList("Beograd");
        Flux<City> fluxCity = loadService.loadCitiesWithWeather(cityNames);

        StepVerifier.create(fluxCity)
            .verifyError();

        assertEquals(cityNames.size(), getRequestCount());
    }

    @Test
    public void loadCitiesWithWeatherFromApiBadRequest() throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse()
            .setResponseCode(HttpStatus.BAD_REQUEST.value())
        );
            
        List<String> cityNames = Arrays.asList("Novi Sad");
        Flux<City> fluxCity = loadService.loadCitiesWithWeather(cityNames);

        StepVerifier.create(fluxCity)
            .verifyError();
            
        assertEquals(cityNames.size(), getRequestCount());
    }

    private int getRequestCount() {
        return mockBackEnd.getRequestCount() - initialRequestCount;
    }
}
