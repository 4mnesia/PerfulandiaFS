package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.service.ItemCarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/itemCarrito")
public class ItemCarritoV2 {

    @Autowired
    private ItemCarritoService itemService;

    // filtrar por carrito id
    @Operation(summary = "Filtrar items por ID de carrito", description = "Obtiene los items asociados a un carrito específico")
    @ApiResponse(responseCode = "200", description = "Lista de items filtrados por carrito", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "No se encontraron items para el carrito proporcionado")
    @GetMapping("/carrito/{carritoId}")
    public ResponseEntity<List<ItemCarrito>> getItemsByCarritoId(@PathVariable Long carritoId) {
        List<ItemCarrito> items = itemService.findByCarritoId(carritoId);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    // filtrar por producto id
    @Operation(summary = "Filtrar items por ID de producto", description = "Obtiene los items asociados a un producto específico")
    @ApiResponse(responseCode = "200", description = "Lista de items filtrados por producto", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "No se encontraron items para el producto proporcionado")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ItemCarrito>> getItemsByProductoId(@PathVariable Long productoId) {
        List<ItemCarrito> items = itemService.findByProductoId(productoId);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    // filtrar por cantidad mayor a un valor
    @Operation(summary = "Filtrar items por cantidad mayor a un valor", description = "Obtiene los items cuya cantidad es mayor al valor especificado")
    @ApiResponse(responseCode = "200", description = "Lista de items filtrados por cantidad", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "No se encontraron items con cantidad mayor al valor proporcionado")
    @GetMapping("/cantidad/{cantidad}")
    public ResponseEntity<List<ItemCarrito>> getItemsByCantidadGreaterThan(@PathVariable int cantidad) {
        List<ItemCarrito> items = itemService.findByCantidadGreaterThan(cantidad);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }
}
