package com.triton.myvacala.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.AddLocationRequest;
import com.triton.myvacala.responsepojo.AddLocationResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMyAddressActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;


    @BindView(R.id.txt_cityname)
    TextView txt_cityname;

    @BindView(R.id.txt_address)
    TextView txt_address;

    @BindView(R.id.btn_change)
    Button btn_change;

    @BindView(R.id.edt_floatandbuildingno)
    EditText edt_floatandbuildingno;

    @BindView(R.id.edt_nearbylandmark)
    EditText edt_nearbylandmark;

    @BindView(R.id.edt_pickname)
    EditText edt_pickname;

    @BindView(R.id.btn_savelocation)
    Button btn_savelocation;


    String latlng = "";

    double latitude = 0, longtitude =0;

    Marker mCurrLocationMarker;

    String CityName = "", AddressLine = "";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    String TAG = "AddMyAddressActivity";

    String customerid = "",state = "",country = "",postalcode = "",street = "";

    SessionManager session;

    String name = "", emailID = "",  mobile = "", type = "";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    @BindView(R.id.ll_home)
    LinearLayout ll_home;

    @BindView(R.id.ll_work)
    LinearLayout ll_work;

    @BindView(R.id.ll_others)
    LinearLayout ll_others;

    @BindView(R.id.txt_home)
    TextView txt_home;

    @BindView(R.id.txt_work)
    TextView txt_work;

    @BindView(R.id.txt_others)
    TextView txt_others;

    @BindView(R.id.ivhome)
    ImageView ivhome;

    @BindView(R.id.ivwork)
    ImageView ivwork;

    @BindView(R.id.ivothers)
    ImageView ivothers;

    String LocationType = "Home";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_address);

        Log.w(TAG,"onCreate-->");

        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        emailID = user.get(SessionManager.KEY_EMAIL_ID);
        mobile = user.get(SessionManager.KEY_MOBILE);
        type = user.get(SessionManager.KEY_TYPE);
        customerid = user.get(SessionManager.KEY_ID);

        Log.w(TAG,"customerid--->"+customerid);


        toolbar_title.setText(getResources().getString(R.string.addmyaddress));
        avi_indicator.setVisibility(View.GONE);

        imgBack.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_savelocation.setOnClickListener(this);


        ll_home.setOnClickListener(this);
        ll_work.setOnClickListener(this);
        ll_others.setOnClickListener(this);

        ivhome.setOnClickListener(this);
        ivwork.setOnClickListener(this);
        ivothers.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            latlng = String.valueOf(getIntent().getSerializableExtra("latlng"));
           Log.w(TAG,"latlng-->"+getIntent().getSerializableExtra("latlng"));

             String newString = latlng.replace("lat/lng:", "");
            Log.w(TAG,"latlng=="+newString);

            String latlngs = newString.trim().replaceAll("\\(", "").replaceAll("\\)","").trim();
            Log.w(TAG,"latlngs=="+latlngs);

            String[] separated = latlngs.split(",");
            String lat = separated[0];
            String lon = separated[1];

            latitude = Double.parseDouble(lat);
            longtitude = Double.parseDouble(lon);

            getAddress(latitude,longtitude);

            Log.w(TAG,"lat"+lat+" "+"lon :"+lon);
            Log.w(TAG,"latitude"+latitude+" "+"longtitude :"+longtitude);

            CityName = extras.getString("cityname");
            AddressLine = extras.getString("address");

            txt_cityname.setText(CityName);
            txt_address.setText(AddressLine);



        }



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        LatLng latLng = new LatLng(latitude,longtitude);

        //  mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        MarkerOptions markerOptions = new MarkerOptions().position(Objects.requireNonNull(latLng)).title("");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.btn_change:
                onBackPressed();
                break;
            case R.id.btn_savelocation:
              saveLocationValidator();
                break;
            case R.id.ll_home:
                changeBackgroundHome();
                break;
            case R.id.ll_work:
                changeBackgroundWork();
                break;
            case R.id.ll_others:
                changeBackgroundOthers();
                break;

            case R.id.ivhome:
                changeBackgroundHome();
                break;
            case R.id.ivwork:
                changeBackgroundWork();
                break;
            case R.id.ivothers:
                changeBackgroundOthers();
                break;

        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeBackgroundHome() {
        LocationType = "Home";
        Log.w(TAG,"LocationType-->"+LocationType);
        ll_home.setBackgroundResource(R.drawable.button_background);
        ll_work.setBackgroundResource(R.drawable.layout_bg);
        ll_others.setBackgroundResource(R.drawable.layout_bg);
        txt_home.setTextColor(getResources().getColor(R.color.white));
        txt_work.setTextColor(getResources().getColor(R.color.dark_gray));
        txt_others.setTextColor(getResources().getColor(R.color.dark_gray));
        ivhome.setBackground(getResources().getDrawable(R.drawable.ic_home_white));
        ivwork.setBackground(getResources().getDrawable(R.drawable.ic_work));
        ivothers.setBackground(getResources().getDrawable(R.drawable.ic_marker_color));


    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeBackgroundWork() {
        LocationType = "Work";
        Log.w(TAG,"LocationType-->"+LocationType);
        ll_home.setBackgroundResource(R.drawable.layout_bg);
        ll_work.setBackgroundResource(R.drawable.button_background);
        ll_others.setBackgroundResource(R.drawable.layout_bg);
        txt_home.setTextColor(getResources().getColor(R.color.dark_gray));
        txt_work.setTextColor(getResources().getColor(R.color.white));
        txt_others.setTextColor(getResources().getColor(R.color.dark_gray));
        ivhome.setBackground(getResources().getDrawable(R.drawable.ic_home_color));
        ivwork.setBackground(getResources().getDrawable(R.drawable.ic_work_white));
        ivothers.setBackground(getResources().getDrawable(R.drawable.ic_marker_color));

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeBackgroundOthers() {
        LocationType = "Others";
        Log.w(TAG,"LocationType-->"+LocationType);
        ll_work.setBackgroundResource(R.drawable.layout_bg);
        ll_home.setBackgroundResource(R.drawable.layout_bg);
        ll_others.setBackgroundResource(R.drawable.button_background);
        txt_home.setTextColor(getResources().getColor(R.color.dark_gray));
        txt_work.setTextColor(getResources().getColor(R.color.dark_gray));
        txt_others.setTextColor(getResources().getColor(R.color.white));
        ivhome.setBackground(getResources().getDrawable(R.drawable.ic_home_color));
        ivwork.setBackground(getResources().getDrawable(R.drawable.ic_work));
        ivothers.setBackground(getResources().getDrawable(R.drawable.ic_marker_white));


    }

    public void saveLocationValidator() {
        boolean can_proceed = true;
        if (edt_floatandbuildingno.getText().toString().trim().equals("")) {
             edt_floatandbuildingno.setError("Please enter building number");
             edt_floatandbuildingno.requestFocus();
            can_proceed = false;
        } else if (edt_pickname.getText().toString().trim().equals("")) {
             edt_pickname.setError("Please enter pick a nick Name for this location");
             edt_pickname.requestFocus();
            can_proceed = false;
        }

        if (can_proceed) {
            if (new ConnectionDetector(AddMyAddressActivity.this).isNetworkAvailable(AddMyAddressActivity.this)) {
                addLocationResponseCall();
                }


            }

        }

    public void addLocationResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddLocationResponse> call = apiInterface.addLocationResponseCall(RestUtils.getContentType(),addLocationRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AddLocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddLocationResponse> call, @NotNull Response<AddLocationResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "AddLocationResponse" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        Intent i = new Intent(AddMyAddressActivity.this, PopularVehicleListActivity.class);
                        startActivity(i);

                    }
                }else{
                    showErrorLoading(response.body().getMessage());
                }

            }






            @Override
            public void onFailure(@NotNull Call<AddLocationResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"AddLocationResponseflr"+t.getMessage());
            }
        });

    }
    private AddLocationRequest addLocationRequest() {
        /*
          Customer_id : 5f05d8fef3090625a91f40c6
          City : Chennai
          State : TamilNadu
          Country : India
          Pincode : 600100
          Street : Koil street
          lat : 15.2345
          long : 70.546
          NearBy_LandMark : Pooja stores
          Location_NickName : MGM
          Flat_No : 24-f
          Location_type : Office
         */
        Log.w(TAG,"AddLocationRequest--->"+"latitude"+latitude+" "+"longtitude :"+longtitude);


        AddLocationRequest addLocationRequest = new AddLocationRequest();
        addLocationRequest.setCustomer_id(customerid);
        addLocationRequest.setCity(CityName);
        addLocationRequest.setState(state);
        addLocationRequest.setCountry(country);
        addLocationRequest.setPincode(postalcode);
        addLocationRequest.setStreet(AddressLine);
        addLocationRequest.setLat(latitude);
        addLocationRequest.setLongX(longtitude);
        addLocationRequest.setNearBy_LandMark(edt_nearbylandmark.getText().toString());
        addLocationRequest.setLocation_NickName(edt_pickname.getText().toString());
        addLocationRequest.setFlat_No(edt_floatandbuildingno.getText().toString());
        addLocationRequest.setLocation_type(LocationType);

        Log.w(TAG," addLocationRequest"+ new Gson().toJson(addLocationRequest));
        return addLocationRequest;
    }
    private void getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                Address address = listAddresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
                Log.w(TAG,"getAddress-->"+result.toString());

                 state = listAddresses.get(0).getAdminArea();
                 country = listAddresses.get(0).getCountryName();
                 String subLocality = listAddresses.get(0).getSubLocality();
                 postalcode = listAddresses.get(0).getPostalCode();
                AddressLine = listAddresses.get(0).getAddressLine(0);
                CityName = listAddresses.get(0).getLocality();


                // Thoroughfare seems to be the street name without numbers
                 street = address.getThoroughfare();

                Log.w(TAG,"AddressLine :"+AddressLine+"CityName :"+CityName+"street :"+street);

                Log.w(TAG,"state :"+state+" "+"country :"+country+"subLocality :"+subLocality+"postalcode :"+postalcode);
            }
        } catch (IOException e) {
            Log.e("tag", Objects.requireNonNull(e.getMessage()));
        }

        result.toString();
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

