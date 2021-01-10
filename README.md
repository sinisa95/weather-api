# Weather-API

To start application you need to provide api key in application.properties, so app is able to use OpenWeatherMap.
```
owm.apiKey = your-api-key
```

Cities, for which weather is fetched, are also defined in application.properties.
```
weather.cities[0]=London
weather.cities[1]=Berlin
weather.cities[2]=Washington
```

H2 in-memory database is used for persitance of data.
