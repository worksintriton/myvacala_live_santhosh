package com.triton.myvacala.activities.parking.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkingShareMyVacalaActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.iv_imgShare)
    ImageView iv_imgShare;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;

    String active_tag = "4";

    String TAG = "ParkingShareMyVacalaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_my_vacala_parking);

        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        toolbar_title.setText(getResources().getString(R.string.sharemyvacala));

        imgBack.setOnClickListener(v -> onBackPressed());

        iv_imgShare.setOnClickListener(this);

        bottom_navigation_parking.setSelectedItemId(R.id.account);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

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
    };
    public void callDirections(String tag){
        Intent intent = new Intent(ParkingShareMyVacalaActivity.this, DashboardParkingActivity.class);
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
            case R.id.iv_imgShare:
                shareMyApp();
                break;
        }
    }

    private void shareMyApp() {

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getMobileappdetails();
        String sharelink = user.get(SessionManager.KEY_MOBILEAPPDETAILS_ANDROIDSHARELINK);
        Log.w(TAG,"sharelink--->"+sharelink);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
       // String shareBody = sharelink;
        String shareMessage= "\nLet me recommend you this application\n\n"+"Click here & install My Vacala";
        shareMessage = shareMessage+"\n\n"+sharelink;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "My Vacala");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}