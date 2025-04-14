package org.example.javafxcoolweatherapp.DataObjects;

import java.util.List;

public abstract class AbstractForecast {
    protected final List<TimeStamp> timeStamps;
    protected final int stampsPerDay;

    public AbstractForecast(List<TimeStamp> timeStamps, int stampsPerDay)
            throws IllegalArgumentException {

        if (stampsPerDay > 24 || stampsPerDay < 0 || 24 % stampsPerDay != 0) {
            throw new IllegalArgumentException("Breach of contract: 0 < stampsPerDay < 24,  24 % stampsPerDay == 0.");

        } else if (timeStamps == null) {
            throw new IllegalArgumentException("List of stamps can not equals null.");

        } else if (timeStamps.isEmpty()) {
            throw new IllegalArgumentException("Amount of stamps must be more than 1.");
        }

        this.stampsPerDay = stampsPerDay;
        this.timeStamps = timeStamps;
    }

    public TimeStamp getTimeStamp(int hour, int day) throws IndexOutOfBoundsException {
        return timeStamps.get((day * stampsPerDay) + (hour / (24 / stampsPerDay)));
    }

    public TimeStamp getTimeStamp(int hour) {
        return getTimeStamp(hour, 0);
    }

    public long getFirstHourUnixUTC(){
        return timeStamps.get(0).getForecastTimeUnixUTC();
    }

    public int getAmountOfTimeStamps() {
        return timeStamps.size();
    }

    public int getAmountOfDays() {
        return getAmountOfTimeStamps() * stampsPerDay / 24;
    }

    public int getStampsPerDay() {
        return stampsPerDay;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n");

        for (int i = 0; i < getAmountOfTimeStamps(); i++) {
            if (i % stampsPerDay == 0) {
                sb.append("Day ").append((i / stampsPerDay) + 1).append("\n");
            }
            sb.append(timeStamps.get(i)).append("\n");
        }

        return sb.append("}").toString();
    }
}
