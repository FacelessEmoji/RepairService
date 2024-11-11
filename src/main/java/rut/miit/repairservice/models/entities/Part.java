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

    @Column(name = "quantity", length = 4, nullable = false, columnDefinition = "integer DEFAULT 1")
    private Integer quantity;

    @Column(name = "price", columnDefinition = "numeric(8,2) DEFAULT 1.00", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderPart> orderParts = new ArrayList<>();

    public Part(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Part() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
