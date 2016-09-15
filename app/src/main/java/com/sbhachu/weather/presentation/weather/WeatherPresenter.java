package com.sbhachu.weather.presentation.weather;

import android.location.Address;
import android.location.Location;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.LatLong;
import com.sbhachu.weather.model.domain.CurrentWeather;
import com.sbhachu.weather.model.domain.Forecast;
import com.sbhachu.weather.model.interactor.ICurrentWeatherInteractor;
import com.sbhachu.weather.model.interactor.IDailyWeatherInteractor;
import com.sbhachu.weather.model.interactor.ILocationInteractor;
import com.sbhachu.weather.model.interactor.IReverseGeocodeInteractor;
import com.sbhachu.weather.model.interactor.impl.CurrentWeatherInteractor;
import com.sbhachu.weather.model.interactor.impl.DailyWeatherInteractor;
import com.sbhachu.weather.model.interactor.impl.LocationInteractor;
import com.sbhachu.weather.model.interactor.impl.ReverseGeocodeInteractor;
import com.sbhachu.weather.presentation.common.BasePresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherPresenter extends BasePresenter<IWeatherView> implements LocationInteractor.Actions, ReverseGeocodeInteractor.Actions, CurrentWeatherInteractor.Listener, DailyWeatherInteractor.Listener {

    private static final String TAG = WeatherPresenter.class.getSimpleName();

    private final ILocationInteractor locationInteractor;
    private final IReverseGeocodeInteractor reverseGeocodeInteractor;
    private final ICurrentWeatherInteractor currentWeatherInteractor;
    private final IDailyWeatherInteractor dailyWeatherInteractor;

    public WeatherPresenter(final ILocationInteractor locationInteractor,
                            final IReverseGeocodeInteractor reverseGeocodeInteractor,
                            final ICurrentWeatherInteractor currentWeatherInteractor,
                            final IDailyWeatherInteractor dailyWeatherInteractor) {
        this.locationInteractor = locationInteractor;
        this.reverseGeocodeInteractor = reverseGeocodeInteractor;
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.dailyWeatherInteractor = dailyWeatherInteractor;
    }

    public void start() {
        locationInteractor.resume(this);
    }

    public void stop() {
        locationInteractor.pause();
    }

    @Override
    public void onLocationChanged(Double latitude, Double longitude) {
        // resolve an address for the current location
        reverseGeocodeInteractor.resolveAddress(latitude, longitude, this);
    }

    @Override
    public void onAddressResolved(final String city, final String countryCode) {
        // once we have an address stop polling for location updates
        locationInteractor.pause();
        fetchWeatherData(city, countryCode);
    }

    public void fetchWeatherData(final String city, final String countryCode) {
        currentWeatherInteractor.getCurrentWeather(city, countryCode, this);
        dailyWeatherInteractor.getDailyWeather(city, countryCode, this);
    }

    public void forceRefresh() {
        final LatLong lastLatLong = locationInteractor.getLastLatLong();
        if (lastLatLong != null) {
            reverseGeocodeInteractor.resolveAddress(lastLatLong.getLatitude(), lastLatLong.getLongitude(), this);
        }
    }

    @Override
    public void onCurrentWeatherSuccess(CurrentWeather currentWeather) {
        if (isViewAttached()) {
            if (currentWeather.getName() != null && currentWeather.getWeather() != null && currentWeather.getWeatherData() != null) {
                getView().updateCurrentWeather(currentWeather);

                final DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
                final String currentDate = formatter.format(new Date());
                getView().updateDate(currentDate);
            } else {
                getView().showError(R.string.invalid_data_error_title, R.string.invalid_data_error_body);
            }
        }
    }

    @Override
    public void onCurrentWeatherFailure() {
        if (isViewAttached()) {
            getView().showError(R.string.error_title, R.string.error_body);
        }
    }

    @Override
    public void onDailyWeatherSuccess(Forecast forecast) {
        if (isViewAttached()) {
            getView().updateDailyWeather(forecast);
        }
    }

    @Override
    public void onDailyWeatherFailure() {
        if (isViewAttached()) {
            getView().showError(R.string.error_title, R.string.error_body);
        }
    }
}
