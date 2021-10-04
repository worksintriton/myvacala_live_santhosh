package com.triton.myvacala.responsepojo;

public class RegisterMobileResponse {

    /**
     * Status : Success
     * Message : OTP has been sent successfully for the registered mobile number
     * Userstatus : Exists
     * OTP : 7192
     * Data : {"_id":"5f0c146edb97a179bb713ed3","Name":"Dinesh","Email":"iddinesh@gmail.com","Phone":6383791451,"DOB":"","Gender":"","Vehicle_Type_Status":false,"Current_Location_Status":false,"User_Status":true,"updatedAt":"2020-07-13T07:59:42.361Z","createdAt":"2020-07-13T07:59:42.361Z","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    private String Userstatus;
    private int OTP;
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

    public String getUserstatus() {
        return Userstatus;
    }

    public void setUserstatus(String Userstatus) {
        this.Userstatus = Userstatus;
    }

    public int getOTP() {
        return OTP;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
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
         * _id : 5f0c146edb97a179bb713ed3
         * Name : Dinesh
         * Email : iddinesh@gmail.com
         * Phone : 6383791451
         * DOB :
         * Gender :
         * Vehicle_Type_Status : false
         * Current_Location_Status : false
         * User_Status : true
         * updatedAt : 2020-07-13T07:59:42.361Z
         * createdAt : 2020-07-13T07:59:42.361Z
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
