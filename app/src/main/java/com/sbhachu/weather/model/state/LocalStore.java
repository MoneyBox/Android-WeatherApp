package com.sbhachu.weather.model.state;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.sbhachu.weather.util.SharedPreferencesHelper;

public class LocalStore {
    private static final String KEY_WEATHER_STORE = "weather::store";
    private static final String KEY_LAST_UPDATED_DATE = "last::updated::date";
    private static final String KEY_LAST_LATITUDE = "last::updated::latitude";
    private static final String KEY_LAST_LONGITUDE = "last::updated::longitude";


    private SharedPreferences sharedPreferences;

    public LocalStore(final Context context) {
        this.sharedPreferences = context.getSharedPreferences(KEY_WEATHER_STORE, 0);
    }

    public boolean getLastUpdatedDate() {
        return SharedPreferencesHelper.getBoolean(sharedPreferences, KEY_LAST_UPDATED_DATE, false);
    }

    public void putLastUpdatedDate(@NonNull final String lastUpdatedDate) {
        SharedPreferencesHelper.putString(sharedPreferences, KEY_LAST_UPDATED_DATE, lastUpdatedDate);
    }

    public float getLatitude() {
        return SharedPreferencesHelper.getFloat(sharedPreferences, KEY_LAST_LATITUDE, 0f);
    }

    public void putLatitude(final float latitude) {
        SharedPreferencesHelper.putFloat(sharedPreferences, KEY_LAST_LATITUDE, latitude);
    }

    public float getLongitude() {
        return SharedPreferencesHelper.getFloat(sharedPreferences, KEY_LAST_LONGITUDE, 0f);
    }

    public void putLongitude(final float longitude) {
        SharedPreferencesHelper.putFloat(sharedPreferences, KEY_LAST_LONGITUDE, longitude);
    }

}
