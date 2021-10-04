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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.myvacala.R;
import com.triton.myvacala.interfaces.VehicleFuelTypeListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.PopularVehicleListResponse;
import com.triton.myvacala.responsepojo.VehicleDetailsResponse;

import java.util.ArrayList;
import java.util.List;


public class PopularFuelTypeListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "PopularFuelTypeListAdapter";
    List<PopularVehicleListResponse.DataBean.VehicleDetailsBean.FuelTypeBean> fuelTypeBeanList = new ArrayList<>();
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    PopularVehicleListResponse.DataBean.VehicleDetailsBean.FuelTypeBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;
    private VehicleFuelTypeListener vehcilFuelTypeListener;

    int selectedPosition=-1;


    public PopularFuelTypeListAdapter(Context context, List<PopularVehicleListResponse.DataBean.VehicleDetailsBean.FuelTypeBean> fuelTypeBeanList, RecyclerView inbox_list, VehicleFuelTypeListener vehcilFuelTypeListener) {
        this.fuelTypeBeanList = fuelTypeBeanList;
        this.context = context;
        this.vehcilFuelTypeListener = vehcilFuelTypeListener;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fuel_type_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);

    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = fuelTypeBeanList.get(position);



        Log.w(TAG,"Fuel_Name-->"+currentItem.getFuel_Type());
        holder.btn_fuel_type.setText(currentItem.getFuel_Type());
        holder.btn_fuel_type.setBackgroundColor(Color.parseColor(currentItem.getBackground_Color()));

        holder.btn_fuel_type.setBackgroundResource(R.drawable.tags_rounded_corners);
        GradientDrawable gd = (GradientDrawable) holder.btn_fuel_type.getBackground();
        gd.setColor(Color.parseColor(currentItem.getBackground_Color()));
        gd.setCornerRadius(10);
        gd.setStroke(4, Color.WHITE);


        /*if(currentItem.getFuel_Type().trim().equalsIgnoreCase("Diesel")){
         holder.btn_fuel_type.setBackgroundColor(Color.parseColor(currentItem.getBackground_Color()));
        }
        if(currentItem.getFuel_Type().trim().equalsIgnoreCase("Petrol")){
            holder.btn_fuel_type.setBackgroundColor(Color.parseColor(currentItem.getBackground_Color()));
        }
        if(currentItem.getFuel_Type().trim().equalsIgnoreCase("Natural Gas")){
            holder.btn_fuel_type.setBackgroundColor(Color.parseColor(currentItem.getBackground_Color()));
        }
        if(currentItem.getFuel_Type().trim().equalsIgnoreCase("Bio-diesel")){
            holder.btn_fuel_type.setBackgroundColor(Color.parseColor(currentItem.getBackground_Color()));
        }
*/
        if(selectedPosition==position){
            holder.btn_fuel_type.setBackgroundResource(R.drawable.tags_rounded_corners);
            gd.setColor(Color.parseColor(currentItem.getBackground_Color()));
            gd.setCornerRadius(10);
            gd.setStroke(4, Color.BLACK);
            //holder.btn_fuel_type.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }

        holder.btn_fuel_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                vehcilFuelTypeListener.vehicleFuelList(position);
            }
        });

    }









    @Override
    public int getItemCount() {
        Log.w(TAG,"Size---->"+fuelTypeBeanList.size());
        return fuelTypeBeanList.size();

    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public Button btn_fuel_type;



        public ViewHolderOne(View itemView) {
            super(itemView);
            btn_fuel_type = itemView.findViewById(R.id.btn_fuel_type);

        }




    }







}
