package com.triton.myvacala.requestpojo;

public class PopularVehicleListRequest {

    /**
     * Vehicle_Type_id : 5f0c0d092f857d66950cf260
     * search_string : bajaj
     */

    private String Vehicle_Type_id;
    private String search_string;

    public String getVehicle_Type_id() {
        return Vehicle_Type_id;
    }

    public void setVehicle_Type_id(String Vehicle_Type_id) {
        this.Vehicle_Type_id = Vehicle_Type_id;
    }

    public String getSearch_string() {
        return search_string;
    }

    public void setSearch_string(String search_string) {
        this.search_string = search_string;
    }
}
