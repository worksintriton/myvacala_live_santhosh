package com.triton.myvacala.requestpojo;

import java.util.List;

public class AddingCartRequest {


    /**
     * Sub_service_id : 5f1eaf8cab6576455baf5f72
     * Customer_id : 5f1fae9bbcd5650a5ab130e8
     * Vehicle_type : Two Wheeler
     * Vehicle_details : [{"_id":"5f212f39c497960c531f1f02","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Brand_Name":"AUDI","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_Name":"AUDI A2","Year_of_Manufacture":"1936","Vehicle_No":"TN 56 YH 6865","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-07-29T08:16:05.396Z","createdAt":"2020-07-29T08:11:37.642Z","__v":0}]
     */

    private String Sub_service_id;
    private String Customer_id;

    public String getVehicletype_id() {
        return Vehicletype_id;
    }

    public void setVehicletype_id(String vehicletype_id) {
        Vehicletype_id = vehicletype_id;
    }

    private String Vehicletype_id;
    private List<VehicleDetailsBean> Vehicle_details;

    public String getSub_service_id() {
        return Sub_service_id;
    }

    public void setSub_service_id(String Sub_service_id) {
        this.Sub_service_id = Sub_service_id;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
    }


    public List<VehicleDetailsBean> getVehicle_details() {
        return Vehicle_details;
    }

    public void setVehicle_details(List<VehicleDetailsBean> Vehicle_details) {
        this.Vehicle_details = Vehicle_details;
    }

    public static class VehicleDetailsBean {
        /**
         * _id : 5f212f39c497960c531f1f02
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * Vehicle_Image : http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg
         * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
         * Vehicletype_Name : Four Wheeler
         * Vehicle_Brand_id : 5f1a842467024c39a738e821
         * Vehicle_Brand_Name : AUDI
         * Vehicle_Name_id : 5f1e79dfa5e13d1c28e45eba
         * Vehicle_Name : AUDI A2
         * Year_of_Manufacture : 1936
         * Vehicle_No : TN 56 YH 6865
         * Fuel_Type_id : 5f1e692a9f12a81b3dd70890
         * Fuel_Type_Name : Petrol
         * Fuel_Type_Background_Color : #802A2A
         * Vehicle_Model_id : 5f1e852b63f9882bd6895bea
         * Vehicle_Model_Name : Coupe
         * Status : Default
         * updatedAt : 2020-07-29T08:16:05.396Z
         * createdAt : 2020-07-29T08:11:37.642Z
         * __v : 0
         */

        private String _id;
        private String Customer_id;
        private String Vehicle_Image;
        private String Vehicletype_id;
        private String Vehicletype_Name;
        private String Vehicle_Brand_id;
        private String Vehicle_Brand_Name;
        private String Vehicle_Name_id;
        private String Vehicle_Name;
        private String Year_of_Manufacture;
        private String Vehicle_No;
        private String Fuel_Type_id;
        private String Fuel_Type_Name;
        private String Fuel_Type_Background_Color;
        private String Vehicle_Model_id;
        private String Vehicle_Model_Name;
        private String Status;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCustomer_id() {
            return Customer_id;
        }

        public void setCustomer_id(String Customer_id) {
            this.Customer_id = Customer_id;
        }

        public String getVehicle_Image() {
            return Vehicle_Image;
        }

        public void setVehicle_Image(String Vehicle_Image) {
            this.Vehicle_Image = Vehicle_Image;
        }

        public String getVehicletype_id() {
            return Vehicletype_id;
        }

        public void setVehicletype_id(String Vehicletype_id) {
            this.Vehicletype_id = Vehicletype_id;
        }

        public String getVehicletype_Name() {
            return Vehicletype_Name;
        }

        public void setVehicletype_Name(String Vehicletype_Name) {
            this.Vehicletype_Name = Vehicletype_Name;
        }

        public String getVehicle_Brand_id() {
            return Vehicle_Brand_id;
        }

        public void setVehicle_Brand_id(String Vehicle_Brand_id) {
            this.Vehicle_Brand_id = Vehicle_Brand_id;
        }

        public String getVehicle_Brand_Name() {
            return Vehicle_Brand_Name;
        }

        public void setVehicle_Brand_Name(String Vehicle_Brand_Name) {
            this.Vehicle_Brand_Name = Vehicle_Brand_Name;
        }

        public String getVehicle_Name_id() {
            return Vehicle_Name_id;
        }

        public void setVehicle_Name_id(String Vehicle_Name_id) {
            this.Vehicle_Name_id = Vehicle_Name_id;
        }

        public String getVehicle_Name() {
            return Vehicle_Name;
        }

        public void setVehicle_Name(String Vehicle_Name) {
            this.Vehicle_Name = Vehicle_Name;
        }

        public String getYear_of_Manufacture() {
            return Year_of_Manufacture;
        }

        public void setYear_of_Manufacture(String Year_of_Manufacture) {
            this.Year_of_Manufacture = Year_of_Manufacture;
        }

        public String getVehicle_No() {
            return Vehicle_No;
        }

        public void setVehicle_No(String Vehicle_No) {
            this.Vehicle_No = Vehicle_No;
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

        public String getFuel_Type_Background_Color() {
            return Fuel_Type_Background_Color;
        }

        public void setFuel_Type_Background_Color(String Fuel_Type_Background_Color) {
            this.Fuel_Type_Background_Color = Fuel_Type_Background_Color;
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

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
