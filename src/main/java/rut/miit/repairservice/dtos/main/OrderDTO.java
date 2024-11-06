package rut.miit.repairservice.dtos.main;

import rut.miit.repairservice.dtos.base.TimestampedDTO;
import rut.miit.repairservice.models.enums.StatusType;

import java.time.LocalDateTime;

public class OrderDTO extends TimestampedDTO {
    private String description;
    private StatusType status;
    private LocalDateTime estimatedCompletionTime;
    private String client;
    private String master;

    public OrderDTO(String description, StatusType status, LocalDateTime estimatedCompletionTime, String client, String master) {
        this.description = description;
        this.status = status;
        this.estimatedCompletionTime = estimatedCompletionTime;
        this.client = client;
        this.master = master;
    }

    public OrderDTO() {
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "description='" + description + '\'' +
                ", status=" + status +
                ", client='" + client + '\'' +
                ", master='" + master + '\'' +
                '}';
    }
}
