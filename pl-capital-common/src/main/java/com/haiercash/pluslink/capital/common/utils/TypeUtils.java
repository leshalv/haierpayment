package com.haiercash.pluslink.capital.common.utils;

public class TypeUtils {
    public static boolean isNumber(Object obj) {
        return obj instanceof Number;
    }
    public static boolean isNumber(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz);
    }
    public static String simpleClassName(Class<?> clazz) {
        if (isNumber(clazz)) {
            return Number.class.getSimpleName();
        }
        return clazz.getSimpleName();
    }
}

