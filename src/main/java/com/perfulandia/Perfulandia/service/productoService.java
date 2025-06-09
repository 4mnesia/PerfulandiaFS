package com.perfulandia.Perfulandia.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.repository.ProductoRepository;

import jakarta.transaction.Transactional;

import com.perfulandia.Perfulandia.repository.ItemCarritoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private ProductoRepository productoRepository;
    private ItemCarritoRepository itemCarritoRepository;

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
    @Transactional
    public void deleteProducto(Long id) {
        // Primero eliminamos los items del carrito asociados al producto
        itemCarritoRepository.deleteByProductoId(id);
        // Luego eliminamos el producto
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto con ID " + id + " no existe.");
        } productoRepository.deleteById(id);
    }
    // actualizar un producto
    public Producto updateProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    // eliminar todos los productos
    public void deleteAllProductos() {
        productoRepository.deleteAll();
    }

    //
}
