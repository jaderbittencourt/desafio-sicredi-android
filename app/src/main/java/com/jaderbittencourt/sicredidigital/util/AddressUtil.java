package com.jaderbittencourt.sicredidigital.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressUtil {

    public String getAddress(Context context, Double latitude, Double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context,Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            return "";
        }
    }
}
