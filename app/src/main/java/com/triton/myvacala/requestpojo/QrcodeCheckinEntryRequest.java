package com.triton.myvacala.requestpojo;

public class QrcodeCheckinEntryRequest {

    /**
     * Booking_id : Bk-ACDBUv
     * parkingdetails_id : 5f4c902d9d6fd85aedb4d79d
     * Cur_date : 2020-08-10
     * Cur_time : 12:05 PM
     * Cur_date_time : 2020-08-10T12:00:00
     * Customer_id : 5f1fc277cfb5c741551e1793
     */

    private String Booking_id;
    private String parkingdetails_id;
    private String Cur_date;
    private String Cur_time;
    private String Cur_date_time;
    private String Customer_id;

    public String getBooking_id() {
        return Booking_id;
    }

    public void setBooking_id(String Booking_id) {
        this.Booking_id = Booking_id;
    }

    public String getParkingdetails_id() {
        return parkingdetails_id;
    }

    public void setParkingdetails_id(String parkingdetails_id) {
        this.parkingdetails_id = parkingdetails_id;
    }

    public String getCur_date() {
        return Cur_date;
    }

    public void setCur_date(String Cur_date) {
        this.Cur_date = Cur_date;
    }

    public String getCur_time() {
        return Cur_time;
    }

    public void setCur_time(String Cur_time) {
        this.Cur_time = Cur_time;
    }

    public String getCur_date_time() {
        return Cur_date_time;
    }

    public void setCur_date_time(String Cur_date_time) {
        this.Cur_date_time = Cur_date_time;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }
}
