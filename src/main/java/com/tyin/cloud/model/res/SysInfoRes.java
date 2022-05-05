package com.tyin.cloud.model.res;

import com.tyin.cloud.core.configs.math.Ardith;
import com.tyin.cloud.core.utils.DateUtils;
import lombok.*;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/3/29 21:21
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysInfoRes {
    public static final int OSHI_WAIT_SECOND = 1000;
    private CpuVo cpu;
    private MemVo mem;
    private JvmVo jvm;
    private SysVo sys;
    private List<DiskVo> disks;

    @Setter
    public static class CpuVo {
        /**
         * 核心数
         */
        @Getter
        private int cpuNum;

        /**
         * CPU总的使用率
         */
        private double total;

        /**
         * CPU系统使用率
         */
        private double sys;

        /**
         * CPU用户使用率
         */
        private double used;

        /**
         * CPU当前等待率
         */
        private double wait;

        /**
         * CPU当前空闲率
         */
        private double free;

        public double getTotal() {
            return Ardith.round(Ardith.mul(total, 100d), 2);
        }

        public double getSys() {
            return Ardith.round(Ardith.mul(sys / total, 100d), 2);
        }

        public double getUsed() {
            return Ardith.round(Ardith.mul(used / total, 100d), 2);
        }

        public double getWait() {
            return Ardith.round(Ardith.mul(wait / total, 100d), 2);
        }

        public double getFree() {
            return Ardith.round(Ardith.mul(free / total, 100d), 2);
        }
    }

    @Data
    public static class MemVo {
        /**
         * 内存总量
         */
        private double total;

        /**
         * 已用内存
         */
        private double used;

        /**
         * 剩余内存
         */
        private double free;

        public double getTotal() {
            return Ardith.div(total, (1024 * 1024 * 1024), 2);
        }

        public double getUsed() {
            return Ardith.div(used, (1024 * 1024 * 1024), 2);
        }

        public double getFree() {
            return Ardith.div(free, (1024 * 1024 * 1024), 2);
        }

        public double getUsage() {
            return Ardith.mul(Ardith.div(used, total, 4), 100d);
        }
    }

    @Data
    public static class JvmVo {
        /**
         * 当前JVM占用的内存总数(M)
         */
        private double total;

        /**
         * JVM最大可用内存总数(M)
         */
        private double max;

        /**
         * JVM空闲内存(M)
         */
        private double free;

        private double used;

        private double usage;

        /**
         * JDK版本
         */
        private String version;

        /**
         * JDK路径
         */
        private String home;

        private String name = getName();

        private String startTime = getStartTime();

        private String runTime = getRunTime();

        public String getName() {
            return ManagementFactory.getRuntimeMXBean().getVmName();
        }

        /**
         * 服务启动时间
         */
        public String getStartTime() {
            return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
        }

        /**
         * JDK运行时间
         */
        public String getRunTime() {
            return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
        }

        public double getTotal() {
            return Ardith.div(total, (1024 * 1024), 2);
        }

        public double getMax() {
            return Ardith.div(max, (1024 * 1024), 2);
        }

        public double getFree() {
            return Ardith.div(free, (1024 * 1024), 2);
        }

        public double getUsed() {
            return Ardith.div(total - free, (1024 * 1024), 2);
        }

        public double getUsage() {
            return Ardith.mul(Ardith.div(total - free, total, 4), 100d);
        }
    }

    @Data
    public static class SysVo {
        /**
         * 服务器名称
         */
        private String computerName;

        /**
         * 服务器Ip
         */
        private String computerIp;

        /**
         * 项目路径
         */
        private String userDir;

        /**
         * 操作系统
         */
        private String osName;

        /**
         * 系统架构
         */
        private String osArch;
    }

    @Data
    public static class DiskVo {
        /**
         * 盘符路径
         */
        private String dirName;

        /**
         * 盘符类型
         */
        private String sysTypeName;

        /**
         * 文件类型
         */
        private String fileTypeName;

        /**
         * 总大小
         */
        private String total;

        /**
         * 剩余大小
         */
        private String free;

        /**
         * 已经使用量
         */
        private String used;

        /**
         * 资源的使用率
         */
        private double usage;
    }
}
