package com.triton.myvacala.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.triton.myvacala.R;

public class TermsAndConditionsActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "TermsAndConditionsActivity";

    ImageView imgBack;

    TextView toolbar_title;

    WebView wv_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        Log.w(TAG,"onCreate");

        toolbar_title=findViewById(R.id.toolbar_title);

        imgBack=findViewById(R.id.imgBack);

        wv_terms=findViewById(R.id.wv_terms);

        toolbar_title.setText(getResources().getString(R.string.privacy));

        String url = "https://myvacala.com/api/uploads/a11c0db0-725a-4d7c-9805-738c30395d08.pdf";

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        startActivity(browserIntent);

        imgBack.setOnClickListener(this);

    }

    public void loadurl(String urlParams)
    {

        wv_terms.setWebViewClient(new MyWebViewClient());

        wv_terms.getSettings().setJavaScriptEnabled(true);

        wv_terms.loadUrl("http://docs.google.com/gview?embedded=true&url="  + urlParams);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgBack:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    private static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}