package com.triton.myvacala.responsepojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkingBookingCreateResponse {


    /**
     * Status : Success
     * Message : Parking Booking Added successfully
     * Data : {"parking_shop_name":"Sakshi Towers","parking_shop_address":"20 algappa road, perambur, chennai -112","parking_shop_address_link":"https://www.google.com/maps/","Booking_id":"Book-111212","Vehicle_details":[{"_id":"5f33c5a423ab6d5fa01b191d","Customer_id":"5f1fc277cfb5c741551e1793","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e70539f12a81b3dd708a1","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e70539f12a81b3dd708a1","Vehicle_Name":"Pulsar","Year_of_Manufacture":"2020","Vehicle_No":"1234","Fuel_Type_id":"5f29167113a663621cf01f94","Fuel_Type_Name":"Nature Gas","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-08-12T10:34:12.814Z","createdAt":"2020-08-12T10:34:12.814Z","__v":0}],"amount":"200","floor":"2","block":"B2","slot":"2","Booking_start_date":"2020-09-03","Booking_start_time":"16","Booking_end_date":"2020-09-03","Booking_end_time":"17","Total_hours":"3 Hours","distance":"130.78","Kms":"130.78"}
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
         * parking_shop_name : Sakshi Towers
         * parking_shop_address : 20 algappa road, perambur, chennai -112
         * parking_shop_address_link : https://www.google.com/maps/
         * Booking_id : Book-111212
         * Vehicle_details : [{"_id":"5f33c5a423ab6d5fa01b191d","Customer_id":"5f1fc277cfb5c741551e1793","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e70539f12a81b3dd708a1","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e70539f12a81b3dd708a1","Vehicle_Name":"Pulsar","Year_of_Manufacture":"2020","Vehicle_No":"1234","Fuel_Type_id":"5f29167113a663621cf01f94","Fuel_Type_Name":"Nature Gas","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-08-12T10:34:12.814Z","createdAt":"2020-08-12T10:34:12.814Z","__v":0}]
         * amount : 200
         * floor : 2
         * block : B2
         * slot : 2
         * Booking_start_date : 2020-09-03
         * Booking_start_time : 16
         * Booking_end_date : 2020-09-03
         * Booking_end_time : 17
         * Total_hours : 3 Hours
         * distance : 130.78
         * Kms : 130.78
         */

        private String _id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        private String parking_shop_name;
        private String parking_shop_address;
        private String parking_shop_address_link;
        private String Booking_id;
        private String amount;
        private String slot_details;

        private String Booking_start_date;
        private String Booking_start_time;
        private String Booking_end_date;
        private String Booking_end_time;
        private String Total_hours;
        private String distance;
        private String Kms;
        private String couponcode_amount;

        public String getCouponcode_amount() {
            return couponcode_amount;
        }

        public void setCouponcode_amount(String couponcode_amount) {
            this.couponcode_amount = couponcode_amount;
        }

        private ArrayList<VehicleDetailsBean> Vehicle_details;

        public String getSlot_details() {
            return slot_details;
        }

        public void setSlot_details(String slot_details) {
            this.slot_details = slot_details;
        }

        public String getParking_shop_name() {
            return parking_shop_name;
        }

        public void setParking_shop_name(String parking_shop_name) {
            this.parking_shop_name = parking_shop_name;
        }

        public String getParking_shop_address() {
            return parking_shop_address;
        }

        public void setParking_shop_address(String parking_shop_address) {
            this.parking_shop_address = parking_shop_address;
        }

        public String getParking_shop_address_link() {
            return parking_shop_address_link;
        }

        public void setParking_shop_address_link(String parking_shop_address_link) {
            this.parking_shop_address_link = parking_shop_address_link;
        }

        public String getBooking_id() {
            return Booking_id;
        }

        public void setBooking_id(String Booking_id) {
            this.Booking_id = Booking_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }



        public String getBooking_start_date() {
            return Booking_start_date;
        }

        public void setBooking_start_date(String Booking_start_date) {
            this.Booking_start_date = Booking_start_date;
        }

        public String getBooking_start_time() {
            return Booking_start_time;
        }

        public void setBooking_start_time(String Booking_start_time) {
            this.Booking_start_time = Booking_start_time;
        }

        public String getBooking_end_date() {
            return Booking_end_date;
        }

        public void setBooking_end_date(String Booking_end_date) {
            this.Booking_end_date = Booking_end_date;
        }

        public String getBooking_end_time() {
            return Booking_end_time;
        }

        public void setBooking_end_time(String Booking_end_time) {
            this.Booking_end_time = Booking_end_time;
        }

        public String getTotal_hours() {
            return Total_hours;
        }

        public void setTotal_hours(String Total_hours) {
            this.Total_hours = Total_hours;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getKms() {
            return Kms;
        }

        public void setKms(String Kms) {
            this.Kms = Kms;
        }

        public ArrayList<VehicleDetailsBean> getVehicle_details() {
            return Vehicle_details;
        }

        public void setVehicle_details(ArrayList<VehicleDetailsBean> Vehicle_details) {
            this.Vehicle_details = Vehicle_details;
        }

        public static class VehicleDetailsBean implements Serializable {
            /**
             * _id : 5f33c5a423ab6d5fa01b191d
             * Customer_id : 5f1fc277cfb5c741551e1793
             * Vehicle_Image : http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png
             * Vehicletype_id : 5f0c0d092f857d66950cf260
             * Vehicletype_Name : Two Wheeler
             * Vehicle_Brand_id : 5f1e70539f12a81b3dd708a1
             * Vehicle_Brand_Name : Bajaj
             * Vehicle_Name_id : 5f1e70539f12a81b3dd708a1
             * Vehicle_Name : Pulsar
             * Year_of_Manufacture : 2020
             * Vehicle_No : 1234
             * Fuel_Type_id : 5f29167113a663621cf01f94
             * Fuel_Type_Name : Nature Gas
             * Fuel_Type_Background_Color : #802A2A
             * Vehicle_Model_id : 5f1af089de7bf45b602f8bb3
             * Vehicle_Model_Name : Standard
             * Status : Default
             * updatedAt : 2020-08-12T10:34:12.814Z
             * createdAt : 2020-08-12T10:34:12.814Z
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
}
