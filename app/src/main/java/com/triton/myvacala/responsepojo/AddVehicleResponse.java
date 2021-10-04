package com.triton.myvacala.responsepojo;

public class AddVehicleResponse {


    /**
     * Status : Success
     * Message : Added successfully
     * Data : {"_id":"5f0d47ac67ea7171279bd4af","Customer_id":"5f07f9f313286d500bc9d4d8","Vehicletype_id":"5f02ea9843d45632b001f868","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f0c1023c57e0c6a78bc9395","Vehicle_Brand_Name":"BMW","Vehicle_Name_id":"5f0c50a1c1362e2e5f2fce1a","Vehicle_Name":"BMW","Year_of_Manufacture":"2019","Vehicle_No":"AP -7771","Fuel_Type_id":"5f0c12f831e1f8795ba172f7","Fuel_Type_Name":"Petrol","Vehicle_Model_id":"5f0c306f7f655108e6ca2ea7","Vehicle_Model_Name":"","Status":"","updatedAt":"2020-07-14T05:50:36.046Z","createdAt":"2020-07-14T05:50:36.046Z","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    private DataBean Data;
    private int Code;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class DataBean {
        /**
         * _id : 5f0d47ac67ea7171279bd4af
         * Customer_id : 5f07f9f313286d500bc9d4d8
         * Vehicletype_id : 5f02ea9843d45632b001f868
         * Vehicletype_Name : Four Wheeler
         * Vehicle_Brand_id : 5f0c1023c57e0c6a78bc9395
         * Vehicle_Brand_Name : BMW
         * Vehicle_Name_id : 5f0c50a1c1362e2e5f2fce1a
         * Vehicle_Name : BMW
         * Year_of_Manufacture : 2019
         * Vehicle_No : AP -7771
         * Fuel_Type_id : 5f0c12f831e1f8795ba172f7
         * Fuel_Type_Name : Petrol
         * Vehicle_Model_id : 5f0c306f7f655108e6ca2ea7
         * Vehicle_Model_Name :
         * Status :
         * updatedAt : 2020-07-14T05:50:36.046Z
         * createdAt : 2020-07-14T05:50:36.046Z
         * __v : 0
         */

        private String _id;
        private String Customer_id;
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
