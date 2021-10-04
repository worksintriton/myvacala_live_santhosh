package com.triton.myvacala.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.triton.myvacala.R;

import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.activities.parking.vehicle.ParkingVehicleBrandListActivity;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.VehicleFuelTypeListener;
import com.triton.myvacala.interfaces.VehicleIdListener;
import com.triton.myvacala.interfaces.VehicleModeTypeListener;
import com.triton.myvacala.listeners.OnLoadMoreListener;
import com.triton.myvacala.requestpojo.AddVehicleRequest;
import com.triton.myvacala.requestpojo.VehicleDetailsRequest;
import com.triton.myvacala.responsepojo.AddVehicleResponse;
import com.triton.myvacala.responsepojo.VehicleBrandListResponse;
import com.triton.myvacala.responsepojo.VehicleDetailsResponse;
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


public class VehicleBrandExpandableListParkingAdapter extends BaseExpandableListAdapter   {

    private String TAG ="VehicleBrandExpandableListParkingAdapter";

    Dialog dialog;
    AlertDialog.Builder builder;
    AlertDialog progressDialog;

    private Context context;
    private List<VehicleBrandListResponse.DataBean> expandableListTitle;


    private HashMap<String, List<VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean>> expandableListDetail;


    private List<VehicleDetailsResponse.DataBean> vehicleDetailsResponseList = null;
    private SharedPreferences preferences;
    RecyclerView rv_fueltype,rv_vehiclemodeltype;


    String vehicleid ="",vehiclename = "",vehicleimage = "";

    String vehiclebrandName,vehicle_Type_id;
    List<VehicleDetailsResponse.DataBean.FuelTypeBean> fuelTypeBeanList = new ArrayList<>();
    List<VehicleDetailsResponse.DataBean.VehicleModelBean> vehicleModelBeanList = new ArrayList<>();


    String fuelid,fuelname,fuelbackgroundcolor;
    String modelid,modelname;
    String mfgyear,vehicleno;
    private VehicleIdListener mCallback;

    AVLoadingIndicatorView avi_indicator;

    List<VehicleBrandListResponse.DataBean.VehicleNameListBean> vehicle_name_list = new ArrayList<>();
    private List<String> vehicleTypeGetList = new ArrayList<>();
    List<VehicleBrandListResponse.DataBean.VehicleNameListBean.FuelTypeBean> fuelTypeBeanListNew = new ArrayList<>();

    private ExpandListChildViewListener expandListChildViewListener;
    private int listPosition;
   // private VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean vehicleModelBean;
    private VehicleBrandListResponse.DataBean dataBean;
    private String vehicletype_Name;



    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    SessionManager session;
    String customerid = "";

    public VehicleBrandExpandableListParkingAdapter(Context context, List<VehicleBrandListResponse.DataBean> expandableListTitle,
                                                    HashMap<String, List<VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean>> expandableListDetail, List<VehicleBrandListResponse.DataBean.VehicleNameListBean> vehicle_name_list,
                                                    ParkingVehicleBrandListActivity parkingVehicleBrandListActivity, List<String> vehicleTypeGetList, ExpandListChildViewListener expandListChildViewListener, String vehicletype_Name) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.vehicle_name_list = vehicle_name_list;
        this.mCallback = (VehicleIdListener)parkingVehicleBrandListActivity;
        this.vehicleTypeGetList = vehicleTypeGetList;
        this.expandListChildViewListener = expandListChildViewListener;
        this.vehicletype_Name = vehicletype_Name;

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        Log.w(TAG,"getChild---->"+"listPosition : "+listPosition+" "+"expandedListPosition : "+expandedListPosition);

        return Objects.requireNonNull(this.expandableListDetail.get(this.vehicleTypeGetList.get(listPosition)))
                .get(expandedListPosition);

        //        Log.w(TAG,"getChild---->"+"listPosition : "+listPosition+" "+"expandedListPosition : "+expandedListPosition);

  //      return this.expandableListDetail.get(this.vehicleTypeGetList.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {

       // Log.w(TAG,"getChildId---->"+"listPosition : "+listPosition+" "+"expandedListPosition : "+expandedListPosition);
        return expandedListPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean  vehicleModelBean = (VehicleBrandListResponse.DataBean.VehicleNameListBean.VehicleModelBean) getChild(listPosition, expandedListPosition);


   //     Log.w(TAG,"getChildView---->"+"listPosition : "+listPosition+" "+"expandedListPosition : "+expandedListPosition);
       // mCallback.vehicleId(listPosition);

     //   Log.w(TAG,"VehicleName-->"+vehicleModelBean.getName());


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.lblListItem);
        ImageView cv_brand_vehicle_image = convertView.findViewById(R.id.cv_brand_vehicle_image);
        if(vehicleModelBean.getVehicleModel_Name()!= null){
            expandedListTextView.setText(Objects.requireNonNull(vehicleModelBean.getVehicle_name()));

        }


        if (vehicleModelBean.getVehicleModel_Image() != null) {

            Glide.with(context)
                    .load(vehicleModelBean.getVehicleModel_Image())
                    .into(cv_brand_vehicle_image);

        }else{
            Glide.with(context)
                    .load(R.drawable.logo)
                    .into(cv_brand_vehicle_image);

        }

        LinearLayout ll_root_brandlistdetails = convertView.findViewById(R.id.ll_root_brandlistdetails);

        ll_root_brandlistdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // expandListChildViewListener.expandListChildView(expandedListPosition);
                vehicleModelData(vehicleModelBean.get_id(),vehicleModelBean.getVehicleModel_Name(),vehicleModelBean.getVehicleModel_Image());
                if (new ConnectionDetector(context).isNetworkAvailable(context)) {

                    vehicleDetailsResponseCall(v,vehicleModelBean.getNameListId(),vehicleModelBean.get_id(),vehicleModelBean.getVehicle_brand_id(),
                            vehicleModelBean.getVehicle_name(),vehicleModelBean.getMfg_year_start(),vehicleModelBean.getMfg_year_end());
                }
               /* alertAddNewVehicle(v,vehicleModelBean.getNameListId(),vehicleModelBean.get_id(),vehicleModelBean.getVehicle_brand_id(),
                        vehicleModelBean.getVehicle_name());*/

            }
        });



        //Log.w(TAG,"getChildView--->"+" "+"expandedListText :"+ch.getName());

        return convertView;
    }

    private void vehicleModelData(String id, String name, String image) {
        vehicleid = id;
        vehiclename = name;
        vehicleimage = image;


    }

    @Override
    public int getChildrenCount(int listPosition) {

        Log.w(TAG, "getChildrenCount" + this.expandableListDetail.get(this.vehicleTypeGetList.get(listPosition)).size());
        return Objects.requireNonNull(this.expandableListDetail.get(this.vehicleTypeGetList.get(listPosition)))
                .size();

              //  return this.expandableListDetail.get(this.vehicleTypeGetList.get(listPosition)).size();
                //expandableListDetail.get(expandableListTitle.get(listPosition)).size();
           }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
     //   String listTitle = (String) getGroup(get(listPosition));
        this.listPosition = listPosition;
        VehicleBrandListResponse.DataBean dataBean = (VehicleBrandListResponse.DataBean) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(layoutInflater).inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.lblListHeader);
        CircleImageView cv_header_brand_vehicle_image = convertView.findViewById(R.id.cv_header_brand_vehicle_image);

      /*  if (dataBean.get() != null) {

            Glide.with(context)
                    .load(expandableListTitle.get(listPosition).getVehicle_Brand_Image())
                    .into(cv_header_brand_vehicle_image);

        }else{*/
            Glide.with(context)
                    .load(R.drawable.logo)
                    .into(cv_header_brand_vehicle_image);

        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(dataBean.getVehicle_Brand_Name());
        vehicle_Type_id = dataBean.getVehicle_Type_id();
        vehiclebrandName = listTitleTextView.getText().toString();

        //Log.w(TAG,"getGroupView--->"+" "+"listTitle :"+dataBean.getVehicle_Brand_Image());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    private void alertAddNewVehicle(View view, String nameListId, String id, String vehicle_brand_id, String vehicle_name, int mfg_year_start, int mfg_year_end) {

        try {



            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.alert_add_new_vehicle_layout);

            avi_indicator = dialog.findViewById(R.id.avi_indicator);
            avi_indicator.smoothToShow();




           ImageView cv_brandvehicleimage = dialog.findViewById(R.id.cv_brand_vehicle_image);
            TextView txt_brandvehiclename =  dialog.findViewById(R.id.txt_brand_vehicle_name);
            EditText edt_vehicleno =  dialog.findViewById(R.id.edt_vehicle_no);
            Button btn_addvehicle = dialog.findViewById(R.id.btn_addvehicle);


            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ArrayList<String> years = new ArrayList<String>();
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



            rv_fueltype = dialog.findViewById(R.id.rv_fueltype);
            rv_vehiclemodeltype = dialog.findViewById(R.id.rv_vehiclemodeltype);





            if (vehicleimage != null && !vehicleimage.isEmpty()) {

                Glide.with(context)
                        .load(vehicleimage)
                        .into(cv_brandvehicleimage);

            }
            else{
                Glide.with(context)
                        .load(R.drawable.logo)
                        .into(cv_brandvehicleimage);

            }
            if (vehiclename != null && !vehiclename.isEmpty()){
                txt_brandvehiclename.setText(vehiclename);
            }





            btn_addvehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    vehicleno = edt_vehicleno.getText().toString();

                    session = new SessionManager(context);
                    HashMap<String, String> user = session.getUserDetails();
                    customerid = user.get(SessionManager.KEY_ID);



                    Log.w(TAG,"vehicleno-->"+vehicleno);

                    if(validateInputs()){
                        if (new ConnectionDetector(context).isNetworkAvailable(context)) {
                            addVehicleResponseCall(view,nameListId,id,vehicle_brand_id,vehicle_name);

                        }
                    }


                    /*final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMesfsage("Please wait.....");
                    dialog.show();
                    locationDeleteResponseCall(dialog,locationid);*/


                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }


    private void vehicleDetailsResponseCall(View view, String nameListId, String id, String vehicle_brand_id, String vehicle_name, int mfg_year_start, int mfg_year_end) {
        //avi_indicator.smoothToShow();
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VehicleDetailsResponse> call = apiInterface.vehicleDetailsResponseCall(RestUtils.getContentType(),vehicleDetailsRequest(nameListId));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<VehicleDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<VehicleDetailsResponse> call, @NotNull Response<VehicleDetailsResponse> response) {

                Log.w(TAG,"VehicleDetailsResponse"+ "--->" + new Gson().toJson(response.body()));
               // avi_indicator.smoothToHide();
                progressDialog.dismiss();
                alertAddNewVehicle(view,  nameListId,  id,  vehicle_brand_id,  vehicle_name,mfg_year_start,mfg_year_end);

                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        vehicleDetailsResponseList = response.body().getData();
                        Log.w(TAG,"vehicleDetailsResponseList Size :"+vehicleDetailsResponseList.size());
                        for(int i= 0; i<vehicleDetailsResponseList.size();i++){
                             fuelTypeBeanList = vehicleDetailsResponseList.get(i).getFuel_Type();
                             vehicleModelBeanList = vehicleDetailsResponseList.get(i).getVehicle_Model();


                        }


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
                rv_fueltype.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                FuelTypeListAdapter fuelTypeListAdapter = new FuelTypeListAdapter(context,fuelTypeBeanList , rv_fueltype, vehcilFuelTypeListener, "");
                rv_fueltype.setAdapter(fuelTypeListAdapter);

                preferences = PreferenceManager.getDefaultSharedPreferences(context);


                fuelTypeListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (preferences.getInt(Constants.INBOX_TOTAL, 0) > fuelTypeBeanList.size()) {
                            Log.e("haint", "Load More");
                        }


                    }
                });

            }
            private void setVehicleModelTypeView() {
                rv_vehiclemodeltype.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                VehicleModelListAdapter vehicleModelListAdapter = new VehicleModelListAdapter(context,vehicleModelBeanList , rv_vehiclemodeltype, vehicleModeTypeListener, "");
                rv_vehiclemodeltype.setAdapter(vehicleModelListAdapter);

                preferences = PreferenceManager.getDefaultSharedPreferences(context);


                vehicleModelListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
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
        Log.w(TAG,"vehicleDetailsRequest"+ "--->" + new Gson().toJson(vehicleDetailsRequest));
        return vehicleDetailsRequest;
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




    private void addVehicleResponseCall(View view, String nameListId, String id, String vehicle_brand_id, String vehicle_name) {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();


       // avi_indicator.smoothToShow();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddVehicleResponse> call = apiInterface.addVehicleResponseCall(RestUtils.getContentType(),addVehicleRequest(nameListId,id,vehicle_brand_id,vehicle_name));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<AddVehicleResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddVehicleResponse> call, @NotNull Response<AddVehicleResponse> response) {
               // avi_indicator.smoothToHide();

                progressDialog.dismiss();
                Log.w(TAG,"AddVehicleResponse--->" + new Gson().toJson(response.body()));


                assert response.body() != null;
                if(response.body().getCode() == 200){
                       // progressDialog.dismiss();
                        dialog.dismiss();
                        Toasty.success(context, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        callDirections("3");

                    }
                else{
                    Toasty.warning(context, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                    //showErrorLoading(response.body().getMessage());
                    }




            }



            @Override
            public void onFailure(@NotNull Call<AddVehicleResponse> call, @NotNull Throwable t) {
               // avi_indicator.smoothToHide();
                dialog.dismiss();
                progressDialog.dismiss();

                Log.w(TAG,"AddVehicleResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AddVehicleRequest addVehicleRequest(String nameListId, String id, String vehicle_brand_id, String vehicle_name) {
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
         * Vehicle_Model_id : 5f0c306f7f655108e6ca2ea7
         * VehicleModel_Name : Cabriolet
         * Vehicle_Image : ""
         */

        Log.w(TAG,"vehicletype_Name"+vehicletype_Name);
        AddVehicleRequest addVehicleRequest = new AddVehicleRequest();
        addVehicleRequest.setVehicle_No(vehicleno);
        addVehicleRequest.setCustomer_id(customerid);
        addVehicleRequest.setVehicletype_id(vehicle_Type_id);
        addVehicleRequest.setVehicletype_Name(vehicletype_Name);
        addVehicleRequest.setVehicle_Brand_id(vehicle_brand_id);
        addVehicleRequest.setVehicle_Brand_Name(vehiclebrandName);
        addVehicleRequest.setVehicle_Name_id(nameListId);
        addVehicleRequest.setVehicle_Name(vehicle_name);
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




    public  interface  ExpandListChildViewListener {
        void expandListChildView(int position);
    }




    private boolean validateInputs() {
        int Min = 5;
        int Max = 13;
        if (vehicleno.isEmpty()) {
            Toasty.warning(context, "Please enter vehicle number", Toast.LENGTH_SHORT, true).show();
            return false;
        } else {
            if (vehicleno.length() >= Min && vehicleno.length() <= Max) {
                Log.w(TAG,"Validvehiclenumber");
            } else {
                //Show Error
                Toasty.warning(context,context.getResources().getString(R.string.validvehiclenumberrormsg), Toast.LENGTH_SHORT, true).show();

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




    public void callDirections(String tag){
        Intent intent = new Intent(context, DashboardParkingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tag",tag);
        context.startActivity(intent);

    }


    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

}
