package com.example.test_ddd.infra.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SnapshotUtil {

    private static final ThreadLocal<HashMap<String, String>> threadLocalData = new ThreadLocal<>();

    private SnapshotUtil() {
    }

    public static void clear() {
        threadLocalData.remove();
    }

    public static void putMap(HashMap<String, String> mapData) {
        threadLocalData.set(mapData);
    }

    public static void put(String key, String val) {
        HashMap<String, String> mapData = threadLocalData.get();
        if (null == mapData) {
            mapData = new HashMap<>();
        }
        mapData.put(key, val);
        threadLocalData.set(mapData);
    }

    public static void putObject(Object obj) {
        try {
            String key = getKey(obj);
            String val = (new ObjectMapper()).writeValueAsString(obj);

            put(key, val);
        } catch (Exception e) {

        }
    }

    public static String get(String key) {
        HashMap<String, String> mapData = threadLocalData.get();
        if (mapData == null) {
            return null;
        }

        return mapData.containsKey(key) ? mapData.get(key) : null;
    }

    public static boolean containsObject(Object obj) {
        HashMap<String, String> mapData = threadLocalData.get();

        if (null != mapData) {
            try {
                return mapData.containsKey(getKey(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public static <T> T getObject(T obj) {
        try {
            String key = getKey(obj);
            String val = get(key);
            if (null == val) {
                return null;
            }

            Class<T> clazz = (Class<T>) obj.getClass();
            return (new ObjectMapper()).readValue(val, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error=" + e.getMessage());
        }

        return null;
    }

    public static HashMap<String, String> getMap() {
        return threadLocalData.get();
    }

    protected static String getKey(Object obj) throws IllegalAccessException {
        Field field = obj.getClass().getDeclaredFields()[0];
        field.setAccessible(true);

        String key = obj.getClass().getName() + "_" + field.get(obj);

        return key;
    }
}