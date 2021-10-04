package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;

import com.triton.myvacala.adapter.OrderHistoryListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.BookingHistoryRequest;

import com.triton.myvacala.responsepojo.BookingHistoryResponse;

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

public class OrderHistoryActivity extends AppCompatActivity {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout ;

    String TAG = "OrderHistoryActivity";



    SessionManager session;
    String name = "", customerid ="";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;



    @BindView(R.id.rv_orderhistory)
    RecyclerView rv_orderhistory;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    private List<BookingHistoryResponse.DataBean> bookingHistoryResponseList  = null;
    OrderHistoryListAdapter orderHistoryListAdapter;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ButterKnife.bind(this);
        toolbar_title.setText(getResources().getString(R.string.orderhistory));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        avi_indicator.setVisibility(View.GONE);

        session = new SessionManager(OrderHistoryActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

        Log.w(TAG, "customerid :" + customerid);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        if (new ConnectionDetector(OrderHistoryActivity.this).isNetworkAvailable(OrderHistoryActivity.this)) {
            bookingHistoryResponseCall();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            // cancel the Visual indication of a refresh
            swipeRefreshLayout.setRefreshing(false);
            if (new ConnectionDetector(OrderHistoryActivity.this).isNetworkAvailable(OrderHistoryActivity.this)) {
                bookingHistoryResponseCall();
            }

        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(OrderHistoryActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    public void bookingHistoryResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<BookingHistoryResponse> call = apiInterface.bookingHistoryResponseCall(RestUtils.getContentType(),bookingHistoryRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<BookingHistoryResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookingHistoryResponse> call, @NotNull Response<BookingHistoryResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "BookingHistoryResponse" + new Gson().toJson(response.body()));


                assert response.body() != null;
                Log.w(TAG,"Code--->"+response.body().getCode());
                if (200 == response.body().getCode()) {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                        bookingHistoryResponseList = response.body().getData();
                        Log.w(TAG, "Size--->" + bookingHistoryResponseList.size());

                        if (response.body().getData().isEmpty()) {
                            tv_norecords.setVisibility(View.VISIBLE);
                            rv_orderhistory.setVisibility(View.GONE);
                        } else {
                            tv_norecords.setVisibility(View.GONE);
                            rv_orderhistory.setVisibility(View.VISIBLE);
                            setView();
                        }

                    } else {
                        swipeRefreshLayout.setVisibility(View.GONE);
                        rv_orderhistory.setVisibility(View.GONE);
                        tv_norecords.setVisibility(View.VISIBLE);
                        tv_norecords.setText(response.body().getMessage());
                    }




                }






            @Override
            public void onFailure(@NotNull Call<BookingHistoryResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"BookingHistoryResponseFlr"+t.getMessage());
            }
        });

    }
    private BookingHistoryRequest bookingHistoryRequest() {
        /*
          Customer_id : 5f05d8fef3090625a91f40c6

         */

        BookingHistoryRequest bookingHistoryRequest = new BookingHistoryRequest();
        bookingHistoryRequest.setCustomer_id(customerid);

        Log.w(TAG," BookingHistoryRequest"+ new Gson().toJson(bookingHistoryRequest));
        return bookingHistoryRequest;
    }

    private void setView() {

        rv_orderhistory.setLayoutManager(new LinearLayoutManager(this));
        rv_orderhistory.setItemAnimator(new DefaultItemAnimator());
        orderHistoryListAdapter = new OrderHistoryListAdapter(getApplicationContext(),bookingHistoryResponseList , rv_orderhistory);
        rv_orderhistory.setAdapter(orderHistoryListAdapter);

        orderHistoryListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > bookingHistoryResponseList.size()) {
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