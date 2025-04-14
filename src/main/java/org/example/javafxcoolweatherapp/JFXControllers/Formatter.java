package org.example.javafxcoolweatherapp.JFXControllers;

import org.example.javafxcoolweatherapp.DataObjects.AbstractForecast;
import org.example.javafxcoolweatherapp.DataObjects.TimeStamp;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public static String formatTemp(double temp) {
        long roundAvg = Math.round(temp);

        return (roundAvg > 0 ? "+" : "") + roundAvg + "°";
    }

    public static String getDateOfThreeHour(final TimeStamp timeStamp) {
        var date = Instant.ofEpochSecond(
                timeStamp.getForecastTimeUnixUTC()).atZone(ZoneId.systemDefault());
        return date.format(DateTimeFormatter.ofPattern("dd.MM"));
    }

    public static String formatDateTime(long unixTime) {
        ZonedDateTime time = Instant.ofEpochSecond(unixTime).atZone(ZoneId.systemDefault());

        return time.format(DateTimeFormatter.ofPattern("dd.MM   HH:mm"));
    }

    public static String formatDateTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("dd.MM   HH:mm"));
    }

    public static String formatHour(int hour) {
        if (hour < 10) {
            return "0" + hour + ":00";
        } else {
            return hour + ":00";
        }
    }

    public static String formatCaption(String caption) {
        if (caption.isEmpty()) return caption;
        return Character.toUpperCase(caption.charAt(0)) +
                caption.toLowerCase().substring(1);
    }

    public static String formatPressure(int pressure) {
        return String.valueOf(pressure) + " HPa";
    }

    public static String formatHumidity(int humidity) {
        return String.valueOf(humidity) + "%";
    }

    public static String formatWindSpeed(double speed) {
        return String.valueOf(speed) + " m/s";
    }

    public static double getAvgTempForThreeHour(int day, final AbstractForecast forecast) {
        double tempSum = 0;
        for (int i = 0; i < 24; i += forecast.getStampsPerDay()) {
            tempSum += forecast.getTimeStamp(i, day).getTempCelsius();
        }

        return tempSum / (24 / forecast.getStampsPerDay());
    }
}
