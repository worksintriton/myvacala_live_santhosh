package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.Parking_Options_Activity;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.GetCustomerVehicleandLocationResponse;
import com.triton.myvacala.responsepojo.NotificationListResponse;
import com.triton.myvacala.responsepojo.ParkingListResponse;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ParkingOptionsListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "ParkingOptionsListAdapter";
    private List<ParkingListResponse.DataBean> dataBeanList;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    ParkingListResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    Dialog dialog;
    private int SlotCountNumbers = 0;


    public static String id = "";
    Context context;
    private String selectedVehicleType;

    private ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;



    public ParkingOptionsListAdapter(Context context, List<ParkingListResponse.DataBean> dataBeanList, RecyclerView inbox_list, String selectedVehicleType, ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean> customerVehicleDataBeanList) {
        this.dataBeanList = dataBeanList;
        this.context = context;
        this.selectedVehicleType = selectedVehicleType;
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

                assert linearLayoutManager != null;
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

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_parkingoptions_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);
        holder.txt_parking_name.setText(dataBeanList.get(position).getParking_details_name());
        holder.txt_address.setText(dataBeanList.get(position).getParking_details_address());
        holder.txt_distance_reachtime.setText(dataBeanList.get(position).getParking_reach_time()+" / "+dataBeanList.get(position).getParking_distance());
        holder.txt_parking_prices.setText("\u20B9 "+dataBeanList.get(position).getParking_prices()+"");

        Log.w(TAG," selectedVehicleType :"+selectedVehicleType);
        if(selectedVehicleType.equalsIgnoreCase("Four Wheeler")){
            if(dataBeanList.get(position).getParking_details_slots_count_Car() != 0){
                holder.btn_parking_slots_count.setText(dataBeanList.get(position).getParking_details_slots_count_Car()+" Available");
            }else{
                holder.btn_parking_slots_count.setText("Not Available");
            }

        }else{
            if(dataBeanList.get(position).getParking_details_slots_count_Bike() != 0){
                holder.btn_parking_slots_count.setText(dataBeanList.get(position).getParking_details_slots_count_Bike()+" Available");

            } else{
                holder.btn_parking_slots_count.setText("Not Available");
            }


        }


        holder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                // String shareBody = sharelink;
                String shareMessage= dataBeanList.get(position).getParking_details_maplink();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Vacala");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });

        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedVehicleType.equalsIgnoreCase("Four Wheeler")){
                    //holder.btn_parking_slots_count.setText(dataBeanList.get(position).getParking_details_slots_count_Car()+" Available");
                    SlotCountNumbers = dataBeanList.get(position).getParking_details_slots_count_Car();

                }else{
                   // holder.btn_parking_slots_count.setText(dataBeanList.get(position).getParking_details_slots_count_Bike()+" Available");
                    SlotCountNumbers = dataBeanList.get(position).getParking_details_slots_count_Bike();
                }

                if(SlotCountNumbers != 0) {
                    Intent i = new Intent(context, Parking_Options_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", dataBeanList.get(position).get_id());
                    i.putExtra("parkingname", dataBeanList.get(position).getParking_details_name());
                    i.putExtra("sharemaplink", dataBeanList.get(position).getParking_details_maplink());
                    i.putExtra("address", dataBeanList.get(position).getParking_details_address());
                    i.putExtra("parkingavlslotcountcar", dataBeanList.get(position).getParking_details_slots_count_Car());
                    i.putExtra("parkingavlslotcountbike", dataBeanList.get(position).getParking_details_slots_count_Bike());
                    i.putExtra("reachtime", dataBeanList.get(position).getParking_reach_time());
                    i.putExtra("parkingdistance", dataBeanList.get(position).getParking_distance());
                    i.putExtra("parkingprices", dataBeanList.get(position).getParking_prices());
                    i.putExtra("customerVehicleDataBeanList", customerVehicleDataBeanList);
                    i.putExtra("selectedVehicleType", selectedVehicleType);
                    context.startActivity(i);

                }
                else{
                    Toasty.error(context, "No slots available", Toast.LENGTH_SHORT, true).show();

                }

            }
        });









    }









    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_parking_name,txt_address,txt_distance_reachtime,txt_parking_prices;
        public LinearLayout ll_share ;
        public  Button btn_parking_slots_count,btn_book;






        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_parking_name = itemView.findViewById(R.id.txt_parking_name);
            btn_parking_slots_count = itemView.findViewById(R.id.btn_parking_slots_count);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_distance_reachtime = itemView.findViewById(R.id.txt_distance_reachtime);
            txt_parking_prices = itemView.findViewById(R.id.txt_parking_prices);
            ll_share = itemView.findViewById(R.id.ll_share);
            btn_book = itemView.findViewById(R.id.btn_book);


        }




    }












}
