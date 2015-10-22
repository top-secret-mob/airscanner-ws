package com.mobica.airscannerws.storage;

/**
 * Created by wojtek on 22.10.15.
 */
public interface StationDao {

    Station getStation(String address);

    void updateStation(Station station);

    void addStation(Station station);
}
