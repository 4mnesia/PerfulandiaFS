package com.perfulandia.Perfulandia.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
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
    // guardar varios productos
    public List<Producto> saveAllProductos(List<Producto> productos) {
        return productoRepository.saveAll(productos);
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

    //V2

    /** Filtra productos por categoría */
    public List<Producto> findByCategoria(String categoriaId) {
        return productoRepository.findByCategoria(categoriaId);
    }
    //filtrar nombre ignorando mayúsculas y minúsculas
    public List<Producto> findByNombreContainingIgnoreCase(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    //filtrar por precio
    public List<Producto> findByPrecioBetween(BigDecimal min, BigDecimal max) {
        return productoRepository.findByPrecioBetween(min, max);
    }
    //filtrar por stock
    public List<Producto> findByStockGreaterThan(int stock) {
        return productoRepository.findByStockGreaterThan(stock);
    }

    /** Filtra productos por marca */
    public List<Producto> findByMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }
}
