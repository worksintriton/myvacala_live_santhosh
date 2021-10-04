package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;

import android.content.Context;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.myvacala.R;
import com.triton.myvacala.listeners.OnLoadMoreListener;

import java.util.ArrayList;


public class ViewYourOrderStatusAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "ViewYourOrderStatusAdapter";
    private ArrayList<String> orderStatusList;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;


    public static String id = "";
    Context context;
    String vehicletype;
    String bookingstatus;


    public ViewYourOrderStatusAdapter(Context context, ArrayList<String> orderStatusList, RecyclerView inbox_list, String vehicletype, String bookingstatus) {
        this.context =context;
        this.orderStatusList = orderStatusList;
        this.vehicletype = vehicletype;
        this.bookingstatus = bookingstatus;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_status, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.w(TAG,"onBindViewHolder--->");
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

            holder.txt_order_status.setText(orderStatusList.get(position));

        Log.w(TAG,"bookingstatus : "+bookingstatus+" orderStatusList.get(position) : "+orderStatusList.get(position));

            if(bookingstatus.equalsIgnoreCase(orderStatusList.get(position))){
                holder.img_vehicletype.setVisibility(View.VISIBLE);

            }else {
                holder.img_vehicletype.setVisibility(View.INVISIBLE);

            }

            Log.w(TAG,"vehicletype-->"+vehicletype);
            if(vehicletype.trim().equalsIgnoreCase("Four Wheeler".trim())){
                holder.img_vehicletype.setImageResource(R.drawable.ic_car);

            }else{
                holder.img_vehicletype.setImageResource(R.drawable.ic_bike);

            }




    }









    @Override
    public int getItemCount() {
        return orderStatusList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_order_status;
        public ImageView img_vehicletype;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            img_vehicletype = itemView.findViewById(R.id.img_vehicletype);
            img_vehicletype.setVisibility(View.INVISIBLE);


        }




    }












}
