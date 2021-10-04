package com.triton.myvacala.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.triton.myvacala.R;


import com.triton.myvacala.activities.parking.maps.ParkingPickUpLocationActivity;
import com.triton.myvacala.activities.parking.maps.ParkingPlacesSearchActivity;
import com.triton.myvacala.adapter.ParkingOptionsListAdapter;
import com.triton.myvacala.adapter.ViewPagerParkingOptionsAdapter;
import com.triton.myvacala.api.API;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.ParkingSelectlatlongListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;

import com.triton.myvacala.requestpojo.CheckTimesRequest;
import com.triton.myvacala.requestpojo.GetCustomerVehicleandLocationRequest;
import com.triton.myvacala.requestpojo.ParkingListRequest;
import com.triton.myvacala.responsepojo.CheckTimesResponse;
import com.triton.myvacala.responsepojo.GetAddressResultResponse;
import com.triton.myvacala.responsepojo.GetCustomerVehicleandLocationResponse;

import com.triton.myvacala.responsepojo.ParkingListResponse;
import com.triton.myvacala.responsepojo.ParkingLocationGetListResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;
import com.triton.myvacala.service.GPSTracker;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ValueRange;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;


import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.location.LocationListener;

public class HomeFragmentParking extends Fragment implements Serializable, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ParkingSelectlatlongListener, View.OnClickListener {


    private static final String TAG = "HomeFragmentParking" ;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.ll_listviewclick)
    LinearLayout ll_listviewclick;

    @BindView(R.id.ll_mapviewclick)
    LinearLayout ll_mapviewclick;

    @BindView(R.id.rllist)
    RelativeLayout rllist;

    @BindView(R.id.ll_mapview)
    LinearLayout ll_mapview;


    @BindView(R.id.rv_parkingoptionslist)
    RecyclerView rv_parkingoptionslist;

    @BindView(R.id.tv_norecords_parkinglist)
    TextView tv_norecords_parkinglist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.img_currentlocation)
    ImageView img_currentlocation;

    private SharedPreferences preferences;
    private String checkinDate = null, checkoutDate = null;
    private String checkinTime = null, checkoutTime = null;
    private List<ParkingListResponse.DataBean> dataBeanList;
    ViewPagerParkingOptionsAdapter viewPagerParkingOptionsAdapter;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 3000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 6000;
    private double latitude;
    private double longitude;


    @BindView(R.id.ll_bookingdate)
    LinearLayout ll_bookingdate;

    @BindView(R.id.ll_bookingfor)
    LinearLayout ll_bookingfor;

    @BindView(R.id.fl_parkingpage)
    FrameLayout fl_parkingpage;

    @BindView(R.id.txt_bookingdateandtime)
    TextView txt_bookingdateandtime;


    BottomSheetDialog bottomSheetDialog;

    TextView txt_selecteddate_time;
    TextView txt_checkin_date;
    TextView txt_checkout_date;
    TextView txt_checkin_time;
    TextView txt_checkout_time;

    RelativeLayout rl_datepicker,rl_timepicker;




    int checkinhours;
    int checkouthours;


    public static String str_checkintime, str_checkoutime;
    public static String strtotalHours, strdays;



    @BindView(R.id.rl_placessearch)
    RelativeLayout rl_placessearch;

    @BindView(R.id.tv_searchlocationaddress)
    TextView tv_searchlocationaddress;


    private String fromactivity;
    double lat = 0, lon = 0;


    private List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList = null;


    @BindView(R.id.togglebutton)
    ToggleButton toggleButton;

    String twowheelervehicleid = "", fourwheelervehicleid = "";
    private String selectedVehicleType, selectedVehicleTypeId;

    String requestCheckinDate, requestCheckoutDate;
    private String customerid;
    private ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;

    String headerVehicleimg, headerVehicleName, headerVehicleBrandName, headerVehicleFuelTypeName, headerVehicleFuelTypeBackgroundcolor, vehicleNumber;

    @BindView(R.id.cv_vehicleimage)
    ImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;

    @BindView(R.id.btn_vehiclefueltype)
    Button btn_vehiclefueltype;

    @BindView(R.id.img_clear_search)
    ImageView img_clear_search;


    public static String stratdate, enddate, strattime, endtime;

    public static String bookingstartdate, bookingenddate;
    private Context mContext;
    private int checkedinTime;
    private int CurrentcheckedinTime;

    String twowheelerstatus = "" ,fourwheelerstatus = "" ,vehicletypename;
    private AlertDialog.Builder alertDialogBuilder;
    private Dialog alertDialog;

    String formattedDate;

    String localTime;

    private String defaultcheckinhours,defaultcheckouthours;
    private String currentDateandTime;
    private String formattedDate1;
    private String CityName;
    private boolean isValidBookingData = false;
    private boolean isCurrentlocationClick = false;
    private int nextMinute = 0;
    private Date startDate;

    private TimePicker timePicker,timePicker_checkout; // set in onCreate
    private DatePicker datePicker,datePicker_checkout; // set in onCreate
    private NumberPicker minutePicker;
    private static final DecimalFormat FORMATTER = new DecimalFormat("00");
    private String hourssplit;
    private int currentmin;
    private String checkintime24hrs;
    private String checkouttime24hrs;
    private String str_checkindate;
    private String str_checkoutdate;
    private String checkintimeplusonehour;
    private String checkindateplusonehour;
    private String checkindatechange;
    private String checkoutdatechange;
    private boolean strlatlng;


    public HomeFragmentParking() {
        // Required empty public constructor
    }

    private SupportMapFragment mapFragment;


   private String checkin_date,checkin_time,checkout_date,checkout_time;
   private int days,hours;

   private  boolean isCheckedin = false;


    private  float zoomLevel = (float) 14.0;

    String roundedminutes;

    private List<ParkingLocationGetListResponse.DataBean> getServiceListResponseList = null;
    List<String> pinCodeList;
    boolean isServiceCityPincode = false;
    String  AddressLine,PostalCode;

    GPSTracker gpsTracker;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w(TAG,"onCreate--->");


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView-->");
        View view = inflater.inflate(R.layout.activity_parking_dashboard, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();
        Log.w(TAG,"onCreateView :"+" mContext :"+mContext);

        gpsTracker = new GPSTracker(mContext);



        SessionManager session = new SessionManager(mContext);
        HashMap<String, String> user = session.getUserDetails();
        customerid = user.get(SessionManager.KEY_ID);


        rllist.setVisibility(View.GONE);



        avi_indicator.setVisibility(View.GONE);
        cv_vehicleimage.setVisibility(View.GONE);
        btn_vehiclefueltype.setVisibility(View.GONE);
        txt_vehiclename.setVisibility(View.GONE);
        txt_vehicle_number.setVisibility(View.GONE);
        txt_bookingdateandtime.setVisibility(View.GONE);

        ll_listviewclick.setOnClickListener(this);
        ll_mapviewclick.setOnClickListener(this);
        img_currentlocation.setOnClickListener(this);
        ll_bookingdate.setOnClickListener(this);


        // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }
        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        googleApiConnected();

        Log.w(TAG,"getArguments()-->"+getArguments());
        if(getArguments() != null) {
            CityName = getArguments().getString("cityname");
            fromactivity = getArguments().getString("fromactivity");
            lat = getArguments().getDouble("lat");
            lon = getArguments().getDouble("lon");
        }




        tv_searchlocationaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ParkingPlacesSearchActivity.class));
            }
        });

        img_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_searchlocationaddress.setText("");
            }
        });







        toggleButton.setOnClickListener(v -> {

            Log.w(TAG,"toggleButton setOnClickListener status"+" "+toggleButton.isChecked());
            Log.w(TAG,"toggleButton setOnClickListener"+" fourwheelerstatus : "+fourwheelerstatus+" twowheelerstatus : "+twowheelerstatus);

/*
            if(!fourwheelerstatus.isEmpty()){
                selectedVehicleTypeId = fourwheelervehicleid;
                selectedVehicleType = "Four Wheeler";
                parkingListResponseCall(defaultcheckinhours,defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId);
                Log.w(TAG, "four wheeler clicked :" + fourwheelervehicleid);
                getVehicleData(selectedVehicleTypeId);
                rllist.setVisibility(View.VISIBLE);

            }
            else{
                showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));
                cv_vehicleimage.setVisibility(View.GONE);
                txt_vehiclename.setVisibility(View.GONE);
                txt_vehicle_number.setVisibility(View.GONE);
                btn_vehiclefueltype.setVisibility(View.GONE);
                rllist.setVisibility(View.GONE);
                rllist.setVisibility(View.GONE);

                if(!twowheelerstatus.isEmpty()){
                    selectedVehicleType = "Two Wheeler";
                    selectedVehicleTypeId = twowheelervehicleid;
                    parkingListResponseCall(defaultcheckinhours,defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId);
                    getVehicleData(selectedVehicleTypeId);
                    rllist.setVisibility(View.VISIBLE);
                }
                else{
                    showErrorLoading( getResources().getString(R.string.vehicletypeerrormsg));
                    cv_vehicleimage.setVisibility(View.GONE);
                    txt_vehiclename.setVisibility(View.GONE);
                    txt_vehicle_number.setVisibility(View.GONE);
                    btn_vehiclefueltype.setVisibility(View.GONE);
                    rllist.setVisibility(View.GONE);

                }


            }*/

            if (toggleButton.isChecked()) {
                //Button is ON

                Log.w(TAG, "Car");
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);
                selectedVehicleTypeId = fourwheelervehicleid;
                selectedVehicleType = "Four Wheeler";

                if(!fourwheelerstatus.isEmpty()){
                    selectedVehicleTypeId = fourwheelervehicleid;
                    selectedVehicleType = "Four Wheeler";
                    Log.w(TAG,"toggleButton.isChecked()");
                    parkingListResponseCall(defaultcheckinhours,defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId,selectedVehicleType);
                    Log.w(TAG, "four wheeler clicked :" + fourwheelervehicleid);
                    getVehicleData(selectedVehicleTypeId);
                    rllist.setVisibility(View.VISIBLE);

                }
                else{
                    showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));
                   cv_vehicleimage.setVisibility(View.GONE);
                    txt_vehiclename.setVisibility(View.GONE);
                    txt_vehicle_number.setVisibility(View.GONE);
                    btn_vehiclefueltype.setVisibility(View.GONE);
                    rllist.setVisibility(View.GONE);
                    rllist.setVisibility(View.GONE);


                }



            }
            else {
                //Button is OFF

                Log.w(TAG, "Bikes");
                Log.w(TAG, "Two wheeler clicked :" + twowheelervehicleid);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
                selectedVehicleType = "Two Wheeler";
                selectedVehicleTypeId = twowheelervehicleid;
                if(!twowheelerstatus.isEmpty()){
                    selectedVehicleType = "Two Wheeler";
                    selectedVehicleTypeId = twowheelervehicleid;
                    Log.w(TAG,"!toggleButton.isChecked()");

                    parkingListResponseCall(defaultcheckinhours,defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId, selectedVehicleType);
                    getVehicleData(selectedVehicleTypeId);
                    rllist.setVisibility(View.VISIBLE);
                }
                else{
                    showErrorLoading( getResources().getString(R.string.vehicletypeerrormsg));
                    cv_vehicleimage.setVisibility(View.GONE);
                    txt_vehiclename.setVisibility(View.GONE);
                    txt_vehicle_number.setVisibility(View.GONE);
                    btn_vehiclefueltype.setVisibility(View.GONE);
                    rllist.setVisibility(View.GONE);

                }

                            }

        });


        //callMethodEveryFiveMins();



        return view;
    }




    @Override
    public void onResume() {
        super.onResume();
    }

    private void googleApiConnected() {

        googleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(mContext)).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();

    }
    private void checkLocation() {
        try {
            LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ignored) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ignored) {
            }

            if (!gps_enabled && !network_enabled) {

                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getMyLocation();
                }

            } else {
                getLatandLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();

        Log.w(TAG,"onLocationChanged : "+" latitude : "+latitude+ " longitude : "+longitude);






    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        permissionChecking();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
    private void permissionChecking() {
        if (mContext != null) {
            if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 5);

            } else {

                checkLocation();
            }
        }
    }
    public void getMyLocation() {

        if (googleApiClient != null) {

            if (googleApiClient.isConnected()) {
                if(mContext != null){
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        return;
                    }

                }

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(2000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(result1 -> {
                    Status status = result1.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied.
                            // You can initialize location requests here.
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterTime = new SimpleDateFormat("HH");
                            Date c = Calendar.getInstance().getTime();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            requestCheckinDate = dateFormat.format(c);
                            requestCheckoutDate = dateFormat.format(c);

                            Calendar calendar = Calendar.getInstance();

                            int Hr24 = calendar.get(Calendar.HOUR_OF_DAY);


                            checkinhours = Hr24+1;
                            if (checkinhours == 23) {
                                checkouthours = 0;
                            } else {
                                checkouthours = Hr24 + 2;
                            }


                            Log.w(TAG,"getMyLocation checkinhours: "+checkinhours+"checkouthours: "+checkouthours);

                            if(mContext != null) {
                                if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {

                                    vehicleTypeGetListResponseCall();
                                }
                            }

                            Handler handler = new Handler();
                            int delay = 1000; //milliseconds

                            handler.postDelayed(new Runnable() {
                                @SuppressLint("LongLogTag")
                                public void run() {
                                    //do something
                                    Log.w(TAG, "getMyLocation-->");

                                    //parkingListResponseCall(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate, selectedVehicleTypeId);


                                }
                            }, delay);


                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
            }


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        getMyLocation();
                        break;
                }
                break;
        }
    }


    @SuppressLint("LongLogTag")
    public void parkingListResponseCall(String checkintime, String checkouttime, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId, String selectedVehicleType) {
        Log.w(TAG, "ParkingListResponse" + "  selectedVehicleType : "+selectedVehicleType + " selectedVehicleTypeId : "+selectedVehicleTypeId);

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingListResponse> call = apiInterface.parkingListResponseCall(RestUtils.getContentType(), parkingListRequest(checkintime, checkouttime, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<ParkingListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ParkingListResponse> call, @NotNull Response<ParkingListResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingListResponse" + new Gson().toJson(response.body()));

                if(mMap != null){
                    mMap.clear();
                }


                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        APIClient.selectedVehicleTypeId = selectedVehicleTypeId;
                        dataBeanList = response.body().getData();
                        if (dataBeanList.size() > 0) {
                            Log.w(TAG, "dataBeanList Size : " + dataBeanList.size());
                            Log.w(TAG, "selectedVehicleType" + HomeFragmentParking.this.selectedVehicleType);
                            stratdate = response.body().getBooking_data().getStart_date();
                            enddate = response.body().getBooking_data().getEnd_date();
                            strattime = response.body().getBooking_data().getStart_time();
                            endtime = response.body().getBooking_data().getEnd_time();

                            Log.w(TAG, "Staticvalues--->" + "stratdate : " + stratdate + "enddate : " + enddate + "strattime : " + strattime + "endtime : " + endtime);


                            viewpageData(dataBeanList,selectedVehicleType);


                        }

                    } else {
                        showErrorLoadingParking(response.body().getMessage());
                        Log.w(TAG, "ErrorMessage " + response.body().getMessage());
                    }


                }


            }


            @Override
            public void onFailure(@NotNull Call<ParkingListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "ParkingListResponseflr" + t.getMessage());
            }
        });

    }

    @SuppressLint("LongLogTag")
    private ParkingListRequest parkingListRequest(String checkin_time, String checkout_time, String CheckinDate, String CheckoutDate, String selectedVehicleTypeId) {
        /*
         * Location_lat : 13.133130
         * Location_long : 80.245290
         * Vehicletype_id : 5f0c0d092f857d66950cf260
         * start_dates : 2020-08-16
         * end_dates : 2020-08-16
         * start_itme : 09
         * end_time : 10
         */

        Log.w(TAG,"ParkingListRequest-->"+"checkout_time: "+checkin_time+" "+"checkout_time : "+checkout_time);

        String CityName = null;
        String fromactivity = null;
        double lat = 0, lon = 0;

         if(getArguments() != null) {
            CityName = getArguments().getString("cityname");
            fromactivity = getArguments().getString("fromactivity");
            lat = getArguments().getDouble("lat");
            lon = getArguments().getDouble("lon");

        }
        if(isCurrentlocationClick){
            Log.w(TAG,"isCurrentLocation-->"+"lat : "+latitude+" lon : "+longitude);

        }else if (fromactivity != null && fromactivity.equalsIgnoreCase("ParkingPlacesSearchActivity")) {
            latitude = lat;
            longitude = lon;
            if (latitude != 0.0 && longitude != 0.0) {
                gotoLocation(latitude, longitude);
            }
            if (CityName != null) {
                tv_searchlocationaddress.setText(CityName);

            }
            Log.w(TAG, "if----->" + "CityName: " + CityName + "latitude : " + latitude + "longitude:" + longitude);
        }
        else {
            Log.w(TAG, "ParkingListRequest:" + gpsTracker.getLatitude() + " " + gpsTracker.getLongitude());
            if (gpsTracker.getLatitude() != 0.0 && gpsTracker.getLongitude() != 0.0) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
            }

            Log.w(TAG, "else----->" + "CityName: " + CityName + "latitude : " + latitude + "longitude:" + longitude);

        }




        ParkingListRequest parkingListRequest = new ParkingListRequest();
        parkingListRequest.setLocation_lat(String.valueOf(latitude));
        parkingListRequest.setLocation_long(String.valueOf(longitude));
        parkingListRequest.setVehicletype_id(selectedVehicleTypeId);
        parkingListRequest.setStart_dates(CheckinDate);
        parkingListRequest.setEnd_dates(CheckoutDate);
        parkingListRequest.setStart_itme(checkin_time);
        parkingListRequest.setEnd_time(checkout_time);
        Log.w(TAG, "ParkingListRequest" + new Gson().toJson(parkingListRequest));

        str_checkindate = CheckinDate;
        str_checkoutdate = CheckoutDate;

        defaultcheckinhours = checkin_time;
        defaultcheckouthours = checkout_time;
        requestCheckinDate = CheckinDate;
         requestCheckoutDate = CheckoutDate;


        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");

        Date datestart = null, dateend = null;
        try {
            if(requestCheckinDate != null && requestCheckoutDate != null ) {
                datestart = inputFormat.parse(requestCheckinDate);
                dateend = inputFormat.parse(requestCheckoutDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (datestart != null) {
            bookingstartdate = outputFormat.format(datestart);
        }

        if (dateend != null) {
            bookingenddate = outputFormat.format(dateend);
        }

            strtotalHours = String.valueOf(hours);
            if(!strtotalHours.isEmpty() && strtotalHours.equalsIgnoreCase("0")){
                strtotalHours = "1";
            }
            strdays = String.valueOf(days);

        Log.w(TAG, "str_checkintime-->" + str_checkintime + "str_checkoutime :" + str_checkoutime);

        str_checkintime = checkin_time;
        str_checkoutime = checkout_time;


        String selectedDateandTime = bookingstartdate + ", " + str_checkintime + " to " +
                bookingenddate + ", " + str_checkoutime + "(" + strdays + " Day " + strtotalHours+" Hours" + ")";
        Log.w(TAG, "static strtotalHours--->" + strtotalHours);
        txt_bookingdateandtime.setVisibility(View.VISIBLE);
        txt_bookingdateandtime.setText(selectedDateandTime);
        txt_bookingdateandtime.setTextSize(8);
        return parkingListRequest;
    }


    private void viewpageData(List<ParkingListResponse.DataBean> dataBeanList, String selectedVehicleType) {
        Log.w(TAG, "viewpageData "+"selectedVehicleType : "+selectedVehicleType);
        if (dataBeanList.size() > 0) {
            insertMarkers(dataBeanList);
            tv_norecords.setVisibility(View.GONE);
            tabLayout.setupWithViewPager(viewPager, true);
            viewPagerParkingOptionsAdapter = new ViewPagerParkingOptionsAdapter(mContext, dataBeanList, selectedVehicleType, customerVehicleDataBeanList,this);
            //viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(viewPagerParkingOptionsAdapter);
            /*After setting the adapter use the timer */

            if (currentPage == dataBeanList.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, false);

        } else {
            tv_norecords.setVisibility(View.VISIBLE);
        }


    }

    @SuppressLint("LongLogTag")
    private void insertMarkers(List<ParkingListResponse.DataBean> dataBeanList) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        Log.w(TAG,"insertMarkers : "+dataBeanList.size()+" Json : "+new Gson().toJson(dataBeanList));
        ArrayList<LatLng> latlongs = new ArrayList<>();
        for (int i = 0; i < dataBeanList.size(); i++) {
            double currentlat = dataBeanList.get(i).getParking_details_lat();
            double currentlon = dataBeanList.get(i).getParking_details_long();
            String reachtime = dataBeanList.get(i).getParking_reach_time() + " Mins";

            latlongs.add(new LatLng(currentlat, currentlon));


            for (LatLng ll : latlongs) {
                if(mMap != null) {
                    mMap.addMarker(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_logo_small_black)).visible(true).title(reachtime)).showInfoWindow();
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(ll).zoom(zoomLevel).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }

        //Bundle extras = getIntent().getExtras();
        if (getArguments() != null) {
            CityName = getArguments().getString("cityname");
            fromactivity = getArguments().getString("fromactivity");
            lat = getArguments().getDouble("lat");
            lon = getArguments().getDouble("lon");

        }
        if(isCurrentlocationClick){
            if (latitude != 0.0 && longitude != 0.0) {
                gotoLocation(latitude, longitude);
                Log.w(TAG, "if-----> isCurrentlocationClick" + "latitude : " + latitude + "longitude:" + longitude);

            }
        }
        else if (fromactivity != null && fromactivity.equalsIgnoreCase("ParkingPlacesSearchActivity")) {
            latitude = lat;
            longitude = lon;
            if (latitude != 0.0 && longitude != 0.0) {
                gotoLocation(latitude, longitude);
                Log.w(TAG, "if----->" + "latitude : " + latitude + "longitude:" + longitude);

            }

        } else {
            gotoCurrentLocation();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_mapviewclick:
                rllist.setVisibility(View.VISIBLE);
                ll_bookingfor.setVisibility(View.VISIBLE);
                fl_parkingpage.setVisibility(View.VISIBLE);
                ll_mapview.setVisibility(View.GONE);

                break;

            case R.id.ll_listviewclick:
                rllist.setVisibility(View.GONE);
                ll_bookingfor.setVisibility(View.GONE);
                fl_parkingpage.setVisibility(View.GONE);
                ll_mapview.setVisibility(View.VISIBLE);
                setView();
                break;

            case R.id.img_currentlocation:
                //googleApiConnected();
                tv_searchlocationaddress.setText("");
                isCurrentlocationClick = true;
                gotoCurrentLocation();
                parkingListResponseCall(defaultcheckinhours,defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId, selectedVehicleType);

                break;

            case R.id.ll_bookingdate:
                checkinDate = null;
                checkoutDate = null;
                gotoBookingDateandTime();
                break;
        }
    }

    @SuppressLint({"LongLogTag", "SetTextI18n", "NewApi"})
    private void gotoBookingDateandTime() {
        LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.date_time_picker_parking, null);
        bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(view);




        LinearLayout ll_checkindate = bottomSheetDialog.findViewById(R.id.ll_checkindate);
        LinearLayout ll_checkoutdate = bottomSheetDialog.findViewById(R.id.ll_checkoutdate);
        LinearLayout ll_checkintime = bottomSheetDialog.findViewById(R.id.ll_checkintime);
        LinearLayout ll_checkoutime = bottomSheetDialog.findViewById(R.id.ll_checkoutime);

        txt_checkin_date = bottomSheetDialog.findViewById(R.id.txt_checkin_date);
        txt_checkout_date = bottomSheetDialog.findViewById(R.id.txt_checkout_date);
        txt_checkin_time = bottomSheetDialog.findViewById(R.id.txt_checkin_time);
        txt_checkout_time = bottomSheetDialog.findViewById(R.id.txt_checkout_time);

        txt_selecteddate_time = bottomSheetDialog.findViewById(R.id.txt_selecteddate_time);
        //TextView txt_noofhours = bottomSheetDialog.findViewById(R.id.txt_noofhours);

        Button btn_done = bottomSheetDialog.findViewById(R.id.btn_done);

         rl_datepicker = bottomSheetDialog.findViewById(R.id.rl_datepicker);
         rl_timepicker = bottomSheetDialog.findViewById(R.id.rl_timepicker);

        rl_datepicker.setVisibility(View.INVISIBLE);
        rl_timepicker.setVisibility(View.INVISIBLE);


        String selectedDateandTime = bookingstartdate + ", " + str_checkintime + " to " +
                bookingenddate + ", " + str_checkoutime + "(" + strdays + " Day " + strtotalHours+" Hours" + ")";
        Log.w(TAG,"selectedDateandTime--->"+selectedDateandTime);
        txt_selecteddate_time.setText(selectedDateandTime);
        txt_selecteddate_time.setTextSize(12);

        txt_checkin_date.setText(bookingstartdate);
        txt_checkout_date.setText(bookingenddate);
        txt_checkin_time.setText(str_checkintime);
        txt_checkout_time.setText(str_checkoutime);


        checkinTime = str_checkintime;
        checkoutTime = str_checkoutime;

        checkinDate = str_checkindate;
        checkoutDate = str_checkoutdate;
        Log.w(TAG,"gotoBookingDateandTime : "+" checkinDate : "+checkinDate+" checkoutDate :"+checkoutDate+" checkinTime : "+checkinTime +" checkoutTime : "+checkoutTime);















        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM", Locale.getDefault());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");



        ImageView img_close = bottomSheetDialog.findViewById(R.id.img_close);
        if (img_close != null) {
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkindatechange = null;
                    checkoutdatechange = null;
                    checkindateplusonehour = null;
                    checkintimeplusonehour = null;
                    bottomSheetDialog.dismiss();
                }
            });
        }



      //  SimpleDateFormat df = new SimpleDateFormat("dd MMM", Locale.getDefault());
       // @SuppressLint("SimpleDateFormat")
      //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //formattedDate = df.format(c);
        //formattedDate1 = simpleDateFormat.format(c);
        //checkinDate = simpleDateFormat.format(c);
       // checkoutDate = simpleDateFormat.format(c);









     /*   Calendar calendar = Calendar.getInstance();
        int Hr24 = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);*/




        //checkinTime = defaultcheckinhours;
       // checkoutTime = defaultcheckinhours;




        strdays = "0";



        bottomSheetDialog.show();

        if (ll_checkindate != null) {
            ll_checkindate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckedInDate(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);

                }
            });



        }
        if (ll_checkoutdate != null) {

            ll_checkoutdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckedOutDate(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                }
            });
        }
        if (ll_checkintime != null) {
            ll_checkintime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckInTime(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                }
            });
        }
        if (ll_checkoutime != null) {
            ll_checkoutime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCheckOutTime(v);
                    ll_checkindate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutdate.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkintime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray);
                    ll_checkoutime.setBackgroundResource(R.drawable.rectangle_corner_bg_gray_withcorner);
                }


            });
        }



        if (btn_done != null) {
            btn_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* if(checkin_date == null){
                        checkin_date = requestCheckinDate;
                    }
                    if(checkout_date == null){
                        checkout_date = requestCheckoutDate;
                    }
                    if(checkin_time == null){
                      //  checkin_time = defaultcheckinhours;
                    }
                    if(checkout_time == null){
                        checkout_time = defaultcheckouthours;
                    }*/
                    if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
                        try{
                            checkTimesResponseCall(txt_checkin_time.getText().toString(), txt_checkout_time.getText().toString(), checkinDate, checkoutDate);
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }


                }
            });
        }
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @SuppressLint("NewApi")
    private void selectCheckedInDate(View view) {
        setupFullHeight(bottomSheetDialog);
        rl_timepicker.setVisibility(View.GONE);
        rl_datepicker.setVisibility(View.VISIBLE);

        datePicker = bottomSheetDialog.findViewById(R.id.datePicker); // initiate a date picker
        datePicker.setVisibility(View.VISIBLE);
        datePicker_checkout = bottomSheetDialog.findViewById(R.id.datePicker_checkout); // initiate a date picker
        datePicker_checkout.setVisibility(View.GONE);
        long now = System.currentTimeMillis() - 1000;
        Objects.requireNonNull(datePicker).setMinDate(now);
        datePicker.setMaxDate(now+(1000*60*60*24*13)); //After 13 Days from Now

        if(checkindatechange != null && !checkindatechange.isEmpty()){
            String[] separated = checkindatechange.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker != null) {
                datePicker.updateDate(year,month-1,day);
            }
        }else if(str_checkindate != null && !str_checkindate.isEmpty()) {

            String[] separated = str_checkindate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker != null) {
                datePicker.updateDate(year,month-1,day);
            }
        }
        else if(checkinDate != null && !checkinDate.isEmpty()) {
            String[] separated = checkinDate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker != null) {
                datePicker.updateDate(year,month-1,day);
            }
        }
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month, date;

                date = Integer.toString(dayOfMonth);
                month = Integer.toString(monthOfYear + 1);

                if (month.length() == 1) {

                    month = "0" + (monthOfYear + 1);
                }
                if (date.length() == 1) {

                    date = "0" + dayOfMonth;
                }



                checkinDate = year + "-" + month + "-" + date;
                checkoutDate = checkinDate;
                checkindatechange =checkinDate;
                Log.w(TAG,"setOnDateChangedListener : "+checkinDate);

                convertDateFormayyyyMMddtoddMMM(checkinDate);

                try{
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                    String checkoutdateandtime = checkoutDate+" "+checkoutTime;
                    String checkindateandtime = checkinDate+" "+checkinTime;
                    Date dateCheckin = simpleDateFormat.parse(checkindateandtime);
                    Date dateCheckout = simpleDateFormat.parse(checkoutdateandtime);
                    if(dateCheckin != null && dateCheckout != null) {
                        printDifference(dateCheckin, dateCheckout);
                    }

                }catch (ParseException e){
                    e.printStackTrace();

                }





            }
        });
    }

    private void convertDateFormayyyyMMddtoddMMM(String inputDate) {
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
            String outputDateStr = outputFormat.format(dateformat);
            txt_checkin_date.setText(outputDateStr);
            txt_checkout_date.setText(outputDateStr);



        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void selectCheckedOutDate(View view) {
        setupFullHeight(bottomSheetDialog);
        rl_timepicker.setVisibility(View.GONE);
        rl_datepicker.setVisibility(View.VISIBLE);

        datePicker = bottomSheetDialog.findViewById(R.id.datePicker); // initiate a date picker
        datePicker.setVisibility(View.GONE);
        datePicker_checkout = bottomSheetDialog.findViewById(R.id.datePicker_checkout); // initiate a date picker
        datePicker_checkout.setVisibility(View.VISIBLE);
        long now = System.currentTimeMillis() - 1000;

        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(checkinDate);

            long startDate = 0;
            if (date != null) {
                startDate = date.getTime();
            }
            Objects.requireNonNull(datePicker_checkout).setMinDate(startDate);
            datePicker_checkout.setMaxDate(startDate+(1000*60*60*24*13)); //After 13 Days from Now

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.w(TAG,"selectCheckedOutDate--->"+str_checkoutdate);


        if(checkindateplusonehour != null && !checkindateplusonehour.isEmpty()){
            String[] separated = checkindateplusonehour.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker_checkout != null) {
                datePicker_checkout.updateDate(year,month-1,day);
            }
        }
        else if(str_checkoutdate != null && !str_checkoutdate.isEmpty()) {
            checkoutDate = str_checkoutdate;
            String[] separated = str_checkoutdate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker_checkout != null) {
                datePicker_checkout.updateDate(year,month-1,day);
            }
        }
        else if(checkoutDate != null && !checkoutDate.isEmpty()) {

            String[] separated = checkoutDate.split("-");
            String stryear = separated[0];
            String strmonth = separated[1];
            String strday = separated[2];
            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);


            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            int day = Integer.parseInt(strday);
            if (datePicker_checkout != null) {
                datePicker_checkout.updateDate(year,month-1,day);
            }
        }


        datePicker_checkout.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Log.w(TAG,"selectCheckedOutDate setOnDateChangedListener : "+"year : "+year+" monthOfYear : "+monthOfYear+" dayOfMonth : "+dayOfMonth);
                String month, date;

                date = Integer.toString(dayOfMonth);
                month = Integer.toString(monthOfYear + 1);

                if (month.length() == 1) {

                    month = "0" + (monthOfYear + 1);
                }
                if (date.length() == 1) {

                    date = "0" + dayOfMonth;
                }

                checkoutDate = year + "-" + month + "-" + date;
                checkoutdatechange = checkoutDate;
                Log.w(TAG,"setOnDateChangedListener : "+checkoutDate);

                convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);

                try{
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                    String checkoutdateandtime = checkoutDate+" "+checkoutTime;
                    String checkindateandtime = checkinDate+" "+checkinTime;
                    Date dateCheckin = simpleDateFormat.parse(checkindateandtime);
                    Date dateCheckout = simpleDateFormat.parse(checkoutdateandtime);

                    GetTimeWithAMPMFromTime(checkinTime);
                    convertTimeWithAMPMFromTime(checkoutTime);

                    String checkindateandtime24hrs = checkinDate+" "+checkintime24hrs;
                    String checkoutdateandtime24hrs = checkoutDate+" "+checkouttime24hrs;

                    Log.w(TAG,"selectCheckedOutDate : "+" checkindateandtime24hrs : "+checkindateandtime24hrs+" checkoutdateandtime24hrs : "+checkoutdateandtime24hrs);

                    Log.w(TAG,"selectCheckedOutDate conditions : "+checkinDate.equals(checkoutDate));
                    Log.w(TAG,"selectCheckedOutDate conditions1 : "+(checkindateandtime24hrs.compareTo(checkoutdateandtime24hrs)>0));

                    if(checkinDate.equals(checkoutDate) &&  checkindateandtime24hrs.compareTo(checkoutdateandtime24hrs)>0){

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");

                        Date date1 = sdf.parse(checkindateandtime);
                        final Calendar calendar = Calendar.getInstance();

                        if (date1 != null) {
                            calendar.setTime(date1);
                        }
                        calendar.add(Calendar.HOUR, 1);

                        String CheckinDateandTime = sdf.format(calendar.getTime());

                        checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);
                        Log.w(TAG,"selectCheckedOutDate--->  if "+"checkoutDate : "+checkoutDate+" checkoutTime :"+checkoutTime);

                        txt_checkout_time.setText(checkoutTime);
                        if(checkoutDate != null && !checkoutDate.isEmpty()) {

                            String[] separated = checkoutDate.split("-");
                            String stryear = separated[0];
                            String strmonth = separated[1];
                            String strday = separated[2];
                            Log.w(TAG,"stryear "+stryear+" strmonth : "+strmonth+" strday: "+strday);
                            int year1 = Integer.parseInt(stryear);
                            int month1 = Integer.parseInt(strmonth);
                            int day = Integer.parseInt(strday);
                            if (datePicker_checkout != null) {
                                datePicker_checkout.updateDate(year1,month1-1,day);
                            }
                        }


                    }else{
                        if(dateCheckin != null && dateCheckout != null) {
                            printDifference(dateCheckin, dateCheckout);
                        }
                    }


                }catch (ParseException e){
                    e.printStackTrace();

                }




            }
        });
    }
    private void convertCheckoutDateFormatyyyyMMddtoddMMM(String inputDate) {
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
            String outputDateStr = outputFormat.format(dateformat);
            txt_checkout_date.setText(outputDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void selectCheckInTime(View view) {
        setupFullHeight(bottomSheetDialog);
        Log.w(TAG,"selectCheckInTime--->");
         rl_datepicker.setVisibility(View.GONE);
         rl_timepicker.setVisibility(View.VISIBLE);
         timePicker = bottomSheetDialog.findViewById(R.id.timePicker); // initiate a time picker
         timePicker.setVisibility(View.VISIBLE);
         timePicker_checkout = bottomSheetDialog.findViewById(R.id.timePicker_checkout); // initiate a time picker
         timePicker_checkout.setVisibility(View.GONE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        String currentDateandTime = sdf.format(new Date());
        String currenttime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
        String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
        String[] separated = currenttime.split(":");
        hourssplit = separated[0];
        String minutesampmsplit = separated[1];

        String[] splited = minutesampmsplit.split("\\s+");
        String minutessplit = splited[0];
        int currentmins = Integer.parseInt(minutessplit);




        setTimePickerInterval(timePicker);
        if(currentmins>55){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            String time24 = null;
            try {
                time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(currenttime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String[] separated1 = time24.split(":");
            String hourssplit = separated1[0];
            int currenthour = Integer.parseInt(hourssplit);
            timePicker.setCurrentHour(currenthour+1);

        }
        currentmin = (currentmins+4)/5;
        timePicker.setCurrentMinute(currentmin);
        Log.w(TAG,"currentmin--><"+currentmin);

        timePicker.setIs24HourView(false);


        Log.w(TAG,"str_checkintime--->"+str_checkintime);
        GetTimeWithAMPMFromTime(str_checkintime);
        Log.w(TAG,"checkintime24hrs--->"+checkintime24hrs);


        String[] separated11 = checkintime24hrs.split(":");
        String hourssplit1 = separated11[0];
        int startcheckouthours = Integer.parseInt(hourssplit1);
        Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
        timePicker.setCurrentHour(startcheckouthours);

        String minutessplit1 = separated11[1];
        int currentmins1 = Integer.parseInt(minutessplit1.trim());
        Log.w(TAG,"currentmins1-->"+currentmins1);
        currentmin = (currentmins1+4)/5;
        timePicker.setCurrentMinute(currentmin);



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute){
                minute =(minute*5);

                String selectedhrs24;
                if(hourOfDay < 10){
                    selectedhrs24 = "0"+hourOfDay;
                }else{
                    selectedhrs24 = String.valueOf(hourOfDay);
                }

                Log.w(TAG,"selectCheckInTime onTimeChanged : "+" hourOfDay : "+hourOfDay+" minute : "+minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                String currentDateandTime = sdf.format(new Date());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String currentDateandTime24hrs = simpleDateFormat.format(new Date());

               // Log.w(TAG,"selectCheckInTime setOnTimeChangedListener currentDateandTime : "+currentDateandTime);
                checkinTime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                String currenttime = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
               // Log.w(TAG,"selectCheckInTime : "+" currenttime : "+currenttime+" currentdate : "+currentdate);
                String selectedhours,selectedminutes;
                String format;

                if (hourOfDay == 0) {

                    hourOfDay += 12;

                    format = "AM";
                }
                else if (hourOfDay == 12) {

                    format = "PM";

                }
                else if (hourOfDay > 12) {

                    hourOfDay -= 12;

                    format = "PM";

                }
                else {

                    format = "AM";
                }

                if(hourOfDay < 10){
                    selectedhours = "0"+hourOfDay;
                }
                else {
                    selectedhours = String.valueOf(hourOfDay);
                }
                if(minute < 10){
                    selectedminutes = "0"+minute;
                }
                else {
                    selectedminutes = String.valueOf(minute);
                }

               // Log.w(TAG,"selectCheckInTime selectedhours : "+selectedhours+" selectedminutes : "+selectedminutes+" format : "+format);
                checkinTime = selectedhours+":"+selectedminutes+" "+format;
             //  Log.w(TAG,"selectCheckInTime checkinTime : "+checkinTime);



                String checkindateandtime = checkinDate+" "+checkinTime;
                Log.w(TAG,"checkindateandtime : "+checkindateandtime);
                getdateandtime(checkindateandtime);
                 txt_checkin_time.setText(checkinTime);

                 checkintime24hrs = selectedhrs24+":"+selectedminutes;
                 //Log.w(TAG,"checkintime24hrs : "+checkintime24hrs);

                String checkindateandtime24hrs = checkinDate+" "+checkintime24hrs;

                Log.w(TAG,"checkindateandtime24hrs : "+ checkindateandtime24hrs +"checkintime24hrs : "+checkintime24hrs);
                try{

                    Log.w(TAG,"currentDateandTime24hrs : "+currentDateandTime24hrs+" checkindateandtime24hrs : "+checkindateandtime24hrs);
                    Log.w(TAG,"Condition : "+(currentDateandTime24hrs.compareTo(checkindateandtime24hrs)>0));

                    if(checkinDate.equals(currentdate) &&checkintime24hrs.equalsIgnoreCase("00:00") ){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(Objects.requireNonNull(simpleDateFormat1.parse(checkinDate)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 1);  // number of days to add
                        String checkinDateandtime = sdf.format(c.getTime());  // dt is now the new date

                        String time = checkinDateandtime.substring(checkinDateandtime.indexOf(' ') + 1);
                        String date =  checkinDateandtime.substring(0, checkinDateandtime.indexOf(' '));

                        checkindateandtime24hrs = date+" "+checkintime24hrs;
                        checkinDate = date;
                        checkinTime = time;
                        convertcinDateFormatyyyyMMddtoddMMM(checkinDate);


                        checkindateandtime = checkinDate+" "+checkinTime;

                        Date date1 = sdf.parse(checkindateandtime);
                        final Calendar calendar = Calendar.getInstance();

                        if (date1 != null) {
                            calendar.setTime(date1);
                        }
                        calendar.add(Calendar.HOUR, 1);

                        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                        String CheckinDateandTime = sdf.format(calendar.getTime());

                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                        checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);
                        Log.w(TAG,"selectCheckInTime---> if "+"checkoutDate : "+checkoutDate+" checkoutTime :"+checkoutTime);


                        txt_checkout_time.setText(checkoutTime);

                        convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);


                    }
                    else if(checkinDate.equals(currentdate) &&  currentDateandTime24hrs.compareTo(checkindateandtime24hrs)>0){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                        String time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(currenttime)));
                        //Log.w(TAG, "time24 : " + time24);
                        String[] separated = time24.split(":");
                        String hourssplit = separated[0];
                        Log.w(TAG,"hourssplit : "+hourssplit);
                        String minutessplit = separated[1];
                        int currentmins = Integer.parseInt(minutessplit);
                        Log.w(TAG,"currentmin12: "+currentmins);


                        Log.w(TAG,"condition mins-->"+(currentmins>55));
                        if(currentmins>55){
                            int currenthour = Integer.parseInt(hourssplit);
                            timePicker.setCurrentHour(currenthour+1);
                            Log.w(TAG,"setCurrentHour currenthouraddone : "+currenthour+1);

                        }else{
                            timePicker.setCurrentHour(Integer.valueOf(hourssplit));
                            Log.w(TAG,"setCurrentHour hourssplit : "+hourssplit);


                        }
                        timePicker.setCurrentMinute((currentmins+4)/5);
                        Log.w(TAG,"setCurrentMinute  : "+(currentmins+4)/5);


                    }
                    else{
                        try {
                            Date dateCurrent = sdf.parse(currentDateandTime);
                            Date dateCheckin = sdf.parse(checkindateandtime);

                            Log.w(TAG,"dateCurrent : "+dateCurrent+" currentDateandTime : "+currentDateandTime);
                            Log.w(TAG,"dateCheckin : "+dateCheckin+" checkindateandtime : "+checkindateandtime);
                            if(dateCurrent != null && dateCheckin != null){
                                Log.w(TAG,"DATEconditions : "+dateCheckin.before(dateCurrent));
                                if(dateCheckin.before(dateCurrent)){
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar c = Calendar.getInstance();
                                    try {
                                        c.setTime(Objects.requireNonNull(simpleDateFormat1.parse(checkinDate)));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    c.add(Calendar.DATE, 1);  // number of days to add
                                    String checkinDateandtime = sdf.format(c.getTime());  // dt is now the new date

                                    String time = checkinDateandtime.substring(checkinDateandtime.indexOf(' ') + 1);
                                    String date =  checkinDateandtime.substring(0, checkinDateandtime.indexOf(' '));
                                    Log.w(TAG,"Conditions if : "+"date : "+date+" time :"+time);
                                    checkindateandtime24hrs = date+" "+checkintime24hrs;
                                    checkinDate = date;
                                    checkinTime = time;
                                    convertcinDateFormatyyyyMMddtoddMMM(checkinDate);


                                    checkindateandtime = checkinDate+" "+checkinTime;

                                    Date date1 = sdf.parse(checkindateandtime);
                                    final Calendar calendar = Calendar.getInstance();

                                    if (date1 != null) {
                                        calendar.setTime(date1);
                                    }
                                    calendar.add(Calendar.HOUR, 1);

                                    // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                                    String CheckinDateandTime = sdf.format(calendar.getTime());

                                    // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                                    // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                                    checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                                    checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);
                                    Log.w(TAG,"selectCheckInTime---> else if "+"checkoutDate : "+checkoutDate+" checkoutTime :"+checkoutTime);

                                    txt_checkout_time.setText(checkoutTime);

                                    String checkoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();

                                    Date dateCheckout = sdf.parse(checkoutdateandtime);
                                    if(dateCheckin != null && dateCheckout != null) {
                                        printDifference(dateCheckin, dateCheckout);
                                    }

                                    convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);

                                }else {
                                    //checkinDate = currentdate;
                                    Log.w(TAG,"Conditions else : "+checkinDate);

                                    try {


                                        checkindateandtime = checkinDate+" "+checkinTime;

                                        Date date1 = sdf.parse(checkindateandtime);
                                        final Calendar calendar = Calendar.getInstance();

                                        if (date1 != null) {
                                            calendar.setTime(date1);
                                        }
                                        calendar.add(Calendar.HOUR, 1);

                                        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                                        String CheckinDateandTime = sdf.format(calendar.getTime());

                                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
                                        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

                                        checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
                                        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);
                                         checkindateplusonehour = checkoutDate;
                                         checkintimeplusonehour = checkoutTime;
                                        Log.w(TAG,"selectCheckInTime---> else else "+"checkoutDate : "+checkoutDate+" checkoutTime :"+checkoutTime);

                                        txt_checkout_time.setText(checkoutTime);
                                        String checkoutdateandtime = checkoutDate+" "+txt_checkout_time.getText().toString();

                                        Date dateCheckout = sdf.parse(checkoutdateandtime);
                                        if(dateCheckin != null && dateCheckout != null) {
                                            printDifference(dateCheckin, dateCheckout);
                                        }


                                        convertCheckoutDateFormatyyyyMMddtoddMMM(checkoutDate);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }



                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }catch(ParseException e){
                    e.printStackTrace();
                }









                //@SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                final Date date;


            }


        });
    }
    private void selectCheckOutTime(View view) {
        setupFullHeight(bottomSheetDialog);
        Log.w(TAG,"selectCheckOutTime-->");
         rl_datepicker.setVisibility(View.GONE);
         rl_timepicker.setVisibility(View.VISIBLE);
         timePicker = bottomSheetDialog.findViewById(R.id.timePicker); // initiate a time picker
         timePicker.setVisibility(View.GONE);
         timePicker_checkout = bottomSheetDialog.findViewById(R.id.timePicker_checkout); // initiate a time picker
         timePicker_checkout.setVisibility(View.VISIBLE);
         timePicker_checkout.setIs24HourView(false);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
       // String currentDateandTime = sdf.format(new Date());


        String checkindateandtime = checkinDate+" "+txt_checkin_time.getText().toString();
        Date date1 = null;
        try {
            date1 = sdf.parse(checkindateandtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Calendar calendar = Calendar.getInstance();

        if (date1 != null) {
            calendar.setTime(date1);
        }
        calendar.add(Calendar.HOUR, 1);

        // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
        String CheckinDateandTime = sdf.format(calendar.getTime());

        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Date: " + CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' ')));
        // Log.w(TAG,"selectCheckInTime CheckinDateandTime Time: " + CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1));

       // checkoutDate =  CheckinDateandTime.substring(0, CheckinDateandTime.indexOf(' '));
        checkoutTime =  CheckinDateandTime.substring(CheckinDateandTime.indexOf(' ') + 1);

        String checkoutdateandtime = checkoutDate+" "+checkoutTime;
        Log.w(TAG,"selectCheckOutTime checkoutdateandtime -->"+checkoutdateandtime);

        convertTimeWithAMPMFromTime(checkoutTime.trim());

        String checkouttime = checkoutdateandtime.substring(checkoutdateandtime.indexOf(' ') + 1);
        String checkoutdate =  checkoutdateandtime.substring(0, checkoutdateandtime.indexOf(' '));

        int currentmins = 0;
        Log.w(TAG,"checkouttime24hrs--->"+checkouttime24hrs+" str_checkoutime : "+str_checkoutime);

        if(checkintimeplusonehour != null && !checkintimeplusonehour.isEmpty()){
            convertTimeWithAMPMFromTime(checkintimeplusonehour);
            Log.w(TAG,"checkintimeplusonehour--->"+checkintimeplusonehour);
            String[] separated1 = checkouttime24hrs.split(":");
            String hourssplit = separated1[0];
            String minutessplit = separated1[1];
            int startcheckouthours = Integer.parseInt(hourssplit);
            Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
            timePicker_checkout.setCurrentHour(startcheckouthours);
            currentmins = Integer.parseInt(minutessplit);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins+"minutessplit : "+minutessplit);

            timePicker_checkout.setCurrentMinute(currentmins);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins);
        }
        else if(str_checkoutime != null && !str_checkoutime.isEmpty()){
            convertTimeWithAMPMFromTime(str_checkoutime);
            Log.w(TAG,"checkouttime24hrs--->"+checkouttime24hrs);
            String[] separated1 = checkouttime24hrs.split(":");
            String hourssplit = separated1[0];
            String minutessplit = separated1[1];
            int startcheckouthours = Integer.parseInt(hourssplit);
            Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
            timePicker_checkout.setCurrentHour(startcheckouthours);
            currentmins = Integer.parseInt(minutessplit);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins+"minutessplit : "+minutessplit);

            timePicker_checkout.setCurrentMinute(currentmins);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins);

        }
        else if(checkouttime24hrs != null && !checkouttime24hrs.isEmpty()){
            Log.w(TAG,"checkouttime24hrs--->"+checkouttime24hrs);
            String[] separated1 = checkouttime24hrs.split(":");
            String hourssplit = separated1[0];
            String minutessplit = separated1[1];
            int startcheckouthours = Integer.parseInt(hourssplit);
            Log.w(TAG,"selectCheckOutTime startcheckouthours -->"+startcheckouthours);
            timePicker_checkout.setCurrentHour(startcheckouthours);
            currentmins = Integer.parseInt(minutessplit);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins+"minutessplit : "+minutessplit);

            timePicker_checkout.setCurrentMinute(currentmins);
            Log.w(TAG,"selectCheckOutTime currentmins -->"+currentmins);
        }



        //String checkindateandtime = checkinDate+" "+checkinTime;
        String currenttime = checkindateandtime.substring(checkindateandtime.indexOf(' ') + 1);
        String currentdate =  checkindateandtime.substring(0, checkindateandtime.indexOf(' '));
        String[] separated = currenttime.split(":");
        hourssplit = separated[0];



        setTimePickerInterval(timePicker_checkout);
        if(currentmins>55){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            String time24 = null;
            try {
                time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(currenttime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Log.w(TAG, "time24 : " + time24);
            String[] separated2 = time24.split(":");
            String hourssplit1 = separated2[0];
            int currenthour = Integer.parseInt(hourssplit1);
            Log.w(TAG,"selectCheckOutTime currenthour-->"+currenthour);
            timePicker_checkout.setCurrentHour(currenthour+1);

        }
        currentmin = (currentmins+4)/5;
        timePicker_checkout.setCurrentMinute(currentmin);
        timePicker_checkout.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute){
                minute =(minute*5);

                Log.w(TAG,"selectCheckOutTime onTimeChanged : "+" hourOfDay : "+hourOfDay+" minute : "+minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
              //  String currentDateandTime = sdf.format(new Date());

              //  String checkindateandtime = checkinDate+" "+checkinTime;
                //Log.w(TAG,"selectCheckOutTime setOnTimeChangedListener checkindateandtime : "+checkindateandtime);
              //  checkoutTime = currentDateandTime.substring(checkindateandtime.indexOf(' ') + 1);

                String selectedhours,selectedminutes;
                String format;

                if (hourOfDay == 0) {

                    hourOfDay += 12;

                    format = "AM";
                }
                else if (hourOfDay == 12) {

                    format = "PM";

                }
                else if (hourOfDay > 12) {

                    hourOfDay -= 12;

                    format = "PM";

                }
                else {

                    format = "AM";
                }

                if(hourOfDay < 10){
                    selectedhours = "0"+hourOfDay;
                }
                else {
                    selectedhours = String.valueOf(hourOfDay);
                }
                if(minute < 10){
                    selectedminutes = "0"+minute;
                }
                else {
                    selectedminutes = String.valueOf(minute);
                }

                Log.w(TAG,"selectCheckOutTime selectedhours : "+selectedhours+" selectedminutes : "+selectedminutes+" format : "+format);
                checkoutTime = selectedhours+":"+selectedminutes+" "+format;
                Log.w(TAG,"selectCheckOutTime checkoutTime : "+checkoutTime);
                txt_checkout_time.setText(checkoutTime);
                String currentCheckoutdateandtime = checkoutDate+" "+checkoutTime;


                try{

                    Date dateCheckin = sdf.parse(checkindateandtime);
                    Date dateCheckout = sdf.parse(checkoutdateandtime);
                    Date dateCheckoutCurrent = sdf.parse(currentCheckoutdateandtime);

                    if(dateCheckin != null && dateCheckoutCurrent != null) {
                        printDifference(dateCheckin, dateCheckoutCurrent);
                    }


                    Log.w(TAG,"selectCheckOutTime checkoutdateandtime : "+checkoutdateandtime+" currentCheckoutdateandtime : "+currentCheckoutdateandtime);
                    Log.w(TAG,"selectCheckOutTime dateCheckout : "+dateCheckout+" dateCheckoutCurrent : "+dateCheckoutCurrent);
                    Log.w(TAG,"selectCheckOutTime checkinDate : "+checkinDate+" checkoutdate : "+checkoutdate);
                    Log.w(TAG,"selectCheckOutTime Condition : "+dateCheckoutCurrent.before(dateCheckout));
                    if(checkinDate.equals(checkoutdate) &&  dateCheckoutCurrent.before(dateCheckout)){
                        Log.w(TAG,"################################33");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                        String time24 = outFormat.format(Objects.requireNonNull(inFormat.parse(checkouttime)));
                        //Log.w(TAG, "time24 : " + time24);
                        String[] separated = time24.split(":");
                        String hourssplit = separated[0];
                        Log.w(TAG,"hourssplit : "+hourssplit);
                        String minutessplit = separated[1];
                        int currentmins = Integer.parseInt(minutessplit);
                        Log.w(TAG,"currentmin12: "+currentmins);


                        if(currentmins>55){
                            int currenthour = Integer.parseInt(hourssplit);
                            timePicker_checkout.setCurrentHour(currenthour+1);

                        }else{
                            timePicker_checkout.setCurrentHour(Integer.valueOf(hourssplit));

                        }
                        timePicker_checkout.setCurrentMinute((currentmins+4)/5);

                    }


                }catch (ParseException e){
                    e.printStackTrace();
                }

            }


        });
    }



    private void gotoCurrentLocation() {
       // mMap.clear();
        GPSTracker gps = new GPSTracker(mContext);
        LatLng latLng = new LatLng(gps.getLatitude(), gps.getLongitude());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        Log.w(TAG,"gotoCurrentLocation-->"+"lat : "+latitude+" lon : "+longitude);
        if(mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
            MarkerOptions markerOptions = new MarkerOptions().position(Objects.requireNonNull(latLng)).title("");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_logo_small_color));
            mMap.addMarker(markerOptions);
        }



    }

    private void gotoLocation(double latitude, double longitude) {
        Log.w(TAG,"gotoLocation-->"+"lat : "+latitude+" lon : "+longitude);

        // mMap.clear();
        GPSTracker gps = new GPSTracker(mContext);
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        MarkerOptions markerOptions = new MarkerOptions().position(Objects.requireNonNull(latLng)).title("");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_logo_small_color));
        mMap.addMarker(markerOptions);

    }


    private void setView() {
        Log.w(TAG,"setView selectedVehicleType : "+selectedVehicleType+" selectedVehicleTypeId : "+selectedVehicleTypeId);
        if (dataBeanList.size() > 0) {
            rv_parkingoptionslist.setVisibility(View.VISIBLE);
            tv_norecords_parkinglist.setVisibility(View.GONE);
            rv_parkingoptionslist.setLayoutManager(new LinearLayoutManager(mContext));
            rv_parkingoptionslist.setItemAnimator(new DefaultItemAnimator());
            ParkingOptionsListAdapter parkingOptionsListAdapter = new ParkingOptionsListAdapter(mContext, dataBeanList, rv_parkingoptionslist, selectedVehicleType, customerVehicleDataBeanList);
            rv_parkingoptionslist.setAdapter(parkingOptionsListAdapter);

            parkingOptionsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (preferences.getInt(Constants.INBOX_TOTAL, 0) > dataBeanList.size()) {
                        Log.e("haint", "Load More");
                    }


                }


            });
        } else {
            rv_parkingoptionslist.setVisibility(View.GONE);
            tv_norecords_parkinglist.setVisibility(View.VISIBLE);
        }


    }


    @SuppressLint({"LongLogTag", "NewApi"})
    private void getLatandLong() {
        try {
            if (fromactivity != null && fromactivity.equalsIgnoreCase("ParkingPlacesSearchActivity")) {
                latitude = lat;
                longitude = lon;

                if (CityName != null) {
                    tv_searchlocationaddress.setText(CityName);

                }
                if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {

                    vehicleTypeGetListResponseCall();
                }

                if (latitude != 0.0 && longitude != 0.0) {
                    // gotoLocation(latitude, longitude);
                }
                Log.w(TAG, "if----->" + "CityName: " + CityName + " latitude : " + latitude + " longitude:" + longitude);
            }else{
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                } else {
                    GPSTracker gps = new GPSTracker(mContext);
                    // Check if GPS enabled
                    if (gps.canGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        Log.w(TAG, "getLatandLong--->" + "latitude" + " " + latitude + "longitude" + " " + longitude);


                        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {

                            vehicleTypeGetListResponseCall();
                        }



                    }
            }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @SuppressLint("LongLogTag")
    private boolean validateInputs() throws ParseException {

        boolean validate = true;

/*

        if (txt_checkin_date.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please Checkindate", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkout_date.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please Checkoutdate", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkin_time.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please CheckinTime", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (txt_checkout_time.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please Checkouttime", Toast.LENGTH_SHORT).show();
            validate = false;
        }else{
            checkTimesResponseCall(txt_checkin_time.getText().toString(), txt_checkout_time.getText().toString(), checkinDate, checkoutDate);

        }
*/



        return validate;


    }
    @SuppressLint("LongLogTag")
    public void vehicleTypeGetListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleTypeGetListResponse> call = apiInterface.vehicleTypeGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<VehicleTypeGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VehicleTypeGetListResponse> call, @NonNull Response<VehicleTypeGetListResponse> response) {

                if (response.body() != null) {
                    if (200 == response.body().getCode()) {

                        Log.w(TAG, "VehicleTypeGetListResponse" + new Gson().toJson(response.body()));

                        vehicleTypeGetListResponseList = response.body().getData();
                        if (!vehicleTypeGetListResponseList.isEmpty()) {
                            for (int i = 0; i < vehicleTypeGetListResponseList.size(); i++) {
                                String vehicletype = response.body().getData().get(i).getVehicle_Type();
                                String id = response.body().getData().get(i).get_id();
                                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                                if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                                    twowheelervehicleid = response.body().getData().get(i).get_id();
                                    selectedVehicleTypeId = twowheelervehicleid;
                                    selectedVehicleType = "Two Wheeler";
                                    Log.w(TAG, "twowheelervehicleid" + twowheelervehicleid);
                                } else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                                    fourwheelervehicleid = response.body().getData().get(i).get_id();
                                    selectedVehicleTypeId = fourwheelervehicleid;
                                    selectedVehicleType = "Four Wheeler";
                                    Log.w(TAG, "fourwheelervehicleid" + fourwheelervehicleid);
                                }


                            }


                        }
                        getCustomerVehicleandLocationResponseCall();

                    }


                }


            }


            @Override
            public void onFailure(@NonNull Call<VehicleTypeGetListResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "VehicleTypeGetListResponseflr" + t.getMessage());
            }
        });

    }
    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {
                    hideLoading();
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void showErrorLoadingParking(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoadingParking());


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading() {
        try {
            alertDialog.dismiss();
        } catch (Exception ignored) {

        }
    }
    public void hideLoadingParking() {
        try {
            startActivity(new Intent(mContext, ParkingPickUpLocationActivity.class));
            alertDialog.dismiss();

        } catch (Exception ignored) {

        }
    }

    @SuppressLint("LongLogTag")
    public void getCustomerVehicleandLocationResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<GetCustomerVehicleandLocationResponse> call = apiInterface.getCustomerVehicleandLocationResponseCall(RestUtils.getContentType(), getCustomerVehicleandLocationRequest());
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<GetCustomerVehicleandLocationResponse>() {
            @SuppressLint({"SetTextI18n", "LongLogTag", "NewApi"})
            @Override
            public void onResponse(@NotNull Call<GetCustomerVehicleandLocationResponse> call, @NotNull Response<GetCustomerVehicleandLocationResponse> response) {
                Log.w(TAG, "GetCustomerVehicleandLocationResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;
                avi_indicator.smoothToHide();

                if (200 == response.body().getCode()) {
                    customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                    Log.w(TAG, "GetCustomerVehicleandLocationResponse" + " fourwheelervehicleid : "+fourwheelervehicleid);

                    getVehicleData(fourwheelervehicleid);
                    if(!fourwheelerstatus.isEmpty()){
                        Date c = Calendar.getInstance().getTime();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        requestCheckinDate = simpleDateFormat.format(c);
                        requestCheckoutDate = simpleDateFormat.format(c);
                        Calendar calendar = Calendar.getInstance();
                        int Hr24 = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        Log.w(TAG,"getCustomerVehicleandLocationResponseCall minutes"+minutes);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                         currentDateandTime = sdf.format(new Date());
                         Log.w(TAG,"getCustomerVehicleandLocationResponseCall Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
                         Log.w(TAG,"getCustomerVehicleandLocationResponseCall Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
                         defaultcheckinhours = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                         String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
                         selectedVehicleTypeId = fourwheelervehicleid;
                         selectedVehicleType = "Four Wheeler";
                        roundedMins(defaultcheckinhours,currentdate);

                        // defaultcheckouthours = sdfStopTime.format(new Date(System.currentTimeMillis() + 3600000));

                        Log.w(TAG,"getCustomerVehicleandLocationResponseCall if : "+" fromactivity : "+fromactivity);
                        if (fromactivity != null && fromactivity.equalsIgnoreCase("ParkingPlacesSearchActivity")) {
                            latitude = lat;
                            longitude = lon;
                            Log.w(TAG,"getCustomerVehicleandLocationResponseCall : "+" latitude : "+latitude+ " longitude : "+longitude);


                            if (CityName != null) {
                                tv_searchlocationaddress.setText(CityName);

                            }


                            if (latitude != 0.0 && longitude != 0.0) {
                                LatLng latLng = new LatLng(latitude, longitude);
                                getAddressResultResponse(latLng, defaultcheckinhours, defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId,selectedVehicleType);
                                // gotoLocation(latitude, longitude);
                            }
                        }
                        else {
                            Log.w(TAG,"getCustomerVehicleandLocationResponseCall else : "+" latitude : "+latitude+ " longitude : "+longitude);
                            Log.w(TAG,"mLastLocation : "+mLastLocation);

                            if (latitude != 0.0 && longitude != 0.0) {
                                        LatLng latLng = new LatLng(latitude, longitude);
                                        getAddressResultResponse(latLng, defaultcheckinhours, defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId,selectedVehicleType);
                                    }else if(mLastLocation != null){
                                        latitude = mLastLocation.getLatitude();
                                        longitude = mLastLocation.getLongitude();
                                        Log.w(TAG,"mLastLocation : "+" latitude : "+latitude+ " longitude : "+longitude);
                                        if (latitude != 0.0 && longitude != 0.0) {
                                            LatLng latLng = new LatLng(latitude, longitude);
                                            getAddressResultResponse(latLng, defaultcheckinhours, defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId,selectedVehicleType);
                                        }

                                    }

                        }




                       /* if(defaultcheckinhours != null && defaultcheckouthours != null && requestCheckinDate != null && requestCheckoutDate != null && selectedVehicleTypeId != null) {
                            parkingListResponseCall(defaultcheckinhours, defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId);
                        }*/
                        rllist.setVisibility(View.VISIBLE);

                    }
                    else{
                       /*showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));
                        cv_vehicleimage.setVisibility(View.GONE);
                        txt_vehiclename.setVisibility(View.GONE);
                        txt_vehicle_number.setVisibility(View.GONE);
                        btn_vehiclefueltype.setVisibility(View.GONE);*/

                        Date c = Calendar.getInstance().getTime();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        requestCheckinDate = simpleDateFormat.format(c);
                        requestCheckoutDate = simpleDateFormat.format(c);
                        Calendar calendar = Calendar.getInstance();
                        int Hr24 = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        Log.w(TAG,"getCustomerVehicleandLocationResponseCall ELSE minutes"+minutes);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                        currentDateandTime = sdf.format(new Date());
                        Log.w(TAG,"getCustomerVehicleandLocationResponseCall Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
                        Log.w(TAG,"getCustomerVehicleandLocationResponseCall Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
                        defaultcheckinhours = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                        String currentdate =  currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
                        selectedVehicleTypeId = twowheelervehicleid;
                        selectedVehicleType = "Two Wheeler";
                        roundedMins(defaultcheckinhours,currentdate);


                        Log.w(TAG, "getCustomerVehicleandLocationResponseCall ELSE-->");

                        Log.w(TAG,"getCustomerVehicleandLocationResponseCall if : "+" fromactivity : "+fromactivity);
                        if (fromactivity != null && fromactivity.equalsIgnoreCase("ParkingPlacesSearchActivity")) {
                            latitude = lat;
                            longitude = lon;
                            Log.w(TAG,"getCustomerVehicleandLocationResponseCall : "+" latitude : "+latitude+ " longitude : "+longitude);


                            if (CityName != null) {
                                tv_searchlocationaddress.setText(CityName);

                            }


                            if (latitude != 0.0 && longitude != 0.0) {
                                LatLng latLng = new LatLng(latitude, longitude);
                                getAddressResultResponse(latLng, defaultcheckinhours, defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId,selectedVehicleType);
                                // gotoLocation(latitude, longitude);
                            }
                        }
                        else {
                            Log.w(TAG,"getCustomerVehicleandLocationResponseCall else ");
                            if (latitude != 0.0 && longitude != 0.0) {
                                LatLng latLng = new LatLng(latitude, longitude);
                                getAddressResultResponse(latLng, defaultcheckinhours, defaultcheckouthours, requestCheckinDate, requestCheckoutDate, selectedVehicleTypeId,selectedVehicleType);
                            }


                        }


                        rllist.setVisibility(View.VISIBLE);
                        toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
                        toggleButton.setChecked(false);


                    }




                }

            }


            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<GetCustomerVehicleandLocationResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "GetCustomerVehicleandLocationResponseflr" + t.getMessage());
            }
        });

    }

    @SuppressLint("LongLogTag")
    private GetCustomerVehicleandLocationRequest getCustomerVehicleandLocationRequest() {
        GetCustomerVehicleandLocationRequest getCustomerVehicleandLocationRequest = new GetCustomerVehicleandLocationRequest();
        getCustomerVehicleandLocationRequest.setCustomer_id(customerid);
        Log.w(TAG, "GetCustomerVehicleandLocationRequest" + new Gson().toJson(getCustomerVehicleandLocationRequest));
        return getCustomerVehicleandLocationRequest;
    }


    @SuppressLint("LongLogTag")
    private void getVehicleData(String selectedVehicleIds) {
        if (customerVehicleDataBeanList != null && customerVehicleDataBeanList.size() > 0) {
            for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                    twowheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                    Log.w(TAG,"getVehicleData : " +"twowheelerstatus : "+twowheelerstatus);

                } else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                    fourwheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                    Log.w(TAG,"getVehicleData : " +"fourwheelerstatus : "+fourwheelerstatus);


                }

                if(fourwheelerstatus != null && !fourwheelerstatus.isEmpty()){
                    selectedVehicleType = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                    vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                    Log.w(TAG, "getVehicleData if headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);
                }else{
                    selectedVehicleType = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                    vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                    Log.w(TAG, "getVehicleData else headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);
                }


              /*  if (selectedVehicleId.equalsIgnoreCase(id)) {
                    selectedVehicleType = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                    vehicleNumber = customerVehicleDataBeanList.get(i).getVehicle_No();
                    Log.w(TAG, "getVehicleData headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName + "headerVehicleFuelTypeBackgroundcolor :" + headerVehicleFuelTypeBackgroundcolor);
                }*/





            }


            setHeaderVehicleData();
        }


    }
    private void setHeaderVehicleData() {

        cv_vehicleimage.setVisibility(View.VISIBLE);
        btn_vehiclefueltype.setVisibility(View.GONE);
        txt_vehiclename.setVisibility(View.VISIBLE);
        txt_vehicle_number.setVisibility(View.VISIBLE);

        if(getActivity() != null){
            if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {
                if(!getActivity().isFinishing()){
                    Glide.with(getActivity())
                            .load(headerVehicleimg)
                            .into(cv_vehicleimage);

                } else {
                    Glide.with(getActivity())
                            .load(R.drawable.logo)
                            .into(cv_vehicleimage);

                }
            }


        }


        if (headerVehicleName != null) {
            txt_vehiclename.setText(headerVehicleName);
        } else {
            txt_vehiclename.setText("");
        }
        if (vehicleNumber != null) {
            txt_vehicle_number.setText(vehicleNumber);
        } else {
            txt_vehicle_number.setText("");
        }
        if (headerVehicleFuelTypeName != null) {
            btn_vehiclefueltype.setText(headerVehicleFuelTypeName);
        } else {
            btn_vehiclefueltype.setText("");
        }

        if (headerVehicleFuelTypeBackgroundcolor != null) {
            btn_vehiclefueltype.setBackgroundColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            btn_vehiclefueltype.setBackgroundResource(R.drawable.tags_rounded_corners);
            GradientDrawable gd = (GradientDrawable) btn_vehiclefueltype.getBackground();
            gd.setColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            gd.setCornerRadius(10);
            gd.setStroke(1, Color.WHITE);

        }

    }
    private void checkTimesResponseCall(String  checkinhours, String checkouthours, String requestCheckinDate, String requestCheckoutDate)  {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CheckTimesResponse> call = apiInterface.checkTimesResponseCall(RestUtils.getContentType(), checkTimesRequest(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate));
        Log.w(TAG, "url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<CheckTimesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<CheckTimesResponse> call, @NotNull Response<CheckTimesResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "CheckTimesResponse" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            isValidBookingData = true;
                             days = response.body().getData().getDays();
                             hours = response.body().getData().getHours();
                             int min = response.body().getData().getMin();

                             checkin_date = response.body().getData().getCheckin_date();
                             checkin_time = response.body().getData().getCheckin_time();
                             checkout_date = response.body().getData().getCheckout_date();
                             checkout_time  = response.body().getData().getCheckout_time();
                             String total_hrs  = response.body().getData().getTotal_hrs();
                             if(total_hrs != null) {
                                //txt_noofhours.setText(total_hrs+" Hours");
                              }
                             if(checkin_time != null){
                                 //txt_checkin_time.setText(checkin_time);
                             }
                             if(checkout_time != null){
                                 //txt_checkout_time.setText(checkout_time);
                             }

                            bottomSheetDialog.dismiss();

                           /* if (gpsTracker.getLatitude() != 0.0 && gpsTracker.getLongitude() != 0.0) {
                                latitude = gpsTracker.getLatitude();
                                longitude = gpsTracker.getLongitude();
                                LatLng latLng = new LatLng(latitude,longitude);
                                getAddressResultResponse(latLng,checkin_time, checkout_time, checkin_date, checkout_date, selectedVehicleTypeId,selectedVehicleType);
                            }*/

                           // parkingListResponseCall(checkin_time, checkout_time, checkin_date, checkout_date, selectedVehicleTypeId);
/*
                            String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                                    txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + days + " Day " + hours+" Hours" + ")";*/

                            bookingstartdate = txt_checkin_date.getText().toString();
                            str_checkintime = txt_checkin_time.getText().toString();
                            bookingenddate = txt_checkout_date.getText().toString();
                            str_checkoutime = txt_checkout_time.getText().toString();
                            strdays = String.valueOf(days);
                            strtotalHours = String.valueOf(hours);

                            String selectedDateandTime = bookingstartdate + ", " + str_checkintime + " to " +
                                    bookingenddate + ", " + str_checkoutime + "(" + strdays + " Day " + strtotalHours+" Hours" + ")";
                            txt_selecteddate_time.setText(selectedDateandTime);
                            txt_selecteddate_time.setTextSize(12);
                            txt_bookingdateandtime.setText(selectedDateandTime);


                        }





                    } else {
                        isValidBookingData = false;
                        showErrorLoading(response.body().getMessage());
                        checkin_date = response.body().getData().getCheckin_date();
                        checkin_time = response.body().getData().getCheckin_time();
                        checkout_date = response.body().getData().getCheckout_date();
                        checkout_time  = response.body().getData().getCheckout_time();
                        String total_hrs  = response.body().getData().getTotal_hrs();
                        if(total_hrs != null) {
                           // txt_noofhours.setText(total_hrs+" Hours");
                        }
                        if(checkin_time != null){
                           // txt_checkin_time.setText(checkin_time);
                        }
                        if(checkout_time != null){
                           // txt_checkout_time.setText(checkout_time);
                        }

                      /*  String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                                txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + 0 + " Day " + "0 Hours"+")";
                        txt_selecteddate_time.setText(selectedDateandTime);*/

                    }


                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckTimesResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "CheckTimesResponse flr" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private CheckTimesRequest checkTimesRequest(String checkin_time, String checkout_time, String requestCheckinDate, String requestCheckoutDate) {
        CheckTimesRequest checkTimesRequest = new CheckTimesRequest();
        checkTimesRequest.setCheckin_date(requestCheckinDate);
        checkTimesRequest.setCheckout_date(requestCheckoutDate);
        checkTimesRequest.setCheckin_time(checkin_time);
        checkTimesRequest.setCheckout_time(checkout_time);
        Log.w(TAG, "checkTimesRequest" + new Gson().toJson(checkTimesRequest));
        return checkTimesRequest;
    }
    private boolean checkValidDateandTime(String checkinTime, String checkoutTime, String checkinDate, String checkoutDate){
        boolean validate = true;
        Log.w(TAG,"checkValidDateandTime :"+" checkinDate : "+ checkinDate +" checkoutDate : "+ checkoutDate +" checkinTime : "+checkinTime+" checkoutTime : "+checkoutTime);


        try {
            String checkindateandtime = checkinDate +" "+checkinTime;
            String checkoutdateandtime = checkoutDate +" "+checkoutTime;
            Log.w(TAG,"checkValidDateandTime :"+" checkindateandtime : "+checkindateandtime+" checkoutdateandtime : "+checkoutdateandtime);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
            Date localdateandtime  = simpleDateFormat.parse(currentDateandTime);
            Date dateandtimecheckin  = simpleDateFormat.parse(checkindateandtime);
            Date dateandtimecheckout  = simpleDateFormat.parse(checkoutdateandtime);
            if (dateandtimecheckin != null && localdateandtime != null) {

                Log.w(TAG,"Condition else if1: "+localdateandtime.after(dateandtimecheckin));
                Log.w(TAG,"Condition else if2: "+dateandtimecheckin.after(dateandtimecheckout));
                Log.w(TAG,"Condition else equals1: "+(localdateandtime.compareTo(dateandtimecheckin) != 0));
                Log.w(TAG,"Condition else equals2: "+dateandtimecheckin.equals(dateandtimecheckout));
                Log.w(TAG,"isCheckedin: "+isCheckedin);



                if(formattedDate1.equals(checkinDate) && formattedDate1.equals(checkoutDate)) {
                    Log.w(TAG, "IF------>");
                    if (isCheckedin) {
                        Log.w(TAG, "IF------>"+"isCheckedin : "+isCheckedin);


                    if (dateandtimecheckin.equals(dateandtimecheckout)) {
                        Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                        Log.w(TAG, "start is equal to end");
                    } else if (localdateandtime.after(dateandtimecheckin) || localdateandtime.compareTo(dateandtimecheckin) == 0) {
                        Log.w(TAG, "current dateandtime greaterthan checkindateandtime " + "localdateandtime : " + localdateandtime + "dateandtimecheckin : " + dateandtimecheckin);
                        Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                    } else if (dateandtimecheckin.after(dateandtimecheckout)) {
                        Log.w(TAG, "start is after end");
                        Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                    }
                }else{
                        Log.w(TAG, "Else------>"+"isCheckedin : "+isCheckedin);

                        if (dateandtimecheckin.equals(dateandtimecheckout)) {
                            Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                            Log.w(TAG, "start is equal to end");
                        } else if (localdateandtime.after(dateandtimecheckin)) {
                            Log.w(TAG, "current dateandtime greaterthan checkindateandtime " + "localdateandtime : " + localdateandtime + "dateandtimecheckin : " + dateandtimecheckin);
                            Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                        } else if (dateandtimecheckin.after(dateandtimecheckout)) {
                            Log.w(TAG, "start is after end");
                            Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                            validate = false;
                        }
                    }

                }else{
                    Log.w(TAG,"ELSE------>");
                    if (dateandtimecheckin.equals(dateandtimecheckout)) {
                        Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                        Log.w(TAG,"start is equal to end");
                    }
                    else if (dateandtimecheckin.after(dateandtimecheckout)) {
                        Log.w(TAG,"start is after end");
                        Toasty.error(mContext, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT, true).show();
                        validate = false;
                    }


                }





            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return validate;
    }

    @Override
    public void parkingSelectlatlongListener(double lat, double lon) {
        Log.w(TAG,"parkingSelectlatlongListener click : "+" lat : "+lat+" lon : "+lon);

        if(mMap != null){
            LatLng coordinate = new LatLng(lat, lon);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, zoomLevel);
            mMap.animateCamera(yourLocation);
        }


    }

    @SuppressLint("NewApi")
    private void roundedMins(String currenttime, String currentdate){

       // Log.w(TAG,"roundedMins currenttime : "+currenttime);


        String[] separated = currenttime.split(":");
         hourssplit = separated[0];
        String minutesampmsplit = separated[1];
        //Log.w(TAG,"minutesampmsplit : "+minutesampmsplit);

        String[] splited = minutesampmsplit.split("\\s+");
        String minutessplit = splited[0];
        String ampm = splited[1];
        //Log.w(TAG,"minutessplit : "+minutessplit+" ampm : "+ampm);


        int currentmins = Integer.parseInt(minutessplit);

        currentmin = (currentmins+4)/5;


                  Long minutes = Long.valueOf(minutessplit);

                        if(ValueRange.of(0, 5).isValidIntValue(minutes)) {
                            roundedminutes = "05";
                            Log.w(TAG,"Value is with in the Range. 05");
                        }
                        else if(ValueRange.of(5, 10).isValidIntValue(minutes)) {
                            roundedminutes = "10";
                            Log.w(TAG,"Value is with in the Range. 10");
                        }
                        else if(ValueRange.of(10, 15).isValidIntValue(minutes)) {
                            roundedminutes = "15";
                            Log.w(TAG,"Value is with in the Range. 15");
                        }
                       else if(ValueRange.of(15, 20).isValidIntValue(minutes)) {
                            roundedminutes = "20";
                            Log.w(TAG,"Value is with in the Range. 20");
                        }
                       else if(ValueRange.of(20, 25).isValidIntValue(minutes)) {
                            roundedminutes = "25";
                            Log.w(TAG,"Value is with in the Range. 25");
                        }
                        else if(ValueRange.of(25, 30).isValidIntValue(minutes)) {
                            roundedminutes = "30";
                            Log.w(TAG,"Value is with in the Range. 30");
                        }
                        else if(ValueRange.of(30, 35).isValidIntValue(minutes)) {
                            roundedminutes = "35";
                            Log.w(TAG,"Value is with in the Range. 35");
                        }
                        else if(ValueRange.of(35, 40).isValidIntValue(minutes)) {
                            roundedminutes = "40";
                            Log.w(TAG,"Value is with in the Range. 40");
                        }

                        else if(ValueRange.of(40, 45).isValidIntValue(minutes)) {
                            roundedminutes = "45";
                            Log.w(TAG,"Value is with in the Range. 45");
                        }
                        else if(ValueRange.of(45, 50).isValidIntValue(minutes)) {
                            roundedminutes = "50";
                            Log.w(TAG,"Value is with in the Range. 50");
                        }
                        else if(ValueRange.of(50, 55).isValidIntValue(minutes)) {
                            roundedminutes = "55";
                            Log.w(TAG,"Value is with in the Range. 55");
                        }
                        else if(ValueRange.of(55, 60).isValidIntValue(minutes)) {
                            roundedminutes = "00";
                            Log.w(TAG,"Value is with in the Range. 00");
                        }
                      defaultcheckinhours = hourssplit+":"+roundedminutes+" "+ampm;


                        if(roundedminutes.equalsIgnoreCase("00")){
                            String currentdateandtime =  currentdate+" "+defaultcheckinhours;
                            @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");

                            final Date date;
                            try {
                                date = sdf.parse(currentdateandtime);
                                final Calendar calendar = Calendar.getInstance();

                                if (date != null) {
                                    calendar.setTime(date);
                                }
                                calendar.add(Calendar.HOUR, 1);
                                // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
                                String currentDateandTime = sdf.format(calendar.getTime());
                                Log.w(TAG,"roundedMins Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
                                Log.w(TAG,"roundedMins Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
                                 defaultcheckinhours = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
                                 requestCheckinDate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
                                 requestCheckoutDate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
                                currentdate = requestCheckinDate;

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                          //  defaultcheckinhours = hoursadd+":"+roundedminutes+" "+ampm;
                        }else{
                            defaultcheckinhours = hourssplit+":"+roundedminutes+" "+ampm;
                        }



                     str_checkintime = defaultcheckinhours;

                       // Log.w(TAG,"roundedMins defaultcheckinhours "+defaultcheckinhours);

        String currentdateandtime =  currentdate+" "+defaultcheckinhours;
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        final Date date;
        try {


            date = sdf.parse(currentdateandtime);
            final Calendar calendar = Calendar.getInstance();

            if (date != null) {
                calendar.setTime(date);
            }
            calendar.add(Calendar.HOUR, 1);

           // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
            String currentDateandTime = sdf.format(calendar.getTime());

            Log.w(TAG,"roundedMins Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
            Log.w(TAG,"roundedMins Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
            defaultcheckouthours = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
            requestCheckoutDate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));

           // String defaultcheckoutdate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
           // convertDateFormatyyyyMMddtoddMMM(defaultcheckoutdate);
            Log.w(TAG," roundedMins defaultcheckouthours-->"+defaultcheckouthours);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            int TIME_PICKER_INTERVAL = 5;
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));


        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }
    }

    public void getdateandtime(String currentdateandtime ){
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        final Date date;
        try {


            date = sdf.parse(currentdateandtime);
            final Calendar calendar = Calendar.getInstance();

            if (date != null) {
                calendar.setTime(date);
            }
            calendar.add(Calendar.HOUR, 1);

            // Log.w(TAG,"Time here " + sdf.format(calendar.getTime()));
            String currentDateandTime = sdf.format(calendar.getTime());

            Log.w(TAG,"getdateandtime Date: " + currentDateandTime.substring(0, currentDateandTime.indexOf(' ')));
            Log.w(TAG,"getdateandtime Time: " + currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1));
            defaultcheckouthours = currentDateandTime.substring(currentDateandTime.indexOf(' ') + 1);
            requestCheckoutDate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));

            // String defaultcheckoutdate = currentDateandTime.substring(0, currentDateandTime.indexOf(' '));
            // convertDateFormatyyyyMMddtoddMMM(defaultcheckoutdate);
            Log.w(TAG," getdateandtime defaultcheckouthours-->"+defaultcheckouthours);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }





    private void convertcinDateFormatyyyyMMddtoddMMM(String inputDate) {
        Log.w(TAG,"convertcinDateFormatyyyyMMddtoddMMM : "+inputDate);
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
             String outputDateStr = outputFormat.format(dateformat);
            Log.w(TAG,"convertcinDateFormatyyyyMMddtoddMMM : "+outputDateStr);
             txt_checkin_date.setText(outputDateStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void convertcoutDateFormatyyyyMMddtoddMMM(String inputDate) {
        Log.w(TAG,"convertcoutDateFormatyyyyMMddtoddMMM : "+inputDate);

        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        Date dateformat = null;
        try {
            dateformat = inputFormat.parse(inputDate);
             String outputDateStr = outputFormat.format(dateformat);
            Log.w(TAG,"convertcoutDateFormatyyyyMMddtoddMMM : "+outputDateStr);

            txt_checkout_date.setText(outputDateStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void GetTimeWithAMPMFromTime(String dt) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            Date date = inFormat.parse(dt);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            checkintime24hrs = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void convertTimeWithAMPMFromTime(String dt) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            Date date = inFormat.parse(dt);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            checkouttime24hrs = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void printDifference(Date startDate, Date endDate) {
        Log.w(TAG,"printDifference--->"+"startDate : "+startDate+" endDate : "+endDate);
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        Log.w(TAG,"startDate : " + startDate);
        Log.w(TAG,"endDate : "+ endDate);
        Log.w(TAG,"different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        Log.w(TAG, ""+"elapsedDays : "+elapsedDays+" elapsedHours : "+ elapsedHours+" elapsedMinutes : "+ elapsedMinutes+ elapsedSeconds);

        String selectedDateandTime = txt_checkin_date.getText().toString() + ", " + txt_checkin_time.getText().toString() + " to " +
                txt_checkout_date.getText().toString() + ", " + txt_checkout_time.getText().toString() + "(" + elapsedDays + " Day " + elapsedHours+" Hours"+ ")";

        Log.w(TAG,"selectedDateandTime--->"+selectedDateandTime);
        txt_selecteddate_time.setText(selectedDateandTime);
        txt_selecteddate_time.setTextSize(12);
    }


    public void parkingLocationGetListResponseCall(String checkintime, String checkouttime, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId, String selectedVehicleType ){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingLocationGetListResponse> call = apiInterface.parkingLocationGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<ParkingLocationGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ParkingLocationGetListResponse> call, @NonNull Response<ParkingLocationGetListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"parkingLocationGetListResponseCall" + new Gson().toJson(response.body()));

                    getServiceListResponseList = response.body().getData();
                    if(!getServiceListResponseList.isEmpty()){
                        int j =1;
                        for(int i =0; i<getServiceListResponseList.size();i++){
                            String cityname = response.body().getData().get(i).getDisplay_Name();
                            Log.w(TAG,"cityname :"+cityname+" "+"Current City :"+CityName);

                            if(cityname != null && CityName != null){
                                if(cityname.trim().equalsIgnoreCase(CityName.trim())){
                                    Log.w(TAG,"if --->isServiceLocation--->");
                                    double lat = response.body().getData().get(i).getLat();
                                    Log.w(TAG,"lat "+lat);
                                    pinCodeList = response.body().getData().get(i).getPincodes();
                                    isServiceCityPincode = true;
                                    validPincode(pinCodeList);
                                    break ;
                                }
                                else{
                                    Log.w(TAG,"Else ---> isServiceLocation--->");
                                    if(j == getServiceListResponseList.size()){
                                        Log.w(TAG,"Else ---> isServiceLocation--->"+"Size: "+" "+getServiceListResponseList.size()+" "+"J Value  "+j);

                                    }
                                }j++;
                            }else{
                                Activity activity = getActivity();
                                if(activity != null){
                                    startActivity(new Intent(mContext, ParkingPickUpLocationActivity.class));

                                }
                            }


                        }





                    }
                    Log.w(TAG,"isServiceCityPincode : "+isServiceCityPincode);
                    if(isServiceCityPincode) {
                        parkingListResponseCall(checkintime,checkouttime, requestCheckinDate, requestCheckoutDate,selectedVehicleTypeId, selectedVehicleType);
                       /* Intent intent = new Intent(mContext, AddMyAddressActivity.class);
                        intent.putExtra("latlng", strlatlng);
                        intent.putExtra("cityname", CityName);
                        intent.putExtra("address", AddressLine);
                        startActivity(intent);*/
                    }else{
                        Activity activity = getActivity();
                        if(activity != null){
                            //Toasty.warning(mContext, getResources().getString(R.string.postalcodeerrormesssage), Toast.LENGTH_SHORT, true).show();
                            startActivity(new Intent(mContext, ParkingPickUpLocationActivity.class));
                        }


                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<ParkingLocationGetListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"parkingLocationGetListResponseCall flr"+t.getMessage());
            }
        });

    }

    private void validPincode(List<String> pinCodeList) {
        Log.w(TAG,"validPincode PostalCode : "+PostalCode);
        if(PostalCode != null){
            if(isServiceCityPincode){
                for(int k=0; k<pinCodeList.size();k++){
                    String resPostalCode = pinCodeList.get(k);
                    if(resPostalCode != null && PostalCode != null){
                        if(resPostalCode.trim().equalsIgnoreCase(PostalCode.trim())){
                            isServiceCityPincode = true;
                            break;

                        } else{
                            isServiceCityPincode = false;

                        }

                    }



                }
                if(!isServiceCityPincode){
                    Activity activity = getActivity();
                    if(activity != null) {
                        Toasty.warning(mContext, getResources().getString(R.string.postalcodeerrormesssage), Toast.LENGTH_SHORT, true).show();
                    }

                }
            }
        }
        else{
            Activity activity = getActivity();
            if(activity != null){
                Toasty.warning(mContext, getResources().getString(R.string.postalcodeunablefinderrormesssage), Toast.LENGTH_SHORT, true).show();
                startActivity(new Intent(mContext, ParkingPickUpLocationActivity.class));

            }

        }

    }

    private void getAddressResultResponse(LatLng latLng,String checkintime, String checkouttime, String requestCheckinDate, String requestCheckoutDate, String selectedVehicleTypeId,String selectedVehicleType) {
        avi_indicator.setVisibility(View.VISIBLE);
         avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        String strlatlng = String.valueOf(latLng);
        String newString = strlatlng.replace("lat/lng:", "");

        String latlngs = newString.trim().replaceAll("\\(", "").replaceAll("\\)","").trim();

        String key = API.MAP_KEY;
        service.getAddressResultResponseCall(latlngs, key).enqueue(new Callback<GetAddressResultResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetAddressResultResponse> call, @NotNull Response<GetAddressResultResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"GetAddressResultResponse" + new Gson().toJson(response.body()));
                if(response.body() != null) {
                    String currentplacename = null;
                    String compundcode = null;

                    if(response.body().getPlus_code().getCompound_code() != null){
                        compundcode = response.body().getPlus_code().getCompound_code();
                    }
                    if(compundcode != null) {
                        String[] separated = compundcode.split(",");
                        String placesname = separated[0];
                        String[] splitData = placesname.split("\\s", 2);
                        String code = splitData[0];
                        currentplacename = splitData[1];
                        Log.w(TAG, "code-->" + code + "currentplacename : " + currentplacename);
                    }


                    String localityName = null;
                    String cityName = null;
                    String sublocalityName = null;
                    String postalCode = null;


                    List<GetAddressResultResponse.ResultsBean> getAddressResultResponseList;
                    getAddressResultResponseList = response.body().getResults();
                    if (getAddressResultResponseList.size() > 0) {
                        AddressLine = getAddressResultResponseList.get(0).getFormatted_address();
                        Log.w(TAG, "FormateedAddress-->" + AddressLine);

                    }
                    List<GetAddressResultResponse.ResultsBean.AddressComponentsBean> addressComponentsBeanList = response.body().getResults().get(0).getAddress_components();
                    if(addressComponentsBeanList != null) {
                        if (addressComponentsBeanList.size() > 0) {
                            for (int i = 0; i < addressComponentsBeanList.size(); i++) {

                                for (int j = 0; j < addressComponentsBeanList.get(i).getTypes().size(); j++) {

                                    List<String> typesList = addressComponentsBeanList.get(i).getTypes();

                                    if (typesList.contains("postal_code")) {
                                        postalCode = addressComponentsBeanList.get(i).getShort_name();
                                        PostalCode = postalCode;

                                    }
                                    if (typesList.contains("locality")) {
                                        CityName = addressComponentsBeanList.get(i).getLong_name();
                                        localityName = addressComponentsBeanList.get(i).getShort_name();


                                    }

                                    if (typesList.contains("administrative_area_level_2")) {
                                        cityName = addressComponentsBeanList.get(i).getShort_name();

                                    }
                                    if (typesList.contains("sublocality_level_1")) {
                                        sublocalityName = addressComponentsBeanList.get(i).getShort_name();

                                    }


                                }

                            }




                        }

                        parkingLocationGetListResponseCall(checkintime,checkouttime,requestCheckinDate,requestCheckoutDate,selectedVehicleTypeId,selectedVehicleType);

                    }
                }


            }

            @Override
            public void onFailure(@NotNull Call<GetAddressResultResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext == null)
            mContext = context.getApplicationContext();
    }
    }

