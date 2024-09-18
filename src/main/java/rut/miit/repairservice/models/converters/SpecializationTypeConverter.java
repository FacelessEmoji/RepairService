package rut.miit.repairservice.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import rut.miit.repairservice.models.enums.SpecializationType;



@Converter(autoApply = true)
public class SpecializationTypeConverter implements AttributeConverter<SpecializationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SpecializationType attribute) {
        return attribute == null ? null : attribute.getSpecializationCode();
    }

    @Override
    public SpecializationType convertToEntityAttribute(Integer dbData) {
        for (SpecializationType specializationType : SpecializationType.values()) {
            if (specializationType.getSpecializationCode() == dbData) {
                return specializationType;
            }
        }
        throw new IllegalArgumentException("Unknown SpecializationType code: " + dbData);
    }
}

