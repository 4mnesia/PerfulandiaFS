package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.ProductoModelAssembler;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@Tag(name = "Productos V2", description = "Filtros avanzados de productos")
@RestController
@RequestMapping("/api/v2/perfulandia/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @Operation(summary = "Filtrar productos por categoría", description = "Obtiene los productos cuya categoría coincide con la ruta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos filtrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos para la categoría proporcionada"),
        @ApiResponse(responseCode = "400", description = "Error en la petición")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByCategoria(
            @Parameter(description = "Categoría a filtrar", required = true, in = ParameterIn.PATH)
            @PathVariable String categoria) {
        try {
            List<Producto> productos = productoService.findByCategoria(categoria);
            if (productos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Filtrar productos por nombre", description = "Busca productos cuyo nombre contiene la cadena dada, ignore case")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos filtrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos para el nombre proporcionado"),
        @ApiResponse(responseCode = "400", description = "Error en la petición")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByNombre(
            @Parameter(description = "Nombre a buscar", required = true, in = ParameterIn.PATH)
            @PathVariable String nombre) {
        try {
            List<Producto> productos = productoService.findByNombreContainingIgnoreCase(nombre);
            if (productos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Filtrar productos por rango de precio", description = "Obtiene los productos cuyo precio está entre los valores mínimo y máximo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos filtrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos para el rango de precio proporcionado"),
        @ApiResponse(responseCode = "400", description = "Error en la petición")
    })
    @GetMapping("/precio")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByPrecioBetween(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        try {
            List<Producto> productos = productoService.findByPrecioBetween(min, max);
            if (productos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Filtrar productos por stock", description = "Obtiene los productos cuyo stock es mayor al valor proporcionado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos filtrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con stock mayor al valor proporcionado"),
        @ApiResponse(responseCode = "400", description = "Error en la petición")
    })
    @GetMapping("/stock/{stock}")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByStockGreaterThan(
            @Parameter(description = "Valor de stock mínimo", required = true, in = ParameterIn.PATH)
            @PathVariable int stock) {
        try {
            List<Producto> productos = productoService.findByStockGreaterThan(stock);
            if (productos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Filtrar productos por marca", description = "Obtiene los productos cuya marca coincide con la proporcionada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos filtrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos para la marca proporcionada"),
        @ApiResponse(responseCode = "400", description = "Error en la petición")
    })
    @GetMapping("/marca/{marca}")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getByMarca(
            @Parameter(description = "Marca a filtrar", required = true, in = ParameterIn.PATH)
            @PathVariable String marca) {
        try {
            List<Producto> productos = productoService.findByMarca(marca);
            if (productos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
