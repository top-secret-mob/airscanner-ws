package com.mobica.airscannerws.storage;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public List<Station> getStations() {
        return Lists.newArrayList(storage.values());
    }

    @Override
    public List<Station> getStations(long minLastUpdateTime) {
        final List<Station> stations = new ArrayList<>();
        for (Station station : storage.values()) {
            if (station.getLastUpdateTime() >= minLastUpdateTime) {
                stations.add(station);
            }
        }
        return stations;
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
