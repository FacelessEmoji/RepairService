package rut.miit.repairservice.services.implementations;

import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.models.enums.StatusType;
import rut.miit.repairservice.services.interfaces.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService<String> {
    @Override
    public List<OrderDTO> getAllOrders() {
        return List.of();
    }

    @Override
    public OrderDTO getOrderById(String s) {
        return null;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO updateOrder(String s, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(String s) {

    }

    @Override
    public OrderDTO updateOrderStatus(String s, StatusType status) {
        return null;
    }

    @Override
    public OrderDTO assignMasterToOrder(String orderId, String masterId) {
        return null;
    }

    @Override
    public OrderDTO assignClientToOrder(String orderId, String clientId) {
        return null;
    }

    @Override
    public OrderDTO updateOrderDescription(String orderId, String description) {
        return null;
    }
}
