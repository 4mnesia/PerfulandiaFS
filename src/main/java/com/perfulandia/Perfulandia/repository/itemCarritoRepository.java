package com.perfulandia.Perfulandia.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perfulandia.Perfulandia.model.itemCarrito;

import jakarta.transaction.Transactional;

public interface itemCarritoRepository extends JpaRepository<itemCarrito, Long> {
    // Crear
    itemCarrito save(itemCarrito itemCarrito);

    // Leer
    Optional<itemCarrito> findById(Long id);

    @Query("SELECT i FROM itemCarrito i WHERE i.carrito.id = :carritoId")
    List<itemCarrito> findByCarritoId(Long carritoId);
    
    @Query("SELECT i FROM itemCarrito i WHERE i.producto.id = :productoId and i.carrito.id = :carritoId")
    Optional<itemCarrito> findByProductoIdAndCarritoId(Long productoId, Long carritoId);
    // Actualizar

    @Transactional
    @Modifying
    @Query("UPDATE itemCarrito i SET i.cantidad = :cantidad WHERE i.id = :id")
    void actualizarCantidad(@Param("id") Long id,
                            @Param("cantidad") Integer cantidad);
    //eliminar
    void deleteById(Long id);//Heredado
    
    @Transactional
    @Modifying
    @Query("DELETE FROM itemCarrito i WHERE i.carrito.id = :carritoId")
    void deleteByCarritoId(Long carritoId);

    @Transactional
    @Modifying
    @Query("DELETE FROM itemCarrito i WHERE i.producto.id = :productoId and i.carrito.id = :carritoId")
    void deleteByProductoIdAndCarritoId(Long productoId, Long carritoId);



}
