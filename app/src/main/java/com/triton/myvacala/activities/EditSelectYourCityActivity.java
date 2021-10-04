package com.triton.myvacala.activities;

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
import com.triton.myvacala.adapter.EditCityListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.GetServiceListResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSelectYourCityActivity extends AppCompatActivity {

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

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;


    private List<GetServiceListResponse.DataBean> getServiceListResponseList = null;
    EditCityListAdapter cityListAdapter;
    private SharedPreferences preferences;
    String TAG = "EditSelectYourCityActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_city_old_user);

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


        if (new ConnectionDetector(EditSelectYourCityActivity.this).isNetworkAvailable(EditSelectYourCityActivity.this)) {
            getServiceListResponseCall();
        }

        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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


                case R.id.search:
                    //active = searchFragment;
                    active_tag = "2";
                    callDirections(active_tag);
                    break;


                case R.id.myvehicle:
                    //active = myVehicleFragment;
                    active_tag = "3";
                    callDirections(active_tag);
                    break;
                case R.id.cart:
                    //active = cartFragment;
                    active_tag = "4";
                    callDirections(active_tag);
                    break;
                case R.id.account:
                    //active = accountFragment;
                    active_tag = "5";
                    callDirections(active_tag);
                    break;

            }
            return true;
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(EditSelectYourCityActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }


    public void getServiceListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<GetServiceListResponse> call = apiInterface.getServiceListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<GetServiceListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetServiceListResponse> call, @NonNull Response<GetServiceListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"GetServiceListResponse" + new Gson().toJson(response.body()));

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
            public void onFailure(@NonNull Call<GetServiceListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"GetServiceListResponseflr"+t.getMessage());
            }
        });

    }

    private void setView() {
        rv_citylist.setLayoutManager(new GridLayoutManager(this, 3));

        // rvSymptomsList.setLayoutManager(new LinearLayoutManager(this));
        rv_citylist.setItemAnimator(new DefaultItemAnimator());
        cityListAdapter = new EditCityListAdapter(getApplicationContext(), getServiceListResponseList, rv_citylist);
        rv_citylist.setAdapter(cityListAdapter);

        cityListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
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