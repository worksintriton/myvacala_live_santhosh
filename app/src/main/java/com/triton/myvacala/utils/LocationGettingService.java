package com.triton.myvacala.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;


import com.triton.myvacala.locationservicedata.LocationServiceInput;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class LocationGettingService extends Service {
    private static final String TAG = "MyLocationService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    LoginManagerLoc loginManagerLoc;
    String userId="",type="",c_lat="",c_lon="";
    public static Location mlocation;
    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            mlocation=location;
             // DriverCurrentStatus("http://callistaindia.com/demo/taxi/MotoUrl/driver_lat_long.php?driver_id="+driverId+"&driver_latitude="+location.getLatitude()+"&driver_longtitude="+location.getLongitude());

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };

    public LocationGettingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        loginManagerLoc = new LoginManagerLoc(getBaseContext());
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        Log.e(TAG, "onCreate");
        loginManagerLoc = new LoginManagerLoc(getBaseContext());
        initializeLocationManager();

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
//Declare the timer
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                      @Override
                      public void run() {
                          //Called each time when 1000 milliseconds (1 second) (the period parameter)
                          if(mlocation!=null) {

                              c_lat= String.valueOf(mlocation.getLatitude());
                              c_lon= String.valueOf(mlocation.getLongitude());
                          }
                          if( loginManagerLoc.isLoggedIn() && (mlocation != null))
                          makeLogin();
                      }

                  },
//Set how long before to start calling themlocation TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                1000*10);

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listener, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: "+ LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


    public void makeLogin(){

        Log.v("locatoin", "made");
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());


        LocationServiceInput input = new LocationServiceInput();
        input.setEmployeeId(""+loginManagerLoc.getUser().getId());
        input.setUpdatedAt(""+formattedDate);
        input.setLat(mlocation.getLatitude());
        input.setLong(mlocation.getLongitude());
        input.setName(""+loginManagerLoc.getUser().getName().toString());
       // Call<LocationServiceOutput> call = stationClient.locationUpdate(input);

     /*   call.enqueue(new Callback<LocationServiceOutput>() {
            @Override
            public void onResponse(Call<LocationServiceOutput> call, Response<LocationServiceOutput> response) {
                LocationServiceOutput  login = response.body();

            }

            @Override
            public void onFailure(Call<LocationServiceOutput> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });*/
    }




}
