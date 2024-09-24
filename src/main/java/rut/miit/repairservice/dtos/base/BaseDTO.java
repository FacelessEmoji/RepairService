package rut.miit.repairservice.dtos.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

public class BaseDTO extends RepresentationModel<BaseDTO> {
    private String id;
    @JsonProperty("_actions")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Map<String, String>> actions;

    public BaseDTO(String id) {
        this.id = id;
    }

    public BaseDTO() {
    }

    public Map<String, Map<String, String>> getActions() {
        return actions;
    }

    public void setActions(Map<String, Map<String, String>> actions) {
        this.actions = actions;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
