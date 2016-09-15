package com.sbhachu.weather.model.interactor;

import com.sbhachu.weather.model.domain.CurrentWeather;

public interface ICurrentWeatherInteractor {

    void getCurrentWeather(final String city, final String countryCode, final Listener listener);

    interface Listener {
        void onCurrentWeatherSuccess(final CurrentWeather currentWeather);

        void onCurrentWeatherFailure();
    }
}
