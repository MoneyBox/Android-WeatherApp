package com.sbhachu.weather.model.interactor.impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sbhachu.weather.application.WeatherApplication;
import com.sbhachu.weather.model.LatLong;
import com.sbhachu.weather.model.interactor.ILocationInteractor;
import com.sbhachu.weather.model.state.LocalStore;


public class LocationInteractor implements ILocationInteractor, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = LocationInteractor.class.getSimpleName();

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private static final float LOCATION_MINIMUM_ACCURACY = 100.0f; //metres

    private static LocationInteractor instance;



    private GoogleApiClient googleApiClient;
    private Actions listener;
    private Context context;
    private LocalStore localStore;

    public static LocationInteractor getInstance() {
        if (instance == null) {
            instance = new LocationInteractor();
        }
        return instance;
    }

    private LocationInteractor() {
        this.context = WeatherApplication.getApplication().getApplicationContext();
        this.localStore = new LocalStore(context);
    }

    public void resume(Actions listener) {
        this.listener = listener;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    public void pause() {
        listener = null;
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    public LatLong getLastLatLong() {
        final Float latitude = localStore.getLatitude();
        final Float longitude = localStore.getLongitude();

        return new LatLong(latitude, longitude);
    }

    @Override
    public void onConnected(Bundle bundle) {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google API Client Connection Suspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google API Connection error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        localStore.putLatitude((float) location.getLatitude());
        localStore.putLongitude((float) location.getLongitude());

        if (listener != null && location.getAccuracy() < LOCATION_MINIMUM_ACCURACY) {
            listener.onLocationChanged(location.getLatitude(), location.getLongitude());
        }
    }
}

