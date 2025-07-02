package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;
import com.perfulandia.Perfulandia.assemblers.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


import java.util.List;

@Tag(name = "DetalleOrden", description = "Operaciones sobre detalles de orden")
@RestController
@RequestMapping("/api/perfulandia")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService service;
    @Autowired
    private DetalleOrdenModelAssembler assembler;

    // Listar todos los detalles
    @Operation(summary = "Listar todos los detalles de orden")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DetalleOrden.class))))
    @GetMapping("/detalleorden")
    public ResponseEntity<CollectionModel<EntityModel<DetalleOrden>>> getAllDetalles() {
        try {
            List<DetalleOrden> list = service.getAllDetalles();
            CollectionModel<EntityModel<DetalleOrden>> model = assembler.toCollectionModel(list);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }

    // Listar detalle por ID
    @Operation(summary = "Obtener detalle por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado", content = @Content(schema = @Schema(implementation = DetalleOrden.class))),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    
    @GetMapping("/detalleorden/{id}")
    public ResponseEntity<EntityModel<DetalleOrden>> getDetalleById(
            @Parameter(description = "ID del detalle", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            DetalleOrden d = service.getDetalleOrdenById(id);
            if (d != null) {
                EntityModel<DetalleOrden> model = assembler.toModel(d);
                return ResponseEntity.ok(model);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }

    // Crear un nuevo detalle
    @Operation(summary = "Crear un detalle de orden")
    @ApiResponse(responseCode = "200", description = "Detalle creado", content = @Content(schema = @Schema(implementation = DetalleOrden.class)))
    @PostMapping("/detalleorden")
    public ResponseEntity<?> createDetalle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del detalle a crear", required = true, content = @Content(schema = @Schema(implementation = DetalleOrden.class))) @RequestBody DetalleOrden detalle) {
        try {
            if (detalle == null) {
                return ResponseEntity.badRequest().body("Detalle no puede ser nulo");
            }
            DetalleOrden saved = service.saveDetalleOrden(detalle);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al guardar detalle: " + e.getMessage());
        }
    }

    // Crear varios detalles en lote
    @Operation(summary = "Crear detalles en lote")
    @ApiResponse(responseCode = "200", description = "Detalles guardados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DetalleOrden.class))))
    @PostMapping("/detalleorden/batch")
    public ResponseEntity<?> createDetallesBatch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lista de detalles a crear", required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = DetalleOrden.class)))) @RequestBody List<DetalleOrden> detalles) {
        try {
            if (detalles == null || detalles.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista no puede estar vacía");
            }
            List<DetalleOrden> saved = service.saveDetallesOrden(detalles);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al guardar detalles en lote: " + e.getMessage());
        }
    }

    // Actualizar un detalle existente
    @Operation(summary = "Actualizar un detalle")
    @ApiResponse(responseCode = "200", description = "Detalle actualizado", content = @Content(schema = @Schema(implementation = DetalleOrden.class)))
    @PutMapping("/detalleorden/{id}")
    public ResponseEntity<?> updateDetalle(
            @Parameter(description = "ID del detalle", required = true, in = ParameterIn.PATH) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del detalle a actualizar", required = true, content = @Content(schema = @Schema(implementation = DetalleOrden.class))) @RequestBody DetalleOrden detalle) {
        try {
            detalle.setId(id);
            DetalleOrden updated = service.saveDetalleOrden(detalle);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al actualizar detalle: " + e.getMessage());
        }
    }

    // Eliminar un detalle por su ID
    @Operation(summary = "Eliminar un detalle")
    @ApiResponse(responseCode = "200", description = "Detalle eliminado")
    @DeleteMapping("/detalleorden/{id}")
    public ResponseEntity<?> deleteDetalle(
            @Parameter(description = "ID del detalle", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            service.deleteDetalleOrden(id);
            return ResponseEntity.ok("Detalle eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al eliminar detalle: " + e.getMessage());
        }
    }

    // Eliminar todos los detalles
    @Operation(summary = "Eliminar todos los detalles de orden", description = "Borra absolutamente todos los registros de DetalleOrden")
    @ApiResponse(responseCode = "204", description = "Todos los detalles eliminados")
    @DeleteMapping("/detalleorden")
    public ResponseEntity<Void> deleteAllDetalles() {
        service.deleteAllDetalles();
        return ResponseEntity.noContent().build();
    }
}
