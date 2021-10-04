package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.LocationListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.LocationDeleteListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.LocationDeleteRequest;
import com.triton.myvacala.requestpojo.LocationListRequest;
import com.triton.myvacala.responsepojo.LocationDeleteResponse;
import com.triton.myvacala.responsepojo.LocationListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListActivity extends AppCompatActivity implements LocationDeleteListener {


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    private String TAG = "LocationListActivity";

    SessionManager session;
    String name = "", customerid ="";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;



    @BindView(R.id.rv_locationlist)
    RecyclerView rv_locationlist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.iv_addlocation)
    ImageView iv_addlocation;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout ;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    private List<LocationListResponse.DataBean> locationListResponseList = null;
    LocationListAdapter locationListAdapter;
    private SharedPreferences preferences;
    private String fromactivity;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        ButterKnife.bind(this);

        session = new SessionManager(LocationListActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

        Log.w(TAG, "customerid :" + customerid);


        toolbar_title.setText(getResources().getString(R.string.chooseyourlocation));
        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"fromactivity : "+fromactivity);

        }else{
            fromactivity  = TAG;
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationListActivity.this, AddAddressMapsActivity.class);
                intent.putExtra("fromactivity",fromactivity);
                startActivity(intent);
            }
        });

        if (new ConnectionDetector(LocationListActivity.this).isNetworkAvailable(LocationListActivity.this)) {
            locationListResponseCall();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            // cancel the Visual indication of a refresh
            swipeRefreshLayout.setRefreshing(false);
            if (new ConnectionDetector(LocationListActivity.this).isNetworkAvailable(LocationListActivity.this)) {
                locationListResponseCall();
            }

        });

        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    } //end of oncreate

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    //active = homeFragment;
                    String active_tag = "1";
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
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(LocationListActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    public void locationListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationListResponse> call = apiInterface.locationListResponseCall(RestUtils.getContentType(),locationListRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LocationListResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationListResponse> call, @NotNull Response<LocationListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "LocationListResponse" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        locationListResponseList = response.body().getData();
                        Log.w(TAG,"Size--->"+locationListResponseList.size());

                        if(response.body().getData().isEmpty()){
                            tv_norecords.setVisibility(View.VISIBLE);
                            rv_locationlist.setVisibility(View.GONE);
                        }else{
                            tv_norecords.setVisibility(View.GONE);
                            rv_locationlist.setVisibility(View.VISIBLE);
                            setView();
                        }

                    }
                }else{
                    assert response.body() != null;
                    showErrorLoading(response.body().getMessage());
                }

            }






            @Override
            public void onFailure(@NotNull Call<LocationListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"LocationListResponseflr"+t.getMessage());
            }
        });

    }
    private LocationListRequest locationListRequest() {
        /*
          Customer_id : 5f05d8fef3090625a91f40c6

         */

        LocationListRequest locationListRequest = new LocationListRequest();
        locationListRequest.setCustomer_id(customerid);

        Log.w(TAG," locationListRequest"+ new Gson().toJson(locationListRequest));
        return locationListRequest;
    }

    private void setView() {
        //rv_locationlist.setLayoutManager(new GridLayoutManager(this, 3));

        rv_locationlist.setLayoutManager(new LinearLayoutManager(this));
        rv_locationlist.setItemAnimator(new DefaultItemAnimator());
        locationListAdapter = new LocationListAdapter(getApplicationContext(),locationListResponseList , rv_locationlist,this);
        rv_locationlist.setAdapter(locationListAdapter);

        locationListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > locationListResponseList.size()) {
                    Log.e("haint", "Load More");
                }


            }


        });







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
        if(fromactivity != null){
        if(fromactivity.equalsIgnoreCase("EditMyAddressActivity")){
            callDirections("5");
        }else if(fromactivity.equalsIgnoreCase("HomeFragment")){
            callDirections("1");
        }else{
            callDirections("5");
        }

        }else{
            callDirections("5");
        }


    }

    @Override
    public void locationDeleteListener(String status, String locationid) {
        Log.w(TAG,"locationDeleteListener : "+" status : "+status+" locationid : "+locationid);

        if(status != null && !status.trim().equalsIgnoreCase("Default".trim())){
            showStatusAlert(locationid);
        }else{
            Toasty.warning(getApplicationContext(), "Default location cannot be deleted.", Toast.LENGTH_SHORT, true).show();


        }

    }


    private void showStatusAlert(final String locationid) {

        try {

            dialog = new Dialog(LocationListActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.deletemsg);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    locationDeleteResponseCall(dialog,locationid);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void locationDeleteResponseCall(final ProgressDialog dialog, String locationid) {
        dialog.show();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationDeleteResponse> call = apiInterface.locationDeleteResponseCall(RestUtils.getContentType(),locationDeleteRequest(locationid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<LocationDeleteResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationDeleteResponse> call, @NotNull Response<LocationDeleteResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"LocationDeleteResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), "Address Removed Successfully", Toast.LENGTH_SHORT, true).show();
                       /* Intent i = new Intent(LocationListActivity.this, LocationListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);*/
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<LocationDeleteResponse> call, @NotNull Throwable t) {
                dialog.dismiss();

                Log.w(TAG,"LocationDeleteResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private LocationDeleteRequest locationDeleteRequest(String locationid) {

        /*
          Location_id : 5f05d911f3090625a91f40c7
         */
        LocationDeleteRequest locationDeleteRequest = new LocationDeleteRequest();
        locationDeleteRequest.setLocation_id(locationid);
        Log.w(TAG,"locationDeleteRequest"+ "--->" + new Gson().toJson(locationDeleteRequest));
        return locationDeleteRequest;
    }

}