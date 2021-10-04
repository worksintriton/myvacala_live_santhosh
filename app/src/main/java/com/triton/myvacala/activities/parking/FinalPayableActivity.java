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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.payment.PaymentMethodTimeExtensionActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.AdditionHrsRequest;
import com.triton.myvacala.requestpojo.CheckTimesRequest;
import com.triton.myvacala.responsepojo.AdditionHrsResponse;
import com.triton.myvacala.responsepojo.CheckTimesResponse;
import com.triton.myvacala.responsepojo.ParkingBookingCreateResponse;
import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalPayableActivity extends AppCompatActivity {

    private static final String TAG = "FinalPayableActivity";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

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
    @BindView(R.id.txt_floor_block_slot)
    TextView txt_floor_block_slot;

    @BindView(R.id.txt_bookingslotwindow)
    TextView txt_bookingslotwindow;

    @BindView(R.id.cv_vehicleimage)
    CircleImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;

    @BindView(R.id.txt_hoursextension)
    TextView txt_hoursextension;

    @BindView(R.id.txt_hoursextension_amount)
    TextView txt_hoursextension_amount;

    @BindView(R.id.txt_total_bill_value)
    TextView txt_total_bill_value;

    @BindView(R.id.txt_time_extension_amount_paid)
    TextView txt_time_extension_amount_paid;

    @BindView(R.id.txt_topay)
    TextView txt_topay;

    @BindView(R.id.rlpay)
    RelativeLayout rlpay;

    String buildingname, address, sharelink, amount, bookingid, slotdetails, startdate, starttime, enddate, endtime, totalhours, reachtime, distance;

    String headerVehicleimg, headerVehicleName, headerVehicleBrandName, headerVehicleFuelTypeName, headerVehicleFuelTypeBackgroundcolor, vehicleNumber;

    private ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanArrayList;
    private ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean> vehicledetailslist ;
    private ArrayList<AdditionHrsResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanAddhrsArrayList ;
    private String outputDateEndDateex;
    private String bookingidres,additionalbookinghrs,overalltimeduration,extratime;
    private int totalprice,finaltotal,alreadypay,additionalbookingamout,overallamountpaid;
    private String fromactivity;
    private String fromapi;
    private String bookingstatus = "";

    private String outputDateStartDate, outputDateEndDate;

    private String durationDates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_payable);

        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        getData();

        rlpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FinalPayableActivity.this, PaymentMethodTimeExtensionActivity.class);
                intent.putExtra("buildingname", buildingname);
                intent.putExtra("address", address);
                intent.putExtra("sharelink", sharelink);
                intent.putExtra("amount", amount);
                intent.putExtra("bookingid", bookingid);
                intent.putExtra("slotdetails", slotdetails);

                intent.putExtra("startdate", startdate);
                intent.putExtra("starttime", starttime);
                intent.putExtra("enddate", enddate);
                intent.putExtra("endtime", endtime);
                intent.putExtra("totalhours", totalhours);
                intent.putExtra("reachtime", reachtime);
                intent.putExtra("distance", distance);

                intent.putExtra("totalprice", totalprice);
                intent.putExtra("finaltotal", finaltotal);
                intent.putExtra("alreadypay", alreadypay);
                intent.putExtra("bookingidres", bookingidres);
                intent.putExtra("additionalbookinghrs", additionalbookinghrs);
                intent.putExtra("additionalbookingamout", additionalbookingamout);
                intent.putExtra("overalltimeduration", overalltimeduration);
                intent.putExtra("overallamountpaid", overallamountpaid);
                intent.putExtra("extratime", extratime);
                intent.putExtra("bookingstatus", bookingstatus);
                intent.putExtra("fromactivity", fromactivity);
                intent.putExtra("fromapi", fromapi);
                intent.putExtra("durationDates", durationDates);

                intent.putExtra("vehicleDetailsBeanArrayList", vehicleDetailsBeanArrayList);
                intent.putExtra("vehicledetailslist", vehicledetailslist);

                startActivity(intent);

            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bottom_navigation_parking.setSelectedItemId(R.id.home);
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
        Intent intent = new Intent(FinalPayableActivity.this, DashboardParkingActivity.class);
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

            totalprice = extras.getInt("totalprice");
            finaltotal = extras.getInt("finaltotal");
            alreadypay = extras.getInt("alreadypay");
            bookingidres = extras.getString("bookingidres");
            additionalbookinghrs = extras.getString("additionalbookinghrs");
            additionalbookingamout = extras.getInt("additionalbookingamout");
            overalltimeduration = extras.getString("overalltimeduration");
            overallamountpaid = extras.getInt("overallamountpaid");
            extratime = extras.getString("extratime");
            bookingstatus = extras.getString("bookingstatus");
            fromactivity = extras.getString("fromactivity");
            fromapi = extras.getString("fromapi");


            if(extratime != null){
                txt_hoursextension.setText("+"+extratime+" Hours Extension");
            }

            if(additionalbookingamout != 0){
                txt_hoursextension_amount.setText("\u20B9 " + additionalbookingamout);

            }


            if(additionalbookingamout != 0){
                txt_total_bill_value.setText("\u20B9 " + finaltotal);

            }

            if(overallamountpaid != 0){
                txt_time_extension_amount_paid.setText("\u20B9 " + alreadypay);

            }
            if(additionalbookingamout != 0){
                txt_topay.setText("\u20B9 " + totalprice);

            }













            String fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"fromactivity : "+fromactivity);
            if (fromactivity != null && fromactivity.equalsIgnoreCase("BookingHistoryListAdapter")) {
                vehicledetailslist = (ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicledetailslist");
                Log.w(TAG, "vehicledetailslist:" + new Gson().toJson(vehicledetailslist));

                if (vehicledetailslist != null && vehicledetailslist.size()>0 ) {
                    getVehicleDataBookinghistory();
                }
            }else if(fromactivity != null && fromactivity.equalsIgnoreCase("PaymentMethodTimeExtensionActivity")){
                vehicleDetailsBeanAddhrsArrayList = ( ArrayList<AdditionHrsResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicleDetailsBeanAddhrsArrayList");
                Log.w(TAG,  " vehicleDetailsBeanAddhrsArrayList : "+new Gson().toJson(vehicleDetailsBeanAddhrsArrayList));

                if(vehicleDetailsBeanAddhrsArrayList != null){
                    getVehicleDataAddhrs();
                }

            }
            else {
                vehicleDetailsBeanArrayList = (ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicleDetailsBeanArrayList");
                Log.w(TAG, "vehicleDetailsBeanArrayList:" + new Gson().toJson(vehicleDetailsBeanArrayList));

                if (vehicleDetailsBeanArrayList != null && vehicleDetailsBeanArrayList.size() >0) {
                    getVehicleData();
                }
            }





            Log.w(TAG, "buildingname :" + buildingname + "address : " + address + "sharelink :" + sharelink + "amount :" + amount + "bookingid : " + bookingid + "slotdetails : " + slotdetails + "distance : " + distance + "reachtime " + reachtime);
            if (buildingname != null) {
                txt_buildingname.setText(buildingname);
            }
            if (address != null) {
                txt_address.setText(address);
            }
            if (amount != null) {
                txt_amountpaid.setText("\u20B9 " + alreadypay);
            }
            if (bookingid != null) {
                txt_bookingid.setText(bookingid);
            }
            if (slotdetails != null) {
                txt_floor_block_slot.setText(slotdetails);
            }

            if (distance != null && reachtime != null) {

            }

            checkTimesResponseCall(starttime,endtime,startdate,enddate);

            @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormatextension = new SimpleDateFormat("dd MMM");
            String inputDateStartDate = startdate;
            String inputDateEndDate = enddate;
            Date startdate, enddate;
            Date enddate_ex;

            try {
                startdate = inputFormat.parse(inputDateStartDate);
                enddate = inputFormat.parse(inputDateEndDate);
                enddate_ex = inputFormat.parse(inputDateEndDate);

                String startDateformat = "",endDateformat = "";

                if (startdate != null) {
                    outputDateStartDate = outputFormat.format(startdate);
                    startDateformat = outputFormatextension.format(startdate);

                }
                if (enddate != null) {
                    outputDateEndDate = outputFormat.format(enddate);
                    endDateformat = outputFormatextension.format(enddate);
                }

                if (enddate_ex != null) {
                    outputDateEndDateex = outputFormatextension.format(enddate_ex);
                    Log.w(TAG, "outputDateEndDateex-->" + outputDateEndDateex);
                }
                Log.w(TAG, "totalhours :" + totalhours);


                Log.w(TAG,"Endtime-->"+endtime);
                durationDates = startDateformat+", "+starttime+" to "+endDateformat+", "+endtime+"("+overalltimeduration+" Hours"+")";
               // txt_bookingslotwindow.setText(outputDateStartDate + " at " + starttime + " to " + outputDateEndDate + " at " + endtime + "(" + totalhours+" Hours" + ")");
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
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);

                headerVehicleimg = vehicleDetailsBeanArrayList.get(i).getVehicle_Image();
                headerVehicleName = vehicleDetailsBeanArrayList.get(i).getVehicle_Name();
                headerVehicleBrandName = vehicleDetailsBeanArrayList.get(i).getVehicle_Brand_Name();
                headerVehicleFuelTypeName = vehicleDetailsBeanArrayList.get(i).getFuel_Type_Name();
                headerVehicleFuelTypeBackgroundcolor = vehicleDetailsBeanArrayList.get(i).getFuel_Type_Background_Color();
                vehicleNumber = vehicleDetailsBeanArrayList.get(i).getVehicle_No();
                String vehicleid = vehicleDetailsBeanArrayList.get(i).get_id();
                String vehicletypeid = vehicleDetailsBeanArrayList.get(i).getVehicletype_id();
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
    private void getVehicleDataAddhrs() {
        Log.w(TAG,"getVehicleDataAddhrs()");
        if (vehicleDetailsBeanAddhrsArrayList != null && vehicleDetailsBeanAddhrsArrayList.size() > 0) {
            for (int i = 0; i < vehicleDetailsBeanAddhrsArrayList.size(); i++) {
                String vehicletype = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_Name();
                String id = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id );

                headerVehicleimg = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_Image();
                headerVehicleName = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_Name();
                headerVehicleBrandName = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_Brand_Name();
                headerVehicleFuelTypeName = vehicleDetailsBeanAddhrsArrayList.get(i).getFuel_Type_Name();
                headerVehicleFuelTypeBackgroundcolor = vehicleDetailsBeanAddhrsArrayList.get(i).getFuel_Type_Background_Color();
                vehicleNumber = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicle_No();
                String vehicleid = vehicleDetailsBeanAddhrsArrayList.get(i).get_id();
               String vehicletypeid = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_id();
                Log.w(TAG, "getVehicleDataAddhrs headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);


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
        Log.w(TAG,"getVehicleDataBookinghistory()");
        if (vehicledetailslist != null && vehicledetailslist.size() > 0) {
            for (int i = 0; i < vehicledetailslist.size(); i++) {
                String vehicletype = vehicledetailslist.get(i).getVehicletype_Name();
                String id = vehicledetailslist.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);

                headerVehicleimg = vehicledetailslist.get(i).getVehicle_Image();
                headerVehicleName = vehicledetailslist.get(i).getVehicle_Name();
                headerVehicleBrandName = vehicledetailslist.get(i).getVehicle_Brand_Name();
                headerVehicleFuelTypeName = vehicledetailslist.get(i).getFuel_Type_Name();
                headerVehicleFuelTypeBackgroundcolor = vehicledetailslist.get(i).getFuel_Type_Background_Color();
                vehicleNumber = vehicledetailslist.get(i).getVehicle_No();
                String vehicleid = vehicledetailslist.get(i).get_id();
                String vehicletypeid = vehicledetailslist.get(i).getVehicletype_id();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




    private void checkTimesResponseCall(String  checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CheckTimesResponse> call = apiInterface.checkTimesResponseCall(RestUtils.getContentType(), checkTimesRequest(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<CheckTimesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<CheckTimesResponse> call, @NotNull Response<CheckTimesResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "CheckTimesResponse" + new Gson().toJson(response.body()));

                if (response.body() != null) {


                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                             int days = response.body().getData().getDays();
                            int  hours = response.body().getData().getHours();
                            int min = response.body().getData().getMin();

                            String checkin_date = response.body().getData().getCheckin_date();
                            String checkin_time = response.body().getData().getCheckin_time();
                            String checkout_date = response.body().getData().getCheckout_date();
                            String checkout_time  = response.body().getData().getCheckout_time();
                            String strtotalHours  = response.body().getData().getTotal_hrs();

                            txt_bookingslotwindow.setText(outputDateStartDate + " at " + starttime + " to " + outputDateEndDate + " at " + endtime + "(" + strtotalHours+" Hours" + ")");






                        }





                    }



                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckTimesResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "CheckTimesResponse flr" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private CheckTimesRequest checkTimesRequest(String checkin_time, String checkout_time, String requestCheckinDate, String requestCheckoutDate) {
        CheckTimesRequest checkTimesRequest = new CheckTimesRequest();
        checkTimesRequest.setCheckin_date(requestCheckinDate);
        checkTimesRequest.setCheckout_date(requestCheckoutDate);
        checkTimesRequest.setCheckin_time(checkin_time);
        checkTimesRequest.setCheckout_time(checkout_time);
        Log.w(TAG, "checkTimesRequest" + new Gson().toJson(checkTimesRequest));
        return checkTimesRequest;
    }


}