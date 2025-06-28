package com.perfulandia.Perfulandia.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.DetalleOrden;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    // Detalles asociados a una orden
    List<DetalleOrden> findByOrden_Id(Long ordenId);
    // Filtra detalles por rango de precio unitario
    List<DetalleOrden> findByPrecioUnitarioBetween(BigDecimal min, BigDecimal max);
}
