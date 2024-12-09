package rut.miit.repairservice.contracts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.contracts.dtos.ClientDTO;

import java.util.List;

@Tag(name = "clients", description = "API для управления клиентами")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface ClientApi {

    @Operation(summary = "Получить клиента по ID")
    @GetMapping(value = "/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientDTO> getClientById(@PathVariable String id);

    @Operation(summary = "Получить список всех клиентов")
    @GetMapping(value = "/client/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClientDTO>> getAllClients();

    @Operation(summary = "Добавить нового клиента")
    @PostMapping(value = "/client/add", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientDTO> addClient(@RequestBody @Valid ClientDTO clientDTO);

    @Operation(summary = "Обновить имя клиента")
    @PutMapping(value = "/client/update/name", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientDTO> updateClientName(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name") String newName);

    @Operation(summary = "Обновить телефон клиента")
    @PutMapping(value = "/client/update/phone", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientDTO> updateClientPhone(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "phone") String newPhone);

    @Operation(summary = "Обновить email клиента")
    @PutMapping(value = "/client/update/email", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientDTO> updateClientEmail(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "email") String newEmail);

    @Operation(summary = "Удалить клиента")
    @DeleteMapping(value = "/client/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteClient(@RequestParam(name = "id") String id);
}

