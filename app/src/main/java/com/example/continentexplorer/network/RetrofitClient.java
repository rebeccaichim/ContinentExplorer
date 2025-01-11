package com.example.continentexplorer.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // Add logging interceptor
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.130:8081/api/")
                    .client(client) // Use the client with logging
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        System.out.println("=== RetrofitClient Initialized ===");
        System.out.println("Base URL: http://192.168.1.130:8081/api/");

        return retrofit;
    }
}
