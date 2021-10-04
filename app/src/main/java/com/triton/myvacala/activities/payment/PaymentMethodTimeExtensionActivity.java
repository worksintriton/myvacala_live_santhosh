package com.triton.myvacala.activities.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.CheckedInActivity;
import com.triton.myvacala.activities.parking.CheckedOutActivity;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;

import com.triton.myvacala.activities.parking.ParkingConfirmActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.AdditionHrsRequest;

import com.triton.myvacala.responsepojo.AdditionHrsResponse;
import com.triton.myvacala.responsepojo.ParkingBookingCreateResponse;
import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodTimeExtensionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PaymentMethodTimeExtensionActivity";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.llpayu)
    LinearLayout llpayu;

    @BindView(R.id.llrazorpay)
    LinearLayout llrazorpay;

    @BindView(R.id.llpaytm)
    LinearLayout llpaytm;

    @BindView(R.id.txt_totalamout)
    TextView txt_totalamout;

    String buildingname, address, sharelink, amount, bookingid, slotdetails, startdate, starttime, enddate, endtime, totalhours, reachtime, distance;

    private String customerid;
    private String bookingidres,additionalbookinghrs,overalltimeduration,extratime;
    private int totalprice,finaltotal,alreadypay,additionalbookingamout,overallamountpaid;
    private String fromactivity;
    private String fromapi;
    private String bookingstatus = "";

    private String outputDateStartDate, outputDateEndDate;

    private String durationDates;

    private ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanArrayList;
    private ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean> vehicledetailslist ;
    private String slotDetails;
    private String Transaction_id;
    String customername,customeremail,customerphone ;

    private String saltkey,merchantkey;
    private boolean isproduction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        toolbar_title.setText(getResources().getString(R.string.paymentmethod));
        imgBack.setOnClickListener(this);

        llpayu.setOnClickListener(this);
        llrazorpay.setOnClickListener(this);
        llpaytm.setOnClickListener(this);



        SessionManager session = new SessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        customerid =  user.get(SessionManager.KEY_ID);
        customername =  user.get(SessionManager.KEY_NAME);
        customerphone =  user.get(SessionManager.KEY_MOBILE);
        customeremail =  user.get(SessionManager.KEY_EMAIL_ID);

        HashMap<String, String> userParking = session.getParkingSlotDetails();
        String area = userParking.get(SessionManager.KEY_PARKING_AREA);
        String floor = userParking.get(SessionManager.KEY_PARKING_FLOOR);
        String slot = userParking.get(SessionManager.KEY_PARKING_SLOT);
         //slotDetails = floor+"/"+area+"/"+slot;

        SessionManager sessionPayu = new SessionManager(this);
        HashMap<String, String> payu = sessionPayu.getPayuDetails();
        saltkey =  payu.get(SessionManager.KEY_PAYU_SALTKEY);
        merchantkey =  payu.get(SessionManager.KEY_PAYU_MERCHANT_KEY);
        String production =  payu.get(SessionManager.KEY_PAYU_PRODUCTION);
        if(production != null){
            if(production.equalsIgnoreCase("true")){
                isproduction = true;
            }else{
                isproduction = false;
            }
        }





        getData();

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
        Intent intent = new Intent(PaymentMethodTimeExtensionActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                onBackPressed();
                break;

                case R.id.llpayu:
                    if (new ConnectionDetector(PaymentMethodTimeExtensionActivity.this).isNetworkAvailable(PaymentMethodTimeExtensionActivity.this)) {
                        gotoPayUPayment();
                    }

                break;

                case R.id.llrazorpay:
                   /* if (new ConnectionDetector(PaymentMethodActivity.this).isNetworkAvailable(PaymentMethodActivity.this)) {
                        parkingBookingCreateResponseCall();
                    }*/
                break;

                case R.id.llpaytm:
                   /* if (new ConnectionDetector(PaymentMethodActivity.this).isNetworkAvailable(PaymentMethodActivity.this)) {
                        parkingBookingCreateResponseCall();
                    }*/
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint({"SetTextI18n", "LongLogTag"})
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
            Log.w(TAG,"fromactivity : "+fromactivity+" fromapi : "+fromapi);
            durationDates = extras.getString("durationDates");

            txt_totalamout.setText("\u20A8"+"."+totalprice);


            if (extratime != null) {


                String fromactivity = extras.getString("fromactivity");
                if (fromactivity != null && fromactivity.equalsIgnoreCase("BookingHistoryListAdapter")) {
                    vehicledetailslist = (ArrayList<ParkingBookingGetListResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicledetailslist");
                    Log.w(TAG, "vehicledetailslist:" + new Gson().toJson(vehicledetailslist));

                    if (vehicledetailslist != null && vehicledetailslist.size() > 0) {
                        //getVehicleDataBookinghistory();
                    }
                } else {
                    vehicleDetailsBeanArrayList = (ArrayList<ParkingBookingCreateResponse.DataBean.VehicleDetailsBean>) getIntent().getSerializableExtra("vehicleDetailsBeanArrayList");
                    Log.w(TAG, "vehicleDetailsBeanArrayList:" + new Gson().toJson(vehicleDetailsBeanArrayList));

                /*if (vehicleDetailsBeanArrayList != null && vehicleDetailsBeanArrayList.size() >0) {
                    getVehicleData();
                }*/
                }


                Log.w(TAG, "buildingname :" + buildingname + "address : " + address + "sharelink :" + sharelink + "amount :" + amount + "bookingid : " + bookingid + "slotdetails : " + slotdetails + "distance : " + distance + "reachtime " + reachtime);


            }

        }


    }


    @SuppressLint("LongLogTag")
    private void additionHrsResponseCall() {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AdditionHrsResponse> call = apiInterface.additionHrsResponseCall(RestUtils.getContentType(), additionHrsRequest());
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<AdditionHrsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<AdditionHrsResponse> call, @NotNull Response<AdditionHrsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "additionHrsResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        Log.w(TAG,"additionHrsResponseCall fromapi : "+fromapi);

                        if(fromapi != null && fromapi.equalsIgnoreCase("checkoutentry")){
                            Intent intent = new Intent(PaymentMethodTimeExtensionActivity.this, CheckedOutActivity.class);
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
                            intent.putExtra("fromactivity",fromactivity);

                            startActivity(intent);
                        }else{
                            callDirections("2");

                            String bookingStatus = response.body().getData().getBooking_status();
                            if(bookingStatus.equalsIgnoreCase("Check-in"))  {
                                Intent intent = new Intent(PaymentMethodTimeExtensionActivity.this, CheckedInActivity.class);
                                intent.putExtra("buildingname",response.body().getData().getParking_shop_name());
                                intent.putExtra("address",response.body().getData().getParking_shop_address());
                                intent.putExtra("sharelink",response.body().getData().getParking_shop_address_link());
                                intent.putExtra("amount",response.body().getData().getTotal_amount()+"");
                                intent.putExtra("bookingid",response.body().getData().getBooking_id());
                                intent.putExtra("slotdetails",response.body().getData().getSlot_details());
                                intent.putExtra("startdate",response.body().getData().getBooking_start_date());
                                intent.putExtra("starttime",response.body().getData().getBooking_start_time());
                                intent.putExtra("enddate",response.body().getData().getBooking_end_date());
                                intent.putExtra("endtime",response.body().getData().getBooking_end_time());
                                intent.putExtra("totalhours",response.body().getData().getTotal_hrs());
                                intent.putExtra("vehicleDetailsBeanAddhrsArrayList",response.body().getData().getVehicle_details());
                                intent.putExtra("reachtime",response.body().getData().getDistance());
                                intent.putExtra("distance",response.body().getData().getKms());
                                intent.putExtra("id",response.body().getData().get_id());
                                intent.putExtra("parkingdetailsid",response.body().getData().getParkingdetails_id());
                                intent.putExtra("bookingstatus",response.body().getData().getBooking_status());
                                intent.putExtra("fromactivity",TAG);
                                startActivity(intent);

                            }
                            if(bookingStatus.equalsIgnoreCase("Upcoming")){
                                Intent intent = new Intent(PaymentMethodTimeExtensionActivity.this, ParkingConfirmActivity.class);
                                intent.putExtra("buildingname",response.body().getData().getParking_shop_name());
                                intent.putExtra("address",response.body().getData().getParking_shop_address());
                                intent.putExtra("sharelink",response.body().getData().getParking_shop_address_link());
                                intent.putExtra("amount",response.body().getData().getTotal_amount()+"");
                                intent.putExtra("bookingid",response.body().getData().getBooking_id());
                                intent.putExtra("slotdetails",response.body().getData().getSlot_details());
                                intent.putExtra("startdate",response.body().getData().getBooking_start_date());
                                intent.putExtra("starttime",response.body().getData().getBooking_start_time());
                                intent.putExtra("enddate",response.body().getData().getBooking_end_date());
                                intent.putExtra("endtime",response.body().getData().getBooking_end_time());
                                intent.putExtra("totalhours",response.body().getData().getTotal_hrs());
                                intent.putExtra("vehicleDetailsBeanAddhrsArrayList",response.body().getData().getVehicle_details());
                                intent.putExtra("reachtime",response.body().getData().getDistance());
                                intent.putExtra("distance",response.body().getData().getKms());
                                intent.putExtra("id",response.body().getData().get_id());
                                intent.putExtra("parkingdetailsid",response.body().getData().getParkingdetails_id());
                                intent.putExtra("bookingstatus",response.body().getData().getBooking_status());
                                intent.putExtra("fromactivity",TAG);
                                startActivity(intent);

                            }

                        }

                    }




                } else {
                    if(response.body()!= null)
                    Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                }


            }


            @Override
            public void onFailure(@NotNull Call<AdditionHrsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "additionHrsResponseCallflr" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private AdditionHrsRequest additionHrsRequest() {
        /*
         * booking_id : 5f52183b850fbc582200ebfa
         * duration_date : 17 Sep ,05:30 PM to 17 Sep ,06:30 PM(0 days 1 hours )
         * start_date : 2020-09-17
         * start_time : 05:45 PM
         * end_date : 2020-09-17
         * end_time : 06:45 PM
         * additional_booking_hrs : 1 hour(s) and 0 minute(s).
         * additonal_booking_amount : 100
         * Overall_time_duraion : 2 hour(s) and 0 minute(s).
         * Overall_amount_paid : 200
         * extra_time : 1 hour(s) and 0 minute(s).
         * Booking_status : Check-in
         */
        AdditionHrsRequest additionHrsRequest = new AdditionHrsRequest();
        additionHrsRequest.setBooking_id(bookingidres);
        additionHrsRequest.setDuration_date(durationDates);
        additionHrsRequest.setStart_date(startdate);
        additionHrsRequest.setStart_time(starttime);
        additionHrsRequest.setEnd_time(endtime);
        additionHrsRequest.setEnd_date(enddate);
        additionHrsRequest.setAdditional_booking_hrs(String.valueOf(additionalbookinghrs));
        additionHrsRequest.setAdditonal_booking_amount(additionalbookingamout);
        additionHrsRequest.setOverall_time_duraion(String.valueOf(overalltimeduration));
        additionHrsRequest.setOverall_amount_paid(overallamountpaid);
        additionHrsRequest.setExtra_time(String.valueOf(extratime));
        additionHrsRequest.setBooking_status(bookingstatus);
        Log.w(TAG, "AdditionHrsRequest" + new Gson().toJson(additionHrsRequest));
        return additionHrsRequest;
    }

    @SuppressLint("LongLogTag")
    private void gotoPayUPayment() {
        Log.w(TAG,"gotoPayUPayment");
        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
        Transaction_id = customerid+System.currentTimeMillis();
        builder.setAmount(String.valueOf(additionalbookingamout))
                .setIsProduction(isproduction)
                .setProductInfo("Book Parking")
                .setKey(merchantkey)
                .setPhone(customerphone)
                .setTransactionId(Transaction_id)
                .setFirstName(customername)
                .setEmail(customeremail)
                .setSurl("https://payuresponse.firebaseapp.com/success")
                .setFurl("https://payuresponse.firebaseapp.com/failure")
                .setUserCredential(merchantkey+":"+customeremail);
        PayUPaymentParams payUPaymentParams = builder.build();
        Log.w(TAG,"payUPaymentParams : "+new Gson().toJson(payUPaymentParams));




        PayUCheckoutPro.open(this, payUPaymentParams, new PayUCheckoutProListener() {
            @Override
            public void onPaymentSuccess(@NotNull Object response) {
                Log.w(TAG,"onPaymentSuccess : "+new Gson().toJson(response));

                if (new ConnectionDetector(PaymentMethodTimeExtensionActivity.this).isNetworkAvailable(PaymentMethodTimeExtensionActivity.this)) {
                    additionHrsResponseCall();
                }
                //Cast response object to HashMap
                // HashMap<String,Object> result = (HashMap<String, Object>) response
                // String payuResponse = result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                //String merchantResponse = result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE));
            }

            @Override
            public void onPaymentFailure(@NotNull Object response) {
                Log.w(TAG,"onPaymentFailure : "+new Gson().toJson(response));
                //Cast response object to HashMap
                       /* HashMap<String,Object> result = (HashMap<String, Object>) response
                        String payuResponse = result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                        String merchantResponse = result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE));*/
            }

            @Override
            public void onPaymentCancel(boolean isTxnInitiated) {
            }

            @Override
            public void onError(@NotNull ErrorResponse errorResponse) {

                String errorMessage = errorResponse.getErrorMessage();
                Log.w(TAG,"ErrorResponse  : "+errorMessage);

            }

            @Override
            public void generateHash(@NotNull HashMap<String, String> valueMap, @NotNull PayUHashGenerationListener hashGenerationListener) {
                String hashName = valueMap.get("hashName");
                //strHashName = hashName;
                String hashData = valueMap.get("hashString");

                Log.w(TAG," hashName : "+hashName+" hashData : "+hashData);


                if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                    String generatedSHA512 = null;
                    String toReturn = null;
                    try {
                        toReturn = hashData+saltkey;
                        MessageDigest digest = MessageDigest.getInstance("SHA-512");
                        digest.reset();
                        digest.update(toReturn.getBytes("utf8"));
                        generatedSHA512 = String.format("%0128x", new BigInteger(1, digest.digest()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Generate Hash from your backend here
                    // String hash = HashGenerationUtils.INSTANCE.generateHashFromSDK(hashData, testSalt);
                    Log.w(TAG,"  hashName : "+hashName+" generatedSHA512 : "+generatedSHA512);

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put(hashName, generatedSHA512);
                    Log.w(TAG,"datamap  "+dataMap);
                    hashGenerationListener.onHashGenerated(dataMap);
                }



            }
        });













    }
}