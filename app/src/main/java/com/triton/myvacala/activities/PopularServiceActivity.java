package com.triton.myvacala.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.MainServicesHomeAdapter;

import com.triton.myvacala.adapter.PopularServicesAdapter;
import com.triton.myvacala.adapter.ViewPagerMainServiceAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.requestpojo.MainServiceGetListRequest;
import com.triton.myvacala.responsepojo.MainServiceGetListResponse;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularServiceActivity extends AppCompatActivity implements View.OnClickListener {

    String twowheelervehicleid ,fourwheelervehicleid ,masterserviceid ;
    String twowheelerstatus = "" ,fourwheelerstatus = "" ,vehicletypename;

    String TAG = "PopularServiceActivity";

    private Context mContext;
    private SharedPreferences preferences;

    @BindView(R.id.cv_vehicleimage)
    ImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.btn_vehiclefueltype)
    Button btn_vehiclefueltype;

    @BindView(R.id.togglebutton)
    ToggleButton toggleButton;


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

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    @BindView(R.id.bottom_popularservice_view)
    BottomNavigationView bottom_popularservice_view;

    @BindView(R.id.txt_lookmore)
    TextView txt_lookmore;

    String active_tag = "1";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;


    ViewPagerMainServiceAdapter viewPagerMainServiceAdapter;

    MainServiceGetListResponse homeBannerResponse;

    private List<MainServiceGetListResponse.DataBean.BannerlistBean> listHomeBannerResponse = new ArrayList<>();


    String city = "",street = "";
    String vehicleImage,vehicleName, vehicleModelName,fuelType;
    String masterservicename;

    @BindView(R.id.ll_city)
    LinearLayout ll_city;


    @BindView(R.id.ll_popularservice)
    LinearLayout ll_popularservice;

    @BindView(R.id.rv_popularservices)
    RecyclerView rv_popularservices;

    @BindView(R.id.tv_nopopularservice)
    TextView tv_nopopularservice;

    @BindView(R.id.rlviewpager)
    RelativeLayout rlviewpager;

    @BindView(R.id.btnnotifications)
    Button btnnotifications;






    MainServiceGetListResponse servicesBean;
    private List<MainServiceGetListResponse.DataBean.MainserviceslistBean> mainserviceslistBeanList = new ArrayList<>();
    MainServicesHomeAdapter mainServicesHomeAdapter;

    MainServiceGetListResponse.DataBean.PopularserviceBean popularserviceBean;
    private List<MainServiceGetListResponse.DataBean.PopularserviceBean> popularserviceBeanList = new ArrayList<>();
    PopularServicesAdapter popularServicesAdapter;


    SessionManager session;
    String name,customerid;
    private ArrayList<MainServiceGetListResponse.DataBean.CustomerVehicleDataBean> customerVehicleDataBeanList = null;
    private ArrayList<MasterServiceGetlistResponse.CustomerVehicleDataBean> customerVehicleDataBeanList1 = null;

    private ArrayList<MainServiceGetListResponse.DataBean.LocationDataBean> locationDataBeanArrayList = null;

    String selectedVehicleType,selectedVehicleId;

    String headerVehicleimg,headerVehicleName,headerVehicleBrandName,headerVehicleFuelTypeName,headerVehicleFuelTypeBackgroundcolor;

    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;
    private String fromactivity;

    private int cart;
    private String alertmessage;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_service);

        ButterKnife.bind(this);
        mContext = PopularServiceActivity.this;

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        session = new SessionManager(mContext);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid =  user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+customerid);

        avi_indicator.setVisibility(View.GONE);
        ll_popularservice.setVisibility(View.GONE);
        bottom_popularservice_view.setVisibility(View.GONE);
        btn_vehiclefueltype.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            twowheelervehicleid = extras.getString("twowheelervehicleid");
            fourwheelervehicleid = extras.getString("fourwheelervehicleid");
            masterserviceid = extras.getString("masterserviceid");

            Log.w(TAG,"extras twowheelervehicleid :"+twowheelervehicleid+" "+"fourwheelervehicleid : "+fourwheelervehicleid+" "+"masterserviceid : "+masterserviceid);


            city = extras.getString("city");
            street = extras.getString("street");

            vehicleImage = extras.getString("vehicleImage");
            vehicleName = extras.getString("vehicleName");
            vehicleModelName = extras.getString("vehicleModelName");
            fuelType = extras.getString("fuelType");
            masterservicename = extras.getString("masterservicename");

            Log.w(TAG,"customerVehicleDataBeanList"+new Gson().toJson(customerVehicleDataBeanList));
            selectedVehicleId = extras.getString("selectedVehicleId");
            Log.w(TAG,"selectedVehicleId : "+selectedVehicleId);


            //customerVehicleDataBeanList =  getIntent().getParcelableArrayListExtra("customervehicledatabeanlist");
            customerVehicleDataBeanList1= (ArrayList<MasterServiceGetlistResponse.CustomerVehicleDataBean>) getIntent().getSerializableExtra("customerVehicleDataBeanList");
            if(fourwheelervehicleid != null){
                getVehicleData1(twowheelervehicleid,fourwheelervehicleid);

                if(!fourwheelerstatus.isEmpty()){
                    cv_vehicleimage.setVisibility(View.VISIBLE);
                    txt_vehiclename.setVisibility(View.VISIBLE);
                    btn_vehiclefueltype.setVisibility(View.VISIBLE);
                    Log.w(TAG,"selectedVehicleId if--->"+selectedVehicleId);
                    vehicletypename = "Four Wheeler";
                    selectedVehicleType = vehicletypename;
                    toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);
                    mainServiceGetListResponseCall(selectedVehicleId);


                }
                else{
                    vehicletypename = "Two Wheeler";
                    selectedVehicleType = vehicletypename;
                    cv_vehicleimage.setVisibility(View.VISIBLE);
                    txt_vehiclename.setVisibility(View.VISIBLE);
                    btn_vehiclefueltype.setVisibility(View.VISIBLE);
                    Log.w(TAG,"selectedVehicleId else--->"+selectedVehicleId);
                    toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
                    toggleButton.setChecked(false);
                    mainServiceGetListResponseCall(selectedVehicleId);


                }
            }



            if(selectedVehicleId != null){
                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                    vehicletypename = "Four Wheeler";
                    selectedVehicleType = vehicletypename;
                    // selectedVehicleId = fourwheelervehicleid;
                    Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);
                    //mainServiceGetListResponseCall(selectedVehicleId);
                }
                fromactivity = extras.getString("fromactivity");
                Log.w(TAG,"fromactivity--->"+fromactivity);
                if(fromactivity != null){
                    if(fromactivity.equalsIgnoreCase("SubServicesActivity")){
                        selectedVehicleType = extras.getString("selectedVehicleType");
                        Log.w(TAG,"selectedVehicleType--->"+selectedVehicleType);
                        if(Objects.requireNonNull(selectedVehicleType).equalsIgnoreCase("Four Wheeler")){
                            toggleButton.setChecked(true);
                            vehicletypename = "Four Wheeler";
                            selectedVehicleType = vehicletypename;
                            //Button is ON
                            Log.w(TAG,"Car");
                            Log.w(TAG,"four wheeler clicked :"+selectedVehicleId);
                            toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);



                        }
                        else{
                            vehicletypename = "Two Wheeler";
                            selectedVehicleType = vehicletypename;
                            toggleButton.setChecked(false);
                            Log.w(TAG,"Bikes");
                            Log.w(TAG,"Two wheeler clicked :"+selectedVehicleId);
                            toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);




                        }
                    }
                }
                /*if(selectedVehicleId != null) {
                    getVehicleData(selectedVehicleId);
                }*/
            }
            else{
                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                    //vehicletypename = "Four Wheeler";
                   // selectedVehicleType = vehicletypename;
                   // selectedVehicleId = fourwheelervehicleid;
                    Log.w(TAG,"fourwheelerstatus---->"+fourwheelerstatus);
                    if(!fourwheelerstatus.isEmpty()){
                        vehicletypename = "Four Wheeler";
                        selectedVehicleType = vehicletypename;
                        cv_vehicleimage.setVisibility(View.VISIBLE);
                        txt_vehiclename.setVisibility(View.VISIBLE);
                        btn_vehiclefueltype.setVisibility(View.VISIBLE);
                        Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);
                        mainServiceGetListResponseCall(selectedVehicleId);
                    } else{
                        vehicletypename = "Two Wheeler";
                        selectedVehicleType = vehicletypename;
                        cv_vehicleimage.setVisibility(View.VISIBLE);
                        txt_vehiclename.setVisibility(View.VISIBLE);
                        btn_vehiclefueltype.setVisibility(View.VISIBLE);
                        Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);
                        mainServiceGetListResponseCall(selectedVehicleId);

                      //  cv_vehicleimage.setVisibility(View.GONE);
                        //txt_vehiclename.setVisibility(View.GONE);
                       // btn_vehiclefueltype.setVisibility(View.GONE);
                      //  showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));

                    }

                    /*if(selectedVehicleId != null) {
                        getVehicleData(selectedVehicleId);
                    }*/

                }




            }

            Log.w(TAG,"vehicleImage : "+vehicleImage+" "+"vehicleName : "+vehicleName+" "+"vehicleModelName : "+vehicleModelName+" "+"fuelType : "+fuelType);

            setHeaderData();
            ll_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PopularServiceActivity.this,LocationListActivity.class));
                }
            });



        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.w(TAG,"setOnCheckedChangeListener : "+isChecked);
            }
        });

        btnnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularServiceActivity.this, NotificationActivity.class);
                intent.putExtra("fromactivity","HomeFragment");
                startActivity(intent);
            }
        });


        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cart > 0){
                    showVehicleStatusChangeAlert(toggleButton,alertmessage);
                }else{
                    Log.w(TAG,"toggleButton setOnClickListener status"+" "+toggleButton.isChecked());
                    Log.w(TAG,"toggleButton setOnClickListener"+" fourwheelerstatus : "+fourwheelerstatus+" twowheelerstatus : "+twowheelerstatus);

                    /*if(!fourwheelerstatus.isEmpty()){
                        Log.w(TAG,"fourwheelerstatus togglebutton---->"+fourwheelerstatus);

                        if(fourwheelerstatus.equalsIgnoreCase("Default")) {
                            if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                                cv_vehicleimage.setVisibility(View.VISIBLE);
                                txt_vehiclename.setVisibility(View.VISIBLE);
                                btn_vehiclefueltype.setVisibility(View.VISIBLE);
                                rlviewpager.setVisibility(View.VISIBLE);
                                vehicletypename = "Four Wheeler";
                                mainServiceGetListResponseCall(fourwheelervehicleid);
                                selectedVehicleType = vehicletypename;
                                selectedVehicleId = fourwheelervehicleid;
                                Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                            }
                        }
                    }
                    else{
                        cv_vehicleimage.setVisibility(View.GONE);
                        txt_vehiclename.setVisibility(View.GONE);
                        btn_vehiclefueltype.setVisibility(View.GONE);
                        rlviewpager.setVisibility(View.INVISIBLE);
                        rv_services.setVisibility(View.GONE);
                        tv_norecords.setVisibility(View.VISIBLE);
                        bottom_popularservice_view.setVisibility(View.GONE);
                        ll_popularservice.setVisibility(View.GONE);
                        showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));

                        if(!twowheelerstatus.isEmpty()){
                            Log.w(TAG,"twowheelerstatus togglebutton---->"+twowheelerstatus);

                            if(twowheelerstatus.equalsIgnoreCase("Default")) {
                                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                                    cv_vehicleimage.setVisibility(View.VISIBLE);
                                    txt_vehiclename.setVisibility(View.VISIBLE);
                                    btn_vehiclefueltype.setVisibility(View.VISIBLE);
                                    rlviewpager.setVisibility(View.VISIBLE);
                                    vehicletypename = "Two Wheeler";
                                    mainServiceGetListResponseCall(twowheelervehicleid);
                                    selectedVehicleType = vehicletypename;
                                    selectedVehicleId = twowheelervehicleid;
                                    Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                                }
                            }
                        }
                        else{
                            cv_vehicleimage.setVisibility(View.GONE);
                            txt_vehiclename.setVisibility(View.GONE);
                            btn_vehiclefueltype.setVisibility(View.GONE);
                            rlviewpager.setVisibility(View.INVISIBLE);
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            bottom_popularservice_view.setVisibility(View.GONE);
                            ll_popularservice.setVisibility(View.GONE);
                            showErrorLoading( getResources().getString(R.string.vehicletypeerrormsg));

                        }
                    }*/


                   if(toggleButton.isChecked()){
                        //Button is ON

                        Log.w(TAG,"Car");
                        Log.w(TAG,"four wheeler clicked :"+fourwheelervehicleid);
                        if(!fourwheelerstatus.isEmpty()){
                            Log.w(TAG,"fourwheelerstatus togglebutton---->"+fourwheelerstatus);

                            if(fourwheelerstatus.equalsIgnoreCase("Default")) {
                                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                                    cv_vehicleimage.setVisibility(View.VISIBLE);
                                    txt_vehiclename.setVisibility(View.VISIBLE);
                                    btn_vehiclefueltype.setVisibility(View.VISIBLE);
                                    rlviewpager.setVisibility(View.VISIBLE);
                                    mainServiceGetListResponseCall(fourwheelervehicleid);
                                    vehicletypename = "Four Wheeler";
                                    selectedVehicleType = vehicletypename;
                                    selectedVehicleId = fourwheelervehicleid;
                                    Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                                }
                            }
                        }
                        else{
                            cv_vehicleimage.setVisibility(View.GONE);
                            txt_vehiclename.setVisibility(View.GONE);
                            btn_vehiclefueltype.setVisibility(View.GONE);
                            rlviewpager.setVisibility(View.INVISIBLE);
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            bottom_popularservice_view.setVisibility(View.GONE);
                            ll_popularservice.setVisibility(View.GONE);
                            vehicletypename = "Four Wheeler";
                            selectedVehicleType = vehicletypename;
                            selectedVehicleId = fourwheelervehicleid;
                            showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));
                            // Toasty.warning(getApplicationContext(), getResources().getString(R.string.vehicletypeerrormsg), Toast.LENGTH_SHORT, true).show();

                        }

                        toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

                    }
                    else{
                        //Button is OFF

                        Log.w(TAG,"Bikes");
                        Log.w(TAG,"Two wheeler clicked :"+twowheelervehicleid+"twowheelerstatus:"+twowheelerstatus);
                        if(!twowheelerstatus.isEmpty()){
                            Log.w(TAG,"twowheelerstatus togglebutton---->"+twowheelerstatus);

                            if(twowheelerstatus.equalsIgnoreCase("Default")) {
                                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                                    cv_vehicleimage.setVisibility(View.VISIBLE);
                                    txt_vehiclename.setVisibility(View.VISIBLE);
                                    btn_vehiclefueltype.setVisibility(View.VISIBLE);
                                    rlviewpager.setVisibility(View.VISIBLE);

                                    mainServiceGetListResponseCall(twowheelervehicleid);
                                    vehicletypename = "Two Wheeler";
                                    selectedVehicleType = vehicletypename;
                                    selectedVehicleId = twowheelervehicleid;
                                    Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                                }
                            }
                        }
                        else{
                            cv_vehicleimage.setVisibility(View.GONE);
                            txt_vehiclename.setVisibility(View.GONE);
                            btn_vehiclefueltype.setVisibility(View.GONE);
                            rlviewpager.setVisibility(View.INVISIBLE);
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            bottom_popularservice_view.setVisibility(View.GONE);
                            ll_popularservice.setVisibility(View.GONE);
                            vehicletypename = "Two Wheeler";
                            selectedVehicleType = vehicletypename;
                            selectedVehicleId = twowheelervehicleid;
                            showErrorLoading( getResources().getString(R.string.vehicletypeerrormsg));

                        }


                        toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);

                    }
                }



            }
        });
        bottom_navigation_view.setSelectedItemId(R.id.home);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        txt_lookmore.setOnClickListener(this);
        cv_vehicleimage.setOnClickListener(this);

    }

    private void getVehicleData1(String bundletwowheelervehicleid, String bundlefourwheelervehicleid) {
        Log.w(TAG,"getVehicleData1 twowheelervehicleid : "+twowheelervehicleid+" fourwheelervehicleid : "+fourwheelervehicleid);
        if(customerVehicleDataBeanList1 != null && customerVehicleDataBeanList1.size()>0){
            for(int i =0; i<customerVehicleDataBeanList1.size();i++) {
                String vehicletype = customerVehicleDataBeanList1.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                if (twowheelervehicleid.equalsIgnoreCase(id)) {
                    headerVehicleimg = customerVehicleDataBeanList1.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList1.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList1.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList1.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList1.get(i).getFuel_Type_Background_Color();
                    twowheelerstatus = customerVehicleDataBeanList1.get(i).getStatus();
                    selectedVehicleId = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                    Log.w(TAG,"getVehicleData1IF headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName+"headerVehicleFuelTypeBackgroundcolor :"+headerVehicleFuelTypeBackgroundcolor+" twowheelerstatus : "+twowheelerstatus);
                }else if(fourwheelervehicleid.equalsIgnoreCase(id)){
                    selectedVehicleId = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                    headerVehicleimg = customerVehicleDataBeanList1.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList1.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList1.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList1.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList1.get(i).getFuel_Type_Background_Color();
                    fourwheelerstatus = customerVehicleDataBeanList1.get(i).getStatus();
                    Log.w(TAG,"getVehicleData1ELSE headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName+"headerVehicleFuelTypeBackgroundcolor :"+headerVehicleFuelTypeBackgroundcolor+" fourwheelerstatus : "+fourwheelerstatus);
                }

            }
            if(!fourwheelerstatus.isEmpty()){
                selectedVehicleId = fourwheelervehicleid;
            }else{
                selectedVehicleId = twowheelervehicleid;

            }
            setHeaderVehicleData();
        }
      /*  if(customerVehicleDataBeanList1 != null && customerVehicleDataBeanList1.size()>0) {
            Log.w(TAG, "customerVehicleDataBeanList--->" + customerVehicleDataBeanList1.size());
            for (int i = 0; i < customerVehicleDataBeanList1.size(); i++) {
                String vehicletype = customerVehicleDataBeanList1.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                 if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                     Log.w(TAG,"IF--->"+vehicletype.equalsIgnoreCase("Two Wheeler"));
                    selectedVehicleId = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                    this.twowheelervehicleid = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                    twowheelerstatus = customerVehicleDataBeanList1.get(i).getStatus();
                    headerVehicleimg = customerVehicleDataBeanList1.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList1.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList1.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList1.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList1.get(i).getFuel_Type_Background_Color();

                    Log.w(TAG, "getVehicleData1 if headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName);
                    Log.w(TAG, "getVehicleData1 if twowheelervehicleid" + this.twowheelervehicleid);
                }
                 else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                     Log.w(TAG,"ELSE-->"+vehicletype.equalsIgnoreCase("Four Wheeler"));
                     selectedVehicleId = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                     fourwheelervehicleid = customerVehicleDataBeanList1.get(i).getVehicletype_id();
                     fourwheelerstatus = customerVehicleDataBeanList1.get(i).getStatus();
                     headerVehicleimg = customerVehicleDataBeanList1.get(i).getVehicle_Image();
                     headerVehicleName = customerVehicleDataBeanList1.get(i).getVehicle_Name();
                     headerVehicleBrandName = customerVehicleDataBeanList1.get(i).getVehicle_Brand_Name();
                     headerVehicleFuelTypeName = customerVehicleDataBeanList1.get(i).getFuel_Type_Name();
                     headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList1.get(i).getFuel_Type_Background_Color();

                     Log.w(TAG, "getVehicleData1 elseif fourwheelervehicleid" + fourwheelervehicleid);
                 }

                Log.w(TAG, "getVehicleData1 twowheelerstatus : " + twowheelerstatus + " " + " fourwheelerstatus : " + fourwheelerstatus+ " selectedVehicleId : "+selectedVehicleId);


            }
        }*/


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
                    Log.w(TAG,"getVehicleData headerVehicleimg :"+headerVehicleimg+" "+"headerVehicleName : "+headerVehicleName+" "+"headerVehicleFuelTypeName : "+headerVehicleFuelTypeName+"headerVehicleFuelTypeBackgroundcolor :"+headerVehicleFuelTypeBackgroundcolor);
                }

            }
            setHeaderVehicleData();
        }
        if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0) {
            Log.w(TAG, "customerVehicleDataBeanList--->" + customerVehicleDataBeanList.size());
            for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                String vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                    twowheelervehicleid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    twowheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();

                    Log.w(TAG, "headerVehicleimg :" + headerVehicleimg + " " + "headerVehicleName : " + headerVehicleName + " " + "headerVehicleFuelTypeName : " + headerVehicleFuelTypeName);
                    Log.w(TAG, "twowheelervehicleid" + twowheelervehicleid);
                } else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                    fourwheelervehicleid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                    fourwheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                    headerVehicleimg = customerVehicleDataBeanList.get(i).getVehicle_Image();
                    headerVehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                    headerVehicleBrandName = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                    headerVehicleFuelTypeName = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                    headerVehicleFuelTypeBackgroundcolor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();

                    Log.w(TAG, "fourwheelervehicleid" + fourwheelervehicleid);
                }
                Log.w(TAG, "getVehicleData twowheelerstatus : " + twowheelerstatus + " " + "fourwheelerstatus : " + fourwheelerstatus);


            }
        }


    }
    private void setHeaderVehicleData() {



        if (headerVehicleimg != null && !headerVehicleimg.isEmpty()) {
            Glide.with(PopularServiceActivity.this)
                    .load(headerVehicleimg)
                    .into(cv_vehicleimage);

        }
        else{
            Glide.with(PopularServiceActivity.this)
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
            btn_vehiclefueltype.setVisibility(View.GONE);
        }

        if(headerVehicleFuelTypeBackgroundcolor != null){
            btn_vehiclefueltype.setBackgroundColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            btn_vehiclefueltype.setBackgroundResource(R.drawable.tags_rounded_corners);
            GradientDrawable gd = (GradientDrawable) btn_vehiclefueltype.getBackground();
            gd.setColor(Color.parseColor(headerVehicleFuelTypeBackgroundcolor));
            gd.setCornerRadius(10);
            gd.setStroke(1, Color.WHITE);

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

        if (vehicleImage != null && !vehicleImage.isEmpty()) {

            Glide.with(PopularServiceActivity.this)
                    .load(vehicleImage)
                    .into(cv_vehicleimage);

        }
        else{
            Glide.with(PopularServiceActivity.this)
                    .load(R.drawable.logo)
                    .into(cv_vehicleimage);

        }
        if(vehicleName != null){
            txt_vehiclename.setText(vehicleName);
        }else{
            txt_vehiclename.setText("");
        }
        if(fuelType != null){
            btn_vehiclefueltype.setText(fuelType);
        }else{
            btn_vehiclefueltype.setVisibility(View.GONE);
        }

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
        Intent intent = new Intent(PopularServiceActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        intent.putExtra("selectedVehicleId",selectedVehicleId);
        intent.putExtra("selectedVehicleType",selectedVehicleType);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("fromactivity", "PopularServiceActivity");
        startActivity(intent);
        Log.w(TAG,"callDirections selectedVehicleId : "+selectedVehicleId+" "+"selectedVehicleType : "+selectedVehicleType);
        finish();

    }


    public void mainServiceGetListResponseCall(String vehicletypeid){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<MainServiceGetListResponse> call = apiInterface.mainServiceGetListResponseCall(RestUtils.getContentType(),mainServiceGetListRequest(vehicletypeid));
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<MainServiceGetListResponse>() {
            @Override
            public void onResponse(@NotNull Call<MainServiceGetListResponse> call, @NotNull Response<MainServiceGetListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"MainServiceGetListResponse" + new Gson().toJson(response.body()));

                listHomeBannerResponse.clear();
                mainserviceslistBeanList.clear();
                popularserviceBeanList.clear();
                assert response.body() != null;
                if(200 == response.body().getCode()){
                    cart = response.body().getCart();
                    alertmessage = response.body().getAlert_msg();
                    customerVehicleDataBeanList = response.body().getData().getCustomerVehicleData();
                    if(vehicletypeid != null) {
                        getVehicleData(vehicletypeid);
                    }
                    if(response.body().getData().getLocationData() != null){
                        session.createLocationDetails(
                                response.body().getData().getLocationData().getCity(),
                                response.body().getData().getLocationData().getState(),
                                response.body().getData().getLocationData().getCountry(),
                                response.body().getData().getLocationData().getPincode(),
                                response.body().getData().getLocationData().getStreet(),
                                response.body().getData().getLocationData().getLocation_NickName(),
                                response.body().getData().getLocationData().getFlat_No() );
                        city = response.body().getData().getLocationData().getCity();
                        street = response.body().getData().getLocationData().getLocation_NickName()+" "+ response.body().getData().getLocationData().getStreet();
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


                    if (response.body().getData().getBannerlist() != null ) {
                        homeBannerResponse = response.body();
                        listHomeBannerResponse = response.body().getData().getBannerlist();

                        for(int i=0; i<listHomeBannerResponse.size(); i++){
                            listHomeBannerResponse.get(i).getServiceBanner_Image();
                        }

                        viewpageData(listHomeBannerResponse);
                    }
                    if (response.body().getData().getMainserviceslist() != null ) {
                        servicesBean = response.body();
                        customerVehicleDataBeanList = response.body().getData().getCustomerVehicleData();

                        mainserviceslistBeanList = response.body().getData().getMainserviceslist();
                        if(response.body().getData().getMainserviceslist().size()>0){
                            rv_services.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            setViewServices();
                        }else{
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                        }
                    }
                    if (response.body().getData().getMainserviceslist() != null ) {
                        servicesBean = response.body();
                        popularserviceBeanList = response.body().getData().getPopularservice();
                        if(response.body().getData().getMainserviceslist().size()>0){
                            bottom_popularservice_view.setVisibility(View.VISIBLE);
                            ll_popularservice.setVisibility(View.VISIBLE);
                            rv_popularservices.setVisibility(View.VISIBLE);
                            tv_nopopularservice.setVisibility(View.GONE);
                            setViewPopularServices();
                        }else{
                            ll_popularservice.setVisibility(View.VISIBLE);
                            rv_popularservices.setVisibility(View.GONE);
                            tv_nopopularservice.setVisibility(View.VISIBLE);
                            bottom_popularservice_view.setVisibility(View.GONE);

                        }
                    }
                }
                else{
                    rv_services.setVisibility(View.GONE);
                    rv_popularservices.setVisibility(View.GONE);
                    bottom_popularservice_view.setVisibility(View.GONE);
                    tv_norecords.setVisibility(View.VISIBLE);
                    customerVehicleDataBeanList = response.body().getData().getCustomerVehicleData();
                    if(vehicletypeid != null){
                        getVehicleData(vehicletypeid);
                    }

                    if(response.body().getData().getLocationData() != null){
                        session.createLocationDetails(
                                response.body().getData().getLocationData().getCity(),
                                response.body().getData().getLocationData().getState(),
                                response.body().getData().getLocationData().getCountry(),
                                response.body().getData().getLocationData().getPincode(),
                                response.body().getData().getLocationData().getStreet(),
                                response.body().getData().getLocationData().getLocation_NickName(),
                                response.body().getData().getLocationData().getFlat_No() );
                        city = response.body().getData().getLocationData().getCity();
                        street = response.body().getData().getLocationData().getLocation_NickName()+" "+ response.body().getData().getLocationData().getStreet();
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


            }


            @Override
            public void onFailure(@NotNull Call<MainServiceGetListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"MainServiceGetListResponseflr"+t.getMessage());
            }
        });

    }
    private MainServiceGetListRequest mainServiceGetListRequest(String vehicletypeid) {
        /*
         * Vehicle_Type_id : 5f0c0cfc2f857d66950cf25f
         * Master_Service_id : 5f1ac41b55abee4870516567
         * Customer_id:""
         */


        MainServiceGetListRequest mainServiceGetListRequest = new MainServiceGetListRequest();
        mainServiceGetListRequest.setVehicle_Type_id(vehicletypeid);
        mainServiceGetListRequest.setMaster_Service_id(masterserviceid);
        mainServiceGetListRequest.setCustomer_id(customerid);
        Log.w(TAG,"MainServiceGetListRequest"+ new Gson().toJson(mainServiceGetListRequest));
        return mainServiceGetListRequest;
    }

    private void viewpageData(final List<MainServiceGetListResponse.DataBean.BannerlistBean> homeBannerResponseArrayList) {
        tabLayout.setupWithViewPager(viewPager, true);

        viewPagerMainServiceAdapter = new ViewPagerMainServiceAdapter(mContext, homeBannerResponseArrayList);
        viewPager.setAdapter(viewPagerMainServiceAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == homeBannerResponseArrayList.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, false);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }


    private void setViewServices() {
        Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

        rv_services.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_services.setItemAnimator(new DefaultItemAnimator());
        mainServicesHomeAdapter = new MainServicesHomeAdapter(mContext, mainserviceslistBeanList, rv_services,city,street,vehicleImage,vehicleName,vehicleModelName,fuelType,masterservicename,vehicletypename,customerVehicleDataBeanList,masterserviceid,twowheelervehicleid,fourwheelervehicleid,selectedVehicleType,selectedVehicleId);
        rv_services.setAdapter(mainServicesHomeAdapter);

        mainServicesHomeAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > mainserviceslistBeanList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }
    private void setViewPopularServices() {
        rv_popularservices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_popularservices.setMotionEventSplittingEnabled(false);
        int size =3;
        // rv_popularservices.setLayoutManager(new LinearLayoutManager(this));
        rv_popularservices.setItemAnimator(new DefaultItemAnimator());
        popularServicesAdapter = new PopularServicesAdapter(mContext, popularserviceBeanList, rv_popularservices,city,street,vehicleImage,vehicleName,vehicleModelName,fuelType,masterservicename,vehicletypename,customerVehicleDataBeanList,masterserviceid,twowheelervehicleid,fourwheelervehicleid, size,selectedVehicleId,selectedVehicleType);
        rv_popularservices.setAdapter(popularServicesAdapter);

        mainServicesHomeAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > popularserviceBeanList.size()) {
                Log.e("haint", "Load More");
            }




        });







    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_lookmore:
                if(popularserviceBeanList.size()>0){
                    ll_popularservice.setVisibility(View.VISIBLE);
                    bottom_popularservice_view.setVisibility(View.VISIBLE);
                    setViewPopularServices1();

                }else{
                    ll_popularservice.setVisibility(View.VISIBLE);
                    rv_services.setVisibility(View.GONE);
                    tv_norecords.setVisibility(View.VISIBLE);
                    bottom_popularservice_view.setVisibility(View.GONE);

                }

                break;

            case R.id.cv_vehicleimage:
                callDirections("3");
                break;
        }
    }

    private void setViewPopularServices1() {
        rv_popularservices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_popularservices.setMotionEventSplittingEnabled(false);

        int size = popularserviceBeanList.size();
        // rv_popularservices.setLayoutManager(new LinearLayoutManager(this));
        rv_popularservices.setItemAnimator(new DefaultItemAnimator());
        popularServicesAdapter = new PopularServicesAdapter(mContext, popularserviceBeanList, rv_popularservices,city,street,vehicleImage,vehicleName,vehicleModelName,fuelType,masterservicename,vehicletypename,customerVehicleDataBeanList,masterserviceid,twowheelervehicleid,fourwheelervehicleid,size, selectedVehicleId, selectedVehicleType);
        rv_popularservices.setAdapter(popularServicesAdapter);
        popularServicesAdapter.notifyDataSetChanged();

        mainServicesHomeAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > popularserviceBeanList.size()) {
                Log.e("haint", "Load More");
            }



        });







    }
    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(PopularServiceActivity.this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {
                    hideLoading();
                });


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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PopularServiceActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void showVehicleStatusChangeAlert(ToggleButton toggleButton, String message) {

        try {

            dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(message);
            Button dialogButtonApprove = dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if(toggleButton.isChecked()){
                        //Button is ON

                        Log.w(TAG,"Car");
                        Log.w(TAG,"four wheeler clicked :"+fourwheelervehicleid);
                        Log.w(TAG,"showVehicleStatusChangeAlert-->"+"fourwheelerstatus: "+fourwheelerstatus+"twowheelerstatus:"+twowheelerstatus);
                        if(!fourwheelerstatus.isEmpty()){
                            cv_vehicleimage.setVisibility(View.VISIBLE);
                            txt_vehiclename.setVisibility(View.VISIBLE);
                            btn_vehiclefueltype.setVisibility(View.VISIBLE);
                            if(fourwheelerstatus.equalsIgnoreCase("Default")) {
                                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                                    vehicletypename = "Four Wheeler";
                                    if(fourwheelervehicleid!= null){
                                        mainServiceGetListResponseCall(fourwheelervehicleid);
                                        selectedVehicleType = vehicletypename;
                                        selectedVehicleId = fourwheelervehicleid;
                                        Log.w(TAG,"selectedVehicleId--->"+fourwheelervehicleid);
                                    }


                                }
                            }
                        }else{
                            cv_vehicleimage.setVisibility(View.GONE);
                            txt_vehiclename.setVisibility(View.GONE);
                            btn_vehiclefueltype.setVisibility(View.GONE);
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            bottom_popularservice_view.setVisibility(View.GONE);
                            ll_popularservice.setVisibility(View.GONE);
                            showErrorLoading( getResources().getString(R.string.vehicletypefourwheelererrormsg));
                            // Toasty.warning(getApplicationContext(), getResources().getString(R.string.vehicletypeerrormsg), Toast.LENGTH_SHORT, true).show();

                        }

                        toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

                    }
                    else{
                        //Button is OFF

                        Log.w(TAG,"Bikes");
                        Log.w(TAG,"Two wheeler clicked :"+twowheelervehicleid+"twowheelerstatus:"+twowheelerstatus);
                        if(!twowheelerstatus.isEmpty()){
                            cv_vehicleimage.setVisibility(View.VISIBLE);
                            txt_vehiclename.setVisibility(View.VISIBLE);
                            btn_vehiclefueltype.setVisibility(View.VISIBLE);
                            if(twowheelerstatus.equalsIgnoreCase("Default")) {
                                if (new ConnectionDetector(PopularServiceActivity.this).isNetworkAvailable(PopularServiceActivity.this)) {
                                    vehicletypename = "Two Wheeler";
                                    mainServiceGetListResponseCall(twowheelervehicleid);
                                    selectedVehicleType = vehicletypename;
                                    selectedVehicleId = twowheelervehicleid;
                                    Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                                }
                            }
                        }else{
                            cv_vehicleimage.setVisibility(View.GONE);
                            txt_vehiclename.setVisibility(View.GONE);
                            btn_vehiclefueltype.setVisibility(View.GONE);
                            rv_services.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            bottom_popularservice_view.setVisibility(View.GONE);
                            ll_popularservice.setVisibility(View.GONE);
                            //  Toasty.warning(getApplicationContext(), getResources().getString(R.string.vehicletypeerrormsg), Toast.LENGTH_SHORT, true).show();
                            showErrorLoading( getResources().getString(R.string.vehicletypeerrormsg));

                        }


                        toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);

                    }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!this.isDestroyed()) {
            Glide.with(PopularServiceActivity.this).pauseRequests();
        }
    }
}