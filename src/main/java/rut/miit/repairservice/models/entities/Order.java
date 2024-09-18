package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.TimestampedEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends TimestampedEntity {
    @Column(name = "description", columnDefinition = "text")
    private String description;

    //Enum
    @Column(name = "status",length = 15, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "master_id")
    private Master master;

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderPart> orderParts = new ArrayList<>();
}
