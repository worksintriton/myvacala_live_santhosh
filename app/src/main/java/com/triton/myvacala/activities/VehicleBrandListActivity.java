package com.triton.myvacala.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.VehicleBrandExpandableListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.KeyboardUtils;
import com.triton.myvacala.interfaces.VehicleIdListener;
import com.triton.myvacala.requestpojo.VehicleBrandListRequest;
import com.triton.myvacala.responsepojo.VehicleBrandListResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;



import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleBrandListActivity extends AppCompatActivity implements View.OnClickListener, VehicleIdListener,VehicleBrandExpandableListAdapter.ExpandListChildViewListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.togglebutton)
    ToggleButton toggleButton;


   @BindView(R.id.lvExp_popularvehiclelist)
   ExpandableListView lvExp_popularvehiclelist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.txt_popularvehicletype)
    TextView txt_popularvehicletype;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.img_clear)
    ImageView img_clear;

    @BindView(R.id.img_search)
    ImageView img_search;

    @BindView(R.id.edt_selectbymodelorbrand)
    EditText edt_selectbymodelorbrand;

    String searchString = "";

    String selectedVehicleTypeId;




    private String TAG = "VehicleBrandListActivity";

    private List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList = null;

    List<VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean> vehicleModelBeans;


    String twowheelervehicleid = "",fourwheelervehicleid ="";
    public String vehicletype_id = "",vehicletype_Name ="";


    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    HashMap<String ,List<VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean>> stringListHashMap = new LinkedHashMap<>();


    List<VehicleBrandListResponse.DataBean> popularVehicleListResponseList  = new ArrayList<>();
    List<VehicleBrandListResponse.DataBean> popularVehicleList  = new ArrayList<>();

    List<VehicleBrandListResponse.DataBean.VehicleNameListBean> vehicle_name_list = new ArrayList<>();


    int listposition ;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_brand_list);
        ButterKnife.bind(this);




        avi_indicator.setVisibility(View.GONE);

        toggleButton.setOnClickListener(v -> {

            if(toggleButton.isChecked()){
                //Button is ON

                Log.w(TAG,"Car");
                txt_popularvehicletype.setText("Popular Cars");
                vehicletype_Name = "Four Wheeler";
                selectedVehicleTypeId = fourwheelervehicleid;
                vehicleBrandListResponseCall(selectedVehicleTypeId,searchString);
                Log.w(TAG,"four wheeler clicked :"+fourwheelervehicleid);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_car);

            }
            else{
                //Button is OFF

                Log.w(TAG,"Bikes");
                vehicletype_Name = "Two Wheeler";
                txt_popularvehicletype.setText("Popular Bikes");
                Log.w(TAG,"Two wheeler clicked :"+twowheelervehicleid);
                selectedVehicleTypeId = twowheelervehicleid;
                vehicleBrandListResponseCall(selectedVehicleTypeId,searchString);
                toggleButton.setBackgroundResource(R.drawable.ic_toggle_bike);

            }

        });

        imgBack.setOnClickListener(this);
        img_clear.setVisibility(View.GONE);
        img_search.setVisibility(View.GONE);
        img_search.setOnClickListener(this);
        img_clear.setOnClickListener(this);

        if (new ConnectionDetector(VehicleBrandListActivity.this).isNetworkAvailable(VehicleBrandListActivity.this)) {

            vehicleTypeGetListResponseCall();
        }

        edt_selectbymodelorbrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.w(TAG,"beforeTextChanged-->"+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(TAG,"onTextChanged-->"+s.toString());
                searchString = s.toString();
                if(!searchString.isEmpty()){
                    img_search.setVisibility(View.VISIBLE);
                    img_clear.setVisibility(View.VISIBLE);

                }else{
                    img_search.setVisibility(View.GONE);
                    img_clear.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG,"afterTextChanged-->"+s.toString());

            }
        });


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
                                vehicletype_Name = "Two Wheeler";
                                Log.w(TAG,"twowheelervehicleid"+twowheelervehicleid);
                            }else if(vehicletype.equalsIgnoreCase("Four Wheeler")){
                                 fourwheelervehicleid = response.body().getData().get(i).get_id();
                                vehicletype_id = fourwheelervehicleid;
                                vehicletype_Name = "Four Wheeler";
                                selectedVehicleTypeId = fourwheelervehicleid;

                                vehicleBrandListResponseCall(selectedVehicleTypeId,searchString);

                                Log.w(TAG,"fourwheelervehicleid"+fourwheelervehicleid);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.img_search:
                vehicleBrandListResponseCall(selectedVehicleTypeId, searchString);
                KeyboardUtils.hideKeyboard(VehicleBrandListActivity.this);
                break;

            case R.id.img_clear:
                edt_selectbymodelorbrand.setText("");
                vehicleBrandListResponseCall(selectedVehicleTypeId, "");
                KeyboardUtils.hideKeyboard(VehicleBrandListActivity.this);
                break;
        }


    }


    public void vehicleBrandListResponseCall(String selectedVehicleTypeId, String searchString){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleBrandListResponse> call = apiInterface.vehicleBrandListResponseCall(RestUtils.getContentType(),vehicleBrandListRequest(selectedVehicleTypeId,searchString));
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VehicleBrandListResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleBrandListResponse> call, @NotNull Response<VehicleBrandListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "vehicleBrandListResponseCall" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        popularVehicleListResponseList.clear();
                        popularVehicleList.clear();
                        stringListHashMap.clear();
                        popularVehicleListResponseList  = response.body().getData();
                        List<VehicleBrandListResponse.DataBean> expandableListTitle = new ArrayList<>();
                        for(int i=0; i<popularVehicleListResponseList.size();i++){
                            String vehiclebrandname = popularVehicleListResponseList.get(i).getVehicle_Brand_Name();
                           // String vehiclebrandimage = popularVehicleListResponseList.get(i).getVehicle_Brand_Image();
                            String getId = popularVehicleListResponseList.get(i).get_id();
                            String vehicle_Type_id = popularVehicleListResponseList.get(i).getVehicle_Type_id();
                            Log.w(TAG,"Size-->"+popularVehicleListResponseList.size()+" "+"vehiclebrandname :"+vehiclebrandname);

                            vehicle_name_list = popularVehicleListResponseList.get(i).getVehicle_name_list();

                            VehicleBrandListResponse.DataBean dataBean = new VehicleBrandListResponse.DataBean(getId,vehicle_Type_id,vehiclebrandname);
                            expandableListTitle.add(dataBean);

                            List<VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean> vehicleModelBeansList = new ArrayList<>();

                            if(vehicle_name_list.size() >0) {

                               for (int j=0; j<vehicle_name_list.size();j++) {

                                   vehicleModelBeans = vehicle_name_list.get(j).getVehicle_Model();
                                   String id = vehicle_name_list.get(j).get_id();
                                   String vehicle_brand_id = vehicle_name_list.get(j).getVehicle_Brand_id();
                                   String vehicle_name = vehicle_name_list.get(j).getVehicle_Name();
                                    int mfg_year_start = vehicle_name_list.get(j).getMfg_year_start();
                                    int mfg_year_end = vehicle_name_list.get(j).getMfg_year_end();
                                   //List<VehicleBrandListResponse.DataBean.VehicleNameListBean> nameListBeans = vehicle_name_list;

                                   for (int k=0; k<vehicleModelBeans.size();k++) {
                                       String vehicleid = vehicleModelBeans.get(k).get_id();
                                       String vehiclename = vehicleModelBeans.get(k).getVehicleModel_Name();
                                       String vehicleimage = vehicleModelBeans.get(k).getVehicleModel_Image();
                                       VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean vehicleModelBean1 =
                                               new VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean(vehicleid, vehiclename, vehicleimage,id,vehicle_brand_id,vehicle_name,mfg_year_start,mfg_year_end);
                                       vehicleModelBeansList.add(vehicleModelBean1);
                                   }
                               }


                           }
                            stringListHashMap.put(vehiclebrandname,vehicleModelBeansList);

                        }
                        if(response.body().getData().isEmpty()){
                            lvExp_popularvehiclelist.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);

                        }else{
                            tv_norecords.setVisibility(View.GONE);
                            lvExp_popularvehiclelist.setVisibility(View.VISIBLE);
                            setView(expandableListTitle);
                        }



                    }
                }else{
                    assert response.body() != null;
                    showErrorLoading(response.body().getMessage());
                }

            }






            @Override
            public void onFailure(@NotNull Call<VehicleBrandListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"vehicleBrandListResponseCallflr"+t.getMessage());
            }
        });

    }
    private VehicleBrandListRequest vehicleBrandListRequest(String selectedVehicleTypeId, String searchString) {
        /*
         * Vehicle_Type_id : 5f0c0d092f857d66950cf260
         * search_string : puls
         */
        VehicleBrandListRequest vehicleBrandListRequest = new VehicleBrandListRequest();
        vehicleBrandListRequest.setVehicle_Type_id(selectedVehicleTypeId);
        vehicleBrandListRequest.setSearch_string(searchString);

        Log.w(TAG," vehicleBrandListRequest"+ new Gson().toJson(vehicleBrandListRequest));
        return vehicleBrandListRequest;
    }



    private void setView(List<VehicleBrandListResponse.DataBean> expandableListTitle){

        String vehicletype_Name;
        if (toggleButton.isChecked()) {
            vehicletype_Name = "Four Wheeler";
        }else {
            vehicletype_Name = "Two Wheeler";
        }

        List<String> vehicleTypeGetList = new ArrayList<>(stringListHashMap.keySet());
        Log.w(TAG,"expandableListTitle-->"+expandableListTitle.size());
        Log.w(TAG,"stringListHashMap-->"+stringListHashMap);

        Log.w(TAG,"Data size-->"+popularVehicleListResponseList.size());
        Log.w(TAG,"vehicleTypeGetList-->"+new Gson().toJson(vehicleTypeGetList));

        VehicleBrandExpandableListAdapter vehicleBrandExpandableListAdapter = new VehicleBrandExpandableListAdapter(this, expandableListTitle, stringListHashMap,vehicle_name_list,
                VehicleBrandListActivity.this,vehicleTypeGetList,this,vehicletype_Name);
        lvExp_popularvehiclelist.setAdapter(vehicleBrandExpandableListAdapter);

        lvExp_popularvehiclelist.setOnGroupExpandListener(groupPosition -> Log.w(TAG,"List Expanded-->"+expandableListTitle.get(groupPosition)));

        lvExp_popularvehiclelist.setOnGroupCollapseListener(groupPosition -> Log.w(TAG,"List Collapsed-->"+expandableListTitle.get(groupPosition)));

        lvExp_popularvehiclelist.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> false);
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
        finish();
    }




    @Override
    public void vehicleId(int position) {
        listposition = position;
        Log.w(TAG,"position--->"+listposition);



    }

    @Override
    public void expandListChildView(int position) {
        Toast.makeText(this, "-------->"+position, Toast.LENGTH_SHORT).show();
    }




}