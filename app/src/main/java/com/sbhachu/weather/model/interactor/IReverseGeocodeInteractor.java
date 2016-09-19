package com.sbhachu.weather.model.interactor;

import android.location.Address;

public interface IReverseGeocodeInteractor {

    void resolveAddress(final Double latitude, final Double longitude, final Actions actions);

    interface Actions {
        void onAddressResolved(final String city, final String countryCode);
    }
}
