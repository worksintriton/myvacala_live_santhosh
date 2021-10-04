package com.triton.myvacala.activities.parking.maps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.EditMapsActivity;
import com.triton.myvacala.activities.EditMyAddressActivity;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.activities.parking.account.ParkingLocationListActivity;
import com.triton.myvacala.adapter.LocationListAdapter;
import com.triton.myvacala.api.API;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.requestpojo.LocationEditRequest;
import com.triton.myvacala.responsepojo.GetAddressResultResponse;
import com.triton.myvacala.responsepojo.LocationEditResponse;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParkingEditMyAddressActivity extends FragmentActivity implements OnMapReadyCallback,
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

    @BindView(R.id.bottom_navigation_parking)
    BottomNavigationView bottom_navigation_parking;


    String latlng = "";

    double latitude = 0, longtitude =0;

    Marker mCurrLocationMarker;

    String CityName = "", AddressLine = "";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    String TAG = "ParkingEditMyAddressActivity";

    String customerid = "",state = "",country = "",postalcode = "",street = "";

    SessionManager session;

    String name = "", emailID = "",  mobile = "", type = "";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    String LocationType = "";

    String  flatno = "",nearbylandmark ="",locationnickname = "",locationtype ="";

     String id = "";
    private String pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_address_parking);

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


        toolbar_title.setText(getResources().getString(R.string.editmyaddress));
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
            if(latlng != null && !latlng.equalsIgnoreCase("null")) {

                String newString = latlng.replace("lat/lng:", "");
                Log.w(TAG, "latlng==" + newString);

                String latlngs = newString.trim().replaceAll("\\(", "").replaceAll("\\)", "").trim();
                Log.w(TAG, "latlngs==" + latlngs);

                String[] separated = latlngs.split(",");
                String lat = separated[0];
                String lon = separated[1];

                latitude = Double.parseDouble(lat);
                longtitude = Double.parseDouble(lon);

                // getAddress(latitude,longtitude);

                LatLng latLng = new LatLng(latitude, longtitude);
                getAddressResultResponse(latLng);
            }



            latitude = extras.getDouble("lat");
            longtitude = extras.getDouble("lon");






            Log.w(TAG,"lat"+latitude+" "+"lon :"+longtitude);

            CityName = extras.getString("cityname");
            street = extras.getString("street");
            state = extras.getString("state");
            country = extras.getString("country");
            pincode = extras.getString("pincode");
            CityName = extras.getString("cityname");
            AddressLine = extras.getString("address");


            flatno = extras.getString("flatno");
            nearbylandmark = extras.getString("nearbylandmark");
            locationnickname = extras.getString("nickname");
            LocationType = extras.getString("locationtype");

            if(flatno != null && !flatno.isEmpty()){
                edt_floatandbuildingno.setText(flatno);
            }

            if(nearbylandmark != null && !nearbylandmark.isEmpty()){
                edt_nearbylandmark.setText(nearbylandmark);
            }
            if(locationnickname != null && !locationnickname.isEmpty()){
                edt_pickname.setText(locationnickname);
            }



            txt_cityname.setText(CityName);
            if(AddressLine != null && !AddressLine.isEmpty()){
                txt_address.setText(AddressLine);

            }else{
                txt_address.setText(street+" "+state+" -"+pincode);
                postalcode = pincode;

            }

            if (getIntent() != null && getIntent().hasExtra("id")) {
                id = getIntent().getStringExtra("id");
            } else{
                id = LocationListAdapter.id;
            }


            if(LocationType != null){
                if(LocationType.equalsIgnoreCase("Home")){
                    LocationType = "Home";
                    changeBackgroundHome();
                }else if(LocationType.equalsIgnoreCase("Work")){
                    LocationType = "Work";
                    changeBackgroundWork();
                }else if(LocationType.equalsIgnoreCase("Others")){
                    LocationType = "Others";
                    changeBackgroundOthers();
                }
            }



        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        bottom_navigation_parking.setSelectedItemId(R.id.account);
        bottom_navigation_parking.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        Intent intent = new Intent(ParkingEditMyAddressActivity.this, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

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
                gotoEditmapsPage();
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
            if (new ConnectionDetector(ParkingEditMyAddressActivity.this).isNetworkAvailable(ParkingEditMyAddressActivity.this)) {
                locationEditResponseCall();
                }


            }

        }

    public void locationEditResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationEditResponse> call = apiInterface.locationEditResponseCall(RestUtils.getContentType(),locationEditRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LocationEditResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationEditResponse> call, @NotNull Response<LocationEditResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "LocationEditResponse" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    Log.w(TAG, "Not null");

                    if(response.body().getCode() == 200){
                        Log.w(TAG, "Not null IF");
                        Intent i = new Intent(ParkingEditMyAddressActivity.this, ParkingLocationListActivity.class);
                        i.putExtra("fromactivity",TAG);
                        startActivity(i);
                        finish();

                    }else{
                        Log.w(TAG, "Not null else");
                        assert response.body() != null;
                        showErrorLoading(response.body().getMessage());
                    }
                }

            }






            @Override
            public void onFailure(@NotNull Call<LocationEditResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"LocationEditResponseflr"+t.getMessage());
            }
        });

    }
    private LocationEditRequest locationEditRequest() {
        /*
         * _id : 5f05d8fef3090625a91f40c6
         * City : Chennai
         * State : TamilNadu
         * Country : India
         * Pincode : 600100
         * Street : Koil street
         * lat : 15.2345
         * long : 70.546
         * NearBy_LandMark : Pooja stores
         * Location_NickName : MGM
         * Flat_No : 24-f
         * Location_type : Office
         */

        LocationEditRequest locationEditRequest = new LocationEditRequest();
        locationEditRequest.setLocation_id(id);
        locationEditRequest.setCity(CityName);
        locationEditRequest.setState(state);
        locationEditRequest.setCountry(country);
        locationEditRequest.setPincode(postalcode);
        locationEditRequest.setStreet(street);
        locationEditRequest.setLat(latitude);
        locationEditRequest.setLongX(longtitude);
        locationEditRequest.setNearBy_LandMark(edt_nearbylandmark.getText().toString());
        locationEditRequest.setLocation_NickName(edt_pickname.getText().toString());
        locationEditRequest.setFlat_No(edt_floatandbuildingno.getText().toString());
        locationEditRequest.setLocation_type(LocationType);

        Log.w(TAG," locationEditRequest"+ new Gson().toJson(locationEditRequest));
        return locationEditRequest;
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

    private void getAddressResultResponse(LatLng latLng) {
        Log.w(TAG,"GetAddressResultResponse-->"+latLng);
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
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
                avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"GetAddressResultResponse" + new Gson().toJson(response.body()));


                assert response.body() != null;
                String compundcode = response.body().getPlus_code().getCompound_code();
                String[] separated = compundcode.split(",");
                String placesname = separated[0];
                String[] splitData = placesname.split("\\s", 2);
                String code = splitData[0];
                String currentplacename = splitData[1];
                Log.w(TAG,"code-->"+code+"currentplacename : "+currentplacename);



                String localityName = null;
                String  cityName = null;
                String  sublocalityName = null;
                String  postalCode = null;




                List<GetAddressResultResponse.ResultsBean> getAddressResultResponseList;
                getAddressResultResponseList = response.body().getResults();
                if(getAddressResultResponseList.size()>0){
                    AddressLine = getAddressResultResponseList.get(0).getFormatted_address();
                    Log.w(TAG,"FormateedAddress-->"+AddressLine);

                }
                List<GetAddressResultResponse.ResultsBean.AddressComponentsBean> addressComponentsBeanList = response.body().getResults().get(0).getAddress_components();
                if (addressComponentsBeanList.size() > 0 ) {
                    for(int i=0; i<addressComponentsBeanList.size();i++){
                        Log.w(TAG,"addressComponentsBeanList size : "+addressComponentsBeanList.size());

                        for(int j=0;j<addressComponentsBeanList.get(i).getTypes().size();j++){
                            Log.w(TAG,"getTypes size : "+addressComponentsBeanList.get(i).getTypes().size());

                            Log.w(TAG,"TYPES-->"+addressComponentsBeanList.get(i).getTypes());
                            List<String> typesList = addressComponentsBeanList.get(i).getTypes();

                            if(typesList.contains("postal_code")){
                                postalCode = addressComponentsBeanList.get(i).getShort_name();
                                postalcode = postalCode;
                                Log.w(TAG,"Postal Short name ---->"+postalCode);

                            }
                            if(typesList.contains("locality")){
                                localityName = addressComponentsBeanList.get(i).getShort_name();
                                Log.w(TAG,"Locality Short name ---->"+localityName);

                            }

                            if(typesList.contains("administrative_area_level_2")){
                                cityName = addressComponentsBeanList.get(i).getShort_name();
                                CityName = cityName;
                                Log.w(TAG,"City  short name ---->"+cityName);

                            }
                            if(typesList.contains("administrative_area_level_1")){
                                state = addressComponentsBeanList.get(i).getLong_name();

                                Log.w(TAG,"state  Long name ---->"+state);

                            }
                            if(typesList.contains("sublocality_level_1")){
                                sublocalityName = addressComponentsBeanList.get(i).getShort_name();
                                Log.w(TAG,"sublocality_level_1  short name ---->"+sublocalityName);

                            }
                            if(typesList.contains("route")){
                                street = addressComponentsBeanList.get(i).getLong_name();
                                Log.w(TAG,"route  long name ---->"+street);

                            }

                        }

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

    private void gotoEditmapsPage() {
        Intent i = new Intent(ParkingEditMyAddressActivity.this, ParkingEditMapsActivity.class);
        i.putExtra("id",id);
        i.putExtra("cityname",CityName);
        i.putExtra("state",state);
        i.putExtra("country",country);
        i.putExtra("street",street);
        i.putExtra("pincode",pincode);
        i.putExtra("flatno",flatno);
        i.putExtra("nearbylandmark",nearbylandmark);
        i.putExtra("nickname",locationnickname);
        i.putExtra("locationtype",locationtype);
        Bundle b = new Bundle();
        b.putDouble("lat",latitude);
        b.putDouble("lon", longtitude);
        i.putExtras(b);
        startActivity(i);

    }




}

