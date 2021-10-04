package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.CityListAdapter;
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

public class SelectYourCityActivity extends AppCompatActivity {

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

    private List<GetServiceListResponse.DataBean> getServiceListResponseList = null;
    CityListAdapter cityListAdapter;
    private SharedPreferences preferences;
    String TAG = "SelectYourCityActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_city);

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

        //end of oncreate
        if (new ConnectionDetector(SelectYourCityActivity.this).isNetworkAvailable(SelectYourCityActivity.this)) {
            getServiceListResponseCall();
        }
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
        cityListAdapter = new CityListAdapter(getApplicationContext(), getServiceListResponseList, rv_citylist);
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