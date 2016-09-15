package com.sbhachu.weather.model.interactor.impl;

import com.sbhachu.weather.model.domain.Forecast;
import com.sbhachu.weather.model.interactor.IDailyWeatherInteractor;
import com.sbhachu.weather.network.ApiClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyWeatherInteractor implements IDailyWeatherInteractor {

    public void getDailyWeather(final String city, final String countryCode, final Listener listener) {
        final String location = String.format(Locale.getDefault(), "%s,%s", city, countryCode);

        final Call<Forecast> dailyWeatherRequest = ApiClient.getInstance().getTemplate().getDailyWeather(location);
        dailyWeatherRequest.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {

                if (response.body() != null) {
                    final Forecast forecast = response.body();
                    if (listener != null) {
                        listener.onDailyWeatherSuccess(forecast);
                    }
                } else {
                    listener.onDailyWeatherFailure();
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                if (listener != null) {
                    listener.onDailyWeatherFailure();
                }
            }
        });
    }
}
