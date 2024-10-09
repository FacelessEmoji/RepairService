package rut.miit.repairservice.datafetchers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.services.implementations.ClientServiceImpl;
import java.util.List;

@DgsComponent
public class ClientDataFetcher {
    private final ClientServiceImpl clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientDataFetcher(ClientServiceImpl clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @DgsQuery
    public List<ClientDTO> clients(@InputArgument String clientId) {
        if (clientId != null) {
            // Если указан clientId, возвращаем только одного клиента
            ClientDTO client = modelMapper.map(clientService.getClientById(clientId), ClientDTO.class);
            return client != null ? List.of(client) : List.of();
        }
        // Иначе возвращаем всех клиентов
        return clientService.getAllClients();
    }

    @DgsMutation
    public ClientDTO addClient(
            @InputArgument String firstName,
            @InputArgument String phoneNumber,
            @InputArgument String email) {

        ClientDTO newClient = new ClientDTO(firstName, phoneNumber, email);
        ClientDTO createdClient = clientService.createClient(newClient);

        return createdClient;
    }

    @DgsMutation
    public ClientDTO updateClientName(
            @InputArgument String id,
            @InputArgument String name) {

        return clientService.updateClientName(id, name);
    }

    @DgsMutation
    public ClientDTO updateClientPhone(
            @InputArgument String id,
            @InputArgument String phoneNumber) {

        return clientService.updateClientPhone(id, phoneNumber);
    }

    @DgsMutation
    public ClientDTO updateClientEmail(
            @InputArgument String id,
            @InputArgument String email) {

        return clientService.updateClientEmail(id, email);
    }

    @DgsMutation
    public String deleteClient(@InputArgument String id) {
        try {
            clientService.deleteClient(id);
            return "Client deleted successfully";
        } catch (EntityNotFoundException e) {
            return "Client not found";
        } catch (Exception e) {
            return "Error deleting client";
        }
    }
}
