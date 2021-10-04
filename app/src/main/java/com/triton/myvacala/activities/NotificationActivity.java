package com.triton.myvacala.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.NotificationsListAdapter;

import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;

import com.triton.myvacala.requestpojo.NotificationListRequest;

import com.triton.myvacala.responsepojo.NotificationListResponse;
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

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    String active_tag = "5";

    String TAG = "NotificationActivity";

    SessionManager session;
    String name = "", customerid ="";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;



    @BindView(R.id.rv_notifications)
    RecyclerView rv_notifications;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    private List<NotificationListResponse.DataBean> notificationResponseList  = null;
    NotificationsListAdapter notificationsListAdapter;
    private SharedPreferences preferences;


    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout ;
    private String fromactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        toolbar_title.setText(getResources().getString(R.string.notifications));
        avi_indicator.setVisibility(View.GONE);

        session = new SessionManager(NotificationActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

        Log.w(TAG, "customerid :" + customerid);



        imgBack.setOnClickListener(v -> onBackPressed());

        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (new ConnectionDetector(NotificationActivity.this).isNetworkAvailable(NotificationActivity.this)) {
            notificationListResponseCall();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            // cancel the Visual indication of a refresh
            swipeRefreshLayout.setRefreshing(false);
            if (new ConnectionDetector(NotificationActivity.this).isNetworkAvailable(NotificationActivity.this)) {
                notificationListResponseCall();
            }

        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                String active_tag = "1";
                callDirections(active_tag);
                break;


            case R.id.search:
                active_tag = "2";
                callDirections(active_tag);
                break;


            case R.id.myvehicle:
                active_tag = "3";
                callDirections(active_tag);
                break;
            case R.id.cart:
                active_tag = "4";
                callDirections(active_tag);
                break;
            case R.id.account:
                active_tag = "5";
                callDirections(active_tag);
                break;

        }
        return true;
    };
    public void callDirections(String tag){
        Intent intent = new Intent(NotificationActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(fromactivity != null && fromactivity.equalsIgnoreCase("HomeFragment")){
           // callDirections("1");
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("CartFragment")){
            callDirections("4");
        }else{
            callDirections(active_tag);
        }
    }

    public void notificationListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<NotificationListResponse> call = apiInterface.notificationListResponseCall(RestUtils.getContentType(),notificationListRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<NotificationListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<NotificationListResponse> call, @NotNull Response<NotificationListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "NotificationListResponse" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if (response.body().getCode() == 200) {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        notificationResponseList = response.body().getData();
                        Log.w(TAG, "Size--->" + notificationResponseList.size());

                        if (response.body().getData().isEmpty()) {
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText("No notifications");
                            rv_notifications.setVisibility(View.GONE);
                        } else {
                            tv_norecords.setVisibility(View.GONE);
                            rv_notifications.setVisibility(View.VISIBLE);
                            setView();
                        }

                    } else {
                        assert response.body() != null;
                        swipeRefreshLayout.setVisibility(View.GONE);
                        //showErrorLoading(response.body().getMessage());
                        tv_norecords.setVisibility(View.VISIBLE);
                        tv_norecords.setText("No notifications");
                    }
                }

            }






            @Override
            public void onFailure(@NotNull Call<NotificationListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"NotificationListResponseflr"+t.getMessage());
            }
        });

    }
    private NotificationListRequest notificationListRequest() {
        /*
          Customer_id : 5f05d8fef3090625a91f40c6

         */

        NotificationListRequest notificationListRequest = new NotificationListRequest();
        notificationListRequest.setCustomer_id(customerid);

        Log.w(TAG," NotificationListRequest"+ new Gson().toJson(notificationListRequest));
        return notificationListRequest;
    }

    private void setView() {

        rv_notifications.setLayoutManager(new LinearLayoutManager(this));
        rv_notifications.setItemAnimator(new DefaultItemAnimator());
        notificationsListAdapter = new NotificationsListAdapter(getApplicationContext(),notificationResponseList , rv_notifications);
        rv_notifications.setAdapter(notificationsListAdapter);

        notificationsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > notificationResponseList.size()) {
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

}