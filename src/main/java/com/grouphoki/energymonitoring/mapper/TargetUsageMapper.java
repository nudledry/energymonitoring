package com.grouphoki.energymonitoring.mapper;

import com.grouphoki.energymonitoring.dto.TargetUsageDto;
import com.grouphoki.energymonitoring.models.TargetUsage;

public class TargetUsageMapper {
    public static TargetUsage mapToTargetUsage(TargetUsageDto targetUsageDto) {
        return TargetUsage.builder()
                .id(targetUsageDto.getId())
                .target(targetUsageDto.getTarget())
                .time(targetUsageDto.getTime())
                .createdAt(targetUsageDto.getCreatedAt())
                .updatedAt(targetUsageDto.getUpdatedAt())
                .createdBy(targetUsageDto.getCreatedBy())
                .build();
    }

    public static TargetUsageDto mapToTargetUsageDto(TargetUsage targetUsage) {
        return TargetUsageDto.builder()
                .id(targetUsage.getId())
                .target(targetUsage.getTarget())
                .time(targetUsage.getTime())
                .createdAt(targetUsage.getCreatedAt())
                .updatedAt(targetUsage.getUpdatedAt())
                .createdBy(targetUsage.getCreatedBy())
                .build();
    }
}
