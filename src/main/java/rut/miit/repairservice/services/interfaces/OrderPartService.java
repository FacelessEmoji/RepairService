package rut.miit.repairservice.services.interfaces;

import rut.miit.repairservice.dtos.main.OrderPartDTO;

import java.util.List;

public interface OrderPartService<ID> {
    List<OrderPartDTO> getAllOrderParts();
    OrderPartDTO getOrderPartById(ID id);
    OrderPartDTO createOrderPart(OrderPartDTO orderPartDTO);
    OrderPartDTO updateOrderPart(ID id, OrderPartDTO orderPartDTO);
    void deleteOrderPart(ID id);

    OrderPartDTO updateOrderPartQuantity(ID id, Integer quantity);
}

