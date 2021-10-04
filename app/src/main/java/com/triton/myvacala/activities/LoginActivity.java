package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;

import com.triton.myvacala.appUtils.ActivityCreate;
import com.triton.myvacala.appUtils.ApplicationData;
import com.triton.myvacala.appUtils.KeyboardUtils;
import com.triton.myvacala.appUtils.NumericKeyBoardTransformationMethod;
import com.triton.myvacala.receiver.OTPSmsListener;
import com.triton.myvacala.receiver.SmsBroadcastListener;
import com.triton.myvacala.requestpojo.RegisterMobileRequest;
import com.triton.myvacala.responsepojo.RegisterMobileResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.GenericTextWatcher;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.edt_mobile_number)
    EditText edt_mobile_number;



    @BindView(R.id.iv_login)
    ImageView iv_login;


    private String TAG = "LoginActivity";
    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;

    String mobileNumber = "",responseotp ="";
    Dialog dialog;

    CountDownTimer timer;
    private ApplicationData applicationData;

    @BindView(R.id.txt_terms)
    TextView txt_terms;

    SessionManager session;

    String name = "", emailID = "",  mobile = "", Userstatus = "", id = "",customerlocation ="";
    String Vehicle_Type_Status = " ",Current_Location_Status = "";


    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    String autoOTP;
    private boolean isUser_Status;

    @BindView(R.id.cb_terms)
    CheckBox cb_terms;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String[] permissionString = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECEIVE_SMS,
            "check"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.w(TAG,"onCreate--->");
        ButterKnife.bind(this);
        applicationData = (ApplicationData) getApplication();
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        
        avi_indicator.setVisibility(View.GONE);

        txt_terms.setOnClickListener(this);


       /* if (checkAndRequestPermissions()){

        }*/



        
        iv_login.setOnClickListener(this);

        // edt_mobile_number.requestFocus();
        edt_mobile_number.setTransformationMethod(new NumericKeyBoardTransformationMethod());

//
//        SmsBroadcastListener.bindListener(new OTPSmsListener() {
//            @Override
//            public void onMessageReceived(String otpText) {
//                avi_indicator.smoothToHide();
//                Log.w(TAG,"extractedOTP--->"+otpText);
//                autoOTP = otpText;
//
//                if(autoOTP != null) {
//                    char[] cArray = autoOTP.toCharArray();
//                    otp_textbox_one.setText(String.valueOf(cArray[0]));
//                    otp_textbox_two.setText(String.valueOf(cArray[1]));
//                    otp_textbox_three.setText(String.valueOf(cArray[2]));
//                    otp_textbox_four.setText(String.valueOf(cArray[3]));
//                }
//               //alertDialogOTP(otpText);
//
//            }
//        });



        //end of create
    }

    private void insertmappermission() {

        int haslocationpermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            haslocationpermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) | checkSelfPermission(Manifest.permission.RECEIVE_SMS);

            if (haslocationpermission != PackageManager.PERMISSION_GRANTED) {

                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) | !shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {

                    requestPermissions(permissionString,
                            REQUEST_CODE_ASK_PERMISSIONS);

                    return;
                }
                requestPermissions(permissionString,
                        REQUEST_CODE_ASK_PERMISSIONS);
            }else{
                if (new ConnectionDetector(LoginActivity.this).isNetworkAvailable(LoginActivity.this)) {
                    verifyOtp();
                }
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               /* startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();*/
                // Permission Granted
                if (new ConnectionDetector(LoginActivity.this).isNetworkAvailable(LoginActivity.this)) {
                    verifyOtp();
                }



            } else {
                // Permission Denied
              /*  Toast.makeText(Permission_Activity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                        .show();*/
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECEIVE_SMS}, 5);
        }
        return true;
    }



    private void verifyOtp() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //RestApiInterface ApiService = RetrofitClient.getApiService();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<RegisterMobileResponse> call = apiInterface.registerMobileResponseCall(RestUtils.getContentType(), registerMobileRequest());
        Log.w(TAG,"registerMobileResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<RegisterMobileResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterMobileResponse> call,@NonNull  Response<RegisterMobileResponse> response) {
              //  avi_indicator.smoothToHide();
                Log.w(TAG,"registerMobileResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    ActivityCreate.getCurrentDateandTime();
                    ActivityCreate.activityCreateResponseCall("Testing","011","idd@gmail.com","Tryed to Login","Get otp button pressed",ActivityCreate.currentdateandtime);
                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        responseotp = String.valueOf(response.body().getOTP());
                        name = response.body().getData().getName();
                        emailID = response.body().getData().getEmail();
                        mobile = String.valueOf(response.body().getData().getPhone());
                        id = response.body().getData().get_id();
                        Userstatus = response.body().getUserstatus();
                        Log.w(TAG,"Userstatus--->"+Userstatus);

                        if (Userstatus.equalsIgnoreCase("New User")) {}
                        else{
                            if(response.body().getData() != null){
                                isUser_Status = response.body().getData().isUser_Status();
                                Vehicle_Type_Status = String.valueOf(response.body().getData().isVehicle_Type_Status());
                                Current_Location_Status  = String.valueOf(response.body().getData().isCurrent_Location_Status());
                                Log.w(TAG,"isUser_Status--->"+isUser_Status+" Vehicle_Type_Status : "+Vehicle_Type_Status+" Current_Location_Status : "+Current_Location_Status);

                            }
                        }




                        Log.w(TAG,"OTP--->"+response.body().getOTP());
                       alertDialogOTP();

                        /* Intent i = new Intent(LoginActivity.this, VerifyOtpActivity.class);
                        i.putExtra("mobilenumber",edt_mobile_number.getText().toString());
                        i.putExtra("otp",String.valueOf(response.body().getData().getOTP()));
                        startActivity(i);*/
                      //  finish();
                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<RegisterMobileResponse> call,@NonNull Throwable t) {
                //avi_indicator.smoothToHide();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private RegisterMobileRequest registerMobileRequest() {
        RegisterMobileRequest registerMobileRequest = new RegisterMobileRequest();
        registerMobileRequest.setPhone(Long.parseLong(edt_mobile_number.getText().toString()));
        Log.w(TAG,"OTP registerMobileRequest"+ new Gson().toJson(registerMobileRequest));
        return registerMobileRequest;
    }

    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    private boolean validateInputs() {
        mobileNumber = edt_mobile_number.getText().toString();
        if (mobileNumber.isEmpty()) {
            edt_mobile_number.setError(getResources().getString(R.string.mobile_number_error));
            edt_mobile_number.requestFocus();
            return false;
        }
        if (mobileNumber.length() < 10) {
            edt_mobile_number.setError("Please enter valid mobile number");
            edt_mobile_number.requestFocus();

             //Toasty.error(getApplicationContext(), getResources().getString(R.string.mobile_length_error), Toast.LENGTH_SHORT, true).show();
             return false;
        }

        if(!cb_terms.isChecked()){

            Toasty.error(getApplicationContext(), getResources().getString(R.string.terms_error), Toast.LENGTH_SHORT, true).show();

            return false;

        }

        return true;
    }

    private void alertDialogOTP() {

        try {

            dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.alert_otp_layout);
            dialog.setCancelable(false);

            LinearLayout timer_layout = dialog.findViewById(R.id.timer_layout);
            TextView txt_timer_count = dialog.findViewById(R.id.txt_timer_count);
            TextView txt_Resend = dialog.findViewById(R.id.txt_Resend);
            ImageView iv_close = dialog.findViewById(R.id.iv_close);

            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            otp_textbox_one = dialog.findViewById(R.id.otp_edit_box1);
            otp_textbox_two = dialog.findViewById(R.id.otp_edit_box2);
            otp_textbox_three = dialog.findViewById(R.id.otp_edit_box3);
            otp_textbox_four = dialog.findViewById(R.id.otp_edit_box4);
            EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four};

            otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));
            otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
            otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
            otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));



            if(autoOTP != null) {
                char[] cArray = autoOTP.toCharArray();
                otp_textbox_one.setText(String.valueOf(cArray[0]));
                otp_textbox_two.setText(String.valueOf(cArray[1]));
                otp_textbox_three.setText(String.valueOf(cArray[2]));
                otp_textbox_four.setText(String.valueOf(cArray[3]));
            }

            timer = new CountDownTimer(applicationData.getTimer_milliseconds(), 1000) {
                @SuppressLint({"DefaultLocale", "SetTextI18n"})
                @Override
                public void onTick(long millisUntilFinished) {
                    applicationData.setTimer_milliseconds(millisUntilFinished);
                    txt_timer_count.setText(getResources().getString(R.string.resendotp)+" " + String.format("%02d : %02d ",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                }

                @Override
                public void onFinish() {
                    timer_layout.setVisibility(View.GONE);
                    txt_Resend.setVisibility(View.VISIBLE);
                    timer.cancel();
                }
            };
            timer.start();

            txt_Resend.setOnClickListener(v -> {
                if (new ConnectionDetector(LoginActivity.this).isNetworkAvailable(LoginActivity.this)) {
                    verifyOtp();
                }
            });






            Button btn_submit = dialog.findViewById(R.id.btn_submit);

            btn_submit.setOnClickListener(v -> {
                String otp1  = otp_textbox_one.getText().toString().trim();
                String otp2  = otp_textbox_two.getText().toString().trim();
                String otp3  = otp_textbox_three.getText().toString().trim();
                String otp4  = otp_textbox_four.getText().toString().trim();
                String verify_otp = otp1+otp2+otp3+otp4;
                Log.w(TAG,"OTP Entered--->"+verify_otp);
                 if(otp1.isEmpty() && otp2.isEmpty() && otp3.isEmpty() && otp4.isEmpty() ){
                     Toasty.error(getApplicationContext(),getResources().getString(R.string.verification_code_text), Toast.LENGTH_SHORT, true).show();

                 }
//                 else if(!responseotp.equalsIgnoreCase(verify_otp)){
//                     showErrorLoading(getResources().getString(R.string.invalid_otp)+" "+getResources().getString(R.string.re_enter_otp));
//
//                 }
                 else {
                     dialog.dismiss();
                     Toasty.success(getApplicationContext(), getResources().getString(R.string.sms_verify_text), Toast.LENGTH_SHORT, true).show();

                     session.setIsLogin(true);
                     session.createLoginSession(name, emailID, mobile, Userstatus, id, customerlocation, "");

                     session.checkUserStatus(Userstatus, Vehicle_Type_Status, Current_Location_Status);

                     Log.w(TAG, "Userstatus--->" + Userstatus);


                     if (Userstatus.equalsIgnoreCase("New User")) {
                         Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                         i.putExtra("mobileno", edt_mobile_number.getText().toString());
                         startActivity(i);
                     } else if (Userstatus.equalsIgnoreCase("Exists")) {
                         Log.w(TAG, "isUser_Status--->" + isUser_Status);
                         if (isUser_Status) {
                             Log.w(TAG, "Current_Location_Status--->" + Current_Location_Status);
                             Log.w(TAG, "Vehicle_Type_Status--->" + Vehicle_Type_Status);
                             assert Current_Location_Status != null;
                             if (Current_Location_Status.equalsIgnoreCase("false")) {
                                 Log.w(TAG, "Current_Location_Status if--->" + Current_Location_Status);

                                 Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                                 startActivity(i);
                                 finish();
                                 overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                 return;
                             } else if (Vehicle_Type_Status.equalsIgnoreCase("false")) {
                                 Log.w(TAG, "Vehicle_Type_Status if--->" + Vehicle_Type_Status);

                                 Intent i = new Intent(LoginActivity.this, PopularVehicleListActivity.class);
                                 startActivity(i);
                                 finish();
                                 overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                 return;
                             } else {
                                 Log.w(TAG, "Else--->" + Vehicle_Type_Status + " " + Current_Location_Status);

                                 Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                 startActivity(i);
                                 finish();
                                 overridePendingTransition(R.anim.new_right, R.anim.new_left);
                             }
                         } else {
                             showErrorLoading("Admin blocks your account please contact me on myvacala team.");
                         }
                     }

                 }

            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_login) {
            if(validateInputs()){
                insertmappermission();

           }
            KeyboardUtils.hideKeyboard(LoginActivity.this);
        }

        else if (v.getId() == R.id.txt_terms) {


            String url = "https://myvacala.com/api/uploads/a11c0db0-725a-4d7c-9805-738c30395d08.pdf";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            startActivity(browserIntent);


        }
    }

    @Override
    protected void onDestroy() {
        SmsBroadcastListener.unbindListener();
        super.onDestroy();
        if ( dialog !=null && dialog.isShowing() ){
            dialog.cancel();

        }
    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(LoginActivity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /*@Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                Log.w(TAG,"otpmessage:"+message);
                // message is the fetching OTP
            }
        }
    };*/


}