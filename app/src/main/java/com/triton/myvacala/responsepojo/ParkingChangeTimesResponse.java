package com.triton.myvacala.responsepojo;

import java.util.List;

public class ParkingChangeTimesResponse {

    /**
     * Status : Success
     * Message : Available
     * Data : {"Price_Details":[{"date":"Monday","start_time":"12","end_time":"19","prices":"10"}],"total_price":200}
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
         * Price_Details : [{"date":"Monday","start_time":"12","end_time":"19","prices":"10"}]
         * total_price : 200
         */

        private int total_price;
        private List<PriceDetailsBean> Price_Details;

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public List<PriceDetailsBean> getPrice_Details() {
            return Price_Details;
        }

        public void setPrice_Details(List<PriceDetailsBean> Price_Details) {
            this.Price_Details = Price_Details;
        }

        public static class PriceDetailsBean {
            /**
             * date : Monday
             * start_time : 12
             * end_time : 19
             * prices : 10
             */

            private String date;
            private String start_time;
            private String end_time;
            private String prices;

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

            public String getPrices() {
                return prices;
            }

            public void setPrices(String prices) {
                this.prices = prices;
            }
        }
    }
}
