package com.perfulandia.Perfulandia.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    // Órdenes por estado (PENDIENTE, ENVIADA, etc.)
    List<Orden> findByEstado(EstadoOrden estado);

    // Órdenes en un rango de fechas
    List<Orden> findByFechaCreacionBetween(LocalDateTime desde, LocalDateTime hasta);

    // Últimas N órdenes
    List<Orden> findTop10ByOrderByFechaCreacionDesc();

    // Conteo de órdenes por usuario (si Orden tiene relación con Usuario)
    long countByUsuarioId(Long usuarioId);
}
