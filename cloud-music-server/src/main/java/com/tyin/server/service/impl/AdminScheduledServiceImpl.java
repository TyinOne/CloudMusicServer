package com.tyin.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.components.ScheduledComponents;
import com.tyin.core.components.properties.models.ScheduledGroupConfig;
import com.tyin.core.module.bean.ScheduledGroupLabel;
import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.core.module.res.admin.AdminScheduleRes;
import com.tyin.core.module.res.admin.AdminScheduledDetail;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.scheduled.CronUtils;
import com.tyin.server.api.PageResult;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.params.valid.SaveScheduledValid;
import com.tyin.server.repository.AdminScheduledLogRepository;
import com.tyin.server.repository.AdminScheduledRepository;
import com.tyin.server.service.IAdminScheduledService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tyin.core.constants.ResMessageConstants.*;

/**
 * @author Tyin
 * @date 2022/7/14 9:42
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AdminScheduledServiceImpl implements IAdminScheduledService {
    private final AdminScheduledLogRepository adminScheduledLogRepository;
    private final PropertiesComponents propertiesComponents;
    private final AdminScheduledRepository adminScheduledRepository;
    private final RedisComponents redisComponents;
    private final ScheduledComponents scheduledComponents;

    @Override
    public void addLog(AdminScheduledLog adminScheduledLog) {
        adminScheduledLogRepository.insert(adminScheduledLog);
    }

    @Override
    public Integer saveScheduled(SaveScheduledValid valid) throws SchedulerException {
        String invalidMessage = CronUtils.getInvalidMessage(valid.getCronExpression());
        Asserts.isTrue(Objects.isNull(invalidMessage), invalidMessage);
        AdminScheduled scheduled = AdminScheduled.builder().build();
        int row = 0;
        if (Objects.isNull(valid.getId())) {
            String scheduledName = valid.getScheduledName();
            List<AdminScheduled> adminScheduleds = adminScheduledRepository.selectList(Wrappers.<AdminScheduled>lambdaQuery().eq(AdminScheduled::getScheduledName, scheduledName));
            Asserts.isTrue(adminScheduleds.size() == 0, SCHEDULED_HAS_EXIST);
            BeanUtils.copyProperties(valid, scheduled);
            row = adminScheduledRepository.insert(scheduled);
            Asserts.isTrue(row == 1, ADD_FAILED);
            scheduledComponents.addScheduled(scheduled);
        } else {
            BeanUtils.copyProperties(valid, scheduled);
            scheduled.setId(valid.getId());
            row = adminScheduledRepository.updateById(scheduled);
            Asserts.isTrue(row == 1, UPDATE_FAILED);
            updateSchedulerJob(scheduled, valid.getScheduledGroup());
        }
        return row;
    }

    @Override
    public List<ScheduledGroupLabel> getGroupLabel() {
        List<ScheduledGroupConfig.ScheduledGroup> list = propertiesComponents.getScheduledGroupConfig().getList();
        return list.stream().map(i -> {
            ScheduledGroupLabel scheduledGroupLabel = new ScheduledGroupLabel();
            scheduledGroupLabel.setLabel(i.getValue());
            scheduledGroupLabel.setValue(i.getKey());
            return scheduledGroupLabel;
        }).collect(Collectors.toList());
    }

    @Override
    public PageResult<AdminScheduleRes, ?> getSchedulePageResult(String keywords, String group, Boolean disabled, Long size, Long current) {
        QueryWrapper<AdminScheduled> wrapper = Wrappers.query();
        wrapper.lambda().apply(StringUtils.isNotEmpty(keywords), "INSTR(`scheduled_name`, {0}) > 0", keywords).eq(StringUtils.isNotEmpty(group), AdminScheduled::getScheduledGroup, group).eq(AdminScheduled::getDisabled, disabled);
        IPage<AdminScheduleRes> resPage = adminScheduledRepository.selectPageRes(wrapper, new Page<>(current, size));
        resPage.setRecords(resPage.getRecords().stream().peek(item -> {
            Map<String, String> sMap = propertiesComponents.getScheduledGroupConfig().getList().stream().collect(Collectors.toMap(ScheduledGroupConfig.ScheduledGroup::getKey, ScheduledGroupConfig.ScheduledGroup::getValue));
            item.setGroup(sMap.get(item.getGroup()));
        }).collect(Collectors.toList()));
        return PageResult.buildResult(resPage);
    }

    @Override
    public AdminScheduledDetail getScheduled(Long id) {
        AdminScheduled adminScheduled = adminScheduledRepository.selectById(id);
        Asserts.isTrue(Objects.nonNull(adminScheduled), DATA_ERROR);
        return AdminScheduledDetail.builder()
                .id(adminScheduled.getId())
                .scheduledName(adminScheduled.getScheduledName())
                .scheduledGroup(adminScheduled.getScheduledGroup())
                .invokeTarget(adminScheduled.getInvokeTarget())
                .cronExpression(adminScheduled.getCronExpression())
                .misfirePolicy(adminScheduled.getMisfirePolicy())
                .concurrent(adminScheduled.getConcurrent())
                .disabled(adminScheduled.getDisabled())
                .build();
    }

    public void updateSchedulerJob(AdminScheduled adminScheduled, String group) {
        Asserts.trycatch(() -> scheduledComponents.updateScheduled(adminScheduled, group));
    }
}
