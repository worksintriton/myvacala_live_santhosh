package com.triton.myvacala.requestpojo;

public class VehicleStatusChangeRequest {

    /**
     * vehicle_id : 5f0d4b23892e18724abf9a51
     * Customer_id : 5f07f9f313286d500bc9d4d8
     * Vehicletype_id:""
     */

    private String vehicle_id;
    private String Customer_id;
    private String Vehicletype_id;


    public String getVehicletype_id() {
        return Vehicletype_id;
    }

    public void setVehicletype_id(String vehicletype_id) {
        Vehicletype_id = vehicletype_id;
    }


    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }
}
