package com.perfulandia.Perfulandia.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.orden;

@Repository
public interface ordenRepository extends JpaRepository<orden, Long> {

    Optional<orden> findByUsuarioId(Long usuarioId);
    Optional<orden> findByCarritoId(Long carritoId);
}
