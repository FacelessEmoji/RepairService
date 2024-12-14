package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.grpc.GrpcLoggingClient;
import rut.miit.repairservice.models.entities.Client;
import rut.miit.repairservice.repositories.ClientRepository;
import rut.miit.repairservice.services.interfaces.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService<String> {
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private GrpcLoggingClient grpcLoggingClient;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setGrpcLoggingClient(GrpcLoggingClient grpcLoggingClient) {
        this.grpcLoggingClient = grpcLoggingClient;
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
        Client savedClient = clientRepository.saveAndFlush(modelMapper.map(clientDTO, Client.class));
        ClientDTO result = modelMapper.map(savedClient, ClientDTO.class);

        grpcLoggingClient.logAction(
                "CREATE",
                "Client",
                savedClient.getId(),
                String.format("Client created: Name=%s, Email=%s, Phone=%s",
                        savedClient.getFirstName(), savedClient.getEmail(), savedClient.getPhoneNumber()),
                java.time.ZonedDateTime.now().toString()
        );

        return result;
    }

    @Override
    public ClientDTO updateClient(String s, ClientDTO clientDTO) {
        Client client = clientRepository.findById(s).orElseThrow();
        client.setFirstName(clientDTO.getFirstName());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setEmail(clientDTO.getEmail());

        grpcLoggingClient.logAction(
                "UPDATE",
                "Client",
                client.getId(),
                String.format("Client updated: Name=%s, Email=%s, Phone=%s",
                        client.getFirstName(), client.getEmail(), client.getPhoneNumber()),
                java.time.ZonedDateTime.now().toString()
        );

        return modelMapper.map(clientRepository.saveAndFlush(client), ClientDTO.class);
    }

    @Override
    public void deleteClient(String clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        clientRepository.delete(client);

        grpcLoggingClient.logAction(
                "DELETE",
                "Client",
                clientId,
                String.format("Client deleted: Name=%s, Email=%s",
                        client.getFirstName(), client.getEmail()),
                java.time.ZonedDateTime.now().toString()
        );
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
