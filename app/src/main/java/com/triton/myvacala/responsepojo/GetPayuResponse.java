package com.triton.myvacala.responsepojo;

public class GetPayuResponse {

    /**
     * Status : Success
     * Message : Mobile Splash Git Added successfully
     * Data : cb5a91e8c48b82b38f76ed3873f1c2ce2398f65bd5a4753a64a10562fe88efb4fea6c7ebbb42f3f6f94d4cfafae11b51c57efd697105ec4542e07c1e2e826fa3
     * Code : 200
     */

    private String Status;
    private String Message;
    private String Data;
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

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }
}
