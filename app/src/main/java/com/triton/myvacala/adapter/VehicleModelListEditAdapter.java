package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.interfaces.VehicleFuelTypeListener;
import com.triton.myvacala.interfaces.VehicleModeTypeListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.VehicleDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
public class VehicleModelListEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private String TAG = "VehicleModelListEditAdapter";
    List<VehicleDetailsResponse.DataBean.VehicleModelBean> vehicleModelBeanList = new ArrayList<>();
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private VehicleDetailsResponse.DataBean.VehicleModelBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate, formatedStartDate = "";
    Dialog dialog;
    private VehicleModeTypeListener vehicleModeTypeListener;

    int selectedPosition = -1;
    String fuel_Type_Name;

    Boolean isSelect = false;


    public VehicleModelListEditAdapter(Context context, List<VehicleDetailsResponse.DataBean.VehicleModelBean> vehicleModelBeanList, RecyclerView inbox_list, VehicleModeTypeListener vehicleModeTypeListener, String fuel_Type_Name) {
        this.vehicleModelBeanList = vehicleModelBeanList;
        this.context = context;
        this.vehicleModeTypeListener = vehicleModeTypeListener;
        this.fuel_Type_Name = fuel_Type_Name;


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
                Log.i("Timer", "Timer");
            }
        }, 120000);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vehicle_model_list_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.w(TAG, "onBindViewHolder--->");
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = vehicleModelBeanList.get(position);
        Log.w(TAG, "Model_Name-->" + currentItem.getVehicleModel_Name());
        holder.ll_root_vehiclemodel.setBackgroundResource(R.drawable.button_background_corner);

        holder.txt_brand_vehicle_name.setText(currentItem.getVehicleModel_Name());
        if (currentItem.getVehicleModel_Image() != null && !currentItem.getVehicleModel_Image().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getVehicleModel_Image())
                    .into(holder.cv_brand_vehicle_image);

        }else{
            Glide.with(context)
                    .load(R.drawable.logo)
                    .into(holder.cv_brand_vehicle_image);

        }
        Log.w(TAG, "position--->" + position);
        Log.w(TAG, "List If------->" + new Gson().toJson(vehicleModelBeanList));
        if (vehicleModelBeanList.get(position).isSelected()) {
            Toasty.info(context, "Selected Model Type : " + holder.txt_brand_vehicle_name.getText().toString(), Toast.LENGTH_SHORT, true).show();
            Log.w(TAG, "IF isSelected--->");
            holder.ll_root_vehiclemodel.setBackgroundResource(R.drawable.button_background_corner);


            return;

        } else {
            Log.w(TAG, "ELSE isSelected--->");
            holder.ll_root_vehiclemodel.setBackgroundResource(R.drawable.layout_bg);




        }



        holder.cv_brand_vehicle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = true;
                //selectedPosition=position;
                //notifyDataSetChanged();
                Log.w(TAG, "setOnClickListener Position--->" + position);
                vehicleModeTypeListener.vehicleModelList(position);
                Log.w(TAG, "isSelected---->" + vehicleModelBeanList.get(position).isSelected());

            }
        });



    }


    @Override
    public int getItemCount() {
        //Log.w(TAG,"Size---->"+fuelTypeBeanList.size());
        return vehicleModelBeanList.size();

    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_brand_vehicle_name;
        public ImageView cv_brand_vehicle_image;
        public LinearLayout ll_root_vehiclemodel;


        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_brand_vehicle_name = itemView.findViewById(R.id.txt_brand_vehicle_name);
            cv_brand_vehicle_image = itemView.findViewById(R.id.cv_brand_vehicle_image);
            ll_root_vehiclemodel = itemView.findViewById(R.id.ll_root_vehiclemodel);

        }

    }
}

