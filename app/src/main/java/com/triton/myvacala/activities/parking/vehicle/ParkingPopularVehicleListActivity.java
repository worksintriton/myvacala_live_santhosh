package com.triton.myvacala.activities.parking.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.adapter.ParkingPopularVehicleListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.appUtils.KeyboardUtils;
import com.triton.myvacala.requestpojo.PopularVehicleListRequest;
import com.triton.myvacala.responsepojo.PopularVehicleListResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingPopularVehicleListActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.togglebutton)
    ToggleButton toggleButton;


   @BindView(R.id.rv_popularvehiclelist)
    RecyclerView rv_popularvehiclelist;


    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.txt_popularvehicletype)
    TextView txt_popularvehicletype;

    @BindView(R.id.cardview_findvehicle)
    CardView cardview_findvehicle;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;


    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.img_clear)
    ImageView img_clear;

    @BindView(R.id.img_search)
    ImageView img_search;

    @BindView(R.id.edt_selectbymodelorbrand)
    EditText edt_selectbymodelorbrand;

    String searchString = "";

    String selectedVehicleTypeId;



    private String TAG = "ParkingPopularVehicleListActivity";

    private List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList = null;




    String twowheelervehicleid = "",fourwheelervehicleid ="";




    private List<PopularVehicleListResponse.DataBean> popularVehicleListResponseList = new ArrayList<>();
    private List<PopularVehicleListResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanList = new ArrayList<>();
    ParkingPopularVehicleListAdapter popularVehicleListOldUserAdapter;
    private SharedPreferences preferences;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_vehicle_list_parking);
        ButterKnife.bind(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        avi_indicator.setVisibility(View.GONE);
        img_clear.setVisibility(View.GONE);
        img_search.setVisibility(View.GONE);
        img_search.setOnClickListener(this);
        img_clear.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        toggleButton.setOnClickListener(v -> {

            if(toggleButton.isChecked()){
                //Button is ON

                Log.w(TAG,"Car");
                txt_popularvehicletype.setText("Popular Cars");
                selectedVehicleTypeId = fourwheelervehicleid;
                popularVehicleListResponseCall(fourwheelervehicleid, searchString);
                Log.w(TAG,"four wheeler clicked :"+fourwheelervehicleid);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

            }
            else{
            //Button is OFF

                Log.w(TAG,"Bikes");
                txt_popularvehicletype.setText("Popular Bikes");
                Log.w(TAG,"Two wheeler clicked :"+twowheelervehicleid);
                selectedVehicleTypeId = twowheelervehicleid;
                popularVehicleListResponseCall(twowheelervehicleid, searchString);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);

            }

        });
        cardview_findvehicle.setOnClickListener(this);
        if (new ConnectionDetector(ParkingPopularVehicleListActivity.this).isNetworkAvailable(ParkingPopularVehicleListActivity.this)) {

            vehicleTypeGetListResponseCall();
        }





        edt_selectbymodelorbrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.w(TAG,"beforeTextChanged-->"+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(TAG,"onTextChanged-->"+s.toString());
                searchString = s.toString();
                if(!searchString.isEmpty()){
                    img_search.setVisibility(View.VISIBLE);
                    img_clear.setVisibility(View.VISIBLE);

                }else{
                    img_search.setVisibility(View.GONE);
                    img_clear.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG,"afterTextChanged-->"+s.toString());

            }
        });

        bottom_navigation_parking.setSelectedItemId(R.id.myvehicle);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




    } //end of create

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    String active_tag = "1";
                    callDirections(active_tag);
                    break;


                case R.id.bookinghistory:
                    active_tag = "2";
                    callDirections(active_tag);
                    break;


                case R.id.myvehicle:
                    active_tag = "3";
                    callDirections(active_tag);
                    break;

                case R.id.account:
                    active_tag = "4";
                    callDirections(active_tag);
                    break;

            }
            return true;
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(ParkingPopularVehicleListActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    public void vehicleTypeGetListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleTypeGetListResponse> call = apiInterface.vehicleTypeGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VehicleTypeGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VehicleTypeGetListResponse> call, @NonNull Response<VehicleTypeGetListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"VehicleTypeGetListResponse" + new Gson().toJson(response.body()));

                    vehicleTypeGetListResponseList = response.body().getData();
                    if(!vehicleTypeGetListResponseList.isEmpty()){
                        for(int i =0; i<vehicleTypeGetListResponseList.size();i++){
                            String vehicletype = response.body().getData().get(i).getVehicle_Type();
                            String id = response.body().getData().get(i).get_id();
                            Log.w(TAG,"vehicletype :"+vehicletype+" "+"id :"+id);
                            if(vehicletype.equalsIgnoreCase("Two Wheeler")){
                                 twowheelervehicleid = response.body().getData().get(i).get_id();
                                Log.w(TAG,"twowheelervehicleid"+twowheelervehicleid);
                            }else if(vehicletype.equalsIgnoreCase("Four Wheeler")){
                                 fourwheelervehicleid = response.body().getData().get(i).get_id();
                                 selectedVehicleTypeId = fourwheelervehicleid;
                                popularVehicleListResponseCall(fourwheelervehicleid,searchString);

                                Log.w(TAG,"fourwheelervehicleid"+fourwheelervehicleid);
                            }


                        }



                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<VehicleTypeGetListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"VehicleTypeGetListResponseflr"+t.getMessage());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardview_findvehicle:
                startActivity(new Intent(ParkingPopularVehicleListActivity.this, ParkingVehicleBrandListActivity.class));
                break;
            case R.id.imgBack:
                onBackPressed();
                break;

            case R.id.img_search:
                popularVehicleListResponseCall(selectedVehicleTypeId, searchString);
                KeyboardUtils.hideKeyboard(ParkingPopularVehicleListActivity.this);
                break;

            case R.id.img_clear:
                edt_selectbymodelorbrand.setText("");
                popularVehicleListResponseCall(selectedVehicleTypeId, "");
                KeyboardUtils.hideKeyboard(ParkingPopularVehicleListActivity.this);
                break;


        }


    }


    public void popularVehicleListResponseCall(String vehicletypeid, String searchString){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PopularVehicleListResponse> call = apiInterface.popularVehicleListResponseCall(RestUtils.getContentType(),popularVehicleListRequest(vehicletypeid,searchString));
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PopularVehicleListResponse>() {
            @Override
            public void onResponse(@NotNull Call<PopularVehicleListResponse> call, @NotNull Response<PopularVehicleListResponse> response) {
                avi_indicator.smoothToHide();


                Log.w(TAG, "PopularVehicleListResponse" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        popularVehicleListResponseList.clear();
                        vehicleDetailsBeanList.clear();
                        popularVehicleListResponseList = response.body().getData();
                        Log.w(TAG,"Size--->"+popularVehicleListResponseList.size());
                        if (popularVehicleListResponseList.size() > 0) {
                            for (int i = 0; i < popularVehicleListResponseList.size(); i++) {
                                vehicleDetailsBeanList = popularVehicleListResponseList.get(i).getVehicle_Details();
                            }
                        }

                        if (response.body().getData().isEmpty()) {
                            tv_norecords.setVisibility(View.VISIBLE);
                            rv_popularvehiclelist.setVisibility(View.GONE);
                        } else {
                            tv_norecords.setVisibility(View.GONE);
                            rv_popularvehiclelist.setVisibility(View.VISIBLE);
                            setView();
                        }

                    } else {
                        popularVehicleListResponseList.clear();
                        vehicleDetailsBeanList.clear();
                        tv_norecords.setVisibility(View.VISIBLE);
                        rv_popularvehiclelist.setVisibility(View.GONE);


                    }
                }


            }






            @Override
            public void onFailure(@NotNull Call<PopularVehicleListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PopularVehicleListResponseflr"+t.getMessage());
            }
        });

    }
    private PopularVehicleListRequest popularVehicleListRequest(String vehicletypeid, String searchString) {
        /*
         * Vehicle_Type_id : 5f0c0d092f857d66950cf260
         * search_string : bajaj
         */

        PopularVehicleListRequest popularVehicleListRequest = new PopularVehicleListRequest();
        popularVehicleListRequest.setVehicle_Type_id(vehicletypeid);
        popularVehicleListRequest.setSearch_string(searchString);
        Log.w(TAG," popularVehicleListRequest"+ new Gson().toJson(popularVehicleListRequest));
        return popularVehicleListRequest;
    }

    private void setView() {

        rv_popularvehiclelist.setLayoutManager(new LinearLayoutManager(this));
        rv_popularvehiclelist.setItemAnimator(new DefaultItemAnimator());
        popularVehicleListOldUserAdapter = new ParkingPopularVehicleListAdapter(getApplicationContext(),popularVehicleListResponseList , rv_popularvehiclelist);
        rv_popularvehiclelist.setAdapter(popularVehicleListOldUserAdapter);

        popularVehicleListOldUserAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > popularVehicleListResponseList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}