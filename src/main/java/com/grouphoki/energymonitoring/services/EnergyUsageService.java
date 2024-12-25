package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import jakarta.validation.Valid;

import java.util.List;

public interface EnergyUsageService {
    void saveEnergyUsage(EnergyUsageDto energyUsageDto);
    void updateTargetUsage(@Valid EnergyUsageDto energyUsageDto);
    void deleteTargetUsage(Long targetUsageId);

    List<EnergyUsageDto> getAllEnergyUsage();
    public List<EnergyUsageDto> getEnergyUsageByUser(String username);
    EnergyUsageDto findEnergyById(Long energyUsageId);

}
