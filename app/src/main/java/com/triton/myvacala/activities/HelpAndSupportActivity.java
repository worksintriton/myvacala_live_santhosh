package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.FAQListAdapter;
import com.triton.myvacala.adapter.NotificationsListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.FAQResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpAndSupportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PHONE_CALL =1 ;
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    String active_tag = "5";

    String TAG = "HelpAndSupportActivity";

    @BindView(R.id.rv_faqlist)
    RecyclerView rv_faqlist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.txt_emailid)
    TextView txt_emailid;

    @BindView(R.id.txt_phonenumber)
    TextView txt_phonenumber;

    @BindView(R.id.img_call)
    ImageView img_call;

    @BindView(R.id.img_email)
    ImageView img_email;


    private List<FAQResponse.DataBean> fAQResponseList = null;
    FAQListAdapter faqListAdapter;
    private SharedPreferences preferences;


    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        ButterKnife.bind(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        avi_indicator.setVisibility(View.GONE);

        toolbar_title.setText(getResources().getString(R.string.helpandsupport));

        imgBack.setOnClickListener(this);
        img_call.setOnClickListener(this);
        img_email.setOnClickListener(this);

        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getMobileappdetails();
        String emailID = user.get(SessionManager.KEY_MOBILEAPPDETAILS_EMAIL);
        String mobile = user.get(SessionManager.KEY_MOBILEAPPDETAILS_PHONENUMBER);

        Log.w(TAG, "emailID-->" + emailID + " " + "mobile : " + mobile);

        if (emailID != null) {
            txt_emailid.setText(emailID);
        } else {
            txt_emailid.setText("");

        }
        if (mobile != null) {
            txt_phonenumber.setText(mobile);
        } else {
            txt_phonenumber.setText("");

        }


        if (new ConnectionDetector(HelpAndSupportActivity.this).isNetworkAvailable(HelpAndSupportActivity.this)) {
            fAQResponseCall();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                String active_tag = "1";
                callDirections(active_tag);
                break;


            case R.id.search:
                active_tag = "2";
                callDirections(active_tag);
                break;


            case R.id.myvehicle:
                active_tag = "3";
                callDirections(active_tag);
                break;
            case R.id.cart:
                active_tag = "4";
                callDirections(active_tag);
                break;
            case R.id.account:
                active_tag = "5";
                callDirections(active_tag);
                break;

        }
        return true;
    };

    public void callDirections(String tag) {
        Intent intent = new Intent(HelpAndSupportActivity.this, DashboardActivity.class);
        intent.putExtra("tag", tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callDirections(active_tag);
    }


    public void fAQResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FAQResponse> call = apiInterface.fAQResponseCall(RestUtils.getContentType());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<FAQResponse>() {
            @Override
            public void onResponse(@NonNull Call<FAQResponse> call, @NonNull Response<FAQResponse> response) {
                avi_indicator.smoothToHide();


                Log.w(TAG, "FAQResponse" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().getCode() == 200) {
                        fAQResponseList = response.body().getData();
                        Log.w(TAG, "Size--->" + fAQResponseList.size());

                        if (response.body().getData().isEmpty()) {
                            tv_norecords.setVisibility(View.VISIBLE);
                            rv_faqlist.setVisibility(View.GONE);
                        } else {
                            tv_norecords.setVisibility(View.GONE);
                            rv_faqlist.setVisibility(View.VISIBLE);
                            setView();
                        }

                    }
                } else {
                    assert response.body() != null;
                    showErrorLoading(response.body().getMessage());
                }


            }


            @Override
            public void onFailure(@NonNull Call<FAQResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "FAQResponseflr" + t.getMessage());
            }
        });

    }


    private void setView() {

        rv_faqlist.setLayoutManager(new LinearLayoutManager(this));
        rv_faqlist.setItemAnimator(new DefaultItemAnimator());
        faqListAdapter = new FAQListAdapter(getApplicationContext(), fAQResponseList, rv_faqlist);
        rv_faqlist.setAdapter(faqListAdapter);

        faqListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > fAQResponseList.size()) {
                    Log.e("haint", "Load More");
                }


            }


        });


    }

    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void hideLoading() {
        try {
            alertDialog.dismiss();
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.img_call:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    gotoPhone();
                }

                break;
            case R.id.img_email:
                gotoEmail();
                break;
        }
    }

    private void gotoPhone() {
        Log.w(TAG,"gotoPhone-->"+txt_phonenumber.getText().toString());
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txt_phonenumber.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }


    private void gotoEmail() {
        Log.w(TAG,"gotoEmail-->"+txt_emailid.getText().toString());
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",txt_emailid.getText().toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}