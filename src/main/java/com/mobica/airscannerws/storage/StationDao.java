package com.mobica.airscannerws.storage;

import java.util.List;

/**
 * Created by wojtek on 22.10.15.
 */
public interface StationDao {

    Station getStation(String address);

    List<Station> getStations();

    void updateStation(Station station);

    void addStation(Station station);
}
