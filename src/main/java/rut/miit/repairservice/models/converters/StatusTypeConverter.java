package rut.miit.repairservice.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import rut.miit.repairservice.models.enums.StatusType;

@Converter(autoApply = true)
public class StatusTypeConverter implements AttributeConverter<StatusType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusType attribute) {
        return attribute == null ? null : attribute.getStatusCode();
    }

    @Override
    public StatusType convertToEntityAttribute(Integer dbData) {
        for (StatusType statusType : StatusType.values()) {
            if (statusType.getStatusCode() == dbData) {
                return statusType;
            }
        }
        throw new IllegalArgumentException("Unknown StatusType code: " + dbData);
    }
}
