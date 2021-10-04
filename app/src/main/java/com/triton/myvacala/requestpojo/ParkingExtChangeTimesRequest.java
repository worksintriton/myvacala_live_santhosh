package com.triton.myvacala.requestpojo;

public class ParkingExtChangeTimesRequest {

    /**
     * start_date : 2020-09-08
     * end_date : 2020-09-08
     * start_time : 07:05 PM
     * end_time : 08:05 PM
     * parking_vendor_id : 5f4793beb12e0b5507b79f74
     * vehicle_type_id : 5f0c0d092f857d66950cf260
     * booking_id : 5f578f5e2ccd2a5711ced06c
     */

    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String parking_vendor_id;
    private String vehicle_type_id;
    private String booking_id;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getParking_vendor_id() {
        return parking_vendor_id;
    }

    public void setParking_vendor_id(String parking_vendor_id) {
        this.parking_vendor_id = parking_vendor_id;
    }

    public String getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(String vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }
}
