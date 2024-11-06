package rut.miit.repairservice.models.entities;

import jakarta.persistence.*;
import rut.miit.repairservice.models.baseEntities.TimestampedEntity;
import rut.miit.repairservice.models.converters.StatusTypeConverter;
import rut.miit.repairservice.models.enums.StatusType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends TimestampedEntity {
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Convert(converter = StatusTypeConverter.class)
    @Column(name = "status",length = 15, nullable = false)
    private StatusType status;

    @Column(name = "estimated_completion_time")
    private LocalDateTime estimatedCompletionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    private Master master;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderPart> orderParts = new ArrayList<>();

    public Order(String description, StatusType status, LocalDateTime estimatedCompletionTime, Client client, Master master) {
        this.description = description;
        this.status = status;
        this.estimatedCompletionTime = estimatedCompletionTime;
        this.client = client;
        this.master = master;
    }

    public Order() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDateTime getEstimatedCompletionTime() {
        return estimatedCompletionTime;
    }

    public void setEstimatedCompletionTime(LocalDateTime estimatedCompletionTime) {
        this.estimatedCompletionTime = estimatedCompletionTime;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
