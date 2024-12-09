package rut.miit.repairservice.contracts.dtos;

import jakarta.validation.constraints.*;

public record OrderPartDTO(
        @NotBlank(message = "Order ID is required")
        String order,

        @NotBlank(message = "Part ID is required")
        String part,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        String id,
        BaseDTO base
){}
