package com.jennifer.easyorder.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitReniec {

    public static Retrofit instance;

    //https://dniruc.apisperu.com/api/v1/dni/76639920?
    // token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFuZHJlc3BhcmphdEBnbWFpbC5jb20ifQ.A4kk76bROCy44OoQZZqfLo27rG3rqg1HK6GXZUcjvuI

    public  static Retrofit getInstance(){
        if (instance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://dniruc.apisperu.com/api/v1/dni/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getLoggingBuilder().build())
                    .build();
            instance = retrofit;
        }
        return instance;
    }

    public static OkHttpClient.Builder getLoggingBuilder(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        return builder;
    }

}
