package com.triton.myvacala.adapter;

import android.app.Dialog;
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
import com.triton.myvacala.activities.SubServiceDetailsActivity;
import com.triton.myvacala.activities.SubServicesActivity;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.MainServiceGetListResponse;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainServicesHomeAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "MainServicesHomeAdapter";
    private List<MainServiceGetListResponse.DataBean.MainserviceslistBean> masterServiceGetlistResponseList  = null;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    MainServiceGetListResponse.DataBean.MainserviceslistBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;
    String city = "",street = "";
    String vehicleImage,vehicleName, vehicleModelName,fuelType;
    String servicename;
    String masterservicename;

    String vehicletypename;
    String twowheelervehicleid = null,fourwheelervehicleid = null,masterserviceid = null;

    ArrayList<MainServiceGetListResponse.DataBean.CustomerVehicleDataBean> customerVehicleDataBeanList;



    String selectedVehicleType, selectedVehicleId;


    public MainServicesHomeAdapter(Context context, List<MainServiceGetListResponse.DataBean.MainserviceslistBean> masterServiceGetlistResponseList, RecyclerView inbox_list, String city, String street, String vehicleImage, String vehicleName, String vehicleModelName, String fuelType, String masterservicename, String vehicletypename, ArrayList<MainServiceGetListResponse.DataBean.CustomerVehicleDataBean> customerVehicleDataBeanList, String masterserviceid, String twowheelervehicleid, String fourwheelervehicleid, String selectedVehicleType, String selectedVehicleId) {
        this.masterServiceGetlistResponseList = masterServiceGetlistResponseList;
        this.context = context;
        this.city = city;
        this.street = street;
        this.vehicleImage = vehicleImage;
        this.vehicleName = vehicleName;
        this.vehicleModelName = vehicleModelName;
        this.fuelType = fuelType;
        this.masterservicename = masterservicename;
        this.vehicletypename = vehicletypename;
        this.customerVehicleDataBeanList = customerVehicleDataBeanList;
        this.masterserviceid = masterserviceid;
        this.twowheelervehicleid = twowheelervehicleid;
        this.fourwheelervehicleid = fourwheelervehicleid;
        this.selectedVehicleType = selectedVehicleType;
        this.selectedVehicleId = selectedVehicleId;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mainserviceshome_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    private void initLayoutOne(ViewHolderOne holder, final int position) {



        currentItem = masterServiceGetlistResponseList.get(position);
        for (int i = 0; i < masterServiceGetlistResponseList.size(); i++) {

            holder.txt_service.setText(currentItem.getService_Name());

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
                servicename = masterServiceGetlistResponseList.get(position).getService_Name();

                Intent intent = new Intent(context, SubServicesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("vehicletypeid",masterServiceGetlistResponseList.get(position).getVehicle_Type_id().get_id());
                intent.putExtra("serviceid",masterServiceGetlistResponseList.get(position).get_id());
                intent.putExtra("city",city);
                intent.putExtra("street",street);
                intent.putExtra("vehicleImage", vehicleImage);
                intent.putExtra("vehicleName", vehicleName);
                intent.putExtra("vehicleModelName", vehicleModelName);
                intent.putExtra("fuelType", fuelType);
                intent.putExtra("servicename", servicename);
                intent.putExtra("masterservicename", masterservicename);
                intent.putExtra("vehicletypename", vehicletypename);
                intent.putExtra("customervehicledatabeanlist", customerVehicleDataBeanList);
                intent.putExtra("twowheelervehicleid",twowheelervehicleid);
                intent.putExtra("fourwheelervehicleid",fourwheelervehicleid);
                intent.putExtra("masterserviceid",masterserviceid);
                intent.putExtra("selectedVehicleType",selectedVehicleType);
                intent.putExtra("selectedVehicleId",selectedVehicleId);
                Log.w(TAG,"selectedVehicleId--->"+selectedVehicleId);

                Log.w(TAG,"vehicletypeid :"+masterServiceGetlistResponseList.get(position).getVehicle_Type_id().get_id()+" "+"serviceid : "+masterServiceGetlistResponseList.get(position).get_id());
                context.startActivity(intent);
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
