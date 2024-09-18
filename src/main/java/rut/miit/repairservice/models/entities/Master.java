package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.TimestampedEntity;
import rut.miit.repairservice.models.converters.SpecializationTypeConverter;
import rut.miit.repairservice.models.enums.SpecializationType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master extends TimestampedEntity {
    @Column(name = "firstName", length = 255, nullable = false)
    private String firstName;

    @Column(name = "phoneNumber", length = 10, nullable = false)
    private String phoneNumber;

    @Convert(converter = SpecializationTypeConverter.class)
    @Column(name = "specialization", nullable = false)
    private SpecializationType specialization;

    @OneToMany(mappedBy = "master",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
