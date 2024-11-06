package rut.miit.repairservice.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.config.ActionLinkGenerator;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.services.implementations.ClientServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientServiceImpl clientService;
    private ModelMapper modelMapper;

    @Autowired
    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable String id) {
        ClientDTO clientDTO = modelMapper.map(clientService.getClientById(id), ClientDTO.class);

        clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class).getClientById(id)).withSelfRel());
        clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class).getAllClients()).withRel("all-clients"));

        Map<String, Map<String, String>> actions = new HashMap<>();

        String baseUrl = "http://localhost:8081/client";

        actions.put("get", ActionLinkGenerator.getAction(baseUrl, id));
        actions.put("updateEmail", ActionLinkGenerator.createAction(baseUrl, "/update/email", id, "PUT"));
        actions.put("updateName", ActionLinkGenerator.createAction(baseUrl, "/update/name", id, "PUT"));
        actions.put("updatePhone", ActionLinkGenerator.createAction(baseUrl, "/update/phone", id, "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, id));

        clientDTO.setActions(actions);

        return ResponseEntity.ok(clientDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        for (ClientDTO client : clients) {
            client.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                    .methodOn(ClientController.class)
                    .getClientById(client.getId())).withSelfRel());
        }
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.createClient(clientDTO);

        String baseUrl = "http://localhost:8081/client";

        Map<String, Map<String, String>> actions = new HashMap<>();

        actions.put("get", ActionLinkGenerator.getAction(baseUrl, createdClient.getId()));

        actions.put("updateEmail", ActionLinkGenerator.createAction(baseUrl, "/update/email", createdClient.getId(), "PUT"));
        actions.put("updateName", ActionLinkGenerator.createAction(baseUrl, "/update/name", createdClient.getId(), "PUT"));
        actions.put("updatePhone", ActionLinkGenerator.createAction(baseUrl, "/update/phone", createdClient.getId(), "PUT"));

        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, createdClient.getId()));
        createdClient.setActions(actions);

        return ResponseEntity.ok(createdClient);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateClientName(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name") String newName) {
        if (id == null || newName == null) {
            return ResponseEntity.badRequest().body("Id and Name can't be null");
        } else if (clientService.getClientById(id).getFirstName().equals(newName)) {
            return ResponseEntity.badRequest().body("New name can't be the same as the old one");
        } else {
            ClientDTO updatedClient = clientService.updateClientName(id, newName);

            updatedClient.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .updateClientPhone(id, "")).withRel("updatePhone"));
            updatedClient.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .updateClientEmail(id, "")).withRel("updateEmail"));

            return ResponseEntity.ok(updatedClient);
        }
    }

    @PutMapping("/update/phone")
    public ResponseEntity<?> updateClientPhone(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "phone") String newPhone) {
        if (id == null || newPhone == null) {
            return ResponseEntity.badRequest().body("Id and Phone number can't be null");
        } else if (clientService.getClientById(id).getPhoneNumber().equals(newPhone)) {
            return ResponseEntity.badRequest().body("New phone number can't be the same as the old one");
        } else {
            ClientDTO updatedClient = clientService.updateClientPhone(id, newPhone);

            updatedClient.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .updateClientName(id, "")).withRel("updateName"));
            updatedClient.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .updateClientEmail(id, "")).withRel("updateEmail"));

            return ResponseEntity.ok(updatedClient);
        }
    }

    @PutMapping("/update/email")
    public ResponseEntity<?> updateClientEmail(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "email") String newEmail) {
        if (id == null || newEmail == null) {
            return ResponseEntity.badRequest().body("Id and Email can't be null");
        } else if (clientService.getClientById(id).getEmail().equals(newEmail)) {
            return ResponseEntity.badRequest().body("New email can't be the same as the old one");
        } else {
            ClientDTO updatedClient = clientService.updateClientEmail(id, newEmail);

            updatedClient.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .updateClientName(id, "")).withRel("updateName"));
            updatedClient.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .updateClientPhone(id, "")).withRel("updatePhone"));

            return ResponseEntity.ok(updatedClient);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClient(@RequestParam(name = "id") String id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok("Client deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting client");
        }
    }
}
