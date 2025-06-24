package com.perfulandia.Perfulandia.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.DetalleOrden;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    // Detalles de una orden
    List<DetalleOrden> findByOrdenId(Long ordenId);

    // Detalles que provienen de un carrito específico (si guardas la relación)
    List<DetalleOrden> findByCarritoId(Long carritoId);

    // Total vendido de un producto (sumatoria)
    @Query("SELECT SUM(d.total) FROM DetalleOrden d WHERE d.producto.id = :prodId")
    BigDecimal sumTotalByProductoId(@Param("prodId") Long productoId);
}
