package rut.miit.repairservice.controllers.rest;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.config.ActionLinkGenerator;
import rut.miit.repairservice.dtos.main.MasterDTO;
import rut.miit.repairservice.models.enums.SpecializationType;
import rut.miit.repairservice.services.implementations.MasterServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/master")
public class MasterController {
    private MasterServiceImpl masterService;
    private ModelMapper modelMapper;

    @Autowired
    public void setMasterService(MasterServiceImpl masterService) {
        this.masterService = masterService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterDTO> getMasterById(@PathVariable String id) {
        MasterDTO masterDTO = modelMapper.map(masterService.getMasterById(id), MasterDTO.class);

        masterDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).getMasterById(id)).withSelfRel());
        masterDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).getAllMasters()).withRel("all-masters"));

        String baseUrl = "http://localhost:8081/master";

        Map<String, Map<String, String>> actions = new HashMap<>();
        actions.put("get", ActionLinkGenerator.getAction(baseUrl, id));
        actions.put("updatePhone", ActionLinkGenerator.createAction(baseUrl, "/update/phone", id, "PUT"));
        actions.put("updateName", ActionLinkGenerator.createAction(baseUrl, "/update/name", id, "PUT"));
        actions.put("updateSpecialization", ActionLinkGenerator.createAction(baseUrl, "/update/specialization", id, "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, id));

        masterDTO.setActions(actions);

        return ResponseEntity.ok(masterDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMasters() {
        List<MasterDTO> masters = masterService.getAllMasters();
        for (MasterDTO master : masters) {
            master.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).getMasterById(master.getId())).withSelfRel());
        }
        return ResponseEntity.ok(masters);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMaster(@RequestBody MasterDTO masterDTO) {
        MasterDTO createdMaster = masterService.createMaster(masterDTO);

        String baseUrl = "http://localhost:8081/master";

        Map<String, Map<String, String>> actions = new HashMap<>();

        actions.put("get", ActionLinkGenerator.getAction(baseUrl, createdMaster.getId()));
        actions.put("updatePhone", ActionLinkGenerator.createAction(baseUrl, "/update/phone", createdMaster.getId(), "PUT"));
        actions.put("updateName", ActionLinkGenerator.createAction(baseUrl, "/update/name", createdMaster.getId(), "PUT"));
        actions.put("updateSpecialization", ActionLinkGenerator.createAction(baseUrl, "/update/specialization", createdMaster.getId(), "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, createdMaster.getId()));

        createdMaster.setActions(actions);

        return ResponseEntity.ok(createdMaster);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateMasterName(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name") String newName) {
        if (id == null || newName == null) {
            return ResponseEntity.badRequest().body("Id and Name can't be null");
        } else if (masterService.getMasterById(id).getFirstName().equals(newName)) {
            return ResponseEntity.badRequest().body("New name can't be the same as the old one");
        } else {
            MasterDTO updatedMaster = masterService.updateMasterName(id, newName);

            updatedMaster.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).updateMasterPhone(id, "")).withRel("updatePhone"));
            updatedMaster.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).updateMasterSpecialization(id, null)).withRel("updateSpecialization"));

            return ResponseEntity.ok(updatedMaster);
        }
    }

    @PutMapping("/update/phone")
    public ResponseEntity<?> updateMasterPhone(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "phone") String newPhone) {
        if (id == null || newPhone == null) {
            return ResponseEntity.badRequest().body("Id and Phone number can't be null");
        } else if (masterService.getMasterById(id).getPhoneNumber().equals(newPhone)) {
            return ResponseEntity.badRequest().body("New phone number can't be the same as the old one");
        } else {
            MasterDTO updatedMaster = masterService.updateMasterPhone(id, newPhone);

            updatedMaster.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).updateMasterName(id, "")).withRel("updateName"));
            updatedMaster.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).updateMasterSpecialization(id, null)).withRel("updateSpecialization"));

            return ResponseEntity.ok(updatedMaster);
        }
    }

    @PutMapping("/update/specialization")
    public ResponseEntity<?> updateMasterSpecialization(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "specialization") String specialization) {
        if (id == null || specialization == null) {
            return ResponseEntity.badRequest().body("Id and Specialization can't be null");
        } else {
            SpecializationType specializationType = SpecializationType.valueOf(specialization.toUpperCase());
            MasterDTO updatedMaster = masterService.updateMasterSpecialization(id, specializationType);

            updatedMaster.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).updateMasterName(id, "")).withRel("updateName"));
            updatedMaster.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MasterController.class).updateMasterPhone(id, "")).withRel("updatePhone"));

            return ResponseEntity.ok(updatedMaster);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMaster(@RequestParam(name = "id") String id) {
        try {
            masterService.deleteMaster(id);
            return ResponseEntity.ok("Master deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Master not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting master");
        }
    }
}
