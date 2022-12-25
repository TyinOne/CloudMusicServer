package com.tyin.server.utils;

import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author Tyin
 * @date 2022/3/29 21:57
 * @description ...
 */
@Slf4j
public class IpUtils {
    private static final URL REGION_RESOURCE = IpUtils.class.getResource("/ip2region.xdb");
    private static final String UNKNOWN = "unknown";
    private static final String LOCAL = "127.0.0.1";
    private static final String LAN_A = "10.";
    private static final String LAN_B = "172.";
    private static final String LAN_C = "192.";
    private static final String SWAGGER = "0.";

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCAL.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                        ipAddress = "";
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    public static Long getIpAddressInt(HttpServletRequest request) {
        Map<String, String> headers = Maps.newHashMap();
        return ipToLong(getIpAddress(request));
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static Long ipToLong(String ipStr) {
        if (ipStr.startsWith("0")) return 1L;
        String[] ip = ipStr.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Integer.parseInt(ip[3]);
    }

    public static String longToIp(long intIp) {
        String[] ipArr = new String[4];
        for (int i = 0; i < ipArr.length; i++) {
            long n = (intIp >>> (8 * i)) & (0xFF);
            ipArr[3 - i] = String.valueOf(n);
        }
        return String.join(".", ipArr);
    }


}
