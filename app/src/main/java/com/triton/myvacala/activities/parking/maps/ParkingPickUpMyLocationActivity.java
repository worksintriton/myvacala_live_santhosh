package com.triton.myvacala.activities.parking.maps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkingPickUpMyLocationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.btn_selectcity)
    Button btn_selectcity;
    String TAG = "ParkingPickUpMyLocationActivity";

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_location_old_user_parking);
        Log.w(TAG,"onCreate--->");

        ButterKnife.bind(this);
        toolbar_title.setText(getResources().getString(R.string.pickuplocation));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_selectcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkingPickUpMyLocationActivity.this,ParkingSelectYourCityOldUserActivity.class));
            }
        });

        bottom_navigation_parking.setSelectedItemId(R.id.account);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }//end of oncreate

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
        Intent intent = new Intent(ParkingPickUpMyLocationActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
        callDirections("1");
    }
}