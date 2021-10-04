package com.triton.myvacala.requestpojo;

import java.util.List;

public class FinalpayableUpdateResponse {

    /**
     * Status : Success
     * Message : Updated
     * Data : {"Customer_Address":["Iluppaiyur,DK HOME Karatampatti Iluppaiyur road, Iluppaiyur, Tamil Nadu 621006, India"],"card_details":[{"Cart_Status":false,"Cart_count":1,"Count_type":false,"Discount_Price":500,"ItemList":[],"Original_Price":2300,"Service_id":"5f3a589dedbde6223ae8abe3","Vehicle_Name_id":["5f8559187fb27e622d58efcb"],"__v":0,"_id":"5f88079f68779c726eced74f","createdAt":"2020-10-15T08:26:07.350Z","sub_ser_Spec1":["spec-0003"],"sub_ser_Title":"My testing-1","sub_ser_display_img":["https://myvacala.com/api/uploads/f4e607d5-671f-4b77-94bf-8eabc470b261.png"],"sub_ser_image":"https://myvacala.com/api/uploads/dc5f7b06-ea6b-4891-9be3-2ba8e54275d7.png","updatedAt":"2020-10-15T08:26:07.350Z"}],"Pick_Up_Location":[],"Job_Card":[],"Customer_Invoice":[],"Workshop_Location":[""],"Status_history_text":[{"title":"Booking completed","date":"Thu Oct 22 2020 10:30:37 GMT+0000 (Coordinated Universal Time)"}],"_id":"5f915f4d9c1be21c2cbcbd72","Booking_id":"8J1VIZ4LNQQK","Customer_Name":"SRIRAM","Customer_id":"5f914cad6fb83b14dea7e77e","Customer_Phone":6379223242,"Customer_Email":"sriram@gmail.com","Services":"Test main service 1","Subserivces":"My testing-1","Vehicle_Type":"","Vehicle_No":"TN 45 AD 4543","Vehicle_Image":"https://myvacala.com/api/uploads/4726e38f-2ff0-4bcf-8008-a0c29080dd85.jpg","Vehicle_Name":"Four - 1","Lubricant_type":"Petrol","Lubricant_type_color":"#802A2A","Arrival_Mode":"PickUp","Pickup_Date":"2020-10-22T00:00:00.000Z","Pickup_Time":"05:00 - 06:00","Coupon_Code":"","Coupon_Code_Amount":"","Total_Amount":700,"Order_Value":2300,"Year_Of_Mfg":"","Booking_Status":"Booking completed","Final_bill_payed":"0","Workshop_Name":"","Track_order_text":"Booking completed is completed","Mechanicworkshop_ids":"","updatedAt":"2020-10-22T10:47:25.557Z","createdAt":"2020-10-22T10:30:37.490Z","__v":0}
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
         * Customer_Address : ["Iluppaiyur,DK HOME Karatampatti Iluppaiyur road, Iluppaiyur, Tamil Nadu 621006, India"]
         * card_details : [{"Cart_Status":false,"Cart_count":1,"Count_type":false,"Discount_Price":500,"ItemList":[],"Original_Price":2300,"Service_id":"5f3a589dedbde6223ae8abe3","Vehicle_Name_id":["5f8559187fb27e622d58efcb"],"__v":0,"_id":"5f88079f68779c726eced74f","createdAt":"2020-10-15T08:26:07.350Z","sub_ser_Spec1":["spec-0003"],"sub_ser_Title":"My testing-1","sub_ser_display_img":["https://myvacala.com/api/uploads/f4e607d5-671f-4b77-94bf-8eabc470b261.png"],"sub_ser_image":"https://myvacala.com/api/uploads/dc5f7b06-ea6b-4891-9be3-2ba8e54275d7.png","updatedAt":"2020-10-15T08:26:07.350Z"}]
         * Pick_Up_Location : []
         * Job_Card : []
         * Customer_Invoice : []
         * Workshop_Location : [""]
         * Status_history_text : [{"title":"Booking completed","date":"Thu Oct 22 2020 10:30:37 GMT+0000 (Coordinated Universal Time)"}]
         * _id : 5f915f4d9c1be21c2cbcbd72
         * Booking_id : 8J1VIZ4LNQQK
         * Customer_Name : SRIRAM
         * Customer_id : 5f914cad6fb83b14dea7e77e
         * Customer_Phone : 6379223242
         * Customer_Email : sriram@gmail.com
         * Services : Test main service 1
         * Subserivces : My testing-1
         * Vehicle_Type :
         * Vehicle_No : TN 45 AD 4543
         * Vehicle_Image : https://myvacala.com/api/uploads/4726e38f-2ff0-4bcf-8008-a0c29080dd85.jpg
         * Vehicle_Name : Four - 1
         * Lubricant_type : Petrol
         * Lubricant_type_color : #802A2A
         * Arrival_Mode : PickUp
         * Pickup_Date : 2020-10-22T00:00:00.000Z
         * Pickup_Time : 05:00 - 06:00
         * Coupon_Code :
         * Coupon_Code_Amount :
         * Total_Amount : 700
         * Order_Value : 2300
         * Year_Of_Mfg :
         * Booking_Status : Booking completed
         * Final_bill_payed : 0
         * Workshop_Name :
         * Track_order_text : Booking completed is completed
         * Mechanicworkshop_ids :
         * updatedAt : 2020-10-22T10:47:25.557Z
         * createdAt : 2020-10-22T10:30:37.490Z
         * __v : 0
         */

        private String _id;
        private String Booking_id;
        private String Customer_Name;
        private String Customer_id;
        private long Customer_Phone;
        private String Customer_Email;
        private String Services;
        private String Subserivces;
        private String Vehicle_Type;
        private String Vehicle_No;
        private String Vehicle_Image;
        private String Vehicle_Name;
        private String Lubricant_type;
        private String Lubricant_type_color;
        private String Arrival_Mode;
        private String Pickup_Date;
        private String Pickup_Time;
        private String Coupon_Code;
        private String Coupon_Code_Amount;
        private int Total_Amount;
        private int Order_Value;
        private String Year_Of_Mfg;
        private String Booking_Status;
        private String Final_bill_payed;
        private String Workshop_Name;
        private String Track_order_text;
        private String Mechanicworkshop_ids;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<String> Customer_Address;
        private List<CardDetailsBean> card_details;
        private List<?> Pick_Up_Location;
        private List<?> Job_Card;
        private List<?> Customer_Invoice;
        private List<String> Workshop_Location;
        private List<StatusHistoryTextBean> Status_history_text;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getBooking_id() {
            return Booking_id;
        }

        public void setBooking_id(String Booking_id) {
            this.Booking_id = Booking_id;
        }

        public String getCustomer_Name() {
            return Customer_Name;
        }

        public void setCustomer_Name(String Customer_Name) {
            this.Customer_Name = Customer_Name;
        }

        public String getCustomer_id() {
            return Customer_id;
        }

        public void setCustomer_id(String Customer_id) {
            this.Customer_id = Customer_id;
        }

        public long getCustomer_Phone() {
            return Customer_Phone;
        }

        public void setCustomer_Phone(long Customer_Phone) {
            this.Customer_Phone = Customer_Phone;
        }

        public String getCustomer_Email() {
            return Customer_Email;
        }

        public void setCustomer_Email(String Customer_Email) {
            this.Customer_Email = Customer_Email;
        }

        public String getServices() {
            return Services;
        }

        public void setServices(String Services) {
            this.Services = Services;
        }

        public String getSubserivces() {
            return Subserivces;
        }

        public void setSubserivces(String Subserivces) {
            this.Subserivces = Subserivces;
        }

        public String getVehicle_Type() {
            return Vehicle_Type;
        }

        public void setVehicle_Type(String Vehicle_Type) {
            this.Vehicle_Type = Vehicle_Type;
        }

        public String getVehicle_No() {
            return Vehicle_No;
        }

        public void setVehicle_No(String Vehicle_No) {
            this.Vehicle_No = Vehicle_No;
        }

        public String getVehicle_Image() {
            return Vehicle_Image;
        }

        public void setVehicle_Image(String Vehicle_Image) {
            this.Vehicle_Image = Vehicle_Image;
        }

        public String getVehicle_Name() {
            return Vehicle_Name;
        }

        public void setVehicle_Name(String Vehicle_Name) {
            this.Vehicle_Name = Vehicle_Name;
        }

        public String getLubricant_type() {
            return Lubricant_type;
        }

        public void setLubricant_type(String Lubricant_type) {
            this.Lubricant_type = Lubricant_type;
        }

        public String getLubricant_type_color() {
            return Lubricant_type_color;
        }

        public void setLubricant_type_color(String Lubricant_type_color) {
            this.Lubricant_type_color = Lubricant_type_color;
        }

        public String getArrival_Mode() {
            return Arrival_Mode;
        }

        public void setArrival_Mode(String Arrival_Mode) {
            this.Arrival_Mode = Arrival_Mode;
        }

        public String getPickup_Date() {
            return Pickup_Date;
        }

        public void setPickup_Date(String Pickup_Date) {
            this.Pickup_Date = Pickup_Date;
        }

        public String getPickup_Time() {
            return Pickup_Time;
        }

        public void setPickup_Time(String Pickup_Time) {
            this.Pickup_Time = Pickup_Time;
        }

        public String getCoupon_Code() {
            return Coupon_Code;
        }

        public void setCoupon_Code(String Coupon_Code) {
            this.Coupon_Code = Coupon_Code;
        }

        public String getCoupon_Code_Amount() {
            return Coupon_Code_Amount;
        }

        public void setCoupon_Code_Amount(String Coupon_Code_Amount) {
            this.Coupon_Code_Amount = Coupon_Code_Amount;
        }

        public int getTotal_Amount() {
            return Total_Amount;
        }

        public void setTotal_Amount(int Total_Amount) {
            this.Total_Amount = Total_Amount;
        }

        public int getOrder_Value() {
            return Order_Value;
        }

        public void setOrder_Value(int Order_Value) {
            this.Order_Value = Order_Value;
        }

        public String getYear_Of_Mfg() {
            return Year_Of_Mfg;
        }

        public void setYear_Of_Mfg(String Year_Of_Mfg) {
            this.Year_Of_Mfg = Year_Of_Mfg;
        }

        public String getBooking_Status() {
            return Booking_Status;
        }

        public void setBooking_Status(String Booking_Status) {
            this.Booking_Status = Booking_Status;
        }

        public String getFinal_bill_payed() {
            return Final_bill_payed;
        }

        public void setFinal_bill_payed(String Final_bill_payed) {
            this.Final_bill_payed = Final_bill_payed;
        }

        public String getWorkshop_Name() {
            return Workshop_Name;
        }

        public void setWorkshop_Name(String Workshop_Name) {
            this.Workshop_Name = Workshop_Name;
        }

        public String getTrack_order_text() {
            return Track_order_text;
        }

        public void setTrack_order_text(String Track_order_text) {
            this.Track_order_text = Track_order_text;
        }

        public String getMechanicworkshop_ids() {
            return Mechanicworkshop_ids;
        }

        public void setMechanicworkshop_ids(String Mechanicworkshop_ids) {
            this.Mechanicworkshop_ids = Mechanicworkshop_ids;
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

        public List<String> getCustomer_Address() {
            return Customer_Address;
        }

        public void setCustomer_Address(List<String> Customer_Address) {
            this.Customer_Address = Customer_Address;
        }

        public List<CardDetailsBean> getCard_details() {
            return card_details;
        }

        public void setCard_details(List<CardDetailsBean> card_details) {
            this.card_details = card_details;
        }

        public List<?> getPick_Up_Location() {
            return Pick_Up_Location;
        }

        public void setPick_Up_Location(List<?> Pick_Up_Location) {
            this.Pick_Up_Location = Pick_Up_Location;
        }

        public List<?> getJob_Card() {
            return Job_Card;
        }

        public void setJob_Card(List<?> Job_Card) {
            this.Job_Card = Job_Card;
        }

        public List<?> getCustomer_Invoice() {
            return Customer_Invoice;
        }

        public void setCustomer_Invoice(List<?> Customer_Invoice) {
            this.Customer_Invoice = Customer_Invoice;
        }

        public List<String> getWorkshop_Location() {
            return Workshop_Location;
        }

        public void setWorkshop_Location(List<String> Workshop_Location) {
            this.Workshop_Location = Workshop_Location;
        }

        public List<StatusHistoryTextBean> getStatus_history_text() {
            return Status_history_text;
        }

        public void setStatus_history_text(List<StatusHistoryTextBean> Status_history_text) {
            this.Status_history_text = Status_history_text;
        }

        public static class CardDetailsBean {
            /**
             * Cart_Status : false
             * Cart_count : 1
             * Count_type : false
             * Discount_Price : 500
             * ItemList : []
             * Original_Price : 2300
             * Service_id : 5f3a589dedbde6223ae8abe3
             * Vehicle_Name_id : ["5f8559187fb27e622d58efcb"]
             * __v : 0
             * _id : 5f88079f68779c726eced74f
             * createdAt : 2020-10-15T08:26:07.350Z
             * sub_ser_Spec1 : ["spec-0003"]
             * sub_ser_Title : My testing-1
             * sub_ser_display_img : ["https://myvacala.com/api/uploads/f4e607d5-671f-4b77-94bf-8eabc470b261.png"]
             * sub_ser_image : https://myvacala.com/api/uploads/dc5f7b06-ea6b-4891-9be3-2ba8e54275d7.png
             * updatedAt : 2020-10-15T08:26:07.350Z
             */

            private boolean Cart_Status;
            private int Cart_count;
            private boolean Count_type;
            private int Discount_Price;
            private int Original_Price;
            private String Service_id;
            private int __v;
            private String _id;
            private String createdAt;
            private String sub_ser_Title;
            private String sub_ser_image;
            private String updatedAt;
            private List<?> ItemList;
            private List<String> Vehicle_Name_id;
            private List<String> sub_ser_Spec1;
            private List<String> sub_ser_display_img;

            public boolean isCart_Status() {
                return Cart_Status;
            }

            public void setCart_Status(boolean Cart_Status) {
                this.Cart_Status = Cart_Status;
            }

            public int getCart_count() {
                return Cart_count;
            }

            public void setCart_count(int Cart_count) {
                this.Cart_count = Cart_count;
            }

            public boolean isCount_type() {
                return Count_type;
            }

            public void setCount_type(boolean Count_type) {
                this.Count_type = Count_type;
            }

            public int getDiscount_Price() {
                return Discount_Price;
            }

            public void setDiscount_Price(int Discount_Price) {
                this.Discount_Price = Discount_Price;
            }

            public int getOriginal_Price() {
                return Original_Price;
            }

            public void setOriginal_Price(int Original_Price) {
                this.Original_Price = Original_Price;
            }

            public String getService_id() {
                return Service_id;
            }

            public void setService_id(String Service_id) {
                this.Service_id = Service_id;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getSub_ser_Title() {
                return sub_ser_Title;
            }

            public void setSub_ser_Title(String sub_ser_Title) {
                this.sub_ser_Title = sub_ser_Title;
            }

            public String getSub_ser_image() {
                return sub_ser_image;
            }

            public void setSub_ser_image(String sub_ser_image) {
                this.sub_ser_image = sub_ser_image;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public List<?> getItemList() {
                return ItemList;
            }

            public void setItemList(List<?> ItemList) {
                this.ItemList = ItemList;
            }

            public List<String> getVehicle_Name_id() {
                return Vehicle_Name_id;
            }

            public void setVehicle_Name_id(List<String> Vehicle_Name_id) {
                this.Vehicle_Name_id = Vehicle_Name_id;
            }

            public List<String> getSub_ser_Spec1() {
                return sub_ser_Spec1;
            }

            public void setSub_ser_Spec1(List<String> sub_ser_Spec1) {
                this.sub_ser_Spec1 = sub_ser_Spec1;
            }

            public List<String> getSub_ser_display_img() {
                return sub_ser_display_img;
            }

            public void setSub_ser_display_img(List<String> sub_ser_display_img) {
                this.sub_ser_display_img = sub_ser_display_img;
            }
        }

        public static class StatusHistoryTextBean {
            /**
             * title : Booking completed
             * date : Thu Oct 22 2020 10:30:37 GMT+0000 (Coordinated Universal Time)
             */

            private String title;
            private String date;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
