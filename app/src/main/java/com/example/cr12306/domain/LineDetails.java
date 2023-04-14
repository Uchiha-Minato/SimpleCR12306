package com.example.cr12306.domain;

public class LineDetails {
    private final String station_name;
    private final int distance;

    public LineDetails(String station_name, int distance) {
        this.station_name = station_name;
        this.distance = distance;
    }

    public String getStation_name() {
        return station_name;
    }

    public int getDistance() {
        return distance;
    }

}
