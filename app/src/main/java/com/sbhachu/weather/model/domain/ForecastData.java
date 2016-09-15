package com.sbhachu.weather.model.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastData {

    @SerializedName("dt")
    private Long date;

    @SerializedName("temp")
    private Temperature temperature;

    @SerializedName("weather")
    private List<Weather> weather;

    public Long getDate() {
        return date;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForecastData)) return false;

        ForecastData that = (ForecastData) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (temperature != null ? !temperature.equals(that.temperature) : that.temperature != null)
            return false;
        return weather != null ? weather.equals(that.weather) : that.weather == null;

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ForecastData{" +
                "date=" + date +
                ", temperature=" + temperature +
                ", weather=" + weather +
                '}';
    }
}
