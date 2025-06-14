package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.perfulandia.Perfulandia.service.ProductoService;
import com.perfulandia.Perfulandia.model.Producto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos con validación sencilla
    @GetMapping("/productos")
    public ResponseEntity<?> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay productos disponibles.");
        }
        return ResponseEntity.ok(productos);
    }

    // Ejemplo de un método para obtener un producto por su ID y si no existe devolver un null
    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) throw new Exception();
            Producto producto = productoService.getProductoById(id);
            if (producto == null) throw new Exception();
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        }
    }
    // Ejemplo de un método para crear un nuevo producto
    @PostMapping("/productos")
    public ResponseEntity<?> createProducto(@RequestBody Producto nuevoProducto) {
        try {
            Producto productoGuardado = productoService.saveProducto(nuevoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el producto: " + e.getMessage());
        }
    }
    // Ejemplo de un método para crear varios productos
    @PostMapping("/productos/batch")
    public ResponseEntity<?> createProductos(@RequestBody List<Producto> nuevosProductos) {
        try {
            List<Producto> productosGuardados = nuevosProductos.stream()
                    .map(productoService::saveProducto)
                    .toList();
            return ResponseEntity.status(HttpStatus.CREATED).body(productosGuardados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear los productos: " + e.getMessage());
        }
    }
    
    // Ejemplo de un método para eliminar un producto por su ID
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("No se puede eliminar el producto porque está en carritos de compra");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }
    //borrar todos los productos
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
    //actualizar un producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        try {
            Producto productoExistente = productoService.getProductoById(id);
            if (productoExistente != null) {
                productoExistente.setNombre(productoActualizado.getNombre());
                productoExistente.setPrecio(productoActualizado.getPrecio());
                // Actualiza otros campos según sea necesario
                Producto productoGuardado = productoService.saveProducto(productoExistente);
                return ResponseEntity.ok(productoGuardado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }
    
}
