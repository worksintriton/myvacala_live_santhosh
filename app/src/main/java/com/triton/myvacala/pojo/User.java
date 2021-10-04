
package com.triton.myvacala.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Email_ID")
    @Expose
    private String emailID;
    @SerializedName("Mobile_No")
    @Expose
    private String mobileNo;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("Date_of_birth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private Object name;
    @SerializedName("Password")
    @Expose
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }
}
