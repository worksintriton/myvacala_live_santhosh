package com.triton.myvacala.activities;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.ApplicationData;
import com.triton.myvacala.appUtils.KeyboardUtils;
import com.triton.myvacala.requestpojo.RegisterMobileRequest;
import com.triton.myvacala.responsepojo.RegisterMobileResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyOtpActivity extends AppCompatActivity implements OnOtpCompletionListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.txt_entered_mobile_number)
    TextView txt_entered_mobile_number;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.verify_otp_view)
    OtpView verify_otp_view;

    @BindView(R.id.txt_timer_count)
    TextView txt_timer_count;

    @BindView(R.id.lnr_invalid_otp)
    LinearLayout lnr_invalid_otp;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.timer_layout)
    LinearLayout timer_layout;

    @BindView(R.id.txt_Resend)
    TextView txt_Resend;

    @BindView(R.id.btn_submit)
    Button btn_submit;


    CountDownTimer timer;
    String mobile_number = "";
    String responseotp = "";
    String entered_otp = "";
    private ApplicationData applicationData;

    private String TAG = "VerifyOtpActivity";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        applicationData = (ApplicationData) getApplication();
        toolbar_title.setText(getResources().getString(R.string.verify_otp_title));
        verify_otp_view.requestFocus();
      //  verify_otp_view.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        verify_otp_view.setOtpCompletionListener(this);
        avi_indicator.setVisibility(View.GONE);
        lnr_invalid_otp.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);

        if (getIntent() != null && getIntent().hasExtra("mobilenumber")) {
            mobile_number = getIntent().getStringExtra("mobilenumber");
        }
        if (getIntent() != null && getIntent().hasExtra("otp")) {
            responseotp = getIntent().getStringExtra("otp");
        }

        txt_entered_mobile_number.setText("+91 " + mobile_number);
        timer_layout.setVisibility(View.VISIBLE);

        imgBack.setOnClickListener(v -> onBackPressed());

        timer = new CountDownTimer(applicationData.getTimer_milliseconds(), 1000) {
            @SuppressLint("DefaultLocale")
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
            if (new ConnectionDetector(VerifyOtpActivity.this).isNetworkAvailable(VerifyOtpActivity.this)) {
                resendOtp();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideKeyboard(VerifyOtpActivity.this);
                Intent intent = new Intent(VerifyOtpActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.start();
        }
    }

    private void resendOtp() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //RestApiInterface ApiService = RetrofitClient.getApiService();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<RegisterMobileResponse> call = apiInterface.registerMobileResponseCall(RestUtils.getContentType(), registerMobileRequest());
        call.enqueue(new Callback<RegisterMobileResponse>() {
            @Override
            public void onResponse(Call<RegisterMobileResponse> call, Response<RegisterMobileResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"LOGIN --->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        timer_layout.setVisibility(View.VISIBLE);
                        txt_Resend.setVisibility(View.GONE);
                        timer = new CountDownTimer(180000, 1000) {
                            @SuppressLint({"SetTextI18n", "DefaultLocale"})
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

                    } else {
                        Toast.makeText(getApplicationContext(), "Mobile number is not registered", Toast.LENGTH_SHORT).show();

                    }
                }


            }

            @Override
            public void onFailure(Call<RegisterMobileResponse> call, Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("LOGIN", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private RegisterMobileRequest registerMobileRequest() {
        RegisterMobileRequest registerMobileRequest = new RegisterMobileRequest();
        registerMobileRequest.setPhone(Long.parseLong(mobile_number));
        Log.w(TAG,"OTP registerMobileRequest"+ new Gson().toJson(registerMobileRequest));
        return registerMobileRequest;
    }






    @Override
    public void onOtpCompleted(String otp) {
        entered_otp = otp;
        KeyboardUtils.hideKeyboard(VerifyOtpActivity.this);

        if (responseotp.equalsIgnoreCase(entered_otp)) {
        lnr_invalid_otp.setVisibility(View.GONE);
        btn_submit.setVisibility(View.VISIBLE);
        }else{
            lnr_invalid_otp.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
