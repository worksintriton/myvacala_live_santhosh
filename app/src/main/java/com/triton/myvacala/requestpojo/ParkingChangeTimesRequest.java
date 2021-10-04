package com.triton.myvacala.requestpojo;

public class ParkingChangeTimesRequest {

    /**
     * start_date : 23-10-2020
     * end_date : 24-10-2020
     * start_time : 11:05 AM
     * end_time : 12:05 PM
     * parking_vendor_id : 5f4c902d9d6fd85aedb4d79d
     * vehicle_type_id : 5f4c902d9d6fd85aedb4d79d
     */

    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String parking_vendor_id;
    private String vehicle_type_id;

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
}
