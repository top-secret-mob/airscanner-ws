package com.mobica.airscannerws.storage;

import java.util.List;

/**
 * Created by wojtek on 22.10.15.
 */
public interface StationDao {

    Station getStationByAddress(String address);

    Station getStationByToken(String token);

    List<Station> getStations();

    List<Station> getStations(long minLastUpdateTime);

    void updateStation(Station station);

    void addStation(Station station);
}
