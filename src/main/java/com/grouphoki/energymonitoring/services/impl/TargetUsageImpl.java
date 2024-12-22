package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.TargetUsageDto;
import com.grouphoki.energymonitoring.mapper.TargetUsageMapper;
import com.grouphoki.energymonitoring.models.TargetUsage;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.repository.TargetUsageRepository;
import com.grouphoki.energymonitoring.repository.UserEntityRepository;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.TargetUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.grouphoki.energymonitoring.mapper.TargetUsageMapper.mapToTargetUsage;
import static com.grouphoki.energymonitoring.mapper.TargetUsageMapper.mapToTargetUsageDto;

@Service
public class TargetUsageImpl implements TargetUsageService {
    private TargetUsageRepository targetUsageRepository;
    private UserEntityRepository userEntityRepository;

    @Autowired
    public TargetUsageImpl(TargetUsageRepository targetUsageRepository, UserEntityRepository userEntityRepository) {
        this.targetUsageRepository = targetUsageRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public void saveTargetUsage(TargetUsageDto targetUsageDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userEntityRepository.findFirstByUsername(username);
        TargetUsage targetUsage = mapToTargetUsage(targetUsageDto);
        targetUsage.setCreatedBy(user);
        targetUsageRepository.save(targetUsage);
    }

    @Override
    public void updateTargetUsage(TargetUsageDto targetUsageDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userEntityRepository.findFirstByUsername(username);
        TargetUsage targetUsage = mapToTargetUsage(targetUsageDto);
        targetUsage.setCreatedBy(user);
        targetUsageRepository.save(targetUsage);
    }

    @Override
    public void deleteTargetUsage(Long targetUsageId) {
        targetUsageRepository.deleteById(targetUsageId);
    }

    @Override
    public void saveTargetUsage(UserEntity userEntity, TargetUsageDto targetUsageDto) {

    }

    @Override
    public List<TargetUsageDto> getAllTargetUsage() {
        List<TargetUsage> targetUsages = targetUsageRepository.findAll();
        return targetUsages.stream().map(TargetUsageMapper::mapToTargetUsageDto).collect(Collectors.toList());
    }

    @Override
    public TargetUsageDto findTargetById(Long targetUsageId) {
        TargetUsage targetUsage = targetUsageRepository.findById(targetUsageId).get();
        return mapToTargetUsageDto(targetUsage);
    }

}
