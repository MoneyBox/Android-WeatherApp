package com.sbhachu.weather.presentation.weather.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.domain.Forecast;
import com.sbhachu.weather.model.domain.ForecastData;
import com.sbhachu.weather.presentation.common.view.HorizontalDividerItemDecoration;
import com.sbhachu.weather.presentation.weather.adapter.DailyWeatherAdapter;

import java.util.List;

public class DailyWeatherView extends LinearLayout {

    private RecyclerView dailyWeatherRecyclerView;
    private DailyWeatherAdapter adapter;

    public DailyWeatherView(Context context) {
        this(context, null, 0);
    }

    public DailyWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DailyWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialiseView();
    }

    private void initialiseView() {
        final View root = LayoutInflater.from(getContext()).inflate(R.layout.view_daily_weather, this);

        dailyWeatherRecyclerView = (RecyclerView) root.findViewById(R.id.rv_daily_weather);
        dailyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        final Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        dailyWeatherRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(dividerDrawable));

        adapter = new DailyWeatherAdapter(getContext());

        dailyWeatherRecyclerView.setAdapter(adapter);
    }

    public void bindData(Forecast forecast) {
        final List<ForecastData> forecastDataList = forecast.getForecastDataList();
        adapter.setItems(forecastDataList);
        adapter.notifyDataSetChanged();
    }
}
