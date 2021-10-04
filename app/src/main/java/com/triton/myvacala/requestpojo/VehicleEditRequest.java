package com.triton.myvacala.requestpojo;

public class VehicleEditRequest {


    /**
     * Vehicle_No : TN 48 CF 5454
     * Vehicle_id : 5f119d6a3fe80176a3fb3f0d
     * Fuel_Type_id : 5f0c136a31e1f8795ba172f9
     * Fuel_Type_Name : Natural Gas
     * Vehicle_Model_id : 5f0c30f87f655108e6ca2ea8
     * Vehicle_Model_Name : Coupe
     * Fuel_Type_Background_Color
     */

    private String Vehicle_No;
    private String Vehicle_id;
    private String Fuel_Type_id;
    private String Fuel_Type_Name;
    private String Vehicle_Model_id;
    private String Vehicle_Model_Name;
    private String Fuel_Type_Background_Color;

    public String getFuel_Type_Background_Color() {
        return Fuel_Type_Background_Color;
    }

    public void setFuel_Type_Background_Color(String fuel_Type_Background_Color) {
        Fuel_Type_Background_Color = fuel_Type_Background_Color;
    }

    public String getVehicle_No() {
        return Vehicle_No;
    }

    public void setVehicle_No(String Vehicle_No) {
        this.Vehicle_No = Vehicle_No;
    }

    public String getVehicle_id() {
        return Vehicle_id;
    }

    public void setVehicle_id(String Vehicle_id) {
        this.Vehicle_id = Vehicle_id;
    }

    public String getFuel_Type_id() {
        return Fuel_Type_id;
    }

    public void setFuel_Type_id(String Fuel_Type_id) {
        this.Fuel_Type_id = Fuel_Type_id;
    }

    public String getFuel_Type_Name() {
        return Fuel_Type_Name;
    }

    public void setFuel_Type_Name(String Fuel_Type_Name) {
        this.Fuel_Type_Name = Fuel_Type_Name;
    }

    public String getVehicle_Model_id() {
        return Vehicle_Model_id;
    }

    public void setVehicle_Model_id(String Vehicle_Model_id) {
        this.Vehicle_Model_id = Vehicle_Model_id;
    }

    public String getVehicle_Model_Name() {
        return Vehicle_Model_Name;
    }

    public void setVehicle_Model_Name(String Vehicle_Model_Name) {
        this.Vehicle_Model_Name = Vehicle_Model_Name;
    }
}
