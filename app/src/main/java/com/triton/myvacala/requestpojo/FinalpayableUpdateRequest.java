package com.triton.myvacala.requestpojo;

public class FinalpayableUpdateRequest {


    /**
     * _id : 5f915f4d9c1be21c2cbcbd72
     * Final_bill_payed : 200
     */

    private String _id;
    private int Final_bill_payed;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getFinal_bill_payed() {
        return Final_bill_payed;
    }

    public void setFinal_bill_payed(int Final_bill_payed) {
        this.Final_bill_payed = Final_bill_payed;
    }
}
