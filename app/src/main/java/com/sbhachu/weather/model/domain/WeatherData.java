package com.sbhachu.weather.model.domain;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("temp")
    private Double temperature;

    @SerializedName("temp_min")
    private Double temperatureMin;

    @SerializedName("temp_max")
    private Double temperatureMax;

    public Double getTemperature() {
        return temperature;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherData)) return false;

        WeatherData that = (WeatherData) o;

        if (temperature != null ? !temperature.equals(that.temperature) : that.temperature != null)
            return false;
        if (temperatureMin != null ? !temperatureMin.equals(that.temperatureMin) : that.temperatureMin != null)
            return false;
        return temperatureMax != null ? temperatureMax.equals(that.temperatureMax) : that.temperatureMax == null;

    }

    @Override
    public int hashCode() {
        int result = temperature != null ? temperature.hashCode() : 0;
        result = 31 * result + (temperatureMin != null ? temperatureMin.hashCode() : 0);
        result = 31 * result + (temperatureMax != null ? temperatureMax.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "temperature=" + temperature +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                '}';
    }
}
