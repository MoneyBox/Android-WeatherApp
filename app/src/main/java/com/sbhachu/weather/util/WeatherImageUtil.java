package com.sbhachu.weather.util;

import com.sbhachu.weather.R;

public class WeatherImageUtil {

    public static int getImageResourceID(String imageReference) {
        switch (imageReference) {
            case "01d":
                return R.drawable.clear_day;
            case "01n":
                return R.drawable.clear_night;
            case "02d":
            case "03d":
            case "04d":
                return R.drawable.clouds_day;
            case "02n":
            case "03n":
            case "04n":
                return R.drawable.clouds_night;
            case "09d":
            case "10d":
                return R.drawable.rain_day;
            case "09n":
            case "10n":
                return R.drawable.rain_night;
            case "11d":
                return R.drawable.storm_day;
            case "11n":
                return R.drawable.storm_night;
            case "13d":
                return R.drawable.snow_day;
            case "13n":
                return R.drawable.snow_night;
            case "50d":
                return R.drawable.mist_day;
            case "50n":
                return R.drawable.mist_night;
        }
        return -1;
    }

}
