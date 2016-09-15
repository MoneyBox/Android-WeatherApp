package com.sbhachu.weather.model.domain;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("city")
    private City city;

    @SerializedName("list")
    private List<ForecastData> forecastDataList;

    public City getCity() {
        return city;
    }

    public List<ForecastData> getForecastDataList() {
        return forecastDataList;
    }

    @VisibleForTesting
    public Forecast(City city, List<ForecastData> forecastDataList) {
        this.city = city;
        this.forecastDataList = forecastDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast)) return false;

        Forecast forecast = (Forecast) o;

        if (city != null ? !city.equals(forecast.city) : forecast.city != null) return false;
        return forecastDataList != null ? forecastDataList.equals(forecast.forecastDataList) : forecast.forecastDataList == null;

    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (forecastDataList != null ? forecastDataList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "city=" + city +
                ", forecastDataList=" + forecastDataList +
                '}';
    }
}
