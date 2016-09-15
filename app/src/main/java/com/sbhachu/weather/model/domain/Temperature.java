package com.sbhachu.weather.model.domain;

import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("min")
    private Double minTemperature;

    @SerializedName("max")
    private Double maxTemperature;

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Temperature)) return false;

        Temperature that = (Temperature) o;

        if (minTemperature != null ? !minTemperature.equals(that.minTemperature) : that.minTemperature != null)
            return false;
        return maxTemperature != null ? maxTemperature.equals(that.maxTemperature) : that.maxTemperature == null;

    }

    @Override
    public int hashCode() {
        int result = minTemperature != null ? minTemperature.hashCode() : 0;
        result = 31 * result + (maxTemperature != null ? maxTemperature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                '}';
    }
}
