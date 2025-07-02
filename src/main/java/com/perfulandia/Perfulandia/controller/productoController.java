package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.perfulandia.Perfulandia.assemblers.ProductoModelAssembler;
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


import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Productos", description = "Operaciones sobre productos")
@RestController
@RequestMapping("/api/perfulandia")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    // Obtener todos los productos 
    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "OK",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
    @GetMapping("/productos")
    public ResponseEntity<?> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay productos disponibles.");
        }
        CollectionModel<EntityModel<Producto>> model = assembler.toCollectionModel(productos);
        return ResponseEntity.ok(model);
    }

    // Obtener producto por ID con HATEOAS
    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
          content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getProductoById(
        @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH)
        @PathVariable Long id) {
        Producto p = productoService.getProductoById(id);
        if (p != null) {
            EntityModel<Producto> model = assembler.toModel(p);
            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo producto
    @Operation(summary = "Crear un producto")
    @ApiResponse(responseCode = "201", description = "Producto creado",
      content = @Content(schema = @Schema(implementation = Producto.class)))
    @PostMapping("/productos")
    public ResponseEntity<Producto> createProducto(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Datos del producto a crear", required = true,
          content = @Content(schema = @Schema(implementation = Producto.class)))
        @RequestBody Producto producto) {
        Producto productoGuardado = productoService.saveProducto(producto);
        if (productoGuardado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

    // crear varios productos en lote
    @Operation(summary = "Crear varios productos en lote", description = "Recibe una lista de productos y los guarda todos de una vez")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos creados correctamente",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
        @ApiResponse(responseCode = "400", description = "Lista de productos inválida")
    })
    @PostMapping("/productos/batch")
    public ResponseEntity<List<Producto>> createProductosBatch(
        @RequestBody List<Producto> productos) {
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
        @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH)
        @PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok().build();
    }

    // borrar todos los productos
    @Operation(summary = "Eliminar todos los productos")
    @ApiResponse(responseCode = "200", description = "Todos los productos eliminados")
    @DeleteMapping("/productos")
    public ResponseEntity<String> deleteAllProductos() {
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
    @ApiResponse(responseCode = "200", description = "Producto actualizado",
      content = @Content(schema = @Schema(implementation = Producto.class)))
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(
        @Parameter(description = "ID del producto", required = true, in = ParameterIn.PATH)
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Datos del producto a actualizar", required = true,
          content = @Content(schema = @Schema(implementation = Producto.class)))
        @RequestBody Producto producto) {
        producto.setId(id);
        Producto updated = productoService.saveProducto(producto);
        return ResponseEntity.ok(updated);
    }
}
