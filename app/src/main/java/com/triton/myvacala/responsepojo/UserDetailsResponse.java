package com.triton.myvacala.responsepojo;

import java.util.List;

public class UserDetailsResponse {


    /**
     * Status : Success
     * Message : Dashboard Data
     * VehicletypeDetails : [{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},{"_id":"5f0c0d092f857d66950cf260","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-13T07:28:09.903Z","createdAt":"2020-07-13T07:28:09.903Z","__v":0}]
     * CustomerVehicleData : [{"_id":"5fa8d5790dba634bb5901ea4","Customer_id":"5fa8d5240dba634bb5901ea1","Vehicle_Image":"https://myvacala.com/api/uploads/7d90d967-f0ac-4c8c-8843-0b288e1b55a6.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f29314a13a663621cf01f97","Vehicle_Brand_Name":"maruti","Vehicle_Name_id":"5f8559707fb27e622d58efcd","Vehicle_Name":"Four - 3","Year_of_Manufacture":"1924","Vehicle_No":"TN 45 AD 4736","Fuel_Type_id":"5f2bfc783634306aac046825","Fuel_Type_Name":"gr","Fuel_Type_Background_Color":"#008000","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-11-09T05:36:57.539Z","createdAt":"2020-11-09T05:36:57.539Z","__v":0},{"_id":"5faa7a109d52016297f6f43e","Customer_id":"5fa8d5240dba634bb5901ea1","Vehicle_Image":"https://myvacala.com/api/uploads/bd581539-ae61-41ba-b92c-8766ded48781.jpg","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f2916c913a663621cf01f96","Vehicle_Brand_Name":"Honda - 1","Vehicle_Name_id":"5f8559de7fb27e622d58efd0","Vehicle_Name":"Two - 2","Year_of_Manufacture":"1950","Vehicle_No":"TN 45 AD 6445","Fuel_Type_id":"5f2b924267cca918a4b44fc3","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f2ba1a9476398537399bb21","Vehicle_Model_Name":"125","Status":"Default","updatedAt":"2020-11-10T11:31:28.315Z","createdAt":"2020-11-10T11:31:28.315Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    private List<VehicletypeDetailsBean> VehicletypeDetails;
    private List<CustomerVehicleDataBean> CustomerVehicleData;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<VehicletypeDetailsBean> getVehicletypeDetails() {
        return VehicletypeDetails;
    }

    public void setVehicletypeDetails(List<VehicletypeDetailsBean> VehicletypeDetails) {
        this.VehicletypeDetails = VehicletypeDetails;
    }

    public List<CustomerVehicleDataBean> getCustomerVehicleData() {
        return CustomerVehicleData;
    }

    public void setCustomerVehicleData(List<CustomerVehicleDataBean> CustomerVehicleData) {
        this.CustomerVehicleData = CustomerVehicleData;
    }

    public static class VehicletypeDetailsBean {
        /**
         * _id : 5f0c0cfc2f857d66950cf25f
         * Vehicle_Type : Four Wheeler
         * updatedAt : 2020-07-13T07:27:56.412Z
         * createdAt : 2020-07-13T07:27:56.412Z
         * __v : 0
         */

        private String _id;
        private String Vehicle_Type;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getVehicle_Type() {
            return Vehicle_Type;
        }

        public void setVehicle_Type(String Vehicle_Type) {
            this.Vehicle_Type = Vehicle_Type;
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

    public static class CustomerVehicleDataBean {
        /**
         * _id : 5fa8d5790dba634bb5901ea4
         * Customer_id : 5fa8d5240dba634bb5901ea1
         * Vehicle_Image : https://myvacala.com/api/uploads/7d90d967-f0ac-4c8c-8843-0b288e1b55a6.jpg
         * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
         * Vehicletype_Name : Four Wheeler
         * Vehicle_Brand_id : 5f29314a13a663621cf01f97
         * Vehicle_Brand_Name : maruti
         * Vehicle_Name_id : 5f8559707fb27e622d58efcd
         * Vehicle_Name : Four - 3
         * Year_of_Manufacture : 1924
         * Vehicle_No : TN 45 AD 4736
         * Fuel_Type_id : 5f2bfc783634306aac046825
         * Fuel_Type_Name : gr
         * Fuel_Type_Background_Color : #008000
         * Vehicle_Model_id : 5f1e852b63f9882bd6895bea
         * Vehicle_Model_Name : Coupe
         * Status : Default
         * updatedAt : 2020-11-09T05:36:57.539Z
         * createdAt : 2020-11-09T05:36:57.539Z
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
