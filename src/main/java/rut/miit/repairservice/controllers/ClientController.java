package rut.miit.repairservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.services.implementations.ClientServiceImpl;

import java.util.List;


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

        clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(ClientController.class)
                .getClientById(id)).withSelfRel());

        clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(ClientController.class)
                .getAllClients()).withRel("all-clients"));

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
        return ResponseEntity.ok(clientService.createClient(clientDTO));
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateClient(
            @RequestParam(name = "id", defaultValue = "null") String id,
            @RequestParam(name = "name", defaultValue = "null") String newName) {
        if (id.equals("null") || newName.equals("null")){
            return ResponseEntity.badRequest().body("Id and Name can't be null");
        }else if (clientService.getClientById(id).getFirstName().equals(newName)){
            return ResponseEntity.badRequest().body("New name can't be the same as the old one");
        } else return ResponseEntity.ok(clientService.updateClientName(id, newName));
    }

    @PutMapping("/update/phone")
    public ResponseEntity<?> updateClientPhone(
            @RequestParam(name = "id", defaultValue = "null") String id,
            @RequestParam(name = "phone", defaultValue = "null") String newPhone) {
        if (id.equals("null") || newPhone.equals("null")) {
            return ResponseEntity.badRequest().body("Id and Phone number can't be null");
        } else if (clientService.getClientById(id).getPhoneNumber().equals(newPhone)) {
            return ResponseEntity.badRequest().body("New phone number can't be the same as the old one");
        } else {
            return ResponseEntity.ok(clientService.updateClientPhone(id, newPhone));
        }
    }

    @PutMapping("/update/email")
    public ResponseEntity<?> updateClientEmail(
            @RequestParam(name = "id", defaultValue = "null") String id,
            @RequestParam(name = "email", defaultValue = "null") String newEmail) {
        if (id.equals("null") || newEmail.equals("null")) {
            return ResponseEntity.badRequest().body("Id and Email can't be null");
        } else if (clientService.getClientById(id).getEmail().equals(newEmail)) {
            return ResponseEntity.badRequest().body("New email can't be the same as the old one");
        } else {
            return ResponseEntity.ok(clientService.updateClientEmail(id, newEmail));
        }
    }

    //TODO: WTF? Need to fix...
    @DeleteMapping("/delete")
    public void deleteClient(@RequestParam(name = "id", defaultValue = "null") String id) {
        clientService.deleteClient(id);
    }

}
