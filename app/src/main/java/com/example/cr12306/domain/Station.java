package com.example.cr12306.domain;

import java.io.Serializable;

public class Station implements Serializable {
    private String station_name;
    private String telecode;

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getTelecode() {
        return telecode;
    }

    public void setTelecode(String telecode) {
        this.telecode = telecode;
    }
}
