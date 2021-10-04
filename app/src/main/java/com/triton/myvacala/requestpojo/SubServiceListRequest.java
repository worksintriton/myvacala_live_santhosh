package com.triton.myvacala.requestpojo;

public class SubServiceListRequest {

    /**
     * Service_id : 5f1acce99e65c148f3554db2
     * Customer_id : 5f0814a3abce73672359e092
     * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
     */

    private String Service_id;
    private String Customer_id;
    private String Vehicletype_id;

    public String getService_id() {
        return Service_id;
    }

    public void setService_id(String Service_id) {
        this.Service_id = Service_id;
    }

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
