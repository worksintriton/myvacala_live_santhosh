package com.triton.myvacala.responsepojo;

import java.util.List;

public class ParkingExtChangeTimesResponse {


    /**
     * Status : Success
     * Message : Available
     * time_extancsion : true
     * Data : {"start_time":"2020-09-17","start_date":"04:45 PM","end_time":"06:45 PM","end_date":"2020-09-17","Price_Details":[{"date":"Thursday","start_time":"1","end_time":"23","prices":100}],"total_price":100,"final_total":200,"already_pay":100,"booking_id":"5f63489b3c2b645e47de5b08","additional_booking_hrs":"1 hour(s) and 0 minute(s).","additonal_booking_amount":100,"Overall_time_duraion":"2 hour(s) and 0 minute(s).","Overall_amount_paid":200,"extra_time":"1 hour(s) and 0 minute(s).","Booking_status":"Check-in"}
     * Code : 200
     */

    private String Status;
    private String Message;
    private boolean time_extancsion;
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

    public boolean isTime_extancsion() {
        return time_extancsion;
    }

    public void setTime_extancsion(boolean time_extancsion) {
        this.time_extancsion = time_extancsion;
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
         * start_time : 2020-09-17
         * start_date : 04:45 PM
         * end_time : 06:45 PM
         * end_date : 2020-09-17
         * Price_Details : [{"date":"Thursday","start_time":"1","end_time":"23","prices":100}]
         * total_price : 100
         * final_total : 200
         * already_pay : 100
         * booking_id : 5f63489b3c2b645e47de5b08
         * additional_booking_hrs : 1 hour(s) and 0 minute(s).
         * additonal_booking_amount : 100
         * Overall_time_duraion : 2 hour(s) and 0 minute(s).
         * Overall_amount_paid : 200
         * extra_time : 1 hour(s) and 0 minute(s).
         * Booking_status : Check-in
         */

        private String start_time;
        private String start_date;
        private String end_time;
        private String end_date;
        private int total_price;
        private int final_total;
        private int already_pay;
        private String booking_id;
        private String additional_booking_hrs;
        private int additonal_booking_amount;
        private String Overall_time_duraion;
        private int Overall_amount_paid;
        private String extra_time;
        private String Booking_status;
        private List<PriceDetailsBean> Price_Details;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public int getFinal_total() {
            return final_total;
        }

        public void setFinal_total(int final_total) {
            this.final_total = final_total;
        }

        public int getAlready_pay() {
            return already_pay;
        }

        public void setAlready_pay(int already_pay) {
            this.already_pay = already_pay;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(String booking_id) {
            this.booking_id = booking_id;
        }

        public String getAdditional_booking_hrs() {
            return additional_booking_hrs;
        }

        public void setAdditional_booking_hrs(String additional_booking_hrs) {
            this.additional_booking_hrs = additional_booking_hrs;
        }

        public int getAdditonal_booking_amount() {
            return additonal_booking_amount;
        }

        public void setAdditonal_booking_amount(int additonal_booking_amount) {
            this.additonal_booking_amount = additonal_booking_amount;
        }

        public String getOverall_time_duraion() {
            return Overall_time_duraion;
        }

        public void setOverall_time_duraion(String Overall_time_duraion) {
            this.Overall_time_duraion = Overall_time_duraion;
        }

        public int getOverall_amount_paid() {
            return Overall_amount_paid;
        }

        public void setOverall_amount_paid(int Overall_amount_paid) {
            this.Overall_amount_paid = Overall_amount_paid;
        }

        public String getExtra_time() {
            return extra_time;
        }

        public void setExtra_time(String extra_time) {
            this.extra_time = extra_time;
        }

        public String getBooking_status() {
            return Booking_status;
        }

        public void setBooking_status(String Booking_status) {
            this.Booking_status = Booking_status;
        }

        public List<PriceDetailsBean> getPrice_Details() {
            return Price_Details;
        }

        public void setPrice_Details(List<PriceDetailsBean> Price_Details) {
            this.Price_Details = Price_Details;
        }

        public static class PriceDetailsBean {
            /**
             * date : Thursday
             * start_time : 1
             * end_time : 23
             * prices : 100
             */

            private String date;
            private String start_time;
            private String end_time;
            private int prices;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getPrices() {
                return prices;
            }

            public void setPrices(int prices) {
                this.prices = prices;
            }
        }
    }
}