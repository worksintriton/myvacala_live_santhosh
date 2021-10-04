package com.triton.myvacala.requestpojo;

public class CheckTimes11Request {


    /**
     * pre_checkindate : 2020-10-06 09:00 PM
     * checkin_date : 2020-10-05
     * checkin_time : 08:00 PM
     * checkout_date : 2020-10-18
     * checkout_time : 09:00 PM
     */

    private String pre_checkindate;
    private String checkin_date;
    private String checkin_time;
    private String checkout_date;
    private String checkout_time;

    public String getPre_checkindate() {
        return pre_checkindate;
    }

    public void setPre_checkindate(String pre_checkindate) {
        this.pre_checkindate = pre_checkindate;
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
}
