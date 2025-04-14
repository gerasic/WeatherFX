package org.example.javafxcoolweatherapp.DataObjects;

import java.util.List;

public class CurrentForecast extends AbstractForecast {
    public CurrentForecast(List<TimeStamp> timeStamps) throws IllegalArgumentException {
        super(timeStamps, 1);
    }
}
