package com.mobica.airscannerws.core;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mobica.airscannerws.api.GeoFence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by woos on 2015-11-18.
 */
public class GeofenceManager {
    private static final Map<String, List<GeoFence>> geofences = new HashMap<>();

    public static List<GeoFence> getGeofences(String authKey, double latitude, double longiture) {
        final String address = StationsManager.getStationAddress(authKey);
        if (Strings.isNullOrEmpty(address)) {
            return null;
        }

        List<GeoFence> fences = geofences.get(address);
        if (fences == null) {
            // some default fences
            fences = Lists.newArrayList(new GeoFence("Mobica", 53.429216, 14.555992, 100),    // Mobica office szczecin
                    new GeoFence("KFC", 53.431702, 14.555048, 100));                          // KFC plac rodla
            geofences.put(address, fences);
        }

        return fences;
    }

    public static void setGeofences(String authKey, List<GeoFence> fences) {
        final String address = StationsManager.getStationAddress(authKey);
        if (Strings.isNullOrEmpty(address)) {
            return;
        }

        geofences.put(address, fences);
    }
}
