package com.triton.myvacala.activities.parking.maps;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.adapter.EditCityListAdapter;
import com.triton.myvacala.adapter.ParkingEditCityListAdapter;
import com.triton.myvacala.adapter.ParkingLocationsListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.GetServiceListResponse;
import com.triton.myvacala.responsepojo.ParkingLocationGetListResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingLocationsCityActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.rv_citylist)
    RecyclerView rv_citylist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;


    private List<ParkingLocationGetListResponse.DataBean> getServiceListResponseList = null;
    private SharedPreferences preferences;
    String TAG = "ParkingLocationsCityActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_city_parking);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Log.w(TAG,"onCreate--->");

        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        toolbar_title.setText(getResources().getString(R.string.selectyourcity));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (new ConnectionDetector(ParkingLocationsCityActivity.this).isNetworkAvailable(ParkingLocationsCityActivity.this)) {
            parkingLocationGetListResponseCall();
        }

        bottom_navigation_parking.setSelectedItemId(R.id.account);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    } //end of oncreate

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    //active = homeFragment;
                    String active_tag = "1";
                    callDirections(active_tag);
                    break;


                case R.id.bookinghistory:
                    //active = searchFragment;
                    active_tag = "2";
                    callDirections(active_tag);
                    break;


                case R.id.myvehicle:
                    //active = myVehicleFragment;
                    active_tag = "3";
                    callDirections(active_tag);
                    break;

                case R.id.account:
                    //active = accountFragment;
                    active_tag = "4";
                    callDirections(active_tag);
                    break;

            }
            return true;
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(ParkingLocationsCityActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }


    public void parkingLocationGetListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingLocationGetListResponse> call = apiInterface.parkingLocationGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<ParkingLocationGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ParkingLocationGetListResponse> call, @NonNull Response<ParkingLocationGetListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"ParkingLocationGetListResponse" + new Gson().toJson(response.body()));

                    getServiceListResponseList = response.body().getData();
                    if(!getServiceListResponseList.isEmpty()){
                        for(int i =0; i<getServiceListResponseList.size();i++){
                            String cityname = response.body().getData().get(i).getDisplay_Name();
                            Log.w(TAG,"cityname :"+cityname);



                        }

                    }
                    if(response.body().getData().isEmpty()){
                        tv_norecords.setVisibility(View.VISIBLE);
                        rv_citylist.setVisibility(View.GONE);
                    }else{
                        tv_norecords.setVisibility(View.GONE);
                        rv_citylist.setVisibility(View.VISIBLE);
                        setView();
                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<ParkingLocationGetListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"ParkingLocationGetListResponse flr"+t.getMessage());
            }
        });

    }

    private void setView() {
        rv_citylist.setLayoutManager(new GridLayoutManager(this, 3));

        // rvSymptomsList.setLayoutManager(new LinearLayoutManager(this));
        rv_citylist.setItemAnimator(new DefaultItemAnimator());
        ParkingLocationsListAdapter parkingLocationsListAdapter = new ParkingLocationsListAdapter(getApplicationContext(), getServiceListResponseList, rv_citylist);
        rv_citylist.setAdapter(parkingLocationsListAdapter);

        parkingLocationsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > getServiceListResponseList.size()) {
                    Log.e("haint", "Load More");
                }


            }


        });







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}