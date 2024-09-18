package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.TimestampedEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends TimestampedEntity {
    @Column(name = "firstName", length = 255, nullable = false)
    private String firstName;

    @Column(name = "phoneNumber", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @OneToMany(mappedBy = "client",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
