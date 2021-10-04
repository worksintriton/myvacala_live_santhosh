package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.ActivityCreate;
import com.triton.myvacala.requestpojo.NewUserRequest;
import com.triton.myvacala.responsepojo.NewUserResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.edt_name)
    EditText edt_name;

    @BindView(R.id.edt_emailid)
    EditText edt_emailid;

    @BindView(R.id.txt_mobilenumber)
    TextView txt_mobilenumber;

    @BindView(R.id.iv_signin)
    ImageView iv_signin;

    @BindView(R.id.back_rela)
    RelativeLayout back_rela;

    private String TAG = "SignUpActivity";
    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;

    SessionManager session;

    String mobileno ="";

    @BindView(R.id.cb_terms)
    CheckBox cb_terms;

    @BindView(R.id.txt_terms)
    TextView txt_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        avi_indicator.setVisibility(View.GONE);
        iv_signin.setOnClickListener(this);
        txt_terms.setOnClickListener(this);

        if (getIntent() != null && getIntent().hasExtra("mobileno")) {
            mobileno = getIntent().getStringExtra("mobileno");
            txt_mobilenumber.setText(mobileno);
        }

        back_rela.setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_signin) {
            signupValidator();
        }

        else if (v.getId() == R.id.txt_terms) {

            String url = "https://myvacala.com/api/uploads/a11c0db0-725a-4d7c-9805-738c30395d08.pdf";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            startActivity(browserIntent);

        }

    }
    public void signupValidator() {
        boolean can_proceed = true;
        int moblength = Objects.requireNonNull(txt_mobilenumber.getText()).toString().trim().length();
        String emailAddress =Objects.requireNonNull( edt_emailid.getText().toString().trim());
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";


        if (Objects.requireNonNull(edt_name.getText()).toString().trim().equals("") && Objects.requireNonNull(edt_emailid.getText()).toString().trim().equals("") &&
                Objects.requireNonNull(txt_mobilenumber.getText()).toString().trim().equals("")) {
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
            edt_emailid.setError("Please enter correct Email address");
            edt_emailid.requestFocus();
            can_proceed = false;
        }else if (Objects.requireNonNull(txt_mobilenumber.getText()).toString().trim().equals("")) {
            txt_mobilenumber.setError("Please enter phone number");
            txt_mobilenumber.requestFocus();
            can_proceed = false;
        } else if (moblength <= 9) {
            txt_mobilenumber.setError("Please enter valid mobile number");
            txt_mobilenumber.requestFocus();
            can_proceed = false;
        }



        if(!cb_terms.isChecked()){

            Toasty.error(getApplicationContext(), getResources().getString(R.string.terms_error), Toast.LENGTH_SHORT, true).show();

            can_proceed = false;

        }



        if (can_proceed) {
            if (new ConnectionDetector(SignUpActivity.this).isNetworkAvailable(SignUpActivity.this)) {
                newUserResponseCall();



             /*   Intent intent = new Intent(Patient_SignUpActivity.this, VerifyOtpActivity.class);
                intent.putExtra("name",etname.getText().toString());
                intent.putExtra("phonenumber",etphonenumber.getText().toString());
                intent.putExtra("email",etemail.getText().toString());
                intent.putExtra("password",etpassword.getText().toString());
                intent.putExtra("type","0");
                startActivity(intent);*/
                // patientsignUpResponseCall(formattedDate);


            }

        }

    }

    private void newUserResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);

        //RestApiInterface ApiService = RetrofitClient.getApiService();
        Call<NewUserResponse> call = apiInterface.newUserResponseCall(RestUtils.getContentType(), newUserRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<NewUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewUserResponse> call, @NonNull  Response<NewUserResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"newUserResponseCall"+ "--->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    ActivityCreate.getCurrentDateandTime();
                    ActivityCreate.activityCreateResponseCall("Testing","011","idd@gmail.com","Tryed to Sign up","Tryed to Sign up",ActivityCreate.currentdateandtime);
                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        session.setIsLogin(true);
                        session.createLoginSession(response.body().getData().getName(),response.body().getData().getEmail(), String.valueOf(response.body().getData().getPhone()),"Exists",response.body().getData().get_id(),"","");
                         Intent i = new Intent(SignUpActivity.this, MapsActivity.class);
                       /* i.putExtra("mobilenumber",edt_mobile_number.getText().toString());
                        i.putExtra("otp",String.valueOf(response.body().getData().getOTP()));*/
                        startActivity(i);

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<NewUserResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("newUserResponseCallflr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private NewUserRequest newUserRequest() {
        /*
          Phone : 7418010184
          Name : Venkat
          Email : venkat@gmail.com
         */
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setPhone(Long.parseLong(txt_mobilenumber.getText().toString()));
        newUserRequest.setName(edt_name.getText().toString());
        newUserRequest.setEmail(edt_emailid.getText().toString());
        Log.w(TAG,"newUserRequest"+ new Gson().toJson(newUserRequest));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}