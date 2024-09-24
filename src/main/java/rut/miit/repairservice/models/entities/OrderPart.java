package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.BaseEntity;

@Entity
@Table(name = "order_parts")
public class OrderPart extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    @Column(name = "quantity", length = 4, nullable = false)
    private Integer quantity;

    public OrderPart(Order order, Part part, Integer quantity) {
        this.order = order;
        this.part = part;
        this.quantity = quantity;
    }

    public OrderPart() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
