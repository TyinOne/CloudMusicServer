package com.tyin.server.service.impl;


import com.google.common.collect.Lists;
import com.tyin.core.module.res.admin.SysInfoRes;
import com.tyin.core.module.res.admin.SysRedisRes;
import com.tyin.core.utils.math.Ardith;
import com.tyin.server.service.ISysServerService;
import com.tyin.server.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static com.tyin.core.module.res.admin.SysInfoRes.*;

/**
 * @author Tyin
 * @date 2022/3/29 16:25
 * @description ...
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysServerServiceImpl implements ISysServerService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public SysInfoRes getSysInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        return SysInfoRes.builder()
                .cpu(getCpuRes(hardware.getProcessor()))
                .mem(getMemRes(hardware.getMemory()))
                .sys(getSysRes())
                .jvm(getJvmRes())
                .disks(getDisksRes(systemInfo.getOperatingSystem()))
                .build();
    }

    @Override
    public SysRedisRes getSysRedisInfo() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) c -> c.serverCommands().info());
        Properties commandStats = redisTemplate.execute((RedisCallback<Properties>) c -> c.serverCommands().info("commandstats"));
        Long dbSize = redisTemplate.execute((RedisCallback<Long>) connection -> connection.serverCommands().dbSize());
        List<SysRedisRes.Command> commands = Lists.newArrayList();
        if (Objects.nonNull(commandStats)) {
            commandStats.stringPropertyNames().forEach(key -> {
                String property = commandStats.getProperty(key);
                commands.add(SysRedisRes.Command.builder()
                        .name(StringUtils.removeStart(key, "cmdstat_"))
                        .value(StringUtils.substringBetween(property, "calls=", ",usec"))
                        .build()
                );
            });
        }
        return SysRedisRes.builder()
                .info(info)
                .commandStats(commands)
                .dbSize(dbSize)
                .build();
    }


    private CpuRes getCpuRes(CentralProcessor processor) {
        CpuRes cpuRes = new CpuRes();
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long ioWait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + ioWait + irq + softirq + steal;
        cpuRes.setCpuNum(processor.getLogicalProcessorCount());
        cpuRes.setTotal(totalCpu);
        cpuRes.setSys(cSys);
        cpuRes.setUsed(user);
        cpuRes.setWait(ioWait);
        cpuRes.setFree(idle);
        return cpuRes;
    }

    private MemRes getMemRes(GlobalMemory memory) {
        MemRes mem = new MemRes();
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
        return mem;
    }

    private SysRes getSysRes() {
        SysRes sys = new SysRes();
        Properties props = System.getProperties();
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
        return sys;
    }

    private JvmRes getJvmRes() {
        JvmRes jvm = new SysInfoRes.JvmRes();
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
        return jvm;
    }

    private List<DiskRes> getDisksRes(OperatingSystem operatingSystem) {
        List<DiskRes> list = Lists.newArrayList();
        FileSystem fileSystem = operatingSystem.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            DiskRes diskRes = new DiskRes();
            diskRes.setDirName(fs.getMount());
            diskRes.setSysTypeName(fs.getType());
            diskRes.setFileTypeName(fs.getName());
            diskRes.setTotal(convertFileSize(total));
            diskRes.setFree(convertFileSize(free));
            diskRes.setUsed(convertFileSize(used));
            double div = Ardith.div(used, total, 4);
            diskRes.setUsage(Ardith.mul(div, 100.0));
            list.add(diskRes);
        }
        return list;
    }

    private String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
