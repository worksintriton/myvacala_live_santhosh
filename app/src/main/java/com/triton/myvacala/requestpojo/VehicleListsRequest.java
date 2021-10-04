package com.triton.myvacala.requestpojo;

public class VehicleListsRequest {

    /**
     * Customer_id : 5f0c146edb97a179bb713ed3
     * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
     */

    private String Customer_id;
    private String Vehicletype_id;

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }

    public String getVehicletype_id() {
        return Vehicletype_id;
    }

    public void setVehicletype_id(String Vehicletype_id) {
        this.Vehicletype_id = Vehicletype_id;
    }
}
