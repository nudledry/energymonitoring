package com.grouphoki.energymonitoring.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Content should not be empty")
    private String content;

    @NotEmpty(message = "Image URL should not be empty")
    private String imageUrl;

    @NotEmpty(message = "Source should not be empty")
    private String source;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
