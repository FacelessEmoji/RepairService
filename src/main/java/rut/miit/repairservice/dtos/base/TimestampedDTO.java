package rut.miit.repairservice.dtos.base;

import java.time.LocalDateTime;

public class TimestampedDTO extends BaseDTO {
    private LocalDateTime created;
    private LocalDateTime modified;

    public TimestampedDTO(LocalDateTime created, LocalDateTime modified) {
        this.created = created;
        this.modified = modified;
    }

    public TimestampedDTO() {

    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
