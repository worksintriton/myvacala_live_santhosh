package com.triton.myvacala.fragment;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;


import com.triton.myvacala.R;
import com.triton.myvacala.activities.DashboardActivity;
import com.triton.myvacala.activities.HelpAndSupportActivity;
import com.triton.myvacala.activities.LocationListActivity;
import com.triton.myvacala.activities.LoginActivity;
import com.triton.myvacala.activities.NotificationActivity;
import com.triton.myvacala.activities.OrderHistoryActivity;
import com.triton.myvacala.activities.Profile_Activity;
import com.triton.myvacala.activities.ShareMyVacalaActivity;
import com.triton.myvacala.activities.TermsAndConditionsActivity;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;


import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccountFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.rlprofile)
    RelativeLayout rlprofile;

    @BindView(R.id.rlmylocations)
    RelativeLayout rlmylocations;

    @BindView(R.id.rlsubscription)
    RelativeLayout rlsubscription;

    @BindView(R.id.rlorderhistory)
    RelativeLayout rlorderhistory;

    @BindView(R.id.rlnotifications)
    RelativeLayout rlnotifications;

    @BindView(R.id.rlhelpandsupport)
    RelativeLayout rlhelpandsupport;

    @BindView(R.id.rlshare)
    RelativeLayout rlshare ;

    @BindView(R.id.rllogout)
    RelativeLayout rllogout ;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txt_privacy)
    TextView txt_privacy;


    SessionManager session;

    String name = "", customerid ="";





    private Context mContext;



    public AccountFragment() {
        // Required empty public constructor

    }

    String TAG ="AccountFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG,"onCreate-->");


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
       // ApplicationData applicationData = (ApplicationData) Objects.requireNonNull(getActivity()).getApplication();
       // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();

        toolbar_title.setText(getResources().getString(R.string.myaccount));

        rlprofile.setOnClickListener(this);
        rlmylocations.setOnClickListener(this);
        rlsubscription.setOnClickListener(this);
        rlorderhistory.setOnClickListener(this);
        rlnotifications.setOnClickListener(this);
        rlhelpandsupport.setOnClickListener(this);
        rlshare.setOnClickListener(this);
        rllogout.setOnClickListener(this);
        txt_privacy.setOnClickListener(this);

        session = new SessionManager(mContext);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDirections("1");

            }
        });







        return view;
    }
    public void callDirections(String tag){
        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);


    }

    @Override
    public void onResume() {
        super.onResume();
    }









    private void saveLoginSyncDetails(String firstname, String residentname, String mobile, String email, String dob, String profilepicthumbnail, String cover_pic, String name, String apartmentnumber, String maritalstatus, String userId) {
        //new PrefManager(getContext()).saveLoginSyncDetails(firstname,residentname,mobile,email,dob,profilepicthumbnail,name,apartmentnumber,maritalstatus,userId,cover_pic);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlprofile:
                gotoProfile();
                break;
            case R.id.rlmylocations:
                gotoLocationList();
                break;
            case R.id.rlsubscription:
                break;
            case R.id.rlorderhistory:
                gotoOrderHistory();
                break;
            case R.id.rlnotifications:
                gotoNotifications();
                break;
            case R.id.rlhelpandsupport:
                gotoHelpandSupport();
                break;
            case R.id.rlshare:
                gotoShareMyVacala();
                break;
            case R.id.rllogout:
                logoutAlert();
                break;

            case R.id.txt_privacy:
                gotoTermsandConditions();
                break;

        }

    }

    private void gotoTermsandConditions() {

        String url = "https://myvacala.com/api/uploads/a11c0db0-725a-4d7c-9805-738c30395d08.pdf";

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        startActivity(browserIntent);

    }


    private void gotoProfile() {
        startActivity(new Intent(getActivity(), Profile_Activity.class));
    }
    private void gotoLocationList() {
        startActivity(new Intent(getActivity(), LocationListActivity.class));
    }
    private void gotoOrderHistory() {
        startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
    }
    private void gotoNotifications() {
        startActivity(new Intent(getActivity(), NotificationActivity.class));
    }
    private void gotoHelpandSupport() {
        startActivity(new Intent(getActivity(), HelpAndSupportActivity.class));
    }
    private void gotoShareMyVacala() {
        startActivity(new Intent(getActivity(), ShareMyVacalaActivity.class));
    }



    private void logoutAlert() {

        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Do you want to Logout?")
                .setCancelText("Cancel")
                .setConfirmText("Ok")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
                            session.logoutUser();

                            startActivity(new Intent(mContext, LoginActivity.class));
                            Objects.requireNonNull(getActivity()).finish();
                            sweetAlertDialog.dismiss();


                        }

                    }
                })
                .show();
    }


}
