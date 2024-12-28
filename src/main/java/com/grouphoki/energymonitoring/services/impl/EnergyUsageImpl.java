package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.exception.ResourceNotFoundException;
import com.grouphoki.energymonitoring.mapper.EnergyUsageMapper;
import com.grouphoki.energymonitoring.models.EnergyUsage;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.repository.EnergyUsageRepository;
import com.grouphoki.energymonitoring.repository.UserEntityRepository;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.EnergyUsageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.grouphoki.energymonitoring.mapper.EnergyUsageMapper.mapToEnergyUsage;
import static com.grouphoki.energymonitoring.mapper.EnergyUsageMapper.mapToEnergyUsageDto;

@Service
@Transactional
public class EnergyUsageImpl implements EnergyUsageService {
    private EnergyUsageRepository energyUsageRepository;
    private UserEntityRepository userEntityRepository;

    @Autowired
    public EnergyUsageImpl(EnergyUsageRepository energyUsageRepository, UserEntityRepository userEntityRepository) {
        this.energyUsageRepository = energyUsageRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public void saveEnergyUsage(EnergyUsageDto energyUsageDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userEntityRepository.findFirstByUsername(username);
        EnergyUsage energyUsage = mapToEnergyUsage(energyUsageDto);
        energyUsage.setCreatedBy(user);
        energyUsageRepository.save(energyUsage);
    }

    @Override
    public void updateEnergyUsage(Long id, EnergyUsageDto energyUsageDto) {
        EnergyUsage energyUsage = findById(id);
        energyUsage.setTime(energyUsageDto.getTime());
        energyUsage.setUseAmount(energyUsageDto.getUseAmount());
        energyUsageRepository.save(energyUsage);
    }

    @Override
    public void deleteEnergyUsage(Long id) {
        energyUsageRepository.deleteById(id);
    }

    @Override
    public void deleteAllByUser(UserEntity user) {
        energyUsageRepository.deleteAllByCreatedBy(user);
    }

    @Override
    public EnergyUsage findById(Long id) {
        return energyUsageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Energy usage not found"));
    }


    @Override
    public List<EnergyUsageDto> getAllEnergyUsage() {
        List<EnergyUsage> energyUsages = energyUsageRepository.findAll();
        return energyUsages.stream().map(EnergyUsageMapper::mapToEnergyUsageDto).collect(Collectors.toList());
    }

    @Override
    public List<EnergyUsageDto> getEnergyUsageByUser(String username) {
        UserEntity user = userEntityRepository.findByUsername(username);
        List<EnergyUsageDto> targetUsages = energyUsageRepository.findByCreatedBy(user);
        return targetUsages;
    }

    @Override
    public EnergyUsageDto findEnergyById(Long targetUsageId) {
        EnergyUsage energyUsage = energyUsageRepository.findById(targetUsageId).get();
        return mapToEnergyUsageDto(energyUsage);
    }

}
