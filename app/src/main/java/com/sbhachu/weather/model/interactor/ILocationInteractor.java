package com.sbhachu.weather.model.interactor;

import android.location.Location;

import com.sbhachu.weather.model.LatLong;

public interface ILocationInteractor {

    void resume(Actions listener);

    void pause();

    LatLong getLastLatLong();

    interface Actions {
        void onLocationChanged(Double latitude, Double longitude);
    }
}
