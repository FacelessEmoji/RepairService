package rut.miit.repairservice.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.config.ActionLinkGenerator;
import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.models.enums.StatusType;
import rut.miit.repairservice.services.implementations.OrderServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderServiceImpl orderService;
    private ModelMapper modelMapper;

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();

        for (OrderDTO order : orders) {
            order.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
        }

        Map<String, Map<String, String>> actions = new HashMap<>();
        String baseUrl = "http://localhost:8081/order";

        actions.put("getByClient", ActionLinkGenerator.createAction(baseUrl, "/all?clientId={clientId}", null, "GET"));
        actions.put("getByMaster", ActionLinkGenerator.createAction(baseUrl, "/all?masterId={masterId}", null, "GET"));
        actions.put("getByStatus", ActionLinkGenerator.createAction(baseUrl, "/all?status={status}", null, "GET"));

        for (OrderDTO order : orders) {
            order.setActions(actions);
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all/client")
    public ResponseEntity<List<OrderDTO>> getOrdersByClientId(@RequestParam String clientId) {
        List<OrderDTO> orders = orderService.getOrdersByClientId(clientId);

        for (OrderDTO order : orders) {
            order.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
        }

        Map<String, Map<String, String>> actions = new HashMap<>();
        String baseUrl = "http://localhost:8081/order";

        actions.put("getAll", ActionLinkGenerator.getAction(baseUrl, "all"));
        actions.put("getByMaster", ActionLinkGenerator.createAction(baseUrl, "/all?masterId={masterId}", null, "GET"));
        actions.put("getByStatus", ActionLinkGenerator.createAction(baseUrl, "/all?status={status}", null, "GET"));

        for (OrderDTO order : orders) {
            order.setActions(actions);
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all/master")
    public ResponseEntity<List<OrderDTO>> getOrdersByMasterId(@RequestParam String masterId) {
        List<OrderDTO> orders = orderService.getOrdersByMasterId(masterId);

        for (OrderDTO order : orders) {
            order.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
        }

        Map<String, Map<String, String>> actions = new HashMap<>();
        String baseUrl = "http://localhost:8081/order";

        actions.put("getAll", ActionLinkGenerator.getAction(baseUrl, "all"));
        actions.put("getByClient", ActionLinkGenerator.createAction(baseUrl, "/all?clientId={clientId}", null, "GET"));
        actions.put("getByStatus", ActionLinkGenerator.createAction(baseUrl, "/all?status={status}", null, "GET"));

        for (OrderDTO order : orders) {
            order.setActions(actions);
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all/status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam StatusType status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);

        for (OrderDTO order : orders) {
            order.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
        }

        Map<String, Map<String, String>> actions = new HashMap<>();
        String baseUrl = "http://localhost:8081/order";

        actions.put("getAll", ActionLinkGenerator.getAction(baseUrl, "all"));
        actions.put("getByClient", ActionLinkGenerator.createAction(baseUrl, "/all?clientId={clientId}", null, "GET"));
        actions.put("getByMaster", ActionLinkGenerator.createAction(baseUrl, "/all?masterId={masterId}", null, "GET"));

        for (OrderDTO order : orders) {
            order.setActions(actions);
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id) {
        OrderDTO orderDTO = modelMapper.map(orderService.getOrderById(id), OrderDTO.class);

        orderDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(id)).withSelfRel());
        orderDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders()).withRel("all-orders"));

        Map<String, Map<String, String>> actions = new HashMap<>();
        String baseUrl = "http://localhost:8081/order";

        actions.put("get", ActionLinkGenerator.getAction(baseUrl, id));
        actions.put("updateStatus", ActionLinkGenerator.createAction(baseUrl, "/update/status", id, "PUT"));
        actions.put("assignMaster", ActionLinkGenerator.createAction(baseUrl, "/assign/master", id, "PUT"));
        actions.put("assignClient", ActionLinkGenerator.createAction(baseUrl, "/assign/client", id, "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, id));

        orderDTO.setActions(actions);

        return ResponseEntity.ok(orderDTO);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        String baseUrl = "http://localhost:8081/order";

        Map<String, Map<String, String>> actions = new HashMap<>();
        actions.put("get", ActionLinkGenerator.getAction(baseUrl, createdOrder.getId()));
        actions.put("updateStatus", ActionLinkGenerator.createAction(baseUrl, "/update/status", createdOrder.getId(), "PUT"));
        actions.put("assignMaster", ActionLinkGenerator.createAction(baseUrl, "/assign/master", createdOrder.getId(), "PUT"));
        actions.put("assignClient", ActionLinkGenerator.createAction(baseUrl, "/assign/client", createdOrder.getId(), "PUT"));
        actions.put("delete", ActionLinkGenerator.deleteAction(baseUrl, createdOrder.getId()));

        createdOrder.setActions(actions);

        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/update/status")
    public ResponseEntity<?> updateOrderStatus(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "status") StatusType status) {
        if (id == null || status == null) {
            return ResponseEntity.badRequest().body("Id and Status can't be null");
        } else {
            OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);

            updatedOrder.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .assignMasterToOrder(id, null)).withRel("assignMaster"));
            updatedOrder.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .assignClientToOrder(id, null)).withRel("assignClient"));

            return ResponseEntity.ok(updatedOrder);
        }
    }

    @PutMapping("/assign/master")
    public ResponseEntity<?> assignMasterToOrder(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "masterId") String masterId) {
        if (id == null || masterId == null) {
            return ResponseEntity.badRequest().body("Id and MasterId can't be null");
        } else {
            OrderDTO updatedOrder = orderService.assignMasterToOrder(id, masterId);

            updatedOrder.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .assignClientToOrder(id, null)).withRel("assignClient"));
            updatedOrder.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .updateOrderStatus(id, null)).withRel("updateStatus"));

            return ResponseEntity.ok(updatedOrder);
        }
    }

    @PutMapping("/assign/client")
    public ResponseEntity<?> assignClientToOrder(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "clientId") String clientId) {
        if (id == null || clientId == null) {
            return ResponseEntity.badRequest().body("Id and ClientId can't be null");
        } else {
            OrderDTO updatedOrder = orderService.assignClientToOrder(id, clientId);

            updatedOrder.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .assignMasterToOrder(id, null)).withRel("assignMaster"));
            updatedOrder.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .updateOrderStatus(id, null)).withRel("updateStatus"));

            return ResponseEntity.ok(updatedOrder);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam(name = "id") String id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting order");
        }
    }
}

