package com.triton.myvacala.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.triton.myvacala.activities.AddAddressMapsActivity;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.GetServiceListResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CityListOldUserAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "CityListAdapter";
    private List<GetServiceListResponse.DataBean> getServiceListResponseList  = null;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    GetServiceListResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;


    public CityListOldUserAdapter(Context context, List<GetServiceListResponse.DataBean> getServiceListResponseList, RecyclerView inbox_list) {
        this.getServiceListResponseList = getServiceListResponseList;
        this.context = context;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_citylist_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = getServiceListResponseList.get(position);
        for (int i = 0; i < getServiceListResponseList.size(); i++) {

            holder.txt_cityname.setText(currentItem.getDisplay_Name());











            if (currentItem.getImage() != null && !currentItem.getImage().isEmpty()) {

                Glide.with(context)
                        .load(currentItem.getImage())
                        .into(holder.cv_cityimage);

            }else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(holder.cv_cityimage);

            }















        }
        holder.ll_citylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddAddressMapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("cityname",getServiceListResponseList.get(position).getDisplay_Name());
                i.putExtra("fromactivity","SelectYourCityOldUserActivity");
                Bundle b = new Bundle();
                b.putDouble("lat", getServiceListResponseList.get(position).getLat());
                b.putDouble("lon", getServiceListResponseList.get(position).getLong());
                i.putExtras(b);

                Log.w(TAG,"cityname-->"+getServiceListResponseList.get(position).getDisplay_Name());
                Log.w(TAG,"lat-->"+getServiceListResponseList.get(position).getLat()+" "+"lon-->"+getServiceListResponseList.get(position).getLong());

                context.startActivity(i);

            }
        });




    }









    @Override
    public int getItemCount() {
        return getServiceListResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_cityname;
        public LinearLayout ll_citylist;
        public CircleImageView cv_cityimage;



        public ViewHolderOne(View itemView) {
            super(itemView);

            txt_cityname = itemView.findViewById(R.id.txt_cityname);
            cv_cityimage = itemView.findViewById(R.id.cv_cityimage);
            ll_citylist = itemView.findViewById(R.id.ll_citylist);











        }




    }














}
