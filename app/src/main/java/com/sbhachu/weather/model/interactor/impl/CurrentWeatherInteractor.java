package com.sbhachu.weather.model.interactor.impl;

import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.interactor.ICurrentWeatherInteractor;
import com.sbhachu.weather.network.ApiClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentWeatherInteractor implements ICurrentWeatherInteractor {

    private static final String TAG = CurrentWeatherInteractor.class.getSimpleName();

    public void getCurrentWeather(final String city, final String countryCode, final Listener listener) {
        final String location = String.format(Locale.getDefault(), "%s,%s", city, countryCode);

        final Call<CurrentWeather> currentWeatherRequest = ApiClient.getInstance().getTemplate().getCurrentWeather(location);
        currentWeatherRequest.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if (response.body() != null) {
                    final CurrentWeather currentWeather = response.body();
                    if (listener != null) {
                        listener.onCurrentWeatherSuccess(currentWeather);
                    }
                } else {
                    listener.onCurrentWeatherFailure();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                if (listener != null) {
                    listener.onCurrentWeatherFailure();
                }
            }
        });
    }
}
