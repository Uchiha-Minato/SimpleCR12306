package com.example.cr12306.domain;

public class TrainInfo {
    public String train_class_name;//车型

    public String getTrain_class_name() {
        return train_class_name;
    }

    public void setTrain_class_name(String train_class_name) {
        this.train_class_name = train_class_name;
    }

    public String arrive_day_str;//当日到达
    public String station_train_code;//当前车站车次
    public String arrive_time;//到达时间
    public String station_name;//车站名
    public String running_time;//历时
    public String start_time;//开车时间

    public String getArrive_day_str() {
        return arrive_day_str;
    }

    public void setArrive_day_str(String arrive_day_str) {
        this.arrive_day_str = arrive_day_str;
    }

    public String getStation_train_code() {
        return station_train_code;
    }

    public void setStation_train_code(String station_train_code) {
        this.station_train_code = station_train_code;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getRunning_time() {
        return running_time;
    }

    public void setRunning_time(String running_time) {
        this.running_time = running_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
