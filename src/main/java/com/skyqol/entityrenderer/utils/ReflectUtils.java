package com.skyqol.entityrenderer.utils;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static <T> MethodHandle findMethod(Class<T> clazz, String[] methodNames, Class<?>... methodTypes) {
        final Method method = ReflectionHelper.findMethod(clazz, null, methodNames, methodTypes);

        try {
            return MethodHandles.lookup().unreflect(method);
        } catch (Exception e) {
            throw new ReflectionHelper.UnableToFindMethodException(methodNames, e);
        }
    }

    public static MethodHandle findFieldGetter(Class<?> clazz, String... fieldNames) {
        final Field field = ReflectionHelper.findField(clazz, fieldNames);

        try {
            return MethodHandles.lookup().unreflectGetter(field);
        } catch (Exception e) {
            throw new ReflectionHelper.UnableToAccessFieldException(fieldNames, e);
        }
    }

    public static MethodHandle findFieldSetter(Class<?> clazz, String... fieldNames) {
        final Field field = ReflectionHelper.findField(clazz, fieldNames);

        try {
            return MethodHandles.lookup().unreflectSetter(field);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelper.UnableToAccessFieldException(fieldNames, e);
        }
    }
}