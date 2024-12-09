package rut.miit.repairservice.contracts.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ClientDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number")
        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        String id,
        LocalDateTime created,
        LocalDateTime modified,
        TimestampedDTO timestamped
){
}

