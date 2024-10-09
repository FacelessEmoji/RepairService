package rut.miit.repairservice.datafetchers;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import rut.miit.repairservice.dtos.main.ClientDTO;
import rut.miit.repairservice.models.entities.Client;
import rut.miit.repairservice.repositories.ClientRepository;
import rut.miit.repairservice.services.implementations.ClientServiceImpl;
import rut.miit.repairservice.services.interfaces.ClientService;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ClientDataFetcher {
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @DgsQuery
//    public List<Client> clients(@InputArgument String clientId) {
////        if (username == null) {
////            return userRepository.findAll();
////        }
////        Optional<User> one = userRepository.findByUsername(username);
////        return one.map(List::of).orElseGet(List::of);
//        return clientRepository.findAll();
//    }

    private ClientServiceImpl clientService;

    @Autowired
    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

        @DgsQuery
    public List<ClientDTO> clients(@InputArgument String clientId) {
//        if (username == null) {
//            return userRepository.findAll();
//        }
//        Optional<User> one = userRepository.findByUsername(username);
//        return one.map(List::of).orElseGet(List::of);
        return clientService.getAllClients();
    }
}