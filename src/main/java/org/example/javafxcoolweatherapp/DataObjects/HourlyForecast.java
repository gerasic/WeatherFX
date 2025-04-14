package org.example.javafxcoolweatherapp.DataObjects;

import java.util.List;

public class HourlyForecast extends AbstractForecast {
    public HourlyForecast(List<TimeStamp> timeStamps) throws IllegalArgumentException {
        super(timeStamps, 1);
    }
}
