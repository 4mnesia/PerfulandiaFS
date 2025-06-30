package com.perfulandia.Perfulandia.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.perfulandia.Perfulandia.assemblers.OrdenModelAssembler;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.service.OrdenService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Órdenes", description = "Gestión de órdenes")
@RestController
@RequestMapping("/api/perfulandia")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;
    @Autowired
    private OrdenModelAssembler assembler;

    // listar todas las órdenes con HATEOAS
    @Operation(summary = "Listar todas las órdenes")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Orden.class))))
    @GetMapping("/orden")
    public ResponseEntity<CollectionModel<EntityModel<Orden>>> getAllOrdenes() {
        try {
            List<Orden> ordenes = ordenService.getAllOrdenes();
            CollectionModel<EntityModel<Orden>> model = assembler.toCollectionModel(ordenes);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // listar orden por id con HATEOAS
    @Operation(summary = "Obtener orden por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrada",
            content = @Content(schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/orden/{id}")
    public ResponseEntity<EntityModel<Orden>> getOrdenById(
        @Parameter(description = "ID de la orden", required = true, in = ParameterIn.PATH)
        @PathVariable Long id) {
        try {
            Orden orden = ordenService.getOrdenById(id);
            if (orden != null) {
                EntityModel<Orden> model = assembler.toModel(orden);
                return ResponseEntity.ok(model);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // guardar orden
    @Operation(summary = "Crear una orden")
    @ApiResponse(responseCode = "201", description = "Orden creada",
        content = @Content(schema = @Schema(implementation = Orden.class)))
    @PostMapping("/orden")
    public ResponseEntity<?> saveOrden(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la orden a crear", required = true,
            content = @Content(schema = @Schema(implementation = Orden.class)))
        @RequestBody Orden orden) {
        try {
            if (orden == null) {
                return ResponseEntity.badRequest().body("La orden no puede ser nula");
            }
            if (orden.getDetalles() == null || orden.getDetalles().isEmpty()) {
                return ResponseEntity.badRequest().body("La orden debe tener al menos un detalle");
            }
            ordenService.saveOrden(orden);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Orden creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la orden: " + e.getMessage());
        }
    }

    // crear varias ordenes en lote
    @Operation(summary = "Crear órdenes en lote")
    @ApiResponse(responseCode = "201", description = "Órdenes guardadas",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Orden.class))))
    @PostMapping("/orden/batch")
    public ResponseEntity<List<Orden>> saveOrdenes(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Lista de órdenes a crear", required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Orden.class))))
        @RequestBody List<Orden> ordenes) {
        try {
            List<Orden> ordenesGuardadas = ordenService.saveOrdenes(ordenes);
            return ResponseEntity.status(HttpStatus.CREATED).body(ordenesGuardadas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // modificar orden
    @Operation(summary = "Actualizar una orden")
    @ApiResponse(responseCode = "200", description = "Orden actualizada",
        content = @Content(schema = @Schema(implementation = Orden.class)))
    @PutMapping("/orden/{id}")
    public ResponseEntity<?> updateOrden(
        @Parameter(description = "ID de la orden", required = true, in = ParameterIn.PATH)
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la orden a actualizar", required = true,
            content = @Content(schema = @Schema(implementation = Orden.class)))
        @RequestBody Orden orden) {
        try {
            Orden ordenActualizada = ordenService.updateOrden(id, orden);
            if (ordenActualizada != null) {
                return ResponseEntity.ok(ordenActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al modificar la orden: " + e.getMessage());
        }
    }

    // eliminar orden por id
    @Operation(summary = "Eliminar una orden por ID", description = "Borra la orden con el identificador especificado")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Orden eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe orden con ese ID")
    })
    @DeleteMapping("/orden/{id}")
    public ResponseEntity<Void> deleteOrden(
        @Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la orden a eliminar", required = true)
        @PathVariable Long id) {
        ordenService.deleteOrden(id);
        return ResponseEntity.noContent().build();
    }

    // eliminar todas las ordenes
    @Operation(summary = "Eliminar todas las órdenes", description = "Borra todas las órdenes y devuelve 200 OK")
    @ApiResponse(responseCode = "200", description = "Todas las órdenes han sido eliminadas")
    @DeleteMapping("/orden")
    public ResponseEntity<Void> deleteAllOrdenes() {
        ordenService.deleteAllOrdenes();
        return ResponseEntity.ok().build();
    }
}
