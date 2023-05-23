package com.example.cr12306.domain;

public class CorridorDetail {
    private final String corridor_name;
    private final String line_name;
    public String from_to;

    public CorridorDetail(String corridor_name, String line_name) {
        this.corridor_name = corridor_name;
        this.line_name = line_name;
    }

    public String getCorridor_name() {
        return corridor_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public String getFrom_to() {
        return from_to;
    }

    public void setFrom_to(String from_to) {
        this.from_to = from_to;
    }
}
