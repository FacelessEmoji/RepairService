package rut.miit.repairservice.contracts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.repairservice.contracts.dtos.PartDTO;

import java.math.BigDecimal;

@Tag(name = "parts", description = "API для управления запчастями")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface PartApi {

    @Operation(summary = "Получить запчасть по ID")
    @GetMapping("/{id}")
    ResponseEntity<PartDTO> getPartById(@PathVariable String id);

    @Operation(summary = "Получить все запчасти")
    @GetMapping("/all")
    ResponseEntity<?> getAllParts();

    @Operation(summary = "Добавить новую запчасть")
    @PostMapping("/add")
    ResponseEntity<?> addPart(@RequestBody PartDTO partDTO);

    @Operation(summary = "Обновить название запчасти")
    @PutMapping("/update/name")
    ResponseEntity<?> updatePartName(@RequestParam(name = "id") String id, @RequestParam(name = "name") String newName);

    @Operation(summary = "Обновить количество запчасти")
    @PutMapping("/update/quantity")
    ResponseEntity<?> updatePartQuantity(@RequestParam(name = "id") String id, @RequestParam(name = "quantity") Integer quantity);

    @Operation(summary = "Обновить цену запчасти")
    @PutMapping("/update/price")
    ResponseEntity<?> updatePartPrice(@RequestParam(name = "id") String id, @RequestParam(name = "price") BigDecimal price);

    @Operation(summary = "Удалить запчасть")
    @DeleteMapping("/delete")
    ResponseEntity<?> deletePart(@RequestParam(name = "id") String id);
}

