package com.perfulandia.Perfulandia.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Filtra carritos por ID de usuario
    List<Carrito> findByUsuarioId(Long usuarioId);
    // Filtra carritos por estado (activo/inactivo)
    List<Carrito> findByEstado(boolean estado);
}
