package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.BaseEntity;

@Entity
@Table(name = "orderParts")
public class OrderPart extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_id")
    private Part part;

    @Column(name = "quantity", length = 4, nullable = false)
    private Integer quantity;
}
