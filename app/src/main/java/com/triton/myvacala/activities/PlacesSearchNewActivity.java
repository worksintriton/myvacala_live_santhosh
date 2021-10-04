package com.triton.myvacala.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.myvacala.R;
import com.triton.myvacala.adapter.PlacesResultsAdapter;
import com.triton.myvacala.adapter.PlacesResultsNewAdapter;
import com.triton.myvacala.api.API;
import com.triton.myvacala.appUtils.Constants;
import com.triton.myvacala.interfaces.PlacesNameListener;
import com.triton.myvacala.responsepojo.AddressResultsResponse;
import com.triton.myvacala.responsepojo.PlacesResultsResponse;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesSearchNewActivity extends AppCompatActivity implements PlacesNameListener {

    String TAG = "PlacesSearchNewActivity";


    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.edt_placessearch)
    EditText edtPlacesSearch;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.img_close)
    ImageView img_close;


    @BindView(R.id.rv_placesresults)
    RecyclerView rv_placesresults;

    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    private Context mContext;
    private SharedPreferences preferences;

    private List<PlacesResultsResponse.PredictionsBean> predictionsBeanList;

    String placesresults = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_search);
        Log.w(TAG,"onCreate--->");

        ButterKnife.bind(this);
        mContext = PlacesSearchNewActivity.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        edtPlacesSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(edtPlacesSearch, InputMethodManager.SHOW_IMPLICIT);


        avi_indicator.setVisibility(View.GONE);
        img_close.setVisibility(View.GONE);
        imgBack.setOnClickListener(v -> onBackPressed());


        img_close.setOnClickListener(v -> {

            if(placesresults != null && !placesresults.isEmpty()){
                edtPlacesSearch.setText("");
                img_close.setVisibility(View.GONE);
            }

        });



        edtPlacesSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.w(TAG,"beforeTextChanged-->"+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(TAG,"onTextChanged-->"+s.toString());
                placesresults = s.toString();
                placesSearchResponseCall(s.toString());
                if(placesresults != null && !placesresults.isEmpty()){
                    img_close.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG,"afterTextChanged-->"+s.toString());
              //  placesSearchResponseCall(s.toString());

            }
        });



    }

    private void placesSearchResponseCall(String places) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);

        String key = API.MAP_KEY;
        service.getCityResults(places, key).enqueue(new Callback<PlacesResultsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlacesResultsResponse> call, @NotNull Response<PlacesResultsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"placesSearchResponseCall" + new Gson().toJson(response.body()));


                assert response.body() != null;
                predictionsBeanList = response.body().getPredictions();
                    if (predictionsBeanList.size() > 0 ) {
                        rv_placesresults.setVisibility(View.VISIBLE);
                        tv_norecords.setVisibility(View.GONE);
                        setViewPlacesResulsts();
                    }else{
                        rv_placesresults.setVisibility(View.GONE);
                        tv_norecords.setVisibility(View.VISIBLE);
                    }


            }

            @Override
            public void onFailure(@NotNull Call<PlacesResultsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }

    private void setViewPlacesResulsts() {
        rv_placesresults.setLayoutManager(new LinearLayoutManager(this));
        rv_placesresults.setItemAnimator(new DefaultItemAnimator());
        PlacesResultsNewAdapter placesResultsNewAdapter = new PlacesResultsNewAdapter(mContext, predictionsBeanList, rv_placesresults, PlacesSearchNewActivity.this);
        rv_placesresults.setAdapter(placesResultsNewAdapter);

        placesResultsNewAdapter.setOnLoadMoreListener(() -> {
            if (preferences.getInt(Constants.INBOX_TOTAL, 0) > predictionsBeanList.size()) {
                Log.e("haint", "Load More");
            }


        });







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void selectedPlacesName(String PlacesName,String selectedPlaceName) {
        addressResponseCall(PlacesName,selectedPlaceName);
    }

    private void addressResponseCall(String PlacesName, String selectedPlaceName) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);

        String key = API.MAP_KEY;
        service.getaddressResults(PlacesName, key).enqueue(new Callback<AddressResultsResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddressResultsResponse> call, @NotNull Response<AddressResultsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"addressResponseCall" + new Gson().toJson(response.body()));


                assert response.body() != null;
                 List<AddressResultsResponse.ResultsBean> resultsBeanList = response.body().getResults();
                if (resultsBeanList.size() > 0 ) {
                    double lat = resultsBeanList.get(0).getGeometry().getLocation().getLat();
                    double lon = resultsBeanList.get(0).getGeometry().getLocation().getLng();

                    Log.w(TAG,"lat-->"+lat+" lon : "+lon);

                    Intent i = new Intent(PlacesSearchNewActivity.this, MapsActivity.class);
                    i.putExtra("cityname",selectedPlaceName);
                    Bundle b = new Bundle();
                    b.putDouble("lat", lat);
                    b.putDouble("lon", lon);
                    i.putExtras(b);
                    startActivity(i);
                }


            }

            @Override
            public void onFailure(@NotNull Call<AddressResultsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }
}