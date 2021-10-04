package com.triton.myvacala.responsepojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdditionHrsResponse {


    /**
     * Status : Success
     * Message : Updated successfully
     * Data : {"Vehicle_details":[{"_id":"5fa8d5790dba634bb5901ea4","Customer_id":"5fa8d5240dba634bb5901ea1","Vehicle_Image":"https://myvacala.com/api/uploads/7d90d967-f0ac-4c8c-8843-0b288e1b55a6.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f29314a13a663621cf01f97","Vehicle_Brand_Name":"maruti","Vehicle_Name_id":"5f8559707fb27e622d58efcd","Vehicle_Name":"Four - 3","Year_of_Manufacture":"1924","Vehicle_No":"TN 45 AD 4736","Fuel_Type_id":"5f2bfc783634306aac046825","Fuel_Type_Name":"gr","Fuel_Type_Background_Color":"#008000","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-11-09T05:36:57.539Z","createdAt":"2020-11-09T05:36:57.539Z","__v":0}],"_id":"5fa92f2930a5ee5f8e10881d","parkingdetails":"5fa53ead670fd83207fab183","slot_details":"floor - 1/Block - 1/slot - 1","parkingdetails_id":"5fa53ead670fd83207fab183","Vehicle_type_id":"5f0c0cfc2f857d66950cf25f","Vehicle_id":"5fa8d5790dba634bb5901ea4","booking_start_date":"2020-11-09","booking_end_date":"2020-11-09","booking_start_time":"05:25 PM","booking_end_time":"09:25 PM","total_amount":80,"total_hrs":"4","Booked_Date_and_Time":"2020-11-09 17:29:19","Customer_id":"5fa8d5240dba634bb5901ea1","Booking_id":"Bk-M576vO","additional_booking_hrs":"1","additonal_booking_amount":"20","Overall_time_duraion":"4","Overall_amount_paid":"80","Booking_status":"Check-in","Checking_date":"09-11-2020 05:36:57 PM","Checking_time":"09-11-2020 05:36:57 PM","Checkout_date":"","Checkout_time":"","total_checking_duration":"0","attach_pic":"","duration_date":"09 Nov, 05:25 PM to 09 Nov, 09:25 PM(4 Hours)","couponcode":"","couponcode_amount":"","Original_amount":"","distance":"40 mins","Kms":"15.95 kms","parking_shop_name":"Chennai Central ","parking_shop_address":"Puratchi Thalaivar Dr MGR Central railway station","parking_shop_address_link":"https://www.google.com/maps/search/Chennai+Central/@13.0648615,80.1135807,12z/data=!3m1!4b1","amount":"20","floor":"floor - 1","block":"Block - 1","slot":"slot - 1","updatedAt":"2020-11-09T12:47:35.965Z","createdAt":"2020-11-09T11:59:37.507Z","__v":0,"Price_Details":null}
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
         * Vehicle_details : [{"_id":"5fa8d5790dba634bb5901ea4","Customer_id":"5fa8d5240dba634bb5901ea1","Vehicle_Image":"https://myvacala.com/api/uploads/7d90d967-f0ac-4c8c-8843-0b288e1b55a6.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f29314a13a663621cf01f97","Vehicle_Brand_Name":"maruti","Vehicle_Name_id":"5f8559707fb27e622d58efcd","Vehicle_Name":"Four - 3","Year_of_Manufacture":"1924","Vehicle_No":"TN 45 AD 4736","Fuel_Type_id":"5f2bfc783634306aac046825","Fuel_Type_Name":"gr","Fuel_Type_Background_Color":"#008000","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-11-09T05:36:57.539Z","createdAt":"2020-11-09T05:36:57.539Z","__v":0}]
         * _id : 5fa92f2930a5ee5f8e10881d
         * parkingdetails : 5fa53ead670fd83207fab183
         * slot_details : floor - 1/Block - 1/slot - 1
         * parkingdetails_id : 5fa53ead670fd83207fab183
         * Vehicle_type_id : 5f0c0cfc2f857d66950cf25f
         * Vehicle_id : 5fa8d5790dba634bb5901ea4
         * booking_start_date : 2020-11-09
         * booking_end_date : 2020-11-09
         * booking_start_time : 05:25 PM
         * booking_end_time : 09:25 PM
         * total_amount : 80
         * total_hrs : 4
         * Booked_Date_and_Time : 2020-11-09 17:29:19
         * Customer_id : 5fa8d5240dba634bb5901ea1
         * Booking_id : Bk-M576vO
         * additional_booking_hrs : 1
         * additonal_booking_amount : 20
         * Overall_time_duraion : 4
         * Overall_amount_paid : 80
         * Booking_status : Check-in
         * Checking_date : 09-11-2020 05:36:57 PM
         * Checking_time : 09-11-2020 05:36:57 PM
         * Checkout_date :
         * Checkout_time :
         * total_checking_duration : 0
         * attach_pic :
         * duration_date : 09 Nov, 05:25 PM to 09 Nov, 09:25 PM(4 Hours)
         * couponcode :
         * couponcode_amount :
         * Original_amount :
         * distance : 40 mins
         * Kms : 15.95 kms
         * parking_shop_name : Chennai Central
         * parking_shop_address : Puratchi Thalaivar Dr MGR Central railway station
         * parking_shop_address_link : https://www.google.com/maps/search/Chennai+Central/@13.0648615,80.1135807,12z/data=!3m1!4b1
         * amount : 20
         * floor : floor - 1
         * block : Block - 1
         * slot : slot - 1
         * updatedAt : 2020-11-09T12:47:35.965Z
         * createdAt : 2020-11-09T11:59:37.507Z
         * __v : 0
         * Price_Details : null
         */

        private String _id;
        private String parkingdetails;
        private String slot_details;
        private String parkingdetails_id;
        private String Vehicle_type_id;
        private String Vehicle_id;
        private String booking_start_date;
        private String booking_end_date;
        private String booking_start_time;
        private String booking_end_time;
        private int total_amount;
        private String total_hrs;
        private String Booked_Date_and_Time;
        private String Customer_id;
        private String Booking_id;
        private String additional_booking_hrs;
        private String additonal_booking_amount;
        private String Overall_time_duraion;
        private String Overall_amount_paid;
        private String Booking_status;
        private String Checking_date;
        private String Checking_time;
        private String Checkout_date;
        private String Checkout_time;
        private String total_checking_duration;
        private String attach_pic;
        private String duration_date;
        private String couponcode;
        private String couponcode_amount;
        private String Original_amount;
        private String distance;
        private String Kms;
        private String parking_shop_name;
        private String parking_shop_address;
        private String parking_shop_address_link;
        private String amount;
        private String floor;
        private String block;
        private String slot;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private Object Price_Details;
        private ArrayList<VehicleDetailsBean> Vehicle_details;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getParkingdetails() {
            return parkingdetails;
        }

        public void setParkingdetails(String parkingdetails) {
            this.parkingdetails = parkingdetails;
        }

        public String getSlot_details() {
            return slot_details;
        }

        public void setSlot_details(String slot_details) {
            this.slot_details = slot_details;
        }

        public String getParkingdetails_id() {
            return parkingdetails_id;
        }

        public void setParkingdetails_id(String parkingdetails_id) {
            this.parkingdetails_id = parkingdetails_id;
        }

        public String getVehicle_type_id() {
            return Vehicle_type_id;
        }

        public void setVehicle_type_id(String Vehicle_type_id) {
            this.Vehicle_type_id = Vehicle_type_id;
        }

        public String getVehicle_id() {
            return Vehicle_id;
        }

        public void setVehicle_id(String Vehicle_id) {
            this.Vehicle_id = Vehicle_id;
        }

        public String getBooking_start_date() {
            return booking_start_date;
        }

        public void setBooking_start_date(String booking_start_date) {
            this.booking_start_date = booking_start_date;
        }

        public String getBooking_end_date() {
            return booking_end_date;
        }

        public void setBooking_end_date(String booking_end_date) {
            this.booking_end_date = booking_end_date;
        }

        public String getBooking_start_time() {
            return booking_start_time;
        }

        public void setBooking_start_time(String booking_start_time) {
            this.booking_start_time = booking_start_time;
        }

        public String getBooking_end_time() {
            return booking_end_time;
        }

        public void setBooking_end_time(String booking_end_time) {
            this.booking_end_time = booking_end_time;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public String getTotal_hrs() {
            return total_hrs;
        }

        public void setTotal_hrs(String total_hrs) {
            this.total_hrs = total_hrs;
        }

        public String getBooked_Date_and_Time() {
            return Booked_Date_and_Time;
        }

        public void setBooked_Date_and_Time(String Booked_Date_and_Time) {
            this.Booked_Date_and_Time = Booked_Date_and_Time;
        }

        public String getCustomer_id() {
            return Customer_id;
        }

        public void setCustomer_id(String Customer_id) {
            this.Customer_id = Customer_id;
        }

        public String getBooking_id() {
            return Booking_id;
        }

        public void setBooking_id(String Booking_id) {
            this.Booking_id = Booking_id;
        }

        public String getAdditional_booking_hrs() {
            return additional_booking_hrs;
        }

        public void setAdditional_booking_hrs(String additional_booking_hrs) {
            this.additional_booking_hrs = additional_booking_hrs;
        }

        public String getAdditonal_booking_amount() {
            return additonal_booking_amount;
        }

        public void setAdditonal_booking_amount(String additonal_booking_amount) {
            this.additonal_booking_amount = additonal_booking_amount;
        }

        public String getOverall_time_duraion() {
            return Overall_time_duraion;
        }

        public void setOverall_time_duraion(String Overall_time_duraion) {
            this.Overall_time_duraion = Overall_time_duraion;
        }

        public String getOverall_amount_paid() {
            return Overall_amount_paid;
        }

        public void setOverall_amount_paid(String Overall_amount_paid) {
            this.Overall_amount_paid = Overall_amount_paid;
        }

        public String getBooking_status() {
            return Booking_status;
        }

        public void setBooking_status(String Booking_status) {
            this.Booking_status = Booking_status;
        }

        public String getChecking_date() {
            return Checking_date;
        }

        public void setChecking_date(String Checking_date) {
            this.Checking_date = Checking_date;
        }

        public String getChecking_time() {
            return Checking_time;
        }

        public void setChecking_time(String Checking_time) {
            this.Checking_time = Checking_time;
        }

        public String getCheckout_date() {
            return Checkout_date;
        }

        public void setCheckout_date(String Checkout_date) {
            this.Checkout_date = Checkout_date;
        }

        public String getCheckout_time() {
            return Checkout_time;
        }

        public void setCheckout_time(String Checkout_time) {
            this.Checkout_time = Checkout_time;
        }

        public String getTotal_checking_duration() {
            return total_checking_duration;
        }

        public void setTotal_checking_duration(String total_checking_duration) {
            this.total_checking_duration = total_checking_duration;
        }

        public String getAttach_pic() {
            return attach_pic;
        }

        public void setAttach_pic(String attach_pic) {
            this.attach_pic = attach_pic;
        }

        public String getDuration_date() {
            return duration_date;
        }

        public void setDuration_date(String duration_date) {
            this.duration_date = duration_date;
        }

        public String getCouponcode() {
            return couponcode;
        }

        public void setCouponcode(String couponcode) {
            this.couponcode = couponcode;
        }

        public String getCouponcode_amount() {
            return couponcode_amount;
        }

        public void setCouponcode_amount(String couponcode_amount) {
            this.couponcode_amount = couponcode_amount;
        }

        public String getOriginal_amount() {
            return Original_amount;
        }

        public void setOriginal_amount(String Original_amount) {
            this.Original_amount = Original_amount;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
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

        public Object getPrice_Details() {
            return Price_Details;
        }

        public void setPrice_Details(Object Price_Details) {
            this.Price_Details = Price_Details;
        }

        public ArrayList<VehicleDetailsBean> getVehicle_details() {
            return Vehicle_details;
        }

        public void setVehicle_details(ArrayList<VehicleDetailsBean> Vehicle_details) {
            this.Vehicle_details = Vehicle_details;
        }

        public static class VehicleDetailsBean implements Serializable {
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
}
