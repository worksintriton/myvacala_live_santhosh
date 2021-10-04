package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;

import com.triton.myvacala.appUtils.ActivityCreate;
import com.triton.myvacala.appUtils.ApplicationData;
import com.triton.myvacala.receiver.OTPSmsListener;
import com.triton.myvacala.receiver.SmsBroadcastListener;
import com.triton.myvacala.requestpojo.ProfileUpdateRequest;
import com.triton.myvacala.requestpojo.RegisterMobileRequest;
import com.triton.myvacala.responsepojo.NewUserResponse;
import com.triton.myvacala.responsepojo.RegisterMobileResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.GenericTextWatcher;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Activity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.rldob)
    RelativeLayout rldob;

    @BindView(R.id.tvdob)
    TextView tvdob;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;


    @BindView(R.id.edt_name)
    EditText edt_name;

    @BindView(R.id.edt_emailid)
    EditText edt_emailid;

    @BindView(R.id.edt_mobilenumber)
    EditText edt_mobilenumber;

    @BindView(R.id.btn_update_profile)
    Button btn_update_profile;


    String active_tag = "5";

    String TAG = "Profile_Activity";

    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;




    private static final int DATE_PICKER_ID = 0 ;

    private int year, month, day;

    String SelectedDob = "";

    SessionManager session;
    private String customerphone,customerid,dob;
    private String autoOTP;
    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    CountDownTimer timer;
    private ApplicationData applicationData;

    private Dialog dialog;
    private String responseotp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        applicationData = (ApplicationData) getApplication();
        toolbar_title.setText(getResources().getString(R.string.myprofile));

        Log.w(TAG,"onCreate--->");
        session = new SessionManager(Profile_Activity.this);
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

         customerid = user.get(SessionManager.KEY_ID);
        String customername = user.get(SessionManager.KEY_NAME);
        String customeremail = user.get(SessionManager.KEY_EMAIL_ID);
         customerphone = user.get(SessionManager.KEY_MOBILE);
         dob = user.get(SessionManager.KEY_CUSTOMER_DOB);

        if(customername != null){
            edt_name.setText(customername);
        }

        if(customeremail != null){
            edt_emailid.setText(customeremail);
        }

        if(customerphone != null){
            edt_mobilenumber.setText(customerphone);
        }

        if(dob != null){
            tvdob.setText(dob);
        }


        avi_indicator.setVisibility(View.GONE);


        imgBack.setOnClickListener(this);
        rldob.setOnClickListener(this);
        btn_update_profile.setOnClickListener(this);

        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SmsBroadcastListener.bindListener(new OTPSmsListener() {
            @Override
            public void onMessageReceived(String otpText) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"extractedOTP--->"+otpText);
                autoOTP = otpText;
               // alertDialogOTP(otpText);
                if(autoOTP != null) {
                    char[] cArray = autoOTP.toCharArray();
                    otp_textbox_one.setText(String.valueOf(cArray[0]));
                    otp_textbox_two.setText(String.valueOf(cArray[1]));
                    otp_textbox_three.setText(String.valueOf(cArray[2]));
                    otp_textbox_four.setText(String.valueOf(cArray[3]));
                }

            }
        });



    }

    private void alertDialogOTP() {

        try {

            dialog = new Dialog(Profile_Activity.this);
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
                if (new ConnectionDetector(Profile_Activity.this).isNetworkAvailable(Profile_Activity.this)) {
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

                }else if(!responseotp.equalsIgnoreCase(verify_otp)){
                    showErrorLoading(getResources().getString(R.string.invalid_otp)+" "+getResources().getString(R.string.re_enter_otp));

                }
                else{
                    dialog.dismiss();
                    Toasty.success(getApplicationContext(),getResources().getString(R.string.sms_verify_text), Toast.LENGTH_SHORT, true).show();

                    profileUpdateResponseCall();



                }




            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
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
        Intent intent = new Intent(Profile_Activity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callDirections(active_tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.rldob:
                selectDateOfBirth();
                break;
            case R.id.btn_update_profile:
                profileUpdateValidator();
                break;


        }
    }


    private void selectDateOfBirth() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        showDialog(DATE_PICKER_ID);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                // return new DatePickerDialog(this, pickerListener, year, month,day);
                DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dialog;
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;



            String strdayOfMonth = "";
            String strMonth = "";
            int month1 =(month + 1);
            if(day == 9 || day <9){
                strdayOfMonth = "0"+day;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(day);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedDob = strdayOfMonth + "-" + strMonth + "-" + year;

            // Show selected date
            tvdob.setText(SelectedDob);

        }
    };

    public void profileUpdateValidator() {
        boolean can_proceed = true;
        int moblength = Objects.requireNonNull(edt_mobilenumber.getText()).toString().trim().length();
        String emailAddress =Objects.requireNonNull( edt_emailid.getText().toString().trim());
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";


        if (Objects.requireNonNull(edt_name.getText()).toString().trim().equals("") && Objects.requireNonNull(edt_emailid.getText()).toString().trim().equals("") &&
                Objects.requireNonNull(edt_mobilenumber.getText()).toString().trim().equals("")) {
            Toasty.warning(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_name.getText().toString().trim().equals("")) {
            edt_name.setError("Please enter name");
            edt_name.requestFocus();
            can_proceed = false;
        }
        else if (Objects.requireNonNull(edt_emailid.getText()).toString().trim().equals("")) {
            edt_emailid.setError("Please enter email");
            edt_emailid.requestFocus();
            can_proceed = false;
        }else if(!emailAddress.matches(emailPattern)){
            edt_emailid.setError("Please enter correct E_mail address");
            edt_emailid.requestFocus();
            can_proceed = false;
        }else if (Objects.requireNonNull(edt_mobilenumber.getText()).toString().trim().equals("")) {
            edt_mobilenumber.setError("Please enter phone number");
            edt_mobilenumber.requestFocus();
            can_proceed = false;
        } else if (moblength <= 9) {
            edt_mobilenumber.setError("Please enter valid mobile number");
            edt_mobilenumber.requestFocus();
            can_proceed = false;
        }
        if (can_proceed) {
             if(!customerphone.equalsIgnoreCase(edt_mobilenumber.getText().toString().trim())){
                verifyOtp();
             }else {
                 if (new ConnectionDetector(Profile_Activity.this).isNetworkAvailable(Profile_Activity.this)) {
                     profileUpdateResponseCall();
                 }
             }

        }

    }

    private void verifyOtp() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //RestApiInterface ApiService = RetrofitClient.getApiService();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<RegisterMobileResponse> call = apiInterface.registerMobileResponseCall(RestUtils.getContentType(), registerMobileRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<RegisterMobileResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterMobileResponse> call,@NonNull  Response<RegisterMobileResponse> response) {
                //  avi_indicator.smoothToHide();
                Log.w(TAG,"OTP"+ "--->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    ActivityCreate.getCurrentDateandTime();
                    ActivityCreate.activityCreateResponseCall("Testing","011","idd@gmail.com","Tryed to Login","Get otp button pressed",ActivityCreate.currentdateandtime);
                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        responseotp = String.valueOf(response.body().getOTP());
                        Log.w(TAG,"OTP--->"+response.body().getOTP());

                        Log.w(TAG,"OTP--->"+response.body().getOTP());
                        alertDialogOTP();



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
        registerMobileRequest.setPhone(Long.parseLong(edt_mobilenumber.getText().toString()));
        Log.w(TAG,"OTP registerMobileRequest"+ new Gson().toJson(registerMobileRequest));
        return registerMobileRequest;
    }

    private void profileUpdateResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
       // RestApiInterface ApiService = RetrofitClient.getApiService();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);

        Call<NewUserResponse> call = apiInterface.profileUpdateResponseCall(RestUtils.getContentType(), profileUpdateRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<NewUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewUserResponse> call, @NonNull Response<NewUserResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"profileUpdateResponseCall"+ "--->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    ActivityCreate.getCurrentDateandTime();
                    ActivityCreate.activityCreateResponseCall("Testing","011","idd@gmail.com","Tryed to Sign up","Tryed to Sign up",ActivityCreate.currentdateandtime);
                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        session.setIsLogin(true);
                        String dob = tvdob.getText().toString();
                        Log.w(TAG,"Dob--->"+dob);



                        session.createLoginSession(response.body().getData().getName(),response.body().getData().getEmail(), String.valueOf(response.body().getData().getPhone()),"Exists",response.body().getData().get_id(),"",dob);
                       onBackPressed();

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<NewUserResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"profileUpdateResponseCallflr--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ProfileUpdateRequest profileUpdateRequest() {
        /*
         * Phone : 9963220842
         * Name : Anjani
         * Email : anjani513@gmail.com
         * DOB : 24-11-1993
         * User_id
         */
        ProfileUpdateRequest newUserRequest = new ProfileUpdateRequest();
        newUserRequest.setPhone(Long.parseLong(edt_mobilenumber.getText().toString()));
        newUserRequest.setName(edt_name.getText().toString());
        newUserRequest.setEmail(edt_emailid.getText().toString());
        newUserRequest.setDOB(tvdob.getText().toString());
        newUserRequest.setUser_id(customerid);
        Log.w(TAG,"ProfileUpdateRequest"+ new Gson().toJson(newUserRequest));
        return newUserRequest;
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

}