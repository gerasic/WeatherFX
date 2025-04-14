package org.example.javafxcoolweatherapp.DataObjects;

import java.util.List;

public class ThreeHourForecast extends AbstractForecast {
    public ThreeHourForecast(List<TimeStamp> timeStamps) throws IllegalArgumentException {
        super(timeStamps, 24 / 3);
    }
}
