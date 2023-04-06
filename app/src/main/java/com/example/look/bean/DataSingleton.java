package com.example.look.bean;

import java.util.HashMap;

public class DataSingleton {

    private static DataSingleton mDataSingleton = null;

    public synchronized static DataSingleton getInstance() {
        if (mDataSingleton == null) {
            mDataSingleton = new DataSingleton();
        }
        return mDataSingleton;
    }

    public final HashMap<String, Object> mMap;

    public DataSingleton() {
        mMap = new HashMap<>();
    }

    public void put(String key, Object value) {
        mMap.put(key, value);
    }

    public Object get(String key) {
        return mMap.get(key);
    }



}
