package rut.miit.repairservice.contracts.dtos;

import jakarta.validation.constraints.*;
import rut.miit.repairservice.models.enums.SpecializationType;

import java.time.LocalDateTime;

public record MasterDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        SpecializationType specialization,

        String id,
        LocalDateTime created,
        LocalDateTime modified,
        TimestampedDTO timestamped
){
}
