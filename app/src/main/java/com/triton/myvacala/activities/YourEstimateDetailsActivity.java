package com.triton.myvacala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.payment.PaymentMethodBookMechanicFinalPayableActivity;
import com.triton.myvacala.adapter.PopularServicesAdapter;
import com.triton.myvacala.adapter.ViewYourEstimateListAdapter;
import com.triton.myvacala.adapter.ViewYourOrderStatusAdapter;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.responsepojo.BookingHistoryResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YourEstimateDetailsActivity extends AppCompatActivity {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txt_finalbillpay)
    TextView txt_finalbillpay;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;



    String TAG = "YourEstimateDetailsActivity";



    SessionManager session;
    String name = "", customerid ="";


    @BindView(R.id.ll_viewyourestimate)
    LinearLayout ll_viewyourestimate;

    @BindView(R.id.rv_viewyourestimate)
    RecyclerView rv_viewyourestimate;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;


    @BindView(R.id.rv_yourorderstatus)
    RecyclerView rv_yourorderstatus;

    @BindView(R.id.cardview_proceedtocart)
    CardView cardview_proceedtocart;

    List<BookingHistoryResponse.DataBean.CustomerInvoiceBean> customerInvoiceBeanList;
    private SharedPreferences preferences;

    String vehicletype,bookingstatus;
    private String id;
    String finalbillpay;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_estimate_details);
        ButterKnife.bind(this);
        toolbar_title.setText(getResources().getString(R.string.yourestimatetypedetails));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        avi_indicator.setVisibility(View.GONE);
        ll_viewyourestimate.setVisibility(View.GONE);
        cardview_proceedtocart.setVisibility(View.GONE);


        session = new SessionManager(YourEstimateDetailsActivity.this);
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

        Log.w(TAG, "customerid :" + customerid);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

             finalbillpay = extras.getString("finalbillpay");
             bookingstatus = extras.getString("bookingstatus");
            vehicletype = extras.getString("vehicletype");
            id = extras.getString("id");
            Log.w(TAG,"_id : "+id);
           // ArrayList<String> customerinvoicelist = (ArrayList<String>) getIntent().getSerializableExtra("customerinvoicelist");

            customerInvoiceBeanList =  (ArrayList<BookingHistoryResponse.DataBean.CustomerInvoiceBean>) getIntent().getSerializableExtra("customerinvoicelist");


            assert customerInvoiceBeanList != null;
            if(customerInvoiceBeanList.size()>0){
                ll_viewyourestimate.setVisibility(View.VISIBLE);
                setViewYourEstimateDetails();
            }else{
                ll_viewyourestimate.setVisibility(View.GONE);
            }
            Log.w(TAG,"finalbillpay : "+finalbillpay+" "+"bookingstatus : "+bookingstatus+" "+"customerinvoicelist"+customerInvoiceBeanList.size());

            if(finalbillpay != null && !finalbillpay.isEmpty()){
                txt_finalbillpay.setText("\u20B9"+" "+finalbillpay);
            }else{
                txt_finalbillpay.setText("");
            }


            if(finalbillpay != null && finalbillpay.equalsIgnoreCase("0")){
                cardview_proceedtocart.setVisibility(View.GONE);
            }else{
                cardview_proceedtocart.setVisibility(View.VISIBLE);

            }
            cardview_proceedtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(YourEstimateDetailsActivity.this, PaymentMethodBookMechanicFinalPayableActivity.class);
                    intent.putExtra("finalbillpayamount",finalbillpay);
                    intent.putExtra("id",id);
                    startActivity(intent);

                }
            });



        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Booking completed");
        arrayList.add("Booking Accepted");
        arrayList.add("Vehicle Picked up");
        arrayList.add("Service Started");
        arrayList.add("Service Completed");
        arrayList.add("Vehicle Delivered");

        setOrderStatus(arrayList);


        bottom_navigation_view.setSelectedItemId(R.id.account);
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }//end of oncreate


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.home:
                String active_tag = "1";
                callDirections(active_tag);
                break;


            case R.id.search:
                active_tag = "2";
                callDirections(active_tag);
                break;


            case R.id.myvehicle:
                active_tag = "3";
                callDirections(active_tag);
                break;
            case R.id.cart:
                active_tag = "4";
                callDirections(active_tag);
                break;
            case R.id.account:
                active_tag = "5";
                callDirections(active_tag);
                break;

        }
        return true;
    };
    public void callDirections(String tag){
        Intent intent = new Intent(YourEstimateDetailsActivity.this,DashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    private void setOrderStatus(ArrayList<String> arrayList) {
        rv_yourorderstatus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_yourorderstatus.setItemAnimator(new DefaultItemAnimator());
        ViewYourOrderStatusAdapter viewYourOrderStatusAdapter = new ViewYourOrderStatusAdapter(getApplicationContext(), arrayList, rv_yourorderstatus,vehicletype,bookingstatus);
        rv_yourorderstatus.setAdapter(viewYourOrderStatusAdapter);

        viewYourOrderStatusAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > arrayList.size()) {
                Log.w(TAG,"haint"+"Load More");
            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void setViewYourEstimateDetails() {
        Log.w(TAG,"customerInvoiceBeanList-------------->"+customerInvoiceBeanList.size());
        rv_viewyourestimate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_viewyourestimate.setItemAnimator(new DefaultItemAnimator());
        ViewYourEstimateListAdapter viewYourEstimateListAdapter = new ViewYourEstimateListAdapter(getApplicationContext(), customerInvoiceBeanList, rv_viewyourestimate);
        rv_viewyourestimate.setAdapter(viewYourEstimateListAdapter);

        viewYourEstimateListAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > customerInvoiceBeanList.size()) {
                Log.w(TAG,"haint"+"Load More");
            }


        });






    }

}

