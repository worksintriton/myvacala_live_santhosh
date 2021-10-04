package com.triton.myvacala.requestpojo;

public class ParkingBookingGetListRequest {

    /**
     * Customer_id : 5f1fae9bbcd5650a5ab130e8
     * date_time :date_time : 2020-10-10 12:00 PM
     */

    private String Customer_id;
    private String date_time;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }
}
