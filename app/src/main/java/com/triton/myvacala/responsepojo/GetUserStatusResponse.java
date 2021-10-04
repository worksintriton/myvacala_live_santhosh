package com.triton.myvacala.responsepojo;

import java.util.List;

public class GetUserStatusResponse {

    /**
     * Status : Success
     * Message : User details
     * Data : [{"_id":"5f1ecf3a1977a163636d7049","Name":"Balaji","Email":"balaji123@gmail.com","Phone":9344537083,"DOB":"","Gender":"","Vehicle_Type_Status":true,"Current_Location_Status":true,"User_Status":true,"updatedAt":"2020-07-27T13:51:08.759Z","createdAt":"2020-07-27T12:57:30.151Z","__v":0}]
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
         * _id : 5f1ecf3a1977a163636d7049
         * Name : Balaji
         * Email : balaji123@gmail.com
         * Phone : 9344537083
         * DOB :
         * Gender :
         * Vehicle_Type_Status : true
         * Current_Location_Status : true
         * User_Status : true
         * updatedAt : 2020-07-27T13:51:08.759Z
         * createdAt : 2020-07-27T12:57:30.151Z
         * __v : 0
         */

        private String _id;
        private String Name;
        private String Email;
        private long Phone;
        private String DOB;
        private String Gender;
        private boolean Vehicle_Type_Status;
        private boolean Current_Location_Status;
        private boolean User_Status;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public long getPhone() {
            return Phone;
        }

        public void setPhone(long Phone) {
            this.Phone = Phone;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public boolean isVehicle_Type_Status() {
            return Vehicle_Type_Status;
        }

        public void setVehicle_Type_Status(boolean Vehicle_Type_Status) {
            this.Vehicle_Type_Status = Vehicle_Type_Status;
        }

        public boolean isCurrent_Location_Status() {
            return Current_Location_Status;
        }

        public void setCurrent_Location_Status(boolean Current_Location_Status) {
            this.Current_Location_Status = Current_Location_Status;
        }

        public boolean isUser_Status() {
            return User_Status;
        }

        public void setUser_Status(boolean User_Status) {
            this.User_Status = User_Status;
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
