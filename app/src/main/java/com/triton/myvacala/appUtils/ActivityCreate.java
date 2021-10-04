package com.triton.myvacala.appUtils;

import android.annotation.SuppressLint;

import android.util.Log;



import com.google.gson.Gson;

import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;

import com.triton.myvacala.requestpojo.ActivityCreateRequest;

import com.triton.myvacala.responsepojo.ActivityCreateResponse;

import com.triton.myvacala.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityCreate {

    private static final String TAG = "ActivityCreate";
    public static String currentdateandtime = "";

    public static void getCurrentDateandTime(){
        Calendar c = Calendar.getInstance();
        Log.w(TAG,"Current time => "+c.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //@SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

         currentdateandtime = df.format(c.getTime());
    }

    public static void activityCreateResponseCall(String Type,String Person_id,String Email_id,String Activity_title,String Activity_description, String Date_and_Time) {
        //RestApiInterface ApiService = RetrofitClient.getApiService();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);

        Call<ActivityCreateResponse> call = apiInterface.activityCreateResponseCall(RestUtils.getContentType(), activityCreateRequest(Type,Person_id, Email_id,Activity_title,Activity_description,Date_and_Time));
        call.enqueue(new Callback<ActivityCreateResponse>() {
            @Override
            public void onResponse(Call<ActivityCreateResponse> call, Response<ActivityCreateResponse> response) {
                Log.w(TAG,"ActivityCreateResponse --->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {




                    } else {

                    }
                }


            }

            @Override
            public void onFailure(Call<ActivityCreateResponse> call, Throwable t) {
                Log.w(TAG,"ActivityCreateResponseflr--->" + t.getMessage());
            }
        });

    }
    public static ActivityCreateRequest activityCreateRequest(String Type,String Person_id,String Email_id,String Activity_title,String Activity_description, String Date_and_Time) {
        /**
         * Type : Testing
         * Person_id : 01
         * Email_id : testing@mgial.com
         * Activity_title : Tryed to Login
         * Activity_description : Some one tryied to login the bouncebacklist site.
         * Date_and_Time : 23/10/2019 12:12:00
         */
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest();
        activityCreateRequest.setType(Type);
        activityCreateRequest.setPerson_id(Person_id);
        activityCreateRequest.setEmail_id(Email_id);
        activityCreateRequest.setActivity_title(Activity_title);
        activityCreateRequest.setActivity_description(Activity_description);
        activityCreateRequest.setDate_and_Time(Date_and_Time);
        Log.w(TAG,"activityCreateRequest"+ new Gson().toJson(activityCreateRequest));
        return activityCreateRequest;
    }


}
