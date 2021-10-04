package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
import com.triton.myvacala.interfaces.VehicleStatusChangeListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.VehicleDeleteRequest;
import com.triton.myvacala.requestpojo.VehicleDetailsRequest;
import com.triton.myvacala.requestpojo.VehicleEditRequest;
import com.triton.myvacala.requestpojo.VehicleStatusChangeRequest;
import com.triton.myvacala.responsepojo.VehicleDeleteResponse;

import com.triton.myvacala.responsepojo.VehicleDetailsResponse;
import com.triton.myvacala.responsepojo.VehicleEditResponse;
import com.triton.myvacala.responsepojo.VehicleListsResponse;
import com.triton.myvacala.responsepojo.VehicleStatusChangeResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;

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


public class VehicleListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_LOADING = 5;
    private  String TAG = "VehicleListAdapter";
    private List<VehicleListsResponse.DataBean> vehicleListsResponseList = null;

    private Context context;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    VehicleListsResponse.DataBean currentItem;
    String strMSgdaystime;
    String strMsg;

    private Boolean isPoll = false;
    String formatedDate,formatedStartDate = "";
    Dialog dialog;

    public static String id = "";


    private String _id;
    private String Customer_id;
    private String Vehicle_Image;
    private String Vehicletype_id;
    private String Vehicletype_Name;
    private String Vehicle_Brand_id;
    private String Vehicle_Brand_Name;
    private String Vehicle_Name_id;
    private String Vehicle_Name;
    private String Year_of_Manufacture;
    private String Vehicle_No;
    private String Fuel_Type_id;
    private String Fuel_Type_Name;
    private String Vehicle_Model_id;
    private String Vehicle_Model_Name;
    private String Status;



    private List<VehicleDetailsResponse.DataBean> vehicleDetailsResponseList = null;
    private SharedPreferences preferences;
    RecyclerView rv_fueltype,rv_vehiclemodeltype;


    String vehicleid ="",vehiclename = "",vehicleimage = "";

    String vehiclebrandName,vehicle_Type_id;
    List<VehicleDetailsResponse.DataBean.FuelTypeBean> fuelTypeBeanList = new ArrayList<>();
    List<VehicleDetailsResponse.DataBean.VehicleModelBean> vehicleModelBeanList = new ArrayList<>();


    String fuelid,fuelname;
    String modelid,modelname;
    String mfgyear,vehicleno;

    SessionManager session;
    String customerid = "";

    int mfgStartYear,mfgEndYear;

    int selectedPosition = -1;

    private FuelTypeEditListAdapter fuelTypeEditListAdapter;
    private  VehicleModelListEditAdapter vehicleModelListEditAdapter;
    private VehicleStatusChangeListener vehicleStatusChangeListener;
    private String Fuel_Type_Background_Color;


    public VehicleListAdapter(Context context, List<VehicleListsResponse.DataBean> vehicleListsResponseList, RecyclerView inbox_list,VehicleStatusChangeListener vehicleStatusChangeListener) {
        this.vehicleListsResponseList = vehicleListsResponseList;
        this.context = context;
        this.vehicleStatusChangeListener = vehicleStatusChangeListener;




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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vehiclelist_cardview, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("SetTextI18n")
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = vehicleListsResponseList.get(position);
        for (int i = 0; i < vehicleListsResponseList.size(); i++) {

            holder.txt_vehiclelist_name.setText(currentItem.getVehicle_Name());
            holder.txt_vehiclelist_year.setText(currentItem.getYear_of_Manufacture());
            holder.txt_vehiclelist_number.setText(currentItem.getVehicle_No());
            holder.txt_vehiclelist_year.setText(currentItem.getYear_of_Manufacture());

            holder.btn_vehiclelist_fueltype.setText(vehicleListsResponseList.get(position).getFuel_Type_Name());
            holder.btn_vehiclelist_fueltype.setBackgroundResource(R.drawable.tags_rounded_corners_withoutcorner);
            GradientDrawable gd = (GradientDrawable) holder.btn_vehiclelist_fueltype.getBackground();
            gd.setColor(Color.parseColor(vehicleListsResponseList.get(position).getFuel_Type_Background_Color()));
           // gd.setCornerRadius(10);



            if (currentItem.getVehicle_Image() != null && !currentItem.getVehicle_Image().isEmpty()) {
                Glide.with(context)
                        .load(currentItem.getVehicle_Image())
                        .into(holder.cv_vehiclelist_image);

            }
            else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(holder.cv_vehiclelist_image);

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
                String id = vehicleListsResponseList.get(position).get_id();
                String status = vehicleListsResponseList.get(position).getStatus();
                if(!status.trim().equalsIgnoreCase("Default".trim())){
                    showStatusAlert(view,id);
                }else{
                    Toasty.warning(context, "Default vehicle cannot be deleted.", Toast.LENGTH_SHORT, true).show();


                }


            }
        });
        holder.txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _id = vehicleListsResponseList.get(position).get_id();
                Customer_id = vehicleListsResponseList.get(position).getCustomer_id();
                Vehicle_Image = vehicleListsResponseList.get(position).getVehicle_Image();
                Vehicletype_id = vehicleListsResponseList.get(position).getVehicletype_id();
                Vehicletype_Name = vehicleListsResponseList.get(position).getVehicletype_Name();
                Vehicle_Brand_id = vehicleListsResponseList.get(position).getVehicle_Brand_id();
                Vehicle_Brand_Name = vehicleListsResponseList.get(position).getVehicle_Brand_Name();
                Vehicle_Name_id = vehicleListsResponseList.get(position).getVehicle_Name_id();
                Vehicle_Name = vehicleListsResponseList.get(position).getVehicle_Name();
                Year_of_Manufacture = vehicleListsResponseList.get(position).getYear_of_Manufacture();
                Vehicle_No = vehicleListsResponseList.get(position).getVehicle_No();
                Fuel_Type_id = vehicleListsResponseList.get(position).getFuel_Type_id();
                Fuel_Type_Name = vehicleListsResponseList.get(position).getFuel_Type_Name();
                Fuel_Type_Background_Color = vehicleListsResponseList.get(position).getFuel_Type_Background_Color();
                Vehicle_Model_id = vehicleListsResponseList.get(position).getVehicle_Model_id();
                Vehicle_Model_Name = vehicleListsResponseList.get(position).getVehicle_Model_Name();
                Status = vehicleListsResponseList.get(position).getStatus();

                if (new ConnectionDetector(context).isNetworkAvailable(context)) {

                    vehicleDetailsResponseCall(v,_id,Customer_id,Vehicle_Image,Vehicletype_id,Vehicletype_Name,Vehicle_Brand_id,Vehicle_Brand_Name,Vehicle_Name_id,Vehicle_Name,Year_of_Manufacture,Vehicle_No,Fuel_Type_id,Fuel_Type_Name,Vehicle_Model_id,Vehicle_Model_Name,Status);
                }



            }
        });



        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = vehicleListsResponseList.get(position).get_id();
                String customerid = vehicleListsResponseList.get(position).getCustomer_id();
                String vehicletypeId = vehicleListsResponseList.get(position).getVehicletype_id();
                vehicleStatusChangeListener.selectedVehicleDetails(id,customerid,vehicletypeId);

            }
        });








    }









    @Override
    public int getItemCount() {
        return vehicleListsResponseList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_vehiclelist_name,txt_vehiclelist_year,txt_vehiclelist_number,txt_edit,txt_delete;
        public Button btn_vehiclelist_fueltype;
        public ImageView cv_vehiclelist_image;
        public LinearLayout ll_root;
        public ImageView iv_default_location;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_vehiclelist_name = itemView.findViewById(R.id.txt_vehiclelist_name);
            txt_vehiclelist_year = itemView.findViewById(R.id.txt_vehiclelist_year);
            btn_vehiclelist_fueltype = itemView.findViewById(R.id.btn_vehiclelist_fueltype);
            txt_vehiclelist_number = itemView.findViewById(R.id.txt_vehiclelist_number);
            cv_vehiclelist_image = itemView.findViewById(R.id.cv_vehiclelist_image);
            txt_edit = itemView.findViewById(R.id.txt_edit);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            iv_default_location = itemView.findViewById(R.id.iv_default_location);
            iv_default_location.setVisibility(View.GONE);

            ll_root = itemView.findViewById(R.id.ll_root);

        }




    }






    private void showStatusAlert(View view, final String vehicleid) {

        try {

            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.deletemsgvehicle);
            Button dialogButtonApprove = dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    vehicleDeleteResponseCall(dialog,vehicleid);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void vehicleDeleteResponseCall(final ProgressDialog dialog, String vehicleid) {
        dialog.show();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleDeleteResponse> call = apiInterface.vehicleDeleteResponseCall(RestUtils.getContentType(),vehicleDeleteRequest(vehicleid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<VehicleDeleteResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleDeleteResponse> call, @NotNull Response<VehicleDeleteResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"VehicleDeleteResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(context, "Vehicle Removed Successfully", Toast.LENGTH_SHORT, true).show();
                        callDirections("3");

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<VehicleDeleteResponse> call, @NotNull Throwable t) {
                dialog.dismiss();

                Log.w(TAG,"VehicleDeleteResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private VehicleDeleteRequest vehicleDeleteRequest(String vehicleid) {
        /*
         * Vehicle_id : 5f0c0cfc2f857d66950cf25f
         */
        VehicleDeleteRequest vehicleDeleteRequest = new VehicleDeleteRequest();
        vehicleDeleteRequest.setVehicle_id(vehicleid);
        Log.w(TAG,"VehicleDeleteRequest"+ "--->" + new Gson().toJson(vehicleDeleteRequest));
        return vehicleDeleteRequest;
    }



    private void showLocationStatusChangeAlert(View view, String vehicleid, String customerid, String vehicletypeId) {

        try {

            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.vehiclestatuschange);
            Button dialogButtonApprove = dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait.....");
                    dialog.show();
                    vehicleStatusChangeResponseCall(dialog,vehicleid,customerid,vehicletypeId);


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
    private void vehicleStatusChangeResponseCall(final ProgressDialog dialog, String vehicleid, String customerid, String vehicletypeId) {
        dialog.show();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleStatusChangeResponse> call = apiInterface.vehicleStatusChangeResponseCall(RestUtils.getContentType(),vehicleStatusChangeRequest(vehicleid,customerid,vehicletypeId));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<VehicleStatusChangeResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleStatusChangeResponse> call, @NotNull Response<VehicleStatusChangeResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"VehicleStatusChangeResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(context, "Default Location Changed Successfully", Toast.LENGTH_SHORT, true).show();

                        callDirections("3");

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<VehicleStatusChangeResponse> call, @NotNull Throwable t) {
                dialog.dismiss();

                Log.w(TAG,"LocationStatusChangeResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private VehicleStatusChangeRequest vehicleStatusChangeRequest(String vehicleid, String customerid, String vehicletypeId) {
        /*
         * vehicle_id : 5f0d4b23892e18724abf9a51
         * Customer_id : 5f07f9f313286d500bc9d4d8
         * Vehicletype_id:""
         */
        VehicleStatusChangeRequest vehicleStatusChangeRequest = new VehicleStatusChangeRequest();
        vehicleStatusChangeRequest.setVehicle_id(vehicleid);
        vehicleStatusChangeRequest.setCustomer_id(customerid);
        vehicleStatusChangeRequest.setVehicletype_id(vehicletypeId);
        Log.w(TAG,"VehicleStatusChangeRequest"+ "--->" + new Gson().toJson(vehicleStatusChangeRequest));
        return vehicleStatusChangeRequest;
    }



    public void callDirections(String tag){
        Intent intent = new Intent(context, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tag",tag);
        context.startActivity(intent);

    }


    private void vehicleDetailsResponseCall(View view, String _id, String customer_id, String vehicle_Image, String vehicletype_id, String vehicletype_Name, String vehicle_Brand_id, String vehicle_Brand_Name, String vehicle_Name_id, String vehicle_Name, String year_of_Manufacture, String vehicle_No, String fuel_Type_id, String fuel_Type_Name, String vehicle_Model_id, String vehicle_Model_Name, String status) {
        //avi_indicator.smoothToShow();
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleDetailsResponse> call = apiInterface.vehicleDetailsResponseCall(RestUtils.getContentType(),vehicleDetailsRequest(vehicle_Name_id));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<VehicleDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleDetailsResponse> call, @NotNull Response<VehicleDetailsResponse> response) {

                Log.w(TAG,"VehicleDetailsResponse"+ "--->" + new Gson().toJson(response.body()));
                // avi_indicator.smoothToHide();
                progressDialog.dismiss();

                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        vehicleDetailsResponseList = response.body().getData();
                        Log.w(TAG,"vehicleDetailsResponseList Size :"+vehicleDetailsResponseList.size());
                        for(int i= 0; i<vehicleDetailsResponseList.size();i++){
                            fuelTypeBeanList = vehicleDetailsResponseList.get(i).getFuel_Type();
                            vehicleModelBeanList = vehicleDetailsResponseList.get(i).getVehicle_Model();
                            mfgStartYear = vehicleDetailsResponseList.get(i).getMfg_year_start();
                            mfgEndYear = vehicleDetailsResponseList.get(i).getMfg_year_end();


                        }
                        alertEditVehicle(view,_id,Customer_id,Vehicle_Image,Vehicletype_id,Vehicletype_Name,Vehicle_Brand_id,Vehicle_Brand_Name,Vehicle_Name_id,Vehicle_Name,Year_of_Manufacture,Vehicle_No,Fuel_Type_id,Fuel_Type_Name,Vehicle_Model_id,Vehicle_Model_Name,Status,mfgStartYear,mfgEndYear);


                        if(fuelTypeBeanList.size() > 0){
                            setFuelTypeView();
                        }
                        if(vehicleModelBeanList.size() > 0){
                            setVehicleModelTypeView();
                        }




                    }
                }



            }

            private void setFuelTypeView() {
                for(int i=0; i<fuelTypeBeanList.size();i++){
                    if(fuel_Type_Name.equalsIgnoreCase(fuelTypeBeanList.get(i).getFuel_Type())){
                        fuelTypeBeanList.get(i).setSelected(true);
                        break;
                    }
                }
                rv_fueltype.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                fuelTypeEditListAdapter = new FuelTypeEditListAdapter(context,fuelTypeBeanList , rv_fueltype, vehcilFuelTypeListener,fuel_Type_Name);
                rv_fueltype.setAdapter(fuelTypeEditListAdapter);

                preferences = PreferenceManager.getDefaultSharedPreferences(context);


                fuelTypeEditListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (preferences.getInt(Constants.INBOX_TOTAL, 0) > fuelTypeBeanList.size()) {
                            Log.e("haint", "Load More");
                        }


                    }
                });

            }
            private void setVehicleModelTypeView() {
                for(int i=0; i<vehicleModelBeanList.size();i++){
                    Log.w(TAG,"setVehicleModelTypeView : "+"vehicle_Model_Name : "+vehicle_Model_Name+" List : "+vehicleModelBeanList.get(i).getVehicleModel_Name());
                    if(vehicle_Model_Name.equalsIgnoreCase(vehicleModelBeanList.get(i).getVehicleModel_Name())){
                        vehicleModelBeanList.get(i).setSelected(true);
                        break;
                    }
                }
                rv_vehiclemodeltype.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                 vehicleModelListEditAdapter = new VehicleModelListEditAdapter(context,vehicleModelBeanList , rv_vehiclemodeltype, vehicleModeTypeListener,vehicle_Model_Name);
                rv_vehiclemodeltype.setAdapter(vehicleModelListEditAdapter);

                preferences = PreferenceManager.getDefaultSharedPreferences(context);


                vehicleModelListEditAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (preferences.getInt(Constants.INBOX_TOTAL, 0) > vehicleModelBeanList.size()) {
                            Log.e("haint", "Load More");
                        }


                    }
                });

            }

            @Override
            public void onFailure(@NotNull Call<VehicleDetailsResponse> call, @NotNull Throwable t) {

                progressDialog.dismiss();
                // avi_indicator.smoothToHide();


                Log.w(TAG,"VehicleDetailsResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private VehicleDetailsRequest vehicleDetailsRequest (String vehicleid) {
        /*
         * Vehicle_id : 5f09a242707c9a30983dd38a
         */
        VehicleDetailsRequest vehicleDetailsRequest = new VehicleDetailsRequest();
        vehicleDetailsRequest.setVehicle_id(vehicleid);
        Log.w(TAG,"VehicleDetailsRequest"+ "--->" + new Gson().toJson(vehicleDetailsRequest));
        return vehicleDetailsRequest;
    }



    VehicleFuelTypeListener vehcilFuelTypeListener = new VehicleFuelTypeListener(){

        @Override
        public void vehicleFuelList(int position) {
            Log.w(TAG,"Position---->"+position);
            for (int i=0;i<fuelTypeBeanList.size();i++){
                fuelTypeBeanList.get(i).setSelected(false);

            }

            fuelid = fuelTypeBeanList.get(position).get_id();
            fuelname = fuelTypeBeanList.get(position).getFuel_Type();
            Fuel_Type_Background_Color = fuelTypeBeanList.get(position).getBackground_Color();
            Fuel_Type_id = fuelid;
            Fuel_Type_Name = fuelname;
            fuelTypeBeanList.get(position).setSelected(true);
            Log.w(TAG,"fuelname--->"+fuelname+"isSelected--->"+fuelTypeBeanList.get(position).isSelected()+ " Fuel_Type_Background_Color :"+Fuel_Type_Background_Color);
            Log.w(TAG,"List If------->"+new Gson().toJson(fuelTypeBeanList));
            fuelTypeEditListAdapter.notifyDataSetChanged();


        }


    };

    VehicleModeTypeListener vehicleModeTypeListener = new VehicleModeTypeListener() {
        @Override
        public void vehicleModelList(int position) {
            Log.w(TAG,"Position---->"+position);
            for (int i=0;i<vehicleModelBeanList.size();i++){
                vehicleModelBeanList.get(i).setSelected(false);

            }

            modelid = vehicleModelBeanList.get(position).get_id();
            modelname = vehicleModelBeanList.get(position).getVehicleModel_Name();
            Vehicle_Model_id = vehicleModelBeanList.get(position).get_id();
            Vehicle_Model_Name = vehicleModelBeanList.get(position).getVehicleModel_Name();
            vehicleModelBeanList.get(position).setSelected(true);
            Log.w(TAG,"Vehicle_Model_Name--->"+ Vehicle_Model_Name+"isSelected---> "+vehicleModelBeanList.get(position).isSelected()+" Vehicle_Model_id :"+Vehicle_Model_id);
            Log.w(TAG,"List If------->"+new Gson().toJson(fuelTypeBeanList));
            vehicleModelListEditAdapter.notifyDataSetChanged();

        }
    };


    @SuppressLint("SetTextI18n")
    private void alertEditVehicle(View view, String _id, String customer_id, String vehicle_Image, String vehicletype_id, String vehicletype_Name, String vehicle_Brand_id, String vehicle_Brand_Name, String vehicle_Name_id, String vehicle_Name, String year_of_Manufacture, String vehicle_No, String fuel_Type_id, String fuel_Type_Name, String vehicle_Model_id, String vehicle_Model_Name, String status, int mfgStartYear, int mfgEndYear) {

        try {
            Log.w(TAG,"alertEditVehicle Fuel_Type_Name : "+Fuel_Type_Name);
            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_add_new_vehicle_layout);
            ImageView cv_brandvehicleimage = dialog.findViewById(R.id.cv_brand_vehicle_image);
            TextView txt_brandvehiclename =  dialog.findViewById(R.id.txt_brand_vehicle_name);
            EditText edt_vehicleno =  dialog.findViewById(R.id.edt_vehicle_no);
            Button btn_addvehicle = dialog.findViewById(R.id.btn_addvehicle);
            btn_addvehicle.setText("Update Vehicle");
            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            ArrayList<String> years = new ArrayList<String>();
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = mfgStartYear; i <= mfgEndYear; i++) {
                years.add(Integer.toString(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, years);

            Spinner spinYear = dialog.findViewById(R.id.spr_year);
            spinYear.setAdapter(adapter);

            if (year_of_Manufacture != null) {
                int spinnerPosition = adapter.getPosition(year_of_Manufacture);
                spinYear.setSelection(spinnerPosition);
            }

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

            rv_fueltype = dialog.findViewById(R.id.rv_fueltype);
            rv_vehiclemodeltype = dialog.findViewById(R.id.rv_vehiclemodeltype);

            if (vehicle_Image != null && !vehicle_Image.isEmpty()) {

                Glide.with(context)
                        .load(vehicle_Image)
                        .into(cv_brandvehicleimage);

            }
            else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(cv_brandvehicleimage);

            }
            if (vehicle_Name != null && !vehicle_Name.isEmpty()){
                txt_brandvehiclename.setText(vehicle_Name);
            }
            if (vehicle_No != null && !vehicle_No.isEmpty()){
                edt_vehicleno.setText(vehicle_No);
            }

            btn_addvehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vehicleno = edt_vehicleno.getText().toString();
                    session = new SessionManager(context);
                    HashMap<String, String> user = session.getUserDetails();
                    customerid = user.get(SessionManager.KEY_ID);
                    Log.w(TAG,"vehicleno-->"+vehicleno);
                    if(validateInputs()) {
                        if (new ConnectionDetector(context).isNetworkAvailable(context)) {
                            vehicleEditResponseCall(view, vehicleno, vehicle_Name_id, Fuel_Type_id, Fuel_Type_Name, Vehicle_Model_id, Vehicle_Model_Name);

                        }
                    }





                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }


    private void vehicleEditResponseCall(View view, String vehicle_No, String vehicle_Name_id, String fuel_Type_id, String fuel_Type_Name, String vehicle_Model_id, String vehicle_Model_Name) {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleEditResponse> call = apiInterface.vehicleEditResponseCall(RestUtils.getContentType(),vehicleEditRequest(vehicle_No,vehicle_Name_id,fuel_Type_id,fuel_Type_Name,vehicle_Model_id,vehicle_Model_Name));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<VehicleEditResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleEditResponse> call, @NotNull Response<VehicleEditResponse> response) {
                // avi_indicator.smoothToHide();
                dialog.dismiss();
                progressDialog.dismiss();
                Log.w(TAG,"VehicleEditResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        // progressDialog.dismiss();

                        Toasty.success(context, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        callDirections("3");

                    }
                }



            }



            @Override
            public void onFailure(@NotNull Call<VehicleEditResponse> call, @NotNull Throwable t) {
                // avi_indicator.smoothToHide();
                dialog.dismiss();
                progressDialog.dismiss();

                Log.w(TAG,"VehicleEditResponseFlr"+"--->" + t.getMessage());
            }
        });

    }
    private VehicleEditRequest vehicleEditRequest(String vehicle_No, String vehicle_Name_id, String fuel_Type_id, String fuel_Type_Name, String vehicle_Model_id, String vehicle_Model_Name) {
        /*
         * Vehicle_No : TN 48 CF 5454
         * Vehicle_id : 5f119d6a3fe80176a3fb3f0d
         * Fuel_Type_id : 5f0c136a31e1f8795ba172f9
         * Fuel_Type_Name : Natural Gas
         * Vehicle_Model_id : 5f0c30f87f655108e6ca2ea8
         * Vehicle_Model_Name : Coupe
         * Fuel_Type_Background_Color
         */

        VehicleEditRequest vehicleEditRequest = new VehicleEditRequest();
        vehicleEditRequest.setVehicle_No(vehicle_No);
        vehicleEditRequest.setVehicle_id(_id);
        vehicleEditRequest.setFuel_Type_id(fuel_Type_id);
        vehicleEditRequest.setFuel_Type_Name(fuel_Type_Name);
        vehicleEditRequest.setVehicle_Model_id(vehicle_Model_id);
        vehicleEditRequest.setVehicle_Model_Name(vehicle_Model_Name);
        vehicleEditRequest.setFuel_Type_Background_Color(Fuel_Type_Background_Color);

        Log.w(TAG,"VehicleEditRequest"+ "--->" + new Gson().toJson(vehicleEditRequest));
        return vehicleEditRequest;
    }


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


       /* if(fuelid == null){
            Toasty.warning(context, "Please select fuel type", Toast.LENGTH_SHORT, true).show();
            return false;
        }else if(modelid == null){
            Toasty.warning(context, "Please select vehicle model", Toast.LENGTH_SHORT, true).show();
            return false;
        }*/
        return true;
    }


}
