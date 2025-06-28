package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    // filtrar por categoria
    @Operation(summary = "Filtrar productos por categoría", description = "Obtiene los productos cuya categoría (String) coincide con la ruta")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por categoría")
    @ApiResponse(responseCode = "404", description = "No se encontraron productos para la categoría proporcionada")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> getByCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.findByCategoria(categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productos);
    }

    // filtrar por nombre ignorando mayúsculas y minúsculas
    @Operation(summary = "Filtrar productos por nombre", description = "Obtiene los productos cuyo nombre contiene la cadena proporcionada, ignorando mayúsculas y minúsculas")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por nombre")
    @ApiResponse(responseCode = "404", description = "No se encontraron productos para el nombre proporcionado")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> getByNombre(@PathVariable String nombre) {
        List<Producto> productos = productoService.findByNombreContainingIgnoreCase(nombre);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productos);
    }

    // filtrar por precio entre un rango
    @Operation(summary = "Filtrar productos por rango de precio", description = "Obtiene los productos cuyo precio está entre los valores proporcionados")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por  rango de precio")
    @ApiResponse(responseCode = "404", description = "No se encontraron productos para el rango de precio proporcionado")
    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> getByPrecioBetween(@RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Producto> productos = productoService.findByPrecioBetween(min, max);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productos);
    }

    // filtrar por stock mayor a un valor
    @Operation(summary = "Filtrar productos por stock", description = "Obtiene los productos cuyo stock es mayor al valor proporcionado")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por stock")
    @ApiResponse(responseCode = "404", description = "No se encontraron productos con stock mayor al valor proporcionado")
    @GetMapping("/stock/{stock}")
    public ResponseEntity<List<Producto>> getByStockGreaterThan(@PathVariable int stock) {
        List<Producto> productos = productoService.findByStockGreaterThan(stock);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productos);
    }

    // filtrar por marca
    @Operation(summary = "Filtrar productos por marca", description = "Obtiene los productos cuya marca coincide con la proporcionada")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por marca")
    @ApiResponse(responseCode = "404", description = "No se encontraron productos para la marca proporcionada")
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> getByMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.findByMarca(marca);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productos);
    }
}
