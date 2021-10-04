package com.triton.myvacala.responsepojo;

import java.util.List;

public class NotificationListResponse {

    /**
     * Status : Success
     * Message : Notifications
     * Data : [{"_id":"5f2957b713a663621cf01f9c","Title":"Testing","Message":"Message","Message_Status":"Message_Status","Date_Time":"27-10-2019 12:10 AM","Booking_id":"QWERTOIUYTR","Customer_id":"5f1fae9bbcd5650a5ab130e8","updatedAt":"2020-08-04T12:42:31.586Z","createdAt":"2020-08-04T12:42:31.586Z","__v":0}]
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
         * _id : 5f2957b713a663621cf01f9c
         * Title : Testing
         * Message : Message
         * Message_Status : Message_Status
         * Date_Time : 27-10-2019 12:10 AM
         * Booking_id : QWERTOIUYTR
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * updatedAt : 2020-08-04T12:42:31.586Z
         * createdAt : 2020-08-04T12:42:31.586Z
         * __v : 0
         */

        private String _id;
        private String Title;
        private String Message;
        private String Message_Status;
        private String Date_Time;
        private String Booking_id;
        private String Customer_id;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getMessage_Status() {
            return Message_Status;
        }

        public void setMessage_Status(String Message_Status) {
            this.Message_Status = Message_Status;
        }

        public String getDate_Time() {
            return Date_Time;
        }

        public void setDate_Time(String Date_Time) {
            this.Date_Time = Date_Time;
        }

        public String getBooking_id() {
            return Booking_id;
        }

        public void setBooking_id(String Booking_id) {
            this.Booking_id = Booking_id;
        }

        public String getCustomer_id() {
            return Customer_id;
        }

        public void setCustomer_id(String Customer_id) {
            this.Customer_id = Customer_id;
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
