package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.CartActivity;
import com.triton.myvacala.activities.ChooseYourAddressActivity;
import com.triton.myvacala.activities.EditMapsActivity;
import com.triton.myvacala.activities.LocationListActivity;
import com.triton.myvacala.activities.SubServiceDetailsActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.interfaces.LocationIdListener;
import com.triton.myvacala.interfaces.SubServiceAddandRemoveListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.LocationDeleteRequest;
import com.triton.myvacala.requestpojo.LocationStatusChangeRequest;
import com.triton.myvacala.responsepojo.GetCardDetailsResponse;
import com.triton.myvacala.responsepojo.LocationDeleteResponse;
import com.triton.myvacala.responsepojo.LocationListResponse;
import com.triton.myvacala.responsepojo.LocationStatusChangeResponse;
import com.triton.myvacala.utils.RestUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseYourAddressListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "ChooseYourAddressListAdapter";
    private ArrayList<GetCardDetailsResponse.LocationAvailableBean> locationAvailableBeanArrayList;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    GetCardDetailsResponse.LocationAvailableBean currentItem;

    Dialog dialog;

    private String fromactivity;

    private String masterservicename,masterserviceid,serviceid,servicename,selectedVehicleId,BookingDate,BookingTime,bookingdateandtime;









    public ChooseYourAddressListAdapter(Context context, ArrayList<GetCardDetailsResponse.LocationAvailableBean> locationAvailableBeanArrayList, RecyclerView inbox_list, String fromactivity, String masterservicename, String masterserviceid, String serviceid, String servicename, String selectedVehicleId, String bookingDate, String bookingTime,String bookingdateandtime) {
        this.locationAvailableBeanArrayList = locationAvailableBeanArrayList;
        this.context = context;
        this.fromactivity = fromactivity;
        this.masterservicename = masterservicename;
        this.masterserviceid = masterserviceid;
        this.serviceid = serviceid;
        this.servicename = servicename;
        this.selectedVehicleId = selectedVehicleId;
        this.BookingDate = bookingDate;
        this.BookingTime = bookingTime;
        this.bookingdateandtime = bookingdateandtime;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chooseyouraddresslist_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = locationAvailableBeanArrayList.get(position);
        for (int i = 0; i < locationAvailableBeanArrayList.size(); i++) {

            holder.txt_cityname.setText(currentItem.getLocation_type());
            holder.txt_address.setText(currentItem.getStreet()+","+" "+currentItem.getCity()+","+" "+currentItem.getState()+","+" "+currentItem.getPincode());

            String first = currentItem.getLocation_type().substring(0, 1);
            holder.txt_name.setText(first);

            if(currentItem.getStatus() != null){
                if(currentItem.getStatus().equalsIgnoreCase("Default")){
                    holder.iv_default_location.setVisibility(View.VISIBLE);
                }else{
                    holder.iv_default_location.setVisibility(View.GONE);
                }

            }




        }


        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = locationAvailableBeanArrayList.get(position).get_id();
                String customerid = locationAvailableBeanArrayList.get(position).getCustomer_id();
                showLocationStatusChangeAlert(view,id,position);

            }
        });




    }









    @Override
    public int getItemCount() {
        return locationAvailableBeanArrayList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_name,txt_cityname,txt_address;
        public ImageView iv_default_location;
        public LinearLayout ll_root;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_Name_First_Letter);
            txt_cityname = itemView.findViewById(R.id.txt_cityname);
            txt_address = itemView.findViewById(R.id.txt_address);

            ll_root = itemView.findViewById(R.id.ll_root);
            iv_default_location = itemView.findViewById(R.id.iv_default_location);
            iv_default_location.setVisibility(View.GONE);

        }




    }












    private void showLocationStatusChangeAlert(View view,String locationid,int position) {

        try {

            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.changethisaddress);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(locationid != null && !locationid.isEmpty()) {
                            Intent intent = new Intent(context, CartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("locationid", locationid);
                            intent.putExtra("masterservicename",masterservicename);
                            intent.putExtra("masterserviceid",masterserviceid);
                            intent.putExtra("serviceid",serviceid);
                            intent.putExtra("servicename",servicename);
                            intent.putExtra("selectedVehicleId",selectedVehicleId);
                            intent.putExtra("BookingDate",BookingDate);
                            intent.putExtra("BookingTime",BookingTime);
                            intent.putExtra("bookingdateandtime",bookingdateandtime);
                            context.startActivity(intent);

                    }
                    dialog.dismiss();
                   /* final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    locationStatusChangeResponseCall(dialog,locationid,customerid);*/


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void locationStatusChangeResponseCall(final ProgressDialog dialog, String locationid,String customerid) {
        dialog.show();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationStatusChangeResponse> call = apiInterface.locationStatusChangeResponseCall(RestUtils.getContentType(),locationStatusChangeRequest(locationid,customerid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<LocationStatusChangeResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationStatusChangeResponse> call, @NotNull Response<LocationStatusChangeResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"LocationStatusChangeResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(context, "Default Location Changed Successfully", Toast.LENGTH_SHORT, true).show();
                        Intent i = new Intent(context, LocationListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<LocationStatusChangeResponse> call, @NotNull Throwable t) {
                dialog.dismiss();

                Log.w(TAG,"LocationStatusChangeResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private LocationStatusChangeRequest locationStatusChangeRequest(String locationid,String customerid) {

        /*
         * Location_id : 5f0c47d4b726662ae3eee135
         * Customer_id : 5f0c146edb97a179bb713ed3
         */
        LocationStatusChangeRequest locationStatusChangeRequest = new LocationStatusChangeRequest();
        locationStatusChangeRequest.setLocation_id(locationid);
        locationStatusChangeRequest.setCustomer_id(customerid);
        Log.w(TAG,"locationStatusChangeRequest"+ "--->" + new Gson().toJson(locationStatusChangeRequest));
        return locationStatusChangeRequest;
    }






}
