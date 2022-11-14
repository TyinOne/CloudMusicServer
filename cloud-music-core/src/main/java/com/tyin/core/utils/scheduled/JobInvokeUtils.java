package com.tyin.core.utils.scheduled;

import com.tyin.core.utils.CommonStringUtils;
import com.tyin.core.utils.InvokeUtils;
import com.tyin.core.utils.SpringUtils;
import com.tyin.core.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/7/14 9:26
 * @description ...
 */
public class JobInvokeUtils {
    public static void invokeMethod(String invokeTarget) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        if (!isValidClassName(beanName)) {
            Object bean = SpringUtils.getBean(beanName);
            InvokeUtils.invokeMethod(bean, methodName, methodParams);
        } else {
            Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
            InvokeUtils.invokeMethod(bean, methodName, methodParams);
        }
    }

    /**
     * 校验是否为为class包名
     *
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return CommonStringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取bean名称
     *
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = CommonStringUtils.substringBefore(invokeTarget, "(");
        return CommonStringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     *
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = CommonStringUtils.substringBefore(invokeTarget, "(");
        return CommonStringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     *
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
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
                clazz.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
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
