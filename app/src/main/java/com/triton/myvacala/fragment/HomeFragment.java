package com.triton.myvacala.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.LocationListActivity;
import com.triton.myvacala.activities.NotificationActivity;
import com.triton.myvacala.adapter.MasterServicesHomeAdapter;
import com.triton.myvacala.adapter.ViewPagerDashboardAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.requestpojo.MasterServiceGetlistRequest;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements Serializable {

    private Context mContext;
    private SharedPreferences preferences;


    @BindView(R.id.txt_name)
    TextView txt_name;

    @BindView(R.id.txt_location)
    TextView txt_location;

    @BindView(R.id.txt_address)
    TextView txt_address;

    @BindView(R.id.rv_services)
    RecyclerView rv_services;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.ll_city)
    LinearLayout ll_city;

    @BindView(R.id.btnnotifications)
    Button btnnotifications;



    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;


    ViewPagerDashboardAdapter viewPagerDashboardAdapter;

    MasterServiceGetlistResponse homeBannerResponse;

    private List<MasterServiceGetlistResponse.HomeBannerListBean> listHomeBannerResponse = null;


    String city = "",street = "";
    String vehicleImage,vehicleName, vehicleModelName,fuelType;




    private String TAG = "HomeFragment";

    MasterServiceGetlistResponse servicesBean;
    private List<MasterServiceGetlistResponse.MasterserviceListBean> masterServiceGetlistResponseList = null;
    private List<MasterServiceGetlistResponse.VehicletypeDetailsBean> vehicletypeDetailsBeanList = null;
    MasterServicesHomeAdapter masterServicesHomeAdapter;


    private ArrayList<MasterServiceGetlistResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;




    SessionManager session;
    String name,customerid;

    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView-->");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);


        session = new SessionManager(mContext);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid =  user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+customerid+"name : "+name);

        txt_name.setText("Hi,"+" "+name);

        btnnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });




        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
            masterServiceGetlistResponseCall();

        }

        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LocationListActivity.class);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }







    public void masterServiceGetlistResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<MasterServiceGetlistResponse> call = apiInterface.masterServiceGetlistResponseCall(RestUtils.getContentType(),masterServiceGetlistRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<MasterServiceGetlistResponse>() {
            @Override
            public void onResponse(@NotNull Call<MasterServiceGetlistResponse> call, @NotNull Response<MasterServiceGetlistResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"MasterServiceGetlistResponse" + new Gson().toJson(response.body()));


                if(response.body() != null) {
                    session.createPayuDetails(
                            response.body().getSalt_key(),
                            response.body().getMerchant_key(),
                            String.valueOf(response.body().isIsproduction()));

                    if (200 == response.body().getCode()) {
                        customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                        if (response.body().getMobile_details() != null) {
                            for (int i = 0; i < response.body().getMobile_details().size(); i++) {
                                session.createMobileappdetails(
                                        response.body().getMobile_details().get(i).getMobileappdetails().get(i).getEmail(),
                                        response.body().getMobile_details().get(i).getMobileappdetails().get(i).getPhone_number(),
                                        response.body().getMobile_details().get(i).getMobileappdetails().get(i).getAndroid_share_link());
                            }
                        }
                        if (response.body().getHomeBannerList() != null) {
                            homeBannerResponse = response.body();
                            listHomeBannerResponse = response.body().getHomeBannerList();

                            for (int i = 0; i < listHomeBannerResponse.size(); i++) {
                                listHomeBannerResponse.get(i).getHomebanner_Image();
                                Log.w(TAG, "RES" + " " + listHomeBannerResponse.get(i).getHomebanner_Image());
                            }

                            viewpageData(listHomeBannerResponse);
                        }
                        if (response.body().getLocationDetails() != null) {
                            session.createLocationDetails(
                                    response.body().getLocationDetails().getCity(),
                                    response.body().getLocationDetails().getState(),
                                    response.body().getLocationDetails().getCountry(),
                                    response.body().getLocationDetails().getPincode(),
                                    response.body().getLocationDetails().getStreet(),
                                    response.body().getLocationDetails().getLocation_NickName(),
                                    response.body().getLocationDetails().getFlat_No());
                            city = response.body().getLocationDetails().getCity();
                            street = response.body().getLocationDetails().getLocation_NickName() + " " + response.body().getLocationDetails().getStreet();
                            Log.w(TAG, "createLocationDetails--->" + "city:" + city + "street :" + street);
                            if (city != null) {
                                txt_location.setText(city);
                            } else {
                                txt_location.setText("");
                            }
                            if (street != null) {
                                txt_address.setText(street);
                            } else {
                                txt_address.setText("");
                            }
                        }
                        if (!customerVehicleDataBeanList.isEmpty()) {
                            for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                                vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                                vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                                vehicleModelName = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                                fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                            }
                            Log.w(TAG, "vehicleImage : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + vehicleModelName + " " + "fuelType : " + fuelType);

                        }
                        if (response.body().getMasterserviceList() != null) {
                            servicesBean = response.body();
                            vehicletypeDetailsBeanList = response.body().getVehicletypeDetails();
                            masterServiceGetlistResponseList = response.body().getMasterserviceList();
                            customerVehicleDataBeanList = response.body().getCustomerVehicleData();

                            if (response.body().getMasterserviceList().isEmpty()) {
                                rv_services.setVisibility(View.GONE);
                                tv_norecords.setVisibility(View.VISIBLE);
                            } else {
                                rv_services.setVisibility(View.VISIBLE);
                                tv_norecords.setVisibility(View.GONE);
                                setViewServices();
                            }
                        }


                    } else {
                        customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                        if (response.body().getLocationDetails() != null) {
                            session.createLocationDetails(
                                    response.body().getLocationDetails().getCity(),
                                    response.body().getLocationDetails().getState(),
                                    response.body().getLocationDetails().getCountry(),
                                    response.body().getLocationDetails().getPincode(),
                                    response.body().getLocationDetails().getStreet(),
                                    response.body().getLocationDetails().getLocation_NickName(),
                                    response.body().getLocationDetails().getFlat_No());
                            city = response.body().getLocationDetails().getCity();
                            street = response.body().getLocationDetails().getLocation_NickName() + " " + response.body().getLocationDetails().getStreet();
                            Log.w(TAG, "createLocationDetails--->" + "city:" + city + "street :" + street);
                            if (city != null) {
                                txt_location.setText(city);
                            } else {
                                txt_location.setText("");
                            }
                            if (street != null) {
                                txt_address.setText(street);
                            } else {
                                txt_address.setText("");
                            }
                        }
                        if (customerVehicleDataBeanList != null) {
                            if (customerVehicleDataBeanList.size() > 0) {
                                for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                                    vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                                    vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                                    vehicleModelName = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                                    fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                                }
                                Log.w(TAG, "vehicleImage : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + vehicleModelName + " " + "fuelType : " + fuelType);

                            }
                        }

                        showErrorLoading(response.body().getMessage());
                    }
                }





            }


            @Override
            public void onFailure(@NotNull Call<MasterServiceGetlistResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"MasterServiceGetlistResponseflr "+t.getMessage());
                    if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
                        masterServiceGetlistResponseCall();

                    }

            }
        });

    }
    private MasterServiceGetlistRequest masterServiceGetlistRequest() {
        /*
         * Customer_id : 5f0814a3abce73672359e092
        */

        MasterServiceGetlistRequest masterServiceGetlistRequest = new MasterServiceGetlistRequest();
        masterServiceGetlistRequest.setCustomer_id(customerid);
        Log.w(TAG,"MasterServiceGetlistRequest"+ new Gson().toJson(masterServiceGetlistRequest));
        return masterServiceGetlistRequest;
    }



    private void viewpageData(final List<MasterServiceGetlistResponse.HomeBannerListBean> homeBannerResponseArrayList) {
        tabLayout.setupWithViewPager(viewPager, true);

        viewPagerDashboardAdapter = new ViewPagerDashboardAdapter(mContext, homeBannerResponseArrayList);
        viewPager.setAdapter(viewPagerDashboardAdapter);
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
        rv_services.setLayoutManager(new GridLayoutManager(mContext, 2));

        // rvSymptomsList.setLayoutManager(new LinearLayoutManager(this));
        rv_services.setItemAnimator(new DefaultItemAnimator());
        masterServicesHomeAdapter = new MasterServicesHomeAdapter(mContext, masterServiceGetlistResponseList, rv_services,vehicletypeDetailsBeanList,
                city,street,vehicleImage,vehicleName,vehicleModelName,fuelType,customerVehicleDataBeanList);
        rv_services.setAdapter(masterServicesHomeAdapter);
        masterServicesHomeAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > masterServiceGetlistResponseList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }

    public void showErrorLoading(String errormesage) {
        alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
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






}
