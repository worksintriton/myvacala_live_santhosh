package com.triton.myvacala.responsepojo;

public class CheckTimesResponse {


    /**
     * Status : Success
     * Message : Correct Date
     * Data : {"days":0,"hours":12,"min":0,"checkin_date":"2020-08-09","checkin_time":"08:00 AM","checkout_date":"2020-08-09","checkout_time":"08:00 PM","total_hrs":"12 hour(s) and 0 minute(s)."}
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
         * days : 0
         * hours : 12
         * min : 0
         * checkin_date : 2020-08-09
         * checkin_time : 08:00 AM
         * checkout_date : 2020-08-09
         * checkout_time : 08:00 PM
         * total_hrs : 12 hour(s) and 0 minute(s).
         */

        private int days;
        private int hours;
        private int min;
        private String checkin_date;
        private String checkin_time;
        private String checkout_date;
        private String checkout_time;
        private String total_hrs;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public String getCheckin_date() {
            return checkin_date;
        }

        public void setCheckin_date(String checkin_date) {
            this.checkin_date = checkin_date;
        }

        public String getCheckin_time() {
            return checkin_time;
        }

        public void setCheckin_time(String checkin_time) {
            this.checkin_time = checkin_time;
        }

        public String getCheckout_date() {
            return checkout_date;
        }

        public void setCheckout_date(String checkout_date) {
            this.checkout_date = checkout_date;
        }

        public String getCheckout_time() {
            return checkout_time;
        }

        public void setCheckout_time(String checkout_time) {
            this.checkout_time = checkout_time;
        }

        public String getTotal_hrs() {
            return total_hrs;
        }

        public void setTotal_hrs(String total_hrs) {
            this.total_hrs = total_hrs;
        }
    }
}
