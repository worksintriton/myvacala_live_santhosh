package com.triton.myvacala.responsepojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MasterServiceGetlistResponse {

    /**
     * Status : Success
     * Message : Dashboard Data
     * MasterserviceList : [{"Serviceavailable_Location":["Begusarai","Iluppaiyur","Chennai"],"_id":"5f1ac41b55abee4870516567","Masterservice_Name":"Book A Mechanic","Service_Image":"http://3.101.31.129:3000/api/uploads/mechanic.jpg","Desc":"One of the master services","updatedAt":"2020-07-28T14:42:49.322Z","createdAt":"2020-07-24T11:20:59.656Z","__v":0},{"Serviceavailable_Location":["Begusarai","Iluppaiyur","Chennai"],"_id":"5f1ea10d89b6693decfabf09","Masterservice_Name":"Book  a Parking","Service_Image":"http://3.101.31.129:3000/api/uploads/264d28df-9db0-416f-bc31-d138587ccea5.jpg","Desc":"Parking Data","updatedAt":"2020-07-27T09:40:29.399Z","createdAt":"2020-07-27T09:40:29.399Z","__v":0},{"Serviceavailable_Location":["Begusarai","Iluppaiyur","Chennai","Begusarai","Iluppaiyur","Chennai"],"_id":"5f1ea18289b6693decfabf0a","Masterservice_Name":"Book a Driver","Service_Image":"http://3.101.31.129:3000/api/uploads/a052bcc2-e00e-4df7-9061-65663baa6ff7.png","Desc":"Driver Booking","updatedAt":"2020-07-27T09:42:26.751Z","createdAt":"2020-07-27T09:42:26.751Z","__v":0}]
     * LocationDetails : {"Customer_Location":{"type":"Point","coordinates":[11.0650673,78.6342302]},"_id":"5f1faf04bcd5650a5ab130ea","City":"Iluppaiyur","State":"Tamil Nadu","Country":"India","Pincode":"621006","Street":"North Street","NearBy_LandMark":"temple","Location_NickName":"DK HOME","Flat_No":"576A","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.0650673,"long":78.6342302,"Location_type":"Work","Status":"Default","updatedAt":"2020-07-31T07:58:16.036Z","createdAt":"2020-07-28T04:52:20.486Z","__v":0}
     * HomeBannerList : [{"_id":"5f0d865de563477c390bd1ba","Homebanner_Image":"http://3.101.31.129:3000/api/uploads/banner3.jpg","Title":"Book a car","Status":"Show","Desc":"Book a car","updatedAt":"2020-08-06T06:56:42.765Z","createdAt":"2020-07-14T10:18:05.110Z","__v":0},{"_id":"5f0d8923e563477c390bd1bc","Homebanner_Image":"http://3.101.31.129:3000/api/uploads/banner2.png","Title":"Book a ride","Status":"Show","Desc":"Book a ride","updatedAt":"2020-08-06T06:56:39.366Z","createdAt":"2020-07-14T10:29:55.097Z","__v":0},{"_id":"5f0d894ae563477c390bd1bd","Homebanner_Image":"http://3.101.31.129:3000/api/uploads/banner.jpg","Title":"Book a car ride","Status":"Show","Desc":"Book a car ride","updatedAt":"2020-08-06T06:53:00.230Z","createdAt":"2020-07-14T10:30:34.615Z","__v":0},{"_id":"5f210f0a34f72d6fed392384","Homebanner_Image":"http://3.101.31.129:3000/api/uploads/f046969f-d04e-4ed4-b04b-20cd7fc28efd.jpg","Title":"Summer Discount Offers","Status":"Show","Desc":"For the summer offers","updatedAt":"2020-08-06T06:53:03.745Z","createdAt":"2020-07-29T05:54:18.720Z","__v":0}]
     * VehicletypeDetails : [{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},{"_id":"5f0c0d092f857d66950cf260","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-13T07:28:09.903Z","createdAt":"2020-07-13T07:28:09.903Z","__v":0}]
     * CustomerVehicleData : [{"_id":"5f212f39c497960c531f1f02","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Brand_Name":"AUDI","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_Name":"AUDI A2","Year_of_Manufacture":"1936","Vehicle_No":"TN 56 YH 6865","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-07-29T08:16:05.396Z","createdAt":"2020-07-29T08:11:37.642Z","__v":0},{"_id":"5f212f95c497960c531f1f03","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e996757d71f3af6be1072","Vehicle_Name":"Pulsar-CT","Year_of_Manufacture":"2020","Vehicle_No":"TN 56 AD 4565","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-07-29T08:16:12.433Z","createdAt":"2020-07-29T08:13:09.438Z","__v":0}]
     * Mobile_details : [{"mobileappdetails":[{"Email":"mohammedimthi2395@gmail.com","phone_number":"9514497862","Android_share_link":"https://play.google.com/store","Ios_share_link":"https://play.google.com/store"}],"_id":"5f2b9cd1476398537399bb1f","updatedAt":"2020-08-06T06:01:53.072Z","createdAt":"2020-08-06T06:01:53.072Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String salt_key;
    private String merchant_key;
    private boolean isproduction;

    public String getSalt_key() {
        return salt_key;
    }

    public void setSalt_key(String salt_key) {
        this.salt_key = salt_key;
    }

    public String getMerchant_key() {
        return merchant_key;
    }

    public void setMerchant_key(String merchant_key) {
        this.merchant_key = merchant_key;
    }

    public boolean isIsproduction() {
        return isproduction;
    }

    public void setIsproduction(boolean isproduction) {
        this.isproduction = isproduction;
    }

    private String Message;
    private LocationDetailsBean LocationDetails;
    private int Code;
    private List<MasterserviceListBean> MasterserviceList;
    private List<HomeBannerListBean> HomeBannerList;
    private List<VehicletypeDetailsBean> VehicletypeDetails;
    private ArrayList<CustomerVehicleDataBean> CustomerVehicleData;
    private List<MobileDetailsBean> Mobile_details;

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

    public LocationDetailsBean getLocationDetails() {
        return LocationDetails;
    }

    public void setLocationDetails(LocationDetailsBean LocationDetails) {
        this.LocationDetails = LocationDetails;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<MasterserviceListBean> getMasterserviceList() {
        return MasterserviceList;
    }

    public void setMasterserviceList(List<MasterserviceListBean> MasterserviceList) {
        this.MasterserviceList = MasterserviceList;
    }

    public List<HomeBannerListBean> getHomeBannerList() {
        return HomeBannerList;
    }

    public void setHomeBannerList(List<HomeBannerListBean> HomeBannerList) {
        this.HomeBannerList = HomeBannerList;
    }

    public List<VehicletypeDetailsBean> getVehicletypeDetails() {
        return VehicletypeDetails;
    }

    public void setVehicletypeDetails(List<VehicletypeDetailsBean> VehicletypeDetails) {
        this.VehicletypeDetails = VehicletypeDetails;
    }

    public ArrayList<CustomerVehicleDataBean> getCustomerVehicleData() {
        return CustomerVehicleData;
    }

    public void setCustomerVehicleData(ArrayList<CustomerVehicleDataBean> CustomerVehicleData) {
        this.CustomerVehicleData = CustomerVehicleData;
    }

    public List<MobileDetailsBean> getMobile_details() {
        return Mobile_details;
    }

    public void setMobile_details(List<MobileDetailsBean> Mobile_details) {
        this.Mobile_details = Mobile_details;
    }

    public static class LocationDetailsBean {
        /**
         * Customer_Location : {"type":"Point","coordinates":[11.0650673,78.6342302]}
         * _id : 5f1faf04bcd5650a5ab130ea
         * City : Iluppaiyur
         * State : Tamil Nadu
         * Country : India
         * Pincode : 621006
         * Street : North Street
         * NearBy_LandMark : temple
         * Location_NickName : DK HOME
         * Flat_No : 576A
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * lat : 11.0650673
         * long : 78.6342302
         * Location_type : Work
         * Status : Default
         * updatedAt : 2020-07-31T07:58:16.036Z
         * createdAt : 2020-07-28T04:52:20.486Z
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
             * coordinates : [11.0650673,78.6342302]
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

    public static class MasterserviceListBean {
        /**
         * Serviceavailable_Location : ["Begusarai","Iluppaiyur","Chennai"]
         * _id : 5f1ac41b55abee4870516567
         * Masterservice_Name : Book A Mechanic
         * Service_Image : http://3.101.31.129:3000/api/uploads/mechanic.jpg
         * Desc : One of the master services
         * updatedAt : 2020-07-28T14:42:49.322Z
         * createdAt : 2020-07-24T11:20:59.656Z
         * __v : 0
         */

        private String _id;
        private String Masterservice_Name;
        private String Service_Image;
        private String Desc;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<String> Serviceavailable_Location;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getMasterservice_Name() {
            return Masterservice_Name;
        }

        public void setMasterservice_Name(String Masterservice_Name) {
            this.Masterservice_Name = Masterservice_Name;
        }

        public String getService_Image() {
            return Service_Image;
        }

        public void setService_Image(String Service_Image) {
            this.Service_Image = Service_Image;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String Desc) {
            this.Desc = Desc;
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

        public List<String> getServiceavailable_Location() {
            return Serviceavailable_Location;
        }

        public void setServiceavailable_Location(List<String> Serviceavailable_Location) {
            this.Serviceavailable_Location = Serviceavailable_Location;
        }
    }

    public static class HomeBannerListBean {
        /**
         * _id : 5f0d865de563477c390bd1ba
         * Homebanner_Image : http://3.101.31.129:3000/api/uploads/banner3.jpg
         * Title : Book a car
         * Status : Show
         * Desc : Book a car
         * updatedAt : 2020-08-06T06:56:42.765Z
         * createdAt : 2020-07-14T10:18:05.110Z
         * __v : 0
         */

        private String _id;
        private String Homebanner_Image;
        private String Title;
        private String Status;
        private String Desc;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getHomebanner_Image() {
            return Homebanner_Image;
        }

        public void setHomebanner_Image(String Homebanner_Image) {
            this.Homebanner_Image = Homebanner_Image;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String Desc) {
            this.Desc = Desc;
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

    public static class CustomerVehicleDataBean implements Parcelable {
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

        protected CustomerVehicleDataBean(Parcel in) {
            _id = in.readString();
            Customer_id = in.readString();
            Vehicle_Image = in.readString();
            Vehicletype_id = in.readString();
            Vehicletype_Name = in.readString();
            Vehicle_Brand_id = in.readString();
            Vehicle_Brand_Name = in.readString();
            Vehicle_Name_id = in.readString();
            Vehicle_Name = in.readString();
            Year_of_Manufacture = in.readString();
            Vehicle_No = in.readString();
            Fuel_Type_id = in.readString();
            Fuel_Type_Name = in.readString();
            Fuel_Type_Background_Color = in.readString();
            Vehicle_Model_id = in.readString();
            Vehicle_Model_Name = in.readString();
            Status = in.readString();
            updatedAt = in.readString();
            createdAt = in.readString();
            __v = in.readInt();
        }

        public static final Creator<CustomerVehicleDataBean> CREATOR = new Creator<CustomerVehicleDataBean>() {
            @Override
            public CustomerVehicleDataBean createFromParcel(Parcel in) {
                return new CustomerVehicleDataBean(in);
            }

            @Override
            public CustomerVehicleDataBean[] newArray(int size) {
                return new CustomerVehicleDataBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(_id);
            dest.writeString(Customer_id);
            dest.writeString(Vehicle_Image);
            dest.writeString(Vehicletype_id);
            dest.writeString(Vehicletype_Name);
            dest.writeString(Vehicle_Brand_id);
            dest.writeString(Vehicle_Brand_Name);
            dest.writeString(Vehicle_Name_id);
            dest.writeString(Vehicle_Name);
            dest.writeString(Year_of_Manufacture);
            dest.writeString(Vehicle_No);
            dest.writeString(Fuel_Type_id);
            dest.writeString(Fuel_Type_Name);
            dest.writeString(Fuel_Type_Background_Color);
            dest.writeString(Vehicle_Model_id);
            dest.writeString(Vehicle_Model_Name);
            dest.writeString(Status);
            dest.writeString(updatedAt);
            dest.writeString(createdAt);
            dest.writeInt(__v);
        }
    }

    public static class MobileDetailsBean {
        /**
         * mobileappdetails : [{"Email":"mohammedimthi2395@gmail.com","phone_number":"9514497862","Android_share_link":"https://play.google.com/store","Ios_share_link":"https://play.google.com/store"}]
         * _id : 5f2b9cd1476398537399bb1f
         * updatedAt : 2020-08-06T06:01:53.072Z
         * createdAt : 2020-08-06T06:01:53.072Z
         * __v : 0
         */

        private String _id;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<MobileappdetailsBean> mobileappdetails;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public List<MobileappdetailsBean> getMobileappdetails() {
            return mobileappdetails;
        }

        public void setMobileappdetails(List<MobileappdetailsBean> mobileappdetails) {
            this.mobileappdetails = mobileappdetails;
        }

        public static class MobileappdetailsBean {
            /**
             * Email : mohammedimthi2395@gmail.com
             * phone_number : 9514497862
             * Android_share_link : https://play.google.com/store
             * Ios_share_link : https://play.google.com/store
             */

            private String Email;
            private String phone_number;
            private String Android_share_link;
            private String Ios_share_link;

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getAndroid_share_link() {
                return Android_share_link;
            }

            public void setAndroid_share_link(String Android_share_link) {
                this.Android_share_link = Android_share_link;
            }

            public String getIos_share_link() {
                return Ios_share_link;
            }

            public void setIos_share_link(String Ios_share_link) {
                this.Ios_share_link = Ios_share_link;
            }
        }
    }
}
