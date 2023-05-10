package com.example.cr12306.domain;

import java.io.Serializable;

public class LeftTicket implements Serializable {
    public String date;//乘车日期

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String train_no;//车次
    public String station_train_code;//当前车站车次
    public String start_station_telecode;//始发站代码
    public String start_station_name;//始发站名
    public String end_station_telecode;//终到站代码
    public String end_station_name;//终到站名
    public String from_station_telecode;//用户输入起始站代码
    public String from_station_name;
    public String to_station_telecode;//用户输入目的地代码
    public String to_station_name;
    public String start_time;//起始站发车时间
    public String arrive_time;//到达目的地时间
    public int day_difference;//行车天数变化
    public String train_class_name;//列车类型
    public String lishi;//历时
    public String start_train_date;//始发站发车日期
    public int controlled_train_flag;//是否受控车次标记
    public String controlled_train_message;//是否受控车次信息

    //由于个人开发者无法连接国铁余票数据库，官网余票数据接口返回的是是否有这一坐席
    //于是直接令以下字段为布尔：
    //false 无此坐席 true 有此坐席
    private boolean rw_num;//软卧
    private boolean srrb_num;//动卧
    private boolean gr_num;//高级软卧
    private boolean rz_num;//软座
    private boolean wz_num;//无座
    private boolean yw_num;//硬卧
    private boolean yz_num;//硬座
    private boolean ze_num;//二等座
    private boolean zy_num;//一等座
    private boolean swz_num;//商务座

    //票价有可能为0开头的数字
    private String wz_price;//无座票价
    private String yz_price;//硬座票价
    private String rz_price;//软座票价
    private String yw_price;//硬卧票价
    private String rw_price;//软卧票价
    private String gr_price;//高级软卧票价
    private String zy_price;//一等座票价
    private String ze_price;//二等座票价
    private String swz_price;//商务座票价
    private String srrb_price;//动卧票价

    public String getStart_station_name() {
        return start_station_name;
    }

    public void setStart_station_name(String start_station_name) {
        this.start_station_name = start_station_name;
    }

    public String getEnd_station_name() {
        return end_station_name;
    }

    public void setEnd_station_name(String end_station_name) {
        this.end_station_name = end_station_name;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getStation_train_code() {
        return station_train_code;
    }

    public void setStation_train_code(String station_train_code) {
        this.station_train_code = station_train_code;
    }

    public String getStart_station_telecode() {
        return start_station_telecode;
    }

    public void setStart_station_telecode(String start_station_telecode) {
        this.start_station_telecode = start_station_telecode;
    }

    public String getEnd_station_telecode() {
        return end_station_telecode;
    }

    public void setEnd_station_telecode(String end_station_telecode) {
        this.end_station_telecode = end_station_telecode;
    }

    public String getFrom_station_telecode() {
        return from_station_telecode;
    }

    public void setFrom_station_telecode(String from_station_telecode) {
        this.from_station_telecode = from_station_telecode;
    }

    public String getFrom_station_name() {
        return from_station_name;
    }

    public void setFrom_station_name(String from_station_name) {
        this.from_station_name = from_station_name;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public String getTo_station_telecode() {
        return to_station_telecode;
    }

    public void setTo_station_telecode(String to_station_telecode) {
        this.to_station_telecode = to_station_telecode;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public int getDay_difference() {
        return day_difference;
    }

    public void setDay_difference(int day_difference) {
        this.day_difference = day_difference;
    }

    public String getTrain_class_name() {
        return train_class_name;
    }

    public void setTrain_class_name(String train_class_name) {
        this.train_class_name = train_class_name;
    }

    public String getLishi() {
        return lishi;
    }

    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    public String getStart_train_date() {
        return start_train_date;
    }

    public void setStart_train_date(String start_train_date) {
        this.start_train_date = start_train_date;
    }

    public int getControlled_train_flag() {
        return controlled_train_flag;
    }

    public void setControlled_train_flag(int controlled_train_flag) {
        this.controlled_train_flag = controlled_train_flag;
    }

    public String getControlled_train_message() {
        return controlled_train_message;
    }

    public void setControlled_train_message(String controlled_train_message) {
        this.controlled_train_message = controlled_train_message;
    }

    public boolean isRw_num() {
        return rw_num;
    }

    public void setRw_num(boolean rw_num) {
        this.rw_num = rw_num;
    }

    public boolean isSrrb_num() {
        return srrb_num;
    }

    public void setSrrb_num(boolean srrb_num) {
        this.srrb_num = srrb_num;
    }

    public boolean isGr_num() {
        return gr_num;
    }

    public void setGr_num(boolean gr_num) {
        this.gr_num = gr_num;
    }

    public boolean isRz_num() {
        return rz_num;
    }

    public void setRz_num(boolean rz_num) {
        this.rz_num = rz_num;
    }

    public boolean isWz_num() {
        return wz_num;
    }

    public void setWz_num(boolean wz_num) {
        this.wz_num = wz_num;
    }

    public boolean isYw_num() {
        return yw_num;
    }

    public void setYw_num(boolean yw_num) {
        this.yw_num = yw_num;
    }

    public boolean isYz_num() {
        return yz_num;
    }

    public void setYz_num(boolean yz_num) {
        this.yz_num = yz_num;
    }

    public boolean isZe_num() {
        return ze_num;
    }

    public void setZe_num(boolean ze_num) {
        this.ze_num = ze_num;
    }

    public boolean isZy_num() {
        return zy_num;
    }

    public void setZy_num(boolean zy_num) {
        this.zy_num = zy_num;
    }

    public boolean isSwz_num() {
        return swz_num;
    }

    public void setSwz_num(boolean swz_num) {
        this.swz_num = swz_num;
    }

    public String getWz_price() {
        return wz_price;
    }

    public void setWz_price(String wz_price) {
        this.wz_price = wz_price;
    }

    public String getYz_price() {
        return yz_price;
    }

    public void setYz_price(String yz_price) {
        this.yz_price = yz_price;
    }

    public String getRz_price() {
        return rz_price;
    }

    public void setRz_price(String rz_price) {
        this.rz_price = rz_price;
    }

    public String getYw_price() {
        return yw_price;
    }

    public void setYw_price(String yw_price) {
        this.yw_price = yw_price;
    }

    public String getRw_price() {
        return rw_price;
    }

    public void setRw_price(String rw_price) {
        this.rw_price = rw_price;
    }

    public String getGr_price() {
        return gr_price;
    }

    public void setGr_price(String gr_price) {
        this.gr_price = gr_price;
    }

    public String getZy_price() {
        return zy_price;
    }

    public void setZy_price(String zy_price) {
        this.zy_price = zy_price;
    }

    public String getZe_price() {
        return ze_price;
    }

    public void setZe_price(String ze_price) {
        this.ze_price = ze_price;
    }

    public String getSwz_price() {
        return swz_price;
    }

    public void setSwz_price(String swz_price) {
        this.swz_price = swz_price;
    }

    public String getSrrb_price() {
        return srrb_price;
    }

    public void setSrrb_price(String srrb_price) {
        this.srrb_price = srrb_price;
    }
}
