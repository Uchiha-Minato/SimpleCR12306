package com.example.cr12306.domain;

public class Interchange {
    public String all_lishi;//总历时
    public String arrive_date;//到达日期
    public String arrive_time;//到达时间
    public String to_station_code;//目的站车站代码
    public String to_station_name;//目的站车站名
    public String from_station_code;//起始站车站代码
    public String from_station_name;//起始站车站名

    //第一趟车
    public String first_start_station_name;//始发站
    public String first_start_time;
    public String first_start_train_date;
    public String first_station_train_code;
    public String first_to_station_name;//目的站
    public String first_lishi;
    public String first_from_station_name;//起始站
    public String first_end_station_name;//终到站
    public String first_arrive_time;
    //第二趟车
    public String second_start_station_name;//始发站
    public String second_start_time;
    public String second_start_train_date;
    public String second_station_train_code;
    public String second_to_station_name;//目的站
    public String second_lishi;
    public String second_from_station_name;//起始站
    public String second_end_station_name;//终到站
    public String second_arrive_time;

    //中转车站信息
    public String middle_date;
    public String middle_station;
    public String wait_time;

    public String getAll_lishi() {
        return all_lishi;
    }

    public void setAll_lishi(String all_lishi) {
        this.all_lishi = all_lishi;
    }

    public String getArrive_date() {
        return arrive_date;
    }

    public void setArrive_date(String arrive_date) {
        this.arrive_date = arrive_date;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getTo_station_code() {
        return to_station_code;
    }

    public void setTo_station_code(String to_station_code) {
        this.to_station_code = to_station_code;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public String getFrom_station_code() {
        return from_station_code;
    }

    public void setFrom_station_code(String from_station_code) {
        this.from_station_code = from_station_code;
    }

    public String getFrom_station_name() {
        return from_station_name;
    }

    public void setFrom_station_name(String from_station_name) {
        this.from_station_name = from_station_name;
    }

    public String getFirst_start_station_name() {
        return first_start_station_name;
    }

    public void setFirst_start_station_name(String first_start_station_name) {
        this.first_start_station_name = first_start_station_name;
    }

    public String getFirst_start_time() {
        return first_start_time;
    }

    public void setFirst_start_time(String first_start_time) {
        this.first_start_time = first_start_time;
    }

    public String getFirst_start_train_date() {
        return first_start_train_date;
    }

    public void setFirst_start_train_date(String first_start_train_date) {
        this.first_start_train_date = first_start_train_date;
    }

    public String getFirst_station_train_code() {
        return first_station_train_code;
    }

    public void setFirst_station_train_code(String first_station_train_code) {
        this.first_station_train_code = first_station_train_code;
    }

    public String getFirst_to_station_name() {
        return first_to_station_name;
    }

    public void setFirst_to_station_name(String first_to_station_name) {
        this.first_to_station_name = first_to_station_name;
    }

    public String getFirst_lishi() {
        return first_lishi;
    }

    public void setFirst_lishi(String first_lishi) {
        this.first_lishi = first_lishi;
    }

    public String getSecond_start_station_name() {
        return second_start_station_name;
    }

    public void setSecond_start_station_name(String second_start_station_name) {
        this.second_start_station_name = second_start_station_name;
    }

    public String getSecond_start_time() {
        return second_start_time;
    }

    public void setSecond_start_time(String second_start_time) {
        this.second_start_time = second_start_time;
    }

    public String getSecond_start_train_date() {
        return second_start_train_date;
    }

    public void setSecond_start_train_date(String second_start_train_date) {
        this.second_start_train_date = second_start_train_date;
    }

    public String getSecond_station_train_code() {
        return second_station_train_code;
    }

    public void setSecond_station_train_code(String second_station_train_code) {
        this.second_station_train_code = second_station_train_code;
    }

    public String getSecond_to_station_name() {
        return second_to_station_name;
    }

    public void setSecond_to_station_name(String second_to_station_name) {
        this.second_to_station_name = second_to_station_name;
    }

    public String getSecond_lishi() {
        return second_lishi;
    }

    public void setSecond_lishi(String second_lishi) {
        this.second_lishi = second_lishi;
    }

    public String getMiddle_date() {
        return middle_date;
    }

    public void setMiddle_date(String middle_date) {
        this.middle_date = middle_date;
    }

    public String getMiddle_station() {
        return middle_station;
    }

    public void setMiddle_station(String middle_station) {
        this.middle_station = middle_station;
    }

    public String getWait_time() {
        return wait_time;
    }

    public void setWait_time(String wait_time) {
        this.wait_time = wait_time;
    }

    public String getFirst_from_station_name() {
        return first_from_station_name;
    }

    public void setFirst_from_station_name(String first_from_station_name) {
        this.first_from_station_name = first_from_station_name;
    }

    public String getFirst_end_station_name() {
        return first_end_station_name;
    }

    public void setFirst_end_station_name(String first_end_station_name) {
        this.first_end_station_name = first_end_station_name;
    }

    public String getSecond_from_station_name() {
        return second_from_station_name;
    }

    public void setSecond_from_station_name(String second_from_station_name) {
        this.second_from_station_name = second_from_station_name;
    }

    public String getSecond_end_station_name() {
        return second_end_station_name;
    }

    public void setSecond_end_station_name(String second_end_station_name) {
        this.second_end_station_name = second_end_station_name;
    }

    public String getFirst_arrive_time() {
        return first_arrive_time;
    }

    public void setFirst_arrive_time(String first_arrive_time) {
        this.first_arrive_time = first_arrive_time;
    }

    public String getSecond_arrive_time() {
        return second_arrive_time;
    }

    public void setSecond_arrive_time(String second_arrive_time) {
        this.second_arrive_time = second_arrive_time;
    }
}
