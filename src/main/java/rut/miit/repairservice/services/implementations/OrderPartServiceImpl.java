package rut.miit.repairservice.services.implementations;

import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.OrderPartDTO;
import rut.miit.repairservice.services.interfaces.OrderPartService;

import java.util.List;

@Service
public class OrderPartServiceImpl implements OrderPartService<String>{
    @Override
    public List<OrderPartDTO> getAllOrderParts() {
        return List.of();
    }

    @Override
    public OrderPartDTO getOrderPartById(String s) {
        return null;
    }

    @Override
    public OrderPartDTO createOrderPart(OrderPartDTO orderPartDTO) {
        return null;
    }

    @Override
    public OrderPartDTO updateOrderPart(String s, OrderPartDTO orderPartDTO) {
        return null;
    }

    @Override
    public void deleteOrderPart(String s) {

    }

    @Override
    public OrderPartDTO updateOrderPartQuantity(String s, Integer quantity) {
        return null;
    }
}
