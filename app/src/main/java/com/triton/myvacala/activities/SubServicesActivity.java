package com.triton.myvacala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.SubServicesPageAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.SubServiceAddandRemoveListener;
import com.triton.myvacala.requestpojo.AddingCartRequest;
import com.triton.myvacala.requestpojo.RemovingCartRequest;
import com.triton.myvacala.requestpojo.SubServiceListRequest;
import com.triton.myvacala.responsepojo.AddingCartResponse;
import com.triton.myvacala.responsepojo.MainServiceGetListResponse;
import com.triton.myvacala.responsepojo.RemovingCartResponse;
import com.triton.myvacala.responsepojo.SubServiceListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubServicesActivity extends AppCompatActivity implements SubServiceAddandRemoveListener, View.OnClickListener {

    @BindView(R.id.cv_vehicleimage)
    ImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.btn_vehiclefueltype)
    Button btn_vehiclefueltype;

    @BindView(R.id.txt_location)
    TextView txt_location;

    @BindView(R.id.txt_address)
    TextView txt_address;

    @BindView(R.id.rv_services)
    RecyclerView rv_services;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    String active_tag = "1";

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.ll_proceedtocart)
    LinearLayout ll_proceedtocart;

    @BindView(R.id.ll_city)
    LinearLayout ll_city;

    @BindView(R.id.btnnotifications)
    Button btnnotifications;



    String TAG = "SubServicesActivity";

    private Context mContext;
    private SharedPreferences preferences;


    SessionManager session;


    private List<SubServiceListResponse.DataBean> subServiceListResponseList = new ArrayList<>();
    private List<String> stringArrayList = new ArrayList<>();
    SubServicesPageAdapter subServicesPageAdapter;

    String serviceid,servicename,masterservicename,customerid,vehicletypename ;
    String city = "",street = "";
    String vehicleImage,vehicleName, vehicleModelName,fuelType;
    private ArrayList<SubServiceListResponse.CustomerVehicleDataBean> customerVehicleDataBeanList = null;


    String twowheelervehicleid = null,fourwheelervehicleid = null,masterserviceid = null;


    String selectedVehicleType, selectedVehicleId;

    private String _id;
    private String Customer_id;
    private String Vehicle_Image;
    private String Vehicletype_id;
    private String Vehicletype_Name;
    private String Vehicle_Brand_id;
    private String Vehicle_Brand_Name;
    private String Vehicle_Name_id;
    private String Vehicle_Name;
    private String Year_of_Manufacture;
    private String Vehicle_No;
    private String Fuel_Type_id;
    private String Fuel_Type_Name;
    private String Fuel_Type_Background_Color;
    private String Vehicle_Model_id;
    private String Vehicle_Model_Name;
    private String Status;
    private String updatedAt;
    private String createdAt;
    private int __v;


    String headerVehicleimg,headerVehicleName,headerVehicleBrandName,headerVehicleFuelTypeName,headerVehicleFuelTypeBackgroundcolor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_services);
        Log.w(TAG,"onCreate-->");

        ButterKnife.bind(this);

        toolbar_title.setText(getResources().getString(R.string.subservicespages));

        mContext = SubServicesActivity.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        avi_indicator.setVisibility(View.GONE);
        ll_proceedtocart.setVisibility(View.GONE);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        customerid = user.get(SessionManager.KEY_ID);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            twowheelervehicleid = extras.getString("twowheelervehicleid");
            fourwheelervehicleid = extras.getString("fourwheelervehicleid");
            masterserviceid = extras.getString("masterserviceid");


            city = extras.getString("city");
            street = extras.getString("street");

            vehicleImage = extras.getString("vehicleImage");
            vehicleName = extras.getString("vehicleName");
            vehicleModelName = extras.getString("vehicleModelName");
            fuelType = extras.getString("fuelType");
            Log.w(TAG,"vehicleImage : "+vehicleImage+" "+"vehicleName : "+vehicleName+" "+"vehicleModelName : "+vehicleModelName+" "+"fuelType : "+fuelType);

            setHeaderData();

            //vehicletypeid = extras.getString("vehicletypeid");
            serviceid = extras.getString("serviceid");
            servicename = extras.getString("servicename");
            masterservicename = extras.getString("masterservicename");

            vehicletypename = extras.getString("vehicletypename");
            //customerVehicleDataBeanList = (ArrayList<MainServiceGetListResponse.DataBean.CustomerVehicleDataBean>) getIntent().getSerializableExtra("customervehicledatabeanlist");
            /*if( customerVehicleDataBeanList != null) {
                Log.w(TAG, "vehicletypename: " + vehicletypename + "customerVehicleDataBeanList size : " + customerVehicleDataBeanList.size());
            }*/
            Log.w(TAG,"customerid : "+customerid+" "+" "+"serviceid : "+serviceid+" "+"servicename : "+servicename+" "+"masterservicename : "+masterservicename);




            selectedVehicleType = extras.getString("selectedVehicleType");
            selectedVehicleId = extras.getString("selectedVehicleId");
            if(selectedVehicleId != null) {
                getVehicleData(selectedVehicleId);
            }
            Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);
            if(customerVehicleDataBeanList != null){
                for(int i=0;i<customerVehicleDataBeanList.size();i++){
                    assert selectedVehicleId != null;
                    if(selectedVehicleId.equalsIgnoreCase(customerVehicleDataBeanList.get(i).getVehicletype_id())){
                        _id = customerVehicleDataBeanList.get(i).get_id();
                        Customer_id = customerVehicleDataBeanList.get(i).getCustomer_id();
                        Vehicle_Image = customerVehicleDataBeanList.get(i).getVehicle_Image();
                        Vehicletype_id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                        Vehicletype_Name = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                        Vehicle_Brand_id = customerVehicleDataBeanList.get(i).getVehicle_Brand_id();
                        Vehicle_Brand_Name = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                        Vehicle_Name_id = customerVehicleDataBeanList.get(i).getVehicle_Name_id();
                        Vehicle_Name = customerVehicleDataBeanList.get(i).getVehicle_Name();
                        Year_of_Manufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                        Vehicle_No = customerVehicleDataBeanList.get(i).getVehicle_No();
                        Fuel_Type_id = customerVehicleDataBeanList.get(i).getFuel_Type_id();
                        Fuel_Type_Name = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                        Fuel_Type_Background_Color = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                        Vehicle_Model_id = customerVehicleDataBeanList.get(i).getVehicle_Model_id();
                        Vehicle_Model_Name = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                        Status = customerVehicleDataBeanList.get(i).getStatus();
                        updatedAt  = customerVehicleDataBeanList.get(i).getUpdatedAt();
                        createdAt  = customerVehicleDataBeanList.get(i).getCreatedAt();
                        __v  = customerVehicleDataBeanList.get(i).get__v();

                        Log.w(TAG,"Vehicletype_Name----->"+Vehicletype_Name+"Vehicletype_id---->"+Vehicletype_id);

                    }
                }
            }


            Log.w(TAG,"selectedVehicleType--->"+selectedVehicleType+" "+"selectedVehicleId : "+selectedVehicleId);


            if (new ConnectionDetector(SubServicesActivity.this).isNetworkAvailable(SubServicesActivity.this)) {
                subServiceListResponseCall();
            }











        }

        btnnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubServicesActivity.this, NotificationActivity.class);
                intent.putExtra("fromactivity","HomeFragment");
                startActivity(intent);
            }
        });


        cv_vehicleimage.setOnClickListener(this);


        bottom_navigation_view.setSelectedItemId(R.id.home);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_proceedtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG,"ll_proceedtocart-->"+new Gson().toJson(customerVehicleDataBeanList));
               Intent intent = new Intent(SubServicesActivity.this,CartActivity.class);
                intent.putExtra("vehicletypeid",selectedVehicleId);
                intent.putExtra("serviceid",serviceid);
                intent.putExtra("city",city);
                intent.putExtra("street",street);
                intent.putExtra("vehicleImage", vehicleImage);
                intent.putExtra("vehicleName", vehicleName);
                intent.putExtra("vehicleModelName", vehicleModelName);
                intent.putExtra("fuelType", fuelType);
                intent.putExtra("vehicletypename", vehicletypename);
                intent.putExtra("customervehicledatabeanlist",customerVehicleDataBeanList );
                intent.putExtra("servicename", servicename);
                intent.putExtra("masterservicename", masterservicename);
                intent.putExtra("selectedVehicleId", selectedVehicleId);
                intent.putExtra("twowheelervehicleid",twowheelervehicleid);
                intent.putExtra("fourwheelervehicleid",fourwheelervehicleid);
                intent.putExtra("masterserviceid",masterserviceid);
                startActivity(intent);
            }
        });

        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(SubServicesActivity.this,LocationListActivity.class));
            }
        });

    }

    private void getVehicleData(String selectedVehicleId) {
        if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
            for(int i =0; i<customerVehicleDataBeanList.size();i++) {
                String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                if (selectedVehicleId.equalsIgnoreCase(id)) {
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                    Log.w(TAG,"headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName);
                }

            }
            setHeaderVehicleData();
        }
        if(customerVehicleDataBeanList != null){
            for(int i=0;i<customerVehicleDataBeanList.size();i++){
                if(selectedVehicleId.equalsIgnoreCase(customerVehicleDataBeanList.get(i).getVehicletype_id())){
                    _id = customerVehicleDataBeanList.get(i).get_id();
                    Customer_id = customerVehicleDataBeanList.get(i).getCustomer_id();
                    Vehicle_Image = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    Vehicletype_id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    Vehicletype_Name = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                    Vehicle_Brand_id = customerVehicleDataBeanList.get(i).getVehicle_Brand_id();
                    Vehicle_Brand_Name = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    Vehicle_Name_id = customerVehicleDataBeanList.get(i).getVehicle_Name_id();
                    Vehicle_Name = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    Year_of_Manufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                    Vehicle_No = customerVehicleDataBeanList.get(i).getVehicle_No();
                    Fuel_Type_id = customerVehicleDataBeanList.get(i).getFuel_Type_id();
                    Fuel_Type_Name = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    Fuel_Type_Background_Color = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                    Vehicle_Model_id = customerVehicleDataBeanList.get(i).getVehicle_Model_id();
                    Vehicle_Model_Name = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                    Status = customerVehicleDataBeanList.get(i).getStatus();
                    updatedAt  = customerVehicleDataBeanList.get(i).getUpdatedAt();
                    createdAt  = customerVehicleDataBeanList.get(i).getCreatedAt();
                    __v  = customerVehicleDataBeanList.get(i).get__v();

                    Log.w(TAG,"Vehicletype_Name----->"+Vehicletype_Name+"Vehicletype_id---->"+Vehicletype_id);

                }
            }
        }

    }
    private void setHeaderVehicleData() {



        if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {

            Glide.with(SubServicesActivity.this)
                    .load(headerVehicleimg)
                    .into(cv_vehicleimage);

        }
        else{
            Glide.with(SubServicesActivity.this)
                    .load(R.drawable.logo)
                    .into(cv_vehicleimage);

        }
        if(headerVehicleName != null){
            txt_vehiclename.setText(headerVehicleName);
        }else{
            txt_vehiclename.setText("");
        }
        if(headerVehicleFuelTypeName != null){
            btn_vehiclefueltype.setText(headerVehicleFuelTypeName);
        }else{
            btn_vehiclefueltype.setText("");
        }

        if(headerVehicleFuelTypeBackgroundcolor != null){
            btn_vehiclefueltype.setBackgroundColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            btn_vehiclefueltype.setBackgroundResource(R.drawable.tags_rounded_corners);
            GradientDrawable gd = (GradientDrawable) btn_vehiclefueltype.getBackground();
            gd.setColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            gd.setCornerRadius(10);
            gd.setStroke(4, Color.WHITE);

        }

    }
    private void setHeaderData() {

        if(city != null){
            txt_location.setText(city);
        }else{
            txt_location.setText("");
        }
        if(street != null){
            txt_address.setText(street);
        }else{
            txt_address.setText("");
        }

       /* if (vehicleImage != null && !vehicleImage.isEmpty()) {

            Glide.with(SubServicesActivity.this)
                    .load(vehicleImage)
                    .into(cv_vehicleimage);

        }
        else{
            Glide.with(SubServicesActivity.this)
                    .load(R.drawable.logo)
                    .into(cv_vehicleimage);

        }
        if(vehicleName != null){
            txt_vehiclename.setText(vehicleName);
        }
        else{
            txt_vehiclename.setText("");
        }
        if(fuelType != null){
            btn_vehiclefueltype.setText(fuelType);
        }
        else{
            btn_vehiclefueltype.setText("");
        }*/

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                //active = homeFragment;
                active_tag = "1";
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
    };
    public void callDirections(String tag){
        Intent intent = new Intent(SubServicesActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }



    public void subServiceListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SubServiceListResponse> call = apiInterface.subServiceListResponseCall(RestUtils.getContentType(),subServiceListRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SubServiceListResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubServiceListResponse> call, @NotNull Response<SubServiceListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"SubServiceListResponse" + new Gson().toJson(response.body()));

                assert response.body() != null;
                Log.w(TAG,"Code--->"+response.body().getCode());
                if(200 == response.body().getCode()){
                    Log.w(TAG,"if------>");
                    assert response.body() != null;
                    customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                    getVehicleData(selectedVehicleId);
                    if(response.body().getData().isEmpty()){
                        rv_services.setVisibility(View.GONE);
                        tv_norecords.setVisibility(View.VISIBLE);
                        ll_proceedtocart.setVisibility(View.GONE);
                    }
                    if(response.body().getLocationData() != null){
                        session.createLocationDetails(
                                response.body().getLocationData().getCity(),
                                response.body().getLocationData().getState(),
                                response.body().getLocationData().getCountry(),
                                response.body().getLocationData().getPincode(),
                                response.body().getLocationData().getStreet(),
                                response.body().getLocationData().getLocation_NickName(),
                                response.body().getLocationData().getFlat_No() );
                        city = response.body().getLocationData().getCity();
                        street = response.body().getLocationData().getLocation_NickName()+" "+ response.body().getLocationData().getStreet();
                        Log.w(TAG,"createLocationDetails--->"+"city:"+city+"street :"+street);
                        if(city != null){
                            txt_location.setText(city);
                        }else{
                            txt_location.setText("");
                        }
                        if(street != null){
                            txt_address.setText(street);
                        }else{
                            txt_address.setText("");
                        }
                    }


                    subServiceListResponseList = response.body().getData();
                    if (subServiceListResponseList.size() > 0 ) {
                        stringArrayList = subServiceListResponseList.get(0).getSub_ser_Spec1();
                        if(stringArrayList.size()>0){
                            ll_proceedtocart.setVisibility(View.VISIBLE);
                            rv_services.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            setViewSubService();
                        }else{
                            ll_proceedtocart.setVisibility(View.GONE);
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    Log.w(TAG,"else------>");
                    rv_services.setVisibility(View.GONE);
                    tv_norecords.setVisibility(View.VISIBLE);
                    ll_proceedtocart.setVisibility(View.GONE);

                    customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                    getVehicleData(selectedVehicleId);
                    if(response.body().getLocationData() != null){
                        session.createLocationDetails(
                                response.body().getLocationData().getCity(),
                                response.body().getLocationData().getState(),
                                response.body().getLocationData().getCountry(),
                                response.body().getLocationData().getPincode(),
                                response.body().getLocationData().getStreet(),
                                response.body().getLocationData().getLocation_NickName(),
                                response.body().getLocationData().getFlat_No() );
                        city = response.body().getLocationData().getCity();
                        street = response.body().getLocationData().getLocation_NickName()+" "+ response.body().getLocationData().getStreet();
                        Log.w(TAG,"createLocationDetails--->"+"city:"+city+"street :"+street);
                        if(city != null){
                            txt_location.setText(city);
                        }else{
                            txt_location.setText("");
                        }
                        if(street != null){
                            txt_address.setText(street);
                        }else{
                            txt_address.setText("");
                        }
                    }


                }

              /*  assert response.body() != null;
                if(response.body().getData().isEmpty()){
                    rv_services.setVisibility(View.GONE);
                    tv_norecords.setVisibility(View.VISIBLE);
                }*/




            }


            @Override
            public void onFailure(@NotNull Call<SubServiceListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SubServiceListResponseflr"+t.getMessage());
            }
        });

    }
    private SubServiceListRequest subServiceListRequest() {
        /*
         * Service_id : 5f1acce99e65c148f3554db2
         * Customer_id : 5f0814a3abce73672359e092
         * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
         */

        SubServiceListRequest subServiceListRequest = new SubServiceListRequest();
        subServiceListRequest.setService_id(serviceid);
        subServiceListRequest.setCustomer_id(customerid);
        subServiceListRequest.setVehicletype_id(selectedVehicleId);
        Log.w(TAG,"SubServiceListRequest"+ new Gson().toJson(subServiceListRequest));
        return subServiceListRequest;
    }

    private void setViewSubService() {
        rv_services.setLayoutManager(new LinearLayoutManager(this));
        rv_services.setItemAnimator(new DefaultItemAnimator());

        subServicesPageAdapter = new SubServicesPageAdapter(mContext, subServiceListResponseList,stringArrayList, rv_services,selectedVehicleId,serviceid,city,street,vehicleImage,vehicleName,vehicleModelName,fuelType,customerVehicleDataBeanList,SubServicesActivity.this,masterservicename,vehicletypename,masterserviceid,twowheelervehicleid,fourwheelervehicleid,selectedVehicleType,selectedVehicleId);
        rv_services.setAdapter(subServicesPageAdapter);

        subServicesPageAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > stringArrayList.size()) {
                Log.e("haint", "Load More");
            }


        });






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // callDirectionsAddVehicle("3");

        Intent intent = new Intent(getApplicationContext(),PopularServiceActivity.class);
        intent.putExtra("twowheelervehicleid",twowheelervehicleid);
        intent.putExtra("fourwheelervehicleid",fourwheelervehicleid);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("city",city);
        intent.putExtra("street",street);
        intent.putExtra("vehicleImage", vehicleImage);
        intent.putExtra("vehicleName", vehicleName);
        intent.putExtra("vehicleModelName", vehicleModelName);
        intent.putExtra("fuelType", fuelType);
        intent.putExtra("masterservicename", masterservicename);
        intent.putExtra("customervehicledatabeanlist", customerVehicleDataBeanList);

        intent.putExtra("selectedVehicleId",selectedVehicleId);
        intent.putExtra("selectedVehicleType",selectedVehicleType);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("fromactivity", "SubServicesActivity");
        intent.putExtra("serviceid", serviceid);
        intent.putExtra("servicename", servicename);
        intent.putExtra("masterservicename", masterservicename);
        startActivity(intent );
        finish();
    }

    @Override
    public void onItemCheck(String type, String subserviceid) {
        if(type != null){
            if(type.equalsIgnoreCase("ADD")){
                if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext()))
                    Log.w(TAG,"onItemCheck--->"+type);
                    addingCartResponseCall(subserviceid);
            }
            else if(type.equalsIgnoreCase("REMOVE")){
                if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext()))
                    Log.w(TAG,"onItemCheck--->"+type);
                    removingCartResponseCall(subserviceid);
            }
        }

    }

    private void addingCartResponseCall(String subserviceid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddingCartResponse> call = apiInterface.addingCartResponseCall(RestUtils.getContentType(),addingCartRequest(subserviceid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<AddingCartResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddingCartResponse> call, @NotNull Response<AddingCartResponse> response) {
                Log.w(TAG,"AddingCartResponse"+ "--->" + new Gson().toJson(response.body()));
                  avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if (new ConnectionDetector(SubServicesActivity.this).isNetworkAvailable(SubServicesActivity.this)) {
                            subServiceListResponseCall();
                        }
                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<AddingCartResponse> call, @NotNull Throwable t) {
               avi_indicator.smoothToHide();

                Log.w(TAG,"AddingCartResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AddingCartRequest addingCartRequest(String subserviceid) {

        AddingCartRequest addingCartRequest = new AddingCartRequest();
        addingCartRequest.setSub_service_id(subserviceid);
        addingCartRequest.setCustomer_id(customerid);
        addingCartRequest.setVehicletype_id(selectedVehicleId);
        List<AddingCartRequest.VehicleDetailsBean> Vehicle_details = new ArrayList<>();
        AddingCartRequest.VehicleDetailsBean vehicleDetailsBean = new AddingCartRequest.VehicleDetailsBean();
        vehicleDetailsBean.set_id(_id);
        vehicleDetailsBean.setCustomer_id(Customer_id);
        vehicleDetailsBean.setVehicle_Image(Vehicle_Image);
        vehicleDetailsBean.setVehicletype_id(Vehicletype_id);
        vehicleDetailsBean.setVehicletype_Name(Vehicletype_Name);
        vehicleDetailsBean.setVehicle_Brand_id(Vehicle_Brand_id);
        vehicleDetailsBean.setVehicle_Brand_Name(Vehicle_Brand_Name);
        vehicleDetailsBean.setVehicle_Name_id(Vehicle_Name_id);
        vehicleDetailsBean.setVehicle_Name(Vehicle_Name);
        vehicleDetailsBean.setYear_of_Manufacture(Year_of_Manufacture);
        vehicleDetailsBean.setVehicle_No(Vehicle_No);
        vehicleDetailsBean.setFuel_Type_id(Fuel_Type_id);
        vehicleDetailsBean.setFuel_Type_Name(Fuel_Type_Name);
        vehicleDetailsBean.setFuel_Type_Background_Color(Fuel_Type_Background_Color);
        vehicleDetailsBean.setFuel_Type_Background_Color(Fuel_Type_Background_Color);
        vehicleDetailsBean.setVehicle_Model_id(Vehicle_Model_id);
        vehicleDetailsBean.setVehicle_Model_Name(Vehicle_Model_Name);
        vehicleDetailsBean.setStatus(Status);
        vehicleDetailsBean.setUpdatedAt(updatedAt);
        vehicleDetailsBean.setCreatedAt(createdAt);
        vehicleDetailsBean.set__v(__v);
        Vehicle_details.add(vehicleDetailsBean);
        addingCartRequest.setVehicle_details(Vehicle_details);
        Log.w(TAG,"AddingCartRequest"+ "--->" + new Gson().toJson(addingCartRequest));
        return addingCartRequest;
    }



    private void removingCartResponseCall(String subserviceid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<RemovingCartResponse> call = apiInterface.removingCartResponseCall(RestUtils.getContentType(),removingCartRequest(subserviceid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<RemovingCartResponse>() {
            @Override
            public void onResponse(@NotNull Call<RemovingCartResponse> call, @NotNull Response<RemovingCartResponse> response) {
                Log.w(TAG,"RemovingCartResponse"+ "--->" + new Gson().toJson(response.body()));
                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if (new ConnectionDetector(SubServicesActivity.this).isNetworkAvailable(SubServicesActivity.this)) {
                            subServiceListResponseCall();
                        }

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<RemovingCartResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"RemovingCartResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private RemovingCartRequest removingCartRequest(String subserviceid) {
        /*
         * Service_id : 5f1eaf8cab6576455baf5f72
         * Customer_id : 5f181fbc609f4e233fe26106
         */
        RemovingCartRequest removingCartRequest = new RemovingCartRequest();
        removingCartRequest.setService_id(subserviceid);
        removingCartRequest.setCustomer_id(customerid);
        Log.w(TAG,"RemovingCartRequest"+ "--->" + new Gson().toJson(removingCartRequest));
        return removingCartRequest;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_vehicleimage:
                callDirectionsAddVehicle("3");
                break;
        }
    }

    public void callDirectionsAddVehicle(String tag){
        Intent intent = new Intent(SubServicesActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        intent.putExtra("selectedVehicleId",selectedVehicleId);
        intent.putExtra("selectedVehicleType",selectedVehicleType);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("fromactivity", "SubServicesActivity");
        intent.putExtra("serviceid", serviceid);
        intent.putExtra("servicename", servicename);
        intent.putExtra("masterservicename", masterservicename);

        startActivity(intent);
        Log.w(TAG,"callDirections selectedVehicleId : "+selectedVehicleId+" "+"selectedVehicleType : "+selectedVehicleType);
        finish();

    }


}