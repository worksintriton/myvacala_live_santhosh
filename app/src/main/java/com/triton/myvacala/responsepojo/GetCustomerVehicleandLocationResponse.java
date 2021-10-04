package com.triton.myvacala.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetCustomerVehicleandLocationResponse {

    /**
     * Status : Success
     * Message : Servicedetails
     * locationData : {"Customer_Location":{"type":"Point","coordinates":[11.172053079742566,78.59447177499533]},"_id":"5f351168b2c53e69fb341b99","City":"Tiruchirappalli","State":"Tamil Nadu","Country":"","Pincode":"621008","Street":"Thuraiyur - Selipalayam Road","NearBy_LandMark":"Balaji Hospital","Location_NickName":"Balaji Hospital near DK Apartment","Flat_No":"88D","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.172053079742566,"long":78.59447177499533,"Location_type":"Work","Status":"Default","updatedAt":"2020-08-20T11:22:20.767Z","createdAt":"2020-08-13T10:09:44.684Z","__v":0}
     * CustomerVehicleData : [{"_id":"5f212eecc497960c531f1f01","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/e1e00e57-d3e7-42dc-99d9-6ccd7b780617.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Brand_Name":"AUDI","Vehicle_Name_id":"5f1ee402e649907a21e1a84f","Vehicle_Name":"AUDI A6","Year_of_Manufacture":"2016","Vehicle_No":"TN 56 FG 6865","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af058de7bf45b602f8bb1","Vehicle_Model_Name":"Hatchbact","Status":"Default","updatedAt":"2020-08-24T14:29:06.945Z","createdAt":"2020-07-29T08:10:20.318Z","__v":0},{"_id":"5f212f95c497960c531f1f03","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e996757d71f3af6be1072","Vehicle_Name":"Pulsar-CT","Year_of_Manufacture":"2020","Vehicle_No":"TN 56 AD 4565","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-07-29T08:16:12.433Z","createdAt":"2020-07-29T08:13:09.438Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private LocationDataBean locationData;
    private int Code;
    private ArrayList<CustomerVehicleDataBean> CustomerVehicleData;

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

    public LocationDataBean getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationDataBean locationData) {
        this.locationData = locationData;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public ArrayList<CustomerVehicleDataBean> getCustomerVehicleData() {
        return CustomerVehicleData;
    }

    public void setCustomerVehicleData(ArrayList<CustomerVehicleDataBean> CustomerVehicleData) {
        this.CustomerVehicleData = CustomerVehicleData;
    }

    public static class LocationDataBean {
        /**
         * Customer_Location : {"type":"Point","coordinates":[11.172053079742566,78.59447177499533]}
         * _id : 5f351168b2c53e69fb341b99
         * City : Tiruchirappalli
         * State : Tamil Nadu
         * Country :
         * Pincode : 621008
         * Street : Thuraiyur - Selipalayam Road
         * NearBy_LandMark : Balaji Hospital
         * Location_NickName : Balaji Hospital near DK Apartment
         * Flat_No : 88D
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * lat : 11.172053079742566
         * long : 78.59447177499533
         * Location_type : Work
         * Status : Default
         * updatedAt : 2020-08-20T11:22:20.767Z
         * createdAt : 2020-08-13T10:09:44.684Z
         * __v : 0
         */

        private CustomerLocationBean Customer_Location;
        private String _id;
        private String City;
        private String State;
        private String Country;
        private String Pincode;
        private String Street;
        private String NearBy_LandMark;
        private String Location_NickName;
        private String Flat_No;
        private String Customer_id;
        private double lat;
        @SerializedName("long")
        private double longX;
        private String Location_type;
        private String Status;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public CustomerLocationBean getCustomer_Location() {
            return Customer_Location;
        }

        public void setCustomer_Location(CustomerLocationBean Customer_Location) {
            this.Customer_Location = Customer_Location;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String Country) {
            this.Country = Country;
        }

        public String getPincode() {
            return Pincode;
        }

        public void setPincode(String Pincode) {
            this.Pincode = Pincode;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String Street) {
            this.Street = Street;
        }

        public String getNearBy_LandMark() {
            return NearBy_LandMark;
        }

        public void setNearBy_LandMark(String NearBy_LandMark) {
            this.NearBy_LandMark = NearBy_LandMark;
        }

        public String getLocation_NickName() {
            return Location_NickName;
        }

        public void setLocation_NickName(String Location_NickName) {
            this.Location_NickName = Location_NickName;
        }

        public String getFlat_No() {
            return Flat_No;
        }

        public void setFlat_No(String Flat_No) {
            this.Flat_No = Flat_No;
        }

        public String getCustomer_id() {
            return Customer_id;
        }

        public void setCustomer_id(String Customer_id) {
            this.Customer_id = Customer_id;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getLocation_type() {
            return Location_type;
        }

        public void setLocation_type(String Location_type) {
            this.Location_type = Location_type;
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

        public static class CustomerLocationBean {
            /**
             * type : Point
             * coordinates : [11.172053079742566,78.59447177499533]
             */

            private String type;
            private List<Double> coordinates;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }
        }
    }

    public static class CustomerVehicleDataBean implements Serializable {
        /**
         * _id : 5f212eecc497960c531f1f01
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * Vehicle_Image : http://3.101.31.129:3000/api/uploads/e1e00e57-d3e7-42dc-99d9-6ccd7b780617.jpg
         * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
         * Vehicletype_Name : Four Wheeler
         * Vehicle_Brand_id : 5f1a842467024c39a738e821
         * Vehicle_Brand_Name : AUDI
         * Vehicle_Name_id : 5f1ee402e649907a21e1a84f
         * Vehicle_Name : AUDI A6
         * Year_of_Manufacture : 2016
         * Vehicle_No : TN 56 FG 6865
         * Fuel_Type_id : 5f1e692a9f12a81b3dd70890
         * Fuel_Type_Name : Petrol
         * Fuel_Type_Background_Color : #802A2A
         * Vehicle_Model_id : 5f1af058de7bf45b602f8bb1
         * Vehicle_Model_Name : Hatchbact
         * Status : Default
         * updatedAt : 2020-08-24T14:29:06.945Z
         * createdAt : 2020-07-29T08:10:20.318Z
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
