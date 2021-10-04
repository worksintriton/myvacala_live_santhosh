package com.triton.myvacala.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.triton.myvacala.R;
import com.triton.myvacala.appUtils.ApplicationData;
import com.triton.myvacala.utils.ConnectionDetector;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class SearchFragment extends Fragment  {







    private Context mContext;
    private ApplicationData applicationData;
    private SharedPreferences preferences;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        applicationData = (ApplicationData) Objects.requireNonNull(getActivity()).getApplication();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();





        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }







}
