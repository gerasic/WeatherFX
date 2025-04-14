package org.example.javafxcoolweatherapp.APIServices.OpenWeather;

import org.example.javafxcoolweatherapp.APIServices.AbstractCacheableSimpleAPIService;
import org.example.javafxcoolweatherapp.DataObjects.CurrentForecast;
import org.example.javafxcoolweatherapp.DataObjects.GeoData;
import org.example.javafxcoolweatherapp.Parsers.DataObjectsJSONParser;

import java.io.IOException;

public class CurrentForecastAPIService extends AbstractCacheableSimpleAPIService<CurrentForecast> {
    private final GeoAPIService geoAPIService;

    public CurrentForecastAPIService(String APIKey, final GeoAPIService geoAPIService) {
        super(APIKey, "open-weather-current-cache");
        this.geoAPIService = geoAPIService;
    }

    @Override
    protected String getResponseByURLImpl(String parameter) throws IOException {
        GeoData geoData = geoAPIService.getData(parameter);
        return urlManager.getData(String.format(
                "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                geoData.getLat(), geoData.getLon(), APIKey
        ));
    }

    @Override
    protected CurrentForecast parseJSONResponseImpl(String data) throws IOException {
        return new CurrentForecast(DataObjectsJSONParser.parseCurrentWeather(data));
    }
}
