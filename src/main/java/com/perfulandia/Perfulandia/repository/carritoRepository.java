package com.perfulandia.Perfulandia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Encuentra todos los carritos de un usuario concreto
    List<Carrito> findByUsuarioId(Long usuarioId);

    // Encuentra todos los carritos con un estado (activo/inactivo)
    List<Carrito> findByEstado(boolean estado);

    // Encuentra un carrito activo de un usuario para usarlo como “carrito actual”
    Optional<Carrito> findByUsuarioIdAndEstadoTrue(Long usuarioId);
}
