package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.models.entities.Client;
import rut.miit.repairservice.repositories.ClientRepository;
import rut.miit.repairservice.services.interfaces.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService<String> {
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(u -> modelMapper.map(u, ClientDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Client getClientById(String s) {
        return clientRepository.findById(s).orElse(null);
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        return modelMapper.map(clientRepository.saveAndFlush(modelMapper.map(clientDTO, Client.class)), ClientDTO.class);
    }

    @Override
    public ClientDTO updateClient(String s, ClientDTO clientDTO) {
        Client client = clientRepository.findById(s).orElseThrow();
        client.setFirstName(clientDTO.getFirstName());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setEmail(clientDTO.getEmail());
        return modelMapper.map(clientRepository.saveAndFlush(client), ClientDTO.class);
    }

    @Override
    public void deleteClient(String s) {
        clientRepository.deleteById(s);
    }

    @Override
    public ClientDTO updateClientName(String s, String name) {
        Client client = clientRepository.findById(s).orElseThrow();
        client.setFirstName(name);
        clientRepository.save(client);
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO updateClientPhone(String s, String phone) {
        Client client = clientRepository.findById(s).orElseThrow();
        client.setPhoneNumber(phone);
        clientRepository.save(client);
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO updateClientEmail(String s, String email) {
        Client client = clientRepository.findById(s).orElseThrow();
        client.setEmail(email);
        clientRepository.save(client);
        return modelMapper.map(client, ClientDTO.class);
    }
}
