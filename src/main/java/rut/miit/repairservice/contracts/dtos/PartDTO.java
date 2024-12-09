package rut.miit.repairservice.contracts.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PartDTO(
        @NotBlank(message = "Part name is required")
        String name,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        BigDecimal price,

        String id,
        BaseDTO base
){

}
