package com.triton.myvacala.activities.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.DashboardActivity;
import com.triton.myvacala.activities.OrderHistoryActivity;

import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.FinalpayableUpdateRequest;

import com.triton.myvacala.requestpojo.FinalpayableUpdateResponse;

import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.MessageDigest;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodBookMechanicFinalPayableActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PaymentMethodBookMechanicFinalPayableActivity";

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





    String active_tag = "1";

    private String from;


    private String email = "snooze@payu.in";
    private String phone = "9999999999";
    private String merchantName = "RH Group";
    private String surl = "https://payuresponse.firebaseapp.com/success";
    private String furl = "https://payuresponse.firebaseapp.com/failure";
    private String amount = "1.0";



    PayUHashGenerationListener hashGenerationListeners;
    private String data;

    private String Transaction_id;
    private String finalbillpayamount;
    private String id;
    private String customerid;
    private String customerphone,customername,customeremail;

    private String saltkey,merchantkey;
    private boolean isproduction;


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
        customername =  user.get(SessionManager.KEY_NAME);
        customerphone =  user.get(SessionManager.KEY_MOBILE);
        customeremail =  user.get(SessionManager.KEY_EMAIL_ID);

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
        Intent intent = new Intent(PaymentMethodBookMechanicFinalPayableActivity.this, DashboardActivity.class);
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
                    if (new ConnectionDetector(PaymentMethodBookMechanicFinalPayableActivity.this).isNetworkAvailable(PaymentMethodBookMechanicFinalPayableActivity.this)) {
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
        builder.setAmount(finalbillpayamount)
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

                if (new ConnectionDetector(PaymentMethodBookMechanicFinalPayableActivity.this).isNetworkAvailable(PaymentMethodBookMechanicFinalPayableActivity.this)) {
                    finalpayableUpdateResponseCall();
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

             from = extras.getString("from");
            finalbillpayamount = extras.getString("finalbillpayamount");
            id = extras.getString("id");
            txt_totalamout.setText("\u20A8"+"."+finalbillpayamount);
        }

    }

    @SuppressLint("LongLogTag")
    public void finalpayableUpdateResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FinalpayableUpdateResponse> call = apiInterface.finalpayableUpdateResponseCall(RestUtils.getContentType(),finalpayableUpdateRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FinalpayableUpdateResponse>() {
            @Override
            public void onResponse(@NotNull Call<FinalpayableUpdateResponse> call, @NotNull Response<FinalpayableUpdateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FinalpayableUpdateResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if(200 == response.body().getCode()){
                    Intent i = new Intent(PaymentMethodBookMechanicFinalPayableActivity.this, OrderHistoryActivity.class);
                    /*i.putExtra("id",response.body().getData().get_id());
                    i.putExtra("finalbillpay",response.body().getData().get(0).getFinal_bill_payed());
                    i.putExtra("bookingstatus",response.body().getData().get(0).getBooking_Status());
                    i.putExtra("customerinvoicelist",response.body().getData().get(0).getCustomer_Invoice());
                    i.putExtra("vehicletype",response.body().getData().get(0).getVehicle_Type());*/
                    startActivity(i);
                }








            }


            @Override
            public void onFailure(@NotNull Call<FinalpayableUpdateResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FinalpayableUpdateResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private FinalpayableUpdateRequest finalpayableUpdateRequest() {
        /*
         * _id : 5f915f4d9c1be21c2cbcbd72
         * Final_bill_payed : 200
         */


        FinalpayableUpdateRequest finalpayableUpdateRequest = new FinalpayableUpdateRequest();
        finalpayableUpdateRequest.set_id(id);
        finalpayableUpdateRequest.setFinal_bill_payed(Integer.parseInt(finalbillpayamount));

        Log.w(TAG,"finalpayableUpdateRequest"+ new Gson().toJson(finalpayableUpdateRequest));
        return finalpayableUpdateRequest;
    }






}