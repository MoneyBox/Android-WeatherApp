package com.sbhachu.weather.util;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SharedPreferencesHelper {

    public static boolean getBoolean(@NonNull SharedPreferences sharedPreferences, @NonNull String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putBoolean(@NonNull SharedPreferences sharedPreferences, @NonNull String key, boolean value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(@NonNull SharedPreferences sharedPreferences, @NonNull String key, @Nullable String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putString(@NonNull SharedPreferences sharedPreferences, @NonNull String key, @Nullable String value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static float getFloat(@NonNull SharedPreferences sharedPreferences, @NonNull String key,  float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void putFloat(@NonNull SharedPreferences sharedPreferences, @NonNull String key, float value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
}
