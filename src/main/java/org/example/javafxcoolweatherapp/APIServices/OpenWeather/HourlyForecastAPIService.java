package org.example.javafxcoolweatherapp.APIServices.OpenWeather;

import org.example.javafxcoolweatherapp.APIServices.AbstractCacheableSimpleAPIService;
import org.example.javafxcoolweatherapp.APIServices.Exceptions.ParseException;
import org.example.javafxcoolweatherapp.APIServices.Exceptions.URLException;
import org.example.javafxcoolweatherapp.DataObjects.GeoData;
import org.example.javafxcoolweatherapp.DataObjects.HourlyForecast;
import org.example.javafxcoolweatherapp.Parsers.DataObjectsJSONParser;

import java.io.IOException;

public class HourlyForecastAPIService
        extends AbstractCacheableSimpleAPIService< HourlyForecast > {

    private final GeoAPIService geoAPIService;

    public HourlyForecastAPIService(String APIKey, GeoAPIService geoAPIService) {
        super(APIKey, "open-weather-hourly-cache");
        this.geoAPIService = geoAPIService;
    }

    @Override
    protected String getResponseByURLImpl(String parameter) throws IOException {
        GeoData geoData = geoAPIService.getData(parameter);
        return urlManager.getData(String.format(
                "https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=%f&lon=%f&appid=%s",
                geoData.getLat(), geoData.getLon(), APIKey
        ));
    }

    @Override
    protected HourlyForecast parseJSONResponseImpl(String data) throws IOException {
        return new HourlyForecast(DataObjectsJSONParser.parseTimeStampsList(data));
    }
}
