package com.triton.myvacala.activities.parking;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.CheckTimes11Request;
import com.triton.myvacala.requestpojo.CheckTimesRequest;
import com.triton.myvacala.requestpojo.ParkingExtChangeTimesRequest;
import com.triton.myvacala.requestpojo.ParkingExtendDateTimeRequest;
import com.triton.myvacala.requestpojo.QrcodeCheckinEntryRequest;
import com.triton.myvacala.responsepojo.AdditionHrsResponse;
import com.triton.myvacala.responsepojo.CheckTimesResponse;
import com.triton.myvacala.responsepojo.ParkingBookingCreateResponse;
import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;
import com.triton.myvacala.responsepojo.ParkingExtChangeTimesResponse;
import com.triton.myvacala.responsepojo.ParkingExtendDateTimeResponse;
import com.triton.myvacala.responsepojo.QrcodeCheckinEntryResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class ParkingConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="ParkingConfirmActivity" ;
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.img_slideqrscan)
    ImageView img_slideqrscan;

    @BindView(R.id.cardview_extendparking)
    CardView cardview_extendparking;



    @BindView(R.id.txt_buildingname)
    TextView txt_buildingname;

    @BindView(R.id.txt_address)
    TextView txt_address;

    @BindView(R.id.txt_distance_reachtime)
    TextView txt_distance_reachtime;

    @BindView(R.id.txt_bookingid)
    TextView txt_bookingid;

    @BindView(R.id.txt_amountpaid)
    TextView txt_amountpaid;

    @BindView(R.id.txt_floor_block_slot)
    TextView txt_floor_block_slot;

    @BindView(R.id.txt_bookingslotwindow)
    TextView txt_bookingslotwindow;

    @BindView(R.id.img_share)
    ImageView img_share;

    @BindView(R.id.cv_vehicleimage)
    CircleImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;

    @BindView(R.id.lbl_txt_discount_amount)
    TextView lbl_txt_discount_amount;

    @BindView(R.id.txt_discount_amount)
    TextView txt_discount_amount;

    private String checkinDate = null, checkoutDate = null;
    private String checkinTime = null, checkoutTime = null;

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

    int checkinhours ;
    int checkouthours ;


    private String str_checkintime,str_checkoutime;
    private  String strtotalHours,strdays;

    long totalHours = 0;

    //qr code scanner object
    private IntentIntegrator qrScan;
    private ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanArrayList;
    private ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean> vehicledetailslist ;
    private ArrayList<AdditionHrsResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanAddhrsArrayList ;

    String buildingname,address,sharelink,amount,bookingid,slotdetails,startdate,starttime,enddate,endtime,totalhours,reachtime,distance;

    String headerVehicleimg, headerVehicleName, headerVehicleBrandName, headerVehicleFuelTypeName, headerVehicleFuelTypeBackgroundcolor, vehicleNumber;

    String outputDateEndDateex;

    SessionManager sessionManager;
    private String customerid;


    private String id,parkingdetailsid;
    private String vehicleid,vehicletypeid;
    private String bookingstatus;
    private boolean isCheckedin = false;
    private boolean isValidBookingData = false;

    private String checkin_date,checkin_time,checkout_date,checkout_time;
    private int days,hours;
    private AlertDialog.Builder alertDialogBuilder;
    private Dialog alertDialog;

    private String formattedDate1;
    private String defaultcheckouttime;

    String fromactivity;
    private String checkouttime24hrs;
    private int currentmin;
    private String couponcodeamount;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_confirm);

        ButterKnife.bind(this);
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        avi_indicator.setVisibility(View.GONE);
        imgBack.setOnClickListener(this);
        cardview_extendparking.setOnClickListener(this);
        img_share.setOnClickListener(this);
        getData();

        sessionManager = new SessionManager(ParkingConfirmActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        customerid = user.get(SessionManager.KEY_ID);



        img_slideqrscan.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Display display = getWindowManager().getDefaultDisplay();
               float width = display.getWidth();
               TranslateAnimation animation = new TranslateAnimation(0, width - 50, 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
               animation.setDuration(1000); // animation duration
               animation.setRepeatCount(1); // animation repeat count
               animation.setRepeatMode(2); // repeat animation (left to right, right to left )
               img_slideqrscan.startAnimation(animation); // start animation


               final Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       //Do something after 100ms
                       qrScan.setPrompt("Scan a Parking QR code");
                       qrScan.setCameraId(0);  // Use a specific camera of the device
                       qrScan.setBeepEnabled(true);
                       qrScan.setBarcodeImageEnabled(false);
                       qrScan.setOrientationLocked(false);

                       //initiating the qr code scan
                       qrScan.initiateScan();
                   }
               }, 600);

               return false;
           }
       });

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
        Intent intent = new Intent(ParkingConfirmActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @SuppressLint("SetTextI18n")
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
            checkoutDate = enddate;
            endtime = extras.getString("endtime");
            totalhours = extras.getString("totalhours");
            reachtime = extras.getString("reachtime");
            distance = extras.getString("distance");
            id = extras.getString("id");
            parkingdetailsid = extras.getString("parkingdetailsid");
            bookingstatus = extras.getString("bookingstatus");
            couponcodeamount = extras.getString("couponcodeamount");
            Log.w(TAG,"couponcodeamount : "+couponcodeamount+ " amount :"+amount);


            if(couponcodeamount != null && !couponcodeamount.isEmpty()){
                lbl_txt_discount_amount.setVisibility(View.VISIBLE);
                txt_discount_amount.setVisibility(View.VISIBLE);
                txt_discount_amount.setText("\u20B9 "+couponcodeamount);
            }else{
                lbl_txt_discount_amount.setVisibility(View.GONE);
                txt_discount_amount.setVisibility(View.GONE);
            }


            addOneHourCheckoutTime();

            Log.w(TAG,"parkingdetailsid--->"+parkingdetailsid+"bookingid : "+bookingid+" reachtime : "+reachtime+" distance : "+distance);




            Log.w(TAG,"startdate : "+startdate+" starttime : "+starttime+" enddate : "+enddate+" endtime : "+endtime+" parkingdetailsid:"+parkingdetailsid);

            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"fromactivity : "+fromactivity);
            if(fromactivity != null && fromactivity.equalsIgnoreCase("BookingHistoryListAdapter")){
                fromactivity = "BookingHistoryListAdapter";
                Log.w(TAG,"fromactivity IF : "+fromactivity);
                bottom_navigation_parking.setSelectedItemId(R.id.bookinghistory);
                vehicledetailslist = ( ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicledetailslist");
                Log.w(TAG, "vehicledetailslist:" + new Gson().toJson(vehicledetailslist));

                if(vehicledetailslist != null){
                    getVehicleData();
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
            else{
                fromactivity = TAG;
                Log.w(TAG,"fromactivity Else : "+fromactivity);
                bottom_navigation_parking.setSelectedItemId(R.id.home);
                vehicleDetailsBeanArrayList = ( ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicleDetailsBeanArrayList");
                Log.w(TAG,  " vehicleDetailsBeanArrayList : "+new Gson().toJson(vehicleDetailsBeanArrayList));
                if(vehicleDetailsBeanArrayList != null){
                    getVehicleDataBookinghistory();
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
            if(slotdetails != null ){
                txt_floor_block_slot.setText(slotdetails);
            }

            if(distance != null && reachtime != null){
                txt_distance_reachtime.setText(reachtime + " / " + distance);

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
                Log.w(TAG,"bookingslotwindow : "+outputDateStartDate+" at "+starttime+" to "+outputDateEndDate+" at "+endtime +"("+totalhours+" Hours"+")");
                txt_bookingslotwindow.setText(outputDateStartDate+" at "+starttime+" to "+outputDateEndDate+" at "+endtime+" "+"("+totalhours+" Hours"+")");
            } catch (ParseException e) {
                e.printStackTrace();
            }












        }

    }

    private void addOneHourCheckoutTime() {

       String checkoutDateandTime = enddate+" "+endtime;
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        final Date date;
        try {
            date = sdf.parse(checkoutDateandTime);
            final Calendar calendar = Calendar.getInstance();

            if (date != null) {
                calendar.setTime(date);
            }
            calendar.add(Calendar.HOUR, 1);

            Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
            String currentDateandTime = sdf.format(calendar.getTime());

            Log.w(TAG,"roundedMins Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
            Log.w(TAG,"roundedMins Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
            String defaultcheckoutDate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
             defaultcheckouttime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
            Log.w(TAG," roundedMins defaultcheckouthours-->"+defaultcheckouttime);

            @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date enddate_ex;

            enddate_ex = inputFormat.parse(defaultcheckoutDate);



            @SuppressLint("SimpleDateFormat") DateFormat outputFormatextension = new SimpleDateFormat("dd MMM");

            if(enddate_ex != null) {
                outputDateEndDateex = outputFormatextension.format(enddate_ex);
                Log.w(TAG, "addOneHourCheckoutDate outputDateEndDateex-->" + outputDateEndDateex);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void getVehicleData() {
        Log.w(TAG,"getVehicleData()");
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
                    Log.w(TAG, "getVehicleData headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor+" vehicletypeid : "+vehicletypeid);


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
                     vehicleid = vehicleDetailsBeanAddhrsArrayList.get(i).get_id();
                     vehicletypeid = vehicleDetailsBeanAddhrsArrayList.get(i).getVehicletype_id();
                    Log.w(TAG, "getVehicleDataAddhrs headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor+" vehicletypeid : "+vehicletypeid);


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
                    Log.w(TAG, "getVehicleDataBookinghistory headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor+" vehicletypeid : "+vehicletypeid);


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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                onBackPressed();
                break;

                case R.id.cardview_extendparking:
                    checkinDate = null;
                    checkoutDate = null;
                    gotoBookingDateandTime();
                break;

            case R.id.img_share:
                if(sharelink != null){
                    gotoShareMapLink();
                }
                break;
        }
    }

    private void gotoShareMapLink() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Vacala");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharelink);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @SuppressLint("LongLogTag")
    private void gotoBookingDateandTime() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.date_time_picker_parking, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);


        LinearLayout ll_checkindate = bottomSheetDialog.findViewById(R.id.ll_checkindate);
        LinearLayout ll_checkoutdate = bottomSheetDialog.findViewById(R.id.ll_checkoutdate);
        LinearLayout ll_checkintime = bottomSheetDialog.findViewById(R.id.ll_checkintime);
        LinearLayout ll_checkoutime = bottomSheetDialog.findViewById(R.id.ll_checkoutime);
        txt_selecteddate_time = bottomSheetDialog.findViewById(R.id.txt_selecteddate_time);
        Button btn_done = bottomSheetDialog.findViewById(R.id.btn_done);

        rl_datepicker = bottomSheetDialog.findViewById(R.id.rl_datepicker);
        rl_timepicker = bottomSheetDialog.findViewById(R.id.rl_timepicker);

        rl_datepicker.setVisibility(View.GONE);
        rl_timepicker.setVisibility(View.GONE);


        TextView txt_lbl_checkindate = bottomSheetDialog.findViewById(R.id.txt_lbl_checkindate);
        TextView txt_lbl_checkintime = bottomSheetDialog.findViewById(R.id.txt_lbl_checkintime);
        TextView  txt_lbl_checkoutdate = bottomSheetDialog.findViewById(R.id.txt_lbl_checkoutdate);
        TextView txt_lbl_checkouttime = bottomSheetDialog.findViewById(R.id.txt_lbl_checkouttime);

        if (txt_lbl_checkindate != null) {
            txt_lbl_checkindate.setText(getResources().getString(R.string.extend_checkin_date));
            txt_lbl_checkindate.setTextSize(14);
        }
        if (txt_lbl_checkintime != null) {
            txt_lbl_checkintime.setText(getResources().getString(R.string.extend_checkin_time));
            txt_lbl_checkintime.setTextSize(14);
        }
        if (txt_lbl_checkoutdate != null) {
            txt_lbl_checkoutdate.setText(getResources().getString(R.string.extend_checkout_date));
            txt_lbl_checkoutdate.setTextSize(14);
        }
        if (txt_lbl_checkouttime != null) {
            txt_lbl_checkouttime.setText(getResources().getString(R.string.extend_checkout_time));
            txt_lbl_checkouttime.setTextSize(14);
        }


        txt_checkin_date = bottomSheetDialog.findViewById(R.id.txt_checkin_date);
        txt_checkout_date = bottomSheetDialog.findViewById(R.id.txt_checkout_date);
        txt_checkin_time = bottomSheetDialog.findViewById(R.id.txt_checkin_time);
        txt_checkout_time = bottomSheetDialog.findViewById(R.id.txt_checkout_time);

        txt_checkin_date.setTextSize(14);
        txt_checkout_date.setTextSize(14);
        txt_checkin_time.setTextSize(14);
        txt_checkout_time.setTextSize(14);






        ImageView img_close = bottomSheetDialog.findViewById(R.id.img_close);
        if (img_close != null) {
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });
        }

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM", Locale.getDefault());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        formattedDate1 = dateFormat.format(c);
        checkinDate = enddate;
        checkoutDate = enddate;

        Log.w(TAG,"checkinDate-->"+checkinDate);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        @SuppressLint("SimpleDateFormat")
        DateFormat date = new SimpleDateFormat("HH:mm");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String localTime = date.format(currentLocalTime);







        txt_checkin_date.setText(outputDateEndDateex);
        txt_checkout_date.setText(outputDateEndDateex);

        String[] separated = localTime.split(":");
        String strcheckintime = separated[0];
       // String defaultcheckintime = strcheckintime+":"+"00";
      //  String defaultcheckintime = starttime;
      //  String defaultcheckouttime = endtime;

        txt_checkin_time.setText(endtime);
        txt_checkout_time.setText(defaultcheckouttime);





       // strdays = "0";
        //strtotalHours = "1 Hours";
        str_checkintime = txt_checkin_time.getText().toString();
        str_checkoutime =  txt_checkout_time.getText().toString();
        String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + 0 + " Day " + "1 Hours"+")";
        txt_selecteddate_time.setText(selectedDateandTime);
        txt_selecteddate_time.setTextSize(12);

        bottomSheetDialog.show();


        if (ll_checkoutdate != null) {
            ll_checkoutdate.setOnClickListener(v -> selectCheckedOutDate(v));
        }

        if (ll_checkoutime != null) {
            ll_checkoutime.setOnClickListener(v -> {
                selectCheckOutTime(v);
            });
        }

        if (btn_done != null) {
            btn_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w(TAG," checkinTime -------------------> " + checkinTime);
                    Log.w(TAG," checkoutTime -------------------> " + checkoutTime);
                    try {
                        if (validateInputs()) {


                           /* @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterTime = new SimpleDateFormat("HH");
                            String requestCheckinDate,requestCheckoutDate;
                            Log.w(TAG," btn_done.setOnClickListener :"+"checkinDate : "+checkinDate+"checkoutDate :"+checkoutDate);
                            if (checkinDate != null && checkoutDate != null) {
                                Date c = Calendar.getInstance().getTime();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                //  requestCheckinDate = dateFormat.format(c);
                                // requestCheckoutDate = dateFormat.format(c);
                                requestCheckinDate = checkinDate;
                                requestCheckoutDate = checkoutDate;
                            } else {
                                requestCheckinDate = checkinDate;
                                requestCheckoutDate = checkoutDate;
                            }

                            try {

                                if(isValidBookingData) {

                                }
                                //parkingListResponseCall(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate, selectedVehicleTypeId);

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
        ParkingConfirmActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
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

        Log.w(TAG,"selectCheckedOutDate--->"+checkoutDate);
             if(checkoutDate != null && !checkoutDate.isEmpty()) {

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

                Log.w(TAG,"setOnDateChangedListener : "+"year : "+year+" monthOfYear : "+monthOfYear+" dayOfMonth : "+dayOfMonth);
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
                Log.w(TAG,"setOnDateChangedListener : "+checkoutDate);

                convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);

                try{
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                    String checkoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();
                    String checkindateandtime = checkinDate+" "+txt_checkin_time.getText().toString();
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
        if(defaultcheckouttime != null && !defaultcheckouttime.isEmpty()){
            convertTimeWithAMPMFromTime(defaultcheckouttime);
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
        String hourssplit = separated[0];



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

                String selectedhrs24;
                if(hourOfDay < 10){
                    selectedhrs24 = "0"+hourOfDay;
                }else{
                    selectedhrs24 = String.valueOf(hourOfDay);
                }


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
                checkouttime24hrs = selectedhrs24+":"+selectedminutes;
                String checkoutdateandtime24hrs = checkinDate+" "+checkouttime24hrs;


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
                    if(checkinDate.equals(checkoutdate) &&checkouttime24hrs.equalsIgnoreCase("00:00") ){
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

                        checkoutdateandtime24hrs = date+" "+checkouttime24hrs;
                        String checkoutdate = date;
                        String checkoutTime = time;
                        convertcoutDateFormatyyyyMMddtoddMMM(checkoutdate);


                       String checkoutdateandtime = checkoutdate+" "+checkoutTime;

                        Date date1 = sdf.parse(checkoutdateandtime);
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

                        //txt_checkout_time.setText(checkoutTime);
                        currentCheckoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();

                         dateCheckoutCurrent = sdf.parse(currentCheckoutdateandtime);

                        if(dateCheckin != null && dateCheckoutCurrent != null) {
                            printDifference(dateCheckin, dateCheckoutCurrent);
                        }

                        convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);


                    }
                    else if(checkinDate.equals(checkoutdate) &&  dateCheckoutCurrent.before(dateCheckout)){
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

                    }else{
                        if(dateCheckin != null && dateCheckoutCurrent != null) {
                            printDifference(dateCheckin, dateCheckoutCurrent);
                        }

                    }


                }catch (ParseException e){
                    e.printStackTrace();
                }

            }


        });
    }

    private void convertcoutDateFormatyyyyMMddtoddMMM(String checkoutdate) {
        Log.w(TAG,"convertcinDateFormatyyyyMMddtoddMMM : "+checkoutdate);
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(checkoutdate);
            String outputDateStr = outputFormat.format(dateformat);
            Log.w(TAG,"convertcinDateFormatyyyyMMddtoddMMM : "+outputDateStr);
            txt_checkout_date.setText(outputDateStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    private boolean validateInputs() throws ParseException {

        boolean validate = true;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        Date date1 = formatter.parse(checkinDate);
        Date date2 = formatter.parse(checkoutDate);
        Date timeCalculationCheckIn = formatterTime.parse(txt_checkin_time.getText().toString());
        Date timeCalculationCheckOut = formatterTime.parse(txt_checkout_time.getText().toString());

        String checkindateandtime = checkinDate+" "+txt_checkin_time.getText().toString();
        String checkoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();


        Log.w(TAG,"totalHours--->"+totalHours+"strtotalHours :"+strtotalHours);


        Log.w(TAG,"validateInputs "+"checkindateandtime:"+checkindateandtime+"checkoutdateandtime :"+checkoutdateandtime);

        if (txt_checkin_date.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Checkindate", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkout_date.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Checkoutdate", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkin_time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please CheckinTime", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkout_time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Checkouttime", Toast.LENGTH_SHORT).show();
            validate = false;
        } else{
                checkTimesResponseCall(txt_checkin_time.getText().toString(), txt_checkout_time.getText().toString(), checkinDate, checkoutDate);

            }

        /*else if (date1.compareTo(date2) > 0) {

            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
            Log.w(TAG, "Date1 is after Date2");
            validate = false;

        }else if (timeCalculationCheckIn.getTime() == timeCalculationCheckOut.getTime()) {
            Log.w(TAG,"CheckinandOutdate : "+txt_checkin_date.getText().toString()+"  "+txt_checkout_date.getText().toString());
            if(txt_checkin_date.getText().toString().equals(txt_checkout_date.getText().toString())){
                Log.w(TAG,"CheckinandOutTime : "+timeCalculationCheckIn.getTime()+"  "+timeCalculationCheckOut.getTime());
                Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_time), Toast.LENGTH_SHORT, true).show();
                Log.w(TAG, "Time1 is same time time2");
                validate = false;
            }

        } else if (date1.equals(date2) && timeCalculationCheckIn.getTime() > timeCalculationCheckOut.getTime()) {
            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_time), Toast.LENGTH_SHORT, true).show();
            Log.w(TAG, "Time1 is after time2");
            validate = false;

        }
*/
        return validate;



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Log.w(TAG,"Result Code :"+resultCode+" "+"data:"+data);
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toasty.warning(getApplicationContext(), "Result Not Found", Toast.LENGTH_SHORT, true).show();


            } else {
                //if qr contains data
                try {

                    Log.w(TAG,"Results--->"+result.getContents());

                    if(result.getContents() != null){
                        //converting the data to json

                        String results = result.getContents();

                        if (results.contains("{")) {
                            Log.w(TAG,"if"+results);
                            JSONObject obj = new JSONObject(result.getContents());

                            Log.w(TAG,"obj"+obj);
                              /*{"Vendor_id":"5f4798fc99c5a35d06ad04f2","Vendor_Name":"par-09",
                    "Parking_Area_Name":"blovk2","Vehicletype_id":"5f0c0cfc2f857d66950cf25f",
                    "Lat":12.8992994,"Long":79.0483023,"Entry":"Check In","Block_Name":"floor5"}*/

                            if (obj.getString("Entry").equalsIgnoreCase("Check In")) {
                                Log.w(TAG,"If---->"+obj.getString("Entry"));
                                String vendor_id = obj.getString("Vendor_id");
                                //setting values to textviews
                                Log.w(TAG, obj.getString("Vendor_id"));
                                Log.w(TAG, obj.getString("Vendor_Name"));
                                Log.w(TAG, obj.getString("Parking_Area_Name"));

                                Date c = Calendar.getInstance().getTime();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String requestCurrentDate = dateFormat.format(c);
                                Calendar calendar = Calendar.getInstance();
                                int Hr24 = calendar.get(Calendar.HOUR_OF_DAY);
                               // String requestCurrentHour = String.valueOf(Hr24);

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                                String currentDateandTime = sdf.format(new Date());
                                Log.w(TAG," Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
                                Log.w(TAG," Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
                                String requestCurrentHour = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);


                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                                String currentdatetime = dateformat.format(calendar.getTime());

                                if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                                    qrcodeCheckinEntryResponseCall(vendor_id,requestCurrentDate,requestCurrentHour,currentdatetime);
                                }
                            }
                            else {
                                Log.w(TAG,"Else--->"+obj.getString("Entry"));

                                alertValidQrCode();
                            }

                        } else {
                            Log.w(TAG,"else"+results);
                            alertValidQrCode();

                        }




                       }
                    else{
                        alertValidQrCode();
                        }





                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode

                    Log.w(TAG,"Result------------->"+result.getContents());

                    //to a toast

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alertValidQrCode() {
        final AlertDialog alertDialog = new AlertDialog.Builder(ParkingConfirmActivity.this).create();
        alertDialog.setMessage(getString(R.string.err_msg_scan));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      callDirections("2");
    }



    private void qrcodeCheckinEntryResponseCall(String vendor_id, String requestCurrentDate, String requestCurrentHour, String currentdatetime) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<QrcodeCheckinEntryResponse> call = apiInterface.qrcodeCheckinEntryResponseCall(RestUtils.getContentType(), qrcodeCheckinEntryRequest(vendor_id,requestCurrentDate,requestCurrentHour,currentdatetime));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<QrcodeCheckinEntryResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<QrcodeCheckinEntryResponse> call, @NotNull Response<QrcodeCheckinEntryResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "QrcodeCheckinEntryResponse" + new Gson().toJson(response.body()));

                if(response.body() != null) {

                        if (200 == response.body().getCode()) {
                            Intent intent = new Intent(ParkingConfirmActivity.this, CheckedInActivity.class);
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
                            intent.putExtra("vehicledetailslist", vehicledetailslist);
                            intent.putExtra("vehicleDetailsBeanArrayList", vehicleDetailsBeanArrayList);
                            intent.putExtra("vehicleDetailsBeanAddhrsArrayList", vehicleDetailsBeanAddhrsArrayList);
                            intent.putExtra("reachtime", reachtime);
                            intent.putExtra("distance", distance);
                            intent.putExtra("id", id);
                            intent.putExtra("fromactivity",fromactivity);
                            intent.putExtra("parkingdetailsid",parkingdetailsid);

                            startActivity(intent);


                        } else {
                            Toasty.warning(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        }



                }
            }

            @Override
            public void onFailure(@NotNull Call<QrcodeCheckinEntryResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "QrcodeCheckinEntryResponseflr" + t.getMessage());
            }
        });

    }
    private QrcodeCheckinEntryRequest qrcodeCheckinEntryRequest(String vendor_id, String requestCurrentDate, String requestCurrentHour, String currentdatetime) {
        /*
         * Booking_id : Bk-ACDBUv
         * parkingdetails_id : 5f4c902d9d6fd85aedb4d79d
         * Cur_date : 2020-08-10
         * Cur_time : 12:00 PM
         * Cur_date_time : 2020-08-10T12:00:00
         * Customer_id : 5f1fc277cfb5c741551e1793
         */
        QrcodeCheckinEntryRequest qrcodeCheckinEntryRequest = new QrcodeCheckinEntryRequest();
        qrcodeCheckinEntryRequest.setBooking_id(bookingid);
        qrcodeCheckinEntryRequest.setParkingdetails_id(vendor_id);
        qrcodeCheckinEntryRequest.setCur_date(requestCurrentDate);
        qrcodeCheckinEntryRequest.setCur_time(requestCurrentHour);
        qrcodeCheckinEntryRequest.setCur_date_time(currentdatetime);
        qrcodeCheckinEntryRequest.setCustomer_id(customerid);

        Log.w(TAG,"qrcodeCheckinEntryRequest"+ new Gson().toJson(qrcodeCheckinEntryRequest));
        return qrcodeCheckinEntryRequest;
    }


    private void parkingExtendDateTimeResponseCall(String resstarttime, String resendtime, String resstartdate, String resenddate, String vehicletypeid) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingExtendDateTimeResponse> call = apiInterface.parkingExtendDateTimeResponseCall(RestUtils.getContentType(), parkingExtendDateTimeRequest(resstarttime,resendtime,resstartdate,resenddate,vehicletypeid));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<ParkingExtendDateTimeResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ParkingExtendDateTimeResponse> call, @NotNull Response<ParkingExtendDateTimeResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingExtendDateTimeResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if (response.body().getData() != null) {
                    if (200 == response.body().getCode()) {
                        //enddate = resenddate;
                        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                            Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                            parkingExtChangeTimesResponseCall(resstarttime, resendtime, resstartdate, resenddate, vehicletypeid);

                        }


                    } else {
                        Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                    }


                }
            }

            @Override
            public void onFailure(@NotNull Call<ParkingExtendDateTimeResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingExtendDateTimeResponseflr" + t.getMessage());
            }
        });

    }
    private ParkingExtendDateTimeRequest parkingExtendDateTimeRequest(String resstarttime, String resendtime, String resstartdate, String resenddate, String vehicletypeid) {
        /*
         * start_date : 2020-08-20
         * end_date : 2020-08-18
         * start_time : 08:00 PM
         * end_time : 09:00 PM
         * parking_vendor_id : 5f4793beb12e0b5507b79f74
         * vehicle_type_id : 5f0c0d092f857d66950cf260
         * slot_details
         */
        ParkingExtendDateTimeRequest ParkingExtendDateTimeRequest = new ParkingExtendDateTimeRequest();
        ParkingExtendDateTimeRequest.setStart_date(resstartdate);
        ParkingExtendDateTimeRequest.setEnd_date(resenddate);
        ParkingExtendDateTimeRequest.setVehicle_type_id(vehicletypeid);
        ParkingExtendDateTimeRequest.setParking_vendor_id(parkingdetailsid);
        ParkingExtendDateTimeRequest.setStart_time(resstarttime);
        ParkingExtendDateTimeRequest.setEnd_time(resendtime);
        ParkingExtendDateTimeRequest.setSlot_details(slotdetails);

        Log.w(TAG,"ParkingExtendDateTimeRequest"+ new Gson().toJson(ParkingExtendDateTimeRequest));
        return ParkingExtendDateTimeRequest;
    }



    private void parkingExtChangeTimesResponseCall(String  checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingExtChangeTimesResponse> call = apiInterface.parkingExtChangeTimesResponseCall(RestUtils.getContentType(), parkingExtChangeTimesRequest(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate,selectedVehicleTypeId));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<ParkingExtChangeTimesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ParkingExtChangeTimesResponse> call, @NotNull Response<ParkingExtChangeTimesResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "parkingExtChangeTimesResponseCall" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().isTime_extancsion()){
                            Intent intent = new Intent(ParkingConfirmActivity.this, FinalPayableActivity.class);
                            intent.putExtra("buildingname", buildingname);
                            intent.putExtra("address", address);
                            intent.putExtra("sharelink", sharelink);
                            intent.putExtra("amount", amount);
                            intent.putExtra("bookingid", bookingid);
                            intent.putExtra("slotdetails", slotdetails);
                            intent.putExtra("startdate", startdate);
                            intent.putExtra("starttime", starttime);
                            intent.putExtra("enddate", requestCheckoutDate);
                            intent.putExtra("endtime", checkouthours);
                            intent.putExtra("totalhours", totalhours);
                            intent.putExtra("vehicledetailslist", vehicledetailslist);
                            intent.putExtra("vehicleDetailsBeanArrayList", vehicleDetailsBeanArrayList);
                            intent.putExtra("vehicleDetailsBeanAddhrsArrayList", vehicleDetailsBeanAddhrsArrayList);
                            intent.putExtra("reachtime", reachtime);
                            intent.putExtra("distance", distance);
                            intent.putExtra("id",id);
                            intent.putExtra("parkingdetailsid",parkingdetailsid);
                            intent.putExtra("vehicleid",vehicleid);
                            intent.putExtra("vehicletypeid",vehicletypeid);
                            intent.putExtra("fromactivity", fromactivity);

                            intent.putExtra("totalprice", response.body().getData().getTotal_price());
                            intent.putExtra("finaltotal", response.body().getData().getFinal_total());
                            intent.putExtra("alreadypay", response.body().getData().getAlready_pay());
                            intent.putExtra("bookingidres", response.body().getData().getBooking_id());
                            intent.putExtra("additionalbookinghrs", response.body().getData().getAdditional_booking_hrs());
                            intent.putExtra("additionalbookingamout", response.body().getData().getAdditonal_booking_amount());
                            intent.putExtra("overalltimeduration", response.body().getData().getOverall_time_duraion());
                            intent.putExtra("overallamountpaid", response.body().getData().getOverall_amount_paid());
                            intent.putExtra("extratime", response.body().getData().getExtra_time());
                            intent.putExtra("bookingstatus", response.body().getData().getBooking_status());

                            startActivity(intent);
                        }
                       /* else{
                            Intent intent = new Intent(ParkingConfirmActivity.this, CheckedInActivity.class);
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
                            intent.putExtra("vehicledetailslist", vehicledetailslist);
                            intent.putExtra("vehicleDetailsBeanArrayList", vehicleDetailsBeanArrayList);
                            intent.putExtra("reachtime", reachtime);
                            intent.putExtra("distance", distance);
                            intent.putExtra("id",id);
                            intent.putExtra("parkingdetailsid",parkingdetailsid);
                            intent.putExtra("vehicleid",vehicleid);
                            intent.putExtra("vehicletypeid",vehicletypeid);
                            intent.putExtra("fromactivity", TAG);
                            startActivity(intent);
                        }*/




                    }
                    else {
                        Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                    }


                }
            }

            @Override
            public void onFailure(@NotNull Call<ParkingExtChangeTimesResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingExtChangeTimesResponse flr" + t.getMessage());
            }
        });

    }
    private ParkingExtChangeTimesRequest parkingExtChangeTimesRequest(String checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId) {
        /*
         * start_date : 2020-09-08
         * end_date : 2020-09-08
         * start_time : 21
         * end_time : 22
         * parking_vendor_id : 5f4793beb12e0b5507b79f74
         * vehicle_type_id : 5f0c0d092f857d66950cf260
         * booking_id : 5f578f5e2ccd2a5711ced06c
         */


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ParkingExtChangeTimesRequest parkingExtChangeTimesRequest = new ParkingExtChangeTimesRequest();
        parkingExtChangeTimesRequest.setStart_date(requestCheckinDate);
        parkingExtChangeTimesRequest.setEnd_date(requestCheckoutDate);
        parkingExtChangeTimesRequest.setVehicle_type_id(vehicletypeid);
        parkingExtChangeTimesRequest.setParking_vendor_id(parkingdetailsid);
        parkingExtChangeTimesRequest.setStart_time(checkinhours);
        parkingExtChangeTimesRequest.setEnd_time(checkouthours);
        parkingExtChangeTimesRequest.setBooking_id(id);

        Log.w(TAG,"parkingExtChangeTimesRequest"+ new Gson().toJson(parkingExtChangeTimesRequest));
        return parkingExtChangeTimesRequest;
    }

    private void checkTimesResponseCall(String  checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CheckTimesResponse> call = apiInterface.checkTimes2ResponseCall(RestUtils.getContentType(), checkTimesRequest(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate));
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
                            hours = response.body().getData().getHours();
                            int min = response.body().getData().getMin();

                            checkin_date = response.body().getData().getCheckin_date();
                            checkin_time = response.body().getData().getCheckin_time();
                            checkout_date = response.body().getData().getCheckout_date();
                            checkout_time  = response.body().getData().getCheckout_time();
                            //endtime = checkout_time;
                             checkinDate = checkin_date;
                            checkoutDate =  checkout_date;

                            parkingExtendDateTimeResponseCall(checkin_time, checkout_time, checkin_date, checkout_date, vehicletypeid);
                            bottomSheetDialog.dismiss();
                            String total_hrs  = response.body().getData().getTotal_hrs();
                            if(total_hrs != null) {
                                strtotalHours = total_hrs;
                                //txt_noofhours.setText(total_hrs+" Hours");
                            }

                           /*  if(checkin_time != null){
                                txt_checkin_time.setText(checkin_time);
                            }*/
                            if(checkout_time != null){
                                txt_checkout_time.setText(checkout_time);
                            }
                            String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + checkinhours + " to " +
                                    txt_checkout_date.getText().toString() + ", " + checkouthours + "(" + days + " Day " + hours+" Hours" + ")";
                            txt_selecteddate_time.setText(selectedDateandTime);
                            txt_selecteddate_time.setTextSize(12);
                            txt_selecteddate_time.setText(selectedDateandTime);
                        }





                    } else {
                        isValidBookingData = false;
                        showErrorLoading(response.body().getMessage());

                        checkin_date = response.body().getData().getCheckin_date();
                        checkin_time = response.body().getData().getCheckin_time();
                        checkout_date = response.body().getData().getCheckout_date();
                        checkout_time  = response.body().getData().getCheckout_time();
                       // endtime = checkout_time;
                        String total_hrs  = response.body().getData().getTotal_hrs();
                        if(total_hrs != null) {
                            strtotalHours = total_hrs;
                           // txt_noofhours.setText(total_hrs+" Hours");
                        }

                       /* if(checkin_time != null){
                            txt_checkin_time.setText(checkin_time);
                        }*/
                        if(checkout_time != null){
                            txt_checkout_time.setText(checkout_time);
                        }

                        String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                                txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + 0 + " Day " + "0 Hours"+")";
                        txt_selecteddate_time.setText(selectedDateandTime);
                        //txt_noofhours.setText("0 Hours");
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
    private CheckTimes11Request checkTimesRequest(String checkin_time, String checkout_time, String requestCheckinDate, String requestCheckoutDate) {
        /*
         * pre_checkindate : 2020-10-06 09:00 PM
         * checkin_date : 2020-10-05
         * checkin_time : 08:00 PM
         * checkout_date : 2020-10-18
         * checkout_time : 09:00 PM
         */

        CheckTimes11Request checkTimesRequest = new CheckTimes11Request();
        checkTimesRequest.setPre_checkindate(startdate+" "+starttime);
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
            //Date localdateandtime  = simpleDateFormat.parse(currentDateandTime);
            Date dateandtimecheckin  = simpleDateFormat.parse(checkindateandtime);
            Date dateandtimecheckout  = simpleDateFormat.parse(checkoutdateandtime);
            if (dateandtimecheckin != null ) {





                if(formattedDate1.equals(checkinDate) && formattedDate1.equals(checkoutDate)) {
                    Log.w(TAG, "IF------>");
                    if (isCheckedin) {
                        Log.w(TAG, "IF------>"+"isCheckedin : "+isCheckedin);


                        if (dateandtimecheckin.equals(dateandtimecheckout)) {
                            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                            Log.w(TAG, "start is equal to end");
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
                        }  else if (dateandtimecheckin.after(dateandtimecheckout)) {
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


    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(ParkingConfirmActivity.this);
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

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        Log.w(TAG,"printDifference startDate : " + startDate);
        Log.w(TAG,"printDifference endDate : "+ endDate);
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




}