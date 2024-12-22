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
public class TargetUsageDto {
    private Long id;

    @NotNull(message = "Time is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime time;

    @NotNull(message = "Target is required")
    private Double target;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserEntity createdBy;
}
