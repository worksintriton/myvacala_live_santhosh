package com.triton.myvacala.activities.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.responsepojo.AdditionHrsResponse;
import com.triton.myvacala.responsepojo.ParkingBookingCreateResponse;
import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CheckedOutActivity extends AppCompatActivity {

    private static final String TAG = "CheckedOutActivity";

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txt_buildingname)
    TextView txt_buildingname;

    @BindView(R.id.txt_address)
    TextView txt_address;

    @BindView(R.id.txt_bookingid)
    TextView txt_bookingid;

    @BindView(R.id.txt_amountpaid)
    TextView txt_amountpaid;


    @BindView(R.id.txt_bookingslotwindow)
    TextView txt_bookingslotwindow;

    @BindView(R.id.cv_vehicleimage)
    CircleImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;

    String buildingname,address,sharelink,amount,bookingid,slotdetails,startdate,starttime,enddate,endtime,totalhours,reachtime,distance;
    private ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanArrayList;
    private ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean> vehicledetailslist;
    private ArrayList<AdditionHrsResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanAddhrsArrayList;



    String headerVehicleimg, headerVehicleName, headerVehicleBrandName, headerVehicleFuelTypeName, headerVehicleFuelTypeBackgroundcolor, vehicleNumber;

    private String outputDateEndDateex;

    private String id,parkingdetailsid;
    private String vehicleid,vehicletypeid;
    private String bookingstatus;
    private String fromactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out);

        ButterKnife.bind(this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getData();

        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    String active_tag = "1";
                    callDirections(active_tag);
                    break;


                case R.id.bookinghistory:
                    active_tag = "2";
                    callDirections(active_tag);
                    break;


                case R.id.myvehicle:
                    active_tag = "3";
                    callDirections(active_tag);
                    break;

                case R.id.account:
                    active_tag = "4";
                    callDirections(active_tag);
                    break;

            }
            return true;
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(CheckedOutActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            buildingname = extras.getString("buildingname");
            address = extras.getString("address");
            sharelink = extras.getString("sharelink");
            amount = extras.getString("amount");
            bookingid = extras.getString("bookingid");
            slotdetails = extras.getString("slotdetails");

            startdate = extras.getString("startdate");
            starttime = extras.getString("starttime");
            enddate = extras.getString("enddate");
            endtime = extras.getString("endtime");
            totalhours = extras.getString("totalhours");
            reachtime = extras.getString("reachtime");
            distance = extras.getString("distance");
            bookingstatus = extras.getString("bookingstatus");


            id = extras.getString("id");
            parkingdetailsid = extras.getString("parkingdetailsid");

              fromactivity = extras.getString("fromactivity");


            if (fromactivity != null && fromactivity.equalsIgnoreCase("BookingHistoryListAdapter")) {
                fromactivity = "BookingHistoryListAdapter";
                bottom_navigation_parking.setSelectedItemId(R.id.bookinghistory);
                vehicledetailslist = (ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicledetailslist");
                Log.w(TAG, "vehicledetailslist:" + new Gson().toJson(vehicledetailslist));

                if (vehicledetailslist != null) {
                    getVehicleDataBookinghistory();
                }
            }
            else if(fromactivity != null && fromactivity.equalsIgnoreCase("PaymentMethodTimeExtensionActivity")){
                fromactivity = "PaymentMethodTimeExtensionActivity";
                Log.w(TAG,"fromactivity ELSE IF : "+fromactivity);
                bottom_navigation_parking.setSelectedItemId(R.id.bookinghistory);
                vehicleDetailsBeanAddhrsArrayList = ( ArrayList<AdditionHrsResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicleDetailsBeanAddhrsArrayList");
                Log.w(TAG,  " vehicleDetailsBeanAddhrsArrayList : "+new Gson().toJson(vehicleDetailsBeanAddhrsArrayList));

                if(vehicleDetailsBeanAddhrsArrayList != null){
                    getVehicleDataAddhrs();
                }

            }
            else {
                fromactivity = TAG;
                bottom_navigation_parking.setSelectedItemId(R.id.home);
                vehicleDetailsBeanArrayList = (ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicleDetailsBeanArrayList");
                Log.w(TAG, "vehicleDetailsBeanArrayList:" + new Gson().toJson(vehicleDetailsBeanArrayList));
                if (vehicleDetailsBeanArrayList != null) {
                    getVehicleData();
                }
            }






            Log.w(TAG,"buildingname :"+buildingname+"address : "+address+"sharelink :"+sharelink+"amount :"+amount+"bookingid : "+bookingid+"slotdetails : "+slotdetails+"distance : "+distance+"reachtime "+reachtime);
            if(buildingname != null){
                txt_buildingname.setText(buildingname);
            }
            if(address != null){
                txt_address.setText(address);
            } if(amount != null){
                txt_amountpaid.setText("\u20B9 "+amount);
            } if(bookingid != null){
                txt_bookingid.setText(bookingid);
            }




            @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormatextension = new SimpleDateFormat("dd MMM");
            String inputDateStartDate = startdate;
            String inputDateEndDate = enddate;
            Date startdate,enddate;
            Date enddate_ex;
            String outputDateStartDate = null,outputDateEndDate = null;

            try {
                startdate = inputFormat.parse(inputDateStartDate);
                enddate = inputFormat.parse(inputDateEndDate);
                enddate_ex = inputFormat.parse(inputDateEndDate);

                if (startdate != null) {
                    outputDateStartDate = outputFormat.format(startdate);
                }
                if (enddate != null) {
                    outputDateEndDate = outputFormat.format(enddate);
                }

                if(enddate_ex != null){
                    outputDateEndDateex = outputFormatextension.format(enddate_ex);
                    Log.w(TAG,"outputDateEndDateex-->"+outputDateEndDateex);
                }
                Log.w(TAG,"totalhours :"+totalhours);
                txt_bookingslotwindow.setText(outputDateStartDate+" at "+starttime+" to "+outputDateEndDate+" at "+endtime+"("+totalhours+" Hours"+")");
            } catch (ParseException e) {
                e.printStackTrace();
            }












        }

    }

    private void getVehicleData() {
        if (vehicleDetailsBeanArrayList != null && vehicleDetailsBeanArrayList.size() > 0) {
            for (int i = 0; i < vehicleDetailsBeanArrayList.size(); i++) {
                String vehicletype = vehicleDetailsBeanArrayList.get(i).getVehicletype_Name();
                String id = vehicleDetailsBeanArrayList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id );

                headerVehicleimg = vehicleDetailsBeanArrayList.get(i).getVehicle_Image();
                headerVehicleName = vehicleDetailsBeanArrayList.get(i).getVehicle_Name();
                headerVehicleBrandName = vehicleDetailsBeanArrayList.get(i).getVehicle_Brand_Name();
                headerVehicleFuelTypeName = vehicleDetailsBeanArrayList.get(i).getFuel_Type_Name();
                headerVehicleFuelTypeBackgroundcolor = vehicleDetailsBeanArrayList.get(i).getFuel_Type_Background_Color();
                vehicleNumber = vehicleDetailsBeanArrayList.get(i).getVehicle_No();
                 vehicleid = vehicleDetailsBeanArrayList.get(i).get_id();
                 vehicletypeid = vehicleDetailsBeanArrayList.get(i).getVehicletype_id();
                Log.w(TAG, "getVehicleData headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);


            }
            if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {

                Glide.with(this)
                        .load(headerVehicleimg)
                        .into(cv_vehicleimage);

            } else {
                Glide.with(this)
                        .load(R.drawable.logo)
                        .into(cv_vehicleimage);

            }
            if (headerVehicleName != null) {
                txt_vehiclename.setText(headerVehicleName);
            } else {
                txt_vehiclename.setText("");
            }
            if (vehicleNumber != null) {
                txt_vehicle_number.setText(vehicleNumber);
            } else {
                txt_vehicle_number.setText("");
            }
        }


    }
    private void getVehicleDataBookinghistory() {
        if (vehicledetailslist != null && vehicledetailslist.size() > 0) {
            for (int i = 0; i < vehicledetailslist.size(); i++) {
                String vehicletype = vehicledetailslist.get(i).getVehicletype_Name();
                String id = vehicledetailslist.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id );

                headerVehicleimg = vehicledetailslist.get(i).getVehicle_Image();
                headerVehicleName = vehicledetailslist.get(i).getVehicle_Name();
                headerVehicleBrandName = vehicledetailslist.get(i).getVehicle_Brand_Name();
                headerVehicleFuelTypeName = vehicledetailslist.get(i).getFuel_Type_Name();
                headerVehicleFuelTypeBackgroundcolor = vehicledetailslist.get(i).getFuel_Type_Background_Color();
                vehicleNumber = vehicledetailslist.get(i).getVehicle_No();
                 vehicleid = vehicledetailslist.get(i).get_id();
                 vehicletypeid = vehicledetailslist.get(i).getVehicletype_id();
                Log.w(TAG, "getVehicleDataBookinghistory headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);


            }
            if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {

                Glide.with(this)
                        .load(headerVehicleimg)
                        .into(cv_vehicleimage);

            } else {
                Glide.with(this)
                        .load(R.drawable.logo)
                        .into(cv_vehicleimage);

            }
            if (headerVehicleName != null) {
                txt_vehiclename.setText(headerVehicleName);
            } else {
                txt_vehiclename.setText("");
            }
            if (vehicleNumber != null) {
                txt_vehicle_number.setText(vehicleNumber);
            } else {
                txt_vehicle_number.setText("");
            }
        }


    }
    private void getVehicleDataAddhrs() {
        if (vehicleDetailsBeanAddhrsArrayList != null && vehicleDetailsBeanAddhrsArrayList.size() > 0) {
            for (int i = 0; i < vehicleDetailsBeanAddhrsArrayList.size(); i++) {
                String vehicletype = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_Name();
                String id = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);

                headerVehicleimg = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_Image();
                headerVehicleName = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_Name();
                headerVehicleBrandName = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_Brand_Name();
                headerVehicleFuelTypeName = vehicleDetailsBeanAddhrsArrayList.get(i).getFuel_Type_Name();
                headerVehicleFuelTypeBackgroundcolor = vehicleDetailsBeanAddhrsArrayList.get(i).getFuel_Type_Background_Color();
                vehicleNumber = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_No();
                vehicleid = vehicleDetailsBeanAddhrsArrayList.get(i).get_id();
                vehicletypeid = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_id();
                Log.w(TAG, "getVehicleData headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);


            }
            if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {

                Glide.with(this)
                        .load(headerVehicleimg)
                        .into(cv_vehicleimage);

            } else {
                Glide.with(this)
                        .load(R.drawable.logo)
                        .into(cv_vehicleimage);

            }
            if (headerVehicleName != null) {
                txt_vehiclename.setText(headerVehicleName);
            } else {
                txt_vehiclename.setText("");
            }
            if (vehicleNumber != null) {
                txt_vehicle_number.setText(vehicleNumber);
            } else {
                txt_vehicle_number.setText("");
            }
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
      callDirections("2");
    }
}