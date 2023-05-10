package com.example.cr12306.domain;

import java.io.Serializable;

public class BuyTicket extends LeftTicket implements Serializable {
    private String seat_type;
    private String price;

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
