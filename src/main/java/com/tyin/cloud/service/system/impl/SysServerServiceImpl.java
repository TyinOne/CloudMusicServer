package com.tyin.cloud.service.system.impl;

import com.google.common.collect.Lists;
import com.tyin.cloud.core.configs.math.Ardith;
import com.tyin.cloud.core.utils.IpUtils;
import com.tyin.cloud.model.res.SysInfoRes;
import com.tyin.cloud.service.system.ISysServerService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Properties;

import static com.tyin.cloud.model.res.SysInfoRes.*;

/**
 * @author Tyin
 * @date 2022/3/29 16:25
 * @description ...
 */
@Service
@Slf4j
public class SysServerServiceImpl implements ISysServerService {

    @Override
    public SysInfoRes getSysInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        return SysInfoRes.builder()
                .cpu(getCpuVo(hardware.getProcessor()))
                .mem(getMemVo(hardware.getMemory()))
                .sys(getSysVo())
                .jvm(getJvmVo())
                .disks(getDisksVo(systemInfo.getOperatingSystem()))
                .build();
    }


    private CpuVo getCpuVo(CentralProcessor processor) {
        CpuVo cpuVo = new CpuVo();
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
        cpuVo.setCpuNum(processor.getLogicalProcessorCount());
        cpuVo.setTotal(totalCpu);
        cpuVo.setSys(cSys);
        cpuVo.setUsed(user);
        cpuVo.setWait(ioWait);
        cpuVo.setFree(idle);
        return cpuVo;
    }

    private MemVo getMemVo(GlobalMemory memory) {
        MemVo mem = new MemVo();
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
        return mem;
    }

    private SysVo getSysVo() {
        SysVo sys = new SysVo();
        Properties props = System.getProperties();
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
        return sys;
    }

    private JvmVo getJvmVo() {
        JvmVo jvm = new SysInfoRes.JvmVo();
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
        return jvm;
    }

    private List<DiskVo> getDisksVo(OperatingSystem operatingSystem) {
        List<DiskVo> list = Lists.newArrayList();
        FileSystem fileSystem = operatingSystem.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            DiskVo diskVo = new DiskVo();
            diskVo.setDirName(fs.getMount());
            diskVo.setSysTypeName(fs.getType());
            diskVo.setFileTypeName(fs.getName());
            diskVo.setTotal(convertFileSize(total));
            diskVo.setFree(convertFileSize(free));
            diskVo.setUsed(convertFileSize(used));
            double div = Ardith.div(used, total, 4);
            diskVo.setUsage(Ardith.mul(div, 100.0));
            list.add(diskVo);
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
