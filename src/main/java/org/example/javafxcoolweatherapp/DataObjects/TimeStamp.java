package org.example.javafxcoolweatherapp.DataObjects;

public class TimeStamp {
    private final long forecastTimeUnixUTC;
    private final double tempCelsius;
    private final double feelsLikeCelsius;
    private final int pressureHPa;
    private final int humidityPercents;
    private final String weatherDescription;
    private final double windSpeedMetersSec;

    public TimeStamp(long forecastTimeUnixUTC,
                     double tempCelsius,
                     double feelsLikeCelsius,
                     int pressureHPa,
                     int humidityPercents,
                     String weatherDescription,
                     double windSpeedMetersSec)
    {
        this.forecastTimeUnixUTC = forecastTimeUnixUTC;
        this.tempCelsius = tempCelsius;
        this.feelsLikeCelsius = feelsLikeCelsius;
        this.pressureHPa = pressureHPa;
        this.humidityPercents = humidityPercents;
        this.weatherDescription = weatherDescription;
        this.windSpeedMetersSec = windSpeedMetersSec;
    }

    public long getForecastTimeUnixUTC() {
        return forecastTimeUnixUTC;
    }

    public double getTempCelsius() {
        return tempCelsius;
    }

    public double getFeelsLikeCelsius() {
        return feelsLikeCelsius;
    }

    public int getPressureHPa() {
        return pressureHPa;
    }

    public int getHumidityPercents() {
        return humidityPercents;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public double getWindSpeedMetersSec() {
        return windSpeedMetersSec;
    }

    @Override
    public String toString() {
        String divider = "; ";
        return "{" +
                forecastTimeUnixUTC + divider +
                tempCelsius + divider +
                feelsLikeCelsius + divider +
                pressureHPa + divider +
                humidityPercents + divider +
                weatherDescription + divider +
                windSpeedMetersSec + "}";
    }
}
