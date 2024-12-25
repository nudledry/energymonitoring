package com.grouphoki.energymonitoring.dto;

import com.grouphoki.energymonitoring.models.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyUsageDto {
    private Long id;

    @NotNull(message = "Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    @NotNull(message = "Amount is required")
    private Double useAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserEntity createdBy;
}
