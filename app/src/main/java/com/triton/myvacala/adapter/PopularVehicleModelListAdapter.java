package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.triton.myvacala.R;
import com.triton.myvacala.interfaces.VehicleModeTypeListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.PopularVehicleListResponse;
import com.triton.myvacala.responsepojo.VehicleDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PopularVehicleModelListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "PopularVehicleModelListAdapter";
    List<PopularVehicleListResponse.DataBean.VehicleDetailsBean.VehicleModelBean> vehicleModelBeanList = new ArrayList<>();

    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    PopularVehicleListResponse.DataBean.VehicleDetailsBean.VehicleModelBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;

    int selectedPosition=-1;


    private VehicleModeTypeListener vehicleModeTypeListener;


    public PopularVehicleModelListAdapter(Context context, List<PopularVehicleListResponse.DataBean.VehicleDetailsBean.VehicleModelBean> vehicleModelBeanList, RecyclerView inbox_list, VehicleModeTypeListener vehicleModeTypeListener) {
        this.vehicleModelBeanList = vehicleModelBeanList;
        this.context = context;
        this.vehicleModeTypeListener = vehicleModeTypeListener;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vehicle_model_list_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        if(selectedPosition==position)
            holder.ll_root_vehiclemodel.setBackgroundResource(R.drawable.button_background_corner);
         else
            holder.ll_root_vehiclemodel.setBackgroundResource(R.drawable.layout_bg);

           // holder.ll_root_vehiclemodel.setBackgroundColor(Color.parseColor("#ffffff"));

        currentItem = vehicleModelBeanList.get(position);
        Log.w(TAG,"vehiclename-->"+currentItem.getVehicleModel_Name());
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

        holder.cv_brand_vehicle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                vehicleModeTypeListener.vehicleModelList(position);
            }
        });

        holder.ll_root_vehiclemodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();


            }
        });









    }









    @Override
    public int getItemCount() {
        Log.w(TAG,"Size---->"+vehicleModelBeanList.size());
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
