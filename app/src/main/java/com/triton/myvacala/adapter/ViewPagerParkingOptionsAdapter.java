package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.YourEstimateDetailsActivity;
import com.triton.myvacala.activities.parking.Parking_Options_Activity;
import com.triton.myvacala.interfaces.ParkingSelectlatlongListener;
import com.triton.myvacala.responsepojo.GetCustomerVehicleandLocationResponse;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.triton.myvacala.responsepojo.ParkingListResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ViewPagerParkingOptionsAdapter extends PagerAdapter {
    private final List<ParkingListResponse.DataBean> homeBannerResponseArrayList;
    private Context context;
    private LayoutInflater inflater;
    private View itemView;
    ParkingListResponse.DataBean currentItem;
    private String TAG = "ViewPagerParkingOptionsAdapter";
    private String selectedVehicleType;
    private int SlotCountNumbers = 0;

    private ParkingSelectlatlongListener parkingSelectlatlongListener;

    private ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;


    public ViewPagerParkingOptionsAdapter(Context context, List<ParkingListResponse.DataBean> homeBannerResponseArrayList, String selectedVehicleType, ArrayList<GetCustomerVehicleandLocationResponse.CustomerVehicleDataBean> customerVehicleDataBeanList, ParkingSelectlatlongListener parkingSelectlatlongListener){
        this.context = context;
        this.homeBannerResponseArrayList = homeBannerResponseArrayList;
        inflater = LayoutInflater.from(context);
        this.selectedVehicleType = selectedVehicleType;
        this.customerVehicleDataBeanList = customerVehicleDataBeanList;
        this.parkingSelectlatlongListener = parkingSelectlatlongListener;
    }
    @Override
    public int getCount() {
        return homeBannerResponseArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }


    @SuppressLint("SetTextI18n")
    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup view, int position) {
        View itemView = inflater.inflate(R.layout.adapter_parkingoptions_cardview, view, false);
        TextView txt_parking_name = itemView.findViewById(R.id.txt_parking_name);
        Button btn_parking_slots_count = itemView.findViewById(R.id.btn_parking_slots_count);
        TextView txt_address = itemView.findViewById(R.id.txt_address);
        TextView txt_distance_reachtime = itemView.findViewById(R.id.txt_distance_reachtime);
        TextView txt_parking_prices = itemView.findViewById(R.id.txt_parking_prices);
        LinearLayout ll_share = itemView.findViewById(R.id.ll_share);
        LinearLayout ll_root = itemView.findViewById(R.id.ll_root);
        Button btn_book = itemView.findViewById(R.id.btn_book);

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lat =  homeBannerResponseArrayList.get(position).getParking_details_lat();
                double lon =  homeBannerResponseArrayList.get(position).getParking_details_long();
                Log.w(TAG,"ll_root click : "+"lat : "+lat+"lon : "+lon);
                parkingSelectlatlongListener.parkingSelectlatlongListener(lat,lon);

            }
        });





        Log.w(TAG,"selectedVehicleType:"+selectedVehicleType);
        if(selectedVehicleType.equalsIgnoreCase("Four Wheeler")){
            if(homeBannerResponseArrayList.get(position).getParking_details_slots_count_Car() != 0){
                btn_parking_slots_count.setText(homeBannerResponseArrayList.get(position).getParking_details_slots_count_Car()+" Available");

            }else{
                btn_parking_slots_count.setText("Not Available");
            }
            SlotCountNumbers = homeBannerResponseArrayList.get(position).getParking_details_slots_count_Car();
        }else{
            if(homeBannerResponseArrayList.get(position).getParking_details_slots_count_Bike() != 0){
                btn_parking_slots_count.setText(homeBannerResponseArrayList.get(position).getParking_details_slots_count_Bike()+" Available");

            }else{
                btn_parking_slots_count.setText("Not Available");

            }
            SlotCountNumbers = homeBannerResponseArrayList.get(position).getParking_details_slots_count_Bike();


        }







            String parkingname = homeBannerResponseArrayList.get(position).getParking_details_name();
            Log.w(TAG,"parkingname-->"+parkingname);

            txt_parking_name.setText(parkingname);
            txt_address.setText(homeBannerResponseArrayList.get(position).getParking_details_address());
            txt_distance_reachtime.setText(homeBannerResponseArrayList.get(position).getParking_reach_time()+" / "+homeBannerResponseArrayList.get(position).getParking_distance());
            txt_parking_prices.setText("\u20B9 "+homeBannerResponseArrayList.get(position).getParking_prices()+"");

        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                // String shareBody = sharelink;
                String shareMessage= homeBannerResponseArrayList.get(position).getParking_details_maplink();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Vacala");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedVehicleType.equalsIgnoreCase("Four Wheeler")){
                    //btn_parking_slots_count.setText(homeBannerResponseArrayList.get(position).getParking_details_slots_count_Car()+" Available");
                    SlotCountNumbers = homeBannerResponseArrayList.get(position).getParking_details_slots_count_Car();
                }else{
                    //btn_parking_slots_count.setText(homeBannerResponseArrayList.get(position).getParking_details_slots_count_Bike()+" Available");
                    SlotCountNumbers = homeBannerResponseArrayList.get(position).getParking_details_slots_count_Bike();


                }

                if(SlotCountNumbers != 0) {
                    Intent i = new Intent(context, Parking_Options_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", homeBannerResponseArrayList.get(position).get_id());
                    i.putExtra("parkingname", homeBannerResponseArrayList.get(position).getParking_details_name());
                    i.putExtra("sharemaplink", homeBannerResponseArrayList.get(position).getParking_details_maplink());
                    i.putExtra("address", homeBannerResponseArrayList.get(position).getParking_details_address());
                    i.putExtra("parkingavlslotcountcar", homeBannerResponseArrayList.get(position).getParking_details_slots_count_Car());
                    i.putExtra("parkingavlslotcountbike", homeBannerResponseArrayList.get(position).getParking_details_slots_count_Bike());
                    i.putExtra("reachtime", homeBannerResponseArrayList.get(position).getParking_reach_time());
                    i.putExtra("parkingdistance", homeBannerResponseArrayList.get(position).getParking_distance());
                    i.putExtra("parkingprices", homeBannerResponseArrayList.get(position).getParking_prices());
                    i.putExtra("customerVehicleDataBeanList", customerVehicleDataBeanList);
                    i.putExtra("selectedVehicleType", selectedVehicleType);
                    context.startActivity(i);
                }else{
                    Toasty.error(context, "No slots available", Toast.LENGTH_SHORT, true).show();

                }

            }
        });





        view.addView(itemView);

        return itemView;

    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
