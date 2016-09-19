package com.sbhachu.weather.presentation.weather;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.TransitionDrawable;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.domain.Forecast;
import com.sbhachu.weather.model.interactor.impl.LocationInteractor;
import com.sbhachu.weather.model.interactor.impl.ReverseGeocodeInteractor;
import com.sbhachu.weather.model.interactor.impl.CurrentWeatherInteractor;
import com.sbhachu.weather.model.interactor.impl.DailyWeatherInteractor;
import com.sbhachu.weather.presentation.common.BaseActivity;
import com.sbhachu.weather.presentation.weather.view.CurrentWeatherView;
import com.sbhachu.weather.presentation.weather.view.DailyWeatherView;
import com.sbhachu.weather.presentation.weather.view.StatusBarView;

import java.util.Locale;

public class WeatherActivity extends BaseActivity<WeatherPresenter> implements IWeatherView, StatusBarView.Actions {

    private static final String TAG = WeatherActivity.class.getSimpleName();

    private static final int PERMISSION_REQUEST_LOCATION = 1;

    private Snackbar snackbar;
    private CurrentWeatherView currentWeatherView;
    private DailyWeatherView dailyWeatherView;
    private StatusBarView statusBarView;

    @Override
    protected void initialiseViews() {
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        currentWeatherView = (CurrentWeatherView) findViewById(R.id.current_weather_view);
        dailyWeatherView = (DailyWeatherView) findViewById(R.id.daily_weather_view);
        statusBarView = (StatusBarView) findViewById(R.id.status_bar_view);
    }

    @Override
    protected void initialiseViewListeners() {
        statusBarView.setActions(this);
    }

    @Override
    protected WeatherPresenter createPresenter() {
        final WeatherPresenter presenter = new WeatherPresenter(LocationInteractor.getInstance(),
                new ReverseGeocodeInteractor(new Geocoder(this, Locale.getDefault())),
                new CurrentWeatherInteractor(), new DailyWeatherInteractor());
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void handleExtras() {
        // not used
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check location permission
        final int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            getPresenter().start();
        } else if (hasPermission == PackageManager.PERMISSION_DENIED && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.e(TAG, "Permission DENIED");
            showLocationPermissionError(R.string.location_permission_body, R.string.allow);
        } else {
            // request permission
            ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().stop();
    }

    @Override
    public void refresh() {
        getPresenter().forceRefresh();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPresenter().start();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showAlert(R.string.location_permission_title,
                                R.string.location_permission_body,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                                    }
                                });
                    } else {
                        showLocationPermissionError(R.string.location_permission_body, R.string.allow);
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void showAlert(int titleResId, int messageResId, DialogInterface.OnClickListener positive) {
        showDialog(new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setCancelable(false)
                .setPositiveButton("OK", positive));
    }

    protected void showLocationPermissionError(int message, int buttonText) {
        if (snackbarContainer != null && snackbar != null && !snackbar.isShown()) {
            snackbar = Snackbar.make(snackbarContainer, message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(buttonText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                    ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                    final TransitionDrawable transition = (TransitionDrawable) snackbarContainer.getBackground();
                    transition.reverseTransition(300);
                }
            });

            final TransitionDrawable transition = (TransitionDrawable) snackbarContainer.getBackground();
            transition.startTransition(300);
            snackbar.show();
        }
    }

    @Override
    public void updateCurrentWeather(CurrentWeather currentWeather) {
        currentWeatherView.bindData(currentWeather);
    }

    @Override
    public void updateDate(String currentDate) {
        statusBarView.bindData(currentDate);
    }

    @Override
    public void updateDailyWeather(Forecast forecast) {
        dailyWeatherView.bindData(forecast);
    }

    @Override
    public void showError(final int titleResId, final int bodyResId) {
        showDialog(new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(bodyResId)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, null));
    }
}
