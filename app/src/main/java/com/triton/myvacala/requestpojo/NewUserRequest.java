package com.triton.myvacala.requestpojo;

public class NewUserRequest {

    /**
     * Phone : 7418010184
     * Name : Venkat
     * Email : venkat@gmail.com
     */

    private long Phone;
    private String Name;
    private String Email;

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
}
