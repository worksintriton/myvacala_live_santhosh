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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.YourEstimateDetailsActivity;
import com.triton.myvacala.activities.parking.CheckedInActivity;
import com.triton.myvacala.activities.parking.CheckedOutActivity;
import com.triton.myvacala.activities.parking.ParkingConfirmActivity;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.responsepojo.BookingHistoryResponse;
import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class BookingHistoryListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "BookingHistoryListAdapter";
    private List<ParkingBookingGetListResponse.DataBean> bookingHistoryResponseList  = null;
    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    ParkingBookingGetListResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;
    Dialog dialog;
    public static String id = "";


    public BookingHistoryListAdapter(Context context, List<ParkingBookingGetListResponse.DataBean> bookingHistoryResponseList, RecyclerView inbox_list) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bookinghistory_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = bookingHistoryResponseList.get(position);
        for (int i = 0; i < bookingHistoryResponseList.size(); i++) {

            if(currentItem.getBooking_status() != null){
                holder.btn_bookinghistory_status.setText(currentItem.getBooking_status());

            }
            if(currentItem.getParking_shop_name() != null){
                holder.txt_bookinghistory_buildingname.setText(currentItem.getParking_shop_name());

            }
            if(currentItem.getParking_shop_address() != null){
                holder.txt_bookinghistory_address.setText(currentItem.getParking_shop_address());

            }
            if(currentItem.getBooking_id() != null){
                holder.txt_bookinghistory_bookingid.setText(currentItem.getBooking_id());

            }

            if(currentItem.getAmount() != null && !currentItem.getAmount().isEmpty()){
                holder.txt_amountpaid.setText("\u20B9 "+currentItem.getTotal_amount());

            }

            if(currentItem.getCouponcode_amount() != null && !currentItem.getCouponcode_amount().isEmpty()){
                holder.lbl_txt_discount_amount.setVisibility(View.VISIBLE);
                holder.txt_discount_amount.setVisibility(View.VISIBLE);
                holder.txt_discount_amount.setText("\u20B9 "+currentItem.getCouponcode_amount());
            }else{
                holder.lbl_txt_discount_amount.setVisibility(View.GONE);
                holder.txt_discount_amount.setVisibility(View.GONE);
            }


            @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM");

            Date datestart = null, dateend = null;
            String formattedStartDate = null,formattedEndDate = null;

            if(currentItem.getBooking_start_date() != null && currentItem.getBooking_end_date() != null){

                try {
                    datestart = inputFormat.parse(currentItem.getBooking_start_date());
                    dateend = inputFormat.parse(currentItem.getBooking_end_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (datestart != null) {
                    formattedStartDate = outputFormat.format(datestart);
                }
                if (dateend != null) {
                     formattedEndDate = outputFormat.format(dateend);
                }

            }
            String date,hrs;
            if(currentItem.getDuration_date() != null){
                date = currentItem.getDuration_date();
            }else{
                date = "0";
            }
            if(currentItem.getTotal_hrs() != null){
                hrs = currentItem.getTotal_hrs();
            }else{
                hrs = "0";
            }

            String bookeddateandtime = formattedStartDate+", "+currentItem.getBooking_start_time()+" to "+formattedEndDate+", "+currentItem.getBooking_end_time()+"("+date+" Day "+hrs+" Hour"+")";
            holder.txt_bookinghistory_bookingdateandtime.setText(currentItem.getDuration_date());




            String Vehicleimg = null,VehicleName = null,VehicleNo = null;
            try{
                for (int j = 0; j < currentItem.getVehicle_details().size(); j++) {
                    String vehicletype = currentItem.getVehicle_details().get(j).getVehicletype_Name();
                    String id = currentItem.getVehicle_details().get(j).getVehicletype_id();
                    Log.w(TAG, "vehicletype :" + vehicletype + " " + "id :" + id);

                    Vehicleimg = currentItem.getVehicle_details().get(j).getVehicle_Image();
                    VehicleName = currentItem.getVehicle_details().get(j).getVehicle_Name();
                    VehicleNo = currentItem.getVehicle_details().get(j).getVehicle_No();


                }

            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }

            if (Vehicleimg != null ){
                Glide.with(context)
                        .load(Vehicleimg)
                        .into(holder.cv_bookinghistory_vehicleimage);

            }
            else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(holder.cv_bookinghistory_vehicleimage);

            }
            if(VehicleName != null){
                holder.txt_bookinghistory_vehiclename.setText(VehicleName);
            }
            if(VehicleNo != null){
                holder.txt_bookinghistory_vehiclenumber.setText(VehicleNo);
            }
            if(bookingHistoryResponseList.get(position).getBooking_status() != null){
                String bookingStatus = bookingHistoryResponseList.get(position).getBooking_status();
                if(bookingStatus.equalsIgnoreCase("Check-in")){
                    holder.btn_bookinghistory_status.setBackgroundColor(Color.parseColor("#006400"));
                    holder.btn_bookinghistory_status.setBackgroundResource(R.drawable.tags_rounded_corners_withoutcorner);
                    GradientDrawable gd = (GradientDrawable) holder.btn_bookinghistory_status.getBackground();
                    gd.setColor(Color.parseColor("#006400"));
                    gd.setCornerRadius(10);

                }
                if(bookingStatus.equalsIgnoreCase("Check-out")){
                    holder.btn_bookinghistory_status.setBackgroundColor(Color.RED);
                    holder.btn_bookinghistory_status.setBackgroundResource(R.drawable.tags_rounded_corners_withoutcorner);
                    GradientDrawable gd = (GradientDrawable) holder.btn_bookinghistory_status.getBackground();
                    gd.setColor(Color.RED);
                    gd.setCornerRadius(10);

                }
                if(bookingStatus.equalsIgnoreCase("Upcoming")){
                        holder.btn_bookinghistory_status.setBackgroundColor(Color.parseColor("#FFA500"));
                        holder.btn_bookinghistory_status.setBackgroundResource(R.drawable.tags_rounded_corners_withoutcorner);
                        GradientDrawable gd = (GradientDrawable) holder.btn_bookinghistory_status.getBackground();
                        gd.setColor(Color.parseColor("#FFA500"));
                        gd.setCornerRadius(10);


                    }
                if(bookingStatus.equalsIgnoreCase("Closed")){
                    holder.btn_bookinghistory_status.setBackgroundColor(Color.parseColor("#8B0000"));
                    holder.btn_bookinghistory_status.setBackgroundResource(R.drawable.tags_rounded_corners_withoutcorner);
                    GradientDrawable gd = (GradientDrawable) holder.btn_bookinghistory_status.getBackground();
                    gd.setColor(Color.parseColor("#8B0000"));
                    gd.setCornerRadius(10);


                }

                }
            }




            /*holder.btn_orderfuel_type.setText(currentItem.getLubricant_type());

            holder.txt_order_bookingstatus.setText(currentItem.getStatus_history_text().get(0).getTitle());
            holder.txt_order_bookingdateandtime.setText(currentItem.getStatus_history_text().get(0).getDate());



           // holder.txt_sub_services_type.setText(currentItem.getSubserivces());
            holder.txt_orderservices_charge_amount.setText("\u20B9"+" "+String.valueOf(currentItem.getTotal_Amount()));
            holder.txt_track_order_status.setText(currentItem.getTrack_order_text());




*/



        holder.cardview_bookinghistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookingHistoryResponseList.get(position).getBooking_status() != null){
                    String bookingStatus = bookingHistoryResponseList.get(position).getBooking_status();
                    if(bookingStatus.equalsIgnoreCase("Check-in")){
                        Intent intent = new Intent(context, CheckedInActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("buildingname",bookingHistoryResponseList.get(position).getParking_shop_name());
                        intent.putExtra("address",bookingHistoryResponseList.get(position).getParking_shop_address());
                        intent.putExtra("sharelink",bookingHistoryResponseList.get(position).getParking_shop_address_link());
                        intent.putExtra("amount",bookingHistoryResponseList.get(position).getTotal_amount()+"");
                        intent.putExtra("bookingid",bookingHistoryResponseList.get(position).getBooking_id());
                        intent.putExtra("slotdetails",bookingHistoryResponseList.get(position).getSlot_details());
                        intent.putExtra("startdate",bookingHistoryResponseList.get(position).getBooking_start_date());
                        intent.putExtra("starttime",bookingHistoryResponseList.get(position).getBooking_start_time());
                        intent.putExtra("enddate",bookingHistoryResponseList.get(position).getBooking_end_date());
                        intent.putExtra("endtime",bookingHistoryResponseList.get(position).getBooking_end_time());
                        intent.putExtra("totalhours",bookingHistoryResponseList.get(position).getTotal_hrs());
                        intent.putExtra("vehicledetailslist",bookingHistoryResponseList.get(position).getVehicle_details());
                        intent.putExtra("reachtime",bookingHistoryResponseList.get(position).getDistance());
                        intent.putExtra("distance",bookingHistoryResponseList.get(position).getKms());
                        intent.putExtra("id",bookingHistoryResponseList.get(position).get_id());
                        intent.putExtra("parkingdetailsid",bookingHistoryResponseList.get(position).getParkingdetails_id());
                        intent.putExtra("bookingstatus",bookingHistoryResponseList.get(position).getBooking_status());
                        intent.putExtra("fromactivity","BookingHistoryListAdapter");
                        Log.w(TAG,"Overalltotalhrs"+bookingHistoryResponseList.get(position).getTotal_hrs());

                        context.startActivity(intent);

                    }
                    if(bookingStatus.equalsIgnoreCase("Check-out")){
                        Intent intent = new Intent(context, CheckedOutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("buildingname",bookingHistoryResponseList.get(position).getParking_shop_name());
                        intent.putExtra("address",bookingHistoryResponseList.get(position).getParking_shop_address());
                        intent.putExtra("sharelink",bookingHistoryResponseList.get(position).getParking_shop_address_link());
                        intent.putExtra("amount",bookingHistoryResponseList.get(position).getTotal_amount()+"");
                        intent.putExtra("bookingid",bookingHistoryResponseList.get(position).getBooking_id());
                        intent.putExtra("slotdetails",bookingHistoryResponseList.get(position).getSlot_details());
                        intent.putExtra("startdate",bookingHistoryResponseList.get(position).getBooking_start_date());
                        intent.putExtra("starttime",bookingHistoryResponseList.get(position).getBooking_start_time());
                        intent.putExtra("enddate",bookingHistoryResponseList.get(position).getBooking_end_date());
                        intent.putExtra("endtime",bookingHistoryResponseList.get(position).getBooking_end_time());
                        intent.putExtra("totalhours",bookingHistoryResponseList.get(position).getTotal_hrs());
                        intent.putExtra("vehicledetailslist",bookingHistoryResponseList.get(position).getVehicle_details());
                        intent.putExtra("reachtime",bookingHistoryResponseList.get(position).getDistance());
                        intent.putExtra("distance",bookingHistoryResponseList.get(position).getKms());
                        intent.putExtra("id",bookingHistoryResponseList.get(position).get_id());
                        intent.putExtra("parkingdetailsid",bookingHistoryResponseList.get(position).getParkingdetails_id());
                        intent.putExtra("bookingstatus",bookingHistoryResponseList.get(position).getBooking_status());
                        intent.putExtra("fromactivity","BookingHistoryListAdapter");
                        context.startActivity(intent);
                    }
                    if(bookingStatus.equalsIgnoreCase("Upcoming")){
                        Intent intent = new Intent(context, ParkingConfirmActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("buildingname",bookingHistoryResponseList.get(position).getParking_shop_name());
                        intent.putExtra("address",bookingHistoryResponseList.get(position).getParking_shop_address());
                        intent.putExtra("sharelink",bookingHistoryResponseList.get(position).getParking_shop_address_link());
                        intent.putExtra("amount",bookingHistoryResponseList.get(position).getTotal_amount()+"");
                        intent.putExtra("bookingid",bookingHistoryResponseList.get(position).getBooking_id());
                        intent.putExtra("slotdetails",bookingHistoryResponseList.get(position).getSlot_details());
                        intent.putExtra("startdate",bookingHistoryResponseList.get(position).getBooking_start_date());
                        intent.putExtra("starttime",bookingHistoryResponseList.get(position).getBooking_start_time());
                        intent.putExtra("enddate",bookingHistoryResponseList.get(position).getBooking_end_date());
                        intent.putExtra("endtime",bookingHistoryResponseList.get(position).getBooking_end_time());
                        intent.putExtra("totalhours",bookingHistoryResponseList.get(position).getTotal_hrs());
                        intent.putExtra("vehicledetailslist",bookingHistoryResponseList.get(position).getVehicle_details());
                        intent.putExtra("reachtime",bookingHistoryResponseList.get(position).getDistance());
                        intent.putExtra("distance",bookingHistoryResponseList.get(position).getKms());
                        intent.putExtra("id",bookingHistoryResponseList.get(position).get_id());
                        intent.putExtra("parkingdetailsid",bookingHistoryResponseList.get(position).getParkingdetails_id());
                        intent.putExtra("bookingstatus",bookingHistoryResponseList.get(position).getBooking_status());
                        intent.putExtra("couponcodeamount",bookingHistoryResponseList.get(position).getCouponcode_amount());

                        intent.putExtra("fromactivity","BookingHistoryListAdapter");
                        context.startActivity(intent);
                        Log.w(TAG,"parkingdetailsid--->"+bookingHistoryResponseList.get(position).getParkingdetails_id()+" amount "+bookingHistoryResponseList.get(position).getTotal_amount());
                    }

                }
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

        public TextView txt_bookinghistory_buildingname,txt_bookinghistory_address,txt_bookinghistory_bookingdateandtime,txt_bookinghistory_bookingid,txt_bookinghistory_vehiclename,txt_bookinghistory_vehiclenumber,txt_amountpaid,lbl_txt_discount_amount,txt_discount_amount;
        public CircleImageView cv_bookinghistory_vehicleimage;
        public Button  btn_bookinghistory_status;
        public CardView cardview_bookinghistory;




        public ViewHolderOne(View itemView) {
            super(itemView);
            btn_bookinghistory_status = itemView.findViewById(R.id.btn_bookinghistory_status);
            txt_bookinghistory_buildingname = itemView.findViewById(R.id.txt_bookinghistory_buildingname);
            txt_bookinghistory_address = itemView.findViewById(R.id.txt_bookinghistory_address);
            txt_bookinghistory_bookingdateandtime = itemView.findViewById(R.id.txt_bookinghistory_bookingdateandtime);
            txt_bookinghistory_bookingid = itemView.findViewById(R.id.txt_bookinghistory_bookingid);
            txt_bookinghistory_vehiclename = itemView.findViewById(R.id.txt_bookinghistory_vehiclename);
            txt_bookinghistory_vehiclenumber = itemView.findViewById(R.id.txt_bookinghistory_vehiclenumber);

            cv_bookinghistory_vehicleimage = itemView.findViewById(R.id.cv_bookinghistory_vehicleimage);
            cardview_bookinghistory = itemView.findViewById(R.id.cardview_bookinghistory);
            txt_amountpaid = itemView.findViewById(R.id.txt_amountpaid);
            lbl_txt_discount_amount = itemView.findViewById(R.id.lbl_txt_discount_amount);
            txt_discount_amount = itemView.findViewById(R.id.txt_discount_amount);

        }




    }












}
