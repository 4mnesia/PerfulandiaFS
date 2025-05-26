package com.perfulandia.Perfulandia.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.repository.productoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class productoService {
    @Autowired
    private productoRepository productoRepository;
    // leer producto por id
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
    // leer todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }
    // guardar un producto
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    // eliminar un producto
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
    // actualizar un producto
    public Producto updateProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    // eliminar todos los productos
    public void deleteAllProductos() {
        productoRepository.deleteAll();
    }

}
