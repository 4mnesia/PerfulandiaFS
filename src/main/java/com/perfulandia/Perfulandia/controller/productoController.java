package com.perfulandia.Perfulandia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.Perfulandia.model.producto;
import com.perfulandia.Perfulandia.service.productoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/perfulandia")
public class productoController {

    @Autowired
    private productoService productoService;
    // Aquí puedes agregar los métodos para manejar las operaciones CRUD de productos

    // Ejemplo de un método para obtener todos los productos
    @GetMapping("/productos")
    public List<producto> getAllProductos() {
        return productoService.getAllProductos();
    }
    // Ejemplo de un método para obtener un producto por su ID y si no existe devolver un null
    @GetMapping("/productos/{id}")
    public producto getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }
    // Ejemplo de un método para crear un nuevo producto
    @PostMapping("/productos")
    public producto createProducto(@RequestBody producto nuevoProducto) {
        return productoService.saveProducto(nuevoProducto);
    }
    // Ejemplo de un método para crear varios productos
    @PostMapping("/productos/batch")
    public List<producto> createProductos(@RequestBody List<producto> nuevosProductos) {
        return nuevosProductos.stream()
                .map(productoService::saveProducto)
                .toList();
    }
    
    // Ejemplo de un método para eliminar un producto por su ID
    @DeleteMapping("/productos/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }
    //borrar todos los productos
    @DeleteMapping("/productos")
    public void deleteAllProductos() {
        productoService.deleteAllProductos();
    }
    //actualizar un producto
    @PutMapping("/productos/{id}")
    public producto updateProducto(@PathVariable Long id, @RequestBody producto productoActualizado) {
        producto productoExistente = productoService.getProductoById(id);
        if (productoExistente != null) {
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            // Actualiza otros campos según sea necesario
            return productoService.saveProducto(productoExistente);
        }
        return null; // O lanza una excepción si el producto no existe
    }
    
}
