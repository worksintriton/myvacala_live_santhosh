package com.triton.myvacala.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class APIClient {

    /*https://myvacala.com/api/mobilesplashscreen/mobile/getlist*/

    private static Retrofit retrofit = null;
    private static OkHttpClient client;

   // public static String BASE_URL = "http://myvacala.com:3000";
    //public static String BASE_URL = "http://3.101.31.129:3000";
    public static String BASE_URL = "https://myvacala.com/api/";

    public static String selectedVehicleTypeId;




   // public static String BASE_URL = "http://15.207.51.203:3000";
   // public static String BASE_URL = "http://3.101.31.129:3000";
    public static Retrofit getClient() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .cache(null)
                .build();
                retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
