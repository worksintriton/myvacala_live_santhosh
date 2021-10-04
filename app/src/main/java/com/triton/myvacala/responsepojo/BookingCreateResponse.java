package com.triton.myvacala.responsepojo;

public class BookingCreateResponse {


    /**
     * Status : Success
     * Message : Added successfully
     * Data : {"Services":"Test main service 1","Booking_id":"4H1HC38Iu8R3","Subserivces":"My testing-1","Vehicle_Details":"Four - 1","Registration_No":"TN 69 5218","Service_Date":"2020-10-20","Service_Time":"03:00 - 04:00","Amount_Paid":460,"Transaction_id":"5f85625e7fb27e622d58efd61603196165498","Coupon_Code_Amount":""}
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
         * Services : Test main service 1
         * Booking_id : 4H1HC38Iu8R3
         * Subserivces : My testing-1
         * Vehicle_Details : Four - 1
         * Registration_No : TN 69 5218
         * Service_Date : 2020-10-20
         * Service_Time : 03:00 - 04:00
         * Amount_Paid : 460
         * Transaction_id : 5f85625e7fb27e622d58efd61603196165498
         * Coupon_Code_Amount :
         */

        private String Services;
        private String Booking_id;
        private String Subserivces;
        private String Vehicle_Details;
        private String Registration_No;
        private String Service_Date;
        private String Service_Time;
        private int Amount_Paid;
        private String Transaction_id;
        private String Coupon_Code_Amount;
        private String User_issues ;

        public String getUser_issues() {
            return User_issues;
        }

        public void setUser_issues(String user_issues) {
            User_issues = user_issues;
        }

        public String getServices() {
            return Services;
        }

        public void setServices(String Services) {
            this.Services = Services;
        }

        public String getBooking_id() {
            return Booking_id;
        }

        public void setBooking_id(String Booking_id) {
            this.Booking_id = Booking_id;
        }

        public String getSubserivces() {
            return Subserivces;
        }

        public void setSubserivces(String Subserivces) {
            this.Subserivces = Subserivces;
        }

        public String getVehicle_Details() {
            return Vehicle_Details;
        }

        public void setVehicle_Details(String Vehicle_Details) {
            this.Vehicle_Details = Vehicle_Details;
        }

        public String getRegistration_No() {
            return Registration_No;
        }

        public void setRegistration_No(String Registration_No) {
            this.Registration_No = Registration_No;
        }

        public String getService_Date() {
            return Service_Date;
        }

        public void setService_Date(String Service_Date) {
            this.Service_Date = Service_Date;
        }

        public String getService_Time() {
            return Service_Time;
        }

        public void setService_Time(String Service_Time) {
            this.Service_Time = Service_Time;
        }

        public int getAmount_Paid() {
            return Amount_Paid;
        }

        public void setAmount_Paid(int Amount_Paid) {
            this.Amount_Paid = Amount_Paid;
        }

        public String getTransaction_id() {
            return Transaction_id;
        }

        public void setTransaction_id(String Transaction_id) {
            this.Transaction_id = Transaction_id;
        }

        public String getCoupon_Code_Amount() {
            return Coupon_Code_Amount;
        }

        public void setCoupon_Code_Amount(String Coupon_Code_Amount) {
            this.Coupon_Code_Amount = Coupon_Code_Amount;
        }
    }
}
