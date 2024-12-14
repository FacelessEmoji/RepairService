package rut.miit.repairservice.init;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rut.miit.repairservice.dtos.main.*;
import rut.miit.repairservice.grpc.GrpcLoggingClient;
import rut.miit.repairservice.models.entities.Master;
import rut.miit.repairservice.models.entities.Part;
import rut.miit.repairservice.models.enums.SpecializationType;
import rut.miit.repairservice.models.enums.StatusType;
import rut.miit.repairservice.services.interfaces.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//        masterService.createMaster(new MasterDTO("Gleb", "88005553535", SpecializationType.COMPUTERS));
//        clientService.createClient(new ClientDTO("Nick", "123456789", "example@test.ru"));
//        partService.createPart(new PartDTO("some battery", 3, new BigDecimal(5000)));
//        orderService.createOrder(new OrderDTO("test description", StatusType.ACCEPTED, null, "Iphone 69 Ultra Max Screen;Iphone 69 Ultra Max Dynamic",
//                clientService.getAllClients().get(0).getId(), masterService.getAllMasters().get(0).getId()));
//        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(0).getId(),
//                partService.getAllParts().get(0).getId(), 1));

        masterService.createMaster(new MasterDTO("Gleb", "88005553535", SpecializationType.COMPUTERS));
        masterService.createMaster(new MasterDTO("Ivan", "88005553536", SpecializationType.PHONES));

        clientService.createClient(new ClientDTO("Nick", "123456789", "example@test.ru"));
        clientService.createClient(new ClientDTO("Alex", "987654321", "alex@test.com"));
        clientService.createClient(new ClientDTO("Julia", "654987321", "julia@test.com"));
        clientService.createClient(new ClientDTO("Michael", "112233445", "michael@test.com"));
        clientService.createClient(new ClientDTO("Sarah", "334455667", "sarah@test.com"));
        clientService.createClient(new ClientDTO("David", "556677889", "david@test.com"));
        clientService.createClient(new ClientDTO("Emily", "778899112", "emily@test.com"));
        clientService.createClient(new ClientDTO("John", "998877665", "john@test.com"));
        clientService.createClient(new ClientDTO("Kate", "223344556", "kate@test.com"));

        partService.createPart(new PartDTO("nuclear battery", 3, new BigDecimal(5000)));
        partService.createPart(new PartDTO("screen", 5, new BigDecimal(3000)));
        partService.createPart(new PartDTO("phone case", 10, new BigDecimal(1500)));
        partService.createPart(new PartDTO("charger", 7, new BigDecimal(1000)));
        partService.createPart(new PartDTO("battery", 4, new BigDecimal(2500)));
        partService.createPart(new PartDTO("camera lens", 6, new BigDecimal(4000)));
        partService.createPart(new PartDTO("headphones", 8, new BigDecimal(1500)));
        partService.createPart(new PartDTO("motherboard", 2, new BigDecimal(7500)));
        partService.createPart(new PartDTO("speaker", 5, new BigDecimal(1800)));
        partService.createPart(new PartDTO("display", 6, new BigDecimal(3000)));
        partService.createPart(new PartDTO("charging cable", 9, new BigDecimal(800)));
        partService.createPart(new PartDTO("keyboard", 4, new BigDecimal(2200)));

// Создание заказов и их привязка к мастерам и клиентам
        orderService.createOrder(new OrderDTO("Fix phone screen", StatusType.ACCEPTED, LocalDateTime.now().minusDays(1),
                "Iphone 69 Ultra Max Screen", clientService.getAllClients().get(0).getId(), masterService.getAllMasters().get(0).getId()));
        orderService.createOrder(new OrderDTO("Replace battery", StatusType.PROCESSING, LocalDateTime.now().minusDays(1),
                "some battery", clientService.getAllClients().get(1).getId(), masterService.getAllMasters().get(1).getId()));
        orderService.createOrder(new OrderDTO("Install new charger", StatusType.ACCEPTED,LocalDateTime.now().minusDays(1),
                "charger", clientService.getAllClients().get(2).getId(), masterService.getAllMasters().get(0).getId()));
        orderService.createOrder(new OrderDTO("Repair motherboard", StatusType.PROCESSING,LocalDateTime.now().minusDays(1),
                "motherboard", clientService.getAllClients().get(3).getId(), masterService.getAllMasters().get(1).getId()));
        orderService.createOrder(new OrderDTO("Camera lens replacement", StatusType.ACCEPTED, LocalDateTime.now().minusDays(1),
                "camera lens", clientService.getAllClients().get(4).getId(), masterService.getAllMasters().get(0).getId()));
        orderService.createOrder(new OrderDTO("Install new display", StatusType.ACCEPTED, LocalDateTime.now().minusDays(1),
                "display", clientService.getAllClients().get(5).getId(), masterService.getAllMasters().get(1).getId()));
        orderService.createOrder(new OrderDTO("Change keyboard", StatusType.READY, LocalDateTime.now().minusDays(1),
                "keyboard", clientService.getAllClients().get(6).getId(), masterService.getAllMasters().get(0).getId()));
        orderService.createOrder(new OrderDTO("Fix phone case", StatusType.ACCEPTED,LocalDateTime.now().minusDays(1),
                "phone case", clientService.getAllClients().get(7).getId(), masterService.getAllMasters().get(1).getId()));
        orderService.createOrder(new OrderDTO("Speaker replacement", StatusType.READY, LocalDateTime.now().minusDays(1),
                "speaker", clientService.getAllClients().get(8).getId(), masterService.getAllMasters().get(0).getId()));
        orderService.createOrder(new OrderDTO("Replace charging cable", StatusType.ACCEPTED, LocalDateTime.now().minusDays(1),
                "charging cable", clientService.getAllClients().get(0).getId(), masterService.getAllMasters().get(1).getId()));
        orderService.createOrder(new OrderDTO("Repair camera lens", StatusType.ACCEPTED, LocalDateTime.now().minusDays(1),
                "camera lens", clientService.getAllClients().get(1).getId(), masterService.getAllMasters().get(0).getId()));
        orderService.createOrder(new OrderDTO("Screen installation", StatusType.ACCEPTED, LocalDateTime.now().minusDays(1),
                "Iphone 69 Ultra Max Screen", clientService.getAllClients().get(2).getId(), masterService.getAllMasters().get(1).getId()));

        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(0).getId(), partService.getAllParts().get(0).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(1).getId(), partService.getAllParts().get(4).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(2).getId(), partService.getAllParts().get(3).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(3).getId(), partService.getAllParts().get(7).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(4).getId(), partService.getAllParts().get(5).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(5).getId(), partService.getAllParts().get(9).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(6).getId(), partService.getAllParts().get(11).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(7).getId(), partService.getAllParts().get(2).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(8).getId(), partService.getAllParts().get(8).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(9).getId(), partService.getAllParts().get(10).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(10).getId(), partService.getAllParts().get(0).getId(), 1));
        orderPartService.createOrderPart(new OrderPartDTO(orderService.getAllOrders().get(11).getId(), partService.getAllParts().get(1).getId(), 1));

        // 1. Orders grouped by master
        List<OrderDTO> orders = orderService.getAllOrders();
        Map<String, List<OrderDTO>> ordersByMaster = orders.stream()
                .collect(Collectors.groupingBy(OrderDTO::getMaster));

        // 2. Calculate revenue per master
        Map<String, BigDecimal> masterRevenues = ordersByMaster.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(order -> calculateOrderRevenue(order))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));

        // 3. Best and worst masters
        String bestMasterId = masterRevenues.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        String worstMasterId = masterRevenues.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        Master bestMaster = masterService.getMasterById(bestMasterId);
        Master worstMaster = masterService.getMasterById(worstMasterId);

        BigDecimal bestMasterRevenue = masterRevenues.getOrDefault(bestMasterId, BigDecimal.ZERO);
        BigDecimal worstMasterRevenue = masterRevenues.getOrDefault(worstMasterId, BigDecimal.ZERO);

        // 4. Turnover for the day
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        BigDecimal dailyTurnover = orders.stream()
                .map(this::calculateOrderRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5. Total completed orders
        long completedOrdersCount = orders.size();

        // 6. Total used parts
        List<OrderPartDTO> allOrderParts = orderPartService.getAllOrderParts();
        int totalUsedParts = allOrderParts.stream()
                .mapToInt(OrderPartDTO::getQuantity)
                .sum();

        // 7. Average order cost
        BigDecimal totalRevenue = orders.stream()
                .map(this::calculateOrderRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderCost = completedOrdersCount > 0
                ? totalRevenue.divide(BigDecimal.valueOf(completedOrdersCount), BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;

        // Combine statistics into one log message
        String statsLogMessage = String.format(
                "Best Master: %s (Revenue: %s), Worst Master: %s (Revenue: %s), Daily Turnover: %s, " +
                        "Completed Orders: %d, Total Used Parts: %d, Average Order Cost: %s",
                bestMaster.getFirstName(), bestMasterRevenue,
                worstMaster.getFirstName(), worstMasterRevenue,
                dailyTurnover, completedOrdersCount, totalUsedParts, averageOrderCost
        );

        // Send the log through gRPC client
        grpcLoggingClient.logAction("Stats", "SystemStats", "DailyStats", statsLogMessage, LocalDateTime.now().toString());
    }

    private BigDecimal calculateOrderRevenue(OrderDTO order) {
        List<OrderPartDTO> orderParts = orderPartService.getAllOrderParts().stream()
                .filter(op -> op.getOrder().equals(order.getId()))
                .toList();

        return orderParts.stream()
                .map(orderPart -> {
                    Part part = partService.getPartById(orderPart.getPart());
                    return part.getPrice().multiply(BigDecimal.valueOf(orderPart.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}