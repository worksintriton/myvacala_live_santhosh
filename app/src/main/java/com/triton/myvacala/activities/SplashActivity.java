package com.triton.myvacala.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;

import com.triton.myvacala.requestpojo.GetUserStatusRequest;
import com.triton.myvacala.requestpojo.RegisterMobileRequest;
import com.triton.myvacala.responsepojo.GetUserStatusResponse;
import com.triton.myvacala.responsepojo.RegisterMobileResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.LocationGettingService;
import com.triton.myvacala.utils.Permission_Activity;
import com.triton.myvacala.utils.RestUtils;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private long SPLASH_TIME_OUT = 3000;
    private String TAG = "SplashActivity";
    SessionManager session;
    String type;
    private AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // check the android sdk version for runtime permission
        int haslocationpermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        startService(new Intent(getApplicationContext(), LocationGettingService.class));



        Log.w(TAG,"onCreate");

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        boolean islogedin = session.isLoggedIn();
        Log.w(TAG,"islogedin-->"+islogedin);

       /* if (haslocationpermission != PackageManager.PERMISSION_GRANTED) {
            Intent myIntent = new Intent(getApplicationContext(), Permission_Activity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myIntent);
            finish();

        }
        else{ }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean islogedin = session.isLoggedIn();
                if(!islogedin) {
                    Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
                }
                else{

                    session = new SessionManager(getApplicationContext());
                    HashMap<String, String> user = session.getUserDetails();
                    type = user.get(SessionManager.KEY_TYPE);
                    String customerid = user.get(SessionManager.KEY_ID);
                    if(customerid != null && !customerid.isEmpty()){
                        if (new ConnectionDetector(SplashActivity.this).isNetworkAvailable(SplashActivity.this)) {
                            getUserStatusResponseCall(customerid);
                        }
                        Log.w(TAG,"Type---->"+type+" "+"id------------->"+customerid);

                    }else{
                        Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.new_right, R.anim.new_left);

                    }


                }



            }
        }, SPLASH_TIME_OUT);

    }


    private void getUserStatusResponseCall(String customerid) {
       // avi_indicator.setVisibility(View.VISIBLE);
        //avi_indicator.smoothToShow();
       // RestApiInterface ApiService = RetrofitClient.getApiService();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);

        Call<GetUserStatusResponse> call = apiInterface.getUserStatusResponseCall(RestUtils.getContentType(), getUserStatusRequest(customerid));
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<GetUserStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserStatusResponse> call, @NonNull Response<GetUserStatusResponse> response) {
              //  avi_indicator.smoothToHide();
                Log.w(TAG,"getUserStatusResponseCall"+ "--->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        if(!response.body().getData().isEmpty()) {

                            String Vehicle_Type_Status = String.valueOf(response.body().getData().get(0).isVehicle_Type_Status());
                            String Current_Location_Status = String.valueOf(response.body().getData().get(0).isCurrent_Location_Status());
                           boolean isUser_Status = response.body().getData().get(0).isUser_Status();

                           if(isUser_Status){
                               assert type != null;
                               if (type.equalsIgnoreCase("New User")) {
                                   Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                                   startActivity(i);
                               }
                               else if (type.equalsIgnoreCase("Exists")) {
                                   Log.w(TAG, "Current_Location_Status--->" + Current_Location_Status);
                                   Log.w(TAG, "Vehicle_Type_Status--->" + Vehicle_Type_Status);
                                   if (Current_Location_Status.equalsIgnoreCase("false")) {
                                       Log.w(TAG, "Current_Location_Status if--->" + Current_Location_Status);

                                       Intent i = new Intent(SplashActivity.this, MapsActivity.class);
                                       startActivity(i);
                                       finish();
                                       overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                   } else if (Vehicle_Type_Status.equalsIgnoreCase("false")) {
                                       Log.w(TAG, "Vehicle_Type_Status if--->" + Vehicle_Type_Status);
                                       Intent i = new Intent(SplashActivity.this, PopularVehicleListActivity.class);
                                       startActivity(i);
                                       finish();
                                       overridePendingTransition(R.anim.new_right, R.anim.new_left);

                                   } else {
                                       Log.w(TAG, "Else--->" + Vehicle_Type_Status + " " + Current_Location_Status);
                                       Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
                                       startActivity(i);
                                       finish();
                                       overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                   }
                               }
                           }else{
                            showErrorLoading("Admin blocks your account please contact me on myvacala team.");
                           }





                        }
                        else{
                            Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                            startActivity(i);
                        }


                    } else {
                        Intent i = new Intent(SplashActivity.this, SliderActivity.class);
                        startActivity(i);
                       // showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<GetUserStatusResponse> call,@NonNull Throwable t) {
               // avi_indicator.smoothToHide();
                Log.w(TAG,"getUserStatusResponseCallflr---->" + t.getMessage());
               // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private GetUserStatusRequest getUserStatusRequest(String customerid) {
        GetUserStatusRequest getUserStatusRequest = new GetUserStatusRequest();
        getUserStatusRequest.setCustomer_id(customerid);
        Log.w(TAG,"GetUserStatusRequest"+ new Gson().toJson(getUserStatusRequest));
        return getUserStatusRequest;
    }

    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }


}

