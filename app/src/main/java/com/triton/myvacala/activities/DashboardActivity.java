package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.fragment.AccountFragment;
import com.triton.myvacala.fragment.CartFragment;
import com.triton.myvacala.fragment.HomeFragment;
import com.triton.myvacala.fragment.MyVehicleFragment;
import com.triton.myvacala.fragment.SearchFragment;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements Serializable, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    private String TAG ="DashboardActivity";

    final Fragment homeFragment = new HomeFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment myVehicleFragment = new MyVehicleFragment();
    final Fragment cartFragment = new CartFragment();
    final Fragment accountFragment = new AccountFragment();
    private String active_tag = "1";


    Fragment active = homeFragment;
    String tag;

    String selectedVehicleId,selectedVehicleType,masterserviceid,fromactivity;
    String serviceid,servicename,masterservicename;

     private String locationID;
     private String bookingdateandtime;
    String BookingDate, BookingTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");



        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedVehicleId = extras.getString("selectedVehicleId");
            selectedVehicleType = extras.getString("selectedVehicleType");
            masterserviceid = extras.getString("masterserviceid");
            fromactivity = extras.getString("fromactivity");
           Log.w(TAG,"selectedVehicleId : "+selectedVehicleId+" "+"selectedVehicleType : "+selectedVehicleType+" "+"masterserviceid : "+masterserviceid);
           Log.w(TAG,"fromactivity : "+fromactivity);
            serviceid = extras.getString("serviceid");
            servicename = extras.getString("servicename");
            masterservicename = extras.getString("masterservicename");
            masterservicename = extras.getString("masterservicename");
            locationID = extras.getString("locationID");
            bookingdateandtime = extras.getString("bookingdateandtime");
            BookingDate = extras.getString("BookingDate");
            BookingTime = extras.getString("BookingTime");
            Log.w(TAG,"locationID--->"+locationID);
        }

            tag = getIntent().getStringExtra("tag");
           if(tag != null){
              if(tag.equalsIgnoreCase("1")){
                  active = homeFragment;
                 // tag = null;
                  bottom_navigation_view.setSelectedItemId(R.id.home);
                  loadFragment(new HomeFragment());
              }else if(tag.equalsIgnoreCase("2")){
                  active = searchFragment;
                 // tag = null;
                  bottom_navigation_view.setSelectedItemId(R.id.search);
                  loadFragment(new SearchFragment());
              }else if(tag.equalsIgnoreCase("3")){
                  active = myVehicleFragment;
                 // tag = null;
                  bottom_navigation_view.setSelectedItemId(R.id.myvehicle);
                  loadFragment(new MyVehicleFragment());
              }else if(tag.equalsIgnoreCase("4")){
                  active = cartFragment;
                 // tag = null;
                  bottom_navigation_view.setSelectedItemId(R.id.cart);
                  loadFragment(new CartFragment());
              } else if(tag.equalsIgnoreCase("5")){
                  active = accountFragment;
                  //tag = null;
                  bottom_navigation_view.setSelectedItemId(R.id.account);
                  loadFragment(new AccountFragment());
              }
           }else{
               bottom_navigation_view.setSelectedItemId(R.id.home);
               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               transaction.replace(R.id.main_container, active, active_tag);
               transaction.commit();
               //bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
           }
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);



    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
//
//
//        if (active != null) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.main_container, active, active_tag);
//            transaction.commit();
//        }
//
//
//
//
//        return true;
//    };

    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if(fromactivity != null){
            Log.w(TAG,"fromactivity loadFragment : "+fromactivity);

            if(fromactivity.equalsIgnoreCase("PopularServiceActivity")) {
                bundle.putString("fromactivity", fromactivity);
                bundle.putString("selectedVehicleId", selectedVehicleId);
                bundle.putString("selectedVehicleType", selectedVehicleType);
                bundle.putString("masterserviceid", masterserviceid);
                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG,"selectedVehicleId : "+selectedVehicleId+" "+"selectedVehicleType : "+selectedVehicleType+" "+"masterserviceid : "+masterserviceid);
                Log.w(TAG,"fromactivity : "+fromactivity);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
            else if(fromactivity.equalsIgnoreCase("SubServicesActivity")) {
                bundle.putString("fromactivity", fromactivity);
                bundle.putString("selectedVehicleId", selectedVehicleId);
                bundle.putString("selectedVehicleType", selectedVehicleType);
                bundle.putString("masterserviceid", masterserviceid);

                bundle.putString("serviceid", serviceid);
                bundle.putString("servicename", servicename);
                bundle.putString("masterservicename", masterservicename);

                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG,"selectedVehicleId : "+selectedVehicleId+" "+"selectedVehicleType : "+selectedVehicleType+" "+"masterserviceid : "+masterserviceid);
                Log.w(TAG,"fromactivity : "+fromactivity);

                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        }else {


            bundle.putString("locationID", locationID);
            bundle.putString("bookingdateandtime", bookingdateandtime);
            bundle.putString("BookingDate", BookingDate);
            bundle.putString("BookingTime", BookingTime);

            Log.w(TAG,"locationid-->"+locationID+"bookingdateandtime : "+bookingdateandtime);


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
    public void onBackPressed() {
        Log.w(TAG,"tag : "+tag);
        if (bottom_navigation_view.getSelectedItemId() == R.id.home) {
            new android.app.AlertDialog.Builder(DashboardActivity.this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DashboardActivity.this.finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else if(tag != null ){
            Log.w(TAG,"Else IF--->"+"fromactivity : "+fromactivity);
            if(fromactivity != null){
                if(fromactivity.equalsIgnoreCase("PopularServiceActivity")) {
                    Intent intent = new Intent(DashboardActivity.this, PopularServiceActivity.class);
                    intent.putExtra("fromactivity", "PopularServiceActivity");
                    intent.putExtra("selectedVehicleId", selectedVehicleId);
                    intent.putExtra("selectedVehicleType", selectedVehicleType);
                    intent.putExtra("masterserviceid", masterserviceid);
                    startActivity(intent);
                }
                else if(fromactivity.equalsIgnoreCase("SubServicesActivity")) {
                    Intent intent = new Intent(DashboardActivity.this, SubServicesActivity.class);
                    intent.putExtra("fromactivity", "SubServicesActivity");
                    intent.putExtra("selectedVehicleId", selectedVehicleId);
                    intent.putExtra("selectedVehicleType", selectedVehicleType);
                    intent.putExtra("masterserviceid", masterserviceid);
                    intent.putExtra("serviceid", serviceid);
                    intent.putExtra("servicename", servicename);
                    intent.putExtra("masterservicename", masterservicename);
                    startActivity(intent);
                }
            }else{
                bottom_navigation_view.setSelectedItemId(R.id.home);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container,new HomeFragment());
                transaction.commit();
            }


        }else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container,new HomeFragment());
            transaction.commit();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container,fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                //active = homeFragment;
                // active_tag = "1";
                replaceFragment(new HomeFragment());
                break;


            case R.id.search:
                //active = searchFragment;
                replaceFragment(new SearchFragment());

                //active_tag = "2";
                break;


            case R.id.myvehicle:
                //active = myVehicleFragment;
                replaceFragment(new MyVehicleFragment());

                // active_tag = "3";
                break;
            case R.id.cart:
                // active = cartFragment;
                replaceFragment(new CartFragment());

                // active_tag = "4";
                break;
            case R.id.account:
                //active = accountFragment;
                replaceFragment(new AccountFragment());

                //active_tag = "5";
                break;

            default:
                return  false;
        }
        return true;
    }
}