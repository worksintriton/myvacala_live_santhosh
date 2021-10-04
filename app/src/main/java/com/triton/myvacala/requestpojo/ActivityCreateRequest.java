package com.triton.myvacala.requestpojo;

public class ActivityCreateRequest {

    /**
     * Type : Testing
     * Person_id : 01
     * Email_id : testing@mgial.com
     * Activity_title : Tryed to Login
     * Activity_description : Some one tryied to login the bouncebacklist site.
     * Date_and_Time : 23/10/2019 12:12:00
     */

    private String Type;
    private String Person_id;
    private String Email_id;
    private String Activity_title;
    private String Activity_description;
    private String Date_and_Time;

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getPerson_id() {
        return Person_id;
    }

    public void setPerson_id(String Person_id) {
        this.Person_id = Person_id;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String Email_id) {
        this.Email_id = Email_id;
    }

    public String getActivity_title() {
        return Activity_title;
    }

    public void setActivity_title(String Activity_title) {
        this.Activity_title = Activity_title;
    }

    public String getActivity_description() {
        return Activity_description;
    }

    public void setActivity_description(String Activity_description) {
        this.Activity_description = Activity_description;
    }

    public String getDate_and_Time() {
        return Date_and_Time;
    }

    public void setDate_and_Time(String Date_and_Time) {
        this.Date_and_Time = Date_and_Time;
    }
}
