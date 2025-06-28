package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.service.ItemCarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ItemCarrito", description = "Operaciones sobre items de carrito")
@RestController
@RequestMapping("/api/perfulandia/itemcarrito")
public class ItemCarritoController {

    @Autowired
    private ItemCarritoService service;

    // GET /api/perfulandia/itemcarrito
    @Operation(summary = "Listar todos los items del carrito")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemCarrito.class))))
    @GetMapping
    public ResponseEntity<?> getAllItems() {
        try {
            return ResponseEntity.ok(service.getAllItemCarritos());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al obtener items: " + e.getMessage());
        }
    }

    // GET /api/perfulandia/itemcarrito/{id}
    @Operation(summary = "Obtener un item por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado", content = @Content(schema = @Schema(implementation = ItemCarrito.class))),
            @ApiResponse(responseCode = "404", description = "Item no encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(
            @Parameter(description = "ID del item", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            ItemCarrito item = service.getItemCarritoById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound()
                        .build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al obtener item por id: " + e.getMessage());
        }
    }

    // POST /api/perfulandia/itemcarrito (un solo item)
    @Operation(summary = "Crear un item de carrito")
    @ApiResponse(responseCode = "200", description = "Item creado", content = @Content(schema = @Schema(implementation = ItemCarrito.class), examples = @ExampleObject(value = "{\"cantidad\":2}")))
    @PostMapping
    public ResponseEntity<?> createItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del item a crear", required = true, content = @Content(schema = @Schema(implementation = ItemCarrito.class))) @RequestBody ItemCarrito item) {
        try {
            if (item == null) {
                return ResponseEntity.badRequest()
                        .body("El item no puede ser nulo");
            }
            ItemCarrito saved = service.saveItemCarrito(item);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al crear item: " + e.getMessage());
        }
    }

    // POST /api/perfulandia/itemcarrito/batch (varios items de golpe)
    @Operation(summary = "Crear varios items en lote")
    @ApiResponse(responseCode = "200", description = "Items guardados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemCarrito.class))))
    @PostMapping("/batch")
    public ResponseEntity<List<ItemCarrito>> createItemsBatch(
            @RequestBody List<ItemCarrito> items // <--- RequestBody para que Jackson lo mapee
    ) {
        List<ItemCarrito> saved = service.saveAllItemCarritos(items);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/perfulandia/itemcarrito/{id}
    @Operation(summary = "Actualizar un item existente")
    @ApiResponse(responseCode = "200", description = "Item actualizado", content = @Content(schema = @Schema(implementation = ItemCarrito.class)))
    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(
            @PathVariable Long id,
            @RequestBody ItemCarrito item // << y aquí
    ) {
        ItemCarrito existing = service.getItemCarritoById(id);
        existing.setCantidad(item.getCantidad());
        // si quisieras actualizar más campos, los pones aquí...
        service.saveItemCarrito(existing);
        return ResponseEntity.ok("Item actualizado correctamente");
    }

    // DELETE /api/perfulandia/itemcarrito/{id}
    @Operation(summary = "Eliminar un item")
    @ApiResponse(responseCode = "200", description = "Item eliminado")

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(
            @Parameter(description = "ID del item", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            service.deleteItemCarrito(id);
            return ResponseEntity.ok("Item eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al eliminar item: " + e.getMessage());
        }
    }
}
