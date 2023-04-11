package com.example.cr12306.domain;


public class TicketTest {
    private String station_train_code;
    private String from_to;

    private String from_station_name;
    private String start_time;
    private String to_station_name;
    private String arrive_time;

    private String lishi;

    public String getStation_train_code() {
        return station_train_code;
    }

    public void setStation_train_code(String station_train_code) {
        this.station_train_code = station_train_code;
    }

    public String getFrom_to() {
        return from_to;
    }

    public void setFrom_to(String from_to) {
        this.from_to = from_to;
    }

    public String getFrom_station_name() {
        return from_station_name;
    }

    public void setFrom_station_name(String from_station_name) {
        this.from_station_name = from_station_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getLishi() {
        return lishi;
    }

    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    public String getTicket_msg() {
        return ticket_msg;
    }

    public void setTicket_msg(String ticket_msg) {
        this.ticket_msg = ticket_msg;
    }

    private String ticket_msg;
}
