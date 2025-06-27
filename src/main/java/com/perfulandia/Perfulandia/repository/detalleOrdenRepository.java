package com.perfulandia.Perfulandia.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.DetalleOrden;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    // Mapea a d.carrito.id = :carritoId
    List<DetalleOrden> findByCarrito_Id(Long carritoId);

    // Mapea a d.orden.id = :ordenId
    List<DetalleOrden> findByOrden_Id(Long ordenId);

    // Si necesitas filtrar por producto:
    List<DetalleOrden> findByProducto_Id(Long productoId);
}
