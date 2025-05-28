package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.perfulandia.Perfulandia.service.productoService;
import com.perfulandia.Perfulandia.model.Producto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class productoController {

    @Autowired
    private productoService productoService;
    // Aquí puedes agregar los métodos para manejar las operaciones CRUD de productos

    // Ejemplo de un método para obtener todos los productos
    @GetMapping("/productos")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }
    // Ejemplo de un método para obtener un producto por su ID y si no existe devolver un null
    @GetMapping("/productos/{id}")
    public Producto getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }
    // Ejemplo de un método para crear un nuevo producto
    @PostMapping("/productos")
    public Producto createProducto(@RequestBody Producto nuevoProducto) {
        return productoService.saveProducto(nuevoProducto);
    }
    // Ejemplo de un método para crear varios productos
    @PostMapping("/productos/batch")
    public List<Producto> createProductos(@RequestBody List<Producto> nuevosProductos) {
        return nuevosProductos.stream()
                .map(productoService::saveProducto)
                .toList();
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
    }
}
    //borrar todos los productos
    @DeleteMapping("/productos")
    public ResponseEntity<String> deleteAllProductos() {
        try {
            productoService.deleteAllProductos();
            return ResponseEntity.ok("All products deleted successfully");
        } catch (RuntimeException e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting products");
        }
    }
    //actualizar un producto
    @PutMapping("/productos/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Producto productoExistente = productoService.getProductoById(id);
        if (productoExistente != null) {
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            // Actualiza otros campos según sea necesario
            return productoService.saveProducto(productoExistente);
        }
        return null; // O lanza una excepción si el producto no existe
    }
    
}
