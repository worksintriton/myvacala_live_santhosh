package com.triton.myvacala.requestpojo;

import com.google.gson.annotations.SerializedName;

public class LocationEditRequest {

    /**
     * _id : 5f05d8fef3090625a91f40c6
     * City : Chennai
     * State : TamilNadu
     * Country : India
     * Pincode : 600100
     * Street : Koil street
     * lat : 15.2345
     * long : 70.546
     * NearBy_LandMark : Pooja stores
     * Location_NickName : MGM
     * Flat_No : 24-f
     * Location_type : Office
     */

    private String Location_id;
    private String City;
    private String State;
    private String Country;
    private String Pincode;
    private String Street;
    private double lat;
    @SerializedName("long")
    private double longX;
    private String NearBy_LandMark;
    private String Location_NickName;
    private String Flat_No;
    private String Location_type;

    public String getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(String location_id) {
        this.Location_id = location_id;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String Pincode) {
        this.Pincode = Pincode;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongX() {
        return longX;
    }

    public void setLongX(double longX) {
        this.longX = longX;
    }

    public String getNearBy_LandMark() {
        return NearBy_LandMark;
    }

    public void setNearBy_LandMark(String NearBy_LandMark) {
        this.NearBy_LandMark = NearBy_LandMark;
    }

    public String getLocation_NickName() {
        return Location_NickName;
    }

    public void setLocation_NickName(String Location_NickName) {
        this.Location_NickName = Location_NickName;
    }

    public String getFlat_No() {
        return Flat_No;
    }

    public void setFlat_No(String Flat_No) {
        this.Flat_No = Flat_No;
    }

    public String getLocation_type() {
        return Location_type;
    }

    public void setLocation_type(String Location_type) {
        this.Location_type = Location_type;
    }
}
