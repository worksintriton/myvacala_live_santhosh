package com.triton.myvacala.sessionmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {
    public static final String KEY_CUSTOMER_DOB = "dob";
    String TAG = "SessionManager";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Session Manager";
    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_TOKEN = "keytoken";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL_ID = "emailid";
    public static final String KEY_MOBILE = "phoneno";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ID = "id";
    public static final String KEY_CUSTOMER_LOCATION = "customerlocation";

    public static final String KEEPLOGIN = "keeplogin";

    public static final String KEY_USER_STATUS = "Userstatus";
    public static final String KEY_VEHICLE_TYPE_STATUS = "Vehicle_Type_Status";
    public static final String KEY_CURRENT_LOCATION_STATUS = "Current_Location_Status";



    public static final String KEY_MOBILEAPPDETAILS_EMAIL = "mobileappemail";
    public static final String KEY_MOBILEAPPDETAILS_PHONENUMBER = "mobileappphonenumber";
    public static final String KEY_MOBILEAPPDETAILS_ANDROIDSHARELINK = "mobileappsharelink";

    public static final String KEY_LOCATIONDETAILS_CITY = "city";
    public static final String KEY_LOCATIONDETAILS_STATE = "state";
    public static final String KEY_LOCATIONDETAILS_COUNTRY = "country";
    public static final String KEY_LOCATIONDETAILS_PINCODE = "pincode";
    public static final String KEY_LOCATIONDETAILS_STREET = "street";
    public static final String KEY_LOCATIONDETAILS_LOCATIONNICKNAME = "locationnickname";
    public static final String KEY_LOCATIONDETAILS_FLATNO = "flatno";

    public static final String KEY_PARKING_AREA = "area";
    public static final String KEY_PARKING_FLOOR = "floor";
    public static final String KEY_PARKING_SLOT = "slot";


    public static final String KEY_PAYU_SALTKEY = "saltkey";
    public static final String KEY_PAYU_MERCHANT_KEY = "merchantkey";
    public static final String KEY_PAYU_PRODUCTION = "production";





    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void updateToken(String refreshedToken) {

        //editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, refreshedToken);
        editor.commit();

        Log.d(TAG, "------------->update token" + refreshedToken);
    }






    public void createLoginSession(String name, String emailID, String mobile, String type,String id,String customerlocation,String dob) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL_ID, emailID);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_TYPE, type);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_CUSTOMER_LOCATION,customerlocation);
        editor.putString(KEY_CUSTOMER_DOB,dob);
        Log.e(TAG, "................................>> session Login Details " + "username" + name+"email"+emailID +" mobile" + mobile+"Type:"+type+"id :"+id);

        editor.commit();

    }




    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        user.put(KEY_EMAIL_ID, pref.getString(KEY_EMAIL_ID, ""));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, ""));
        user.put(KEY_TYPE, pref.getString(KEY_TYPE, ""));
        user.put(KEY_ID, pref.getString(KEY_ID, ""));
        user.put(KEY_CUSTOMER_LOCATION, pref.getString(KEY_CUSTOMER_LOCATION, ""));
        user.put(KEY_CUSTOMER_DOB, pref.getString(KEY_CUSTOMER_DOB, ""));
        return user;
    }

    public void checkUserStatus(String Userstatus,String Vehicle_Type_Status,String Current_Location_Status) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_STATUS, Userstatus);
        editor.putString(KEY_VEHICLE_TYPE_STATUS, Vehicle_Type_Status);
        editor.putString(KEY_CURRENT_LOCATION_STATUS, Current_Location_Status);
        Log.e(TAG, "................................>> session Login Details " + "Vehicle_Type_Status" + Vehicle_Type_Status+" "+"Current_Location_Status :"+Current_Location_Status);
        editor.commit();

    }

    public HashMap<String, String> getUserStatusDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USER_STATUS, pref.getString(KEY_USER_STATUS, ""));
        user.put(KEY_VEHICLE_TYPE_STATUS, pref.getString(KEY_VEHICLE_TYPE_STATUS, ""));
        user.put(KEY_CURRENT_LOCATION_STATUS, pref.getString(KEY_CURRENT_LOCATION_STATUS, ""));
        return user;
    }







    public boolean checkLogin() {

        if (!this.isLoggedIn()) {


        } else if (this.isLoggedIn()) {

        }

        return false;
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();

    }




    public void setIsLogin(boolean isLoogedIn){
        editor.putBoolean(KEEPLOGIN,isLoogedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEEPLOGIN, false);
    }



    public void createMobileappdetails(String emailID, String phonenumber,String sharelink) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_MOBILEAPPDETAILS_EMAIL, emailID);
        editor.putString(KEY_MOBILEAPPDETAILS_PHONENUMBER, phonenumber);
        editor.putString(KEY_MOBILEAPPDETAILS_ANDROIDSHARELINK, sharelink);

        Log.e(TAG, "................................>> session createMobileappdetails " + "emailID" + emailID+"phonenumber"+phonenumber +" sharelink" + sharelink);

        editor.commit();

    }

    public void createPayuDetails(String saltkey, String merchantkey,String production) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PAYU_SALTKEY, saltkey);
        editor.putString(KEY_PAYU_MERCHANT_KEY, merchantkey);
        editor.putString(KEY_PAYU_PRODUCTION, production);

        Log.e(TAG, "................................>> session createPayuDetails " + "saltkey" + saltkey+" merchantkey "+merchantkey +" production " + production);

        editor.commit();

    }
    public HashMap<String, String> getPayuDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_PAYU_SALTKEY, pref.getString(KEY_PAYU_SALTKEY, ""));
        user.put(KEY_PAYU_MERCHANT_KEY, pref.getString(KEY_PAYU_MERCHANT_KEY, ""));
        user.put(KEY_PAYU_PRODUCTION, pref.getString(KEY_PAYU_PRODUCTION, ""));
        return user;
    }
    public void createParkingSlotdetails(String area, String floor,String slot) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PARKING_AREA, area);
        editor.putString(KEY_PARKING_FLOOR, floor);
        editor.putString(KEY_PARKING_SLOT, slot);

        Log.w(TAG, "................................>> session createParkingSlotdetails " +area+" "+floor+" "+slot );

        editor.commit();

    }
    public HashMap<String, String> getMobileappdetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_MOBILEAPPDETAILS_EMAIL, pref.getString(KEY_MOBILEAPPDETAILS_EMAIL, ""));
        user.put(KEY_MOBILEAPPDETAILS_PHONENUMBER, pref.getString(KEY_MOBILEAPPDETAILS_PHONENUMBER, ""));
        user.put(KEY_MOBILEAPPDETAILS_ANDROIDSHARELINK, pref.getString(KEY_MOBILEAPPDETAILS_ANDROIDSHARELINK, ""));
        return user;
    }

    public void createLocationDetails(String city, String state,String country,String pincode,String street,String locationnickname,String flatno) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_LOCATIONDETAILS_CITY, city);
        editor.putString(KEY_LOCATIONDETAILS_STATE, state);
        editor.putString(KEY_LOCATIONDETAILS_COUNTRY, country);
        editor.putString(KEY_LOCATIONDETAILS_PINCODE, pincode);
        editor.putString(KEY_LOCATIONDETAILS_STREET, street);
        editor.putString(KEY_LOCATIONDETAILS_LOCATIONNICKNAME, locationnickname);
        editor.putString(KEY_LOCATIONDETAILS_FLATNO, flatno);

        Log.e(TAG, "................................>> session createLocationDetails " + "city" + city+"street :"+street);

        editor.commit();

    }
    public HashMap<String, String> getLocationDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_LOCATIONDETAILS_CITY, pref.getString(KEY_LOCATIONDETAILS_CITY, ""));
        user.put(KEY_LOCATIONDETAILS_STATE, pref.getString(KEY_LOCATIONDETAILS_STATE, ""));
        user.put(KEY_LOCATIONDETAILS_COUNTRY, pref.getString(KEY_LOCATIONDETAILS_COUNTRY, ""));
        user.put(KEY_LOCATIONDETAILS_PINCODE, pref.getString(KEY_LOCATIONDETAILS_PINCODE, ""));
        user.put(KEY_LOCATIONDETAILS_STREET, pref.getString(KEY_LOCATIONDETAILS_STREET, ""));
        user.put(KEY_LOCATIONDETAILS_LOCATIONNICKNAME, pref.getString(KEY_LOCATIONDETAILS_LOCATIONNICKNAME, ""));
        user.put(KEY_LOCATIONDETAILS_FLATNO, pref.getString(KEY_LOCATIONDETAILS_FLATNO, ""));
        return user;
    }
    public HashMap<String, String> getParkingSlotDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_PARKING_AREA, pref.getString(KEY_PARKING_AREA, ""));
        user.put(KEY_PARKING_FLOOR, pref.getString(KEY_PARKING_FLOOR, ""));
        user.put(KEY_PARKING_SLOT, pref.getString(KEY_PARKING_SLOT, ""));
        return user;
    }




}
