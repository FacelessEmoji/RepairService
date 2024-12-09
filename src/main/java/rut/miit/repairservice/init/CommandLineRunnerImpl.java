package rut.miit.repairservice.init;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rut.miit.repairservice.dtos.main.*;
import rut.miit.repairservice.grpc.GrpcLoggingClient;
import rut.miit.repairservice.models.enums.SpecializationType;
import rut.miit.repairservice.models.enums.StatusType;
import rut.miit.repairservice.services.interfaces.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private ClientService<String> clientService;
    @Autowired
    private MasterService<String> masterService;
    @Autowired
    private OrderService<String> orderService;
    @Autowired
    private OrderPartService<String> orderPartService;
    @Autowired
    private PartService<String> partService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GrpcLoggingClient grpcLoggingClient;


    @Override
    public void run(String... args) throws Exception {
        // Создание тестовых данных
        masterService.createMaster(new MasterDTO("Gleb", "88005553535", SpecializationType.COMPUTERS));
        clientService.createClient(new ClientDTO("Nick", "123456789", "example@test.ru"));
        partService.createPart(new PartDTO("some battery", 3, new BigDecimal(5000)));
        orderService.createOrder(new OrderDTO("test description", StatusType.ACCEPTED, null, "Iphone 69 Ultra Max Screen;Iphone 69 Ultra Max Dynamic",
                clientService.getAllClients().get(0).getId(), masterService.getAllMasters().get(0).getId()));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(0).getId(),
                partService.getAllParts().get(0).getId(), 1));

        // Отправка тестового лога
        grpcLoggingClient.logAction("Action1", "EntityType1", "EntityId1", "User1", "2024-12-11T03:00:00");
    }
}
