package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Productos", description = "Operaciones sobre productos")
@RestController
@RequestMapping("/api/perfulandia")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos con validación sencilla
    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
    @GetMapping("/productos")
    public ResponseEntity<?> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay productos disponibles.");
        }
        return ResponseEntity.ok(productos);
    }

    // Ejemplo de un método para obtener un producto por su ID y si no existe
    // devolver un null
    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProductoById(
            @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        Producto p = productoService.getProductoById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    // Ejemplo de un método para crear un nuevo producto
    @Operation(summary = "Crear un producto")
    @ApiResponse(responseCode = "201", description = "Producto creado", content = @Content(schema = @Schema(implementation = Producto.class)))
    @PostMapping("/productos")
    public ResponseEntity<Producto> createProducto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del producto a crear", required = true, content = @Content(schema = @Schema(implementation = Producto.class))) @RequestBody Producto producto) {
        Producto productoGuardado = productoService.saveProducto(producto);
        if (productoGuardado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(201).body(productoGuardado);
    }

    // crear varios productos
    @Operation(summary = "Crear varios productos en lote", description = "Recibe una lista de productos y los guarda todos de una vez", responses = {
            @ApiResponse(responseCode = "200", description = "Productos creados correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
            @ApiResponse(responseCode = "400", description = "Lista de productos inválida")
    })
    @PostMapping("/batch")
    public ResponseEntity<List<Producto>> createProductosBatch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lista de productos a crear", required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))) @RequestBody List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Producto> saved = productoService.saveAllProductos(productos);
        return ResponseEntity.ok(saved);
    }

    // eliminar un producto por su ID
    @Operation(summary = "Eliminar un producto", description = "Borra un producto por su ID")
    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente")
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(
            @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok().build(); // <— 200 OK, sin body
    }

    // borrar todos los productos
    @Operation(summary = "Eliminar todos los productos")
    @ApiResponse(responseCode = "200", description = "Todos los productos eliminados")
    @DeleteMapping("/productos")
    public ResponseEntity<String> deleteAllProductos(
            @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            productoService.deleteAllProductos();
            return ResponseEntity.ok("All products deleted successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("No se pueden eliminar los productos porque están en uso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar los productos: " + e.getMessage());
        }
    }

    // actualizar un producto
    @Operation(summary = "Actualizar un producto", description = "Modifica un producto existente por ID")
    @ApiResponse(responseCode = "200", description = "Producto actualizado", content = @Content(schema = @Schema(implementation = Producto.class)))
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(
            @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del producto a actualizar", required = true, content = @Content(schema = @Schema(implementation = Producto.class))) @RequestBody Producto producto) {
        producto.setId(id);
        Producto updated = productoService.saveProducto(producto);
        return ResponseEntity.ok(updated); // <— devuelve body con JSON
    }
}
