package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import com.perfulandia.Perfulandia.service.CarritoService;
import com.perfulandia.Perfulandia.assemblers.CarritoModelAssembler;
import com.perfulandia.Perfulandia.model.Carrito;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

@Tag(name = "Carrito", description = "Gestión de carritos")
@RestController
@RequestMapping("/api/perfulandia")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private CarritoModelAssembler assembler;

    // Obtener todos los carritos
    @Operation(summary = "Listar todos los carritos", description = "Obtiene la lista completa de carritos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de carritos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrito.class))))
    @GetMapping("/carrito")
    public ResponseEntity<CollectionModel<EntityModel<Carrito>>> getAllCarritos() {
        try {
            List<Carrito> carritos = carritoService.getAllCarritos();
            CollectionModel<EntityModel<Carrito>> model = assembler.toCollectionModel(carritos);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los carritos: " + e.getMessage());
        }
    }

    // carritos por id
    @Operation(summary = "Obtener un carrito por ID", description = "Devuelve un carrito si existe el ID proporcionado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrito encontrado", content = @Content(schema = @Schema(implementation = Carrito.class))),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @GetMapping("/carrito/{id}")
    public ResponseEntity<EntityModel<Carrito>> getCarritoById(
            @Parameter(description = "ID del carrito", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            Carrito carrito = carritoService.getCarritoById(id);
            EntityModel<Carrito> model = assembler.toModel(carrito);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el carrito por ID: " + e.getMessage());
        }
    }

    // crear carrito
    @Operation(summary = "Crear un nuevo carrito", description = "Registra un nuevo carrito con sus items")
    @ApiResponse(responseCode = "201", description = "Carrito creado correctamente", content = @Content(schema = @Schema(implementation = Carrito.class), examples = @ExampleObject(value = """
            {
              \"usuario\": {\"id\":1},
              \"item\": [{\"producto\":{\"id\":1},\"cantidad\":2}],
              \"estado\": true
            }""")))
    @PostMapping("/carrito")
    public Carrito createCarrito(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del carrito a crear", required = true, content = @Content(schema = @Schema(implementation = Carrito.class))) @RequestBody Carrito nuevoCarrito) {
        try {
            return carritoService.saveCarrito(nuevoCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el carrito: " + e.getMessage());
        }
    }

    // crear varios carritos
    @Operation(summary = "Guardar varios carritos en lote")
    @ApiResponse(responseCode = "200", description = "Carritos guardados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrito.class))))
    @PostMapping("/carrito/batch")
    public ResponseEntity<List<Carrito>> createCarritosBatch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lista de carritos a crear", required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrito.class)))) @RequestBody List<Carrito> carritos) {
        try {
            if (carritos == null || carritos.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<Carrito> saved = carritoService.saveCarritos(carritos);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // actualizar carrito
    @Operation(summary = "Actualizar un carrito existente", description = "Modifica los datos de un carrito por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrito actualizado", content = @Content(schema = @Schema(implementation = Carrito.class))),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PutMapping("/carrito/{id}")
    public Carrito updateCarrito(
            @Parameter(description = "ID del carrito", required = true, in = ParameterIn.PATH) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nueva información del carrito", required = true, content = @Content(schema = @Schema(implementation = Carrito.class))) @RequestBody Carrito carritoActualizado) {
        try {
            Carrito existente = carritoService.getCarritoById(id);
            if (existente == null) {
                throw new RuntimeException("Carrito no encontrado con id: " + id);
            }
            existente.setUsuario(carritoActualizado.getUsuario());
            existente.setEstado(carritoActualizado.isEstado());
            existente.getItem().clear();
            if (carritoActualizado.getItem() != null) {
                existente.getItem().addAll(carritoActualizado.getItem());
            }
            return carritoService.updateCarrito(existente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el carrito: " + e.getMessage());
        }
    }

    // eliminar un carrito por su ID
    @DeleteMapping("/carrito/{id}")
    @Operation(summary = "Eliminar un carrito por ID", description = "Borra el carrito especificado, devolviendo 204 si todo va bien")
    @ApiResponse(responseCode = "204", description = "Carrito eliminado correctamente")
    public ResponseEntity<String> deleteCarrito(
            @Parameter(description = "ID del carrito", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        carritoService.deleteCarrito(id);
        return ResponseEntity.ok("Carrito eliminado con todo su contenido");
    }

    // borrar todos los carritos
    @Operation(summary = "Eliminar todos los carritos")
    @ApiResponse(responseCode = "204", description = "Todos los carritos han sido eliminados")
    @DeleteMapping("/carrito")
    public ResponseEntity<Void> deleteAllCarritos() {
        try {
            carritoService.deleteAllCarritos();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar todos los carritos: " + e.getMessage());
        }
    }
}