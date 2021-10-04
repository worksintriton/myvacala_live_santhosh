package com.triton.myvacala.activities.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.payment.PaymentMethodActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.KeyboardUtils;
import com.triton.myvacala.fragment.HomeFragmentParking;
import com.triton.myvacala.requestpojo.CheckTimesRequest;
import com.triton.myvacala.requestpojo.CouponsCodeValidationRequest;
import com.triton.myvacala.requestpojo.ParkingChangeTimesRequest;
import com.triton.myvacala.requestpojo.ParkingSlotCheckAvailableRequest;
import com.triton.myvacala.responsepojo.CheckTimesResponse;
import com.triton.myvacala.responsepojo.CouponsCodeValidationResponse;
import com.triton.myvacala.responsepojo.GetCustomerVehicleandLocationResponse;
import com.triton.myvacala.responsepojo.ParkingChangeTimesResponse;
import com.triton.myvacala.responsepojo.ParkingSlotCheckAvailableResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Parking_Options_Activity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    private String customerid;

    @BindView(R.id.cv_vehicleimage)
    ImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;

    @BindView(R.id.btn_vehiclefueltype)
    Button btn_vehiclefueltype;

    @BindView(R.id.img_header_back)
    ImageView img_header_back;

    @BindView(R.id.txt_parking_name)
    TextView txt_parking_name;

    @BindView(R.id.rlshare)
    RelativeLayout rlshare;

    @BindView(R.id.txt_address)
    TextView txt_address;

    @BindView(R.id.btn_parking_slots_count)
    Button btn_parking_slots_count;

    @BindView(R.id.txt_distance_reachtime)
    TextView txt_distance_reachtime;

    @BindView(R.id.txt_bookingdateandtime)
    TextView txt_bookingdateandtime;

    @BindView(R.id.rlchangedateandtime)
    RelativeLayout rlchangedateandtime;

    @BindView(R.id.txt_total)
    TextView txt_total;

    @BindView(R.id.txt_discount)
    TextView txt_discount;

    @BindView(R.id.txt_to_pay)
    TextView txt_to_pay;

    @BindView(R.id.edt_coupon)
    EditText edt_coupon;


    @BindView(R.id.btn_apply)
    Button btn_apply;


    @BindView(R.id.rlpay)
    RelativeLayout rlpay;

    @BindView(R.id.lladdressdetails)
    LinearLayout lladdressdetails;

    private String parkingid, parkingname, sharemaplink, parkingaddress, selectedVehicleType;
    private int parkingavlslotcountcar, parkingavlslotcountbike, parkingprices;
    private String reachtime, parkingdistance;

    private ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;

    private String TAG = "Parking_Options_Activity";

    String headerVehicleimg, headerVehicleName, headerVehicleBrandName, headerVehicleFuelTypeName, headerVehicleFuelTypeBackgroundcolor, vehicleNumber;


    private String stratdate, enddate, strattime, endtime;
    private String resstartdate, resenddate, resstarttime, resendtime;
    private String strdays, strtotalHours;
    private String str_checkintime, str_checkoutime;
    private String vehicleid, vehicletypeid;


    private String checkinDate = null, checkoutDate = null;
    private String checkinTime = null, checkoutTime = null;

    private String defaultcheckinhours,defaultcheckouthours;


    BottomSheetDialog bottomSheetDialog;
    RelativeLayout rl_datepicker,rl_timepicker;

    private TimePicker timePicker,timePicker_checkout;
    private DatePicker datePicker,datePicker_checkout;



    LinearLayout ll_checkindate;
    LinearLayout ll_checkoutdate;
    LinearLayout ll_checkintime;
    LinearLayout ll_checkoutime;

    TextView txt_selecteddate_time;
    Button btn_done;

    TextView txt_checkin_date;
    TextView txt_checkout_date;
    TextView txt_checkin_time;
    TextView txt_checkout_time;

    String checkinhours;
    String checkouthours;

    long totalHours = 0;

    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;
    private boolean isClickDonebtn = false;


    private int checkedinTime;
    private int CurrentcheckedinTime;

    String Coupon_Code ="",Coupon_Code_Percentage = "",Coupon_Code_Amount = "";
    String valueType;
    int value;
    int originalamout = 0,discountamout = 0;

    int youPay =0;
    int topay;
    int payamout;
    private String OriginalAmount = "";

    String formattedDate;

    private  boolean isCheckedin = false;

    private String currentDateandTime;
    private String formattedDate1;

    private String checkin_date,checkin_time,checkout_date,checkout_time;
    private int days,hours ,min;

    boolean isValidBookingData = false;
    private int currentmin;
    private String checkintime24hrs;
    private String checkouttime24hrs;
    private String requestCheckoutDate;
    private String hourssplit;
    private String str_checkindate;
    private String str_checkoutdate;


    private String checkintimeplusonehour;
    private String checkindateplusonehour;
    private String checkindatechange;
    private String checkoutdatechange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_options);
        ButterKnife.bind(this);
        SessionManager session = new SessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        customerid = user.get(SessionManager.KEY_ID);
        avi_indicator.setVisibility(View.GONE);


        getData();

        img_header_back.setOnClickListener(this);
        rlshare.setOnClickListener(this);
        rlchangedateandtime.setOnClickListener(this);
        rlpay.setOnClickListener(this);

        btn_apply.setOnClickListener(this);
        lladdressdetails.setOnClickListener(this);
       // btn_apply.setEnabled(false);



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
        Intent intent = new Intent(Parking_Options_Activity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parkingid = extras.getString("id");
            parkingname = extras.getString("parkingname");
            sharemaplink = extras.getString("sharemaplink");
            parkingaddress = extras.getString("address");
            parkingavlslotcountcar = extras.getInt("parkingavlslotcountcar");
            parkingavlslotcountbike = extras.getInt("parkingavlslotcountbike");
            reachtime = extras.getString("reachtime");
            parkingdistance = extras.getString("parkingdistance");
            parkingprices = extras.getInt("parkingprices");
            selectedVehicleType = extras.getString("selectedVehicleType");
            customerVehicleDataBeanList = (ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean>) getIntent().getSerializableExtra("customerVehicleDataBeanList");
            Log.w(TAG, "customerVehicleDataBeanList:" + new Gson().toJson(customerVehicleDataBeanList));
            getVehicleData(selectedVehicleType);

            setData();

        }

    }

    private void getVehicleData(String selectedVehicleType) {
        if (customerVehicleDataBeanList != null && customerVehicleDataBeanList.size() > 0) {
            for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id + "selectedVehicleType : " + selectedVehicleType);
                if (selectedVehicleType.equalsIgnoreCase(vehicletype)) {
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                    vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                    vehicleid = customerVehicleDataBeanList.get(i).get_id();
                    vehicletypeid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    Log.w(TAG, "getVehicleData headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);
                }

            }
            setHeaderVehicleData();
        }




    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (parkingname != null) {
            txt_parking_name.setText(parkingname);
        }
        if (parkingaddress != null) {
            txt_address.setText(parkingaddress);
        }
        if (selectedVehicleType.equalsIgnoreCase("Four Wheeler")) {
            btn_parking_slots_count.setText(parkingavlslotcountcar + " Available");
        } else {
            btn_parking_slots_count.setText(parkingavlslotcountbike + " Available");


        }


        txt_distance_reachtime.setText(reachtime + " / " + parkingdistance);

        if(parkingprices != 0) {
            txt_total.setText("\u20B9 " + parkingprices);
            txt_to_pay.setText("\u20B9 " + parkingprices);
            payamout = parkingprices;
            txt_discount.setText("\u20B9 " + 0);
        }

        resstartdate = HomeFragmentParking.stratdate;
        resenddate = HomeFragmentParking.enddate;
        resstarttime = HomeFragmentParking.strattime;
        resendtime = HomeFragmentParking.endtime;

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            calculateTotalhrs(resstarttime, resendtime, resstartdate, resenddate);
        }


        String HomePageCheckindateandtime = resstartdate+" "+resstarttime;
        Log.w(TAG,"HomePageCheckindateandtime : "+HomePageCheckindateandtime);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        currentDateandTime = sdf.format(new Date());
        Log.w(TAG,"currentDateandTime : "+currentDateandTime);

       /* try {
            Date date1 = sdf.parse(HomePageCheckindateandtime);
            Date date2 = sdf.parse(currentDateandTime);

           if(date1 != null && date2 != null) {
               Log.w(TAG,"Condition 1 : "+ (date1.getDate() == date2.getDate()));
               Log.w(TAG,"Condition 2 : "+ (date1.getTime() < date2.getTime()));
               if (date1.getDate() == date2.getDate() && date1.getTime() < date2.getTime()) {
                   Log.w(TAG, "Time1 is after time2");
                   String currenttime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                   String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
                   roundedMins(currenttime,currentdate);


               }
           }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            parkingSlotCheckAvailableResponseCall(resstarttime, resendtime, resstartdate, resenddate, vehicletypeid);
        }


        Log.w(TAG, "Staticvalues--->" + "resstartdate : " + resstartdate + "resenddate : " + resenddate + "strattime : " + resstarttime + "resendtime : " + resendtime);

        checkinDate = resstartdate;
        checkoutDate = resenddate;

        stratdate = HomeFragmentParking.bookingstartdate;
        enddate = HomeFragmentParking.bookingenddate;
        strattime = HomeFragmentParking.str_checkintime;
        endtime = HomeFragmentParking.str_checkoutime;
        strdays = HomeFragmentParking.strdays;
        strtotalHours = HomeFragmentParking.strtotalHours;
        Log.w(TAG, "static strtotalHours--->" + strtotalHours+"strdays :"+strdays);


        Log.w(TAG, "Staticvalues--->" + "stratdate : " + stratdate + "enddate : " + enddate + "strattime : " + strattime + "endtime : " + endtime + " strdays : " + strdays + "strtotalHours : " + strtotalHours);
        if (stratdate != null && enddate != null && strattime != null && endtime != null) {

            String selectedDateandTime = stratdate + ", " + strattime + " to " +
                    enddate + ", " + endtime + "(" + strdays + " Day " + strtotalHours+" Hours" + ")";
            txt_bookingdateandtime.setVisibility(View.VISIBLE);
            txt_bookingdateandtime.setText(selectedDateandTime);
            txt_bookingdateandtime.setTextSize(8);
        }


    }



    @SuppressLint("NewApi")
    private void roundedMins(String currenttime, String currentdate){

        Log.w(TAG,"roundedMins currenttime : "+currenttime);
        String roundedminutes = null;

        String[] separated = currenttime.split(":");
        String hourssplit = separated[0];
        String minutesampmsplit = separated[1];
        Log.w(TAG,"minutesampmsplit : "+minutesampmsplit);

        String[] splited = minutesampmsplit.split("\\s+");
        String minutessplit = splited[0];
        String ampm = splited[1];
        Log.w(TAG,"minutessplit : "+minutessplit+" ampm : "+ampm);




        Long minutes = Long.valueOf(minutessplit);

        if(ValueRange.of(0, 5).isValidIntValue(minutes)) {
            roundedminutes = "05";
            Log.w(TAG,"Value is with in the Range. 05");
        }
        else if(ValueRange.of(5, 10).isValidIntValue(minutes)) {
            roundedminutes = "10";
            Log.w(TAG,"Value is with in the Range. 10");
        }
        else if(ValueRange.of(10, 15).isValidIntValue(minutes)) {
            roundedminutes = "15";
            Log.w(TAG,"Value is with in the Range. 15");
        }
        else if(ValueRange.of(15, 20).isValidIntValue(minutes)) {
            roundedminutes = "20";
            Log.w(TAG,"Value is with in the Range. 20");
        }
        else if(ValueRange.of(20, 25).isValidIntValue(minutes)) {
            roundedminutes = "25";
            Log.w(TAG,"Value is with in the Range. 25");
        }
        else if(ValueRange.of(25, 30).isValidIntValue(minutes)) {
            roundedminutes = "30";
            Log.w(TAG,"Value is with in the Range. 30");
        }
        else if(ValueRange.of(30, 35).isValidIntValue(minutes)) {
            roundedminutes = "35";
            Log.w(TAG,"Value is with in the Range. 35");
        }
        else if(ValueRange.of(35, 40).isValidIntValue(minutes)) {
            roundedminutes = "40";
            Log.w(TAG,"Value is with in the Range. 40");
        }

        else if(ValueRange.of(40, 45).isValidIntValue(minutes)) {
            roundedminutes = "45";
            Log.w(TAG,"Value is with in the Range. 45");
        } else if(ValueRange.of(45, 50).isValidIntValue(minutes)) {
            roundedminutes = "50";
            Log.w(TAG,"Value is with in the Range. 50");
        } else if(ValueRange.of(50, 55).isValidIntValue(minutes)) {
            roundedminutes = "55";
            Log.w(TAG,"Value is with in the Range. 55");
        } else if(ValueRange.of(55, 60).isValidIntValue(minutes)) {
            roundedminutes = "55";
            Log.w(TAG,"Value is with in the Range. 00");
        }
        defaultcheckinhours = hourssplit+":"+roundedminutes+" "+ampm;
        str_checkintime = defaultcheckinhours;

        Log.w(TAG,"roundedMins defaultcheckinhours "+defaultcheckinhours);




    }


    private void setHeaderVehicleData() {

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
        if (headerVehicleFuelTypeName != null) {
            btn_vehiclefueltype.setText(headerVehicleFuelTypeName);
        } else {
            btn_vehiclefueltype.setText("");
        }

        if (headerVehicleFuelTypeBackgroundcolor != null) {
            btn_vehiclefueltype.setBackgroundColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            btn_vehiclefueltype.setBackgroundResource(R.drawable.tags_rounded_corners);
            GradientDrawable gd = (GradientDrawable) btn_vehiclefueltype.getBackground();
            gd.setColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            gd.setCornerRadius(10);
            gd.setStroke(4, Color.WHITE);

        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_header_back:
                onBackPressed();
                break;

            case R.id.rlshare:
                if (sharemaplink != null) {
                    gotoShareLocation();
                }
                break;
                case R.id.lladdressdetails:
                if (sharemaplink != null) {
                    gotoGoogleMap(sharemaplink);
                }
                break;

            case R.id.rlchangedateandtime:
                checkinDate = null;
                checkoutDate = null;
                gotoBookingDateandTime();
                break;
            case R.id.rlpay:
                Intent intent = new Intent(Parking_Options_Activity.this, PaymentMethodActivity.class);
                intent.putExtra("parkingid", parkingid);
                intent.putExtra("vehicletypeid", vehicletypeid);
                intent.putExtra("vehicleid", vehicleid);
                intent.putExtra("resstartdate", resstartdate);
                intent.putExtra("resenddate", resenddate);
                intent.putExtra("resstarttime", resstarttime);
                intent.putExtra("resendtime", resendtime);
                intent.putExtra("totalamount", payamout);
                intent.putExtra("totalhrs", strtotalHours);
                intent.putExtra("reachtime", reachtime);
                intent.putExtra("parkingdistance", parkingdistance);
                intent.putExtra("strdays", strdays);
                intent.putExtra("couponcode", Coupon_Code);
                intent.putExtra("couponcodeamount", Coupon_Code_Amount);
                intent.putExtra("originalamount", OriginalAmount);
                intent.putExtra("days", days);
                intent.putExtra("hours", hours);
                intent.putExtra("min", min);
                Log.w(TAG,"ParkingOptTOPaymentPage--->"+"resstartdate : "+resstartdate+" resstarttime : "+resstarttime+" resenddate : "+resenddate+" resendtime : "+resendtime+" strtotalHours : "+strtotalHours+ " Coupon_Code_Amount : "+Coupon_Code_Amount);
                startActivity(intent);
                break;

            case R.id.btn_apply:
                if(btn_apply.getText().toString().equalsIgnoreCase("Apply")){

                    OriginalAmount = String.valueOf(parkingprices);
                    Log.w(TAG,"Apply---->");
                    if(validateCouponCodeInputs()){

                        KeyboardUtils.hideKeyboard(Parking_Options_Activity.this);
                    btn_apply.setText("Remove");
                    if (new ConnectionDetector(Parking_Options_Activity.this).isNetworkAvailable(Parking_Options_Activity.this)) {
                        couponsCodeValidationResponseCall();
                    }

                }
                }
                else{
                    Log.w(TAG,"Remove---->");
                    Toasty.info(getApplicationContext(), getResources().getString(R.string.couponcoderemoved), Toast.LENGTH_SHORT, true).show();

                    btn_apply.setText("Apply");
                    edt_coupon.setText("");
                    Coupon_Code = "";
                    Coupon_Code_Amount = "";
                    OriginalAmount = "";
                    Coupon_Code_Percentage = "";
                    youPay = discountamout;
                    Log.w(TAG,"parkingprices");


                    txt_discount.setText("\u20B9"+" "+0);
                    txt_to_pay.setText("\u20B9"+" "+parkingprices);
                    payamout = parkingprices;

                }

                break;
        }
    }

    private void gotoGoogleMap(String sharemaplink) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(sharemaplink));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void gotoShareLocation() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String msg = "Hey, check out "+parkingname+" parking location using Myvacala"+"\n";
        String shareBody = msg + "https://myvacala.page.link/share"+"?Parkingid="+parkingid;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Vacala");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint({"LongLogTag", "SetTextI18n", "NewApi"})
    private void gotoBookingDateandTime() {
        LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.date_time_picker_parking, null);
        bottomSheetDialog = new BottomSheetDialog(Parking_Options_Activity.this);
        bottomSheetDialog.setContentView(view);

        LinearLayout ll_checkindate = bottomSheetDialog.findViewById(R.id.ll_checkindate);
        LinearLayout ll_checkoutdate = bottomSheetDialog.findViewById(R.id.ll_checkoutdate);
        LinearLayout ll_checkintime = bottomSheetDialog.findViewById(R.id.ll_checkintime);
        LinearLayout ll_checkoutime = bottomSheetDialog.findViewById(R.id.ll_checkoutime);

        txt_checkin_date = bottomSheetDialog.findViewById(R.id.txt_checkin_date);
        txt_checkout_date = bottomSheetDialog.findViewById(R.id.txt_checkout_date);
        txt_checkin_time = bottomSheetDialog.findViewById(R.id.txt_checkin_time);
        txt_checkout_time = bottomSheetDialog.findViewById(R.id.txt_checkout_time);

        txt_selecteddate_time = bottomSheetDialog.findViewById(R.id.txt_selecteddate_time);
        //TextView txt_noofhours = bottomSheetDialog.findViewById(R.id.txt_noofhours);

        Button btn_done = bottomSheetDialog.findViewById(R.id.btn_done);

        rl_datepicker = bottomSheetDialog.findViewById(R.id.rl_datepicker);
        rl_timepicker = bottomSheetDialog.findViewById(R.id.rl_timepicker);

        rl_datepicker.setVisibility(View.GONE);
        rl_timepicker.setVisibility(View.GONE);


        String selectedDateandTime = stratdate + ", " + strattime + " to " +
                enddate + ", " + endtime + "(" + strdays + " Day " + strtotalHours +" Hours"+ ")";

        Log.w(TAG,"selectedDateandTime--->"+selectedDateandTime);
        txt_selecteddate_time.setText(selectedDateandTime);
        txt_selecteddate_time.setTextSize(12);


        txt_checkin_date.setText(stratdate);
        txt_checkout_date.setText(enddate);
        txt_checkin_time.setText(strattime);
        txt_checkout_time.setText(endtime);

        //txt_checkin_time.setText(str_checkintime);
        //txt_checkout_time.setText(str_checkoutime);
        checkinDate = resstartdate;
        checkoutDate = resenddate;

        Log.w(TAG,"gotoBookingDateandTime : "+" checkoutDate : "+checkoutDate);




        //checkinTime = str_checkintime;
        //checkoutTime = str_checkoutime;

        checkinTime = strattime;
        checkoutTime = endtime;

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);




        ImageView img_close = bottomSheetDialog.findViewById(R.id.img_close);
        if (img_close != null) {
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkindatechange = null;
                    checkoutdatechange = null;
                    checkindateplusonehour = null;
                    checkintimeplusonehour = null;
                    bottomSheetDialog.dismiss();
                }
            });
        }





        strdays = "0";



        bottomSheetDialog.show();

        if (ll_checkindate != null) {
            ll_checkindate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckedInDate(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);

                }
            });



        }
        if (ll_checkoutdate != null) {

            ll_checkoutdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckedOutDate(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                }
            });
        }
        if (ll_checkintime != null) {
            ll_checkintime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckInTime(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                }
            });
        }
        if (ll_checkoutime != null) {
            ll_checkoutime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckOutTime(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                }


            });
        }



        if (btn_done != null) {
            btn_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w(TAG, " checkinTime -------------------> " + checkinTime);
                    Log.w(TAG, " checkoutTime -------------------> " + checkoutTime);
                    try {
                        if (validateInputs()) {


                            Log.w(TAG, " btn_done.setOnClickListener :" + "checkinDate : " + checkinDate + "checkoutDate :" + checkoutDate);

                           /* try {
                                isClickDonebtn = true;
                                if(isValidBookingData){
                                    if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                                        parkingChangeTimesResponseCall(checkin_time, checkout_time, checkinDate, checkoutDate, vehicletypeid);
                                    }
                                }

                                bottomSheetDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Parking_Options_Activity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }



    @SuppressLint("NewApi")
    private void selectCheckedInDate(View view) {
        setupFullHeight(bottomSheetDialog);

        rl_timepicker.setVisibility(View.GONE);
        rl_datepicker.setVisibility(View.VISIBLE);

        datePicker = bottomSheetDialog.findViewById(R.id.datePicker); // initiate a date picker
        datePicker.setVisibility(View.VISIBLE);
        datePicker_checkout = bottomSheetDialog.findViewById(R.id.datePicker_checkout); // initiate a date picker
        datePicker_checkout.setVisibility(View.GONE);
        long now = System.currentTimeMillis() - 1000;
        Objects.requireNonNull(datePicker).setMinDate(now);
        datePicker.setMaxDate(now+(1000*60*60*24*13)); //After 13 Days from Now


        if(resstartdate != null && !resstartdate.isEmpty()) {

            String[] separated = resstartdate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker != null) {
                datePicker.updateDate(year,month-1,day);
            }
        }
        else if(checkinDate != null && !checkinDate.isEmpty()) {
            String[] separated = checkinDate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker != null) {
                datePicker.updateDate(year,month-1,day);
            }
        }
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month, date;

                date = Integer.toString(dayOfMonth);
                month = Integer.toString(monthOfYear + 1);

                if (month.length() == 1) {

                    month = "0" + (monthOfYear + 1);
                }
                if (date.length() == 1) {

                    date = "0" + dayOfMonth;
                }



                checkinDate = year + "-" + month + "-" + date;
                checkoutDate = checkinDate;
                str_checkindate = checkinDate;
                Log.w(TAG,"setOnDateChangedListener : "+checkinDate);

                convertDateFormayyyyMMddtoddMMM(checkinDate);


                try{
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                    String checkoutdateandtime = checkoutDate+" "+checkoutTime;
                    String checkindateandtime = checkinDate+" "+checkinTime;
                    Date dateCheckin = simpleDateFormat.parse(checkindateandtime);
                    Date dateCheckout = simpleDateFormat.parse(checkoutdateandtime);
                    if(dateCheckin != null && dateCheckout != null) {
                        printDifference(dateCheckin, dateCheckout);
                    }

                }catch (ParseException e){
                    e.printStackTrace();

                }




            }
        });
    }
    private void convertDateFormayyyyMMddtoddMMM(String inputDate) {
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
            String outputDateStr = outputFormat.format(dateformat);
            txt_checkin_date.setText(outputDateStr);
            txt_checkout_date.setText(outputDateStr);

           /* String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                    txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + 0 + " Day " + strtotalHours+" Hours"+ ")";

            Log.w(TAG,"selectedDateandTime--->"+selectedDateandTime);
            txt_selecteddate_time.setText(selectedDateandTime);
            txt_selecteddate_time.setTextSize(12);*/

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NewApi")
    private void selectCheckedOutDate(View view) {
        setupFullHeight(bottomSheetDialog);

        rl_timepicker.setVisibility(View.GONE);
        rl_datepicker.setVisibility(View.VISIBLE);

        datePicker = bottomSheetDialog.findViewById(R.id.datePicker); // initiate a date picker
        datePicker.setVisibility(View.GONE);
        datePicker_checkout = bottomSheetDialog.findViewById(R.id.datePicker_checkout); // initiate a date picker
        datePicker_checkout.setVisibility(View.VISIBLE);
        long now = System.currentTimeMillis() - 1000;

        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(checkinDate);

            long startDate = 0;
            if (date != null) {
                startDate = date.getTime();
            }
            Objects.requireNonNull(datePicker_checkout).setMinDate(startDate);
            datePicker_checkout.setMaxDate(startDate+(1000*60*60*24*13)); //After 13 Days from Now

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.w(TAG,"selectCheckedOutDate--->"+resenddate);
        if(checkindateplusonehour != null && !checkindateplusonehour.isEmpty()){
            String[] separated = checkindateplusonehour.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker_checkout != null) {
                datePicker_checkout.updateDate(year,month-1,day);
            }
        }
        else if(resenddate != null && !resenddate.isEmpty()) {

            String[] separated = resenddate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker_checkout != null) {
                datePicker_checkout.updateDate(year,month-1,day);
            }
        }
        else if(checkoutDate != null && !checkoutDate.isEmpty()) {

            String[] separated = checkoutDate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker_checkout != null) {
                datePicker_checkout.updateDate(year,month-1,day);
            }
        }

        datePicker_checkout.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Log.w(TAG,"selectCheckedOutDate setOnDateChangedListener : "+"year : "+year+" monthOfYear : "+monthOfYear+" dayOfMonth : "+dayOfMonth);
                String month, date;

                date = Integer.toString(dayOfMonth);
                month = Integer.toString(monthOfYear + 1);

                if (month.length() == 1) {

                    month = "0" + (monthOfYear + 1);
                }
                if (date.length() == 1) {

                    date = "0" + dayOfMonth;
                }

                checkoutDate = year + "-" + month + "-" + date;
                checkoutdatechange = checkoutDate;
                Log.w(TAG,"setOnDateChangedListener : "+checkoutDate);

                convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);

                try{
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                    String checkoutdateandtime = checkoutDate+" "+checkoutTime;
                    String checkindateandtime = checkinDate+" "+checkinTime;
                    Date dateCheckin = simpleDateFormat.parse(checkindateandtime);
                    Date dateCheckout = simpleDateFormat.parse(checkoutdateandtime);

                    GetTimeWithAMPMFromTime(checkinTime);
                    convertTimeWithAMPMFromTime(checkoutTime);

                    String checkindateandtime24hrs = checkinDate+" "+checkintime24hrs;
                    String checkoutdateandtime24hrs = checkoutDate+" "+checkouttime24hrs;

                    Log.w(TAG,"selectCheckedOutDate : "+" checkindateandtime24hrs : "+checkindateandtime24hrs+" checkoutdateandtime24hrs : "+checkoutdateandtime24hrs);

                    Log.w(TAG,"selectCheckedOutDate conditions : "+checkinDate.equals(checkoutDate));
                    Log.w(TAG,"selectCheckedOutDate conditions1 : "+(checkindateandtime24hrs.compareTo(checkoutdateandtime24hrs)>0));

                    if(checkinDate.equals(checkoutDate) &&  checkindateandtime24hrs.compareTo(checkoutdateandtime24hrs)>0){

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");

                        Date date1 = sdf.parse(checkindateandtime);
                        final Calendar calendar = Calendar.getInstance();

                        if (date1 != null) {
                            calendar.setTime(date1);
                        }
                        calendar.add(Calendar.HOUR, 1);

                        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                        String CheckinDateandTime = sdf.format(calendar.getTime());

                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                        checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);
                        Log.w(TAG,"selectCheckedOutDate--->  if "+"checkoutDate : "+checkoutDate+" checkoutTime :"+checkoutTime);

                        txt_checkout_time.setText(checkoutTime);
                        if(checkoutDate != null && !checkoutDate.isEmpty()) {

                            String[] separated = checkoutDate.split("-");
                            String stryear = separated[0];
                            String strmonth = separated[1];
                            String strday = separated[2];
                            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);
                            int year1 = Integer.parseInt(stryear);
                            int month1 = Integer.parseInt(strmonth);
                            int day = Integer.parseInt(strday);
                            if (datePicker_checkout != null) {
                                datePicker_checkout.updateDate(year1,month1-1,day);
                            }
                        }


                    }else{
                        if(dateCheckin != null && dateCheckout != null) {
                            printDifference(dateCheckin, dateCheckout);
                        }
                    }


                }catch (ParseException e){
                    e.printStackTrace();

                }




            }
        });



    }
    private void convertCheckoutDateFormatyyyyMMddtoddMMM(String inputDate) {
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
            String outputDateStr = outputFormat.format(dateformat);
            txt_checkout_date.setText(outputDateStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void selectCheckInTime(View view) {
        setupFullHeight(bottomSheetDialog);

        Log.w(TAG,"selectCheckInTime--->");
        rl_datepicker.setVisibility(View.GONE);
        rl_timepicker.setVisibility(View.VISIBLE);
        timePicker = bottomSheetDialog.findViewById(R.id.timePicker); // initiate a time picker
        timePicker.setVisibility(View.VISIBLE);
        timePicker_checkout = bottomSheetDialog.findViewById(R.id.timePicker_checkout); // initiate a time picker
        timePicker_checkout.setVisibility(View.GONE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        String currentDateandTime = sdf.format(new Date());
        // Log.w(TAG,"gotoBookingDateandTime Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
        // Log.w(TAG,"gotoBookingDateandTime Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
        String currenttime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
        String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
        String[] separated = currenttime.split(":");
        hourssplit = separated[0];
        String minutesampmsplit = separated[1];

        String[] splited = minutesampmsplit.split("\\s+");
        String minutessplit = splited[0];
        int currentmins = Integer.parseInt(minutessplit);
        //Log.w(TAG,"selectCheckInTime roundedminutes -->"+roundedminutes);




        setTimePickerInterval(timePicker);
        if(currentmins>55){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            String time24 = null;
            try {
                time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(currenttime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Log.w(TAG, "time24 : " + time24);
            String[] separated1 = time24.split(":");
            String hourssplit = separated1[0];
            int currenthour = Integer.parseInt(hourssplit);
            // Log.w(TAG,"currenthour-->"+currenthour);
            timePicker.setCurrentHour(currenthour+1);

        }
        currentmin = (currentmins+4)/5;
        timePicker.setCurrentMinute(currentmin);
        Log.w(TAG,"currentmin--><"+currentmin);

        timePicker.setIs24HourView(false);


        Log.w(TAG,"str_checkintime--->"+str_checkintime);
        GetTimeWithAMPMFromTime(txt_checkin_time.getText().toString());
        Log.w(TAG,"checkintime24hrs--->"+checkintime24hrs);


        String[] separated11 = checkintime24hrs.split(":");
        String hourssplit1 = separated11[0];
        int startcheckouthours = Integer.parseInt(hourssplit1);
        Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
        timePicker.setCurrentHour(startcheckouthours);

        String minutessplit1 = separated11[1];
        int currentmins1 = Integer.parseInt(minutessplit1.trim());
        Log.w(TAG,"currentmins1-->"+currentmins1);
        currentmin = (currentmins1+4)/5;
        timePicker.setCurrentMinute(currentmin);



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute){
                minute =(minute*5);

                String selectedhrs24;
                if(hourOfDay < 10){
                    selectedhrs24 = "0"+hourOfDay;
                }else{
                    selectedhrs24 = String.valueOf(hourOfDay);
                }

                Log.w(TAG,"selectCheckInTime onTimeChanged : "+" hourOfDay : "+hourOfDay+" minute : "+minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                String currentDateandTime = sdf.format(new Date());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String currentDateandTime24hrs = simpleDateFormat.format(new Date());

                // Log.w(TAG,"selectCheckInTime setOnTimeChangedListener currentDateandTime : "+currentDateandTime);
                checkinTime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                String currenttime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
                // Log.w(TAG,"selectCheckInTime : "+" currenttime : "+currenttime+" currentdate : "+currentdate);
                String selectedhours,selectedminutes;
                String format;

                if (hourOfDay == 0) {

                    hourOfDay += 12;

                    format = "AM";
                }
                else if (hourOfDay == 12) {

                    format = "PM";

                }
                else if (hourOfDay > 12) {

                    hourOfDay -= 12;

                    format = "PM";

                }
                else {

                    format = "AM";
                }

                if(hourOfDay < 10){
                    selectedhours = "0"+hourOfDay;
                }
                else {
                    selectedhours = String.valueOf(hourOfDay);
                }
                if(minute < 10){
                    selectedminutes = "0"+minute;
                }
                else {
                    selectedminutes = String.valueOf(minute);
                }

                // Log.w(TAG,"selectCheckInTime selectedhours : "+selectedhours+" selectedminutes : "+selectedminutes+" format : "+format);
                checkinTime = selectedhours+":"+selectedminutes+" "+format;
                //  Log.w(TAG,"selectCheckInTime checkinTime : "+checkinTime);



                String checkindateandtime = checkinDate+" "+checkinTime;
                getdateandtime(checkindateandtime);
                txt_checkin_time.setText(checkinTime);

                checkintime24hrs = selectedhrs24+":"+selectedminutes;
                //Log.w(TAG,"checkintime24hrs : "+checkintime24hrs);

                String checkindateandtime24hrs = checkinDate+" "+checkintime24hrs;

                Log.w(TAG,"checkindateandtime24hrs : "+ checkindateandtime24hrs +"checkintime24hrs : "+checkintime24hrs);
                try{

                    Log.w(TAG,"currentDateandTime24hrs : "+currentDateandTime24hrs+" checkindateandtime24hrs : "+checkindateandtime24hrs);
                    Log.w(TAG,"Condition : "+(currentDateandTime24hrs.compareTo(checkindateandtime24hrs)>0));

                    if(checkinDate.equals(currentdate) &&checkintime24hrs.equalsIgnoreCase("00:00") ){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(Objects.requireNonNull(simpleDateFormat1.parse(checkinDate)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 1);  // number of days to add
                        String checkinDateandtime = sdf.format(c.getTime());  // dt is now the new date

                        String time = checkinDateandtime.substring(checkinDateandtime.indexOf(' ') + 1);
                        String date =  checkinDateandtime.substring(0, checkinDateandtime.indexOf(' '));

                        checkindateandtime24hrs = date+" "+checkintime24hrs;
                        checkinDate = date;
                        checkinTime = time;
                        convertcinDateFormatyyyyMMddtoddMMM(checkinDate);


                        checkindateandtime = checkinDate+" "+checkinTime;

                        Date date1 = sdf.parse(checkindateandtime);
                        final Calendar calendar = Calendar.getInstance();

                        if (date1 != null) {
                            calendar.setTime(date1);
                        }
                        calendar.add(Calendar.HOUR, 1);

                        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                        String CheckinDateandTime = sdf.format(calendar.getTime());

                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                        checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);

                        txt_checkout_time.setText(checkoutTime);

                        convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);


                    }else if(checkinDate.equals(currentdate) &&  currentDateandTime24hrs.compareTo(checkindateandtime24hrs)>0){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                        String time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(currenttime)));
                        //Log.w(TAG, "time24 : " + time24);
                        String[] separated = time24.split(":");
                        String hourssplit = separated[0];
                        Log.w(TAG,"hourssplit : "+hourssplit);
                        String minutessplit = separated[1];
                        int currentmins = Integer.parseInt(minutessplit);
                        Log.w(TAG,"currentmin12: "+currentmins);


                        Log.w(TAG,"condition mins-->"+(currentmins>55));
                        if(currentmins>55){
                            int currenthour = Integer.parseInt(hourssplit);
                            timePicker.setCurrentHour(currenthour+1);
                            Log.w(TAG,"setCurrentHour currenthouraddone : "+currenthour+1);

                        }else{
                            timePicker.setCurrentHour(Integer.valueOf(hourssplit));
                            Log.w(TAG,"setCurrentHour hourssplit : "+hourssplit);


                        }
                        timePicker.setCurrentMinute((currentmins+4)/5);
                        Log.w(TAG,"setCurrentMinute  : "+(currentmins+4)/5);


                    }
                    else{
                        try {
                            Date dateCurrent = sdf.parse(currentDateandTime);
                            Date dateCheckin = sdf.parse(checkindateandtime);

                            Log.w(TAG,"dateCurrent : "+dateCurrent+" currentDateandTime : "+currentDateandTime);
                            Log.w(TAG,"dateCheckin : "+dateCheckin+" checkindateandtime : "+checkindateandtime);
                            if(dateCurrent != null && dateCheckin != null){
                                Log.w(TAG,"DATEconditions : "+dateCheckin.before(dateCurrent));
                                if(dateCheckin.before(dateCurrent)){
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar c = Calendar.getInstance();
                                    try {
                                        c.setTime(Objects.requireNonNull(simpleDateFormat1.parse(checkinDate)));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    c.add(Calendar.DATE, 1);  // number of days to add
                                    String checkinDateandtime = sdf.format(c.getTime());  // dt is now the new date

                                    String time = checkinDateandtime.substring(checkinDateandtime.indexOf(' ') + 1);
                                    String date =  checkinDateandtime.substring(0, checkinDateandtime.indexOf(' '));
                                    Log.w(TAG,"Conditions if : "+"date : "+date+" time :"+time);
                                    checkindateandtime24hrs = date+" "+checkintime24hrs;
                                    checkinDate = date;
                                    checkinTime = time;
                                    convertcinDateFormatyyyyMMddtoddMMM(checkinDate);


                                    checkindateandtime = checkinDate+" "+checkinTime;

                                    Date date1 = sdf.parse(checkindateandtime);
                                    final Calendar calendar = Calendar.getInstance();

                                    if (date1 != null) {
                                        calendar.setTime(date1);
                                    }
                                    calendar.add(Calendar.HOUR, 1);

                                    // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                                    String CheckinDateandTime = sdf.format(calendar.getTime());

                                    // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                                    // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                                    checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                                    checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);

                                    checkindateplusonehour = checkoutDate;
                                    checkintimeplusonehour = checkoutTime;

                                    txt_checkout_time.setText(checkoutTime);

                                    String checkoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();

                                    Date dateCheckout = sdf.parse(checkoutdateandtime);
                                    if(dateCheckin != null && dateCheckout != null) {
                                        printDifference(dateCheckin, dateCheckout);
                                    }

                                    convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);

                                }else {
                                    //checkinDate = currentdate;
                                    Log.w(TAG,"Conditions else : "+checkinDate);

                                    try {


                                        checkindateandtime = checkinDate+" "+checkinTime;

                                        Date date1 = sdf.parse(checkindateandtime);
                                        final Calendar calendar = Calendar.getInstance();

                                        if (date1 != null) {
                                            calendar.setTime(date1);
                                        }
                                        calendar.add(Calendar.HOUR, 1);

                                        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                                        String CheckinDateandTime = sdf.format(calendar.getTime());

                                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                                        checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                                        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);

                                        checkindateplusonehour = checkoutDate;
                                        checkintimeplusonehour = checkoutTime;

                                        txt_checkout_time.setText(checkoutTime);
                                        String checkoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();

                                        Date dateCheckout = sdf.parse(checkoutdateandtime);
                                        if(dateCheckin != null && dateCheckout != null) {
                                            printDifference(dateCheckin, dateCheckout);
                                        }


                                        convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }



                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }catch(ParseException e){
                    e.printStackTrace();
                }









                //@SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                final Date date;


            }


        });
    }
    private void selectCheckOutTime(View view) {
        setupFullHeight(bottomSheetDialog);

        Log.w(TAG,"selectCheckOutTime-->");
        rl_datepicker.setVisibility(View.GONE);
        rl_timepicker.setVisibility(View.VISIBLE);
        timePicker = bottomSheetDialog.findViewById(R.id.timePicker); // initiate a time picker
        timePicker.setVisibility(View.GONE);
        timePicker_checkout = bottomSheetDialog.findViewById(R.id.timePicker_checkout); // initiate a time picker
        timePicker_checkout.setVisibility(View.VISIBLE);
        timePicker_checkout.setIs24HourView(false);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        // String currentDateandTime = sdf.format(new Date());


        String checkindateandtime = checkinDate+" "+txt_checkin_time.getText().toString();
        Date date1 = null;
        try {
            date1 = sdf.parse(checkindateandtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Calendar calendar = Calendar.getInstance();

        if (date1 != null) {
            calendar.setTime(date1);
        }
        calendar.add(Calendar.HOUR, 1);

        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
        String CheckinDateandTime = sdf.format(calendar.getTime());

        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

        // checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);

        String checkoutdateandtime = checkoutDate+" "+checkoutTime;
        Log.w(TAG,"selectCheckOutTime checkoutdateandtime -->"+checkoutdateandtime);

        convertTimeWithAMPMFromTime(checkoutTime.trim());

        String checkouttime = checkoutdateandtime.substring(checkoutdateandtime.indexOf(' ') + 1);
        String checkoutdate =  checkoutdateandtime.substring(0, checkoutdateandtime.indexOf(' '));

        int currentmins = 0;
        if(checkintimeplusonehour != null && !checkintimeplusonehour.isEmpty()){
            try{
                convertTimeWithAMPMFromTime(checkintimeplusonehour);
                Log.w(TAG,"checkintimeplusonehour--->"+checkintimeplusonehour);
                String[] separated1 = checkouttime24hrs.split(":");
                String hourssplit = separated1[0];
                String minutessplit = separated1[1];
                int startcheckouthours = Integer.parseInt(hourssplit);
                Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
                timePicker_checkout.setCurrentHour(startcheckouthours);
                currentmins = Integer.parseInt(minutessplit);
                Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins+"minutessplit : "+minutessplit);

                timePicker_checkout.setCurrentMinute(currentmins);
                Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins);

            }catch (IndexOutOfBoundsException e){
                Log.w(TAG,"selectCheckOutTime"+e.getLocalizedMessage());
            }

        }
        else if(endtime != null && !endtime.isEmpty()){
            convertTimeWithAMPMFromTime(endtime);
            Log.w(TAG,"checkouttime24hrs--->"+checkouttime24hrs);
            String[] separated1 = checkouttime24hrs.split(":");
            String hourssplit = separated1[0];
            String minutessplit = separated1[1];
            int startcheckouthours = Integer.parseInt(hourssplit);
            Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
            timePicker_checkout.setCurrentHour(startcheckouthours);
            currentmins = Integer.parseInt(minutessplit);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins+"minutessplit : "+minutessplit);

            timePicker_checkout.setCurrentMinute(currentmins);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins);

        }
        else if(checkouttime24hrs != null && !checkouttime24hrs.isEmpty()){
            Log.w(TAG,"checkouttime24hrs--->"+checkouttime24hrs);
            String[] separated1 = checkouttime24hrs.split(":");
            String hourssplit = separated1[0];
            String minutessplit = separated1[1];
            int startcheckouthours = Integer.parseInt(hourssplit);
            Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
            timePicker_checkout.setCurrentHour(startcheckouthours);
            currentmins = Integer.parseInt(minutessplit);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins+"minutessplit : "+minutessplit);

            timePicker_checkout.setCurrentMinute(currentmins);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins);
        }



        //String checkindateandtime = checkinDate+" "+checkinTime;
        String currenttime = checkindateandtime.substring(checkindateandtime.indexOf(' ') + 1);
        String currentdate =  checkindateandtime.substring(0, checkindateandtime.indexOf(' '));
        String[] separated = currenttime.split(":");
        hourssplit = separated[0];



        setTimePickerInterval(timePicker_checkout);
        if(currentmins>55){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            String time24 = null;
            try {
                time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(currenttime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Log.w(TAG, "time24 : " + time24);
            String[] separated2 = time24.split(":");
            String hourssplit1 = separated2[0];
            int currenthour = Integer.parseInt(hourssplit1);
            Log.w(TAG,"selectCheckOutTime currenthour-->"+currenthour);
            timePicker_checkout.setCurrentHour(currenthour+1);

        }
        currentmin = (currentmins+4)/5;
        timePicker_checkout.setCurrentMinute(currentmin);
        timePicker_checkout.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute){
                minute =(minute*5);

                Log.w(TAG,"selectCheckOutTime onTimeChanged : "+" hourOfDay : "+hourOfDay+" minute : "+minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                //  String currentDateandTime = sdf.format(new Date());

                //  String checkindateandtime = checkinDate+" "+checkinTime;
                //Log.w(TAG,"selectCheckOutTime setOnTimeChangedListener checkindateandtime : "+checkindateandtime);
                //  checkoutTime = currentDateandTime.substring(checkindateandtime.indexOf(' ') + 1);

                String selectedhours,selectedminutes;
                String format;

                if (hourOfDay == 0) {

                    hourOfDay += 12;

                    format = "AM";
                }
                else if (hourOfDay == 12) {

                    format = "PM";

                }
                else if (hourOfDay > 12) {

                    hourOfDay -= 12;

                    format = "PM";

                }
                else {

                    format = "AM";
                }

                if(hourOfDay < 10){
                    selectedhours = "0"+hourOfDay;
                }
                else {
                    selectedhours = String.valueOf(hourOfDay);
                }
                if(minute < 10){
                    selectedminutes = "0"+minute;
                }
                else {
                    selectedminutes = String.valueOf(minute);
                }

                Log.w(TAG,"selectCheckOutTime selectedhours : "+selectedhours+" selectedminutes : "+selectedminutes+" format : "+format);
                checkoutTime = selectedhours+":"+selectedminutes+" "+format;
                Log.w(TAG,"selectCheckOutTime checkoutTime : "+checkoutTime);
                txt_checkout_time.setText(checkoutTime);
                String currentCheckoutdateandtime = checkoutDate+" "+checkoutTime;


                try{

                    Date dateCheckin = sdf.parse(checkindateandtime);
                    Date dateCheckout = sdf.parse(checkoutdateandtime);
                    Date dateCheckoutCurrent = sdf.parse(currentCheckoutdateandtime);

                    if(dateCheckin != null && dateCheckoutCurrent != null) {
                        printDifference(dateCheckin, dateCheckoutCurrent);
                    }


                    Log.w(TAG,"checkoutdateandtime : "+checkoutdateandtime+" currentCheckoutdateandtime : "+currentCheckoutdateandtime);
                    Log.w(TAG,"dateCheckout : "+dateCheckout+" dateCheckoutCurrent : "+dateCheckoutCurrent);
                    Log.w(TAG,"Condition : "+dateCheckoutCurrent.before(dateCheckout));
                    if(checkinDate.equals(checkoutdate) &&  dateCheckoutCurrent.before(dateCheckout)){
                        Log.w(TAG,"################################33");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                        String time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(checkouttime)));
                        //Log.w(TAG, "time24 : " + time24);
                        String[] separated = time24.split(":");
                        String hourssplit = separated[0];
                        Log.w(TAG,"hourssplit : "+hourssplit);
                        String minutessplit = separated[1];
                        int currentmins = Integer.parseInt(minutessplit);
                        Log.w(TAG,"currentmin12: "+currentmins);


                        if(currentmins>55){
                            int currenthour = Integer.parseInt(hourssplit);
                            timePicker_checkout.setCurrentHour(currenthour+1);

                        }else{
                            timePicker_checkout.setCurrentHour(Integer.valueOf(hourssplit));

                        }
                        timePicker_checkout.setCurrentMinute((currentmins+4)/5);

                    }


                }catch (ParseException e){
                    e.printStackTrace();
                }

            }


        });
    }




    @SuppressLint("LongLogTag")
    private boolean validateInputs() throws ParseException {

        boolean validate = true;


        if (txt_checkin_date.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Checkindate", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkout_date.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Checkoutdate", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkin_time.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please CheckinTime", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkout_time.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Checkouttime", Toast.LENGTH_SHORT).show();
            validate = false;
        }else{
            isClickDonebtn = true;
            checkTimesResponseCall(txt_checkin_time.getText().toString(), txt_checkout_time.getText().toString(), checkinDate, checkoutDate);

        }



        return validate;


    }




    private void parkingSlotCheckAvailableResponseCall(String resstarttime, String resendtime, String resstartdate, String resenddate, String vehicletypeid) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingSlotCheckAvailableResponse> call = apiInterface.parkingSlotCheckAvailableResponseCall(RestUtils.getContentType(), parkingSlotCheckAvailableRequest(resstarttime,resendtime,resstartdate,resenddate,vehicletypeid));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<ParkingSlotCheckAvailableResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ParkingSlotCheckAvailableResponse> call, @NotNull Response<ParkingSlotCheckAvailableResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingSlotCheckAvailableResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if (response.body().getData() != null) {
                    if (200 == response.body().getCode()) {
                        SessionManager sessionManager = new SessionManager(Parking_Options_Activity.this);
                        sessionManager.createParkingSlotdetails(response.body().getData().getArea(),response.body().getData().getFloor(),response.body().getData().getSlot());
                        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                            parkingChangeTimesResponseCall(resstarttime, resendtime, resstartdate, resenddate, vehicletypeid);
                        }


                    } else {
                        showErrorLoadingSlotnotavl(response.body().getMessage());
                        //Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                    }


                }
            }

            @Override
            public void onFailure(@NotNull Call<ParkingSlotCheckAvailableResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingSlotCheckAvailableResponseflr" + t.getMessage());
            }
        });

    }
    private ParkingSlotCheckAvailableRequest parkingSlotCheckAvailableRequest(String resstarttime, String resendtime, String resstartdate, String resenddate, String vehicletypeid) {
        /*
         * start_date : 23-10-2020
         * end_date : 24-10-2020
         * start_time : 10
         * end_time : 12
         * parking_vendor_id : 5f4c902d9d6fd85aedb4d79d
         * vehicle_type_id : 5f4c902d9d6fd85aedb4d79d
         */



        ParkingSlotCheckAvailableRequest parkingSlotCheckAvailableRequest = new ParkingSlotCheckAvailableRequest();
        parkingSlotCheckAvailableRequest.setStart_date(resstartdate);
        parkingSlotCheckAvailableRequest.setEnd_date(resenddate);
        parkingSlotCheckAvailableRequest.setVehicle_type_id(vehicletypeid);
        parkingSlotCheckAvailableRequest.setParking_vendor_id(parkingid);
        parkingSlotCheckAvailableRequest.setStart_time(resstarttime);
        parkingSlotCheckAvailableRequest.setEnd_time(resendtime);

        Log.w(TAG,"ParkingSlotCheckAvailableRequest"+ new Gson().toJson(parkingSlotCheckAvailableRequest));
        return parkingSlotCheckAvailableRequest;
    }



    private void parkingChangeTimesResponseCall(String checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingChangeTimesResponse> call = apiInterface.parkingChangeTimesResponseCall(RestUtils.getContentType(), parkingChangeTimesRequest(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate,selectedVehicleTypeId));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<ParkingChangeTimesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ParkingChangeTimesResponse> call, @NotNull Response<ParkingChangeTimesResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingChangeTimesResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if (response.body().getData() != null) {
                    if (200 == response.body().getCode()) {
                       // Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if(isClickDonebtn){
                            resstartdate = requestCheckinDate;
                            resstarttime = checkinhours;
                            resenddate = requestCheckoutDate;
                            resendtime = checkouthours;
                            Log.w(TAG,"parkingChangeTimesResponseCall : "+"resstartdate : "+resstartdate+" resstarttime : "+resstarttime+" resenddate : "+resenddate+" resendtime : "+resendtime);
                            String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                                    txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + strdays + " Day " + hours+" Hours" + ")";
                            txt_bookingdateandtime.setText(selectedDateandTime);

                        }



                      int totalprices = response.body().getData().getTotal_price();
                       Log.w(TAG,"parkingChangeTimesResponseCalltotalprices-->"+totalprices);
                        if(totalprices != 0){
                            parkingprices = totalprices;

                            txt_total.setText("\u20B9 " + parkingprices);
                            txt_to_pay.setText("\u20B9 " + parkingprices);
                            payamout = parkingprices;
                            txt_discount.setText("\u20B9 " + 0);


                        }


                    } else {
                        Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                    }


                }
            }

            @Override
            public void onFailure(@NotNull Call<ParkingChangeTimesResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingChangeTimesResponseflr" + t.getMessage());
            }
        });

    }
    private ParkingChangeTimesRequest parkingChangeTimesRequest(String checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId) {
        /*
         * start_date : 23-10-2020
         * end_date : 24-10-2020
         * start_time : 09:09 PM
         * end_time : 12:09 PM
         * parking_vendor_id : 5f4c902d9d6fd85aedb4d79d
         * vehicle_type_id : 5f4c902d9d6fd85aedb4d79d
         */

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ParkingChangeTimesRequest parkingChangeTimesRequest = new ParkingChangeTimesRequest();
        parkingChangeTimesRequest.setStart_date(requestCheckinDate);
        parkingChangeTimesRequest.setEnd_date(requestCheckoutDate);
        parkingChangeTimesRequest.setVehicle_type_id(vehicletypeid);
        parkingChangeTimesRequest.setParking_vendor_id(parkingid);
        parkingChangeTimesRequest.setStart_time(String.valueOf(checkinhours));
        parkingChangeTimesRequest.setEnd_time(String.valueOf(checkouthours));

        updateDate(requestCheckinDate,requestCheckoutDate,checkinhours,checkouthours);

        Log.w(TAG,"ParkingChangeTimesRequest"+ new Gson().toJson(parkingChangeTimesRequest));
        return parkingChangeTimesRequest;
    }

    private void updateDate(String requestCheckinDate, String requestCheckoutDate, String checkinhours, String checkouthours) {
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");

        Date datestart = null, dateend = null;
        try {
            if(requestCheckinDate != null && requestCheckoutDate != null ) {
                datestart = inputFormat.parse(requestCheckinDate);
                dateend = inputFormat.parse(requestCheckoutDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (datestart != null) {
            stratdate = outputFormat.format(datestart);
        }

        if (dateend != null) {
            enddate = outputFormat.format(dateend);
        }


        strattime = checkinhours ;
        endtime = checkouthours ;

    }


    public void showErrorLoadingSlotnotavl(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(Parking_Options_Activity.this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {
                    onBackPressed();
                    hideLoading();

                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(Parking_Options_Activity.this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {

                    hideLoading();

                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading() {
        try {
            alertDialog.dismiss();
        } catch (Exception ignored) {

        }
    }


    public void couponsCodeValidationResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CouponsCodeValidationResponse> call = apiInterface.couponsCodeValidationResponseCall(RestUtils.getContentType(),couponsCodeValidationRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CouponsCodeValidationResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<CouponsCodeValidationResponse> call, @NotNull Response<CouponsCodeValidationResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"CouponsCodeValidationResponse" + new Gson().toJson(response.body()));
                if(response.body() != null) {
                    if (response.body().getData() != null) {
                        if (200 == response.body().getCode()) {
                            Toasty.success(getApplicationContext(), getResources().getString(R.string.couponcodeapplied), Toast.LENGTH_SHORT, true).show();

                            Coupon_Code = edt_coupon.getText().toString();
                            valueType = response.body().getData().getValue_Type();
                            value = response.body().getData().getValue();
                            if (valueType.equalsIgnoreCase("Per")) {
                                Coupon_Code_Amount = String.valueOf(value);

                                double per = (double) value;
                                Log.w(TAG, "per-->" + per+" discountamout : "+parkingprices);
                                double res = (parkingprices / 100.0f) * per;


                                double coupondiscounttotalamount = parkingprices - res;
                                Log.w(TAG, "coupondiscounttotalamount-->" + coupondiscounttotalamount);
                                Coupon_Code_Percentage = String.valueOf(value);
                                Coupon_Code_Amount = String.valueOf(res);

                                youPay = (int) coupondiscounttotalamount;

                                Log.w(TAG, "youPay-->" + youPay);
                                txt_discount.setText("\u20B9"+" "+res);

                                 topay = (int) coupondiscounttotalamount;
                                if(topay > 0){
                                    txt_to_pay.setText("\u20B9"+" "+topay);
                                    payamout = topay;
                                }else{
                                    topay = 0;
                                    payamout = topay;
                                    txt_to_pay.setText("\u20B9"+" "+0);

                                }

                            } else if (valueType.equalsIgnoreCase("Amt")) {
                                Coupon_Code_Amount = String.valueOf(value);
                                Log.w(TAG, "youPay-->" + youPay);
                                txt_discount.setText("\u20B9"+" "+value);
                                topay = parkingprices - value;
                                   if(topay > 0){
                                       payamout = topay;
                                       txt_to_pay.setText("\u20B9"+" "+topay);

                                   }else{
                                       topay = 0;
                                       payamout = topay;
                                       txt_to_pay.setText("\u20B9"+" "+0);

                                   }


                            }

                        } else {
                            Coupon_Code = "";
                            showErrorLoading(response.body().getMessage());

                        }


                    }
                }









            }


            @Override
            public void onFailure(@NotNull Call<CouponsCodeValidationResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"CouponsCodeValidationResponseflr"+t.getMessage());
            }
        });

    }
    private CouponsCodeValidationRequest couponsCodeValidationRequest() {
        /*
         * Coupon_Code : MAC
         * Vehicle_type_id : qweqweqweqweq123123
         * Amount : 2000
         */
        CouponsCodeValidationRequest couponsCodeValidationRequest = new CouponsCodeValidationRequest();
        couponsCodeValidationRequest.setCoupon_Code(edt_coupon.getText().toString());
        couponsCodeValidationRequest.setVehicle_type_id(vehicletypeid);
        couponsCodeValidationRequest.setAmount(youPay);
        Log.w(TAG,"CouponsCodeValidationRequest"+ new Gson().toJson(couponsCodeValidationRequest));
        return couponsCodeValidationRequest;
    }


    private boolean validateCouponCodeInputs() {
        if (edt_coupon.getText().toString().equalsIgnoreCase("")) {
            edt_coupon.setError("Please enter your coupon code");
            edt_coupon.requestFocus();
            return false;
        }
        return true;
    }

    private void calculateTotalhrs(String  checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate) {

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

                            days = response.body().getData().getDays();
                            strdays = String.valueOf(days);
                            hours = response.body().getData().getHours();
                             min = response.body().getData().getMin();

                            checkin_date = response.body().getData().getCheckin_date();
                            checkin_time = response.body().getData().getCheckin_time();
                            checkout_date = response.body().getData().getCheckout_date();
                            checkout_time  = response.body().getData().getCheckout_time();
                            strtotalHours  = response.body().getData().getTotal_hrs();


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
                            isValidBookingData = true;
                            days = response.body().getData().getDays();
                            strdays = String.valueOf(days);
                            hours = response.body().getData().getHours();
                             min = response.body().getData().getMin();

                            checkin_date = response.body().getData().getCheckin_date();
                            checkin_time = response.body().getData().getCheckin_time();
                            checkout_date = response.body().getData().getCheckout_date();
                            checkout_time  = response.body().getData().getCheckout_time();
                            strtotalHours  = response.body().getData().getTotal_hrs();

                            if (new ConnectionDetector(Parking_Options_Activity.this).isNetworkAvailable(Parking_Options_Activity.this)) {
                                bottomSheetDialog.dismiss();
                                parkingChangeTimesResponseCall(checkin_time, checkout_time, checkin_date, checkout_date, vehicletypeid);
                            }


                            if(checkin_time != null){
                                txt_checkin_time.setText(checkin_time);
                            }
                            if(checkout_time != null){
                                txt_checkout_time.setText(checkout_time);
                            }

                            resstarttime = checkin_time;
                            resendtime = checkout_time;


                            String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + checkinhours + " to " +
                                    txt_checkout_date.getText().toString() + ", " + checkouthours + "(" + days + " Day " + hours+" Hours" + ")";
                            txt_selecteddate_time.setText(selectedDateandTime);
                            txt_selecteddate_time.setTextSize(12);
                            txt_bookingdateandtime.setText(selectedDateandTime);
                        }





                    }
                    else {
                        isValidBookingData = false;
                        showErrorLoading(response.body().getMessage());

                        checkin_date = response.body().getData().getCheckin_date();
                        checkin_time = response.body().getData().getCheckin_time();
                        checkout_date = response.body().getData().getCheckout_date();
                        checkout_time  = response.body().getData().getCheckout_time();

                        if(checkin_time != null){
                            txt_checkin_time.setText(checkin_time);
                        }
                        if(checkout_time != null){
                            txt_checkout_time.setText(checkout_time);
                        }

                        String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                                txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + 0 + " Day " + "0 Hours"+")";
                        txt_selecteddate_time.setText(selectedDateandTime);

                        //  Toasty.error(mContext, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

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

    private boolean checkValidDateandTime(String checkinTime, String checkoutTime, String checkinDate, String checkoutDate){
        boolean validate = true;
        Log.w(TAG,"checkValidDateandTime :"+" checkinDate : "+ checkinDate +" checkoutDate : "+ checkoutDate +" checkinTime : "+checkinTime+" checkoutTime : "+checkoutTime);


        try {
            String checkindateandtime = checkinDate +" "+checkinTime;
            String checkoutdateandtime = checkoutDate +" "+checkoutTime;
            Log.w(TAG,"checkValidDateandTime :"+" checkindateandtime : "+checkindateandtime+" checkoutdateandtime : "+checkoutdateandtime);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
            Date localdateandtime  = simpleDateFormat.parse(currentDateandTime);
            Date dateandtimecheckin  = simpleDateFormat.parse(checkindateandtime);
            Date dateandtimecheckout  = simpleDateFormat.parse(checkoutdateandtime);
            if (dateandtimecheckin != null && localdateandtime != null) {

                Log.w(TAG,"Condition else if1: "+localdateandtime.after(dateandtimecheckin));
                Log.w(TAG,"Condition else if2: "+dateandtimecheckin.after(dateandtimecheckout));
                Log.w(TAG,"Condition else equals1: "+(localdateandtime.compareTo(dateandtimecheckin) != 0));
                Log.w(TAG,"Condition else equals2: "+dateandtimecheckin.equals(dateandtimecheckout));
                Log.w(TAG,"isCheckedin: "+isCheckedin);



                if(formattedDate1.equals(checkinDate) && formattedDate1.equals(checkoutDate)) {
                    Log.w(TAG, "IF------>");
                    if (isCheckedin) {
                        Log.w(TAG, "IF------>"+"isCheckedin : "+isCheckedin);


                        if (dateandtimecheckin.equals(dateandtimecheckout)) {
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                            Log.w(TAG, "start is equal to end");
                        } else if (localdateandtime.after(dateandtimecheckin) || localdateandtime.compareTo(dateandtimecheckin) == 0) {
                            Log.w(TAG, "current dateandtime greaterthan checkindateandtime " + "localdateandtime : " + localdateandtime + "dateandtimecheckin : " + dateandtimecheckin);
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                        } else if (dateandtimecheckin.after(dateandtimecheckout)) {
                            Log.w(TAG, "start is after end");
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                        }
                    }else{
                        Log.w(TAG, "Else------>"+"isCheckedin : "+isCheckedin);

                        if (dateandtimecheckin.equals(dateandtimecheckout)) {
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                            Log.w(TAG, "start is equal to end");
                        } else if (localdateandtime.after(dateandtimecheckin)) {
                            Log.w(TAG, "current dateandtime greaterthan checkindateandtime " + "localdateandtime : " + localdateandtime + "dateandtimecheckin : " + dateandtimecheckin);
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                        } else if (dateandtimecheckin.after(dateandtimecheckout)) {
                            Log.w(TAG, "start is after end");
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                        }
                    }

                }else{
                    Log.w(TAG,"ELSE------>");
                    if (dateandtimecheckin.equals(dateandtimecheckout)) {
                        Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                        Log.w(TAG,"start is equal to end");
                    }
                    else if (dateandtimecheckin.after(dateandtimecheckout)) {
                        Log.w(TAG,"start is after end");
                        Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                    }


                }





            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return validate;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((bottomSheetDialog != null) && bottomSheetDialog.isShowing())
            bottomSheetDialog.dismiss();
        bottomSheetDialog = null;
    }


    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        Log.w(TAG,"startDate : " + startDate);
        Log.w(TAG,"endDate : "+ endDate);
        Log.w(TAG,"different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        Log.w(TAG, ""+"elapsedDays : "+elapsedDays+" elapsedHours : "+ elapsedHours+" elapsedMinutes : "+ elapsedMinutes+ elapsedSeconds);

        String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + elapsedDays + " Day " + elapsedHours+" Hours"+ ")";

        Log.w(TAG,"selectedDateandTime--->"+selectedDateandTime);
        txt_selecteddate_time.setText(selectedDateandTime);
        txt_selecteddate_time.setTextSize(12);
    }

    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            int TIME_PICKER_INTERVAL = 5;
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));


        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }
    }

    public void getdateandtime(String currentdateandtime ){
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        final Date date;
        try {


            date = sdf.parse(currentdateandtime);
            final Calendar calendar = Calendar.getInstance();

            if (date != null) {
                calendar.setTime(date);
            }
            calendar.add(Calendar.HOUR, 1);

            // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
            String currentDateandTime = sdf.format(calendar.getTime());

            Log.w(TAG,"getdateandtime Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
            Log.w(TAG,"getdateandtime Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
            defaultcheckouthours = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
            requestCheckoutDate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));

            // String defaultcheckoutdate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
            // convertDateFormatyyyyMMddtoddMMM(defaultcheckoutdate);
            Log.w(TAG," getdateandtime defaultcheckouthours-->"+defaultcheckouthours);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void convertcinDateFormatyyyyMMddtoddMMM(String inputDate) {
        Log.w(TAG,"convertcinDateFormatyyyyMMddtoddMMM : "+inputDate);
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
            String outputDateStr = outputFormat.format(dateformat);
            Log.w(TAG,"convertcinDateFormatyyyyMMddtoddMMM : "+outputDateStr);
            txt_checkin_date.setText(outputDateStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void convertcoutDateFormatyyyyMMddtoddMMM(String inputDate) {
        Log.w(TAG,"convertcoutDateFormatyyyyMMddtoddMMM : "+inputDate);

        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
            String outputDateStr = outputFormat.format(dateformat);
            Log.w(TAG,"convertcoutDateFormatyyyyMMddtoddMMM : "+outputDateStr);

            txt_checkout_date.setText(outputDateStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void GetTimeWithAMPMFromTime(String dt) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            Date date = inFormat.parse(dt);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm ");
            checkintime24hrs = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void convertTimeWithAMPMFromTime(String dt) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            Date date = inFormat.parse(dt);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            checkouttime24hrs = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}