package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "quantity", length = 4, nullable = false)
    private Integer quantity;

    @Column(name = "price", columnDefinition = "numeric(8,2)", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "part",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderPart> orderParts = new ArrayList<>();
}
