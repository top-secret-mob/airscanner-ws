package com.mobica.airscannerws.storage;

/**
 * Created by wojtek on 22.10.15.
 */
public class StorageConnector {
    private static final StationDao stationDao = new StationMemoryDao();

    public static StationDao open() {
        return stationDao;
    }
}
