package com.perfulandia.Perfulandia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.detalleOrden;

@Repository
public interface detalleOrdenRepository extends JpaRepository<detalleOrden, Long> {
    Optional<detalleOrden> findByProductoId(Long productoId);
}
