package com.sbhachu.weather.presentation.weather.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.domain.ForecastData;
import com.sbhachu.weather.util.WeatherImageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DailyWeatherItemView extends LinearLayout {

    private TextView maxTempTextView;
    private TextView minTempTextView;
    private ImageView iconImageView;
    private TextView dayTextView;

    public DailyWeatherItemView(Context context) {
        super(context);
        initialiseView();
    }

    private void initialiseView() {
        final View root = LayoutInflater.from(getContext()).inflate(R.layout.view_daily_weather_item, this);
        maxTempTextView = (TextView) root.findViewById(R.id.tv_max_temp);
        minTempTextView = (TextView) root.findViewById(R.id.tv_min_temp);
        iconImageView = (ImageView) root.findViewById(R.id.iv_icon);
        dayTextView = (TextView) root.findViewById(R.id.tv_day);
    }

    public void bindData(ForecastData forecastData) {
        maxTempTextView.setText(String.format(Locale.getDefault(), "%d°C", forecastData.getTemperature().getMaxTemperature().intValue()));
        minTempTextView.setText(String.format(Locale.getDefault(), "%d°C", forecastData.getTemperature().getMinTemperature().intValue()));
        iconImageView.setImageResource(WeatherImageUtil.getImageResourceID(forecastData.getWeather().get(0).getIcon()));

        final SimpleDateFormat formatter = new SimpleDateFormat("EEE", Locale.getDefault());
        final String day = formatter.format(new Date(forecastData.getDate() * 1000));
        dayTextView.setText(day);
    }
}
