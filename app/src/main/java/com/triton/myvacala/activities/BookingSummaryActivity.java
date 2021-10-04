package com.triton.myvacala.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triton.myvacala.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingSummaryActivity extends AppCompatActivity {

    String amountpaid,bookingid,registrationno,servicedate,servicetime,service,subservice,vehicledetails;


    @BindView(R.id.txt_service)
    TextView txt_service;

    @BindView(R.id.txt_subservices)
    TextView txt_subservices;

    @BindView(R.id.txt_bookingid)
    TextView txt_bookingid;

    @BindView(R.id.txt_vehicledetails)
    TextView txt_vehicledetails;

    @BindView(R.id.txt_registrationno)
    TextView txt_registrationno;

    @BindView(R.id.txt_servicedate)
    TextView txt_servicedate;

    @BindView(R.id.txt_servicetime)
    TextView txt_servicetime;

    @BindView(R.id.txt_amountpaid)
    TextView txt_amountpaid;

    String TAG = "BookingSummaryActivity";

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;
    String active_tag = "1";


    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txt_discountamount)
    TextView txt_discountamount;

    @BindView(R.id.ll_discountamount)
    LinearLayout ll_discountamount;

    @BindView(R.id.ll_userissues)
    LinearLayout ll_userissues;

    @BindView(R.id.txt_userissues)
    TextView txt_userissues;



    private String discountamount;
    private String userissues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        ButterKnife.bind(this);

        Log.w(TAG,"onCreate--->");




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            amountpaid = extras.getString("amountpaid");
            bookingid = extras.getString("bookingid");
            registrationno = extras.getString("registrationno");
            servicedate = extras.getString("servicedate");
            servicetime = extras.getString("servicetime");
            subservice = extras.getString("subservice");
            service = extras.getString("service");
            vehicledetails = extras.getString("vehicledetails");
            discountamount = extras.getString("discountamount");
            userissues = extras.getString("userissues");

            if(discountamount != null && !discountamount.isEmpty()){
                ll_discountamount.setVisibility(View.VISIBLE);
                txt_discountamount.setText("\u20B9"+" "+discountamount);
            }else{
                ll_discountamount.setVisibility(View.GONE);

            }


            if(userissues != null && !userissues.isEmpty()){
                ll_userissues.setVisibility(View.VISIBLE);
                txt_userissues.setText(userissues);
            }else{
                ll_userissues.setVisibility(View.GONE);

            }


            @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(servicedate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);


            txt_service.setText(service);
            txt_subservices.setText(subservice);
            txt_bookingid.setText(bookingid);
            txt_vehicledetails.setText(vehicledetails);
            txt_registrationno.setText(registrationno);
            txt_servicedate.setText(outputDateStr);
            txt_servicetime.setText(servicetime);
            txt_amountpaid.setText("\u20B9"+" "+amountpaid);







        }

        bottom_navigation_view.setSelectedItemId(R.id.home);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();

            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                //active = homeFragment;
                active_tag = "1";
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
    };
    public void callDirections(String tag){
        Intent intent = new Intent(BookingSummaryActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callDirections("1");
    }
}