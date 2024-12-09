package rut.miit.repairservice.contracts.dtos;

import jakarta.validation.constraints.*;
import rut.miit.repairservice.models.enums.StatusType;

import java.time.LocalDateTime;

public record OrderDTO(
        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Status is required")
        StatusType status,

        LocalDateTime estimatedCompletionTime,

        String requiredParts,

        @NotBlank(message = "Client ID is required")
        String client,

        @NotBlank(message = "Master ID is required")
        String master,

        String id,
        LocalDateTime created,
        LocalDateTime modified,
        TimestampedDTO timestamped
){}
