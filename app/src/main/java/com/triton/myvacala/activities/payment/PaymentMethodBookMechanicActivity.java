package com.triton.myvacala.activities.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;

import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.BookingSummaryActivity;
import com.triton.myvacala.activities.DashboardActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.BookingCreateRequest;
import com.triton.myvacala.requestpojo.GetPayuRequest;
import com.triton.myvacala.responsepojo.BookingCreateResponse;
import com.triton.myvacala.responsepojo.GetPayuResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodBookMechanicActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PaymentMethodBookMechanicActivity";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

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


    private String parkingid,vehicleid,resstartdate,resenddate,resstarttime,resendtime,totalhrs;
    private int totalamount;

    private double reachtime, parkingdistance;
    private String strdays;
    String slotDetails;
    private String couponcode = "",couponcodeamount = "",originalamount = "";
    private int days,hours,min;

    ArrayList<BookingCreateRequest.CardDetailsBean> card_details = new ArrayList<>();

    String vehicletypeid,serviceid,customerid,customername,customeremail,customerphone ;
    String city ,street;
    String vehicleImage,vehicleName, vehicleModelName,fuelType;
    String vehicletypename,servicename,masterservicename;


    String VehicleType_Id,Vehicle_Id,Vehicle_Type,Lubricant_type,Lubricant_Type_Background_Color,Year_Of_Mfg;

    String active_tag = "1";
    private String address;
    String BookingDate ="",BookingTime = "";
    String Arrival_Mode = "PickUp";
    String Coupon_Code ="",Coupon_Code_Percentage = "",Coupon_Code_Amount = "";

    int originalamout = 0,discountamout = 0;

    int youPay =0;

    private String _id;
    private String Customer_id;
    private String Vehicle_Image;
    private String Vehicletype_id;
    private String Vehicletype_Name;
    private String Vehicle_Brand_id;
    private String Vehicle_Brand_Name;
    private String Vehicle_Name_id;
    private String Vehicle_Name;
    private String Year_of_Manufacture;
    private String Vehicle_No;
    private String Fuel_Type_id;
    private String Fuel_Type_Name;
    private String Fuel_Type_Background_Color;
    private String Vehicle_Model_id;
    private String Vehicle_Model_Name;
    private String Status;
    private String updatedAt;
    private String createdAt;
    private int __v;
    private String from;


    private String email = "snooze@payu.in";
    private String phone = "9999999999";
    private String merchantName = "RH Group";
    private String surl = "https://payuresponse.firebaseapp.com/success";
    private String furl = "https://payuresponse.firebaseapp.com/failure";
    private String amount = "1.0";



    PayUHashGenerationListener hashGenerationListeners;
    private String data;
    private String strHashName;
    private boolean isgetPayuResponseCall = true;
    private String Transaction_id;

    private String saltkey,merchantkey;
    private boolean isproduction;
    private String userissues;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG,"onCreate");
        setContentView(R.layout.activity_payment_method_book_mechanic);
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


        HashMap<String, String> userParking = session.getParkingSlotDetails();
        String area = userParking.get(SessionManager.KEY_PARKING_AREA);
        String floor = userParking.get(SessionManager.KEY_PARKING_FLOOR);
        String slot = userParking.get(SessionManager.KEY_PARKING_SLOT);
         slotDetails = floor+"/"+area+"/"+slot;

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

        if(from != null && from.equalsIgnoreCase("CartFragment")){
            bottom_navigation_view.setSelectedItemId(R.id.cart);
            bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        }



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                //active = homeFragment;
                active_tag = "1";
                callDirections(active_tag);
                break;


            case R.id.search:
                //active = searchFragment;
                active_tag = "2";
                callDirections(active_tag);
                break;


            case R.id.myvehicle:
                //active = myVehicleFragment;
                active_tag = "3";
                callDirections(active_tag);
                break;
            case R.id.cart:
                //active = cartFragment;
                active_tag = "4";
                callDirections(active_tag);
                break;
            case R.id.account:
                //active = accountFragment;
                active_tag = "5";
                callDirections(active_tag);
                break;

        }




        return true;
    };
    public void callDirections(String tag){
        Intent intent = new Intent(PaymentMethodBookMechanicActivity.this, DashboardActivity.class);
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
                    if (new ConnectionDetector(PaymentMethodBookMechanicActivity.this).isNetworkAvailable(PaymentMethodBookMechanicActivity.this)) {
                        gotoPayUPayment();

                       //bookingCreateResponseCall();
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

    @SuppressLint("LongLogTag")
    private void gotoPayUPayment() {
        Log.w(TAG,"gotoPayUPayment");
        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
        Transaction_id = customerid+System.currentTimeMillis();
        builder.setAmount(String.valueOf(youPay))
                .setIsProduction(isproduction)
                .setProductInfo("Book Mechanic")
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

                if (new ConnectionDetector(PaymentMethodBookMechanicActivity.this).isNetworkAvailable(PaymentMethodBookMechanicActivity.this)) {
                    bookingCreateResponseCall();
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




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint({"SetTextI18n", "LongLogTag"})
    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customername = extras.getString("customername");
            customerid = extras.getString("customerid");
            customerphone = extras.getString("customerphone");
            address = extras.getString("address");
            customeremail = extras.getString("customeremail");
            Vehicle_Id = extras.getString("Vehicle_Id");
            Vehicle_Type = extras.getString("Vehicle_Type");
            Vehicle_No = extras.getString("Vehicle_No");
            Vehicle_Image = extras.getString("Vehicle_Image");
            Vehicle_Name = extras.getString("Vehicle_Name");
            Lubricant_type = extras.getString("Lubricant_type");
            BookingDate = extras.getString("BookingDate");
            Arrival_Mode = extras.getString("Arrival_Mode");
            BookingTime = extras.getString("BookingTime");
            Coupon_Code = extras.getString("Coupon_Code");
            Coupon_Code_Percentage = extras.getString("Coupon_Code_Percentage");
            Coupon_Code_Amount = extras.getString("Coupon_Code_Amount");
            youPay = extras.getInt("youPay");
            originalamout = extras.getInt("originalamout");
            Year_Of_Mfg = extras.getString("Year_Of_Mfg");
            userissues = extras.getString("userissues");
            Lubricant_Type_Background_Color = extras.getString("Lubricant_Type_Background_Color");
             from = extras.getString("from");


            txt_totalamout.setText("\u20A8"+"."+youPay);

            card_details = (ArrayList<BookingCreateRequest.CardDetailsBean>) getIntent().getSerializableExtra("card_details");


            Log.w(TAG,"Vehicle_Name : "+Vehicle_Name+" "+"address : "+address);





        }

    }

    @SuppressLint("LongLogTag")
    public void bookingCreateResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<BookingCreateResponse> call = apiInterface.bookingCreateResponseCall(RestUtils.getContentType(),bookingCreateRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<BookingCreateResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookingCreateResponse> call, @NotNull Response<BookingCreateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"BookingCreateResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if(200 == response.body().getCode()){
                    Intent intent = new Intent(PaymentMethodBookMechanicActivity.this, BookingSummaryActivity.class);
                    intent.putExtra("amountpaid",String.valueOf(response.body().getData().getAmount_Paid()));
                    intent.putExtra("bookingid",response.body().getData().getBooking_id());
                    intent.putExtra("registrationno",response.body().getData().getRegistration_No());
                    intent.putExtra("servicedate",response.body().getData().getService_Date());
                    intent.putExtra("servicetime",response.body().getData().getService_Time());
                    intent.putExtra("service",response.body().getData().getServices());
                    intent.putExtra("subservice",response.body().getData().getSubserivces());
                    intent.putExtra("vehicledetails",response.body().getData().getVehicle_Details());
                    intent.putExtra("discountamount",response.body().getData().getCoupon_Code_Amount());
                    intent.putExtra("userissues",response.body().getData().getUser_issues());
                    startActivity(intent);
                }








            }


            @Override
            public void onFailure(@NotNull Call<BookingCreateResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"BookingCreateResponseFlr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private BookingCreateRequest bookingCreateRequest() {
        /*
         * Customer_Name : Dinesh
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * Customer_Phone :  6383791451
         * Customer_Address : Chennai
         *  Customer_Email : iddineshkumar@gmail.com
         * Services : AC SERVICES
         * Subserivces : Monsoon shield
         * Vehicle_Id : 5f1ee402e649907a21e1a84f
         * Vehicle_Type : Four Wheeler
         * Vehicle_No : 5218
         * Vehicle_Image : http://3.101.31.129:3000/api/uploads/e1e00e57-d3e7-42dc-99d9-6ccd7b780617.jpg
         * Vehicle_Name : AUDI A6
         * Lubricant_type : Petrol
         * Booking_Date : 2020-07-28
         * Arrival_Mode : Pickup
         * Pickup_Date : 2020-07-30
         * Pickup_Time : 2 days
         * Coupon_Code :
         * Coupon_Code_Percentage :
         * Coupon_Code_Amount :
         * Total_Amount : 1230
         * Order_Value : 1000
         * Booking_Time : 2020-07-21
         * Year_Of_Mfg : 2019
         * Lubricant_type_color
         * User_issues
         */

        BookingCreateRequest bookingCreateRequest = new BookingCreateRequest();
        bookingCreateRequest.setCustomer_Name(customername);
        bookingCreateRequest.setCustomer_id(customerid);
        bookingCreateRequest.setCustomer_Phone(customerphone);
        bookingCreateRequest.setCustomer_Address(address);
        bookingCreateRequest.setCustomer_Email(customeremail);
        bookingCreateRequest.setServices("");
        bookingCreateRequest.setSubserivces("");
        bookingCreateRequest.setVehicle_Id(Vehicle_Id);
        bookingCreateRequest.setVehicle_Type(Vehicle_Type);
        bookingCreateRequest.setVehicle_No(Vehicle_No);
        bookingCreateRequest.setVehicle_Image(Vehicle_Image);
        bookingCreateRequest.setVehicle_Name(Vehicle_Name);
        bookingCreateRequest.setLubricant_type(Lubricant_type);
        bookingCreateRequest.setBooking_Date(BookingDate);
        bookingCreateRequest.setArrival_Mode(Arrival_Mode);
        bookingCreateRequest.setPickup_Date(BookingDate);
        bookingCreateRequest.setPickup_Time(BookingTime);
        bookingCreateRequest.setCoupon_Code(Coupon_Code);
        bookingCreateRequest.setCoupon_Code_Percentage(Coupon_Code_Percentage);
        bookingCreateRequest.setCoupon_Code_Amount(Coupon_Code_Amount);
        bookingCreateRequest.setTotal_Amount(youPay);
        bookingCreateRequest.setOrder_Value(originalamout);
        bookingCreateRequest.setBooking_Time(BookingTime);
        bookingCreateRequest.setYear_Of_Mfg(Year_Of_Mfg);
        bookingCreateRequest.setLubricant_type_color(Lubricant_Type_Background_Color);
        bookingCreateRequest.setTransaction_id(Transaction_id);
        bookingCreateRequest.setCard_details(card_details);
        bookingCreateRequest.setUser_issues(userissues);
        Log.w(TAG,"BookingCreateRequest"+ new Gson().toJson(bookingCreateRequest));
        return bookingCreateRequest;
    }





}