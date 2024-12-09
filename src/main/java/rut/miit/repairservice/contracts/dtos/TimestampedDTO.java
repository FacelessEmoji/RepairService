package rut.miit.repairservice.contracts.dtos;

import java.time.LocalDateTime;

public record TimestampedDTO(
        String id,
        LocalDateTime created,
        LocalDateTime modified,
        BaseDTO base
) {
}
