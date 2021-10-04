package com.triton.myvacala.requestpojo;

import java.io.Serializable;
import java.util.List;

public class BookingCreateRequest {

    /**
     * Arrival_Mode : PickUp
     * Booking_Date : 2020-08-21
     * Booking_Time : 0
     * Coupon_Code :
     * Coupon_Code_Amount :
     * Coupon_Code_Percentage :
     * Customer_Address : Iluppaiyur,DK HOME, North Street
     * Customer_Email : iddineshkumar@gmail.com
     * Customer_Name : DINESH
     * Customer_Phone : 6383791451
     * Customer_id : 5f1fae9bbcd5650a5ab130e8
     * Lubricant_type : Petrol
     * Lubricant_type_color : #802A2A
     * Order_Value : 1000
     * Pickup_Date : 2020-08-21
     * Pickup_Time : 22:00 - 23:00
     * Services : Book A Mechanic
     * Subserivces : TYRES
     * Total_Amount : 990
     * Vehicle_Id : 5f0c0cfc2f857d66950cf25f
     * Vehicle_Image : http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg
     * Vehicle_Name : AUDI A2
     * Vehicle_No : TN 56 YH 6865
     * Vehicle_Type : Four Wheeler
     * Year_Of_Mfg : 1936
     * card_details : [{"sub_ser_Spec1":["spec-2"],"sub_ser_display_img":["http://3.101.31.129:3000/api/uploads/d4e5cfe8-6e57-4c5d-81dc-c4fff89043c5.jfif"],"ItemList":[{"title":"iNc-2"}],"Vehicle_Name_id":["5f1e79dfa5e13d1c28e45eba"],"_id":"5f30fcf713adb056f0f3eceb","Service_id":"5f1fb861aaa6182470e47cb2","sub_ser_Title":"Test-audiA2-1","sub_ser_image":"http://3.101.31.129:3000/api/uploads/c6d29660-f8df-4f67-beec-8c33eba946af.jfif","Original_Price":1200,"Discount_Price":200,"Count_type":false,"Cart_Status":false,"Cart_count":1,"updatedAt":"2020-08-10T07:58:18.594Z","createdAt":"2020-08-10T07:53:27.252Z","__v":0},{"sub_ser_Spec1":["spec-4"],"sub_ser_display_img":["http://3.101.31.129:3000/api/uploads/b5f65d66-582d-4bf5-9392-67519d326fb4.jfif"],"ItemList":[{"title":"Inc-4"}],"Vehicle_Name_id":["5f1e79dfa5e13d1c28e45eba"],"_id":"5f30fec913adb056f0f3eced","Service_id":"5f1fb861aaa6182470e47cb2","sub_ser_Title":"Testaudi-A2-4","sub_ser_image":"http://3.101.31.129:3000/api/uploads/fc22d6a5-df77-4123-b664-5b715c631732.jfif","Original_Price":1500,"Discount_Price":300,"Count_type":false,"Cart_Status":false,"Cart_count":1,"updatedAt":"2020-08-10T08:01:13.743Z","createdAt":"2020-08-10T08:01:13.743Z","__v":0}]
     * Transaction_id : qwertyuiop[
     */

    private String Arrival_Mode;
    private String Booking_Date;
    private String Booking_Time;
    private String Coupon_Code;
    private String Coupon_Code_Amount;
    private String Coupon_Code_Percentage;
    private String Customer_Address;
    private String Customer_Email;
    private String Customer_Name;
    private String Customer_Phone;
    private String Customer_id;
    private String Lubricant_type;
    private String Lubricant_type_color;
    private int Order_Value;
    private String Pickup_Date;
    private String Pickup_Time;
    private String Services;
    private String Subserivces;
    private int Total_Amount;
    private String Vehicle_Id;
    private String Vehicle_Image;
    private String Vehicle_Name;
    private String Vehicle_No;
    private String Vehicle_Type;
    private String Year_Of_Mfg;
    private String Transaction_id;
    private String User_issues ;

    public String getUser_issues() {
        return User_issues;
    }

    public void setUser_issues(String user_issues) {
        User_issues = user_issues;
    }

    private List<CardDetailsBean> card_details;

    public String getArrival_Mode() {
        return Arrival_Mode;
    }

    public void setArrival_Mode(String Arrival_Mode) {
        this.Arrival_Mode = Arrival_Mode;
    }

    public String getBooking_Date() {
        return Booking_Date;
    }

    public void setBooking_Date(String Booking_Date) {
        this.Booking_Date = Booking_Date;
    }

    public String getBooking_Time() {
        return Booking_Time;
    }

    public void setBooking_Time(String Booking_Time) {
        this.Booking_Time = Booking_Time;
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

    public String getCoupon_Code_Percentage() {
        return Coupon_Code_Percentage;
    }

    public void setCoupon_Code_Percentage(String Coupon_Code_Percentage) {
        this.Coupon_Code_Percentage = Coupon_Code_Percentage;
    }

    public String getCustomer_Address() {
        return Customer_Address;
    }

    public void setCustomer_Address(String Customer_Address) {
        this.Customer_Address = Customer_Address;
    }

    public String getCustomer_Email() {
        return Customer_Email;
    }

    public void setCustomer_Email(String Customer_Email) {
        this.Customer_Email = Customer_Email;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String Customer_Name) {
        this.Customer_Name = Customer_Name;
    }

    public String getCustomer_Phone() {
        return Customer_Phone;
    }

    public void setCustomer_Phone(String Customer_Phone) {
        this.Customer_Phone = Customer_Phone;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String Customer_id) {
        this.Customer_id = Customer_id;
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

    public int getOrder_Value() {
        return Order_Value;
    }

    public void setOrder_Value(int Order_Value) {
        this.Order_Value = Order_Value;
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

    public int getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(int Total_Amount) {
        this.Total_Amount = Total_Amount;
    }

    public String getVehicle_Id() {
        return Vehicle_Id;
    }

    public void setVehicle_Id(String Vehicle_Id) {
        this.Vehicle_Id = Vehicle_Id;
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

    public String getVehicle_No() {
        return Vehicle_No;
    }

    public void setVehicle_No(String Vehicle_No) {
        this.Vehicle_No = Vehicle_No;
    }

    public String getVehicle_Type() {
        return Vehicle_Type;
    }

    public void setVehicle_Type(String Vehicle_Type) {
        this.Vehicle_Type = Vehicle_Type;
    }

    public String getYear_Of_Mfg() {
        return Year_Of_Mfg;
    }

    public void setYear_Of_Mfg(String Year_Of_Mfg) {
        this.Year_Of_Mfg = Year_Of_Mfg;
    }

    public String getTransaction_id() {
        return Transaction_id;
    }

    public void setTransaction_id(String Transaction_id) {
        this.Transaction_id = Transaction_id;
    }

    public List<CardDetailsBean> getCard_details() {
        return card_details;
    }

    public void setCard_details(List<CardDetailsBean> card_details) {
        this.card_details = card_details;
    }

    public static class CardDetailsBean implements Serializable {
        /**
         * sub_ser_Spec1 : ["spec-2"]
         * sub_ser_display_img : ["http://3.101.31.129:3000/api/uploads/d4e5cfe8-6e57-4c5d-81dc-c4fff89043c5.jfif"]
         * ItemList : [{"title":"iNc-2"}]
         * Vehicle_Name_id : ["5f1e79dfa5e13d1c28e45eba"]
         * _id : 5f30fcf713adb056f0f3eceb
         * Service_id : 5f1fb861aaa6182470e47cb2
         * sub_ser_Title : Test-audiA2-1
         * sub_ser_image : http://3.101.31.129:3000/api/uploads/c6d29660-f8df-4f67-beec-8c33eba946af.jfif
         * Original_Price : 1200
         * Discount_Price : 200
         * Count_type : false
         * Cart_Status : false
         * Cart_count : 1
         * updatedAt : 2020-08-10T07:58:18.594Z
         * createdAt : 2020-08-10T07:53:27.252Z
         * __v : 0
         */

        private String _id;
        private String Service_id;
        private String sub_ser_Title;
        private String sub_ser_image;
        private int Original_Price;
        private int Discount_Price;
        private boolean Count_type;
        private boolean Cart_Status;
        private int Cart_count;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<String> sub_ser_Spec1;
        private List<String> sub_ser_display_img;
        private List<ItemListBean> ItemList;
        private List<String> Vehicle_Name_id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getService_id() {
            return Service_id;
        }

        public void setService_id(String Service_id) {
            this.Service_id = Service_id;
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

        public int getOriginal_Price() {
            return Original_Price;
        }

        public void setOriginal_Price(int Original_Price) {
            this.Original_Price = Original_Price;
        }

        public int getDiscount_Price() {
            return Discount_Price;
        }

        public void setDiscount_Price(int Discount_Price) {
            this.Discount_Price = Discount_Price;
        }

        public boolean isCount_type() {
            return Count_type;
        }

        public void setCount_type(boolean Count_type) {
            this.Count_type = Count_type;
        }

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

        public List<ItemListBean> getItemList() {
            return ItemList;
        }

        public void setItemList(List<ItemListBean> ItemList) {
            this.ItemList = ItemList;
        }

        public List<String> getVehicle_Name_id() {
            return Vehicle_Name_id;
        }

        public void setVehicle_Name_id(List<String> Vehicle_Name_id) {
            this.Vehicle_Name_id = Vehicle_Name_id;
        }

        public static class ItemListBean {
            /**
             * title : iNc-2
             */

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
