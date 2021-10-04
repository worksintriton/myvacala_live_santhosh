package com.triton.myvacala.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.ChooseYourAddressListBottomCartAdapter;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.fragment.CartFragment;
import com.triton.myvacala.interfaces.LocationIdListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.GetCardDetailsResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseYourAddressBottomCartActivity extends AppCompatActivity implements LocationIdListener {


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    private String TAG = "ChooseYourAddressBottomCartActivity";

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

    ChooseYourAddressListBottomCartAdapter chooseYourAddressListAdapter;
    private SharedPreferences preferences;

    private String bookingdateandtime,BookingDate,BookingTime;

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
            bookingdateandtime = extras.getString("bookingdateandtime");
            BookingDate = extras.getString("BookingDate");
            BookingTime = extras.getString("BookingTime");
            Log.w(TAG,"fromactivity: "+fromactivity);


        }




        locationAvailableBeanArrayList = CartFragment.locationAvailableBeanArrayList;

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
        chooseYourAddressListAdapter = new ChooseYourAddressListBottomCartAdapter(getApplicationContext(),locationAvailableBeanArrayList , rv_locationlist,fromactivity,this);
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


    @Override
    public void onItemCheck(String locationID) {

        if(locationID != null && !locationID.isEmpty()){
            Log.w(TAG,"locationID : "+locationID);
            callDirections(locationID);
        }

    }

    public void callDirections(String locationID){
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        intent.putExtra("tag","4");
        intent.putExtra("locationID",locationID);
        intent.putExtra("bookingdateandtime",bookingdateandtime);
        intent.putExtra("BookingDate",BookingDate);
        intent.putExtra("BookingTime",BookingTime);
        startActivity(intent);
        finish();


    }
}