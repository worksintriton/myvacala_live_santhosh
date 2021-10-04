package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;

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

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.CartActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.interfaces.SubServiceAddandRemoveListener;
import com.triton.myvacala.interfaces.SubServiceIdListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.RemovingCartRequest;
import com.triton.myvacala.responsepojo.GetCardDetailsResponse;
import com.triton.myvacala.responsepojo.RemovingCartResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartServiceListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "CartServiceListAdapter";
    private List<GetCardDetailsResponse.DataBean.ItemDetailsBean> itemDetailsBeanList = new ArrayList<>();
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    GetCardDetailsResponse.DataBean.ItemDetailsBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;

    public static String id = "";

   // private SubServiceIdListener subServiceIdListener;
    private SubServiceAddandRemoveListener subServiceAddandRemoveListener;


    public CartServiceListAdapter(Context context, List<GetCardDetailsResponse.DataBean.ItemDetailsBean> itemDetailsBeanList, RecyclerView inbox_list,CartActivity cartActivity) {
        this.itemDetailsBeanList = itemDetailsBeanList;
        this.context = context;
         this.subServiceAddandRemoveListener = (SubServiceAddandRemoveListener)cartActivity;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart_services_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = itemDetailsBeanList.get(position);
        for (int i = 0; i < itemDetailsBeanList.size(); i++) {

            holder.txt_cart_subservice_title.setText(currentItem.getSub_ser_Title());
            holder.txt_cart_subservice_originalamount.setText("\u20B9"+" "+String.valueOf(currentItem.getOriginal_Price()));
            holder.txt_cart_subservice_originalamount.setPaintFlags( holder.txt_cart_subservice_originalamount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_cart_subservice_discountamount.setText("\u20B9"+" "+String.valueOf(currentItem.getDiscount_Price()));

        }





        if(itemDetailsBeanList.get(position).getCart_count()>0){
            holder.btn_add_remove.setText("REMOVE");

        }else{
            holder.btn_add_remove.setText("ADD");
        }


        holder.ll_multipleadd.setVisibility(View.GONE);
        holder.btn_add_remove.setVisibility(View.GONE);
        if(itemDetailsBeanList.get(position).isCount_type()){
            holder.btn_add_remove.setVisibility(View.VISIBLE);
            holder.ll_multipleadd.setVisibility(View.GONE);

        }
        else{
            holder.btn_add_remove.setVisibility(View.GONE);
            holder.ll_multipleadd.setVisibility(View.VISIBLE);
            holder.txt_count_number.setText(String.valueOf(itemDetailsBeanList.get(position).getCart_count()));
        }

        holder.txt_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new ConnectionDetector(context).isNetworkAvailable(context))
                    subServiceAddandRemoveListener.onItemCheck("ADD",itemDetailsBeanList.get(position).get_id());

            }
        });
        if(itemDetailsBeanList.get(position).getCart_count() == 0) {
            holder.txt_decrease.setVisibility(View.INVISIBLE);
        }else{
            holder.txt_decrease.setVisibility(View.VISIBLE);

        }


        holder.txt_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetailsBeanList.get(position).getCart_count()>0) {
                    if (new ConnectionDetector(context).isNetworkAvailable(context))
                        subServiceAddandRemoveListener.onItemCheck("REMOVE", itemDetailsBeanList.get(position).get_id());
                }
            }
        });


        holder.btn_add_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(holder.btn_add_remove.getText().toString().equalsIgnoreCase("ADD")){
                    if (new ConnectionDetector(context).isNetworkAvailable(context))
                        //addingCartResponseCall(v,subServiceListResponseList.get(position).get_id(),customerid);
                        subServiceAddandRemoveListener.onItemCheck("ADD",itemDetailsBeanList.get(position).get_id());
                }
                else if(holder.btn_add_remove.getText().toString().equalsIgnoreCase("REMOVE")){
                    if (new ConnectionDetector(context).isNetworkAvailable(context))
                        subServiceAddandRemoveListener.onItemCheck("REMOVE",itemDetailsBeanList.get(position).get_id());

                    // removingCartResponseCall(v,subServiceListResponseList.get(position).get_id(),customerid);
                }
            }
        });









    }









    @Override
    public int getItemCount() {
        return itemDetailsBeanList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_cart_subservice_title,txt_cart_subservice_originalamount,txt_cart_subservice_discountamount;

        public LinearLayout ll_multipleadd;
        public Button btn_add_remove;

        public TextView txt_decrease,txt_count_number,txt_increase;









        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_cart_subservice_title = itemView.findViewById(R.id.txt_cart_subservice_title);
            txt_cart_subservice_originalamount = itemView.findViewById(R.id.txt_cart_subservice_originalamount);
            txt_cart_subservice_discountamount = itemView.findViewById(R.id.txt_cart_subservice_discountamount);
            txt_cart_subservice_discountamount = itemView.findViewById(R.id.txt_cart_subservice_discountamount);
            btn_add_remove = itemView.findViewById(R.id.btn_add_remove);
            ll_multipleadd = itemView.findViewById(R.id.ll_multipleadd);
            txt_decrease = itemView.findViewById(R.id.txt_decrease);
            txt_count_number = itemView.findViewById(R.id.txt_count_number);
            txt_increase = itemView.findViewById(R.id.txt_increase);
        }




    }




















}
