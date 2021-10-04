package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.YourEstimateDetailsActivity;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.BookingHistoryResponse;
import com.triton.myvacala.responsepojo.NotificationListResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChooseDateAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "NotificationsListAdapter";
    private List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    VehicleTypeGetListResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    Dialog dialog;

    public static String id = "";


    public ChooseDateAdapter(Context context, List<VehicleTypeGetListResponse.DataBean> vehicleTypeGetListResponseList, RecyclerView inbox_list) {
        this.vehicleTypeGetListResponseList = vehicleTypeGetListResponseList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_date_adpater, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = vehicleTypeGetListResponseList.get(position);
        for (int i = 0; i < vehicleTypeGetListResponseList.size(); i++) {


            holder.numberPickerOccurence.setDisplayedValues(new String[]{currentItem.getVehicle_Type()});



        }






    }









    @Override
    public int getItemCount() {
        return vehicleTypeGetListResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public NumberPicker numberPickerOccurence;



        public ViewHolderOne(View itemView) {
            super(itemView);
            numberPickerOccurence = itemView.findViewById(R.id.numberPickerOccurence);


        }




    }












}
