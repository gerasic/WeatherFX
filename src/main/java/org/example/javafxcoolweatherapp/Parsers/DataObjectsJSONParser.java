package org.example.javafxcoolweatherapp.Parsers;

import org.example.javafxcoolweatherapp.DataObjects.GeoData;
import org.example.javafxcoolweatherapp.DataObjects.TimeStamp;
import org.example.javafxcoolweatherapp.DataObjects.TimeStampFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataObjectsJSONParser {
    private static final JSONParser parser = new JSONParser();

    private static <R> R handleParseExceptions(String data, Parseable<R> parseable) throws IOException {
        if (data == null) {
            throw new IOException("Invalid data for parsing.");
        }
        try {
            return parseable.parse(data);
        } catch (Exception e) {
            throw new IOException("Invalid data for parsing.", e);
        }
    }

    private static double longToDoubleIfNeeded(Object number) {
        if (number instanceof Double) {
            return (double) number;
        }
        if (number instanceof Long) {
            return ((Long) number).doubleValue();
        } else {
            return 0.0;
        }
    }

    public static GeoData parseGeoData(String data) throws IOException {
        return handleParseExceptions(data, data1 -> {
            JSONArray citiesArray = (JSONArray) parser.parse(data1);
            JSONObject firstCity = (JSONObject) citiesArray.get(0);

            return new GeoData((double) firstCity.get("lat"), (double) firstCity.get("lon"));
        });
    }

    public static List<TimeStamp> parseTimeStampsList(String data) throws IOException {
        return handleParseExceptions(data, data1 -> {
            JSONObject fullResponse = (JSONObject) parser.parse(data1);
            JSONArray timeStampsArray = (JSONArray) fullResponse.get("list");

            List<TimeStamp> tsList = new ArrayList<>();
            TimeStampFactory timeStampFactory = new TimeStampFactory();

            for (Object item : timeStampsArray) {
                JSONObject timeStamp = (JSONObject) item;
                JSONObject main = (JSONObject) timeStamp.get("main");
                JSONObject weather = (JSONObject)((JSONArray) timeStamp.get("weather")).get(0);
                JSONObject wind = (JSONObject) timeStamp.get("wind");



                tsList.add(timeStampFactory
                        .setForecastTimeUnixUTC((long) timeStamp.get("dt"))
                        .setTempCelsius(longToDoubleIfNeeded(main.get("temp")))
                        .setFeelsLikeCelsius(longToDoubleIfNeeded(main.get("feels_like")))
                        .setPressureHPa((int) (long) main.get("pressure"))
                        .setHumidityPercents((int) (long) main.get("humidity"))
                        .setWeatherDescription((String) weather.get("description"))
                        .setWindSpeedMetersSec(longToDoubleIfNeeded(wind.get("speed")))
                        .create()
                );
            }

            return tsList;
        });
    }

    public static List<TimeStamp> parseCurrentWeather(String data) throws IOException {
        return handleParseExceptions(data, data1 -> {
            JSONObject timeStamp = (JSONObject) parser.parse(data1);

            List<TimeStamp> tsList = new ArrayList<>();
            TimeStampFactory timeStampFactory = new TimeStampFactory();

            JSONObject main = (JSONObject) timeStamp.get("main");
            JSONObject weather = (JSONObject)((JSONArray) timeStamp.get("weather")).get(0);
            JSONObject wind = (JSONObject) timeStamp.get("wind");

            tsList.add(timeStampFactory
                    .setForecastTimeUnixUTC((long) timeStamp.get("dt"))
                    .setFeelsLikeCelsius((double) main.get("feels_like"))
                    .setPressureHPa((int) (long) main.get("pressure"))
                    .setHumidityPercents((int) (long) main.get("humidity"))
                    .setWeatherDescription((String) weather.get("description"))
                    .setFeelsLikeCelsius((double) wind.get("speed"))
                    .create()
            );

            return tsList;
        });
    }
}
