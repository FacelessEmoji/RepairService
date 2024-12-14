package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.OrderPartDTO;
import rut.miit.repairservice.grpc.GrpcLoggingClient;
import rut.miit.repairservice.models.entities.OrderPart;
import rut.miit.repairservice.repositories.OrderPartRepository;
import rut.miit.repairservice.repositories.OrderRepository;
import rut.miit.repairservice.repositories.PartRepository;
import rut.miit.repairservice.services.interfaces.OrderPartService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderPartServiceImpl implements OrderPartService<String> {
    private OrderPartRepository orderPartRepository;
    private OrderRepository orderRepository;
    private PartRepository partRepository;
    private ModelMapper modelMapper;
    private GrpcLoggingClient grpcLoggingClient;

    @Autowired
    public void setOrderPartRepository(OrderPartRepository orderPartRepository) {
        this.orderPartRepository = orderPartRepository;
    }
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Autowired
    public void setPartRepository(PartRepository partRepository) {
        this.partRepository = partRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setGrpcLoggingClient(GrpcLoggingClient grpcLoggingClient) {
        this.grpcLoggingClient = grpcLoggingClient;
    }

    @Override
    public List<OrderPartDTO> getAllOrderParts() {
        return orderPartRepository.findAll().stream()
                .map(op -> modelMapper.map(op, OrderPartDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderPart getOrderPartById(String s) {
        return orderPartRepository.findById(s).orElse(null);
    }
    @Override
    public OrderPartDTO createOrderPart(OrderPartDTO orderPartDTO) {
        OrderPart orderPart = modelMapper.map(orderPartDTO, OrderPart.class);
        orderPart.setOrder(orderRepository.findById(orderPartDTO.getOrder()).orElseThrow());
        orderPart.setPart(partRepository.findById(orderPartDTO.getPart()).orElseThrow());

        OrderPart savedOrderPart = orderPartRepository.saveAndFlush(orderPart);

        grpcLoggingClient.logAction(
                "CREATE",
                "OrderPart",
                savedOrderPart.getId(),
                String.format(
                        "OrderPart created. Order ID: %s, Part ID: %s, Quantity: %d",
                        savedOrderPart.getOrder().getId(),
                        savedOrderPart.getPart().getId(),
                        savedOrderPart.getQuantity()
                ),
                java.time.ZonedDateTime.now().toString()
        );

        return modelMapper.map(savedOrderPart, OrderPartDTO.class);
    }

    @Override
    public OrderPartDTO updateOrderPart(String s, OrderPartDTO orderPartDTO) {
        OrderPart orderPart = orderPartRepository.findById(s).orElseThrow();
        orderPart.setQuantity(orderPartDTO.getQuantity());
        orderPart.setPart(partRepository.findById(orderPartDTO.getPart()).orElseThrow());
        orderPart.setOrder(orderRepository.findById(orderPartDTO.getOrder()).orElseThrow());

        OrderPart updatedOrderPart = orderPartRepository.saveAndFlush(orderPart);

        grpcLoggingClient.logAction(
                "UPDATE",
                "OrderPart",
                updatedOrderPart.getId(),
                String.format(
                        "OrderPart updated. Order ID: %s, Part ID: %s, Quantity: %d",
                        updatedOrderPart.getOrder().getId(),
                        updatedOrderPart.getPart().getId(),
                        updatedOrderPart.getQuantity()
                ),
                java.time.ZonedDateTime.now().toString()
        );

        return modelMapper.map(updatedOrderPart, OrderPartDTO.class);
    }

    @Override
    public void deleteOrderPart(String s) {
        OrderPart orderPart = orderPartRepository.findById(s).orElseThrow();
        String details = String.format(
                "Deleting OrderPart. Order ID: %s, Part ID: %s, Quantity: %d",
                orderPart.getOrder().getId(),
                orderPart.getPart().getId(),
                orderPart.getQuantity()
        );

        orderPartRepository.deleteById(s);

        grpcLoggingClient.logAction(
                "DELETE",
                "OrderPart",
                s,
                details,
                java.time.ZonedDateTime.now().toString()
        );
    }


    @Override
    public OrderPartDTO updateOrderPartQuantity(String s, Integer quantity) {
        OrderPart orderPart = orderPartRepository.findById(s).orElseThrow();
        orderPart.setQuantity(quantity);
        return modelMapper.map(orderPartRepository.saveAndFlush(orderPart), OrderPartDTO.class);
    }
}

