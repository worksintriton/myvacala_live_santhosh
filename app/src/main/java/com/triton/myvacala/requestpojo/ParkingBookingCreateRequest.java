package com.triton.myvacala.requestpojo;

public class ParkingBookingCreateRequest {

    /**
     * slot_details :
     * parkingdetails_id : 5f4798fc99c5a35d06ad04f2
     * Vehicle_type_id : 5f0c0d092f857d66950cf260
     * Vehicle_id : 5f1e70539f12a81b3dd708a1
     * booking_start_date : 2020-08-10
     * booking_end_date : 2020-08-10
     * booking_start_time : 12:00
     * booking_end_time : 13:00
     * total_amount : 100
     * total_hrs : 1
     * Booked_Date_and_Time : 2020-08-10T01:20:09
     * Customer_id : 5f1fc277cfb5c741551e1793
     * distance
     * Kms
     * duration_date
     * couponcode
     * couponcode_amount
     * Original_amount
     */


    private String slot_details;
    private String parkingdetails_id;
    private String Vehicle_type_id;
    private String Vehicle_id;
    private String booking_start_date;
    private String booking_end_date;
    private String booking_start_time;
    private String booking_end_time;
    private String total_amount;
    private String total_hrs;
    private String Booked_Date_and_Time;
    private String Customer_id;
    private String distance;
    private String Kms;
    private String duration_date;
    private String couponcode;
    private String couponcode_amount;
    private String Original_amount;

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public String getCouponcode_amount() {
        return couponcode_amount;
    }

    public void setCouponcode_amount(String couponcode_amount) {
        this.couponcode_amount = couponcode_amount;
    }

    public String getOriginal_amount() {
        return Original_amount;
    }

    public void setOriginal_amount(String original_amount) {
        Original_amount = original_amount;
    }

    public String getDuration_date() {
        return duration_date;
    }

    public void setDuration_date(String duration_date) {
        this.duration_date = duration_date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getKms() {
        return Kms;
    }

    public void setKms(String kms) {
        Kms = kms;
    }

    public String getSlot_details() {
        return slot_details;
    }

    public void setSlot_details(String slot_details) {
        this.slot_details = slot_details;
    }

    public String getParkingdetails_id() {
        return parkingdetails_id;
    }

    public void setParkingdetails_id(String parkingdetails_id) {
        this.parkingdetails_id = parkingdetails_id;
    }

    public String getVehicle_type_id() {
        return Vehicle_type_id;
    }

    public void setVehicle_type_id(String Vehicle_type_id) {
        this.Vehicle_type_id = Vehicle_type_id;
    }

    public String getVehicle_id() {
        return Vehicle_id;
    }

    public void setVehicle_id(String Vehicle_id) {
        this.Vehicle_id = Vehicle_id;
    }

    public String getBooking_start_date() {
        return booking_start_date;
    }

    public void setBooking_start_date(String booking_start_date) {
        this.booking_start_date = booking_start_date;
    }

    public String getBooking_end_date() {
        return booking_end_date;
    }

    public void setBooking_end_date(String booking_end_date) {
        this.booking_end_date = booking_end_date;
    }

    public String getBooking_start_time() {
        return booking_start_time;
    }

    public void setBooking_start_time(String booking_start_time) {
        this.booking_start_time = booking_start_time;
    }

    public String getBooking_end_time() {
        return booking_end_time;
    }

    public void setBooking_end_time(String booking_end_time) {
        this.booking_end_time = booking_end_time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_hrs() {
        return total_hrs;
    }

    public void setTotal_hrs(String total_hrs) {
        this.total_hrs = total_hrs;
    }

    public String getBooked_Date_and_Time() {
        return Booked_Date_and_Time;
    }

    public void setBooked_Date_and_Time(String Booked_Date_and_Time) {
        this.Booked_Date_and_Time = Booked_Date_and_Time;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }
}
