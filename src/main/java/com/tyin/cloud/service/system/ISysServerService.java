package com.tyin.cloud.service.system;

import com.tyin.cloud.model.res.SysInfoRes;
import com.tyin.cloud.model.res.SysRedisRes;

/**
 * @author Tyin
 * @date 2022/3/29 16:24
 * @description ...
 */
public interface ISysServerService {
    SysInfoRes getSysInfo();

    SysRedisRes getSysRedisInfo();
}
