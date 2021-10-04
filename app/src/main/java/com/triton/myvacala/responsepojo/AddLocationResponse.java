package com.triton.myvacala.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddLocationResponse {

    /**
     * Status : Success
     * Message : Added location Successfully
     * Data : {"_id":"5f06ac9ad23bdb28af841e3d","City":"Chennai","State":"TamilNadu","Country":"India","Pincode":"600100","Street":"Koil street","NearBy_LandMark":"Pooja stores","Location_NickName":"MGM","Flat_No":"24-f","Customer_Location":{"type":"Point","coordinates":[15.2345,70.546]},"Customer_id":"5f05d8fef3090625a91f40c6","lat":15.2345,"long":70.546,"Location_type":"Office","updatedAt":"2020-07-09T05:35:22.617Z","createdAt":"2020-07-09T05:35:22.617Z","__v":0}
     * Code : 300
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
         * _id : 5f06ac9ad23bdb28af841e3d
         * City : Chennai
         * State : TamilNadu
         * Country : India
         * Pincode : 600100
         * Street : Koil street
         * NearBy_LandMark : Pooja stores
         * Location_NickName : MGM
         * Flat_No : 24-f
         * Customer_Location : {"type":"Point","coordinates":[15.2345,70.546]}
         * Customer_id : 5f05d8fef3090625a91f40c6
         * lat : 15.2345
         * long : 70.546
         * Location_type : Office
         * updatedAt : 2020-07-09T05:35:22.617Z
         * createdAt : 2020-07-09T05:35:22.617Z
         * __v : 0
         */

        private String _id;
        private String City;
        private String State;
        private String Country;
        private String Pincode;
        private String Street;
        private String NearBy_LandMark;
        private String Location_NickName;
        private String Flat_No;
        private CustomerLocationBean Customer_Location;
        private String Customer_id;
        private double lat;
        @SerializedName("long")
        private double longX;
        private String Location_type;
        private String updatedAt;
        private String createdAt;
        private int __v;

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

        public CustomerLocationBean getCustomer_Location() {
            return Customer_Location;
        }

        public void setCustomer_Location(CustomerLocationBean Customer_Location) {
            this.Customer_Location = Customer_Location;
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
             * coordinates : [15.2345,70.546]
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
}
