package rut.miit.repairservice.contracts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.contracts.dtos.MasterDTO;

import java.util.List;

@Tag(name = "masters", description = "API для управления мастерами")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface MasterApi {

    @Operation(summary = "Получить мастера по ID")
    @GetMapping(value = "/master/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MasterDTO> getMasterById(@PathVariable String id);

    @Operation(summary = "Получить список всех мастеров")
    @GetMapping(value = "/master/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MasterDTO>> getAllMasters();

    @Operation(summary = "Добавить нового мастера")
    @PostMapping(value = "/master/add", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MasterDTO> addMaster(@RequestBody @Valid MasterDTO masterDTO);

    @Operation(summary = "Обновить имя мастера")
    @PutMapping(value = "/master/update/name", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MasterDTO> updateMasterName(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name") String newName);

    @Operation(summary = "Обновить телефон мастера")
    @PutMapping(value = "/master/update/phone", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MasterDTO> updateMasterPhone(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "phone") String newPhone);

    @Operation(summary = "Обновить специализацию мастера")
    @PutMapping(value = "/master/update/specialization", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MasterDTO> updateMasterSpecialization(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "specialization") String specialization);

    @Operation(summary = "Удалить мастера")
    @DeleteMapping(value = "/master/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteMaster(@RequestParam(name = "id") String id);
}

