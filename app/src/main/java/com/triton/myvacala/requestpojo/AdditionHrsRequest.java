package com.triton.myvacala.requestpojo;

public class AdditionHrsRequest {

    /**
     * booking_id : 5f52183b850fbc582200ebfa
     * duration_date : 17 Sep ,05:30 PM to 17 Sep ,06:30 PM(0 days 1 hours )
     * start_date : 2020-09-17
     * start_time : 05:45 PM
     * end_date : 2020-09-17
     * end_time : 06:45 PM
     * additional_booking_hrs : 1 hour(s) and 0 minute(s).
     * additonal_booking_amount : 100
     * Overall_time_duraion : 2 hour(s) and 0 minute(s).
     * Overall_amount_paid : 200
     * extra_time : 1 hour(s) and 0 minute(s).
     * Booking_status : Check-in
     */

    private String booking_id;
    private String duration_date;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String additional_booking_hrs;
    private int additonal_booking_amount;
    private String Overall_time_duraion;
    private int Overall_amount_paid;
    private String extra_time;
    private String Booking_status;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getDuration_date() {
        return duration_date;
    }

    public void setDuration_date(String duration_date) {
        this.duration_date = duration_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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
}
