package com.tyin.server.loader;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tyin.core.components.CloudTimerTaskComponents;
import com.tyin.core.components.ScheduledComponents;
import com.tyin.core.components.properties.PropertiesEnum;
import com.tyin.core.module.base.TimerTaskState;
import com.tyin.core.module.bean.DictLabel;
import com.tyin.core.module.entity.AdminDict;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.utils.DateUtils;
import com.tyin.core.utils.JsonUtils;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.repository.AdminDictRepository;
import com.tyin.server.repository.AdminDictTypeRepository;
import com.tyin.server.repository.AdminInviteCodeRepository;
import com.tyin.server.repository.AdminScheduledRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Tyin
 * @date 2022/5/22 5:36
 * @description ...
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SystemLoader {
    private final PropertiesComponents propertiesComponents;
    private final ScheduledComponents scheduledComponents;
    private final CloudTimerTaskComponents timerTaskComponents;
    private final AdminDictRepository adminDictRepository;
    private final AdminDictTypeRepository adminDictTypeRepository;
    private final AdminScheduledRepository adminScheduledRepository;
    private final AdminInviteCodeRepository adminInviteCodeRepository;

    @Async
    public void initScheduled() throws SchedulerException {
        List<AdminScheduled> scheduledList = adminScheduledRepository.selectList(Wrappers.<AdminScheduled>lambdaQuery().eq(AdminScheduled::getDisabled, Boolean.FALSE));
        scheduledComponents.init(scheduledList);
    }

    @Async
    public void startTimerTask() {
        List<TimerTaskState> list = Lists.newArrayList();
        //把数据库已使用或者已过期的状态改为失效
        //AdminInviteCode任务
        adminInviteCodeRepository.update(AdminInviteCode.builder().invalid(Boolean.TRUE).build(), Wrappers.<AdminInviteCode>lambdaUpdate()
                .eq(AdminInviteCode::getInvalid, Boolean.FALSE)
                .le(AdminInviteCode::getExpirationTime, DateUtils.getNowDate()));
        List<AdminInviteCode> adminInviteCode = adminInviteCodeRepository.selectList(Wrappers.<AdminInviteCode>lambdaQuery().eq(AdminInviteCode::getInvalid, Boolean.FALSE));
        list.addAll(adminInviteCode);
        timerTaskComponents.init(list);
    }

    @Async
    public void initAll(List<String> dictTypes) {
        if (dictTypes.size() == 0) {
            dictTypes = adminDictTypeRepository.selectDictLabel().stream().map(DictLabel::getValue).toList();
        }
        List<Map<String, Object>> maps = adminDictRepository.selectMaps(Wrappers.<AdminDict>lambdaQuery().in(AdminDict::getDictType, dictTypes));
        Map<String, List<Map<String, Object>>> groupByTypeMap = maps.stream().collect(Collectors.groupingBy(i -> i.get("dict_type").toString()));
        groupByTypeMap.forEach((k, v) -> {
            Map<String, String> itemMaps = Maps.newHashMap();
            PropertiesEnum propertiesEnum = PropertiesEnum.getClazzByType(k);
            if (Objects.nonNull(propertiesEnum)) {
                Class<?> clazzByType = propertiesEnum.getClazz();
                v.forEach(i -> itemMaps.put(i.get("dict_key").toString(), i.get("dict_value").toString()));
                String jsonString = JsonUtils.toJSONString(itemMaps);
                propertiesComponents.setModules(propertiesEnum, jsonString);
            }
        });
    }
}