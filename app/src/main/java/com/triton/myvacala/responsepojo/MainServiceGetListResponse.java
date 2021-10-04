package com.triton.myvacala.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainServiceGetListResponse {


    /**
     * Status : Success
     * Message : Servicedetails
     * Data : {"mainserviceslist":[{"_id":"5f1acd2f9e65c148f3554db4","Service_Name":"Batteries","Service_Image":"http://3.101.31.129:3000/api/uploads/image.jpg","Desc":"Data1","Popular":true,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-04T08:37:25.348Z","createdAt":"2020-07-24T11:59:43.844Z","__v":0},{"_id":"5f1fb861aaa6182470e47cb2","Service_Name":"Scheduled Services","Service_Image":"http://3.101.31.129:3000/api/uploads/47c92178-4072-4c77-bd05-4da2af4f22d0.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:08:23.384Z","createdAt":"2020-07-28T05:32:17.941Z","__v":0},{"_id":"5f1fbfd9a413cc413e07fc6a","Service_Name":"Ac Service","Service_Image":"http://3.101.31.129:3000/api/uploads/e6f4b10a-6f46-4155-8dd6-5fa290b97107.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:08:55.360Z","createdAt":"2020-07-28T06:04:09.627Z","__v":0},{"_id":"5f1fbff3cfb5c741551e178f","Service_Name":"Tyres & Wheel Care","Service_Image":"http://3.101.31.129:3000/api/uploads/05013994-d91a-407e-9247-8cbabdeb60fc.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:10:02.644Z","createdAt":"2020-07-28T06:04:35.017Z","__v":0},{"_id":"5f1fc7b500937545906e59f5","Service_Name":"Cleaning & Detailing","Service_Image":"http://3.101.31.129:3000/api/uploads/804d5ef2-d9f6-4feb-aafb-8d79b24b6144.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:10:54.869Z","createdAt":"2020-07-28T06:37:41.697Z","__v":0},{"_id":"5f22d6db7c4ba02c1d300066","Service_Name":"Ac Services","Service_Image":"http://3.101.31.129:3000/api/uploads/0b2ec400-d2e7-4633-9292-ca35447e7ab4.png","Desc":"Ac service needed","Popular":true,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:19:07.035Z","createdAt":"2020-07-30T14:19:07.035Z","__v":0},{"_id":"5f22d9ab7c4ba02c1d30006a","Service_Name":"Car service","Service_Image":"http://3.101.31.129:3000/api/uploads/7f505e65-183a-4722-92db-31cc326295b1.jpg","Desc":"Complete car service","Popular":true,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:31:07.841Z","createdAt":"2020-07-30T14:31:07.841Z","__v":0},{"_id":"5f2bc03dd483cb615a892026","Service_Name":"Insurance Claims","Service_Image":"http://3.101.31.129:3000/api/uploads/f85b618b-6746-4bf9-80d1-95d7636592e1.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:12:53.286Z","createdAt":"2020-08-06T08:33:02.002Z","__v":0},{"_id":"5f33f909c40f8603dacced33","Service_Name":"Custom Services","Service_Image":"http://3.101.31.129:3000/api/uploads/b9ffda0d-caa5-4fbf-891b-e13740edf751.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-13T17:15:08.171Z","createdAt":"2020-08-12T14:13:29.367Z","__v":0},{"_id":"5f3a589dedbde6223ae8abe3","Service_Name":"Test main service 1","Service_Image":"http://3.101.31.129:3000/api/uploads/935b78ca-5741-4fef-b2d6-b1f9727871bb.jpeg","Desc":"Test main services 123","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-17T10:15:40.528Z","createdAt":"2020-08-17T10:14:53.588Z","__v":0}],"Bannerlist":[{"_id":"5f1fcc4100937545906e59f9","ServiceBanner_Image":"http://3.101.31.129:3000/api/uploads/e2eab949-c33c-466e-a771-f442bf8c70ef.png","Master_Service_id":"5f1ac41b55abee4870516567","Title":"Myvacala Services","Status":"Show","Desc":"Book a service for your vehicle","updatedAt":"2020-08-12T14:46:29.430Z","createdAt":"2020-07-28T06:57:05.522Z","__v":0},{"_id":"5f1fd00ec5ed5548a2e7bc1b","ServiceBanner_Image":"http://3.101.31.129:3000/api/uploads/d8573592-988f-437a-bef8-d60b921db264.png","Master_Service_id":"5f1ac41b55abee4870516567","Title":"Myvacala Discounts","Status":"Show","Desc":"Book a service for your vehicle","updatedAt":"2020-08-12T14:46:42.327Z","createdAt":"2020-07-28T07:13:18.077Z","__v":0},{"_id":"5f1fd023c5ed5548a2e7bc1c","ServiceBanner_Image":"http://3.101.31.129:3000/api/uploads/2868d552-4938-4684-a13b-c66d5c37dbf9.png","Master_Service_id":"5f1ac41b55abee4870516567","Title":"Myvacala Discounts","Status":"Show","Desc":"Book a service for your vehicle","updatedAt":"2020-08-12T14:46:53.901Z","createdAt":"2020-07-28T07:13:39.691Z","__v":0}],"popularservice":[{"_id":"5f1acd2f9e65c148f3554db4","Service_Name":"Batteries","Service_Image":"http://3.101.31.129:3000/api/uploads/image.jpg","Desc":"Data1","Popular":true,"Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-04T08:37:25.348Z","createdAt":"2020-07-24T11:59:43.844Z","__v":0},{"_id":"5f1fd781c5ed5548a2e7bc21","Service_Name":"Brake cleaning","Service_Image":"http://3.101.31.129:3000/api/uploads/image.jpg","Desc":"Datas","Popular":true,"Vehicle_Type_id":"5f0c0d092f857d66950cf260","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-28T11:24:57.578Z","createdAt":"2020-07-28T07:45:05.325Z","__v":0},{"_id":"5f22d6db7c4ba02c1d300066","Service_Name":"Ac Services","Service_Image":"http://3.101.31.129:3000/api/uploads/0b2ec400-d2e7-4633-9292-ca35447e7ab4.png","Desc":"Ac service needed","Popular":true,"Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:19:07.035Z","createdAt":"2020-07-30T14:19:07.035Z","__v":0},{"_id":"5f22d9ab7c4ba02c1d30006a","Service_Name":"Car service","Service_Image":"http://3.101.31.129:3000/api/uploads/7f505e65-183a-4722-92db-31cc326295b1.jpg","Desc":"Complete car service","Popular":true,"Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:31:07.841Z","createdAt":"2020-07-30T14:31:07.841Z","__v":0},{"_id":"5f22daee7c4ba02c1d30006c","Service_Name":"Bike Engine service","Service_Image":"http://3.101.31.129:3000/api/uploads/e7643615-1577-464f-b0dc-358d2f18e301.jpg","Desc":"Bike Engine service","Popular":true,"Vehicle_Type_id":"5f0c0d092f857d66950cf260","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:36:30.511Z","createdAt":"2020-07-30T14:36:30.511Z","__v":0}],"CustomerVehicleData":[{"_id":"5f212f39c497960c531f1f02","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Brand_Name":"AUDI","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_Name":"AUDI A2","Year_of_Manufacture":"1936","Vehicle_No":"TN 56 YH 6865","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-07-29T08:16:05.396Z","createdAt":"2020-07-29T08:11:37.642Z","__v":0},{"_id":"5f212f95c497960c531f1f03","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e996757d71f3af6be1072","Vehicle_Name":"Pulsar-CT","Year_of_Manufacture":"2020","Vehicle_No":"TN 56 AD 4565","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-07-29T08:16:12.433Z","createdAt":"2020-07-29T08:13:09.438Z","__v":0}],"locationData":{"Customer_Location":{"type":"Point","coordinates":[11.172053079742566,78.59447177499533]},"_id":"5f351168b2c53e69fb341b99","City":"Tiruchirappalli","State":"Tamil Nadu","Country":"","Pincode":"621008","Street":"Thuraiyur - Selipalayam Road","NearBy_LandMark":"Balaji Hospital","Location_NickName":"Balaji Hospital near DK Apartment","Flat_No":"88D","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.172053079742566,"long":78.59447177499533,"Location_type":"Work","Status":"Default","updatedAt":"2020-08-13T10:39:24.831Z","createdAt":"2020-08-13T10:09:44.684Z","__v":0}}
     * Code : 200
     */

    private String Status;
    private String Message;
    private DataBean Data;
    private int Code;
    private int cart;
    private String alert_msg;

    public String getAlert_msg() {
        return alert_msg;
    }

    public void setAlert_msg(String alert_msg) {
        this.alert_msg = alert_msg;
    }

    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

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
         * mainserviceslist : [{"_id":"5f1acd2f9e65c148f3554db4","Service_Name":"Batteries","Service_Image":"http://3.101.31.129:3000/api/uploads/image.jpg","Desc":"Data1","Popular":true,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-04T08:37:25.348Z","createdAt":"2020-07-24T11:59:43.844Z","__v":0},{"_id":"5f1fb861aaa6182470e47cb2","Service_Name":"Scheduled Services","Service_Image":"http://3.101.31.129:3000/api/uploads/47c92178-4072-4c77-bd05-4da2af4f22d0.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:08:23.384Z","createdAt":"2020-07-28T05:32:17.941Z","__v":0},{"_id":"5f1fbfd9a413cc413e07fc6a","Service_Name":"Ac Service","Service_Image":"http://3.101.31.129:3000/api/uploads/e6f4b10a-6f46-4155-8dd6-5fa290b97107.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:08:55.360Z","createdAt":"2020-07-28T06:04:09.627Z","__v":0},{"_id":"5f1fbff3cfb5c741551e178f","Service_Name":"Tyres & Wheel Care","Service_Image":"http://3.101.31.129:3000/api/uploads/05013994-d91a-407e-9247-8cbabdeb60fc.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:10:02.644Z","createdAt":"2020-07-28T06:04:35.017Z","__v":0},{"_id":"5f1fc7b500937545906e59f5","Service_Name":"Cleaning & Detailing","Service_Image":"http://3.101.31.129:3000/api/uploads/804d5ef2-d9f6-4feb-aafb-8d79b24b6144.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:10:54.869Z","createdAt":"2020-07-28T06:37:41.697Z","__v":0},{"_id":"5f22d6db7c4ba02c1d300066","Service_Name":"Ac Services","Service_Image":"http://3.101.31.129:3000/api/uploads/0b2ec400-d2e7-4633-9292-ca35447e7ab4.png","Desc":"Ac service needed","Popular":true,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:19:07.035Z","createdAt":"2020-07-30T14:19:07.035Z","__v":0},{"_id":"5f22d9ab7c4ba02c1d30006a","Service_Name":"Car service","Service_Image":"http://3.101.31.129:3000/api/uploads/7f505e65-183a-4722-92db-31cc326295b1.jpg","Desc":"Complete car service","Popular":true,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:31:07.841Z","createdAt":"2020-07-30T14:31:07.841Z","__v":0},{"_id":"5f2bc03dd483cb615a892026","Service_Name":"Insurance Claims","Service_Image":"http://3.101.31.129:3000/api/uploads/f85b618b-6746-4bf9-80d1-95d7636592e1.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-12T14:12:53.286Z","createdAt":"2020-08-06T08:33:02.002Z","__v":0},{"_id":"5f33f909c40f8603dacced33","Service_Name":"Custom Services","Service_Image":"http://3.101.31.129:3000/api/uploads/b9ffda0d-caa5-4fbf-891b-e13740edf751.png","Desc":"Data","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-13T17:15:08.171Z","createdAt":"2020-08-12T14:13:29.367Z","__v":0},{"_id":"5f3a589dedbde6223ae8abe3","Service_Name":"Test main service 1","Service_Image":"http://3.101.31.129:3000/api/uploads/935b78ca-5741-4fef-b2d6-b1f9727871bb.jpeg","Desc":"Test main services 123","Popular":false,"Vehicle_Type_id":{"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0},"Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-17T10:15:40.528Z","createdAt":"2020-08-17T10:14:53.588Z","__v":0}]
         * Bannerlist : [{"_id":"5f1fcc4100937545906e59f9","ServiceBanner_Image":"http://3.101.31.129:3000/api/uploads/e2eab949-c33c-466e-a771-f442bf8c70ef.png","Master_Service_id":"5f1ac41b55abee4870516567","Title":"Myvacala Services","Status":"Show","Desc":"Book a service for your vehicle","updatedAt":"2020-08-12T14:46:29.430Z","createdAt":"2020-07-28T06:57:05.522Z","__v":0},{"_id":"5f1fd00ec5ed5548a2e7bc1b","ServiceBanner_Image":"http://3.101.31.129:3000/api/uploads/d8573592-988f-437a-bef8-d60b921db264.png","Master_Service_id":"5f1ac41b55abee4870516567","Title":"Myvacala Discounts","Status":"Show","Desc":"Book a service for your vehicle","updatedAt":"2020-08-12T14:46:42.327Z","createdAt":"2020-07-28T07:13:18.077Z","__v":0},{"_id":"5f1fd023c5ed5548a2e7bc1c","ServiceBanner_Image":"http://3.101.31.129:3000/api/uploads/2868d552-4938-4684-a13b-c66d5c37dbf9.png","Master_Service_id":"5f1ac41b55abee4870516567","Title":"Myvacala Discounts","Status":"Show","Desc":"Book a service for your vehicle","updatedAt":"2020-08-12T14:46:53.901Z","createdAt":"2020-07-28T07:13:39.691Z","__v":0}]
         * popularservice : [{"_id":"5f1acd2f9e65c148f3554db4","Service_Name":"Batteries","Service_Image":"http://3.101.31.129:3000/api/uploads/image.jpg","Desc":"Data1","Popular":true,"Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-08-04T08:37:25.348Z","createdAt":"2020-07-24T11:59:43.844Z","__v":0},{"_id":"5f1fd781c5ed5548a2e7bc21","Service_Name":"Brake cleaning","Service_Image":"http://3.101.31.129:3000/api/uploads/image.jpg","Desc":"Datas","Popular":true,"Vehicle_Type_id":"5f0c0d092f857d66950cf260","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-28T11:24:57.578Z","createdAt":"2020-07-28T07:45:05.325Z","__v":0},{"_id":"5f22d6db7c4ba02c1d300066","Service_Name":"Ac Services","Service_Image":"http://3.101.31.129:3000/api/uploads/0b2ec400-d2e7-4633-9292-ca35447e7ab4.png","Desc":"Ac service needed","Popular":true,"Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:19:07.035Z","createdAt":"2020-07-30T14:19:07.035Z","__v":0},{"_id":"5f22d9ab7c4ba02c1d30006a","Service_Name":"Car service","Service_Image":"http://3.101.31.129:3000/api/uploads/7f505e65-183a-4722-92db-31cc326295b1.jpg","Desc":"Complete car service","Popular":true,"Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:31:07.841Z","createdAt":"2020-07-30T14:31:07.841Z","__v":0},{"_id":"5f22daee7c4ba02c1d30006c","Service_Name":"Bike Engine service","Service_Image":"http://3.101.31.129:3000/api/uploads/e7643615-1577-464f-b0dc-358d2f18e301.jpg","Desc":"Bike Engine service","Popular":true,"Vehicle_Type_id":"5f0c0d092f857d66950cf260","Master_Service_id":"5f1ac41b55abee4870516567","updatedAt":"2020-07-30T14:36:30.511Z","createdAt":"2020-07-30T14:36:30.511Z","__v":0}]
         * CustomerVehicleData : [{"_id":"5f212f39c497960c531f1f02","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/c1582f0c-3307-45b7-8c6c-5f5cf85d50d0.jpg","Vehicletype_id":"5f0c0cfc2f857d66950cf25f","Vehicletype_Name":"Four Wheeler","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Brand_Name":"AUDI","Vehicle_Name_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_Name":"AUDI A2","Year_of_Manufacture":"1936","Vehicle_No":"TN 56 YH 6865","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1e852b63f9882bd6895bea","Vehicle_Model_Name":"Coupe","Status":"Default","updatedAt":"2020-07-29T08:16:05.396Z","createdAt":"2020-07-29T08:11:37.642Z","__v":0},{"_id":"5f212f95c497960c531f1f03","Customer_id":"5f1fae9bbcd5650a5ab130e8","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicletype_id":"5f0c0d092f857d66950cf260","Vehicletype_Name":"Two Wheeler","Vehicle_Brand_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Brand_Name":"Bajaj","Vehicle_Name_id":"5f1e996757d71f3af6be1072","Vehicle_Name":"Pulsar-CT","Year_of_Manufacture":"2020","Vehicle_No":"TN 56 AD 4565","Fuel_Type_id":"5f1e692a9f12a81b3dd70890","Fuel_Type_Name":"Petrol","Fuel_Type_Background_Color":"#802A2A","Vehicle_Model_id":"5f1af089de7bf45b602f8bb3","Vehicle_Model_Name":"Standard","Status":"Default","updatedAt":"2020-07-29T08:16:12.433Z","createdAt":"2020-07-29T08:13:09.438Z","__v":0}]
         * locationData : {"Customer_Location":{"type":"Point","coordinates":[11.172053079742566,78.59447177499533]},"_id":"5f351168b2c53e69fb341b99","City":"Tiruchirappalli","State":"Tamil Nadu","Country":"","Pincode":"621008","Street":"Thuraiyur - Selipalayam Road","NearBy_LandMark":"Balaji Hospital","Location_NickName":"Balaji Hospital near DK Apartment","Flat_No":"88D","Customer_id":"5f1fae9bbcd5650a5ab130e8","lat":11.172053079742566,"long":78.59447177499533,"Location_type":"Work","Status":"Default","updatedAt":"2020-08-13T10:39:24.831Z","createdAt":"2020-08-13T10:09:44.684Z","__v":0}
         */

        private LocationDataBean locationData;
        private List<MainserviceslistBean> mainserviceslist;
        private List<BannerlistBean> Bannerlist;
        private List<PopularserviceBean> popularservice;
        private ArrayList<CustomerVehicleDataBean> CustomerVehicleData;

        public LocationDataBean getLocationData() {
            return locationData;
        }

        public void setLocationData(LocationDataBean locationData) {
            this.locationData = locationData;
        }

        public List<MainserviceslistBean> getMainserviceslist() {
            return mainserviceslist;
        }

        public void setMainserviceslist(List<MainserviceslistBean> mainserviceslist) {
            this.mainserviceslist = mainserviceslist;
        }

        public List<BannerlistBean> getBannerlist() {
            return Bannerlist;
        }

        public void setBannerlist(List<BannerlistBean> Bannerlist) {
            this.Bannerlist = Bannerlist;
        }

        public List<PopularserviceBean> getPopularservice() {
            return popularservice;
        }

        public void setPopularservice(List<PopularserviceBean> popularservice) {
            this.popularservice = popularservice;
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
             * updatedAt : 2020-08-13T10:39:24.831Z
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

        public static class MainserviceslistBean {
            /**
             * _id : 5f1acd2f9e65c148f3554db4
             * Service_Name : Batteries
             * Service_Image : http://3.101.31.129:3000/api/uploads/image.jpg
             * Desc : Data1
             * Popular : true
             * Vehicle_Type_id : {"_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-13T07:27:56.412Z","createdAt":"2020-07-13T07:27:56.412Z","__v":0}
             * Master_Service_id : 5f1ac41b55abee4870516567
             * updatedAt : 2020-08-04T08:37:25.348Z
             * createdAt : 2020-07-24T11:59:43.844Z
             * __v : 0
             */

            private String _id;
            private String Service_Name;
            private String Service_Image;
            private String Desc;
            private boolean Popular;
            private VehicleTypeIdBean Vehicle_Type_id;
            private String Master_Service_id;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getService_Name() {
                return Service_Name;
            }

            public void setService_Name(String Service_Name) {
                this.Service_Name = Service_Name;
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

            public boolean isPopular() {
                return Popular;
            }

            public void setPopular(boolean Popular) {
                this.Popular = Popular;
            }

            public VehicleTypeIdBean getVehicle_Type_id() {
                return Vehicle_Type_id;
            }

            public void setVehicle_Type_id(VehicleTypeIdBean Vehicle_Type_id) {
                this.Vehicle_Type_id = Vehicle_Type_id;
            }

            public String getMaster_Service_id() {
                return Master_Service_id;
            }

            public void setMaster_Service_id(String Master_Service_id) {
                this.Master_Service_id = Master_Service_id;
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

            public static class VehicleTypeIdBean {
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
        }

        public static class BannerlistBean {
            /**
             * _id : 5f1fcc4100937545906e59f9
             * ServiceBanner_Image : http://3.101.31.129:3000/api/uploads/e2eab949-c33c-466e-a771-f442bf8c70ef.png
             * Master_Service_id : 5f1ac41b55abee4870516567
             * Title : Myvacala Services
             * Status : Show
             * Desc : Book a service for your vehicle
             * updatedAt : 2020-08-12T14:46:29.430Z
             * createdAt : 2020-07-28T06:57:05.522Z
             * __v : 0
             */

            private String _id;
            private String ServiceBanner_Image;
            private String Master_Service_id;
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

            public String getServiceBanner_Image() {
                return ServiceBanner_Image;
            }

            public void setServiceBanner_Image(String ServiceBanner_Image) {
                this.ServiceBanner_Image = ServiceBanner_Image;
            }

            public String getMaster_Service_id() {
                return Master_Service_id;
            }

            public void setMaster_Service_id(String Master_Service_id) {
                this.Master_Service_id = Master_Service_id;
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

        public static class PopularserviceBean {
            /**
             * _id : 5f1acd2f9e65c148f3554db4
             * Service_Name : Batteries
             * Service_Image : http://3.101.31.129:3000/api/uploads/image.jpg
             * Desc : Data1
             * Popular : true
             * Vehicle_Type_id : 5f0c0cfc2f857d66950cf25f
             * Master_Service_id : 5f1ac41b55abee4870516567
             * updatedAt : 2020-08-04T08:37:25.348Z
             * createdAt : 2020-07-24T11:59:43.844Z
             * __v : 0
             */

            private String _id;
            private String Service_Name;
            private String Service_Image;
            private String Desc;
            private boolean Popular;
            private String Vehicle_Type_id;
            private String Master_Service_id;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getService_Name() {
                return Service_Name;
            }

            public void setService_Name(String Service_Name) {
                this.Service_Name = Service_Name;
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

            public boolean isPopular() {
                return Popular;
            }

            public void setPopular(boolean Popular) {
                this.Popular = Popular;
            }

            public String getVehicle_Type_id() {
                return Vehicle_Type_id;
            }

            public void setVehicle_Type_id(String Vehicle_Type_id) {
                this.Vehicle_Type_id = Vehicle_Type_id;
            }

            public String getMaster_Service_id() {
                return Master_Service_id;
            }

            public void setMaster_Service_id(String Master_Service_id) {
                this.Master_Service_id = Master_Service_id;
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

        public static class CustomerVehicleDataBean implements Serializable {
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
