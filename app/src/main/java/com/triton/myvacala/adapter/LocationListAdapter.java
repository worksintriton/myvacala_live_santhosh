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
import com.triton.myvacala.R;

import com.triton.myvacala.activities.EditMapsActivity;
import com.triton.myvacala.activities.EditMyAddressActivity;
import com.triton.myvacala.activities.LocationListActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.interfaces.LocationDeleteListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.LocationDeleteRequest;
import com.triton.myvacala.requestpojo.LocationStatusChangeRequest;
import com.triton.myvacala.responsepojo.LocationDeleteResponse;
import com.triton.myvacala.responsepojo.LocationListResponse;
import com.triton.myvacala.responsepojo.LocationStatusChangeResponse;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.triton.myvacala.utils.RestUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "LocationListAdapter";
    private List<LocationListResponse.DataBean> locationListResponseList  = null;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    LocationListResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;

    public static String id = "";
    private LocationDeleteListener locationDeleteListener;


    public LocationListAdapter(Context context, List<LocationListResponse.DataBean> locationListResponseList, RecyclerView inbox_list,LocationDeleteListener locationDeleteListener) {
        this.locationListResponseList = locationListResponseList;
        this.context = context;
        this.locationDeleteListener = locationDeleteListener;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_locationlist_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = locationListResponseList.get(position);
        for (int i = 0; i < locationListResponseList.size(); i++) {

            holder.txt_cityname.setText(currentItem.getLocation_type());
            holder.txt_address.setText(currentItem.getStreet());

            if(currentItem.getLocation_type() != null && !currentItem.getLocation_type().isEmpty()) {
                Log.w(TAG,"LocationType : "+currentItem.getLocation_type());
                String first = currentItem.getLocation_type().substring(0, 1);
                holder.txt_name.setText(first);
            }

            if(currentItem.getStatus() != null){
                if(currentItem.getStatus().equalsIgnoreCase("Default")){
                    holder.iv_default_location.setVisibility(View.VISIBLE);
                }else{
                    holder.iv_default_location.setVisibility(View.GONE);
                }

            }




        }
        holder.txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = locationListResponseList.get(position).get_id();
                String status = locationListResponseList.get(position).getStatus();
                locationDeleteListener.locationDeleteListener(status,id);

              /*  if(!status.trim().equalsIgnoreCase("Default".trim())){
                    showStatusAlert(view,id);
                }else{
                    Toasty.warning(context, "Default location cannot be deleted.", Toast.LENGTH_SHORT, true).show();


                }*/


            }
        });
        holder.txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(context, EditMyAddressActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                id = locationListResponseList.get(position).get_id();
                i.putExtra("id",locationListResponseList.get(position).get_id());
                i.putExtra("cityname",locationListResponseList.get(position).getCity());
                i.putExtra("state",locationListResponseList.get(position).getState());
                i.putExtra("country",locationListResponseList.get(position).getCountry());
                i.putExtra("street",locationListResponseList.get(position).getStreet());
                i.putExtra("pincode",locationListResponseList.get(position).getPincode());
                i.putExtra("flatno",locationListResponseList.get(position).getFlat_No());
                i.putExtra("nearbylandmark",locationListResponseList.get(position).getNearBy_LandMark());
                i.putExtra("nickname",locationListResponseList.get(position).getLocation_NickName());
                i.putExtra("locationtype",locationListResponseList.get(position).getLocation_type());
                Bundle b = new Bundle();
                b.putDouble("lat", locationListResponseList.get(position).getLat());
                b.putDouble("lon", locationListResponseList.get(position).getLongX());
                i.putExtras(b);
                Log.w(TAG,"cityname-->"+locationListResponseList.get(position).getCity());
                Log.w(TAG,"lat : "+locationListResponseList.get(position).getLat()+"lon :"+locationListResponseList.get(position).getLongX()+"cityname :"+locationListResponseList.get(position).getCity());
                context.startActivity(i);




            }
        });

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = locationListResponseList.get(position).get_id();
                String customerid = locationListResponseList.get(position).getCustomer_id();
                showLocationStatusChangeAlert(view,id,customerid);
            }
        });




    }









    @Override
    public int getItemCount() {
        return locationListResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_name,txt_cityname,txt_address,txt_edit,txt_delete;
        public ImageView iv_default_location;
        public LinearLayout ll_root;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_Name_First_Letter);
            txt_cityname = itemView.findViewById(R.id.txt_cityname);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_edit = itemView.findViewById(R.id.txt_edit);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            ll_root = itemView.findViewById(R.id.ll_root);
            iv_default_location = itemView.findViewById(R.id.iv_default_location);
            iv_default_location.setVisibility(View.GONE);

        }




    }









    private void showStatusAlert(View view, final String locationid) {

        try {

            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.deletemsg);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    locationDeleteResponseCall(dialog,locationid);


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
    private void locationDeleteResponseCall(final ProgressDialog dialog, String locationid) {
        dialog.show();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationDeleteResponse> call = apiInterface.locationDeleteResponseCall(RestUtils.getContentType(),locationDeleteRequest(locationid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<LocationDeleteResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationDeleteResponse> call, @NotNull Response<LocationDeleteResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"LocationDeleteResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(context, "Address Removed Successfully", Toast.LENGTH_SHORT, true).show();
                        Intent i = new Intent(context, LocationListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<LocationDeleteResponse> call, @NotNull Throwable t) {
                dialog.dismiss();

                Log.w(TAG,"LocationDeleteResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private LocationDeleteRequest locationDeleteRequest(String locationid) {

        /*
          Location_id : 5f05d911f3090625a91f40c7
         */
        LocationDeleteRequest locationDeleteRequest = new LocationDeleteRequest();
        locationDeleteRequest.setLocation_id(locationid);
        Log.w(TAG,"locationDeleteRequest"+ "--->" + new Gson().toJson(locationDeleteRequest));
        return locationDeleteRequest;
    }



    private void showLocationStatusChangeAlert(View view,String locationid,String customerid) {

        try {

            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.locationstatuschange);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    locationStatusChangeResponseCall(dialog,locationid,customerid);


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
