package com.perfulandia.Perfulandia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.*;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    // Obtiene los items de un carrito específico
    List<ItemCarrito> findByCarrito_Id(Long carritoId);
    // Obtiene items de un producto específico
    List<ItemCarrito> findByProductoId(Long productoId);
    // Filtra items cuya cantidad sea mayor a un valor
    List<ItemCarrito> findByCantidadGreaterThan(int cantidad);
}
