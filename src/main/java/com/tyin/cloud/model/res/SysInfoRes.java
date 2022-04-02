package com.tyin.cloud.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tyin.cloud.core.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Data
    public static class CpuVo {
        /**
         * 核心数
         */
        @JsonProperty("cpu_num")
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

        /**
         * JDK版本
         */
        private String version;

        /**
         * JDK路径
         */
        private String home;

        @JsonProperty("start_time")
        private String startTime = getStartTime();

        @JsonProperty("run_time")
        private String runTime = getRunTime();

        public String getStartTime() {
            return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
        }

        /**
         * JDK运行时间
         */
        public String getRunTime() {
            return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
        }
    }

    @Data
    public static class SysVo {
        /**
         * 服务器名称
         */
        @JsonProperty("computer_name")
        private String computerName;

        /**
         * 服务器Ip
         */
        @JsonProperty("computer_ip")
        private String computerIp;

        /**
         * 项目路径
         */
        @JsonProperty("user_dir")
        private String userDir;

        /**
         * 操作系统
         */
        @JsonProperty("os_name")
        private String osName;

        /**
         * 系统架构
         */
        @JsonProperty("os_arch")
        private String osArch;
    }

    @Data
    public static class DiskVo {
        /**
         * 盘符路径
         */
        @JsonProperty("dir_name")
        private String dirName;

        /**
         * 盘符类型
         */
        @JsonProperty("sys_type_name")
        private String sysTypeName;

        /**
         * 文件类型
         */
        @JsonProperty("file_type_name")
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
