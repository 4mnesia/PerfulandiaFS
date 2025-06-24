package com.perfulandia.Perfulandia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.ItemCarrito;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    // Todos los items de un carrito
    List<ItemCarrito> findByCarritoId(Long carritoId);

    // Items de un determinado producto
    List<ItemCarrito> findByProductoId(Long productoId);

    // Conteo de items en un carrito
    long countByCarritoId(Long carritoId);

    // Elimina todos los items de un carrito
    void deleteByCarritoId(Long carritoId);
}
