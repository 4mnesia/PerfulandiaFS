package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/carrito")
public class CarritoControllerV2 {

    @Autowired
    private CarritoService carritoService;

    // filtrar por usuario
    @Operation(summary = "Filtrar carritos por ID de usuario", description = "Obtiene los carritos asociados a un usuario específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de carritos filtrados por usuario", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrito.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron carritos para el usuario proporcionado") })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carrito>> getCarritosByUsuarioId(@PathVariable Long usuarioId) {
        List<Carrito> carritos = carritoService.findByUsuarioId(usuarioId);
        if (carritos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carritos);
    }

    // filtrar por estado
    @Operation(summary = "Filtrar carritos por estado", description = "Obtiene los carritos según su estado (activo/inactivo)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de carritos filtrados por estado", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrito.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron carritos para el estado proporcionado")
    })
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Carrito>> getCarritosByEstado(
            @Parameter(description = "Estado del carrito (true para activo, false para inactivo)", required = true, in = ParameterIn.PATH, example = "true") @PathVariable boolean estado) {
        List<Carrito> carritos = carritoService.findByEstado(estado);
        if (carritos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carritos);
    }
}
