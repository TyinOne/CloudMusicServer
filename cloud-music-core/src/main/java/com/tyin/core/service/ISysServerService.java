package com.tyin.core.service;


import com.tyin.core.module.res.admin.SysInfoRes;
import com.tyin.core.module.res.admin.SysRedisRes;

/**
 * @author Tyin
 * @date 2022/3/29 16:24
 * @description ...
 */
public interface ISysServerService {
    SysInfoRes getSysInfo();

    SysRedisRes getSysRedisInfo();
}
