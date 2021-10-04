package com.triton.myvacala.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetCardDetailsResponse {


    /**
     * Status : Success
     * Message : Servicedetails
     * Data : {"Item_Details":[{"sub_ser_Spec1":["Spec-3"],"sub_ser_display_img":["http://3.101.31.129:3000/api/uploads/0758aa10-c0a1-4e2d-aa90-82a1539d0908.jfif"],"ItemList":[{"title":"Inc-3"}],"Vehicle_Name_id":["5f1e79dfa5e13d1c28e45eba"],"_id":"5f30fe6313adb056f0f3ecec","Service_id":"5f1fb861aaa6182470e47cb2","sub_ser_Title":"Test-audi-A2-2","sub_ser_image":"http://3.101.31.129:3000/api/uploads/c4325f95-773d-463f-9335-05350ec3a801.jfif","Original_Price":1300,"Discount_Price":50,"Count_type":true,"Cart_Status":false,"Cart_count":1,"updatedAt":"2020-08-10T07:59:31.709Z","createdAt":"2020-08-10T07:59:31.709Z","__v":0}],"_id":"5f435adbfb29f82408efd749","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_details":[{"Customer_id":"5f1fae9bbcd5650a5ab130e8","Fuel_Type_Background_Color":"#802A2A","Fuel_Type_Name":"Petrol","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Status":"Default","Vehicle_Brand_Name":"AUDI","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicle_Model_Name":"Coupe","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Name":"AUDI A2","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_No":"TN 56 YH 6865","Vehicletype_Name":"Four Wheeler","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Year_of_Manufacture":"1936","__v":0,"_id":"5f212f39c497960c531f1f02","createdAt":"2020-07-29T08:11:37.642Z","updatedAt":"2020-07-29T08:16:05.396Z"}],"Vehicle_type":"Four Wheeler","updatedAt":"2020-08-24T07:57:58.514Z","createdAt":"2020-08-24T06:14:51.265Z","Original_Price":1300,"Discount_Price":50,"__v":0}
     * locationData : {"Customer_Location":{"type":"Point","coordinates":[11.172053079742566,78.59447177499533]},"_id":"5f351168b2c53e69fb341b99","City":"Tiruchirappalli","State":"Tamil Nadu","Country":"","Pincode":"621008","Street":"Thuraiyur - Selipalayam Road","NearBy_LandMark":"Balaji Hospital","Location_NickName":"Balaji Hospital near DK Apartment","Flat_No":"88D","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.172053079742566,"long":78.59447177499533,"Location_type":"Work","Status":"Default","updatedAt":"2020-08-20T11:22:20.767Z","createdAt":"2020-08-13T10:09:44.684Z","__v":0}
     * location_available : [{"Customer_Location":{"type":"Point","coordinates":[11.172053079742566,78.59447177499533]},"_id":"5f351168b2c53e69fb341b99","City":"Tiruchirappalli","State":"Tamil Nadu","Country":"","Pincode":"621008","Street":"Thuraiyur - Selipalayam Road","NearBy_LandMark":"Balaji Hospital","Location_NickName":"Balaji Hospital near DK Apartment","Flat_No":"88D","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.172053079742566,"long":78.59447177499533,"Location_type":"Work","Status":"Default","updatedAt":"2020-08-20T11:22:20.767Z","createdAt":"2020-08-13T10:09:44.684Z","__v":0},{"Customer_Location":{"type":"Point","coordinates":[11.065084252106068,78.63424692302944]},"_id":"5f3e5e0467a706607accf542","City":"Iluppaiyur","State":"Tamil Nadu","Country":"India","Pincode":"621006","Street":"North Street","NearBy_LandMark":"temple","Location_NickName":"dk home","Flat_No":"568","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.065084252106068,"long":78.63424692302944,"Location_type":"Home","Status":"","updatedAt":"2020-08-20T11:27:00.132Z","createdAt":"2020-08-20T11:27:00.132Z","__v":0},{"Customer_Location":{"type":"Point","coordinates":[11.065087871582104,78.63424256443977]},"_id":"5f3e5e5667a706607accf543","City":"Iluppaiyur","State":"Tamil Nadu","Country":"India","Pincode":"621006","Street":"North Street","NearBy_LandMark":"mariyamman temple","Location_NickName":"TEMPLE HOUSE","Flat_No":"755","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.065087871582104,"long":78.63424256443977,"Location_type":"Others","Status":"","updatedAt":"2020-08-20T11:28:22.842Z","createdAt":"2020-08-20T11:28:22.842Z","__v":0}]
     * CustomerVehicleData : [{"_id":"5f212f39c497960c531f1f02","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Brand_Name":"AUDI","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_Name":"AUDI A2","Year_of_Manufacture":"1936","Vehicle_No":"TN 56 YH 6865","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-08-24T07:22:57.131Z","createdAt":"2020-07-29T08:11:37.642Z","__v":0},{"_id":"5f212f95c497960c531f1f03","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e996757d71f3af6be1072","Vehicle_Name":"Pulsar-CT","Year_of_Manufacture":"2020","Vehicle_No":"TN 56 AD 4565","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-07-29T08:16:12.433Z","createdAt":"2020-07-29T08:13:09.438Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private DataBean Data;
    private LocationDataBean locationData;
    private int Code;
    private ArrayList<LocationAvailableBean> location_available;
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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
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

    public ArrayList<LocationAvailableBean> getLocation_available() {
        return location_available;
    }

    public void setLocation_available(ArrayList<LocationAvailableBean> location_available) {
        this.location_available = location_available;
    }

    public List<CustomerVehicleDataBean> getCustomerVehicleData() {
        return CustomerVehicleData;
    }

    public void setCustomerVehicleData(List<CustomerVehicleDataBean> CustomerVehicleData) {
        this.CustomerVehicleData = CustomerVehicleData;
    }

    public static class DataBean {
        /**
         * Item_Details : [{"sub_ser_Spec1":["Spec-3"],"sub_ser_display_img":["http://3.101.31.129:3000/api/uploads/0758aa10-c0a1-4e2d-aa90-82a1539d0908.jfif"],"ItemList":[{"title":"Inc-3"}],"Vehicle_Name_id":["5f1e79dfa5e13d1c28e45eba"],"_id":"5f30fe6313adb056f0f3ecec","Service_id":"5f1fb861aaa6182470e47cb2","sub_ser_Title":"Test-audi-A2-2","sub_ser_image":"http://3.101.31.129:3000/api/uploads/c4325f95-773d-463f-9335-05350ec3a801.jfif","Original_Price":1300,"Discount_Price":50,"Count_type":true,"Cart_Status":false,"Cart_count":1,"updatedAt":"2020-08-10T07:59:31.709Z","createdAt":"2020-08-10T07:59:31.709Z","__v":0}]
         * _id : 5f435adbfb29f82408efd749
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * Vehicle_details : [{"Customer_id":"5f1fae9bbcd5650a5ab130e8","Fuel_Type_Background_Color":"#802A2A","Fuel_Type_Name":"Petrol","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Status":"Default","Vehicle_Brand_Name":"AUDI","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicle_Model_Name":"Coupe","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Name":"AUDI A2","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_No":"TN 56 YH 6865","Vehicletype_Name":"Four Wheeler","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Year_of_Manufacture":"1936","__v":0,"_id":"5f212f39c497960c531f1f02","createdAt":"2020-07-29T08:11:37.642Z","updatedAt":"2020-07-29T08:16:05.396Z"}]
         * Vehicle_type : Four Wheeler
         * updatedAt : 2020-08-24T07:57:58.514Z
         * createdAt : 2020-08-24T06:14:51.265Z
         * Original_Price : 1300
         * Discount_Price : 50
         * __v : 0
         */

        private String _id;
        private String Customer_id;
        private String Vehicle_type;
        private String updatedAt;
        private String createdAt;
        private int Original_Price;
        private int Discount_Price;
        private int __v;
        private List<ItemDetailsBean> Item_Details;
        private List<VehicleDetailsBean> Vehicle_details;

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

        public String getVehicle_type() {
            return Vehicle_type;
        }

        public void setVehicle_type(String Vehicle_type) {
            this.Vehicle_type = Vehicle_type;
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

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public List<ItemDetailsBean> getItem_Details() {
            return Item_Details;
        }

        public void setItem_Details(List<ItemDetailsBean> Item_Details) {
            this.Item_Details = Item_Details;
        }

        public List<VehicleDetailsBean> getVehicle_details() {
            return Vehicle_details;
        }

        public void setVehicle_details(List<VehicleDetailsBean> Vehicle_details) {
            this.Vehicle_details = Vehicle_details;
        }

        public static class ItemDetailsBean {
            /**
             * sub_ser_Spec1 : ["Spec-3"]
             * sub_ser_display_img : ["http://3.101.31.129:3000/api/uploads/0758aa10-c0a1-4e2d-aa90-82a1539d0908.jfif"]
             * ItemList : [{"title":"Inc-3"}]
             * Vehicle_Name_id : ["5f1e79dfa5e13d1c28e45eba"]
             * _id : 5f30fe6313adb056f0f3ecec
             * Service_id : 5f1fb861aaa6182470e47cb2
             * sub_ser_Title : Test-audi-A2-2
             * sub_ser_image : http://3.101.31.129:3000/api/uploads/c4325f95-773d-463f-9335-05350ec3a801.jfif
             * Original_Price : 1300
             * Discount_Price : 50
             * Count_type : true
             * Cart_Status : false
             * Cart_count : 1
             * updatedAt : 2020-08-10T07:59:31.709Z
             * createdAt : 2020-08-10T07:59:31.709Z
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
                 * title : Inc-3
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

        public static class VehicleDetailsBean {
            /**
             * Customer_id : 5f1fae9bbcd5650a5ab130e8
             * Fuel_Type_Background_Color : #802A2A
             * Fuel_Type_Name : Petrol
             * Fuel_Type_id : 5f1e692a9f12a81b3dd70890
             * Status : Default
             * Vehicle_Brand_Name : AUDI
             * Vehicle_Brand_id : 5f1a842467024c39a738e821
             * Vehicle_Image : http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg
             * Vehicle_Model_Name : Coupe
             * Vehicle_Model_id : 5f1e852b63f9882bd6895bea
             * Vehicle_Name : AUDI A2
             * Vehicle_Name_id : 5f1e79dfa5e13d1c28e45eba
             * Vehicle_No : TN 56 YH 6865
             * Vehicletype_Name : Four Wheeler
             * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
             * Year_of_Manufacture : 1936
             * __v : 0
             * _id : 5f212f39c497960c531f1f02
             * createdAt : 2020-07-29T08:11:37.642Z
             * updatedAt : 2020-07-29T08:16:05.396Z
             */

            private String Customer_id;
            private String Fuel_Type_Background_Color;
            private String Fuel_Type_Name;
            private String Fuel_Type_id;
            private String Status;
            private String Vehicle_Brand_Name;
            private String Vehicle_Brand_id;
            private String Vehicle_Image;
            private String Vehicle_Model_Name;
            private String Vehicle_Model_id;
            private String Vehicle_Name;
            private String Vehicle_Name_id;
            private String Vehicle_No;
            private String Vehicletype_Name;
            private String Vehicletype_id;
            private String Year_of_Manufacture;
            private int __v;
            private String _id;
            private String createdAt;
            private String updatedAt;

            public String getCustomer_id() {
                return Customer_id;
            }

            public void setCustomer_id(String Customer_id) {
                this.Customer_id = Customer_id;
            }

            public String getFuel_Type_Background_Color() {
                return Fuel_Type_Background_Color;
            }

            public void setFuel_Type_Background_Color(String Fuel_Type_Background_Color) {
                this.Fuel_Type_Background_Color = Fuel_Type_Background_Color;
            }

            public String getFuel_Type_Name() {
                return Fuel_Type_Name;
            }

            public void setFuel_Type_Name(String Fuel_Type_Name) {
                this.Fuel_Type_Name = Fuel_Type_Name;
            }

            public String getFuel_Type_id() {
                return Fuel_Type_id;
            }

            public void setFuel_Type_id(String Fuel_Type_id) {
                this.Fuel_Type_id = Fuel_Type_id;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getVehicle_Brand_Name() {
                return Vehicle_Brand_Name;
            }

            public void setVehicle_Brand_Name(String Vehicle_Brand_Name) {
                this.Vehicle_Brand_Name = Vehicle_Brand_Name;
            }

            public String getVehicle_Brand_id() {
                return Vehicle_Brand_id;
            }

            public void setVehicle_Brand_id(String Vehicle_Brand_id) {
                this.Vehicle_Brand_id = Vehicle_Brand_id;
            }

            public String getVehicle_Image() {
                return Vehicle_Image;
            }

            public void setVehicle_Image(String Vehicle_Image) {
                this.Vehicle_Image = Vehicle_Image;
            }

            public String getVehicle_Model_Name() {
                return Vehicle_Model_Name;
            }

            public void setVehicle_Model_Name(String Vehicle_Model_Name) {
                this.Vehicle_Model_Name = Vehicle_Model_Name;
            }

            public String getVehicle_Model_id() {
                return Vehicle_Model_id;
            }

            public void setVehicle_Model_id(String Vehicle_Model_id) {
                this.Vehicle_Model_id = Vehicle_Model_id;
            }

            public String getVehicle_Name() {
                return Vehicle_Name;
            }

            public void setVehicle_Name(String Vehicle_Name) {
                this.Vehicle_Name = Vehicle_Name;
            }

            public String getVehicle_Name_id() {
                return Vehicle_Name_id;
            }

            public void setVehicle_Name_id(String Vehicle_Name_id) {
                this.Vehicle_Name_id = Vehicle_Name_id;
            }

            public String getVehicle_No() {
                return Vehicle_No;
            }

            public void setVehicle_No(String Vehicle_No) {
                this.Vehicle_No = Vehicle_No;
            }

            public String getVehicletype_Name() {
                return Vehicletype_Name;
            }

            public void setVehicletype_Name(String Vehicletype_Name) {
                this.Vehicletype_Name = Vehicletype_Name;
            }

            public String getVehicletype_id() {
                return Vehicletype_id;
            }

            public void setVehicletype_id(String Vehicletype_id) {
                this.Vehicletype_id = Vehicletype_id;
            }

            public String getYear_of_Manufacture() {
                return Year_of_Manufacture;
            }

            public void setYear_of_Manufacture(String Year_of_Manufacture) {
                this.Year_of_Manufacture = Year_of_Manufacture;
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

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }
        }
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

    public static class LocationAvailableBean {
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

        private CustomerLocationBeanX Customer_Location;
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

        public CustomerLocationBeanX getCustomer_Location() {
            return Customer_Location;
        }

        public void setCustomer_Location(CustomerLocationBeanX Customer_Location) {
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

        public static class CustomerLocationBeanX {
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

    public static class CustomerVehicleDataBean {
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
         * updatedAt : 2020-08-24T07:22:57.131Z
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