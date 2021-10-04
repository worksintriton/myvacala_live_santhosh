package com.triton.myvacala.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.activities.parking.DashboardParkingActivity;
import com.triton.myvacala.adapter.BookingHistoryListAdapter;

import com.triton.myvacala.api.APIClient;
import com.triton.myvacala.api.RestApiInterface;
import com.triton.myvacala.appUtils.ApplicationData;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.requestpojo.ParkingBookingGetListRequest;

import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;
import com.triton.myvacala.sessionmanager.SessionManager;
import com.triton.myvacala.utils.ConnectionDetector;
import com.triton.myvacala.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingHistoryFragmentParking extends Fragment  {

    private static final String TAG = "BookingHistoryFragmentParking" ;
    private Context mContext;
    private ApplicationData applicationData;
    private SharedPreferences preferences;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    @BindView(R.id.rv_bookinghistory)
    RecyclerView rv_bookinghistory;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout ;

    @BindView(R.id.imgBack)
    ImageView imgBack;


    SessionManager session;
    String name,customerid;


    private List<ParkingBookingGetListResponse.DataBean> bookingHistoryResponseList  = null;
    BookingHistoryListAdapter bookingHistoryListAdapter;




    public BookingHistoryFragmentParking() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        customerid = user.get(SessionManager.KEY_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_parking_booking_history, container, false);
        applicationData = (ApplicationData) Objects.requireNonNull(getActivity()).getApplication();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        toolbar_title.setText(getResources().getString(R.string.bookinghistory));




        if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
            parkingBookingGetListResponseCall();
        }


        swipeRefreshLayout.setOnRefreshListener(() -> {
            // cancel the Visual indication of a refresh
            swipeRefreshLayout.setRefreshing(false);

            if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                parkingBookingGetListResponseCall();
            }

        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDirections("1");

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void callDirections(String tag){
        Intent intent = new Intent(mContext, DashboardParkingActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);


    }




    @SuppressLint("LongLogTag")
    public void parkingBookingGetListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ParkingBookingGetListResponse> call = apiInterface.parkingBookingGetListResponseCall(RestUtils.getContentType(),parkingBookingGetListRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ParkingBookingGetListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ParkingBookingGetListResponse> call, @NotNull Response<ParkingBookingGetListResponse> response) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "ParkingBookingGetListResponse" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        bookingHistoryResponseList = response.body().getData();
                        Log.w(TAG, "Size--->" + bookingHistoryResponseList.size());

                        if (response.body().getData().isEmpty()) {
                            rv_bookinghistory.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText("No booking history");
                        } else {
                            tv_norecords.setVisibility(View.GONE);
                            rv_bookinghistory.setVisibility(View.VISIBLE);
                            setView();
                        }

                    } else {
                        rv_bookinghistory.setVisibility(View.GONE);
                        tv_norecords.setVisibility(View.VISIBLE);


                    }
                }
            }






            @Override
            public void onFailure(@NotNull Call<ParkingBookingGetListResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ParkingBookingGetListResponseflr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private ParkingBookingGetListRequest parkingBookingGetListRequest() {
        /*
         * Customer_id : 5f1fae9bbcd5650a5ab130e8
         * date_time :date_time : 2020-10-10 12:00 PM
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        String currentDateandTime = sdf.format(new Date());

        ParkingBookingGetListRequest parkingBookingGetListRequest = new ParkingBookingGetListRequest();
        parkingBookingGetListRequest.setCustomer_id(customerid);
        parkingBookingGetListRequest.setDate_time(currentDateandTime);

        Log.w(TAG," ParkingBookingGetListRequest"+ new Gson().toJson(parkingBookingGetListRequest));
        return parkingBookingGetListRequest;
    }

    private void setView() {
        //rv_locationlist.setLayoutManager(new GridLayoutManager(this, 3));

        rv_bookinghistory.setLayoutManager(new LinearLayoutManager(mContext));
        rv_bookinghistory.setItemAnimator(new DefaultItemAnimator());
        bookingHistoryListAdapter = new BookingHistoryListAdapter(mContext,bookingHistoryResponseList,rv_bookinghistory);
        rv_bookinghistory.setAdapter(bookingHistoryListAdapter);

        bookingHistoryListAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > bookingHistoryResponseList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }







}
