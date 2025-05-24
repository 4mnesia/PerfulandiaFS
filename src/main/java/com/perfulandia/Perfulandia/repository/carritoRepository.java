package com.perfulandia.Perfulandia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.carrito;


@Repository
public interface carritoRepository extends JpaRepository<carrito, Long> {
    Optional<carrito> findByUsuarioId(Long usuarioId);
}
