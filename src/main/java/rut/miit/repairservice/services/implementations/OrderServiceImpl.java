package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return modelMapper.map(orderRepository.saveAndFlush(modelMapper.map(order, Order.class)), OrderDTO.class);
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
}

