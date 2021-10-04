package com.triton.myvacala.activities.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.ChooseDateAdapter;
import com.triton.myvacala.adapter.NotificationsListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;
import com.triton.myvacala.utils.RestUtils;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScrollChoiceActivity extends AppCompatActivity {

    private static final String TAG = "ScrollChoiceActivity";
    private List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList;
    WheelPicker wheelPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_choice);


        wheelPicker = findViewById(R.id.wheelpicker);
        vehicleTypeGetListResponseCall();


        wheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                Log.w(TAG,"position : "+position+"data : "+ ((VehicleTypeGetListResponse.DataBean) data).get_id());
            }
        });







    }

    public void vehicleTypeGetListResponseCall(){

        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleTypeGetListResponse> call = apiInterface.vehicleTypeGetListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VehicleTypeGetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VehicleTypeGetListResponse> call, @NonNull Response<VehicleTypeGetListResponse> response) {


                if (response.body() != null) {
                    Log.w(TAG,"VehicleTypeGetListResponse" + new Gson().toJson(response.body()));

                    vehicleTypeGetListResponseList = response.body().getData();
                    if(vehicleTypeGetListResponseList.size()>0){
                        setView();
                    }

                    if(!vehicleTypeGetListResponseList.isEmpty()){
                        for(int i =0; i<vehicleTypeGetListResponseList.size();i++){
                            String vehicletype = response.body().getData().get(i).getVehicle_Type();
                            String id = response.body().getData().get(i).get_id();
                            Log.w(TAG,"vehicletype :"+vehicletype+" "+"id :"+id);
                            if(vehicletype.equalsIgnoreCase("Two Wheeler")){
                               String twowheelervehicleid = response.body().getData().get(i).get_id();
                                Log.w(TAG,"twowheelervehicleid"+twowheelervehicleid);
                            }else if(vehicletype.equalsIgnoreCase("Four Wheeler")){
                                String fourwheelervehicleid = response.body().getData().get(i).get_id();

                                Log.w(TAG,"fourwheelervehicleid"+fourwheelervehicleid);
                            }


                        }



                    }


                }








            }


            @Override
            public void onFailure(@NonNull Call<VehicleTypeGetListResponse> call,@NonNull  Throwable t) {

                Log.w(TAG,"VehicleTypeGetListResponseflr"+t.getMessage());
            }
        });

    }

    private void setView() {

        wheelPicker.setData(vehicleTypeGetListResponseList);
        wheelPicker.setAtmospheric(true);
        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(Color.LTGRAY);
        wheelPicker.setSelectedItemTextColor(Color.BLACK);
        wheelPicker.setItemTextColor(Color.DKGRAY);
        wheelPicker.setVisibleItemCount(5);
        wheelPicker.setCurved(true);
        wheelPicker.setItemSpace(70);









    }

}