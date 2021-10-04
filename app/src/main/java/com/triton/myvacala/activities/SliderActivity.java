package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.triton.myvacala.R;

import com.triton.myvacala.adapter.ViewPagerSplashAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;

import com.triton.myvacala.responsepojo.SplashScreenListResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderActivity extends AppCompatActivity {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    private SharedPreferences preferences;
    String TAG = "SliderActivity";


    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @BindView(R.id.iv_login)
    ImageView iv_login;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    List<SplashScreenListResponse.DataBean> splashBannerResponseArrayList;
    ViewPagerSplashAdapter viewPagerSplashAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Log.w(TAG,"onCreate--->");

        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SliderActivity.this,LoginActivity.class));
                finish();
            }
        });

        if (new ConnectionDetector(SliderActivity.this).isNetworkAvailable(SliderActivity.this)) {
            splashScreenListResponseCall();
        }


        }


    public void splashScreenListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SplashScreenListResponse> call = apiInterface.splashScreenListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SplashScreenListResponse>() {
            @Override
            public void onResponse(@NonNull Call<SplashScreenListResponse> call, @NonNull Response<SplashScreenListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"GetServiceListResponse" + new Gson().toJson(response.body()));

                    splashBannerResponseArrayList = response.body().getData();
                    if(splashBannerResponseArrayList != null){
                        viewpageData(splashBannerResponseArrayList);

                    }


                }








            }


            @Override
            public void onFailure(@NonNull Call<SplashScreenListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"SplashScreenListResponseFlr"+t.getMessage());
            }
        });

    }

    private void viewpageData(List<SplashScreenListResponse.DataBean> splashBannerResponseArrayList) {
        tabLayout.setupWithViewPager(viewPager, true);

        viewPagerSplashAdapter = new ViewPagerSplashAdapter(SliderActivity.this, splashBannerResponseArrayList);
        viewPager.setAdapter(viewPagerSplashAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == splashBannerResponseArrayList.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, false);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

}