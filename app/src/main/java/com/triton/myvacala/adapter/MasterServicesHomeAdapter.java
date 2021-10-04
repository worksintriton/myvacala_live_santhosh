package com.triton.myvacala.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.triton.myvacala.R;
import com.triton.myvacala.activities.PopularServiceActivity;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MasterServicesHomeAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "MasterServicesHomeAdapter";
    private List<MasterServiceGetlistResponse.MasterserviceListBean> masterServiceGetlistResponseList  = null;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    MasterServiceGetlistResponse.MasterserviceListBean currentItem;




    private List<MasterServiceGetlistResponse.VehicletypeDetailsBean> vehicletypeDetailsBeanList = null;
    private ArrayList<MasterServiceGetlistResponse.CustomerVehicleDataBean> customerVehicleDataBeanList = null;


    String city,street;
    String vehicleImage,vehicleName, vehicleModelName,fuelType;
    String status,vehicletypeId,yearOfManufacture,fuelTypeId;

    String masterservicename;


    public MasterServicesHomeAdapter(Context context, List<MasterServiceGetlistResponse.MasterserviceListBean> masterServiceGetlistResponseList, RecyclerView inbox_list, List<MasterServiceGetlistResponse.VehicletypeDetailsBean> vehicletypeDetailsBeanList, String city, String street, String vehicleImage, String vehicleName, String vehicleModelName, String fuelType, ArrayList<MasterServiceGetlistResponse.CustomerVehicleDataBean> customerVehicleDataBeanList) {
        this.masterServiceGetlistResponseList = masterServiceGetlistResponseList;
        this.context = context;
        this.vehicletypeDetailsBeanList = vehicletypeDetailsBeanList;
        this.city = city;
        this.street = street;
        this.vehicleImage = vehicleImage;
        this.vehicleName = vehicleName;
        this.vehicleModelName = vehicleModelName;
        this.fuelType = fuelType;
        this.customerVehicleDataBeanList = customerVehicleDataBeanList;


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) inbox_list.getLayoutManager();
        inbox_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();


                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                handler.postDelayed(this, 120000); //now is every 2 minutes
                Log.i("Timer","Timer");
            }
        }, 120000);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_serviceshome_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = masterServiceGetlistResponseList.get(position);
        for (int i = 0; i < masterServiceGetlistResponseList.size(); i++) {

            holder.txt_service.setText(currentItem.getMasterservice_Name());

        }

        if (currentItem.getService_Image() != null && !currentItem.getService_Image().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getService_Image())
                    //.load(R.drawable.logo)
                    .into(holder.cv_service);

        }
        else{
            Glide.with(context)
                    .load(R.drawable.logo)
                    .into(holder.cv_service);

        }

        holder.ll_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicletype = null,twowheelerstatus = null,fourwheelerstatus = null;
                if (masterServiceGetlistResponseList.get(position).getMasterservice_Name().equalsIgnoreCase("Book A Mechanic")) {
                    String twowheelervehicleid = null, fourwheelervehicleid = null;
                    if (!vehicletypeDetailsBeanList.isEmpty()) {
                        for (int i = 0; i < vehicletypeDetailsBeanList.size(); i++) {
                             vehicletype = vehicletypeDetailsBeanList.get(i).getVehicle_Type();
                            String id = vehicletypeDetailsBeanList.get(i).get_id();
                            Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                            if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                                twowheelervehicleid = vehicletypeDetailsBeanList.get(i).get_id();
                                Log.w(TAG, "twowheelervehicleid" + twowheelervehicleid);
                            } else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                                fourwheelervehicleid = vehicletypeDetailsBeanList.get(i).get_id();
                                Log.w(TAG, "fourwheelervehicleid" + fourwheelervehicleid);
                            }


                        }
                    }
                    if (!customerVehicleDataBeanList.isEmpty()) {
                        if (vehicletype != null) {
                            for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                                 vehicletype = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                                String id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                                Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                                if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                                    fourwheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                                    if(fourwheelerstatus != null && fourwheelerstatus.equalsIgnoreCase("Default")){
                                        fourwheelervehicleid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                                        vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                                        vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                                        vehicleModelName = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                                        fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                                        status = customerVehicleDataBeanList.get(i).getStatus();
                                        vehicletypeId = customerVehicleDataBeanList.get(i).getVehicletype_id();
                                        yearOfManufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                                        fuelTypeId = customerVehicleDataBeanList.get(i).getFuel_Type_id();
                                        Log.w(TAG, "fourwheelervehicleid" + fourwheelervehicleid);
                                    }


                                } else {
                                    twowheelerstatus = customerVehicleDataBeanList.get(i).getStatus();
                                    if(twowheelerstatus != null && twowheelerstatus.equalsIgnoreCase("Default")){
                                        twowheelervehicleid = customerVehicleDataBeanList.get(i).getVehicletype_id();
                                        vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                                        vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                                        vehicleModelName = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                                        fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                                        status = customerVehicleDataBeanList.get(i).getStatus();
                                        vehicletypeId = customerVehicleDataBeanList.get(i).getVehicletype_id();
                                        yearOfManufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                                        fuelTypeId = customerVehicleDataBeanList.get(i).getFuel_Type_id();

                                        Log.w(TAG, "twowheelervehicleid" + fourwheelervehicleid);
                                    }


                                }
                                Log.w(TAG, "twowheelerstatus : " + twowheelerstatus + " " + "fourwheelerstatus : " + fourwheelerstatus);


                            }
                        }
                     /*   for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                            vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                            vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                            vehicleModelName = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                            fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                            status = customerVehicleDataBeanList.get(i).getStatus();
                            vehicletypeId = customerVehicleDataBeanList.get(i).getVehicletype_id();
                            yearOfManufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                            fuelTypeId = customerVehicleDataBeanList.get(i).getFuel_Type_id();
                        }*/
                        Log.w(TAG, "vehicleImage : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + vehicleModelName + " " + "fuelType : " + fuelType);
                        Log.w(TAG, "status : " + status + " " + "vehicletypeId : " + vehicletypeId + " " + "yearOfManufacture : " + yearOfManufacture + " " + "fuelTypeId : " + fuelTypeId);

                    }
                    masterservicename = masterServiceGetlistResponseList.get(position).getMasterservice_Name();
                    Intent intent = new Intent(context, PopularServiceActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("twowheelervehicleid", twowheelervehicleid);
                    intent.putExtra("fourwheelervehicleid", fourwheelervehicleid);
                    intent.putExtra("masterserviceid", masterServiceGetlistResponseList.get(position).get_id());
                    intent.putExtra("city", city);
                    intent.putExtra("street", street);
                    intent.putExtra("vehicleImage", vehicleImage);
                    intent.putExtra("vehicleName", vehicleName);
                    intent.putExtra("vehicleModelName", vehicleModelName);
                    intent.putExtra("fuelType", fuelType);
                    intent.putExtra("masterservicename", masterservicename);
                    intent.putExtra("customerVehicleDataBeanList", customerVehicleDataBeanList);
                    Log.w(TAG, "twowheelervehicleid :" + twowheelervehicleid + " " + "fourwheelervehicleid : " + fourwheelervehicleid + " " + "masterserviceid : " + masterServiceGetlistResponseList.get(position).get_id());
                    Log.w(TAG, "vehicleImage : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + vehicleModelName + " " + "fuelType : " + fuelType + " " + "masterservicename : " + masterservicename);

                    context.startActivity(intent);
                }


                String servicename = masterServiceGetlistResponseList.get(position).getMasterservice_Name().trim();
                Log.w(TAG,"ServiceName--->"+servicename);
                if (servicename.equalsIgnoreCase("Book  a Parking")) {
                    String twowheelervehicleid = null, fourwheelervehicleid = null;
                    if (!vehicletypeDetailsBeanList.isEmpty()) {
                        for (int i = 0; i < vehicletypeDetailsBeanList.size(); i++) {
                             vehicletype = vehicletypeDetailsBeanList.get(i).getVehicle_Type();
                            String id = vehicletypeDetailsBeanList.get(i).get_id();
                            Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);
                            if (vehicletype.equalsIgnoreCase("Two Wheeler")) {
                                twowheelervehicleid = vehicletypeDetailsBeanList.get(i).get_id();
                                Log.w(TAG, "twowheelervehicleid" + twowheelervehicleid);
                            } else if (vehicletype.equalsIgnoreCase("Four Wheeler")) {
                                fourwheelervehicleid = vehicletypeDetailsBeanList.get(i).get_id();
                                Log.w(TAG, "fourwheelervehicleid" + fourwheelervehicleid);
                            }


                        }
                    }
                    if (!customerVehicleDataBeanList.isEmpty()) {
                        for (int i = 0; i < customerVehicleDataBeanList.size(); i++) {
                            vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                            vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                            vehicleModelName = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                            fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                            status = customerVehicleDataBeanList.get(i).getStatus();
                            vehicletypeId = customerVehicleDataBeanList.get(i).getVehicletype_id();
                            yearOfManufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                            fuelTypeId = customerVehicleDataBeanList.get(i).getFuel_Type_id();
                        }
                        Log.w(TAG, "vehicleImage : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + vehicleModelName + " " + "fuelType : " + fuelType);
                        Log.w(TAG, "status : " + status + " " + "vehicletypeId : " + vehicletypeId + " " + "yearOfManufacture : " + yearOfManufacture + " " + "fuelTypeId : " + fuelTypeId);

                    }
                    masterservicename = masterServiceGetlistResponseList.get(position).getMasterservice_Name();
                    //Intent intent = new Intent(context, ParkingDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent intent = new Intent(context, DashboardParkingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("twowheelervehicleid", twowheelervehicleid);
                    intent.putExtra("fourwheelervehicleid", fourwheelervehicleid);
                    intent.putExtra("masterserviceid", masterServiceGetlistResponseList.get(position).get_id());
                    intent.putExtra("city", city);
                    intent.putExtra("street", street);
                    intent.putExtra("vehicleImage", vehicleImage);
                    intent.putExtra("vehicleName", vehicleName);
                    intent.putExtra("vehicleModelName", vehicleModelName);
                    intent.putExtra("fuelType", fuelType);
                    intent.putExtra("masterservicename", masterservicename);
                    intent.putExtra("customervehicledatabeanlist", customerVehicleDataBeanList);
                    Log.w(TAG, "twowheelervehicleid :" + twowheelervehicleid + " " + "fourwheelervehicleid : " + fourwheelervehicleid + " " + "masterserviceid : " + masterServiceGetlistResponseList.get(position).get_id());
                    Log.w(TAG, "vehicleImage : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + vehicleModelName + " " + "fuelType : " + fuelType + " " + "masterservicename : " + masterservicename);

                    context.startActivity(intent);
                }


            }


        });






    }

    @Override
    public int getItemCount() {
        return masterServiceGetlistResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_service;
        public LinearLayout ll_services;
        public CircleImageView cv_service;



        public ViewHolderOne(View itemView) {
            super(itemView);

            cv_service = itemView.findViewById(R.id.cv_service);
            txt_service = itemView.findViewById(R.id.txt_service);
            ll_services = itemView.findViewById(R.id.ll_services);



        }




    }














}
