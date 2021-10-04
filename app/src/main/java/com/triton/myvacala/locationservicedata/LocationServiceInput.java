package com.triton.myvacala.locationservicedata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationServiceInput {

    @SerializedName("Employee_id")
    @Expose
    private String employeeId;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Long")
    @Expose
    private Double _long;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
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
