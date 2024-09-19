package rut.miit.repairservice.services.interfaces;

import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.models.enums.StatusType;

import java.util.List;

public interface OrderService<ID> {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(ID id);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(ID id, OrderDTO orderDTO);
    void deleteOrder(ID id);

    OrderDTO updateOrderStatus(ID id, StatusType status);
    OrderDTO assignMasterToOrder(ID orderId, String masterId);
    OrderDTO assignClientToOrder(ID orderId, String clientId);
    OrderDTO updateOrderDescription(ID orderId, String description);
}

