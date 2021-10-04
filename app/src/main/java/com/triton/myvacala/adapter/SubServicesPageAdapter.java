package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.SubServiceDetailsActivity;
import com.triton.myvacala.activities.SubServicesActivity;

import com.triton.myvacala.interfaces.SubServiceAddandRemoveListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;

import com.triton.myvacala.responsepojo.MainServiceGetListResponse;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;

import com.triton.myvacala.responsepojo.SubServiceListResponse;

import com.triton.myvacala.utils.ConnectionDetector;


import java.util.ArrayList;

import java.util.List;




public class SubServicesPageAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "SubServicesPageAdapter";
    private List<SubServiceListResponse.DataBean> subServiceListResponseList;
    private List<String> stringArrayList = new ArrayList<>();
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    SubServiceListResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;

    String vehicletypeid,serviceid;
    String city = "",street = "";
    String vehicleImage,vehicleName, vehicleModelName,fuelType;


    String subservice;



    ArrayList<SubServiceListResponse.CustomerVehicleDataBean> customerVehicleDataBeanList = null;

    private SubServiceAddandRemoveListener subServiceAddandRemoveListener;


    String servicename;
    String masterservicename;

    String vehicletypename;
    String twowheelervehicleid = null,fourwheelervehicleid = null,masterserviceid = null;
    String selectedVehicleType, selectedVehicleId;


    public SubServicesPageAdapter(Context context, List<SubServiceListResponse.DataBean> subServiceListResponseList, List<String> stringArrayList, RecyclerView inbox_list,
                                  String vehicletypeid, String serviceid, String city, String street, String vehicleImage,
                                  String vehicleName, String vehicleModelName, String fuelType,
                                  ArrayList<SubServiceListResponse.CustomerVehicleDataBean> customerVehicleDataBeanList,
                                  SubServicesActivity subServicesActivity, String masterservicename, String vehicletypename, String masterserviceid, String twowheelervehicleid, String fourwheelervehicleid,
                                  String selectedVehicleType, String selectedVehicleId) {
        this.subServiceListResponseList = subServiceListResponseList;
        this.context = context;
        this.stringArrayList = stringArrayList;
        this.vehicletypeid =vehicletypeid;
        this.serviceid = serviceid;
        this.city = city;
        this.street = street;
        this.vehicleImage = vehicleImage;
        this.vehicleName = vehicleName;
        this.vehicleModelName = vehicleModelName;
        this.fuelType = fuelType;
        this.customerVehicleDataBeanList = customerVehicleDataBeanList;
        this.subServiceAddandRemoveListener = (SubServiceAddandRemoveListener)subServicesActivity;
        this.masterservicename = masterservicename;
        this.vehicletypename = vehicletypename;
        this.masterservicename = masterservicename;
        this.masterserviceid = masterserviceid;
        this.twowheelervehicleid = twowheelervehicleid;
        this.fourwheelervehicleid = fourwheelervehicleid;
        this.selectedVehicleType = selectedVehicleType;
        this.selectedVehicleId = selectedVehicleId;



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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subservicespage_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {



          currentItem = subServiceListResponseList.get(position);

          if(subServiceListResponseList.size()>0){


                  stringArrayList = subServiceListResponseList.get(position).getSub_ser_Spec1();
                   Log.w(TAG,"stringArrayList : "+new Gson().toJson(stringArrayList));
                  if(stringArrayList.size()>0){

                      for(int j =0;j<stringArrayList.size();j++){

                          String name = stringArrayList.get(j);
                          Log.w(TAG,"name--->"+name);
                          holder.txt_subservice_specification.append("\u2022"+" "+name +"\n");
                      }

                  }

          }






           subservice = currentItem.getSub_ser_Title();

            holder.txt_subservices.setText(currentItem.getSub_ser_Title());
            holder.txt_subservices_originalamount.setText("\u20B9"+" "+String.valueOf(currentItem.getOriginal_Price()));
            holder.txt_subservices_originalamount.setPaintFlags( holder.txt_subservices_originalamount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.txt_subservices_discountamount.setText("\u20B9"+" "+String.valueOf(currentItem.getDiscount_Price()));


            Log.w(TAG,"Size--->"+stringArrayList.size());

            if(currentItem.getCart_count()>0){
                holder.btn_add.setText("REMOVE");

            }else{
                holder.btn_add.setText("ADD");
            }


             holder.ll_multipleadd.setVisibility(View.GONE);
             holder.btn_add.setVisibility(View.GONE);
             if(subServiceListResponseList.get(position).isCount_type()){
                     holder.btn_add.setVisibility(View.VISIBLE);
                     holder.ll_multipleadd.setVisibility(View.GONE);

                 }
             else{
                     holder.btn_add.setVisibility(View.GONE);
                     holder.ll_multipleadd.setVisibility(View.VISIBLE);
                     holder.txt_count_number.setText(String.valueOf(subServiceListResponseList.get(position).getCart_count()));
                 }

             holder.txt_increase.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (new ConnectionDetector(context).isNetworkAvailable(context))
                         subServiceAddandRemoveListener.onItemCheck("ADD",subServiceListResponseList.get(position).get_id());

                 }
             });
        if(subServiceListResponseList.get(position).getCart_count() == 0) {
            holder.txt_decrease.setVisibility(View.INVISIBLE);
        }else{
            holder.txt_decrease.setVisibility(View.VISIBLE);

        }


            holder.txt_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(subServiceListResponseList.get(position).getCart_count()>0) {
                        if (new ConnectionDetector(context).isNetworkAvailable(context))
                            subServiceAddandRemoveListener.onItemCheck("REMOVE", subServiceListResponseList.get(position).get_id());
                    }
                }
            });


             holder.btn_add.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {



                     if(holder.btn_add.getText().toString().equalsIgnoreCase("ADD")){
                         if (new ConnectionDetector(context).isNetworkAvailable(context))
                             //addingCartResponseCall(v,subServiceListResponseList.get(position).get_id(),customerid);
                         subServiceAddandRemoveListener.onItemCheck("ADD",subServiceListResponseList.get(position).get_id());
                     }
                     else if(holder.btn_add.getText().toString().equalsIgnoreCase("REMOVE")){
                         if (new ConnectionDetector(context).isNetworkAvailable(context))
                             subServiceAddandRemoveListener.onItemCheck("REMOVE",subServiceListResponseList.get(position).get_id());

                         // removingCartResponseCall(v,subServiceListResponseList.get(position).get_id(),customerid);
                     }
                 }
             });







        if (currentItem.getSub_ser_image() != null && !currentItem.getSub_ser_image().isEmpty()) {

            Glide.with(context)
                    .load(currentItem.getSub_ser_image())
                    //.load(R.drawable.logo)
                    .into(holder.iv_subservice_image);

        }
        else{
            Glide.with(context)
                    .load(R.drawable.logo)
                    .into(holder.iv_subservice_image);

        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, SubServiceDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("itemlist", subServiceListResponseList.get(position).getItemList());
                intent.putExtra("subservicetitle",subServiceListResponseList.get(position).getSub_ser_Title());
                intent.putExtra("subserviceimage",subServiceListResponseList.get(position).getSub_ser_image());
                intent.putExtra("subservicedisplayimage",subServiceListResponseList.get(position).getSub_ser_display_img());
                intent.putExtra("discountprice",String.valueOf(subServiceListResponseList.get(position).getDiscount_Price()));
                intent.putExtra("subservicepagedetid",subServiceListResponseList.get(position).get_id());
                intent.putExtra("subservicepagedetserviceid",subServiceListResponseList.get(position).getService_id());
                intent.putExtra("cartstatus",subServiceListResponseList.get(position).isCart_Status());
                intent.putExtra("counttype",subServiceListResponseList.get(position).isCount_type());
                intent.putExtra("cartcount",subServiceListResponseList.get(position).getCart_count());
                intent.putExtra("vehicletypeid",vehicletypeid);
                intent.putExtra("serviceid",serviceid);
                intent.putExtra("city",city);
                intent.putExtra("street",street);
                intent.putExtra("vehicleImage", vehicleImage);
                intent.putExtra("vehicleName", vehicleName);
                intent.putExtra("vehicleModelName", vehicleModelName);
                intent.putExtra("fuelType", fuelType);
                intent.putExtra("customervehicledatabeanlist", customerVehicleDataBeanList);
                intent.putExtra("servicename", servicename);
                intent.putExtra("masterservicename", masterservicename);
                intent.putExtra("vehicletypename", vehicletypename);
                intent.putExtra("twowheelervehicleid",twowheelervehicleid);
                intent.putExtra("fourwheelervehicleid",fourwheelervehicleid);
                intent.putExtra("masterserviceid",masterserviceid);
                intent.putExtra("selectedVehicleType",selectedVehicleType);
                intent.putExtra("selectedVehicleId",selectedVehicleId);
                context.startActivity(intent);
                }




        });










    }












    @Override
    public int getItemCount() {
        return subServiceListResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_subservices,txt_subservices_originalamount,txt_subservices_discountamount,txt_subservice_specification,txt_decrease,txt_count_number,txt_increase;
        public LinearLayout ll_root,ll_multipleadd;
        public ImageView iv_subservice_image;
        public RecyclerView rv_specifications;
        public Button btn_add;



        public ViewHolderOne(View itemView) {
            super(itemView);

            iv_subservice_image = itemView.findViewById(R.id.iv_subservice_image);
            txt_subservices = itemView.findViewById(R.id.txt_subservices);
            txt_subservices_originalamount = itemView.findViewById(R.id.txt_subservices_originalamount);
            txt_subservices_discountamount = itemView.findViewById(R.id.txt_subservices_discountamount);
            txt_subservice_specification = itemView.findViewById(R.id.txt_subservice_specification);
            rv_specifications = itemView.findViewById(R.id.rv_specifications);
            btn_add = itemView.findViewById(R.id.btn_add);
            ll_root = itemView.findViewById(R.id.ll_root);
            ll_multipleadd = itemView.findViewById(R.id.ll_multipleadd);
            txt_decrease = itemView.findViewById(R.id.txt_decrease);
            txt_count_number = itemView.findViewById(R.id.txt_count_number);
            txt_increase = itemView.findViewById(R.id.txt_increase);





        }




    }








}
