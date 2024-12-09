package rut.miit.repairservice.contracts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.contracts.dtos.OrderDTO;
import rut.miit.repairservice.models.enums.StatusType;

import java.util.List;

@Tag(name = "orders", description = "API для управления заказами")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface OrderApi {

    @Operation(summary = "Получить все заказы")
    @GetMapping("/all")
    ResponseEntity<List<OrderDTO>> getAllOrders();

    @Operation(summary = "Получить заказы по ID клиента")
    @GetMapping("/all/client")
    ResponseEntity<List<OrderDTO>> getOrdersByClientId(@RequestParam String clientId);

    @Operation(summary = "Получить заказы по ID мастера")
    @GetMapping("/all/master")
    ResponseEntity<List<OrderDTO>> getOrdersByMasterId(@RequestParam String masterId);

    @Operation(summary = "Получить заказы по статусу")
    @GetMapping("/all/status")
    ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam StatusType status);

    @Operation(summary = "Получить заказ по ID")
    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> getOrderById(@PathVariable String id);

    @Operation(summary = "Добавить новый заказ")
    @PostMapping("/add")
    ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO);

    @Operation(summary = "Обновить статус заказа")
    @PutMapping("/update/status")
    ResponseEntity<?> updateOrderStatus(@RequestParam(name = "id") String id, @RequestParam(name = "status") StatusType status);

    @Operation(summary = "Назначить мастера на заказ")
    @PutMapping("/assign/master")
    ResponseEntity<?> assignMasterToOrder(@RequestParam(name = "id") String id, @RequestParam(name = "masterId") String masterId);

    @Operation(summary = "Назначить клиента на заказ")
    @PutMapping("/assign/client")
    ResponseEntity<?> assignClientToOrder(@RequestParam(name = "id") String id, @RequestParam(name = "clientId") String clientId);

    @Operation(summary = "Удалить заказ")
    @DeleteMapping("/delete")
    ResponseEntity<?> deleteOrder(@RequestParam(name = "id") String id);
}

