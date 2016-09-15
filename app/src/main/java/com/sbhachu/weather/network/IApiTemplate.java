package com.sbhachu.weather.network;

import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.domain.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiTemplate {

    @GET("weather")
    Call<CurrentWeather> getCurrentWeather(@Query("q") final String location);

    @GET("forecast")
    Call<Forecast> getHourlyWeather(@Query("q") final String location);

    @GET("forecast/daily")
    Call<Forecast> getDailyWeather(@Query("q") final String location);
}
