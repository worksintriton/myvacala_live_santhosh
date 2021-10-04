package com.triton.myvacala.activities.parking.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;

import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.activities.parking.maps.ParkingAddAddressMapsActivity;
import com.triton.myvacala.adapter.ParkingLocationListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.LocationListRequest;
import com.triton.myvacala.responsepojo.LocationListResponse;
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

public class ParkingLocationListActivity extends AppCompatActivity {


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    private String TAG = "ParkingLocationListActivity";

    SessionManager session;
    String name = "", customerid ="";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;



    @BindView(R.id.rv_locationlist)
    RecyclerView rv_locationlist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.iv_addlocation)
    ImageView iv_addlocation;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout ;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    private List<LocationListResponse.DataBean> locationListResponseList = null;
    ParkingLocationListAdapter locationListAdapter;
    private SharedPreferences preferences;
    private String fromactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list_parking);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        ButterKnife.bind(this);

        session = new SessionManager(ParkingLocationListActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

        Log.w(TAG, "customerid :" + customerid);


        toolbar_title.setText(getResources().getString(R.string.chooseyourlocation));
        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");

        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkingLocationListActivity.this, ParkingAddAddressMapsActivity.class));
            }
        });

        if (new ConnectionDetector(ParkingLocationListActivity.this).isNetworkAvailable(ParkingLocationListActivity.this)) {
            locationListResponseCall();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            // cancel the Visual indication of a refresh
            swipeRefreshLayout.setRefreshing(false);
            if (new ConnectionDetector(ParkingLocationListActivity.this).isNetworkAvailable(ParkingLocationListActivity.this)) {
                locationListResponseCall();
            }

        });

        bottom_navigation_parking.setSelectedItemId(R.id.account);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    } //end of oncreate

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
        Intent intent = new Intent(ParkingLocationListActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    public void locationListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationListResponse> call = apiInterface.locationListResponseCall(RestUtils.getContentType(),locationListRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LocationListResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationListResponse> call, @NotNull Response<LocationListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "LocationListResponse" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        locationListResponseList = response.body().getData();
                        Log.w(TAG,"Size--->"+locationListResponseList.size());

                        if(response.body().getData().isEmpty()){
                            tv_norecords.setVisibility(View.VISIBLE);
                            rv_locationlist.setVisibility(View.GONE);
                        }else{
                            tv_norecords.setVisibility(View.GONE);
                            rv_locationlist.setVisibility(View.VISIBLE);
                            setView();
                        }

                    }
                }else{
                    assert response.body() != null;
                    showErrorLoading(response.body().getMessage());
                }

            }






            @Override
            public void onFailure(@NotNull Call<LocationListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"LocationListResponseflr"+t.getMessage());
            }
        });

    }
    private LocationListRequest locationListRequest() {
        /*
          Customer_id : 5f05d8fef3090625a91f40c6

         */

        LocationListRequest locationListRequest = new LocationListRequest();
        locationListRequest.setCustomer_id(customerid);

        Log.w(TAG," locationListRequest"+ new Gson().toJson(locationListRequest));
        return locationListRequest;
    }

    private void setView() {
        //rv_locationlist.setLayoutManager(new GridLayoutManager(this, 3));

        rv_locationlist.setLayoutManager(new LinearLayoutManager(this));
        rv_locationlist.setItemAnimator(new DefaultItemAnimator());
        locationListAdapter = new ParkingLocationListAdapter(getApplicationContext(),locationListResponseList , rv_locationlist);
        rv_locationlist.setAdapter(locationListAdapter);

        locationListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > locationListResponseList.size()) {
                    Log.e("haint", "Load More");
                }


            }


        });







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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null){
            if(fromactivity.equalsIgnoreCase("ParkingEditMyAddressActivity")){
                callDirections("4");
            }
        }
        finish();
    }
}