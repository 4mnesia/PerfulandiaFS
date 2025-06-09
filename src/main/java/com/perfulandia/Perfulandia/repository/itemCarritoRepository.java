package com.perfulandia.Perfulandia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.ItemCarrito;
import jakarta.transaction.Transactional;



@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    Optional<ItemCarrito> findByCarritoId(Long carritoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM itemCarrito i WHERE i.producto.id = :productoId")
    void deleteByProductoId(@Param("productoId") Long productoId);
/* 
    @Transactional
    @Modifying
    void deleteByProductoId(Long productoId); */
}
