package rut.miit.repairservice.contracts.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record BaseDTO(
        String id,
        @JsonProperty("_actions")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, Map<String, String>> actions
) {
}
