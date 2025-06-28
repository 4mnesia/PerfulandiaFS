package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/detalleOrden")
public class DetalleOrdenV2 {

    @Autowired
    private DetalleOrdenService detalleService;

    // detalles asociados a una orden
    @Operation(summary = "Obtener detalles de una orden por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalles de la orden encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DetalleOrden.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron detalles para la orden proporcionada")
    })
    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<DetalleOrden>> findByOrden_Id(@PathVariable Long ordenId) {
        List<DetalleOrden> detalles = detalleService.findByOrdenId(ordenId);
        if (detalles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalles);
    }

    // Filtra detalles por rango de precio unitario
    @Operation(summary = "Filtrar detalles por rango de precio unitario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalles filtrados por precio unitario", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DetalleOrden.class)))),
            @ApiResponse(responseCode = "400", description = "Rango de precios inválido")
    })
    @GetMapping("/precioUnitario")
    public ResponseEntity<List<DetalleOrden>> findByPrecioUnitarioBetween(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        if (min.compareTo(max) > 0) {
            return ResponseEntity.badRequest().build();
        }
        List<DetalleOrden> detalles = detalleService.findByPrecioUnitarioBetween(min, max);
        return ResponseEntity.ok(detalles);
    }

}
