package com.tyin.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/7/21 16:25
 * @description ...
 */
public class InvokeUtils {
    public static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (Objects.nonNull(methodParams) && methodParams.size() > 0) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 获取参数值
     *
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] clazz = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            clazz[index] = os[0];
            index++;
        }
        return clazz;
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] clazz = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            clazz[index] = (Class<?>) os[1];
            index++;
        }
        return clazz;
    }

    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = CommonStringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr)) {
            return null;
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> clazz = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = CommonStringUtils.trimToEmpty(methodParam);
            // String字符串类型，包含'
            if (CommonStringUtils.contains(str, "'")) {
                clazz.add(new Object[]{CommonStringUtils.replace(str, "'", ""), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if (CommonStringUtils.equals(str, "true") || CommonStringUtils.equalsIgnoreCase(str, "false")) {
                clazz.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，包含L
            else if (CommonStringUtils.containsIgnoreCase(str, "L")) {
                clazz.add(new Object[]{Long.valueOf(CommonStringUtils.replaceIgnoreCase(str, "L", "")), Long.class});
            }
            // double浮点类型，包含D
            else if (CommonStringUtils.containsIgnoreCase(str, "D")) {
                clazz.add(new Object[]{Double.valueOf(CommonStringUtils.replaceIgnoreCase(str, "D", "")), Double.class});
            }
            // 其他类型归类为整形
            else {
                clazz.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return clazz;
    }
}
