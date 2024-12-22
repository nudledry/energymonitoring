package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.TargetUsageDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface TargetUsageService {
    void saveTargetUsage(UserEntity userEntity, TargetUsageDto targetUsageDto);

    List<TargetUsageDto> getAllTargetUsage();

    TargetUsageDto findTargetById(Long targetUsageId);
    void saveTargetUsage(@Valid TargetUsageDto targetUsageDto);
    void updateTargetUsage(@Valid TargetUsageDto targetUsageDto);
    void deleteTargetUsage(Long targetUsageId);
}
