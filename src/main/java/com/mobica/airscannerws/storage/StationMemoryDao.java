package com.mobica.airscannerws.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wojtek on 22.10.15.
 */
public class StationMemoryDao implements StationDao {
    // <MAC, Station>
    private final Map<String, Station> storage = new ConcurrentHashMap<>();

    @Override
    public Station getStation(String address) {
        return storage.get(address);
    }

    @Override
    public void updateStation(Station station) {
        storage.put(station.getAddress(), station);
    }

    @Override
    public void addStation(Station station) {
        storage.put(station.getAddress(), station);
    }
}
