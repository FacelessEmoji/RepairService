package rut.miit.repairservice.services.implementations;

import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.services.interfaces.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService<String> {
    @Override
    public List<ClientDTO> getAllClients() {
        return List.of();
    }

    @Override
    public ClientDTO getClientById(String s) {
        return null;
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ClientDTO updateClient(String s, ClientDTO clientDTO) {
        return null;
    }

    @Override
    public void deleteClient(String s) {

    }

    @Override
    public ClientDTO updateClientName(String s, String name) {
        return null;
    }

    @Override
    public ClientDTO updateClientPhone(String s, String phone) {
        return null;
    }

    @Override
    public ClientDTO updateClientEmail(String s, String email) {
        return null;
    }
}
