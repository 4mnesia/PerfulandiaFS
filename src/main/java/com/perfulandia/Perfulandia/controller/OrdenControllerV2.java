package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/ordenes")
public class OrdenControllerV2 {

    @Autowired private OrdenService ordenService;

    //filtrar por estado
    @Operation(summary = "Filtrar órdenes por estado", description = "Obtiene las órdenes filtradas por el estado proporcionado")
    @ApiResponse(responseCode = "200", description = "Lista de órdenes filtradas por estado", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "No se encontraron órdenes para el estado proporcionado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Orden>> getByEstado(@PathVariable String estado) {
        List<Orden> ordenes = ordenService.findByEstado(EstadoOrden.valueOf(estado));
        if (ordenes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ordenes);
    }
    //filtrar por dirección de envío ignore case
    @Operation(summary = "Filtrar órdenes por dirección de envío", description = "Obtiene las órdenes filtradas por la dirección de envío proporcionada")
    @ApiResponse(responseCode = "200", description = "Lista de órdenes filtradas por dirección de envío", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "No se encontraron órdenes para la dirección de envío proporcionada")
    @GetMapping("/direccion-envio/{direccion}")
    public ResponseEntity<List<Orden>> getByDireccionEnvio(@PathVariable String direccion) {
        List<Orden> ordenes = ordenService.findByDireccionEnvio(direccion);
        if (ordenes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ordenes);
    }
    //filtrar por fecha de creación
    @Operation(summary = "Filtrar órdenes por rango de fecha de creación", description = "Obtiene las órdenes filtradas por el rango de fecha de creación proporcionado")
    @ApiResponse(responseCode = "200", description = "Lista de órdenes filtradas por rango de fecha de creación", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "No se encontraron órdenes para el rango de fecha proporcionado")
    @GetMapping("/fecha-creacion")
    public ResponseEntity<List<Orden>> getByFechaCreacion(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        java.time.LocalDateTime inicio = java.time.LocalDateTime.parse(fechaInicio);
        java.time.LocalDateTime fin = java.time.LocalDateTime.parse(fechaFin);
        List<Orden> ordenes = ordenService.findByFechaCreacionBetween(inicio, fin);
        if (ordenes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ordenes);
    }
}