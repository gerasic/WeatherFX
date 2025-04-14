package org.example.javafxcoolweatherapp.DataObjects;

public class GeoData {
    private final double lat;
    private final double lon;

    public GeoData(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", lat, lon);
    }
}
