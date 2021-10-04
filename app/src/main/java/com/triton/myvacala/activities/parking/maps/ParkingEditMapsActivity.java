package com.triton.myvacala.activities.parking.maps;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.Constants;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.PlacesSearchEditActivity;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.adapter.LocationListAdapter;
import com.triton.myvacala.api.API;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.responsepojo.GetAddressResultResponse;
import com.triton.myvacala.responsepojo.GetServiceListResponse;
import com.triton.myvacala.service.GPSTracker;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParkingEditMapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_setpickuppoint)
    Button btn_setpickuppoint;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.imgLocationPinUp)
    ImageView imgLocationPinUp;

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;



    @BindView(R.id.rl_placessearch)
    RelativeLayout rl_placessearch;

    @BindView(R.id.tv_searchlocationaddress)
    TextView tv_searchlocationaddress;

    String TAG = "ParkingEditMapsActivity";

    private double latitude = 0, longitude = 0;

    String strlatlng = "",id = "";



    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    private List<GetServiceListResponse.DataBean> getServiceListResponseList = null;
    List<String> pinCodeList;
    boolean isServiceCityPincode = false;


    String CityName = "", AddressLine = "",PostalCode ="";;

    String  flatno = "",nearbylandmark ="",locationnickname = "",locationtype ="";
    double lat,lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_maps_parking);

        Log.w(TAG,"onCreate-->");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");

            CityName = extras.getString("cityname");
            flatno = extras.getString("flatno");
            nearbylandmark = extras.getString("landmark");
            locationnickname = extras.getString("nickname");
            locationtype = extras.getString("locationtype");

            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");


        } else {
            id = LocationListAdapter.id;
        }

        ButterKnife.bind(this);
        toolbar_title.setText(getResources().getString(R.string.pickuplocation));
        imgLocationPinUp.setVisibility(View.GONE);


        avi_indicator.setVisibility(View.GONE);


        rl_placessearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParkingEditMapsActivity.this,PlacesSearchEditActivity.class);
                i.putExtra("id",id);
                i.putExtra("cityname",CityName);
                i.putExtra("flatno",flatno);
                i.putExtra("landmark",nearbylandmark);
                i.putExtra("nickname",locationnickname);
                i.putExtra("locationtype",locationtype);

                Bundle b = new Bundle();
                b.putDouble("lat", lat);
                b.putDouble("lon", lon);
                i.putExtras(b);
                startActivity(i);
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        if (extras != null) {}else{
            checkLocation();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);


      /*  if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCVeEuZcqrs9phnrk1aNSpiJ57hb-V8hhE");
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autocompleteFragment != null;
        autocompleteFragment.setHint(getResources().getString(R.string.searchlocationaddress));


        autocompleteFragment.setCountries("IN");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                Log.w(TAG, "Place: " + place.getName() + ", " + place.getId() + " " + place.getAddress() + "" + place.getLatLng());
                strlatlng = String.valueOf(place.getLatLng());
                CityName = place.getName();
                AddressLine = place.getAddress();
                if (new ConnectionDetector(EditMapsActivity.this).isNetworkAvailable(EditMapsActivity.this)) {
                    getServiceListResponseCall();
                }


                mMap.clear();
                //  mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                MarkerOptions markerOptions = new MarkerOptions().position(Objects.requireNonNull(place.getLatLng())).title(place.getName());
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
                mMap.addMarker(markerOptions);


            }

            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });*/


        btn_setpickuppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CityName != null){
                    if (new ConnectionDetector(ParkingEditMapsActivity.this).isNetworkAvailable(ParkingEditMapsActivity.this)) {
                        getServiceListResponseCall();
                    }
                }



            }
        });

        bottom_navigation_parking.setSelectedItemId(R.id.account);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }  //end of oncreate

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    //active = homeFragment;
                    String active_tag = "1";
                    callDirections(active_tag);
                    break;


                case R.id.bookinghistory:
                    //active = searchFragment;
                    active_tag = "2";
                    callDirections(active_tag);
                    break;


                case R.id.myvehicle:
                    //active = myVehicleFragment;
                    active_tag = "3";
                    callDirections(active_tag);
                    break;

                case R.id.account:
                    //active = accountFragment;
                    active_tag = "4";
                    callDirections(active_tag);
                    break;

            }
            return true;
        }


    };
    public void callDirections(String tag){
        Intent intent = new Intent(ParkingEditMapsActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(Constants.AppKey.CHENNAI_LATLNG)
                .zoom(18)
                .bearing(0)
                .tilt(45)
                .build()));



        //  mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mMap.getUiSettings().setZoomControlsEnabled(true);
        // mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.w(TAG,"setOnMapClickListener latLng---->"+latLng);
                strlatlng  = String.valueOf(latLng);


                String newString = strlatlng.replace("lat/lng:", "");
                Log.w(TAG,"setOnMapClickListener latlng=="+newString);

                String latlngs = newString.trim().replaceAll("\\(", "").replaceAll("\\)","").trim();
                Log.w(TAG,"setOnMapClickListener latlngs=="+latlngs);

                String[] separated = latlngs.split(",");
                String lat = separated[0];
                String lon = separated[1];

                latitude = Double.parseDouble(lat);
                longitude = Double.parseDouble(lon);



                Log.w(TAG,"setOnMapClickListener latlong :"+latitude+" "+longitude);

                if(latitude != 0 && longitude != 0){
                    latLng = new LatLng(latitude,longitude);
                    getAddressResultResponse(latLng);

                }


                //   getAddress(latitude,longitude);

              /*  Geocoder geocoder = new Geocoder(EditMapsActivity.this, Locale.getDefault());
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(latitude,
                            longitude, 1);
                    if (null != listAddresses && listAddresses.size() > 0) {
                        String state = listAddresses.get(0).getAdminArea();
                        String country = listAddresses.get(0).getCountryName();
                        String subLocality = listAddresses.get(0).getSubLocality();
                        String city = listAddresses.get(0).getLocality();
                        AddressLine = listAddresses.get(0).getAddressLine(0);

                        CityName = listAddresses.get(0).getLocality();

                        Log.w(TAG,"setOnMapClickListener city :"+ " "+ CityName+" "+ "address :"+" "+AddressLine);


                        if (new ConnectionDetector(EditMapsActivity.this).isNetworkAvailable(EditMapsActivity.this)) {
                            getServiceListResponseCall();
                        }



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                mMap.clear();
                //mMap.addMarker(new MarkerOptions().position(latLng));
                //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                MarkerOptions markerOptions = new MarkerOptions().position(Objects.requireNonNull(latLng)).title("");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
                mMap.addMarker(markerOptions);
            }
        });


    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
        mGoogleApiClient.connect();
        setUpDragListners();
    }
    private void setUpDragListners() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        GPSTracker gps = new GPSTracker(ParkingEditMapsActivity.this);

        gps.canGetLocation();
        LatLng curentpoint = new LatLng(gps.getLatitude(), gps.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(curentpoint).zoom(15f).tilt(30).build();
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mMap.clear();
                imgLocationPinUp.setVisibility(View.VISIBLE);

            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                // mMap.clear();
                imgLocationPinUp.setVisibility(View.GONE);

                LatLng center = mMap.getCameraPosition().target;
                double CameraLat = mMap.getCameraPosition().target.latitude;
                double CameraLong = mMap.getCameraPosition().target.longitude;
                getChangeLocationBackground(CameraLat,CameraLong);

            }
        });

    }
    private void getChangeLocationBackground(double latitude, double longitude) {
        Log.w(TAG,"getChangeLocationBackground--->"+"latitude :"+latitude+" "+"longitude :"+longitude);
        LatLng latLng = new LatLng(latitude, longitude);
        strlatlng = String.valueOf(latLng);
        if(latitude != 0 && longitude != 0){
            getAddressResultResponse(latLng);

        }

        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions().position(mMap.getCameraPosition().target)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
        mMap.addMarker(markerOptions);





    }
    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.w(TAG,"onLocationChanged-->");


        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.w(TAG,"latLng--->"+latLng);

        strlatlng  = String.valueOf(latLng);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));


        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        assert provider != null;
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());

            // getAddress(latitude,longitude);

            if(latitude != 0 && longitude != 0){
                latLng = new LatLng(latitude,longitude);
                Log.w(TAG,"onLocationChanged-->"+"Call getAddressResultResponse");
                getAddressResultResponse(latLng);

            }


            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                //CityName = extras.getString("cityname");
                String placesearchactivity = extras.getString("placesearchactivity");
                double lat = extras.getDouble("lat");
                double lon = extras.getDouble("lon");
                latitude = lat;
                longitude = lon;

                Log.w(TAG,"BundleData for search places :"+"lat :"+lat+" "+"lon :"+lon);

                latLng = new LatLng(lat, lon);
                if(placesearchactivity != null && placesearchactivity.equalsIgnoreCase("placesearchactivity")){
                    if(latitude != 0 && longitude != 0){
                        latLng = new LatLng(latitude,longitude);
                        Log.w(TAG,"onLocationChanged BundleData-->"+"Call getAddressResultResponse");
                        Log.w(TAG,"onLocationChanged BundleData for searched places :"+"lat :"+lat+" "+"lon :"+lon);
                        strlatlng = String.valueOf(latLng);
                        Log.w(TAG,"onLocationChanged BundleData"+strlatlng);
                        getAddressResultResponse(latLng);
                    }
                }



                mMap.clear();
                //  mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                markerOptions = new MarkerOptions().position(Objects.requireNonNull(latLng)).title(CityName);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
                mMap.addMarker(markerOptions);


            }



        }



        //   markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }

    @Override
    public void onConnectionFailed(@NotNull ConnectionResult connectionResult) {
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String permissions[], @NotNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "permission denied",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    private void checkLocation(){
        try{
            LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ignored) {}

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ignored) {}

            if(!gps_enabled && !network_enabled) {

                if( !lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                    showSettingsAlert();
                }

            }else{
                getLatandLong();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void getLatandLong(){
        try{
            if (ContextCompat.checkSelfPermission(ParkingEditMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ParkingEditMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ParkingEditMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
            else {
                GPSTracker gps = new GPSTracker(ParkingEditMapsActivity.this);

                // Check if GPS enabled
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Log.w(TAG,"getLatandLong--->"+"latitude" + " " + latitude);
                    Log.w(TAG,"getLatandLong--->"+"longitude" + " " + longitude);

                   /* String country = gps.getCountryName(MapsActivity.this);
                    String city = gps.getLocality(MapsActivity.this);
                    String postalCode = gps.getPostalCode(MapsActivity.this);
                    String addressLine = gps.getAddressLine(MapsActivity.this);
                    Log.w(TAG,"country : "+country+" "+"city : "+" "+city+"postalCode : "+" "+postalCode+" "+"addressLine :"+" "+addressLine);*/

                    //  Toasty.warning(getApplicationContext(), "latitude :"+latitude+"longitude :"+longitude+"address :"+addressLine, Toast.LENGTH_SHORT, true).show();


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ParkingEditMapsActivity.this);

        // Setting DialogHelp Title
        alertDialog.setTitle("GPS is settings");

        // Setting DialogHelp Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }


    public void getServiceListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<GetServiceListResponse> call = apiInterface.getServiceListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<GetServiceListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetServiceListResponse> call, @NonNull Response<GetServiceListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"GetServiceListResponse" + new Gson().toJson(response.body()));

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
                                        startActivity(new Intent(ParkingEditMapsActivity.this,ParkingEditPickUpLocationActivity.class));
                                    }
                                }j++;
                            }else{
                                startActivity(new Intent(ParkingEditMapsActivity.this,ParkingEditPickUpLocationActivity.class));

                            }


                        }





                    }
                    if(isServiceCityPincode){
                        Intent intent = new Intent(ParkingEditMapsActivity.this, ParkingEditMyAddressActivity.class);
                        intent.putExtra("latlng", strlatlng);
                        intent.putExtra("cityname", CityName);
                        intent.putExtra("address", AddressLine);
                        intent.putExtra("flatno", flatno);
                        intent.putExtra("nearbylandmark", nearbylandmark);
                        intent.putExtra("locationnickname", locationnickname);
                        intent.putExtra("locationtype",locationtype);
                        intent.putExtra("id", id);
                        Log.w(TAG, "id-->" + id);
                        startActivity(intent);
                    }else{
                        Toasty.warning(getApplicationContext(), getResources().getString(R.string.postalcodeerrormesssage), Toast.LENGTH_SHORT, true).show();

                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<GetServiceListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"GetServiceListResponseflr"+t.getMessage());
            }
        });

    }

    private void validPincode(List<String> pinCodeList) {
        Log.w(TAG,"Called vaildPincode-->"+"PostalCode :"+PostalCode+"isServiceCityPincode :"+isServiceCityPincode);
        if(isServiceCityPincode){
            for(int k=0; k<pinCodeList.size();k++){
                String resPostalCode = pinCodeList.get(k);
                if(resPostalCode != null && PostalCode != null){
                    Log.w(TAG,"resPostalCode---->"+resPostalCode+"Current location PostalCode:"+PostalCode);
                    if(resPostalCode.trim().equalsIgnoreCase(PostalCode.trim())){
                        Log.w(TAG,"if --->isServiceLocation Pin code--->");
                        isServiceCityPincode = true;
                        break;

                    } else{
                        Log.w(TAG,"Else --->isServiceLocation Pin code--->");
                        isServiceCityPincode = false;

                    }

                }



            }
            if(!isServiceCityPincode){
                Toasty.warning(getApplicationContext(), getResources().getString(R.string.postalcodeerrormesssage), Toast.LENGTH_SHORT, true).show();

            }
        }
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

                String state = listAddresses.get(0).getAdminArea();
                String country = listAddresses.get(0).getCountryName();
                String subLocality = listAddresses.get(0).getSubLocality();
                String city = listAddresses.get(0).getLocality();
                //AddressLine = listAddresses.get(0).getAddressLine(0);
                // CityName = listAddresses.get(0).getLocality();
                //  PostalCode = listAddresses.get(0).getPostalCode();

                Log.w(TAG,"getAddress-->"+" CityName : "+" "+CityName+" "+"PostalCode : "+PostalCode);


            }
        } catch (IOException e) {
            Log.e("tag", Objects.requireNonNull(e.getMessage()));
        }

        result.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getAddressResultResponse(LatLng latLng) {
        Log.w(TAG,"GetAddressResultResponse-->"+latLng);
        //avi_indicator.setVisibility(View.VISIBLE);
        // avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        String strlatlng = String.valueOf(latLng);
        Log.w(TAG,"getAddressResultResponse strlatlng-->"+strlatlng);
        String newString = strlatlng.replace("lat/lng:", "");
        Log.w(TAG,"getAddressResultResponse latlng=="+newString);

        String latlngs = newString.trim().replaceAll("\\(", "").replaceAll("\\)","").trim();
        Log.w(TAG,"getAddressResultResponse latlngs=="+latlngs);

        String key = API.MAP_KEY;
        service.getAddressResultResponseCall(latlngs, key).enqueue(new Callback<GetAddressResultResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetAddressResultResponse> call, @NotNull Response<GetAddressResultResponse> response) {
                //avi_indicator.smoothToHide();
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
                                Log.w(TAG, "addressComponentsBeanList size : " + addressComponentsBeanList.size());

                                for (int j = 0; j < addressComponentsBeanList.get(i).getTypes().size(); j++) {
                                    Log.w(TAG, "getTypes size : " + addressComponentsBeanList.get(i).getTypes().size());

                                    Log.w(TAG, "TYPES-->" + addressComponentsBeanList.get(i).getTypes());
                                    List<String> typesList = addressComponentsBeanList.get(i).getTypes();

                                    if (typesList.contains("postal_code")) {
                                        postalCode = addressComponentsBeanList.get(i).getShort_name();
                                        PostalCode = postalCode;
                                        Log.w(TAG, "Postal Short name ---->" + postalCode);

                                    }
                                    if (typesList.contains("locality")) {
                                        CityName = addressComponentsBeanList.get(i).getLong_name();
                                        localityName = addressComponentsBeanList.get(i).getShort_name();
                                        Log.w(TAG, "Locality Short name ---->" + localityName);
                                        Log.w(TAG, "Locality City  short name ---->" + cityName);


                                    }

                                    if (typesList.contains("administrative_area_level_2")) {
                                        cityName = addressComponentsBeanList.get(i).getShort_name();
                                        //  CityName = cityName;
                                        Log.w(TAG, "City  short name ---->" + cityName);

                                    }
                                    if (typesList.contains("sublocality_level_1")) {
                                        sublocalityName = addressComponentsBeanList.get(i).getShort_name();
                                        Log.w(TAG, "sublocality_level_1  short name ---->" + cityName);

                                    }

                                }

                            }
                            if (AddressLine != null) {
                                tv_searchlocationaddress.setText(AddressLine);
                            } else if (sublocalityName != null) {
                                tv_searchlocationaddress.setText(sublocalityName);
                            } else if (localityName != null) {
                                tv_searchlocationaddress.setText(localityName);
                            } else if (currentplacename != null) {
                                tv_searchlocationaddress.setText(currentplacename);

                            } else {
                                if (cityName != null && !cityName.isEmpty()) {
                                    tv_searchlocationaddress.setText(cityName);

                                }
                            }


                        }
                    }
                }


            }

            @Override
            public void onFailure(@NotNull Call<GetAddressResultResponse> call, @NotNull Throwable t) {
                //avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }

}