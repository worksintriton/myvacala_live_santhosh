package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.DashboardActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.VehicleFuelTypeListener;
import com.triton.myvacala.interfaces.VehicleModeTypeListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.AddVehicleRequest;
import com.triton.myvacala.responsepojo.AddVehicleResponse;
import com.triton.myvacala.responsepojo.PopularVehicleListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularVehicleListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  String TAG = "PopularVehicleListAdapter";
    private List<PopularVehicleListResponse.DataBean> popularVehicleListResponseList;


    List<PopularVehicleListResponse.DataBean.VehicleDetailsBean.FuelTypeBean> fuelTypeBeanList = new ArrayList<>();
    List<PopularVehicleListResponse.DataBean.VehicleDetailsBean.VehicleModelBean> vehicleModelBeanList = new ArrayList<>();


    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    PopularVehicleListResponse.DataBean currentItem;


    Dialog dialog;


    RecyclerView rv_fueltype,rv_vehiclemodeltype;
    String fuelid,fuelname,fuelbackgroundcolor;
    String modelid,modelname;
    String mfgyear,vehicleno;
    String vehicleid ="",vehiclename = "",vehicleimage = "";




    SessionManager session;
    String customerid = "";

    private SharedPreferences preferences;

    private String _id;
    private String Vehicle_Type_Name;
    private String Vehicle_Type_id;
    private String Vehicle_Brand_Name;
    private String Vehicle_Brand_id;


    AVLoadingIndicatorView avi_indicator;

    int mfg_year_start,mfg_year_end;



    public PopularVehicleListAdapter(Context context, List<PopularVehicleListResponse.DataBean> popularVehicleListResponseList, RecyclerView inbox_list ) {
        this.context = context;
        this.popularVehicleListResponseList = popularVehicleListResponseList;



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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_popular_vehicle_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = popularVehicleListResponseList.get(position);
       // vehicleDetailsBean = vehicleDetailsBeanList.get(position);




        for (int i = 0; i < popularVehicleListResponseList.size(); i++) {
             vehicleid = currentItem.getVehicle_Details().get(0).get_id();
             vehiclename = currentItem.getVehicle_Details().get(0).getVehicle_Name();
             vehicleimage = currentItem.getVehicle_Details().get(0).getVehicle_Image();
            //String vehiclename = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getVehicle_Name();
            //String vehicleimage = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getVehicle_Name();
            Log.w(TAG,"vehiclename--->"+vehiclename+" "+"vehicleimage : "+vehicleimage);
            holder.txt_popular_vehicle_name.setText(vehiclename);

            if (vehicleimage != null ) {

                Glide.with(context)
                        .load(vehicleimage)
                        .into(holder.cv_popular_vehicle_image);

            }else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(holder.cv_popular_vehicle_image);

            }



        }


        holder.ll_popular_vehicle_list.setOnClickListener(v -> {
            for(int i=0;i<popularVehicleListResponseList.size();i++) {
                _id = popularVehicleListResponseList.get(position).get_id();
                Vehicle_Type_Name = popularVehicleListResponseList.get(position).getVehicle_Type_Name();
                Vehicle_Type_id = popularVehicleListResponseList.get(position).getVehicle_Type_id();
                Vehicle_Brand_Name = popularVehicleListResponseList.get(position).getVehicle_Brand_Name();
                Vehicle_Brand_id = popularVehicleListResponseList.get(position).getVehicle_Brand_id();

                vehicleid = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).get_id();
                vehiclename = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getVehicle_Name();
                vehicleimage = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getVehicle_Image();
                fuelTypeBeanList = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getFuel_Type();
                vehicleModelBeanList = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getVehicle_Model();
                mfg_year_start = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getMfg_year_start();
                mfg_year_end = popularVehicleListResponseList.get(position).getVehicle_Details().get(0).getMfg_year_end();

                Log.w(TAG,"Vehicle_Type_Name--->"+Vehicle_Type_Name);

                Log.w(TAG,"fuelTypeBeanList : "+new Gson().toJson(fuelTypeBeanList)+" vehicleModelBeanList : "+new Gson().toJson(vehicleModelBeanList));
            }
            alertAddNewVehicle(v, vehiclename, vehicleimage,mfg_year_start,mfg_year_end);
        });





    }









    @Override
    public int getItemCount() {
        return popularVehicleListResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_popular_vehicle_name;
        public LinearLayout ll_popular_vehicle_list;
        public ImageView cv_popular_vehicle_image;



        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_popular_vehicle_name = itemView.findViewById(R.id.txt_popular_vehicle_name);
            ll_popular_vehicle_list = itemView.findViewById(R.id.ll_popular_vehicle_list);
            cv_popular_vehicle_image = itemView.findViewById(R.id.cv_popular_vehicle_image);













        }




    }





    private void alertAddNewVehicle(View view, String vehicle_name, String vehicle_image, int mfg_year_start, int mfg_year_end) {

        try {



            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_add_new_vehicle_layout);

            avi_indicator = dialog.findViewById(R.id.avi_indicator);
            avi_indicator.smoothToShow();

            ImageView cv_brandvehicleimage = dialog.findViewById(R.id.cv_brand_vehicle_image);
            TextView txt_brandvehiclename =  dialog.findViewById(R.id.txt_brand_vehicle_name);
            EditText edt_vehicleno =  dialog.findViewById(R.id.edt_vehicle_no);
            Button btn_addvehicle = dialog.findViewById(R.id.btn_addvehicle);
            rv_fueltype = dialog.findViewById(R.id.rv_fueltype);
            rv_vehiclemodeltype = dialog.findViewById(R.id.rv_vehiclemodeltype);

            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            if(fuelTypeBeanList.size() > 0){
                setFuelTypeView();
            }
            if(vehicleModelBeanList.size() > 0){
                setVehicleModelTypeView();
            }





            ArrayList<String> years = new ArrayList<>();
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = mfg_year_start; i <= mfg_year_end; i++) {
                years.add(Integer.toString(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, years);

            Spinner spinYear = dialog.findViewById(R.id.spr_year);
            spinYear.setAdapter(adapter);

            spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    mfgyear = spinYear.getSelectedItem().toString();
                    Log.w(TAG,"mfgyear :"+mfgyear);



                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });







            if (vehicle_image != null && !vehicle_image.isEmpty()) {

                Glide.with(context)
                        .load(vehicle_image)
                        .into(cv_brandvehicleimage);

            }
            else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(cv_brandvehicleimage);

            }
            if (vehicle_name != null && !vehicle_name.isEmpty()){
                txt_brandvehiclename.setText(vehicle_name);
            }





            btn_addvehicle.setOnClickListener(view1 -> {


                vehicleno = edt_vehicleno.getText().toString();

                session = new SessionManager(context);
                HashMap<String, String> user = session.getUserDetails();
                customerid = user.get(SessionManager.KEY_ID);



                Log.w(TAG,"vehicleno-->"+vehicleno);

                if(validateInputs()){
                    if (new ConnectionDetector(context).isNetworkAvailable(context)) {
                        addVehicleResponseCall(view1);

                    }
                }





            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }



    private void setFuelTypeView() {
        //rv_fueltype.setLayoutManager(new GridLayoutManager(context, 4));

        rv_fueltype.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        PopularFuelTypeListAdapter popularFuelTypeListAdapter = new PopularFuelTypeListAdapter(context,fuelTypeBeanList , rv_fueltype, vehcilFuelTypeListener);
        rv_fueltype.setAdapter(popularFuelTypeListAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);


        popularFuelTypeListAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > fuelTypeBeanList.size()) {
                Log.e("haint", "Load More");
            }


        });

    }
    private void setVehicleModelTypeView() {
        rv_vehiclemodeltype.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        PopularVehicleModelListAdapter popularVehicleModelListAdapter = new PopularVehicleModelListAdapter(context,vehicleModelBeanList , rv_vehiclemodeltype, vehicleModeTypeListener);
        rv_vehiclemodeltype.setAdapter(popularVehicleModelListAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);


        popularVehicleModelListAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > vehicleModelBeanList.size()) {
                Log.e("haint", "Load More");
            }


        });

    }


    VehicleFuelTypeListener vehcilFuelTypeListener = new VehicleFuelTypeListener(){

        @Override
        public void vehicleFuelList(int position) {
            fuelid = fuelTypeBeanList.get(position).get_id();
            fuelname = fuelTypeBeanList.get(position).getFuel_Type();
            fuelbackgroundcolor = fuelTypeBeanList.get(position).getBackground_Color();
        }
    };

    VehicleModeTypeListener vehicleModeTypeListener = new VehicleModeTypeListener() {
        @Override
        public void vehicleModelList(int position) {
            modelid = vehicleModelBeanList.get(position).get_id();
            modelname = vehicleModelBeanList.get(position).getVehicleModel_Name();
        }
    };


    private boolean validateInputs() {
        int Min = 5;
        int Max = 13;
        if (vehicleno.isEmpty()) {
            Toasty.warning(context, "Please enter vehicle number", Toast.LENGTH_SHORT, true).show();
            return false;
        } else {
            Log.w(TAG, "Conditions : " + (vehicleno.length() >= Min && vehicleno.length() <= Max) + " " + "Length : " + vehicleno.length());
            if (vehicleno.length() >= Min && vehicleno.length() <= Max) {
                Log.w(TAG,"ISVALIDVEHICLENUMBER");
            } else {
                //Show Error
                Toasty.warning(context, context.getResources().getString(R.string.validvehiclenumberrormsg), Toast.LENGTH_SHORT, true).show();

                return false;
            }
        }


        if(fuelid == null){
            Toasty.warning(context, "Please select fuel type", Toast.LENGTH_SHORT, true).show();
            return false;
        }else if(modelid == null){
            Toasty.warning(context, "Please select vehicle model", Toast.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }






    private void addVehicleResponseCall(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();


      //  avi_indicator.smoothToShow();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddVehicleResponse> call = apiInterface.addVehicleResponseCall(RestUtils.getContentType(),addVehicleRequest());

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<AddVehicleResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddVehicleResponse> call, @NotNull Response<AddVehicleResponse> response) {
               // avi_indicator.smoothToHide();
                dialog.dismiss();
                progressDialog.dismiss();
                Log.w(TAG,"AddVehicleResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        Toasty.success(context, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        Intent i = new Intent(context, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);




                    }
                }



            }



            @Override
            public void onFailure(@NotNull Call<AddVehicleResponse> call, @NotNull Throwable t) {
                //avi_indicator.smoothToHide();
                dialog.dismiss();
                progressDialog.dismiss();
                Log.w(TAG,"AddVehicleResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AddVehicleRequest addVehicleRequest() {
        /*
         * Vehicle_No : AP -7771
         * Customer_id : 5f07f9f313286d500bc9d4d8
         * Vehicletype_id : 5f02ea9843d45632b001f868
         * Vehicletype_Name : Four Wheeler
         * Vehicle_Brand_id : 5f0c1023c57e0c6a78bc9395
         * Vehicle_Brand_Name : BMW
         * Vehicle_Name_id : 5f0c50a1c1362e2e5f2fce1a
         * Vehicle_Name : BMW
         * Year_of_Manufacture : 2019
         * Fuel_Type_id : 5f0c12f831e1f8795ba172f7
         * Fuel_Type_Name : Petrol
         * Fuel_Type_Background_Color
         * Vehicle_Model_id : 5f0c306f7f655108e6ca2ea7
         * VehicleModel_Name : Cabriolet
         * Vehicle_Image : ""
         */


        AddVehicleRequest addVehicleRequest = new AddVehicleRequest();
        addVehicleRequest.setVehicle_No(vehicleno);
        addVehicleRequest.setCustomer_id(customerid);
        addVehicleRequest.setVehicletype_id(Vehicle_Type_id);
        addVehicleRequest.setVehicletype_Name(Vehicle_Type_Name);
        addVehicleRequest.setVehicle_Brand_id(Vehicle_Brand_id);
        addVehicleRequest.setVehicle_Brand_Name(Vehicle_Brand_Name);
        addVehicleRequest.setVehicle_Name_id(vehicleid);
        addVehicleRequest.setVehicle_Name(vehiclename);
        addVehicleRequest.setYear_of_Manufacture(mfgyear);
        addVehicleRequest.setFuel_Type_id(fuelid);
        addVehicleRequest.setFuel_Type_Name(fuelname);
        addVehicleRequest.setFuel_Type_Background_Color(fuelbackgroundcolor);
        addVehicleRequest.setVehicle_Model_id(modelid);
        addVehicleRequest.setVehicle_Model_Name(modelname);
        addVehicleRequest.setVehicle_Image(vehicleimage);


        Log.w(TAG,"addVehicleRequest"+ "--->" + new Gson().toJson(addVehicleRequest));
        return addVehicleRequest;
    }





}
