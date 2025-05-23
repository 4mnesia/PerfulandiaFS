package com.perfulandia.Perfulandia.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perfulandia.Perfulandia.model.producto;
import com.perfulandia.Perfulandia.repository.productoRepository;

@Service
public class productoService {
    @Autowired
    private productoRepository productoRepository;

    // leer producto por id
    public producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
    // leer todos los productos
    public List<producto> getAllProductos() {
        return productoRepository.findAll();
    }
    // guardar un producto
    public producto saveProducto(producto producto) {
        return productoRepository.save(producto);
    }
    // eliminar un producto
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
    // actualizar un producto
    public producto updateProducto(producto producto) {
        return productoRepository.save(producto);
    }
    // eliminar todos los productos
    public void deleteAllProductos() {
        productoRepository.deleteAll();
    }

}
