package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.EditMapsActivity;
import com.triton.myvacala.activities.LocationListActivity;
import com.triton.myvacala.activities.YourEstimateDetailsActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.LocationDeleteRequest;
import com.triton.myvacala.requestpojo.LocationStatusChangeRequest;
import com.triton.myvacala.responsepojo.BookingHistoryResponse;
import com.triton.myvacala.responsepojo.LocationDeleteResponse;
import com.triton.myvacala.responsepojo.LocationListResponse;
import com.triton.myvacala.responsepojo.LocationStatusChangeResponse;
import com.triton.myvacala.utils.RestUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistoryListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "OrderHistoryListAdapter";
    private List<BookingHistoryResponse.DataBean> bookingHistoryResponseList  = null;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    BookingHistoryResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    Dialog dialog;

    public static String id = "";


    public OrderHistoryListAdapter(Context context,  List<BookingHistoryResponse.DataBean> bookingHistoryResponseList, RecyclerView inbox_list) {
        this.bookingHistoryResponseList = bookingHistoryResponseList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_orderhistory_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

            currentItem = bookingHistoryResponseList.get(position);
            Log.w(TAG,"Userissues-->"+currentItem.getUser_issues());
            holder.btn_orderstatus.setText(currentItem.getBooking_Status());
            holder.txt_ordervehicle_name.setText(currentItem.getVehicle_Name());
            holder.txt_order_bookingid.setText(currentItem.getBooking_id());
            holder.btn_orderfuel_type.setVisibility(View.GONE);
            Log.w(TAG,"currentItem.getLubricant_type() : "+bookingHistoryResponseList.get(position).getLubricant_type());



            if(currentItem.getLubricant_type_color() != null && !currentItem.getLubricant_type_color().isEmpty() && bookingHistoryResponseList.get(position).getLubricant_type() != null && !bookingHistoryResponseList.get(position).getLubricant_type().isEmpty()) {
                holder.btn_orderfuel_type.setVisibility(View.VISIBLE);
                holder.btn_orderfuel_type.setBackgroundColor(Color.parseColor(currentItem.getLubricant_type_color()));
                holder.btn_orderfuel_type.setBackgroundResource(R.drawable.tags_rounded_corners_withoutcorner);
                GradientDrawable gd = (GradientDrawable) holder.btn_orderfuel_type.getBackground();
                gd.setColor(Color.parseColor(currentItem.getLubricant_type_color()));
                gd.setCornerRadius(10);
                gd.setStroke(4, Color.WHITE);
                holder.btn_orderfuel_type.setText(bookingHistoryResponseList.get(position).getLubricant_type());
            }
            holder.txt_order_bookingstatus.setText(currentItem.getStatus_history_text().get(0).getTitle());
            holder.txt_order_bookingdateandtime.setText(currentItem.getStatus_history_text().get(0).getDate());



            if(currentItem.getServices() != null){
                holder.txt_servicename.setText(currentItem.getServices());


            }
          if(currentItem.getSubserivces() != null){
            holder.txt_sub_services_type.setText(currentItem.getSubserivces());


          }

          if(currentItem.getUser_issues() != null && !currentItem.getUser_issues().isEmpty()){
              holder.ll_userissues.setVisibility(View.VISIBLE);
              holder.txt_userissues.setText(currentItem.getUser_issues());
          }else{
              holder.ll_userissues.setVisibility(View.GONE);

          }

            holder.txt_orderservices_charge_amount.setText("\u20B9"+" "+String.valueOf(currentItem.getTotal_Amount()));
            holder.txt_track_order_status.setText(currentItem.getTrack_order_text());




            if (currentItem.getVehicle_Image() != null && !currentItem.getVehicle_Image().isEmpty()) {

                Glide.with(context)
                        .load(currentItem.getVehicle_Image())
                        .into(holder.cv_ordervehicle_image);

            }else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(holder.cv_ordervehicle_image);

            }





        holder.ll_trackorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, YourEstimateDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id",bookingHistoryResponseList.get(position).get_id());
                i.putExtra("finalbillpay",bookingHistoryResponseList.get(position).getFinal_bill_payed());
                i.putExtra("bookingstatus",bookingHistoryResponseList.get(position).getBooking_Status());
                i.putExtra("customerinvoicelist",bookingHistoryResponseList.get(position).getCustomer_Invoice());
                i.putExtra("vehicletype",bookingHistoryResponseList.get(position).getVehicle_Type());
                context.startActivity(i);

            }
        });





    }









    @Override
    public int getItemCount() {
        return bookingHistoryResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_ordervehicle_name,txt_order_bookingstatus,txt_order_bookingdateandtime,txt_orderservices_charge_amount,txt_track_order_status,txt_sub_services_type,txt_order_bookingid,txt_servicename,txt_userissues;
        public ImageView cv_ordervehicle_image;
        public LinearLayout ll_trackorder,ll_userissues;
        public Button btn_orderfuel_type, btn_orderstatus;




        public ViewHolderOne(View itemView) {
            super(itemView);
            btn_orderfuel_type = itemView.findViewById(R.id.btn_orderfuel_type);
            btn_orderstatus = itemView.findViewById(R.id.btn_orderstatus);
            txt_ordervehicle_name = itemView.findViewById(R.id.txt_ordervehicle_name);
            txt_order_bookingid = itemView.findViewById(R.id.txt_order_bookingid);
            txt_servicename = itemView.findViewById(R.id.txt_servicename);
            txt_order_bookingstatus = itemView.findViewById(R.id.txt_order_bookingstatus);
            txt_order_bookingdateandtime = itemView.findViewById(R.id.txt_order_bookingdateandtime);
            txt_orderservices_charge_amount = itemView.findViewById(R.id.txt_orderservices_charge_amount);
            txt_track_order_status = itemView.findViewById(R.id.txt_track_order_status);
            txt_sub_services_type = itemView.findViewById(R.id.txt_sub_services_type);
            ll_trackorder = itemView.findViewById(R.id.ll_trackorder);
            ll_userissues = itemView.findViewById(R.id.ll_userissues);
            txt_userissues = itemView.findViewById(R.id.txt_userissues);
            cv_ordervehicle_image = itemView.findViewById(R.id.cv_ordervehicle_image);

        }




    }












}
