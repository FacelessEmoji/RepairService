package rut.miit.repairservice.services.interfaces;

import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.models.entities.Client;

import java.util.List;

public interface ClientService<ID> {
    List<ClientDTO> getAllClients();
    Client getClientById(ID id);
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO updateClient(ID id, ClientDTO clientDTO);
    void deleteClient(ID id);

    ClientDTO updateClientName(ID id, String name);
    ClientDTO updateClientPhone(ID id, String phone);
    ClientDTO updateClientEmail(ID id, String email);
}
