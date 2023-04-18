package com.example.cr12306.domain;

public class DistanceDetail {
    private final String station;
    private final int distance;

    public DistanceDetail(String station, int distance){
        this.station = station;
        this.distance = distance;
    }
    public String getStation() {
        return station;
    }

    public int getDistance() {
        return distance;
    }

}
