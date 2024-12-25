package com.grouphoki.energymonitoring.mapper;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.models.EnergyUsage;

public class EnergyUsageMapper {
    public static EnergyUsage mapToEnergyUsage(EnergyUsageDto energyUsageDto) {
        return com.grouphoki.energymonitoring.models.EnergyUsage.builder()
                .id(energyUsageDto.getId())
                .useAmount(energyUsageDto.getUseAmount())
                .time(energyUsageDto.getTime())
                .createdAt(energyUsageDto.getCreatedAt())
                .updatedAt(energyUsageDto.getUpdatedAt())
                .createdBy(energyUsageDto.getCreatedBy())
                .build();
    }

    public static EnergyUsageDto mapToEnergyUsageDto(EnergyUsage energyUsage) {
        return EnergyUsageDto.builder()
                .id(energyUsage.getId())
                .useAmount(energyUsage.getUseAmount())
                .time(energyUsage.getTime())
                .createdAt(energyUsage.getCreatedAt())
                .updatedAt(energyUsage.getUpdatedAt())
                .createdBy(energyUsage.getCreatedBy())
                .build();
    }
}
