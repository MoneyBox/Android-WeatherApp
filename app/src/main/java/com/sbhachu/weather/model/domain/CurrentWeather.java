package com.sbhachu.weather.model.domain;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather {

    @SerializedName("weather")
    private List<Weather> weather;

    
    @SerializedName("main")
    private WeatherData weatherData;

    @SerializedName("name")
    private String name;

    public List<Weather> getWeather() {
        return weather;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public String getName() {
        return name;
    }

    @VisibleForTesting
    public CurrentWeather(List<Weather> weather, WeatherData weatherData, String name) {
        this.weather = weather;
        this.weatherData = weatherData;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentWeather)) return false;

        CurrentWeather that = (CurrentWeather) o;

        if (weather != null ? !weather.equals(that.weather) : that.weather != null) return false;
        if (weatherData != null ? !weatherData.equals(that.weatherData) : that.weatherData != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = weather != null ? weather.hashCode() : 0;
        result = 31 * result + (weatherData != null ? weatherData.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "weather=" + weather +
                ", weatherData=" + weatherData +
                ", name='" + name + '\'' +
                '}';
    }
}
