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
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @Convert(converter = SpecializationTypeConverter.class)
    @Column(name = "specialization", nullable = false)
    private SpecializationType specialization;

    @OneToMany(mappedBy = "master", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Master(String firstName, String phoneNumber, SpecializationType specialization) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
    }

    public Master() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SpecializationType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationType specialization) {
        this.specialization = specialization;
    }
}
