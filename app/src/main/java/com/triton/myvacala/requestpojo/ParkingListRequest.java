package com.triton.myvacala.requestpojo;

public class ParkingListRequest {


    /**
     * Location_lat : 13.133130
     * Location_long : 80.245290
     * Vehicletype_id : 5f0c0d092f857d66950cf260
     * start_dates : 2020-08-16
     * end_dates : 2020-08-16
     * start_itme : 09
     * end_time : 10
     */

    private String Location_lat;
    private String Location_long;
    private String Vehicletype_id;
    private String start_dates;
    private String end_dates;
    private String start_itme;
    private String end_time;

    public String getLocation_lat() {
        return Location_lat;
    }

    public void setLocation_lat(String Location_lat) {
        this.Location_lat = Location_lat;
    }

    public String getLocation_long() {
        return Location_long;
    }

    public void setLocation_long(String Location_long) {
        this.Location_long = Location_long;
    }

    public String getVehicletype_id() {
        return Vehicletype_id;
    }

    public void setVehicletype_id(String Vehicletype_id) {
        this.Vehicletype_id = Vehicletype_id;
    }

    public String getStart_dates() {
        return start_dates;
    }

    public void setStart_dates(String start_dates) {
        this.start_dates = start_dates;
    }

    public String getEnd_dates() {
        return end_dates;
    }

    public void setEnd_dates(String end_dates) {
        this.end_dates = end_dates;
    }

    public String getStart_itme() {
        return start_itme;
    }

    public void setStart_itme(String start_itme) {
        this.start_itme = start_itme;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
