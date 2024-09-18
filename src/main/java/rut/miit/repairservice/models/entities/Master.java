package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.TimestampedEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master extends TimestampedEntity {
    @Column(name = "firstName", length = 255, nullable = false)
    private String firstName;

    @Column(name = "phoneNumber", length = 10, nullable = false)
    private String phoneNumber;

    //Enum
    @Column(name = "specialization", nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "master",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
