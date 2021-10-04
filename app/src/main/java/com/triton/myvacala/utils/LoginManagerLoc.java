package com.triton.myvacala.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.triton.myvacala.pojo.User;


/**
 * Created by Tritonitsolution on 10/31/2017.
 */

public class LoginManagerLoc {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Fastashusers";
    String user_id = "";
    String name = "";
    String logntype = "";
    String  Password ="";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USER = "userdatal";
    public LoginManagerLoc(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(User user){

        Gson gson = new Gson();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER, gson.toJson(user));
        editor.commit();
    }


    public User getUser(){

        Gson gson = new Gson();
        User user = new User();
        user =  gson.fromJson(pref.getString(KEY_USER, ""), User.class);

        return user;
    }
    public void  setUser(User user){

        Gson gson = new Gson();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER, gson.toJson(user));
        editor.commit();

    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


}
