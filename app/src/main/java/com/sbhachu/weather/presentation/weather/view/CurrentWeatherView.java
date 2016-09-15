package com.sbhachu.weather.presentation.weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.domain.Weather;
import com.sbhachu.weather.model.domain.WeatherData;
import com.sbhachu.weather.util.WeatherImageUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentWeatherView extends LinearLayout {

    private TextView cityTextView;
    private TextView temperatureTextView;
    private TextView statusTextView;

    public CurrentWeatherView(Context context) {
        this(context, null, 0);
    }

    public CurrentWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CurrentWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialiseView();
    }

    private void initialiseView() {
        final View root = LayoutInflater.from(getContext()).inflate(R.layout.view_current_weather, this);

        cityTextView = (TextView) root.findViewById(R.id.tv_city);
        temperatureTextView = (TextView) root.findViewById(R.id.tv_temperature);
        statusTextView = (TextView) root.findViewById(R.id.tv_status);
    }

    public void bindData(final CurrentWeather currentWeather) {
        cityTextView.setText(currentWeather.getName());

        final WeatherData weatherData = currentWeather.getWeatherData();
        temperatureTextView.setText(String.format(Locale.getDefault(), "%d°C", weatherData.getTemperature().intValue()));

        final Weather weather = currentWeather.getWeather().get(0);
        temperatureTextView.setCompoundDrawablesWithIntrinsicBounds(WeatherImageUtil.getImageResourceID(weather.getIcon()), 0, 0, 0);
        statusTextView.setText(weather.getStatus());
    }
}