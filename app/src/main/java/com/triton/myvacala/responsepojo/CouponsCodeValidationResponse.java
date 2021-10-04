package com.triton.myvacala.responsepojo;

public class CouponsCodeValidationResponse {

    /**
     * Status : Success
     * Message : Coupon validated
     * Data : {"Value_Type":"Per","Value":10}
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
         * Value_Type : Per
         * Value : 10
         */

        private String Value_Type;
        private int Value;

        public String getValue_Type() {
            return Value_Type;
        }

        public void setValue_Type(String Value_Type) {
            this.Value_Type = Value_Type;
        }

        public int getValue() {
            return Value;
        }

        public void setValue(int Value) {
            this.Value = Value;
        }
    }
}
