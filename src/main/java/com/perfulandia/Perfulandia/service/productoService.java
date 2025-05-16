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

    /**
     * Obtiene todos los productos disponibles
     * @return Lista de todos los productos
     */
    public List<producto> getAllProductos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su ID
     * @param id ID del producto a buscar
     * @return Producto encontrado o null si no existe
     */
    public producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo producto o actualiza uno existente
     * @param producto Producto a guardar
     * @return Producto guardado con su ID asignado
     */
    public producto saveProducto(producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto por su ID
     * @param id ID del producto a eliminar
     */
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    /**
     * Busca productos por nombre
     * @param nombre Nombre del producto
     * @return Lista de productos que coinciden con el nombre
     */
    public List<producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    /**
     * Actualiza el stock de un producto
     * @param id ID del producto
     * @param cantidad Nueva cantidad en stock
     */
    public void actualizarStock(Long id, Integer cantidad) {
        productoRepository.actualizarStock(id, cantidad);
    }

    /**
     * Verifica si hay suficiente stock de un producto
     * @param id ID del producto
     * @param cantidad Cantidad requerida
     * @return true si hay suficiente stock, false en caso contrario
     */
    public Boolean hayStockSuficiente(Long id, Integer cantidad) {
        producto producto = getProductoById(id);
        return producto != null && producto.getStock() >= cantidad;
    }
}
