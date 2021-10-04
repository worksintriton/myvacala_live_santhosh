package com.triton.myvacala.responsepojo;

public class ParkingSlotCheckAvailableResponse {

    /**
     * Status : Success
     * Message : Available Slot Details
     * Data : {"area":"Block-1","floor":"Floor-1","slot":"1","status":"enable"}
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
         * area : Block-1
         * floor : Floor-1
         * slot : 1
         * status : enable
         */

        private String area;
        private String floor;
        private String slot;
        private String status;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
