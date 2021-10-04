package com.triton.myvacala.activities.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.triton.myvacala.R;

public class DateandTimePickerActivity extends AppCompatActivity {
    DatePicker picker;
    Button displayDate;
    TextView textview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker_parking_rounded_bottomsheetdialog);



    }


}