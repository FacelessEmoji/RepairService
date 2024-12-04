package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.repairservice.config.RabbitMQConfiguration;
import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.models.entities.Order;
import rut.miit.repairservice.models.enums.StatusType;
import rut.miit.repairservice.repositories.ClientRepository;
import rut.miit.repairservice.repositories.MasterRepository;
import rut.miit.repairservice.repositories.OrderRepository;
import rut.miit.repairservice.services.interfaces.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService<String> {
    private OrderRepository orderRepository;
    private MasterRepository masterRepository;
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private RabbitTemplate rabbitTemplate;


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setMasterRepository(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(o -> modelMapper.map(o, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(String s) {
        return orderRepository.findById(s).orElse(null);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setClient(clientRepository.findById(orderDTO.getClient()).orElse(null));
        order.setMaster(masterRepository.findById(orderDTO.getMaster()).orElse(null));

        Order savedOrder = orderRepository.saveAndFlush(order);
        OrderDTO savedOrderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "order.price", savedOrder.getId());

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "order.parts", savedOrder.getId());

        return savedOrderDTO;
    }


    @Override
    public OrderDTO updateOrder(String s, OrderDTO orderDTO) {
        Order order = orderRepository.findById(s).orElseThrow();
        order.setDescription(orderDTO.getDescription());
        order.setStatus(orderDTO.getStatus());
        order.setClient(clientRepository.findById(order.getClient().getId()).orElseThrow());
        order.setMaster(masterRepository.findById(order.getMaster().getId()).orElseThrow());
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderDTO.class);
    }

    @Override
    public void deleteOrder(String s) {
        orderRepository.deleteById(s);
    }

    @Override
    public OrderDTO updateOrderStatus(String s, StatusType status) {
        Order order = orderRepository.findById(s).orElseThrow();
        order.setStatus(status);
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderDTO.class);
    }

    @Override
    public OrderDTO assignMasterToOrder(String orderId, String masterId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setMaster(masterRepository.findById(masterId).orElseThrow());
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderDTO.class);
    }

    @Override
    public OrderDTO assignClientToOrder(String orderId, String clientId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setClient(clientRepository.findById(clientId).orElseThrow());
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrderDescription(String orderId, String description) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setDescription(description);
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersByClientId(String clientId) {
        return orderRepository.findAllByClient_Id(clientId)
                .stream().map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByMasterId(String masterId) {
        return orderRepository.findAllByMaster_Id(masterId)
                .stream().map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByClientIdAndMasterId(String clientId, String masterId) {
        return orderRepository.findAllByClient_IdAndMaster_Id(clientId, masterId)
                .stream().map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(StatusType status) {
        return orderRepository.findAllByStatus(status)
                .stream().map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList());
    }
}

