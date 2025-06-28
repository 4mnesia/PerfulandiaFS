package com.perfulandia.Perfulandia.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    // Filtra órdenes por estado
    List<Orden> findByEstado(EstadoOrden estado);
    // Busca órdenes cuya dirección de envío contenga una cadena
    List<Orden> findByDireccionEnvio(String direccion);
    // Filtra órdenes por rango de fecha de creación
    List<Orden> findByFechaCreacionBetween(LocalDateTime start, LocalDateTime end);
    // Consulta personalizada: órdenes actualizadas después de una fecha
    @Query("SELECT o FROM Orden o WHERE o.fechaActualizacion >= ?1")
    List<Orden> findUpdatedAfter(LocalDateTime fecha);
}
