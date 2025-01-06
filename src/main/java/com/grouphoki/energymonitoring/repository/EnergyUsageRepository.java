package com.grouphoki.energymonitoring.repository;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.models.EnergyUsage;
import com.grouphoki.energymonitoring.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnergyUsageRepository extends JpaRepository<EnergyUsage, Long> {
    @Query("SELECT new com.grouphoki.energymonitoring.dto.EnergyUsageDto(e.id, e.time, e.useAmount, e.createdAt, e.updatedAt, e.createdBy) " +
            "FROM EnergyUsage e WHERE e.createdBy = :createdBy")
    List<EnergyUsageDto> findByCreatedBy(@Param("createdBy") UserEntity createdBy);

    void deleteAllByCreatedBy(UserEntity user);  
}
