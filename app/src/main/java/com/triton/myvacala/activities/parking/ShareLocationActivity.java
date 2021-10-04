package com.triton.myvacala.activities.parking;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;

import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.PopularServiceActivity;
import com.triton.myvacala.activities.SplashActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.CheckTimesRequest;
import com.triton.myvacala.requestpojo.UserDetailsRequest;
import com.triton.myvacala.responsepojo.CheckTimesResponse;
import com.triton.myvacala.responsepojo.UserDetailsResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShareLocationActivity extends Activity {

    private static final String TAG = "ShareLocationActivity";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;
    private String customerid;

    private   List<UserDetailsResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;
    private   List<UserDetailsResponse.VehicletypeDetailsBean> vehicletypeDetailsBeanList;

    String headerVehicleimg,headerVehicleName,headerVehicleBrandName,headerVehicleFuelTypeName,headerVehicleFuelTypeBackgroundcolor;
    String twowheelerstatus = "" ,fourwheelerstatus = "" ,vehicletypename;
    String selectedVehicleType,selectedVehicleId;
    String twowheelervehicleid ,fourwheelervehicleid ;


    @BindView(R.id.cv_vehicleimage)
    ImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;



    @BindView(R.id.togglebutton)
    ToggleButton toggleButton;

    private String vehicleNumber;
    private boolean isFourWheeler;
    private AlertDialog.Builder alertDialogBuilder;
    private Dialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);

        ButterKnife.bind(this);
        SessionManager session = new SessionManager(this);
        session.checkLogin();
        boolean islogedin = session.isLoggedIn();
        Log.w(TAG,"islogedin-->"+islogedin);

        if(islogedin){
            HashMap<String, String> user = session.getUserDetails();
            customerid = user.get(SessionManager.KEY_ID);
            Log.w(TAG,"customerid : "+customerid);
        }else{
            startActivity(new Intent(ShareLocationActivity.this, SplashActivity.class));
            return;
        }

        avi_indicator.setVisibility(View.GONE);




        if (getIntent().getData() != null) {
            Uri data = getIntent().getData();

            Log.w(TAG, "data :" + data.toString());
            try {
                String val = data.toString();
                String value = val.substring(val.indexOf("=") + 1, val.length());
                Log.w(TAG,"value parkingid : "+ value);

                byte[] output = android.util.Base64.decode(val.substring(val.indexOf("=") + 1, val.length()), Base64.DEFAULT);




            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            if(customerid != null){
                userDetailsResponseCall();
            }

        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggleButton.isChecked()){
                    //Button is ON

                    Log.w(TAG,"Car");
                    Log.w(TAG,"four wheeler clicked :"+fourwheelervehicleid);
                    if(!fourwheelerstatus.isEmpty()){
                        Log.w(TAG,"fourwheelerstatus togglebutton---->"+fourwheelerstatus);

                        if(fourwheelerstatus.equalsIgnoreCase("Default")) {
                            if (new ConnectionDetector(ShareLocationActivity.this).isNetworkAvailable(ShareLocationActivity.this)) {
                                cv_vehicleimage.setVisibility(View.VISIBLE);
                                txt_vehiclename.setVisibility(View.VISIBLE);
                                vehicletypename = "Four Wheeler";
                                selectedVehicleType = vehicletypename;
                                selectedVehicleId = fourwheelervehicleid;
                                Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                                getVehicleDataToggle(vehicletypename);
                            }
                        }
                    }
                    else{
                        cv_vehicleimage.setVisibility(View.GONE);
                        txt_vehiclename.setVisibility(View.GONE);
                        vehicletypename = "Four Wheeler";
                        selectedVehicleType = vehicletypename;
                        selectedVehicleId = fourwheelervehicleid;
                        showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));
                        // Toasty.warning(getApplicationContext(), getResources().getString(R.string.vehicletypeerrormsg), Toast.LENGTH_SHORT, true).show();

                    }

                    toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

                }
                else{
                    //Button is OFF

                    Log.w(TAG,"Bikes");
                    Log.w(TAG,"Two wheeler clicked :"+twowheelervehicleid+"twowheelerstatus:"+twowheelerstatus);
                    if(!twowheelerstatus.isEmpty()){
                        Log.w(TAG,"twowheelerstatus togglebutton---->"+twowheelerstatus);

                        if(twowheelerstatus.equalsIgnoreCase("Default")) {
                            if (new ConnectionDetector(ShareLocationActivity.this).isNetworkAvailable(ShareLocationActivity.this)) {
                                cv_vehicleimage.setVisibility(View.VISIBLE);
                                txt_vehiclename.setVisibility(View.VISIBLE);

                                // mainServiceGetListResponseCall(twowheelervehicleid);
                                vehicletypename = "Two Wheeler";
                                selectedVehicleType = vehicletypename;
                                selectedVehicleId = twowheelervehicleid;
                                Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);
                                getVehicleDataToggle(vehicletypename);

                            }
                        }
                    }
                    else{
                        cv_vehicleimage.setVisibility(View.GONE);
                        txt_vehiclename.setVisibility(View.GONE);
                        vehicletypename = "Two Wheeler";
                        selectedVehicleType = vehicletypename;
                        selectedVehicleId = twowheelervehicleid;
                        showErrorLoading( getResources().getString(R.string.vehicletypeerrormsg));

                    }


                    toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);

                }



            }
        });








    }//end of oncreate

    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(ShareLocationActivity.this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {
                    hideLoading();
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading() {
        try {
            alertDialog.dismiss();
        } catch (Exception ignored) {

        }
    }

    private void userDetailsResponseCall() {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<UserDetailsResponse> call = apiInterface.userDetailsResponseCall(RestUtils.getContentType(), userDetailsRequest());
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<UserDetailsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<UserDetailsResponse> call, @NotNull Response<UserDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "UserDetailsResponse" + new Gson().toJson(response.body()));

                if (response.body() != null) {


                    if (200 == response.body().getCode()) {

                         customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                         vehicletypeDetailsBeanList = response.body().getVehicletypeDetails();

                         if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
                             getVehicleData();
                         }






                    }



                }
            }

            @Override
            public void onFailure(@NotNull Call<UserDetailsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "UserDetailsResponse flr" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private UserDetailsRequest userDetailsRequest() {
        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setCustomer_id(customerid);

        Log.w(TAG, "userDetailsRequest" + new Gson().toJson(userDetailsRequest));
        return userDetailsRequest;
    }

    private void getVehicleData() {

        if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0) {
            Log.w(TAG, "customerVehicleDataBeanList--->" + customerVehicleDataBeanList.size());
            for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                    vehicletypename = "Two Wheeler";
                    twowheelervehicleid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    twowheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                    Log.w(TAG, "twowheelervehicleid" + twowheelervehicleid);
                }
                else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                    vehicletypename = "Four Wheeler";
                    fourwheelervehicleid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    fourwheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                    Log.w(TAG, "fourwheelervehicleid" + fourwheelervehicleid);
                }
                Log.w(TAG, "getVehicleData twowheelerstatus : " + twowheelerstatus + " " + "fourwheelerstatus : " + fourwheelerstatus);


            }
        }

        if(!fourwheelerstatus.isEmpty()){
            cv_vehicleimage.setVisibility(View.VISIBLE);
            txt_vehiclename.setVisibility(View.VISIBLE);
            Log.w(TAG,"selectedVehicleId if--->"+selectedVehicleId);
            vehicletypename = "Four Wheeler";
            selectedVehicleType = vehicletypename;
            toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);
            isFourWheeler = true;


        }
        else{
            vehicletypename = "Two Wheeler";
            selectedVehicleType = vehicletypename;
            cv_vehicleimage.setVisibility(View.VISIBLE);
            txt_vehiclename.setVisibility(View.VISIBLE);
            Log.w(TAG,"selectedVehicleId else--->"+selectedVehicleId);
            toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
            toggleButton.setChecked(false);
            isFourWheeler = false;


        }

        if(isFourWheeler){
            if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
                for(int i =0; i<customerVehicleDataBeanList.size();i++) {
                    String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                    if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                        headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                        headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                        headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                        headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                        headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                        vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                        Log.w(TAG,"getVehicleData headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName+"headerVehicleFuelTypeBackgroundcolor :"+headerVehicleFuelTypeBackgroundcolor);
                    }

                }
                setHeaderVehicleData();
            }
        }
        else{
            if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
                for(int i =0; i<customerVehicleDataBeanList.size();i++) {
                    String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                    if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                        headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                        headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                        headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                        headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                        headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                        vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                        Log.w(TAG,"getVehicleData headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName+"headerVehicleFuelTypeBackgroundcolor :"+headerVehicleFuelTypeBackgroundcolor);
                    }

                }
                setHeaderVehicleData();
            }
        }





    }
    private void getVehicleDataToggle(String vehicletypename) {
        if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
                for(int i =0; i<customerVehicleDataBeanList.size();i++) {
                    String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                    if (vehicletype.equalsIgnoreCase(vehicletypename)) {
                        headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                        headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                        headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                        headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                        headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                        vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                        Log.w(TAG,"getVehicleData headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName+"headerVehicleFuelTypeBackgroundcolor :"+headerVehicleFuelTypeBackgroundcolor);
                    }

                }
                setHeaderVehicleData();
            }






    }




    private void setHeaderVehicleData() {

        if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {
            Glide.with(ShareLocationActivity.this)
                    .load(headerVehicleimg)
                    .into(cv_vehicleimage);

        }
        else{
            Glide.with(ShareLocationActivity.this)
                    .load(R.drawable.logo)
                    .into(cv_vehicleimage);

        }
        if(headerVehicleName != null){
            txt_vehiclename.setText(headerVehicleName);
        }else{
            txt_vehiclename.setText("");
        }


        if (vehicleNumber != null) {
            txt_vehicle_number.setText(vehicleNumber);
        } else {
            txt_vehicle_number.setText("");
        }

    }













    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }






}
