package com.triton.myvacala.utils;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.triton.myvacala.R;
import com.triton.myvacala.activities.LoginActivity;
import com.triton.myvacala.activities.SliderActivity;
import com.triton.myvacala.sessionmanager.SessionManager;


/**
 * Created by Iddinesh.
 */

public class Permission_Activity extends AppCompatActivity {

    Button permission_btn;

    Intent thisIntent;
    private  String TAG ="Permission_Activity";

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String[] permissionString = {
            Manifest.permission.RECEIVE_SMS,
            "check"};

    SessionManager session;

    String user_level="";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        session = new SessionManager(Permission_Activity.this);
        session.checkLogin();
        boolean islogedin = session.isLoggedIn();
        Log.w(TAG,"islogedin-->"+islogedin);

        setContentView(R.layout.activity_permission);






        thisIntent = getIntent();

        permission_btn = findViewById(R.id.permission_btn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Window window = Permission_Activity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Permission_Activity.this.getResources().getColor(R.color.myvacalabutton));
        }
        if(!islogedin) {
            insertmappermission();
        }else{
            startActivity(new Intent(getApplicationContext(), SliderActivity.class));
            finish();

        }



        permission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), SliderActivity.class));
                finish();

            }
        });




    }

    private void insertmappermission() {

        int haslocationpermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            haslocationpermission = checkSelfPermission(Manifest.permission.RECEIVE_SMS);

            if (haslocationpermission != PackageManager.PERMISSION_GRANTED) {

                if (!shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {

                    requestPermissions(permissionString, REQUEST_CODE_ASK_PERMISSIONS);
                    /*showMessageOKCancel(
                            (dialog, which) ->);*/

                    return;
                }
                requestPermissions(permissionString,
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

    }
    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(Permission_Activity.this)
                .setMessage("Allow permission to access this location ?")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getApplicationContext(), SliderActivity.class));
                finish();
                // Permission Granted



            } else {
                // Permission Denied
              /*  Toast.makeText(Permission_Activity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                        .show();*/
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}