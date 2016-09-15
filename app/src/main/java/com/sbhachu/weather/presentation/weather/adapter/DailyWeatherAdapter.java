package com.sbhachu.weather.presentation.weather.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.sbhachu.weather.model.domain.ForecastData;
import com.sbhachu.weather.presentation.common.adapter.BaseRecyclerAdapter;
import com.sbhachu.weather.presentation.common.view.BaseRecyclerView;
import com.sbhachu.weather.presentation.weather.view.DailyWeatherItemView;

public class DailyWeatherAdapter extends BaseRecyclerAdapter<ForecastData, DailyWeatherItemView> {

    private Context context;

    public DailyWeatherAdapter(final Context context) {
        this.context = context;
    }

    @Override
    protected DailyWeatherItemView onCreateItemView(ViewGroup parent, int viewType) {
        return new DailyWeatherItemView(context);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerView<DailyWeatherItemView> holder, int position) {
        final DailyWeatherItemView view = holder.getView();
        final ForecastData forecastData = getItems().get(position);

        view.bindData(forecastData);
    }
}
