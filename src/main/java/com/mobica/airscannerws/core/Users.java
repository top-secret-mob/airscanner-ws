package com.mobica.airscannerws.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps all registered users
 */
public class Users {
    private static final Users ourInstance = new Users();
    // <MAC, User>
    private final Map<String, User> users = new HashMap<>();

    public static Users getInstance() {
        return ourInstance;
    }

    private Users() {
    }

    public void addUser(String address, String gcmId) {
        users.put(address, new User(address, gcmId));
    }

    public User getUser(String address) {
        return users.get(address);
    }

    public boolean contains(String address) {
        return users.containsKey(address);
    }
}
