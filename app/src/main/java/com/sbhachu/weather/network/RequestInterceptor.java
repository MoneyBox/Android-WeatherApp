package com.sbhachu.weather.network;

import android.util.Log;

import com.sbhachu.weather.BuildConfig;
import com.sbhachu.weather.application.Properties;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private static final String TAG = RequestInterceptor.class.getSimpleName();

    private static RequestInterceptor requestInterceptor;

    private RequestInterceptor() {
    }

    public static RequestInterceptor getInstance() {
        if (requestInterceptor == null) {
            requestInterceptor = new RequestInterceptor();
        }
        return requestInterceptor;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request request = chain.request();
        Response response;

        final long t1 = System.nanoTime();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format(">>> HTTP %s %s", request.method().toUpperCase(), request.url()));
        }

        final HttpUrl originalHttpUrl = request.url();

        final HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("mode", Properties.JSON)
                .addQueryParameter("units", Properties.UNITS)
                .addQueryParameter("appid", Properties.API_KEY)
                .build();

        final Request updatedRequest = request.newBuilder()
                .url(url)
                .build();

        response = chain.proceed(updatedRequest);

        final long t2 = System.nanoTime();

        final long bodySize = response.body().contentLength();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format("<<< HTTP %s %s (%d bytes) in %.1fms", response.code(), response.request().url(), bodySize, (t2 - t1) / 1e6d));
        }

        return response;
    }
}

