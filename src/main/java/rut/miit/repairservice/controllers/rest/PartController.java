
package rut.miit.repairservice.controllers.rest;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.config.ActionLinkGenerator;
import rut.miit.repairservice.dtos.main.PartDTO;
import rut.miit.repairservice.services.implementations.PartServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/part")
public class PartController {
    private PartServiceImpl partService;
    private ModelMapper modelMapper;

    @Autowired
    public void setPartService(PartServiceImpl partService) {
        this.partService = partService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartDTO> getPartById(@PathVariable String id) {
        PartDTO partDTO = modelMapper.map(partService.getPartById(id), PartDTO.class);

        partDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class).getPartById(id)).withSelfRel());
        partDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class).getAllParts()).withRel("all-parts"));

        Map<String, Map<String, String>> actions = new HashMap<>();

        String baseUrl = "http://localhost:8081/part";

        actions.put("get", ActionLinkGenerator.getAction(baseUrl, id));
        actions.put("updateName", ActionLinkGenerator.createAction(baseUrl, "/update/name", id, "PUT"));
        actions.put("updateQuantity", ActionLinkGenerator.createAction(baseUrl, "/update/quantity", id, "PUT"));
        actions.put("updatePrice", ActionLinkGenerator.createAction(baseUrl, "/update/price", id, "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, id));

        partDTO.setActions(actions);

        return ResponseEntity.ok(partDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllParts() {
        List<PartDTO> parts = partService.getAllParts();
        for (PartDTO part : parts) {
            part.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                    .methodOn(PartController.class)
                    .getPartById(part.getId())).withSelfRel());
        }
        return ResponseEntity.ok(parts);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPart(@RequestBody PartDTO partDTO) {
        PartDTO createdPart = partService.createPart(partDTO);

        String baseUrl = "http://localhost:8081/part";

        Map<String, Map<String, String>> actions = new HashMap<>();

        actions.put("get", ActionLinkGenerator.getAction(baseUrl, createdPart.getId()));
        actions.put("updateName", ActionLinkGenerator.createAction(baseUrl, "/update/name", createdPart.getId(), "PUT"));
        actions.put("updateQuantity", ActionLinkGenerator.createAction(baseUrl, "/update/quantity", createdPart.getId(), "PUT"));
        actions.put("updatePrice", ActionLinkGenerator.createAction(baseUrl, "/update/price", createdPart.getId(), "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, createdPart.getId()));

        createdPart.setActions(actions);

        return ResponseEntity.ok(createdPart);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updatePartName(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name") String newName) {
        if (id == null || newName == null) {
            return ResponseEntity.badRequest().body("Id and Name can't be null");
        } else if (partService.getPartById(id).getName().equals(newName)) {
            return ResponseEntity.badRequest().body("New name can't be the same as the old one");
        } else {
            PartDTO updatedPart = partService.updatePartName(id, newName);

            updatedPart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class)
                    .updatePartQuantity(id, null)).withRel("updateQuantity"));
            updatedPart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class)
                    .updatePartPrice(id, null)).withRel("updatePrice"));

            return ResponseEntity.ok(updatedPart);
        }
    }

    @PutMapping("/update/quantity")
    public ResponseEntity<?> updatePartQuantity(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "quantity") Integer quantity) {
        if (id == null || quantity == null) {
            return ResponseEntity.badRequest().body("Id and Quantity can't be null");
        } else {
            PartDTO updatedPart = partService.updatePartQuantity(id, quantity);

            updatedPart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class)
                    .updatePartName(id, "")).withRel("updateName"));
            updatedPart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class)
                    .updatePartPrice(id, null)).withRel("updatePrice"));

            return ResponseEntity.ok(updatedPart);
        }
    }

    @PutMapping("/update/price")
    public ResponseEntity<?> updatePartPrice(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "price") BigDecimal price) {
        if (id == null || price == null) {
            return ResponseEntity.badRequest().body("Id and Price can't be null");
        } else {
            PartDTO updatedPart = partService.updatePartPrice(id, price);

            updatedPart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class)
                    .updatePartName(id, "")).withRel("updateName"));
            updatedPart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PartController.class)
                    .updatePartQuantity(id, null)).withRel("updateQuantity"));

            return ResponseEntity.ok(updatedPart);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePart(@RequestParam(name = "id") String id) {
        try {
            partService.deletePart(id);
            return ResponseEntity.ok("Part deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Part not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting part");
        }
    }
}

