package com.triton.myvacala.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.DashboardActivity;
import com.triton.myvacala.activities.PopularServiceActivity;
import com.triton.myvacala.activities.PopularVehicleListOldUserActivity;
import com.triton.myvacala.activities.SubServicesActivity;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.adapter.VehicleListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.VehicleStatusChangeListener;
import com.triton.myvacala.requestpojo.VehicleListsRequest;
import com.triton.myvacala.requestpojo.VehicleStatusChangeRequest;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.triton.myvacala.responsepojo.VehicleListsResponse;
import com.triton.myvacala.responsepojo.VehicleStatusChangeResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyVehicleFragment extends Fragment implements View.OnClickListener, VehicleStatusChangeListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.togglebutton)
    ToggleButton toggleButton;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.rv_vehiclelists)
    RecyclerView rv_vehiclelists;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.txt_popularvehicletype)
    TextView txt_popularvehicletype;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.iv_addvehicle)
    ImageView iv_addvehicle;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout ;



    private String TAG ="MyVehicleFragment";

    private List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList = null;


    private String twowheelervehicleid = "",fourwheelervehicleid ="";
    private String vehicletype_id = "";

    private List<VehicleListsResponse.DataBean> vehicleListsResponseList = new ArrayList<>();
    VehicleListAdapter vehicleListAdapter;
    private SharedPreferences preferences;

    SessionManager session;
    String name,customerid;

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    private String selectedVehicleId,selectedVehicleType,masterserviceid,fromactivity;

    private Context mContext;
    private Dialog dialog;

    private int cart;
    private String alertmessage;


    public MyVehicleFragment() {
        // Required empty public constructor
    }
    String serviceid,servicename,masterservicename;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG,"onCreate--->");

        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);



    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView--->");
        View view = inflater.inflate(R.layout.fragment_myvehicle, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);

        mContext = getActivity();

        if(getArguments() != null) {
            selectedVehicleId = getArguments().getString("selectedVehicleId");
            masterserviceid = getArguments().getString("masterserviceid");
            selectedVehicleType = getArguments().getString("selectedVehicleType");
            fromactivity = getArguments().getString("fromactivity");
            /*PopularServiceActivity*/
            Log.w(TAG,"fromactivity--->"+fromactivity);
            Log.w(TAG,"selectedVehicleId : "+selectedVehicleId+" "+"selectedVehicleType : "+selectedVehicleType +" "+"masterserviceid : "+masterserviceid);
            serviceid =  getArguments().getString("serviceid");
            servicename =  getArguments().getString("servicename");
            masterservicename =  getArguments().getString("masterservicename");

        }


        toolbar_title.setText(getResources().getString(R.string.yourcars));

        avi_indicator.setVisibility(View.GONE);


        if(fromactivity != null){
            if(fromactivity.equalsIgnoreCase("PopularServiceActivity")){
             if(selectedVehicleType.equalsIgnoreCase("Four Wheeler")){
                   toggleButton.setChecked(true);
                     //Button is ON
                     Log.w(TAG,"Car");
                     txt_popularvehicletype.setText("Popular Cars");
                     vehicletype_id = selectedVehicleId;
                     Log.w(TAG,"four wheeler clicked :"+selectedVehicleId);
                     toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);
                     if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                         vehicleTypeGetListResponseCallHeader();
                         vehicleListsResponseCall(selectedVehicleId);

                     }


             }
             else{
                 toggleButton.setChecked(false);
                     Log.w(TAG,"Bikes");
                     vehicletype_id = selectedVehicleId;
                     txt_popularvehicletype.setText("Popular Bikes");
                     Log.w(TAG,"Two wheeler clicked :"+selectedVehicleId);
                     toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
                     if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                         vehicleTypeGetListResponseCallHeader();

                         vehicleListsResponseCall(selectedVehicleId);

                     }



             }


            }
            else if(fromactivity.equalsIgnoreCase("SubServicesActivity")){
             if(selectedVehicleType.equalsIgnoreCase("Four Wheeler")){
                   toggleButton.setChecked(true);
                     //Button is ON
                     Log.w(TAG,"Car");
                     txt_popularvehicletype.setText("Popular Cars");
                     vehicletype_id = selectedVehicleId;
                     Log.w(TAG,"four wheeler clicked :"+selectedVehicleId);
                     toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);
                     if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                         vehicleTypeGetListResponseCallHeader();

                         vehicleListsResponseCall(selectedVehicleId);

                     }


             }
             else{
                 toggleButton.setChecked(false);
                     Log.w(TAG,"Bikes");
                     vehicletype_id = selectedVehicleId;
                     txt_popularvehicletype.setText("Popular Bikes");
                     Log.w(TAG,"Two wheeler clicked :"+selectedVehicleId);
                     toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
                     if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                         vehicleTypeGetListResponseCallHeader();

                         vehicleListsResponseCall(selectedVehicleId);

                     }



             }


            }
        }else{
            if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {

                vehicleTypeGetListResponseCall();

            }
        }




        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.w(TAG,"setOnCheckedChangeListener"+buttonView.getText().toString()+""+""+isChecked);


            }
        });

        toggleButton.setOnClickListener(v -> {

            Log.w(TAG," toggleButton.setOnClickListener"+toggleButton.isChecked());

            if(toggleButton.isChecked()){
                //Button is ON
                Log.w(TAG,"Car");
                txt_popularvehicletype.setText("Popular Cars");
                vehicletype_id = fourwheelervehicleid;
                selectedVehicleType = "Four Wheeler";
                selectedVehicleId = vehicletype_id;
                //vehicleBrandListResponseCall(fourwheelervehicleid);
                Log.w(TAG,"four wheeler clicked :"+fourwheelervehicleid);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

            }
            else{
                //Button is OFF

                Log.w(TAG,"Bikes");
                vehicletype_id = twowheelervehicleid;
                txt_popularvehicletype.setText("Popular Bikes");
                selectedVehicleType = "Two Wheeler";
                selectedVehicleId = vehicletype_id;
                Log.w(TAG,"Two wheeler clicked :"+twowheelervehicleid);
                //vehicleBrandListResponseCall(twowheelervehicleid);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);


            }
            vehicleListsResponseCall(vehicletype_id);

        });

        iv_addvehicle.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PopularVehicleListOldUserActivity.class);
            intent.putExtra("tag",3);
            startActivity(intent);
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            // cancel the Visual indication of a refresh
            swipeRefreshLayout.setRefreshing(false);
            Log.w(TAG,"vehicletype_id=====--->"+vehicletype_id);

            if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                vehicleListsResponseCall(vehicletype_id);
            }

        });

        imgBack.setOnClickListener(this);




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    public void vehicleTypeGetListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleTypeGetListResponse> call = apiInterface.vehicleTypeGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VehicleTypeGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VehicleTypeGetListResponse> call, @NonNull Response<VehicleTypeGetListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"VehicleTypeGetListResponse" + new Gson().toJson(response.body()));

                    vehicleTypeGetListResponseList = response.body().getData();
                    if(!vehicleTypeGetListResponseList.isEmpty()){
                        for(int i =0; i<vehicleTypeGetListResponseList.size();i++){
                            String vehicletype = response.body().getData().get(i).getVehicle_Type();
                            String id = response.body().getData().get(i).get_id();
                            Log.w(TAG,"vehicletype :"+vehicletype+" "+"id :"+id);
                            if(vehicletype.equalsIgnoreCase("Two Wheeler")){
                                twowheelervehicleid = response.body().getData().get(i).get_id();
                                vehicletype_id = twowheelervehicleid;

                                Log.w(TAG,"twowheelervehicleid"+twowheelervehicleid);
                            }else if(vehicletype.equalsIgnoreCase("Four Wheeler")){
                                fourwheelervehicleid = response.body().getData().get(i).get_id();
                                vehicletype_id = fourwheelervehicleid;


                                //vehicleBrandListResponseCall(fourwheelervehicleid);

                                Log.w(TAG,"fourwheelervehicleid"+fourwheelervehicleid);
                            }






                        }
                        vehicletype_id = fourwheelervehicleid;
                        Log.w(TAG,"vehicletype_id--->"+vehicletype_id);

                        if(fourwheelervehicleid != null && getActivity() != null){
                            if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                                vehicleListsResponseCall(fourwheelervehicleid);
                            }
                        }



                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<VehicleTypeGetListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"VehicleTypeGetListResponseflr"+t.getMessage());
            }
        });

    }
    public void vehicleTypeGetListResponseCallHeader(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleTypeGetListResponse> call = apiInterface.vehicleTypeGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VehicleTypeGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VehicleTypeGetListResponse> call, @NonNull Response<VehicleTypeGetListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    Log.w(TAG,"VehicleTypeGetListResponse" + new Gson().toJson(response.body()));

                    vehicleTypeGetListResponseList = response.body().getData();
                    if(!vehicleTypeGetListResponseList.isEmpty()){
                        for(int i =0; i<vehicleTypeGetListResponseList.size();i++){
                            String vehicletype = response.body().getData().get(i).getVehicle_Type();
                            String id = response.body().getData().get(i).get_id();
                            Log.w(TAG,"vehicletype :"+vehicletype+" "+"id :"+id);
                            if(vehicletype.equalsIgnoreCase("Two Wheeler")){
                                twowheelervehicleid = response.body().getData().get(i).get_id();
                                vehicletype_id = twowheelervehicleid;

                                Log.w(TAG,"twowheelervehicleid"+twowheelervehicleid);
                            }else if(vehicletype.equalsIgnoreCase("Four Wheeler")){
                                fourwheelervehicleid = response.body().getData().get(i).get_id();
                                vehicletype_id = fourwheelervehicleid;


                                //vehicleBrandListResponseCall(fourwheelervehicleid);

                                Log.w(TAG,"fourwheelervehicleid"+fourwheelervehicleid);
                            }
                            if(selectedVehicleId.equalsIgnoreCase(id)){
                                selectedVehicleType = response.body().getData().get(i).getVehicle_Type();
                                if(selectedVehicleType.equalsIgnoreCase("Two Wheeler")){
                                    toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);
                                    toggleButton.setChecked(false);
                                }else{
                                    toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

                                }
                            }
                            Log.w(TAG,"selectedVehicleType : "+selectedVehicleType);






                        }
                      /*  vehicletype_id = fourwheelervehicleid;
                        Log.w(TAG,"vehicletype_id--->"+vehicletype_id);

                        if(fourwheelervehicleid != null && getActivity() != null){
                            if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                                vehicleListsResponseCall(fourwheelervehicleid);
                            }
                        }*/



                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<VehicleTypeGetListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"VehicleTypeGetListResponseflr"+t.getMessage());
            }
        });

    }





    public void vehicleListsResponseCall(String vehicletype_id){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleListsResponse> call = apiInterface.vehicleListsResponseCall(RestUtils.getContentType(),vehicleListsRequest(vehicletype_id));
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VehicleListsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<VehicleListsResponse> call, @NotNull Response<VehicleListsResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "VehicleListsResponse" + new Gson().toJson(response.body()));
                vehicleListsResponseList.clear();

                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        vehicleListsResponseList = response.body().getData();
                        cart = response.body().getCart();
                        alertmessage = response.body().getAlert_msg();
                        Log.w(TAG, "Size--->" + vehicleListsResponseList.size());

                        if (response.body().getData().isEmpty()) {
                            rv_vehiclelists.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText("No vehicles found");
                        } else {
                            tv_norecords.setVisibility(View.GONE);
                            rv_vehiclelists.setVisibility(View.VISIBLE);
                            setView();
                        }

                    } else {
                        rv_vehiclelists.setVisibility(View.GONE);
                        tv_norecords.setVisibility(View.VISIBLE);
                        tv_norecords.setText("No vehicles found");

                        // assert response.body() != null;
                        //showErrorLoading(response.body().getMessage());
                    }
                }
            }






            @Override
            public void onFailure(@NotNull Call<VehicleListsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"VehicleListsResponseflr"+t.getMessage());
            }
        });

    }
    private VehicleListsRequest vehicleListsRequest(String vehicletype_id) {
        /*
         * Customer_id : 5f0c146edb97a179bb713ed3
         * Vehicletype_id : 5f0c0cfc2f857d66950cf25f
         */

        VehicleListsRequest vehicleListsRequest = new VehicleListsRequest();
        vehicleListsRequest.setCustomer_id(customerid);
        vehicleListsRequest.setVehicletype_id(vehicletype_id);

        Log.w(TAG," VehicleListsRequest"+ new Gson().toJson(vehicleListsRequest));
        return vehicleListsRequest;
    }

    private void setView() {
        //rv_locationlist.setLayoutManager(new GridLayoutManager(this, 3));

        rv_vehiclelists.setLayoutManager(new LinearLayoutManager(mContext));
        rv_vehiclelists.setItemAnimator(new DefaultItemAnimator());
        vehicleListAdapter = new VehicleListAdapter(mContext,vehicleListsResponseList , rv_vehiclelists,this);
        rv_vehiclelists.setAdapter(vehicleListAdapter);

        vehicleListAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > vehicleListsResponseList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }

    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
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
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            if (fromactivity != null) {
                if (fromactivity.equalsIgnoreCase("PopularServiceActivity")) {
                    callDirectionsBackPopular();

                } else if (fromactivity.equalsIgnoreCase("SubServicesActivity")) {
                    callDirectionsBackSubServices();

                }
            } else {
                callDirections("1");
            }
        }
    }

    public void callDirections(String tag){
        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);


    }




    public void callDirectionsBackPopular(){
        Intent intent = new Intent(getActivity(), PopularServiceActivity.class);
        intent.putExtra("selectedVehicleId",selectedVehicleId);
        intent.putExtra("selectedVehicleType",selectedVehicleType);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("fromactivity","PopularServiceActivity");
        startActivity(intent);


    }
    public void callDirectionsBackSubServices(){
        Intent intent = new Intent(getActivity(), SubServicesActivity.class);
        intent.putExtra("selectedVehicleId",selectedVehicleId);
        intent.putExtra("selectedVehicleType",selectedVehicleType);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("fromactivity","SubServicesActivity");
        intent.putExtra("serviceid", serviceid);
        intent.putExtra("servicename", servicename);
        intent.putExtra("masterservicename", masterservicename);
        startActivity(intent);


    }


    @Override
    public void selectedVehicleDetails(String vehicle_id, String Customer_id, String Vehicletype_id) {
        if(cart == 0){
            showVehicleStatusChangeAlert(vehicle_id,Customer_id,Vehicletype_id,getResources().getString(R.string.vehiclestatuschange));
        }else{
            showVehicleStatusChangeAlert(vehicle_id,Customer_id,Vehicletype_id,alertmessage);

        }
    }


    private void showVehicleStatusChangeAlert(String vehicleid, String customerid, String vehicletypeId, String message) {

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
                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    vehicleStatusChangeResponseCall(dialog,vehicleid,customerid,vehicletypeId);


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
    private void vehicleStatusChangeResponseCall(final ProgressDialog dialog, String vehicleid, String customerid, String vehicletypeId) {
        dialog.show();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleStatusChangeResponse> call = apiInterface.vehicleStatusChangeResponseCall(RestUtils.getContentType(),vehicleStatusChangeRequest(vehicleid,customerid,vehicletypeId));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<VehicleStatusChangeResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleStatusChangeResponse> call, @NotNull Response<VehicleStatusChangeResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"VehicleStatusChangeResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(mContext, "Default Location Changed Successfully", Toast.LENGTH_SHORT, true).show();
                        //vehicleListAdapter.notifyDataSetChanged();

                        vehicleListsResponseCall(vehicletypeId);

                        //callDirections("3");

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<VehicleStatusChangeResponse> call, @NotNull Throwable t) {
                dialog.dismiss();

                Log.w(TAG,"LocationStatusChangeResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private VehicleStatusChangeRequest vehicleStatusChangeRequest(String vehicleid, String customerid, String vehicletypeId) {
        /*
         * vehicle_id : 5f0d4b23892e18724abf9a51
         * Customer_id : 5f07f9f313286d500bc9d4d8
         * Vehicletype_id:""
         */
        VehicleStatusChangeRequest vehicleStatusChangeRequest = new VehicleStatusChangeRequest();
        vehicleStatusChangeRequest.setVehicle_id(vehicleid);
        vehicleStatusChangeRequest.setCustomer_id(customerid);
        vehicleStatusChangeRequest.setVehicletype_id(vehicletypeId);
        Log.w(TAG,"VehicleStatusChangeRequest"+ "--->" + new Gson().toJson(vehicleStatusChangeRequest));
        return vehicleStatusChangeRequest;
    }



}
