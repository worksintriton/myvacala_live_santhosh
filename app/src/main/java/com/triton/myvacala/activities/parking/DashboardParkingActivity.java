package com.triton.myvacala.activities.parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.DashboardActivity;
import com.triton.myvacala.fragment.AccountFragmentParking;
import com.triton.myvacala.fragment.BookingHistoryFragmentParking;
import com.triton.myvacala.fragment.HomeFragmentParking;
import com.triton.myvacala.fragment.MyVehicleFragmentParking;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardParkingActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "DasboardParkingActivity" ;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    final Fragment homeFragmentParking = new HomeFragmentParking();
    final Fragment bookingHistoryFragmentParking = new BookingHistoryFragmentParking();
    final Fragment myVehicleFragmentParking = new MyVehicleFragmentParking();
    final Fragment accountFragmentParking = new AccountFragmentParking();
    Fragment active = homeFragmentParking;
    String tag;
    private String fromactivity,cityname;
    private double lat,lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_parking);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");

        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityname = extras.getString("cityname");
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
            fromactivity = extras.getString("fromactivity");

            Log.w(TAG,"fromactivity : "+fromactivity+"lat : "+lat+"lon : "+lon+"cityname :"+cityname);


        }

        tag = getIntent().getStringExtra("tag");
        Log.w(TAG,"tag : "+tag);
        if(tag != null){
            if(tag.equalsIgnoreCase("1")){
                active = homeFragmentParking;
                // tag = null;
                bottom_navigation_parking.setSelectedItemId(R.id.home);
                loadFragment(new HomeFragmentParking());
            }else if(tag.equalsIgnoreCase("2")){
                active = bookingHistoryFragmentParking;
                // tag = null;
                bottom_navigation_parking.setSelectedItemId(R.id.bookinghistory);
                loadFragment(new BookingHistoryFragmentParking());
            }else if(tag.equalsIgnoreCase("3")){
                active = myVehicleFragmentParking;
                // tag = null;
                bottom_navigation_parking.setSelectedItemId(R.id.myvehicle);
                loadFragment(new MyVehicleFragmentParking());
            } else if(tag.equalsIgnoreCase("4")){
                active = accountFragmentParking;
                //tag = null;
                bottom_navigation_parking.setSelectedItemId(R.id.account);
                loadFragment(new AccountFragmentParking());
            }
        }else{
            bottom_navigation_parking.setSelectedItemId(R.id.home);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String active_tag = "1";
            transaction.replace(R.id.main_container, active, active_tag);
            transaction.commit();
            //bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }


        bottom_navigation_parking.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                if (bottom_navigation_parking.getSelectedItemId() == R.id.home) {
                        gotoMainDashboard();
                }else{
                    replaceFragment(new HomeFragmentParking());
                }

                break;


            case R.id.bookinghistory:
                replaceFragment(new BookingHistoryFragmentParking());
                break;


            case R.id.myvehicle:
                replaceFragment(new MyVehicleFragmentParking());
                break;
            case R.id.account:
                replaceFragment(new AccountFragmentParking());
                break;

            default:
                return false;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();

    }

    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        Log.w(TAG,"loadFragment --->");

        if(fromactivity != null){
            Log.w(TAG,"loadFragment if--->");
            Log.w(TAG,"fromactivity loadFragment : "+fromactivity);

            if(fromactivity.equalsIgnoreCase("ParkingPlacesSearchActivity")) {
                bundle.putString("fromactivity", fromactivity);
                bundle.putString("cityname", cityname);
                bundle.putDouble("lat", lat);
                bundle.putDouble("lon", lon);
                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG,"fromactivity : "+fromactivity+"lat : "+lat+"lon : "+lon+"cityname :"+cityname);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        }else {


            Log.w(TAG,"loadFragment else--->");



            // set Fragmentclass Arguments
            fragment.setArguments(bundle);

            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w(TAG,"onActivityResult--->");
        Log.w(TAG,"resultCode---->"+resultCode+"requestCode: "+requestCode);

        Fragment fragment = Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.main_container));
        fragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        if (bottom_navigation_parking.getSelectedItemId() == R.id.home) {
            Log.w(TAG,"onBackPressed--->"+bottom_navigation_parking.getSelectedItemId());
            gotoMainDashboard();

        }else{
            bottom_navigation_parking.setSelectedItemId(R.id.home);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container,new HomeFragmentParking());
            transaction.commit();
        }
    }

    private void gotoMainDashboard(){
        Intent intent = new Intent(DashboardParkingActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}