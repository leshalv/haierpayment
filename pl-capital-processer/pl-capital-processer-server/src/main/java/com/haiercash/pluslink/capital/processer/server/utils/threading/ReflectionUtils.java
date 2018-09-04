package com.haiercash.pluslink.capital.processer.server.utils.threading;

import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    private static Field getFieldCore(Class<?> clazz, String filedName, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be null");

        try {
            Field field = clazz.getDeclaredField(filedName);
            if (Modifier.isStatic(field.getModifiers()) != isStatic)
                return null;
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            return null;
        }
    }

    private static Method getMethodCore(Class<?> clazz, String methodName, Class<?>[] parameterTypes, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be null");

        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            if (Modifier.isStatic(method.getModifiers()) != isStatic)
                return null;
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            return null;
        }
    }

    //获取构造函数
    public static <T> Constructor<T> getConstructor(Class<T> clazz) {
        return getConstructor(clazz, null);
    }

    //获取构造函数
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] parameterTypes) {
        Assert.notNull(clazz, "clazz can not be null");

        try {
            Constructor<T> constructor = clazz.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception e) {
            return null;
        }
    }


    //获取字段,可获取父类私有字段
    public static Field getField(Class<?> clazz, String filedName, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be null");

        while (!clazz.equals(Object.class)) {
            Field field = getFieldCore(clazz, filedName, isStatic);
            if (field == null) {
                clazz = clazz.getSuperclass();
                continue;
            }
            return field;
        }
        return null;
    }


    //获取方法,可获取父类私有方法
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be null");

        while (!clazz.equals(Object.class)) {
            Method method = getMethodCore(clazz, methodName, parameterTypes, isStatic);
            if (method == null) {
                clazz = clazz.getSuperclass();
                continue;
            }
            return method;
        }
        return null;
    }

    //创建实例
    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, null, null);
    }

    //创建实例
    public static <T> T newInstance(Class<T> clazz, Class<?>[] parameterTypes, Object[] args) {
        Constructor<T> constructor = getConstructor(clazz, parameterTypes);
        if (constructor == null)
            throw new RuntimeException("no such constructor");
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("create instance fail", e);
        }
    }

    //获取实例字段值
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object target, String fieldName) {
        Assert.notNull(target, "target can not be null");

        Class<?> clazz = target.getClass();
        Field field = getField(clazz, fieldName, false);
        if (field == null)
            throw new RuntimeException("no such field");
        try {
            return (T) field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("get field value fail", e);
        }
    }

    //获取静态字段值
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Class<?> clazz, String fieldName) {
        Assert.notNull(clazz, "clazz can not be null");

        Field field = getField(clazz, fieldName, true);
        if (field == null)
            throw new RuntimeException("no such field");
        try {
            return (T) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("get field value fail", e);
        }
    }

    //设置实例字段值
    public static void setFieldValue(Object target, String fieldName, Object value) {
        Assert.notNull(target, "target can not be null");

        Class<?> clazz = target.getClass();
        Field field = getField(clazz, fieldName, false);
        if (field == null)
            throw new RuntimeException("no such field");
        try {
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("set field value fail", e);
        }
    }

    //设置静态字段值
    public static void setFieldValue(Class<?> clazz, String fieldName, Object value) {
        Assert.notNull(clazz, "clazz can not be null");

        Field field = getField(clazz, fieldName, true);
        if (field == null)
            throw new RuntimeException("no such field");
        try {
            field.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException("set field value fail", e);
        }
    }

    //调用实例方法
    public static <T> T invoke(Object target, String methodName) {
        return invoke(target, methodName, null, null);
    }

    //调用实例方法
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object target, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Assert.notNull(target, "target can not be null");

        Class<?> clazz = target.getClass();
        Method method = getMethod(clazz, methodName, parameterTypes, false);
        if (method == null)
            throw new RuntimeException("no such method");
        try {
            return (T) method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("invoke method fail", e);
        }
    }

    //调用静态方法
    public static <T> T invoke(Class<?> clazz, String methodName) {
        return invoke(clazz, methodName, null, null);
    }

    //调用静态方法
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Assert.notNull(clazz, "clazz can not be null");

        Method method = getMethod(clazz, methodName, parameterTypes, true);
        if (method == null)
            throw new RuntimeException("no such method");
        try {
            return (T) method.invoke(null, args);
        } catch (Exception e) {
            throw new RuntimeException("invoke method fail", e);
        }
    }
}
