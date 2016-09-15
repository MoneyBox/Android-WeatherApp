package com.sbhachu.weather.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sbhachu.weather.application.Properties;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();

    private static ApiClient instance;
    private IApiTemplate template;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }

        return instance;
    }

    private ApiClient() {
        final RequestInterceptor interceptor = RequestInterceptor.getInstance();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Properties.TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();

        final Gson gson = new GsonBuilder()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Properties.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        template = retrofit.create(IApiTemplate.class);
    }

    public IApiTemplate getTemplate() {
        return template;
    }
}


