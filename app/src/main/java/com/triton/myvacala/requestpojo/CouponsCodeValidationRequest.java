package com.triton.myvacala.requestpojo;

public class CouponsCodeValidationRequest {

    /**
     * Coupon_Code : MAC
     * Vehicle_type_id : qweqweqweqweq123123
     * Amount : 2000
     */

    private String Coupon_Code;
    private String Vehicle_type_id;
    private int Amount;

    public String getCoupon_Code() {
        return Coupon_Code;
    }

    public void setCoupon_Code(String Coupon_Code) {
        this.Coupon_Code = Coupon_Code;
    }

    public String getVehicle_type_id() {
        return Vehicle_type_id;
    }

    public void setVehicle_type_id(String Vehicle_type_id) {
        this.Vehicle_type_id = Vehicle_type_id;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }
}
