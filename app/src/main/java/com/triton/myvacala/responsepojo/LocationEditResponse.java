package com.triton.myvacala.responsepojo;

public class LocationEditResponse {

    /**
     * Status : Success
     * Message : Customerdetails Updated
     * Data : null
     * Code : 200
     */

    private String Status;
    private String Message;
    private Object Data;
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

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }
}
