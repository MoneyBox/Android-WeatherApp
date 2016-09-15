package com.sbhachu.weather.presentation.weather;

import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.domain.Forecast;

public interface IWeatherView {
    void updateCurrentWeather(final CurrentWeather currentWeather);

    void updateDailyWeather(final Forecast forecast);

    void updateDate(final String currentDate);

    void showError(final int titleResId, final int bodyResId);
}
