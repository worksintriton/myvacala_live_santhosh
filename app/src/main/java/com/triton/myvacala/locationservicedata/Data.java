package com.triton.myvacala.locationservicedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Employee_id")
    @Expose
    private String employeeId;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("Name")
    @Expose
    private String name;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
