package com.triton.myvacala.requestpojo;

public class ProfileUpdateRequest {

    /**
     * Phone : 9963220842
     * Name : Anjani
     * Email : anjani513@gmail.com
     * DOB : 24-11-1993
     * User_id
     */

    private long Phone;
    private String Name;
    private String Email;
    private String DOB;
    private String User_id;

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public long getPhone() {
        return Phone;
    }

    public void setPhone(long Phone) {
        this.Phone = Phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
