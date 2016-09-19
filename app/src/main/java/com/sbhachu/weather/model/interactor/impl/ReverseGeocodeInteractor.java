package com.sbhachu.weather.model.interactor.impl;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.sbhachu.weather.model.interactor.IReverseGeocodeInteractor;

import java.io.IOException;
import java.util.List;

public class ReverseGeocodeInteractor implements IReverseGeocodeInteractor {

    private Geocoder geocoder;

    public ReverseGeocodeInteractor(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public void resolveAddress(final Double latitude, final Double longitude, final Actions actions) {
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && !addressList.isEmpty() && actions != null) {
                final Address address = addressList.get(0);
                final String city = address.getLocality();
                final String countryCode = address.getCountryCode();

                actions.onAddressResolved(city, countryCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
