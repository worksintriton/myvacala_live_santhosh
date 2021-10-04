package com.triton.myvacala.requestpojo;

public class MainServiceGetListRequest {

    /**
     * Vehicle_Type_id : 5f0c0cfc2f857d66950cf25f
     * Master_Service_id : 5f1ac41b55abee4870516567
     * Customer_id:""
     */

    private String Vehicle_Type_id;
    private String Master_Service_id;
    private String Customer_id;

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }

    public String getVehicle_Type_id() {
        return Vehicle_Type_id;
    }

    public void setVehicle_Type_id(String Vehicle_Type_id) {
        this.Vehicle_Type_id = Vehicle_Type_id;
    }

    public String getMaster_Service_id() {
        return Master_Service_id;
    }

    public void setMaster_Service_id(String Master_Service_id) {
        this.Master_Service_id = Master_Service_id;
    }
}
