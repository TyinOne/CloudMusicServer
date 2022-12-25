package com.tyin.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;

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

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

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

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return LOCAL;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignored) {
        }
        return UNKNOWN;
    }

    public static String getIpCity(Long longIp) throws IOException {
        String ip = longToIp(longIp);
        if (ip.startsWith(LAN_A) || ip.startsWith(LAN_B) || ip.startsWith(LAN_C) || ip.startsWith(SWAGGER) || ip.equals(LOCAL)) {
            return "本地IP";
        }
        if (Objects.isNull(REGION_RESOURCE)) {
            return "未知地址";
        }
        InputStream resourceAsStream = IpUtils.class.getClassLoader().getResourceAsStream("ip2region.xdb");
        byte[] cBuff;
        try {
            assert resourceAsStream != null;
            cBuff = resourceAsStream.readAllBytes();
        } catch (Exception e) {
            log.warn(String.format("failed to load content from `%s`: %s\n", "resourceAsStream", e));
            return "未知地址";
        }
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn(String.format("failed to create searcher with `%s`: %s\n", "resourceAsStream", e));
            return "未知地址";
        }
        // 2、查询
        try {
            return searcher.search(ip);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(String.format("failed to search(%s): %s\n", ip, e));
        } finally {
            // 3、关闭资源
            searcher.close();
        }
        return "未知地址";
    }

}