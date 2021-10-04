package com.triton.myvacala.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.Parking_Options_Activity;
import com.triton.myvacala.activities.payment.PaymentMethodBookMechanicActivity;
import com.triton.myvacala.adapter.CartServiceListAdapter;
import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.appUtils.CustomDateTimePicker;
import com.triton.myvacala.appUtils.KeyboardUtils;
import com.triton.myvacala.interfaces.SubServiceAddandRemoveListener;
import com.triton.myvacala.requestpojo.AddingCartRequest;
import com.triton.myvacala.requestpojo.BookingCreateRequest;
import com.triton.myvacala.requestpojo.CouponsCodeValidationRequest;
import com.triton.myvacala.requestpojo.GetCardDetailsRequest;
import com.triton.myvacala.requestpojo.RemovingCartRequest;
import com.triton.myvacala.responsepojo.AddingCartResponse;
import com.triton.myvacala.responsepojo.BookingCreateResponse;
import com.triton.myvacala.responsepojo.CouponsCodeValidationResponse;
import com.triton.myvacala.responsepojo.GetCardDetailsResponse;
import com.triton.myvacala.responsepojo.RemovingCartResponse;
import com.triton.myvacala.responsepojo.SubServiceListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CartActivity extends AppCompatActivity implements View.OnClickListener, SubServiceAddandRemoveListener {


    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.cv_vehicleimage)
    ImageView cv_vehicleimage;

    @BindView(R.id.txt_vehiclename)
    TextView txt_vehiclename;

    @BindView(R.id.btn_vehiclefueltype)
    Button btn_vehiclefueltype;

    @BindView(R.id.txt_location)
    TextView txt_location;

    @BindView(R.id.txt_address)
    TextView txt_address;


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.rv_cartserviceslist)
    RecyclerView rv_cartserviceslist;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.txt_subservices_discountamount)
    TextView txt_subservices_discountamount;

    @BindView(R.id.txt_subservice_originalamount)
    TextView txt_subservice_originalamount;

    @BindView(R.id.ll_proceedtopayment)
    LinearLayout ll_proceedtopayment;


    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    @BindView(R.id.rl_dateandtime)
    RelativeLayout rl_dateandtime;

    @BindView(R.id.btn_dateandtime)
    Button btn_dateandtime;

    @BindView(R.id.txt_youraddress)
    TextView txt_youraddress;


    @BindView(R.id.btn_pickup)
    Button btn_pickup;

    @BindView(R.id.btn_selfdrop)
    Button btn_selfdrop;

    @BindView(R.id.btn_afterservice)
    Button btn_afterservice;

    @BindView(R.id.btn_online)
    Button btn_online;

    @BindView(R.id.btn_changeaddress)
    Button btn_changeaddress;

    @BindView(R.id.edt_coupon)
    EditText edt_coupon;

    @BindView(R.id.edt_userissues)
    EditText edt_userissues;

    @BindView(R.id.btn_apply)
    Button btn_apply;

    @BindView(R.id.btn_remove)
    Button btn_remove;

    @BindView(R.id.content)
    LinearLayout ll_content;

    @BindView(R.id.ll_cart_is_empty)
    LinearLayout ll_cart_is_empty;

    @BindView(R.id.rldiscountamout)
    RelativeLayout rldiscountamout;

    @BindView(R.id.txt_discountamount)
    TextView txt_discountamount;




    @BindView(R.id.btnnotifications)
    Button btnnotifications;



    String TAG = "CartActivity";


    private List<GetCardDetailsResponse.DataBean> dataBeanList = new ArrayList<>();
    private List<GetCardDetailsResponse.DataBean.ItemDetailsBean> itemDetailsBeanList ;

    private Context mContext;
    private SharedPreferences preferences;

    int originalamout = 0,discountamout = 0;

    int youPay =0;


    String vehicletypeid,serviceid,customerid,customername,customeremail,customerphone ;
    String city ,street;
    String vehicleImage,vehicleName, vehicleModelName,fuelType;
    String vehicletypename,servicename,masterservicename;


    String VehicleType_Id,Vehicle_Id,Vehicle_Type,Lubricant_type,Lubricant_Type_Background_Color,Year_Of_Mfg;

    String BookingDate ="",BookingTime = "";

    @BindView(R.id.ll_city)
    LinearLayout ll_city;


    String Arrival_Mode = "PickUp";

    String valueType;
    int value;

    AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;

    String Coupon_Code ="",Coupon_Code_Percentage = "",Coupon_Code_Amount = "";



    String active_tag = "1";

    private ArrayList<SubServiceListResponse.CustomerVehicleDataBean> customerVehicleDataBeanList = null;

    String selectedVehicleId;

    String twowheelervehicleid = null,fourwheelervehicleid = null,masterserviceid = null;
    private String fuelTypeBgColor;



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
    private String Fuel_Type_Background_Color;
    private String Vehicle_Model_id;
    private String Vehicle_Model_Name;
    private String Status;
    private String updatedAt;
    private String createdAt;
    private int __v;

    public static ArrayList<GetCardDetailsResponse.LocationAvailableBean> locationAvailableBeanArrayList;
    private String locationid;



    private String City;
    private String State;
    private String Country;
    private String Pincode;
    private String Street;
    private String NearBy_LandMark;
    private String Location_NickName;
    private String Flat_No;
    private double lat;
    @SerializedName("long")
    private double longX;
    private String Location_type;


    private String bookingdateandtime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);
        ll_cart_is_empty.setVisibility(View.GONE);
        ll_content.setVisibility(View.GONE);
        ll_proceedtopayment.setVisibility(View.GONE);

        rldiscountamout.setVisibility(View.GONE);


        mContext = CartActivity.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        toolbar_title.setText(getResources().getString(R.string.cartorcheckout));

        SessionManager session = new SessionManager(CartActivity.this);
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

         customerid = user.get(SessionManager.KEY_ID);
         customername = user.get(SessionManager.KEY_NAME);
         customeremail = user.get(SessionManager.KEY_EMAIL_ID);
         customerphone = user.get(SessionManager.KEY_MOBILE);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city = extras.getString("city");
            street = extras.getString("street");
            selectedVehicleId = extras.getString("selectedVehicleId");
            Log.w(TAG,"selectedVehicleId-->"+selectedVehicleId);
            twowheelervehicleid = extras.getString("twowheelervehicleid");
            fourwheelervehicleid = extras.getString("fourwheelervehicleid");
            masterserviceid = extras.getString("masterserviceid");




            serviceid = extras.getString("serviceid");
            Log.w(TAG,"customerid : "+customerid+" "+"vehicletypeid :"+vehicletypeid+" "+"serviceid : "+serviceid);


            servicename = extras.getString("servicename");
            Log.w(TAG,"servicename--->"+servicename);
            masterservicename = extras.getString("masterservicename");



          /*  customerVehicleDataBeanList = (ArrayList<SubServiceListResponse.CustomerVehicleDataBean>) getIntent().getSerializableExtra("customervehicledatabeanlist");

            if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
                getvehicleData();
            }
*/

            setHeaderData();


            locationid = extras.getString("locationid");
            Log.w(TAG,"locationid---->"+locationid);

            BookingDate = extras.getString("BookingDate");
            BookingTime = extras.getString("BookingTime");
            bookingdateandtime = extras.getString("bookingdateandtime");
            if(bookingdateandtime != null && !bookingdateandtime.isEmpty() ) {
                btn_dateandtime.setText(bookingdateandtime);
            }





        }

        btnnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, NotificationActivity.class);
                intent.putExtra("fromactivity","HomeFragment");
                startActivity(intent);
            }
        });


        if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {
            getCardDetailsResponseCall();
        }



        imgBack.setOnClickListener(this);
          ll_city.setOnClickListener(this);
          ll_proceedtopayment.setOnClickListener(this);
          rl_dateandtime.setOnClickListener(this);
          btn_dateandtime.setOnClickListener(this);

          btn_pickup.setOnClickListener(this);
          btn_selfdrop.setOnClickListener(this);
          btn_afterservice.setOnClickListener(this);
          btn_online.setOnClickListener(this);
          btn_changeaddress.setOnClickListener(this);
          btn_apply.setOnClickListener(this);
          btn_remove.setOnClickListener(this);

        bottom_navigation_view.setSelectedItemId(R.id.home);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);





    }

    @SuppressLint("SetTextI18n")
    private void setHeaderData() {

        if(city != null){
            txt_location.setText(city);
            txt_youraddress.setText(city+" ,"+street);
        }else{
            txt_location.setText("");
        }
        if(street != null){
            txt_address.setText(street);
        }else{
            txt_address.setText("");
        }







    }
    private void setVehicleData(){
        if (vehicleImage != null && !vehicleImage.isEmpty()) {
            Glide.with(CartActivity.this)
                    .load(vehicleImage)
                    .into(cv_vehicleimage);
        }
        else{
            Glide.with(CartActivity.this)
                    .load(R.drawable.logo)
                    .into(cv_vehicleimage);

        }
        if(vehicleName != null){
            txt_vehiclename.setText(vehicleName);
        }else{
            txt_vehiclename.setText("");
        }
        if(fuelType != null){
            btn_vehiclefueltype.setVisibility(View.VISIBLE);
            btn_vehiclefueltype.setText(fuelType);
        }else{
            btn_vehiclefueltype.setVisibility(View.GONE);
            btn_vehiclefueltype.setText("");
        }
        if(fuelTypeBgColor != null){
            btn_vehiclefueltype.setBackgroundColor(Color.parseColor(fuelTypeBgColor));
            btn_vehiclefueltype.setBackgroundResource(R.drawable.tags_rounded_corners);
            GradientDrawable gd = (GradientDrawable) btn_vehiclefueltype.getBackground();
            gd.setColor(Color.parseColor(fuelTypeBgColor));
            gd.setCornerRadius(10);
            gd.setStroke(4, Color.WHITE);

        }
    }
    private void setVehicleData1(){
        if (vehicleImage != null && !vehicleImage.isEmpty()) {
            Glide.with(CartActivity.this)
                    .load(vehicleImage)
                    .into(cv_vehicleimage);
        }
        else{
            Glide.with(CartActivity.this)
                    .load(R.drawable.logo)
                    .into(cv_vehicleimage);

        }
        if(Vehicle_Name != null){
            txt_vehiclename.setText(Vehicle_Name);
        }else{
            txt_vehiclename.setText("");
        }
        if(Fuel_Type_Name != null){
            btn_vehiclefueltype.setVisibility(View.VISIBLE);
            btn_vehiclefueltype.setText(Fuel_Type_Name);
        }else{
            btn_vehiclefueltype.setVisibility(View.GONE);
            btn_vehiclefueltype.setText("");
        }
        if(Fuel_Type_Background_Color != null){
            btn_vehiclefueltype.setBackgroundColor(Color.parseColor(Fuel_Type_Background_Color));
            btn_vehiclefueltype.setBackgroundResource(R.drawable.tags_rounded_corners);
            GradientDrawable gd = (GradientDrawable) btn_vehiclefueltype.getBackground();
            gd.setColor(Color.parseColor(Fuel_Type_Background_Color));
            gd.setCornerRadius(10);
            gd.setStroke(4, Color.WHITE);

        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                //active = homeFragment;
                active_tag = "1";
                callDirections(active_tag);
                break;


            case R.id.search:
                //active = searchFragment;
                active_tag = "2";
                callDirections(active_tag);
                break;


            case R.id.myvehicle:
                //active = myVehicleFragment;
                active_tag = "3";
                callDirections(active_tag);
                break;
            case R.id.cart:
                //active = cartFragment;
                active_tag = "4";
                callDirections(active_tag);
                break;
            case R.id.account:
                //active = accountFragment;
                active_tag = "5";
                callDirections(active_tag);
                break;

        }




        return true;
    };
    public void callDirections(String tag){
        Intent intent = new Intent(CartActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }


    public void getCardDetailsResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<GetCardDetailsResponse> call = apiInterface.getCardDetailsResponseCall(RestUtils.getContentType(),getCardDetailsRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<GetCardDetailsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<GetCardDetailsResponse> call, @NotNull Response<GetCardDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"GetCardDetailsResponse" + new Gson().toJson(response.body()));
                if(response.body() != null){
                    if (200 == response.body().getCode()) {
                        ll_cart_is_empty.setVisibility(View.GONE);
                        ll_content.setVisibility(View.VISIBLE);
                        ll_proceedtopayment.setVisibility(View.VISIBLE);
                        List<GetCardDetailsResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanList;
                        List<GetCardDetailsResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;
                        vehicleDetailsBeanList = response.body().getData().getVehicle_details();
                        Log.w(TAG,"GetCardDetailsResponse  vehicleDetailsBeanList"+new Gson().toJson(vehicleDetailsBeanList));
                        customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                       /* if(customerVehicleDataBeanList != null && customerVehicleDataBeanList.size()>0){
                            getvehicleDataRes(customerVehicleDataBeanList);
                        }*/

                        if (vehicleDetailsBeanList.size() > 0) {

                            for (int i = 0; i < vehicleDetailsBeanList.size(); i++) {

                                _id = vehicleDetailsBeanList.get(i).get_id();
                                Customer_id = vehicleDetailsBeanList.get(i).getCustomer_id();
                                Vehicle_Image = vehicleDetailsBeanList.get(i).getVehicle_Image();
                                Vehicletype_id = vehicleDetailsBeanList.get(i).getVehicletype_id();
                                Vehicletype_Name = vehicleDetailsBeanList.get(i).getVehicletype_Name();
                                Vehicle_Brand_id = vehicleDetailsBeanList.get(i).getVehicle_Brand_id();
                                Vehicle_Brand_Name = vehicleDetailsBeanList.get(i).getVehicle_Brand_Name();
                                Vehicle_Name_id = vehicleDetailsBeanList.get(i).getVehicle_Name_id();
                                Vehicle_Name = vehicleDetailsBeanList.get(i).getVehicle_Name();
                                Year_of_Manufacture = vehicleDetailsBeanList.get(i).getYear_of_Manufacture();
                                Vehicle_No = vehicleDetailsBeanList.get(i).getVehicle_No();
                                Fuel_Type_id = vehicleDetailsBeanList.get(i).getFuel_Type_id();
                                Fuel_Type_Name = vehicleDetailsBeanList.get(i).getFuel_Type_Name();
                                Vehicle_Model_id = vehicleDetailsBeanList.get(i).getVehicle_Model_id();
                                Vehicle_Model_Name = vehicleDetailsBeanList.get(i).getVehicle_Model_Name();
                                Status = vehicleDetailsBeanList.get(i).getStatus();
                                updatedAt  = vehicleDetailsBeanList.get(i).getUpdatedAt();
                                createdAt  = vehicleDetailsBeanList.get(i).getCreatedAt();
                                __v  = vehicleDetailsBeanList.get(i).get__v();


                                if (vehicleDetailsBeanList.get(i).getFuel_Type_Background_Color() != null) {
                                    Fuel_Type_Background_Color = vehicleDetailsBeanList.get(i).getFuel_Type_Background_Color().trim();
                                    Lubricant_Type_Background_Color = Fuel_Type_Background_Color;

                                }

                                Log.w(TAG, "vehicletypename: " + vehicletypename + "vehicletypeid: " + vehicletypeid + "Fuel_Type_Background_Color :" + Fuel_Type_Background_Color+" Vehicle_Name : "+Vehicle_Name);

                            }



                        }
                        
                        if(response.body().getLocation_available() != null && response.body().getLocation_available().size()>0) {
                            locationAvailableBeanArrayList = response.body().getLocation_available();
                            Log.w(TAG,"locationAvailableBeanArrayList : "+new Gson().toJson(locationAvailableBeanArrayList));
                            getChangedAddressDetails(locationAvailableBeanArrayList);
                        }




                        originalamout = response.body().getData().getOriginal_Price();
                        discountamout = response.body().getData().getDiscount_Price();
                        youPay = discountamout;
                        txt_subservice_originalamount.setText("\u20B9" + " " + youPay);
                        txt_subservices_discountamount.setText("\u20B9" + " " + discountamout);
                        txt_subservices_discountamount.setPaintFlags(txt_subservices_discountamount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        itemDetailsBeanList = response.body().getData().getItem_Details();

                        if (itemDetailsBeanList.size() > 0) {
                            getvehicleDataRes(vehicleDetailsBeanList);
                            rv_cartserviceslist.setVisibility(View.VISIBLE);
                            ll_proceedtopayment.setVisibility(View.VISIBLE);

                            tv_norecords.setVisibility(View.GONE);
                            setViewSubService();
                        } else {
                            getCustomerVehicleDataBeanRes(customerVehicleDataBeanList);
                            rv_cartserviceslist.setVisibility(View.GONE);
                            ll_proceedtopayment.setVisibility(View.GONE);
                            ll_content.setVisibility(View.GONE);
                            ll_cart_is_empty.setVisibility(View.VISIBLE);


                        }

                    } else {
                        List<GetCardDetailsResponse.CustomerVehicleDataBean> customerVehicleDataBeanList;
                        customerVehicleDataBeanList = response.body().getCustomerVehicleData();
                        getCustomerVehicleDataBeanRes(customerVehicleDataBeanList);


                        ll_content.setVisibility(View.GONE);
                        ll_proceedtopayment.setVisibility(View.GONE);
                        ll_cart_is_empty.setVisibility(View.VISIBLE);


                    }
                }









            }


            @Override
            public void onFailure(@NotNull Call<GetCardDetailsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"GetCardDetailsResponseflr"+t.getMessage());
            }
        });

    }

    private void gotoSubServiceActivity() {
        Intent intent = new Intent(CartActivity.this, SubServicesActivity.class);
        intent.putExtra("vehicletypeid", vehicletypeid);
        intent.putExtra("serviceid", serviceid);
        intent.putExtra("city", city);
        intent.putExtra("street", street);
        intent.putExtra("vehicleImage", vehicleImage);
        intent.putExtra("vehicleName", vehicleName);
        intent.putExtra("vehicleModelName", vehicleModelName);
        intent.putExtra("fuelType", fuelType);
        intent.putExtra("vehicletypename", vehicletypename);
        intent.putExtra("customervehicledatabeanlist", customerVehicleDataBeanList);
        intent.putExtra("servicename", servicename);
        intent.putExtra("masterservicename", masterservicename);
        intent.putExtra("selectedVehicleId", selectedVehicleId);
        intent.putExtra("twowheelervehicleid", twowheelervehicleid);
        intent.putExtra("fourwheelervehicleid", fourwheelervehicleid);
        intent.putExtra("masterserviceid", masterserviceid);
        startActivity(intent);
        finish();
    }

    private void getvehicleDataRes(List<GetCardDetailsResponse.DataBean.VehicleDetailsBean> vehicleDetailsBeanList) {

        if(vehicleDetailsBeanList != null){
            for(int i=0;i<vehicleDetailsBeanList.size();i++){
                if(selectedVehicleId != null) {
                    if (selectedVehicleId.equalsIgnoreCase(vehicleDetailsBeanList.get(i).getVehicletype_id())) {
                        _id = vehicleDetailsBeanList.get(i).get_id();
                        Customer_id = vehicleDetailsBeanList.get(i).getCustomer_id();
                        vehicleImage = vehicleDetailsBeanList.get(i).getVehicle_Image();
                        Vehicletype_id = vehicleDetailsBeanList.get(i).getVehicletype_id();
                        vehicletypeid = Vehicletype_id;
                        Vehicletype_Name = vehicleDetailsBeanList.get(i).getVehicletype_Name();
                        Vehicle_Brand_id = vehicleDetailsBeanList.get(i).getVehicle_Brand_id();
                        Vehicle_Brand_Name = vehicleDetailsBeanList.get(i).getVehicle_Brand_Name();
                        Vehicle_Name_id = vehicleDetailsBeanList.get(i).getVehicle_Name_id();
                        Vehicle_Name = vehicleDetailsBeanList.get(i).getVehicle_Name();
                        Year_of_Manufacture = vehicleDetailsBeanList.get(i).getYear_of_Manufacture();
                        Vehicle_No = vehicleDetailsBeanList.get(i).getVehicle_No();
                        Fuel_Type_id = vehicleDetailsBeanList.get(i).getFuel_Type_id();
                        Fuel_Type_Name = vehicleDetailsBeanList.get(i).getFuel_Type_Name();
                        Fuel_Type_Background_Color = vehicleDetailsBeanList.get(i).getFuel_Type_Background_Color();
                        Vehicle_Model_id = vehicleDetailsBeanList.get(i).getVehicle_Model_id();
                        Vehicle_Model_Name = vehicleDetailsBeanList.get(i).getVehicle_Model_Name();
                        Status = vehicleDetailsBeanList.get(i).getStatus();
                        updatedAt  = vehicleDetailsBeanList.get(i).getUpdatedAt();
                        createdAt  = vehicleDetailsBeanList.get(i).getCreatedAt();
                        __v  = vehicleDetailsBeanList.get(i).get__v();


                        Log.w(TAG, "getvehicleData Vehicletype_Name----->" + Vehicletype_Name + "Vehicletype_id---->" + Vehicletype_id+ " Vehicle_Name :"+Vehicle_Name+ " Fuel_Type_Name :"+Fuel_Type_Name);

                    }

                }
            }
            Log.w(TAG, "getvehicleDataRes Vehicle_Image : " + Vehicle_Image + " " + "vehicleName : " + Vehicle_Name + " " + "vehicleModelName : " + Vehicle_Model_Name + " " + "fuelType : " + Fuel_Type_Name+"fuelTypeBgColor :"+Fuel_Type_Background_Color);


            setVehicleData1();

        }
    }
    private void getCustomerVehicleDataBeanRes(List<GetCardDetailsResponse.CustomerVehicleDataBean> customerVehicleDataBeanList) {

        if(customerVehicleDataBeanList != null){
            for(int i=0;i<customerVehicleDataBeanList.size();i++){
                if(selectedVehicleId != null) {
                    if (selectedVehicleId.equalsIgnoreCase(customerVehicleDataBeanList.get(i).getVehicletype_id())) {
                        _id = customerVehicleDataBeanList.get(i).get_id();
                        Customer_id = customerVehicleDataBeanList.get(i).getCustomer_id();
                        vehicleImage = customerVehicleDataBeanList.get(i).getVehicle_Image();
                        Vehicletype_id = customerVehicleDataBeanList.get(i).getVehicletype_id();
                        Vehicletype_Name = customerVehicleDataBeanList.get(i).getVehicletype_Name();
                        Vehicle_Brand_id = customerVehicleDataBeanList.get(i).getVehicle_Brand_id();
                        Vehicle_Brand_Name = customerVehicleDataBeanList.get(i).getVehicle_Brand_Name();
                        Vehicle_Name_id = customerVehicleDataBeanList.get(i).getVehicle_Name_id();
                        vehicleName = customerVehicleDataBeanList.get(i).getVehicle_Name();
                        Year_of_Manufacture = customerVehicleDataBeanList.get(i).getYear_of_Manufacture();
                        Vehicle_No = customerVehicleDataBeanList.get(i).getVehicle_No();
                        Fuel_Type_id = customerVehicleDataBeanList.get(i).getFuel_Type_id();
                        Fuel_Type_Name = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                        fuelType = customerVehicleDataBeanList.get(i).getFuel_Type_Name();
                        fuelTypeBgColor = customerVehicleDataBeanList.get(i).getFuel_Type_Background_Color();
                        Vehicle_Model_id = customerVehicleDataBeanList.get(i).getVehicle_Model_id();
                        Vehicle_Model_Name = customerVehicleDataBeanList.get(i).getVehicle_Model_Name();
                        Status = customerVehicleDataBeanList.get(i).getStatus();
                        updatedAt = customerVehicleDataBeanList.get(i).getUpdatedAt();
                        createdAt = customerVehicleDataBeanList.get(i).getCreatedAt();
                        __v = customerVehicleDataBeanList.get(i).get__v();

                        Log.w(TAG, "getCustomerVehicleDataBeanRes Vehicletype_Name----->" + Vehicletype_Name + "Vehicletype_id---->" + Vehicletype_id);

                    }

                }
            }
            Log.w(TAG, "getCustomerVehicleDataBeanRes Vehicle_Image : " + vehicleImage + " " + "vehicleName : " + vehicleName + " " + "vehicleModelName : " + Vehicle_Model_Name + " " + "fuelType : " + fuelType+"fuelTypeBgColor :"+fuelTypeBgColor);


            setVehicleData();

        }
    }

    private void getChangedAddressDetails(ArrayList<GetCardDetailsResponse.LocationAvailableBean> locationAvailableBeanArrayList) {
        Log.w(TAG,"getChangedAddressDetails call");
        if(locationid != null && !locationid.isEmpty()){
            for(int i=0; i<locationAvailableBeanArrayList.size();i++){
                if(locationid.equalsIgnoreCase(locationAvailableBeanArrayList.get(i).get_id())){
                     _id = locationAvailableBeanArrayList.get(i).get_id();
                     City = locationAvailableBeanArrayList.get(i).getCity();
                    State = locationAvailableBeanArrayList.get(i).getState();
                    Country = locationAvailableBeanArrayList.get(i).getCountry();
                    Pincode = locationAvailableBeanArrayList.get(i).getPincode();
                    Street = locationAvailableBeanArrayList.get(i).getStreet();
                    NearBy_LandMark = locationAvailableBeanArrayList.get(i).getNearBy_LandMark();
                    Location_NickName = locationAvailableBeanArrayList.get(i).getLocation_NickName();
                    Flat_No = locationAvailableBeanArrayList.get(i).getFlat_No();
                    Customer_id = locationAvailableBeanArrayList.get(i).getCustomer_id();
                     lat = locationAvailableBeanArrayList.get(i).getLat();
                     longX = locationAvailableBeanArrayList.get(i).getLongX();
                    Location_type = locationAvailableBeanArrayList.get(i).getLocation_type();
                     Status = locationAvailableBeanArrayList.get(i).getStatus();
                    updatedAt = locationAvailableBeanArrayList.get(i).getUpdatedAt();
                   createdAt = locationAvailableBeanArrayList.get(i).getCreatedAt();
                   __v = locationAvailableBeanArrayList.get(i).get__v();



                }

                Log.w(TAG,"getChangedAddressDetails call"+"City : "+City+"Street :"+Street);

                if(City != null){
                    city = City;
                    txt_location.setText(City);
                    txt_youraddress.setText(Location_NickName+" "+City+" "+Street+" "+Pincode);
                }else{
                    txt_location.setText("");
                }
                if(Street != null){
                    street = Location_NickName+" "+Street+" "+Pincode;
                    txt_address.setText(Location_NickName+" "+City+" "+Street+" "+Pincode);
                }else{
                    txt_address.setText("");
                }
            }

        }
    }


    private GetCardDetailsRequest getCardDetailsRequest() {
        /*
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         */
        GetCardDetailsRequest getCardDetailsRequest = new GetCardDetailsRequest();
        getCardDetailsRequest.setCustomer_id(customerid);
        Log.w(TAG,"GetCardDetailsRequest"+ new Gson().toJson(getCardDetailsRequest));
        return getCardDetailsRequest;
    }

    private void setViewSubService() {
        rv_cartserviceslist.setLayoutManager(new LinearLayoutManager(this));
        rv_cartserviceslist.setItemAnimator(new DefaultItemAnimator());
        CartServiceListAdapter cartServiceListAdapter = new CartServiceListAdapter(mContext, itemDetailsBeanList, rv_cartserviceslist,CartActivity.this);
        rv_cartserviceslist.setAdapter(cartServiceListAdapter);

        cartServiceListAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > itemDetailsBeanList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }


    public void bookingCreateResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<BookingCreateResponse> call = apiInterface.bookingCreateResponseCall(RestUtils.getContentType(),bookingCreateRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<BookingCreateResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookingCreateResponse> call, @NotNull Response<BookingCreateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"BookingCreateResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if(200 == response.body().getCode()){
                    Intent intent = new Intent(CartActivity.this,BookingSummaryActivity.class);
                    intent.putExtra("amountpaid",String.valueOf(response.body().getData().getAmount_Paid()));
                    intent.putExtra("bookingid",response.body().getData().getBooking_id());
                    intent.putExtra("registrationno",response.body().getData().getRegistration_No());
                    intent.putExtra("servicedate",response.body().getData().getService_Date());
                    intent.putExtra("servicetime",response.body().getData().getService_Time());
                    intent.putExtra("service",response.body().getData().getServices());
                    intent.putExtra("subservice",response.body().getData().getSubserivces());
                    intent.putExtra("vehicledetails",response.body().getData().getVehicle_Details());
                    startActivity(intent);
                }








            }


            @Override
            public void onFailure(@NotNull Call<BookingCreateResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"BookingCreateResponseFlr"+t.getMessage());
            }
        });

    }
    private BookingCreateRequest bookingCreateRequest() {
        /*
         * Customer_Name : Dinesh
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * Customer_Phone :  6383791451
         * Customer_Address : Chennai
         *  Customer_Email : iddineshkumar@gmail.com
         * Services : AC SERVICES
         * Subserivces : Monsoon shield
         * Vehicle_Id : 5f1ee402e649907a21e1a84f
         * Vehicle_Type : Four Wheeler
         * Vehicle_No : 5218
         * Vehicle_Image : http://3.101.31.129:3000/api/uploads/e1e00e57-d3e7-42dc-99d9-6ccd7b780617.jpg
         * Vehicle_Name : AUDI A6
         * Lubricant_type : Petrol
         * Booking_Date : 2020-07-28
         * Arrival_Mode : Pickup
         * Pickup_Date : 2020-07-30
         * Pickup_Time : 2 days
         * Coupon_Code :
         * Coupon_Code_Percentage :
         * Coupon_Code_Amount :
         * Total_Amount : 1230
         * Order_Value : 1000
         * Booking_Time : 2020-07-21
         * Year_Of_Mfg : 2019
         * Lubricant_type_color
         */
        List<BookingCreateRequest.CardDetailsBean> card_details = new ArrayList<>();

        if(itemDetailsBeanList.size()>0) {
            Log.w(TAG,"itemDetailsBeanList--->"+new Gson().toJson(itemDetailsBeanList));
            for (int i = 0; i < itemDetailsBeanList.size(); i++) {
                BookingCreateRequest.CardDetailsBean cardDetailsBean = new BookingCreateRequest.CardDetailsBean();
                cardDetailsBean.setSub_ser_Spec1(itemDetailsBeanList.get(i).getSub_ser_Spec1());
                cardDetailsBean.setSub_ser_display_img(itemDetailsBeanList.get(i).getSub_ser_display_img());
                List<BookingCreateRequest.CardDetailsBean.ItemListBean> ItemList = new ArrayList<>();
                BookingCreateRequest.CardDetailsBean.ItemListBean itemListBean = new BookingCreateRequest.CardDetailsBean.ItemListBean();
                itemListBean.setTitle(itemDetailsBeanList.get(i).getItemList().get(0).getTitle());
                cardDetailsBean.setItemList(ItemList);
                cardDetailsBean.setVehicle_Name_id(itemDetailsBeanList.get(i).getVehicle_Name_id());
                cardDetailsBean.set_id(itemDetailsBeanList.get(i).get_id());
                cardDetailsBean.setService_id(itemDetailsBeanList.get(i).getService_id());
                cardDetailsBean.setSub_ser_Title(itemDetailsBeanList.get(i).getSub_ser_Title());
                cardDetailsBean.setSub_ser_image(itemDetailsBeanList.get(i).getSub_ser_image());
                cardDetailsBean.setOriginal_Price(itemDetailsBeanList.get(i).getOriginal_Price());
                cardDetailsBean.setDiscount_Price(itemDetailsBeanList.get(i).getDiscount_Price());
                cardDetailsBean.setCount_type(itemDetailsBeanList.get(i).isCount_type());
                cardDetailsBean.setCart_Status(itemDetailsBeanList.get(i).isCart_Status());
                cardDetailsBean.setCart_count(itemDetailsBeanList.get(i).getCart_count());
                cardDetailsBean.setUpdatedAt(itemDetailsBeanList.get(i).getUpdatedAt());
                cardDetailsBean.setCreatedAt(itemDetailsBeanList.get(i).getCreatedAt());
                cardDetailsBean.set__v(itemDetailsBeanList.get(i).get__v());
                card_details.add(cardDetailsBean);

            }
        }
        Log.w(TAG,"CARDDETAILS--->"+new Gson().toJson(card_details));



        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        Log.w(TAG,"Services-->"+masterservicename+" "+"Subserivces  :"+servicename);

        BookingCreateRequest bookingCreateRequest = new BookingCreateRequest();
        bookingCreateRequest.setCustomer_Name(customername);
        bookingCreateRequest.setCustomer_id(customerid);
        bookingCreateRequest.setCustomer_Phone(customerphone);
        bookingCreateRequest.setCustomer_Address(city+","+street);
        bookingCreateRequest.setCustomer_Email(customeremail);
        bookingCreateRequest.setServices("");
        bookingCreateRequest.setSubserivces("");
        bookingCreateRequest.setVehicle_Id(Vehicle_Id);
        bookingCreateRequest.setVehicle_Type(Vehicle_Type);
        bookingCreateRequest.setVehicle_No(Vehicle_No);
        bookingCreateRequest.setVehicle_Image(Vehicle_Image);
        bookingCreateRequest.setVehicle_Name(Vehicle_Name);
        bookingCreateRequest.setLubricant_type(Lubricant_type);
        bookingCreateRequest.setBooking_Date(BookingDate);
        bookingCreateRequest.setArrival_Mode(Arrival_Mode);
        bookingCreateRequest.setPickup_Date(BookingDate);
        bookingCreateRequest.setPickup_Time(BookingTime);
        bookingCreateRequest.setCoupon_Code(Coupon_Code);
        bookingCreateRequest.setCoupon_Code_Percentage(Coupon_Code_Percentage);
        bookingCreateRequest.setCoupon_Code_Amount(Coupon_Code_Amount);
        bookingCreateRequest.setTotal_Amount(youPay);
        bookingCreateRequest.setOrder_Value(originalamout);
        bookingCreateRequest.setBooking_Time(BookingTime);
        bookingCreateRequest.setYear_Of_Mfg(Year_Of_Mfg);
        bookingCreateRequest.setLubricant_type_color(Lubricant_Type_Background_Color);
        bookingCreateRequest.setTransaction_id("");
        bookingCreateRequest.setCard_details(card_details);
        Log.w(TAG,"BookingCreateRequest"+ new Gson().toJson(bookingCreateRequest));
        return bookingCreateRequest;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_city:
                //startActivity(new Intent(CartActivity.this,LocationListActivity.class));
                break;
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.ll_proceedtopayment:
                if(validateInputs()) {
                    if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {
                        //bookingCreateRequest();

                        ArrayList<BookingCreateRequest.CardDetailsBean> card_details = new ArrayList<>();

                        if(itemDetailsBeanList.size()>0) {
                            Log.w(TAG,"itemDetailsBeanList--->"+new Gson().toJson(itemDetailsBeanList));
                            for (int i = 0; i < itemDetailsBeanList.size(); i++) {
                                BookingCreateRequest.CardDetailsBean cardDetailsBean = new BookingCreateRequest.CardDetailsBean();
                                cardDetailsBean.setSub_ser_Spec1(itemDetailsBeanList.get(i).getSub_ser_Spec1());
                                cardDetailsBean.setSub_ser_display_img(itemDetailsBeanList.get(i).getSub_ser_display_img());
                                List<BookingCreateRequest.CardDetailsBean.ItemListBean> ItemList = new ArrayList<>();
                                BookingCreateRequest.CardDetailsBean.ItemListBean itemListBean = new BookingCreateRequest.CardDetailsBean.ItemListBean();
                                itemListBean.setTitle(itemDetailsBeanList.get(i).getItemList().get(0).getTitle());
                                cardDetailsBean.setItemList(ItemList);
                                cardDetailsBean.setVehicle_Name_id(itemDetailsBeanList.get(i).getVehicle_Name_id());
                                cardDetailsBean.set_id(itemDetailsBeanList.get(i).get_id());
                                cardDetailsBean.setService_id(itemDetailsBeanList.get(i).getService_id());
                                cardDetailsBean.setSub_ser_Title(itemDetailsBeanList.get(i).getSub_ser_Title());
                                cardDetailsBean.setSub_ser_image(itemDetailsBeanList.get(i).getSub_ser_image());
                                cardDetailsBean.setOriginal_Price(itemDetailsBeanList.get(i).getOriginal_Price());
                                cardDetailsBean.setDiscount_Price(itemDetailsBeanList.get(i).getDiscount_Price());
                                cardDetailsBean.setCount_type(itemDetailsBeanList.get(i).isCount_type());
                                cardDetailsBean.setCart_Status(itemDetailsBeanList.get(i).isCart_Status());
                                cardDetailsBean.setCart_count(itemDetailsBeanList.get(i).getCart_count());
                                cardDetailsBean.setUpdatedAt(itemDetailsBeanList.get(i).getUpdatedAt());
                                cardDetailsBean.setCreatedAt(itemDetailsBeanList.get(i).getCreatedAt());
                                cardDetailsBean.set__v(itemDetailsBeanList.get(i).get__v());
                                card_details.add(cardDetailsBean);

                            }
                        }
                        Log.w(TAG,"CARDDETAILS--->"+new Gson().toJson(card_details));
                        String address = city+","+street;

                        if(Fuel_Type_Name == null){
                            Fuel_Type_Name = btn_vehiclefueltype.getText().toString();
                        }

                        Intent intent = new Intent(CartActivity.this, PaymentMethodBookMechanicActivity.class);
                        intent.putExtra("customername",customername);
                        intent.putExtra("customerid",customerid);
                        intent.putExtra("customerphone",customerphone);
                        intent.putExtra("address",address);
                        intent.putExtra("customeremail",customeremail);
                        intent.putExtra("Vehicle_Id",Vehicle_Id);
                        intent.putExtra("Vehicle_Type",Vehicle_Type);
                        intent.putExtra("Vehicle_No",Vehicle_No);
                        intent.putExtra("Vehicle_Image",Vehicle_Image);
                        intent.putExtra("Vehicle_Name",Vehicle_Name);
                        intent.putExtra("Lubricant_type",Fuel_Type_Name);
                        intent.putExtra("BookingDate",BookingDate);
                        intent.putExtra("Arrival_Mode",Arrival_Mode);
                        intent.putExtra("BookingTime",BookingTime);
                        intent.putExtra("Coupon_Code",Coupon_Code);
                        intent.putExtra("Coupon_Code_Percentage",Coupon_Code_Percentage);
                        intent.putExtra("Coupon_Code_Amount",Coupon_Code_Amount);
                        intent.putExtra("youPay",youPay);
                        intent.putExtra("originalamout",originalamout);
                        intent.putExtra("Year_Of_Mfg",Year_Of_Mfg);
                        intent.putExtra("Lubricant_Type_Background_Color",Lubricant_Type_Background_Color);
                        intent.putExtra("card_details",card_details);
                        intent.putExtra("userissues",edt_userissues.getText().toString());
                        intent.putExtra("from",TAG);
                        startActivity(intent);

                        //bookingCreateResponseCall();
                    }
                }
                break;
            case R.id.rl_dateandtime:
                selectDate();
                break;
            case R.id.btn_dateandtime:
                selectDate();
                break;
            case R.id.btn_pickup:
                changeBackgroundPickUp();
                break;
            case R.id.btn_selfdrop:
                changeBackgroundSelfDrop();
                break;
            case R.id.btn_afterservice:
                changeBackgroundAfterService();
                break;
            case R.id.btn_online:
                changeBackgroundOnline();
                break;
            case R.id.btn_changeaddress:
                  gotoChooseYourAddress();
                break;

            case R.id.btn_apply:
                if(btn_apply.getText().toString().equalsIgnoreCase("Apply")){

                    Log.w(TAG,"Apply---->");
                    if(validateCouponCodeInputs()){

                        KeyboardUtils.hideKeyboard(CartActivity.this);
                        btn_apply.setText("Remove");
                        if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {
                            couponsCodeValidationResponseCall();
                        }

                    }
                }
                else{
                    Toasty.info(getApplicationContext(), getResources().getString(R.string.couponcoderemoved), Toast.LENGTH_SHORT, true).show();

                    Log.w(TAG,"Remove---->");
                    btn_apply.setText("Apply");
                    rldiscountamout.setVisibility(View.GONE);
                    edt_coupon.setText("");
                    Coupon_Code = "";
                    Coupon_Code_Amount = "";
                    Coupon_Code_Percentage = "";
                    youPay = discountamout;
                    txt_subservice_originalamount.setText("\u20B9"+" "+ youPay);


                }


              /*  if(validateCouponCodeInputs()){
                    KeyboardUtils.hideKeyboard(CartActivity.this);

                    if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {
                        couponsCodeValidationResponseCall();
                    }
                }*/
                break;

            /*case R.id.btn_remove:
                rldiscountamout.setVisibility(View.GONE);

                edt_coupon.setText("");
                Coupon_Code = "";
                Coupon_Code_Amount = "";
                Coupon_Code_Percentage = "";
                youPay = discountamout;
                txt_subservice_originalamount.setText("\u20B9"+" "+ youPay);

                break;*/




        }

    }

    private void gotoChooseYourAddress() {
        bookingdateandtime = btn_dateandtime.getText().toString();

        Intent intent = new Intent(CartActivity.this,ChooseYourAddressActivity.class);
        intent.putExtra("fromactivity",TAG);
        intent.putExtra("masterservicename",masterservicename);
        intent.putExtra("masterserviceid",masterserviceid);
        intent.putExtra("serviceid",serviceid);
        intent.putExtra("servicename",servicename);
        intent.putExtra("selectedVehicleId",selectedVehicleId);
        intent.putExtra("BookingDate",BookingDate);
        intent.putExtra("BookingTime",BookingTime);
        intent.putExtra("bookingdateandtime",bookingdateandtime);



        Log.w(TAG,"gotoChooseYourAddress : "+BookingDate+" "+BookingTime);
        startActivity(intent);
    }

    private void selectDate() {
        new CustomDateTimePicker(CartActivity.this,
                new CustomDateTimePicker.ICustomDateTimeListener() {
                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year,
                                      String monthFullName,
                                      String monthShortName,
                                      int monthNumber, int date,
                                      String weekDayFullName,
                                      String weekDayShortName, int hour24,
                                      int hour12,
                                      int min, int sec, String AM_PM,String timeslot) {

                     //   Toast.makeText(getApplicationContext(),dateSelected+", "+hour24+","+min,Toast.LENGTH_SHORT).show();
                        String entry_date = date + "-" + monthShortName + "-" + year + " " +timeslot;
                        btn_dateandtime.setText(entry_date);


                        int month1 =(monthNumber + 1);
                       /* if(day == 9 || day <9){
                            strdayOfMonth = "0"+day;
                            Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
                        }else{
                            strdayOfMonth = String.valueOf(day);
                        }*/
                        String strMonth = "";
                        if(month1 == 9 || month1 <9){
                            strMonth = "0"+month1;
                            Log.w(TAG,"Selected strMonth if-->"+strMonth);
                        }else{
                            strMonth = String.valueOf(month1);
                            Log.w(TAG,"Selected strMonth else-->"+strMonth);

                        }
                        String strdayOfMonth ="";
                        if(date == 9 || date <9){
                            strdayOfMonth = "0"+date;
                            Log.w(TAG,"Selected  dayOfMonth if-->"+strdayOfMonth);

                        }else{
                            strdayOfMonth = String.valueOf(date);
                            Log.w(TAG,"Selected  dayOfMonth else-->"+strdayOfMonth);

                        }

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        BookingDate = year+"-"+strMonth+"-"+strdayOfMonth;
                        BookingTime = timeslot;

                    }

                    @Override
                    public void onCancel() {

                    }
                }).set24HourFormat(true).setDate(Calendar.getInstance())
                .showDialog();
    }


    private boolean validateInputs() {
        String bookingdateandtime = btn_dateandtime.getText().toString();
        Log.w(TAG,"validateInputs-->"+bookingdateandtime);
        if(bookingdateandtime.isEmpty()){
            Toasty.warning(mContext,"Please select date and time" , Toast.LENGTH_SHORT, true).show();
            return false;


        }
        return true;

    }

    private boolean validateCouponCodeInputs() {
        if (edt_coupon.getText().toString().equalsIgnoreCase("")) {
          edt_coupon.setError("Please enter your coupon code");
          edt_coupon.requestFocus();
          return false;
        }
        return true;
    }


  /*  @Override
    public void onItemCheck(String subserviceid) {
        Log.w(TAG,"onItemCheck--->"+"subserviceid-->"+subserviceid);
        if(subserviceid != null){
            if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {

                removingCartResponseCall(subserviceid);
            }
        }

    }*/

    private void removingCartResponseCall(String subserviceid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<RemovingCartResponse> call = apiInterface.removingCartResponseCall(RestUtils.getContentType(),removingCartRequest(subserviceid,customerid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<RemovingCartResponse>() {
            @Override
            public void onResponse(@NotNull Call<RemovingCartResponse> call, @NotNull Response<RemovingCartResponse> response) {
                Log.w(TAG,"RemovingCartResponse"+ "--->" + new Gson().toJson(response.body()));
              avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {
                            getCardDetailsResponseCall();
                        }
                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<RemovingCartResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"RemovingCartResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private RemovingCartRequest removingCartRequest(String subserviceid, String customerid) {
        /*
         * Service_id : 5f1eaf8cab6576455baf5f72
         * Customer_id : 5f181fbc609f4e233fe26106
         */
        RemovingCartRequest removingCartRequest = new RemovingCartRequest();
        removingCartRequest.setService_id(subserviceid);
        removingCartRequest.setCustomer_id(customerid);
        Log.w(TAG,"RemovingCartRequest"+ "--->" + new Gson().toJson(removingCartRequest));
        return removingCartRequest;
    }


    private void changeBackgroundPickUp() {
        Arrival_Mode = "PickUp";
        btn_selfdrop.setBackgroundResource(R.drawable.button_rectangle_corner_gray);
        btn_pickup.setBackgroundResource(R.drawable.button_rectangle_corner);


    }
    private void changeBackgroundSelfDrop() {
        Arrival_Mode = "SelfDrop";
        btn_selfdrop.setBackgroundResource(R.drawable.button_rectangle_corner);
        btn_pickup.setBackgroundResource(R.drawable.button_rectangle_corner_gray);


    }

    private void changeBackgroundAfterService() {
        btn_online.setBackgroundResource(R.drawable.button_rectangle_corner_gray);
        btn_afterservice.setBackgroundResource(R.drawable.button_rectangle_corner);


    }
    private void changeBackgroundOnline() {
        btn_online.setBackgroundResource(R.drawable.button_rectangle_corner);
        btn_afterservice.setBackgroundResource(R.drawable.button_rectangle_corner_gray);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void couponsCodeValidationResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CouponsCodeValidationResponse> call = apiInterface.couponsCodeValidationResponseCall(RestUtils.getContentType(),couponsCodeValidationRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CouponsCodeValidationResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<CouponsCodeValidationResponse> call, @NotNull Response<CouponsCodeValidationResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"CouponsCodeValidationResponse" + new Gson().toJson(response.body()));
                assert response.body() != null;

                if(response.body().getData() != null){
                    if(200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(), getResources().getString(R.string.couponcodeapplied), Toast.LENGTH_SHORT, true).show();

                        rldiscountamout.setVisibility(View.VISIBLE);

                        Coupon_Code = edt_coupon.getText().toString();
                        valueType = response.body().getData().getValue_Type();
                        value = response.body().getData().getValue();
                        if (valueType.equalsIgnoreCase("Per")) {

                            double per = value;
                            Log.w(TAG, "per-->" + per);
                            double res = (discountamout / 100.0f) * per;
                            double coupondiscounttotalamount = discountamout - res;
                            Log.w(TAG, "coupondiscounttotalamount-->" + coupondiscounttotalamount);
                            Coupon_Code_Percentage = String.valueOf(value);
                            Coupon_Code_Amount = String.valueOf(res);
                            txt_discountamount.setText("\u20B9"+" "+res);
                            youPay = (int) coupondiscounttotalamount;
                            txt_subservice_originalamount.setText("\u20B9"+" "+ youPay);


                        }
                        else if (valueType.equalsIgnoreCase("Amt")) {
                            double per = value;
                            double coupondiscounttotalamount = discountamout - per;
                            Log.w(TAG, "coupondiscounttotalamount-->" + coupondiscounttotalamount);
                            txt_discountamount.setText("\u20B9"+" "+per);

                            Coupon_Code_Amount = String.valueOf(value);

                            youPay = (int) coupondiscounttotalamount;
                            txt_subservice_originalamount.setText("\u20B9"+" "+ youPay);



                        }

                    }else{
                        Coupon_Code = "";
                        showErrorLoading(response.body().getMessage());

                    }


                }









            }


            @Override
            public void onFailure(@NotNull Call<CouponsCodeValidationResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"CouponsCodeValidationResponseflr"+t.getMessage());
            }
        });

    }
    private CouponsCodeValidationRequest couponsCodeValidationRequest() {
        /*
         * Coupon_Code : MAC
         * Vehicle_type_id : qweqweqweqweq123123
         * Amount : 2000
         */
        CouponsCodeValidationRequest couponsCodeValidationRequest = new CouponsCodeValidationRequest();
        couponsCodeValidationRequest.setCoupon_Code(edt_coupon.getText().toString());
        couponsCodeValidationRequest.setVehicle_type_id(vehicletypeid);
        couponsCodeValidationRequest.setAmount(youPay);
        Log.w(TAG,"CouponsCodeValidationRequest"+ new Gson().toJson(couponsCodeValidationRequest));
        return couponsCodeValidationRequest;
    }

    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {
                    hideLoading();
                });




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showErrorLoadingCardDetails(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> {
                    hideLoading();
                    finish();
                });




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    @Override
    public void onItemCheck(String type, String subserviceid) {
        if(type != null){
            if(type.equalsIgnoreCase("ADD")){
                if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext()))
                    Log.w(TAG,"onItemCheck--->"+type);
                addingCartResponseCall(subserviceid);
            }
            else if(type.equalsIgnoreCase("REMOVE")){
                if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext()))
                    Log.w(TAG,"onItemCheck--->"+type);
                removingCartResponseCall(subserviceid);
            }
        }
    }


    private void addingCartResponseCall(String subserviceid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddingCartResponse> call = apiInterface.addingCartResponseCall(RestUtils.getContentType(),addingCartRequest(subserviceid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<AddingCartResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddingCartResponse> call, @NotNull Response<AddingCartResponse> response) {
                Log.w(TAG,"AddingCartResponse"+ "--->" + new Gson().toJson(response.body()));
                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        if (new ConnectionDetector(CartActivity.this).isNetworkAvailable(CartActivity.this)) {
                            getCardDetailsResponseCall();
                        }
                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<AddingCartResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"AddingCartResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AddingCartRequest addingCartRequest(String subserviceid) {

        AddingCartRequest addingCartRequest = new AddingCartRequest();
        addingCartRequest.setSub_service_id(subserviceid);
        addingCartRequest.setCustomer_id(customerid);
        addingCartRequest.setVehicletype_id(selectedVehicleId);
        List<AddingCartRequest.VehicleDetailsBean> Vehicle_details = new ArrayList<>();
        AddingCartRequest.VehicleDetailsBean vehicleDetailsBean = new AddingCartRequest.VehicleDetailsBean();
        vehicleDetailsBean.set_id(_id);
        vehicleDetailsBean.setCustomer_id(Customer_id);
        vehicleDetailsBean.setVehicle_Image(Vehicle_Image);
        vehicleDetailsBean.setVehicletype_id(Vehicletype_id);
        vehicleDetailsBean.setVehicletype_Name(Vehicletype_Name);
        vehicleDetailsBean.setVehicle_Brand_id(Vehicle_Brand_id);
        vehicleDetailsBean.setVehicle_Brand_Name(Vehicle_Brand_Name);
        vehicleDetailsBean.setVehicle_Name_id(Vehicle_Name_id);
        vehicleDetailsBean.setVehicle_Name(Vehicle_Name);
        vehicleDetailsBean.setYear_of_Manufacture(Year_of_Manufacture);
        vehicleDetailsBean.setVehicle_No(Vehicle_No);
        vehicleDetailsBean.setFuel_Type_id(Fuel_Type_id);
        vehicleDetailsBean.setFuel_Type_Name(Fuel_Type_Name);
        vehicleDetailsBean.setFuel_Type_Background_Color(Fuel_Type_Background_Color);
        vehicleDetailsBean.setFuel_Type_Background_Color(Fuel_Type_Background_Color);
        vehicleDetailsBean.setVehicle_Model_id(Vehicle_Model_id);
        vehicleDetailsBean.setVehicle_Model_Name(Vehicle_Model_Name);
        vehicleDetailsBean.setStatus(Status);
        vehicleDetailsBean.setUpdatedAt(updatedAt);
        vehicleDetailsBean.setCreatedAt(createdAt);
        vehicleDetailsBean.set__v(__v);
        Vehicle_details.add(vehicleDetailsBean);
        addingCartRequest.setVehicle_details(Vehicle_details);
        Log.w(TAG,"AddingCartRequest"+ "--->" + new Gson().toJson(addingCartRequest));
        return addingCartRequest;
    }
}