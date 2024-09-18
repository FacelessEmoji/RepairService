package rut.miit.repairservice.dtos.base;

public class BaseDTO {
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
