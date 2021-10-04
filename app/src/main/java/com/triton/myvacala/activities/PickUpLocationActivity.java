package com.triton.myvacala.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.triton.myvacala.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickUpLocationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.btn_selectcity)
    Button btn_selectcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_location);
        String TAG = "PickUpLocationActivity";
        Log.w(TAG,"onCreate--->");

        ButterKnife.bind(this);
        toolbar_title.setText(getResources().getString(R.string.pickuplocation));
        imgBack.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_selectcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PickUpLocationActivity.this,SelectYourCityActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        finish();*/
    }
}