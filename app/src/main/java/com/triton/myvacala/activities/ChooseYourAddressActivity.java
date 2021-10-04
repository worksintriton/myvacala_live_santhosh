package com.triton.myvacala.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.triton.myvacala.adapter.ChooseYourAddressListAdapter;
import com.triton.myvacala.adapter.LocationListAdapter;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.fragment.CartFragment;
import com.triton.myvacala.interfaces.LocationIdListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.GetCardDetailsResponse;
import com.triton.myvacala.responsepojo.LocationListResponse;
import com.triton.myvacala.responsepojo.SubServiceListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseYourAddressActivity extends AppCompatActivity  {


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    private String TAG = "ChooseYourAddressActivity";

    SessionManager session;
    String name = "", customerid ="";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;



    @BindView(R.id.rv_locationlist)
    RecyclerView rv_locationlist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    String fromactivity;






    private ArrayList<GetCardDetailsResponse.LocationAvailableBean> locationAvailableBeanArrayList;

    ChooseYourAddressListAdapter chooseYourAddressListAdapter;
    private SharedPreferences preferences;
    private String masterservicename,masterserviceid,serviceid,servicename,selectedVehicleId,BookingDate,BookingTime,bookingdateandtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_address);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ButterKnife.bind(this);

        toolbar_title.setText(getResources().getString(R.string.chooseyourlocation));
        avi_indicator.setVisibility(View.GONE);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");
            masterservicename = extras.getString("masterservicename");
            masterserviceid = extras.getString("masterserviceid");
            serviceid = extras.getString("serviceid");
            servicename = extras.getString("servicename");
            selectedVehicleId = extras.getString("selectedVehicleId");
            selectedVehicleId = extras.getString("selectedVehicleId");
            BookingDate = extras.getString("BookingDate");
            BookingTime = extras.getString("BookingTime");
            bookingdateandtime = extras.getString("bookingdateandtime");

            Log.w(TAG,"Intent : "+BookingDate+" "+BookingTime);





            Log.w(TAG,"fromactivity: "+fromactivity);


        }




        locationAvailableBeanArrayList = CartActivity.locationAvailableBeanArrayList;

        if(locationAvailableBeanArrayList != null && locationAvailableBeanArrayList.size()>0){
            Log.w(TAG,"locationAvailableBeanArrayList : "+new Gson().toJson(locationAvailableBeanArrayList));
            setView();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void setView() {
        //rv_locationlist.setLayoutManager(new GridLayoutManager(this, 3));

        rv_locationlist.setLayoutManager(new LinearLayoutManager(this));
        rv_locationlist.setItemAnimator(new DefaultItemAnimator());

        chooseYourAddressListAdapter = new ChooseYourAddressListAdapter(getApplicationContext(),locationAvailableBeanArrayList ,
                rv_locationlist,fromactivity,masterservicename,masterserviceid,serviceid,servicename,selectedVehicleId,BookingDate,BookingTime,bookingdateandtime);
        rv_locationlist.setAdapter(chooseYourAddressListAdapter);

        chooseYourAddressListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > locationAvailableBeanArrayList.size()) {
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