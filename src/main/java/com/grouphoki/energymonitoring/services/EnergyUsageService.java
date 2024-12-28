package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.models.EnergyUsage;
import com.grouphoki.energymonitoring.models.UserEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface EnergyUsageService {
    void saveEnergyUsage(EnergyUsageDto energyUsageDto);
    void updateEnergyUsage(Long id, @Valid EnergyUsageDto energyUsageDto);
    void deleteEnergyUsage(Long id);
    void deleteAllByUser(UserEntity user);
    EnergyUsage findById(Long id);

    List<EnergyUsageDto> getAllEnergyUsage();
    List<EnergyUsageDto> getEnergyUsageByUser(String username);
    EnergyUsageDto findEnergyById(Long energyUsageId);



}
