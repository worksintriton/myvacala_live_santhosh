package com.triton.myvacala.responsepojo;

import java.util.List;

public class ParkingLocationGetListResponse {

    /**
     * Status : Success
     * Message : Locationdetails
     * Data : [{"Pincodes":["600073"],"_id":"5f912ee26fb83b14dea7e77c","Location_Name":"Chennai","Image":"https://myvacala.com/api/uploads/58c56286-e384-4c37-97d5-0d3d3bdb28c8.png","Lat":12.936427,"Long":80.179217,"Display_Name":"Chennai","Disable":false,"updatedAt":"2020-10-22T07:04:02.728Z","createdAt":"2020-10-22T07:04:02.728Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    private List<DataBean> Data;

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

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Pincodes : ["600073"]
         * _id : 5f912ee26fb83b14dea7e77c
         * Location_Name : Chennai
         * Image : https://myvacala.com/api/uploads/58c56286-e384-4c37-97d5-0d3d3bdb28c8.png
         * Lat : 12.936427
         * Long : 80.179217
         * Display_Name : Chennai
         * Disable : false
         * updatedAt : 2020-10-22T07:04:02.728Z
         * createdAt : 2020-10-22T07:04:02.728Z
         * __v : 0
         */

        private String _id;
        private String Location_Name;
        private String Image;
        private double Lat;
        private double Long;
        private String Display_Name;
        private boolean Disable;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<String> Pincodes;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getLocation_Name() {
            return Location_Name;
        }

        public void setLocation_Name(String Location_Name) {
            this.Location_Name = Location_Name;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public double getLat() {
            return Lat;
        }

        public void setLat(double Lat) {
            this.Lat = Lat;
        }

        public double getLong() {
            return Long;
        }

        public void setLong(double Long) {
            this.Long = Long;
        }

        public String getDisplay_Name() {
            return Display_Name;
        }

        public void setDisplay_Name(String Display_Name) {
            this.Display_Name = Display_Name;
        }

        public boolean isDisable() {
            return Disable;
        }

        public void setDisable(boolean Disable) {
            this.Disable = Disable;
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

        public List<String> getPincodes() {
            return Pincodes;
        }

        public void setPincodes(List<String> Pincodes) {
            this.Pincodes = Pincodes;
        }
    }
}
