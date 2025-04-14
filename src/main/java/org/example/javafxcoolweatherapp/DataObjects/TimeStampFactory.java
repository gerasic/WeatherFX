package org.example.javafxcoolweatherapp.DataObjects;

public final class TimeStampFactory {
    private long forecastTimeUnixUTC;
    private double tempCelsius;
    private double feelsLikeCelsius;
    private int pressureHPa;
    private int humidityPercents;
    private String weatherDescription;
    private double windSpeedMetersSec;

    public TimeStamp create() {
        return new TimeStamp(
                forecastTimeUnixUTC,
                tempCelsius,
                feelsLikeCelsius,
                pressureHPa,
                humidityPercents,
                weatherDescription,
                windSpeedMetersSec
        );
    }

    public TimeStampFactory setForecastTimeUnixUTC(long forecastTimeUnixUTC) {
        this.forecastTimeUnixUTC = forecastTimeUnixUTC;
        return this;
    }

    public TimeStampFactory setFeelsLikeCelsius(double feelsLikeCelsius) {
        this.feelsLikeCelsius = feelsLikeCelsius;
        return this;
    }

    public TimeStampFactory setPressureHPa(int pressureHPa) {
        this.pressureHPa = pressureHPa;
        return this;
    }

    public TimeStampFactory setHumidityPercents(int humidityPercents) {
        this.humidityPercents = humidityPercents;
        return this;
    }

    public TimeStampFactory setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
        return this;
    }

    public TimeStampFactory setWindSpeedMetersSec(double windSpeedMetersSec) {
        this.windSpeedMetersSec = windSpeedMetersSec;
        return this;
    }

    public TimeStampFactory setTempCelsius(double tempCelsius) {
        this.tempCelsius = tempCelsius;
        return this;
    }
}
