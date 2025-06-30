package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.ItemCarritoModelAssembler;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ItemCarrito", description = "Operaciones sobre items de carrito")
@RestController
@RequestMapping("/api/perfulandia/itemcarrito")
public class ItemCarritoController {

    @Autowired
    private ItemCarritoService service;
    @Autowired
    private ItemCarritoModelAssembler assembler;

    // GET todos los items del carrito
    @Operation(summary = "Listar todos los items del carrito")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemCarrito.class))))
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ItemCarrito>>> getAllItems() {
        try {
            List<ItemCarrito> list = service.getAllItemCarritos();
            CollectionModel<EntityModel<ItemCarrito>> model = assembler.toCollectionModel(list);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }

    // GET un item por ID
    @Operation(summary = "Obtener un item por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item encontrado",
            content = @Content(schema = @Schema(implementation = ItemCarrito.class))),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ItemCarrito>> getItemById(
        @Parameter(description = "ID del item", required = true, in = ParameterIn.PATH)
        @PathVariable Long id) {
        try {
            ItemCarrito item = service.getItemCarritoById(id);
            if (item != null) {
                EntityModel<ItemCarrito> model = assembler.toModel(item);
                return ResponseEntity.ok(model);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }

    // POST crear un item de carrito
    @Operation(summary = "Crear un item de carrito")
    @ApiResponse(responseCode = "200", description = "Item creado",
        content = @Content(schema = @Schema(implementation = ItemCarrito.class),
            examples = @ExampleObject(value = "{\"cantidad\":2}")))
    @PostMapping
    public ResponseEntity<?> createItem(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del item a crear", required = true,
            content = @Content(schema = @Schema(implementation = ItemCarrito.class)))
        @RequestBody ItemCarrito item) {
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

    // POST crear varios items en lote
    @Operation(summary = "Crear varios items en lote")
    @ApiResponse(responseCode = "200", description = "Items guardados",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemCarrito.class))))
    @PostMapping("/batch")
    public ResponseEntity<List<ItemCarrito>> createItemsBatch(
        @RequestBody List<ItemCarrito> items) {
        List<ItemCarrito> saved = service.saveAllItemCarritos(items);
        return ResponseEntity.ok(saved);
    }

    // PUT actualizar un item existente
    @Operation(summary = "Actualizar un item existente")
    @ApiResponse(responseCode = "200", description = "Item actualizado",
        content = @Content(schema = @Schema(implementation = ItemCarrito.class)))
    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(
        @PathVariable Long id,
        @RequestBody ItemCarrito item) {
        ItemCarrito existing = service.getItemCarritoById(id);
        existing.setCantidad(item.getCantidad());
        service.saveItemCarrito(existing);
        return ResponseEntity.ok("Item actualizado correctamente");
    }

    // DELETE un item por ID
    @Operation(summary = "Eliminar un item")
    @ApiResponse(responseCode = "200", description = "Item eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(
        @Parameter(description = "ID del item", required = true, in = ParameterIn.PATH)
        @PathVariable Long id) {
        try {
            service.deleteItemCarrito(id);
            return ResponseEntity.ok("Item eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al eliminar item: " + e.getMessage());
        }
    }
}
