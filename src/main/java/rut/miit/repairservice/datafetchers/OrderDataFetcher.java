package rut.miit.repairservice.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.models.enums.StatusType;
import rut.miit.repairservice.services.implementations.OrderServiceImpl;

import java.util.List;

@DgsComponent
public class OrderDataFetcher {
    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderDataFetcher(OrderServiceImpl orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @DgsQuery
    public List<OrderDTO> orders(@InputArgument String orderId) {
        if (orderId != null) {
            OrderDTO order = modelMapper.map(orderService.getOrderById(orderId), OrderDTO.class);
            return order != null ? List.of(order) : List.of();
        }
        return orderService.getAllOrders();
    }

    @DgsQuery
    public List<OrderDTO> ordersByClientId(@InputArgument String clientId) {
        return orderService.getOrdersByClientId(clientId);
    }

    @DgsQuery
    public List<OrderDTO> ordersByMasterId(@InputArgument String masterId) {
        return orderService.getOrdersByMasterId(masterId);
    }

    @DgsQuery
    public List<OrderDTO> ordersByClientIdAndMasterId(
            @InputArgument String clientId,
            @InputArgument String masterId) {
        return orderService.getOrdersByClientIdAndMasterId(clientId, masterId);
    }

    @DgsQuery
    public List<OrderDTO> ordersByStatus(@InputArgument StatusType status) {
        return orderService.getOrdersByStatus(status);
    }

    @DgsMutation
    public OrderDTO addOrder(
            @InputArgument String description,
            @InputArgument StatusType status,
            @InputArgument String client,
            @InputArgument String master) {

        OrderDTO newOrder = new OrderDTO(description, status, null, client, master);
        return orderService.createOrder(newOrder);
    }

    @DgsMutation
    public OrderDTO updateOrderStatus(
            @InputArgument String id,
            @InputArgument StatusType status) {

        return orderService.updateOrderStatus(id, status);
    }

    @DgsMutation
    public OrderDTO assignMaster(
            @InputArgument String id,
            @InputArgument String masterId) {

        return orderService.assignMasterToOrder(id, masterId);
    }

    @DgsMutation
    public OrderDTO assignClient(
            @InputArgument String id,
            @InputArgument String clientId) {

        return orderService.assignClientToOrder(id, clientId);
    }

    @DgsMutation
    public String deleteOrder(@InputArgument String id) {
        try {
            orderService.deleteOrder(id);
            return "Order deleted successfully";
        } catch (EntityNotFoundException e) {
            return "Order not found";
        } catch (Exception e) {
            return "Error deleting order";
        }
    }
}
