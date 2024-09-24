package rut.miit.repairservice.dtos.base;

import org.springframework.hateoas.RepresentationModel;

public class BaseDTO extends RepresentationModel<BaseDTO> {
    private String id;

    public BaseDTO(String id) {
        this.id = id;
    }

    public BaseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
