package com.sbhachu.weather.model.interactor;

import com.sbhachu.weather.model.domain.Forecast;

public interface IDailyWeatherInteractor {

    void getDailyWeather(final String city, final String countryCode, final Listener listener);

    interface Listener {
        void onDailyWeatherSuccess(final Forecast forecast);

        void onDailyWeatherFailure();
    }
}
