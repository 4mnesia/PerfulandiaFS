package com.perfulandia.Perfulandia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perfulandia.Perfulandia.model.carrito;

import jakarta.transaction.Transactional;

public interface carritoRepository extends JpaRepository<carrito, Long> {
    //CREAR
    carrito save(carrito carrito);

    //leer
    Optional<carrito> findById(Long id);

    @Query("SELECT c FROM carrito c WHERE c.usuario.id = :usuarioId")
    Optional<carrito> findByUsuarioId(Long usuarioId);

    //actualizar
    @Transactional
    @Modifying
    @Query("UPDATE carrito c SET c.usuario.id = :usuarioId WHERE c.id = :carritoId")
    void updateCarrito( @Param("carritoId") Long carritoId,
                        @Param("usuarioId") Long usuarioId);
    //eliminar
    void deleteById(Long id);//Heredado

    @Transactional
    @Modifying
    @Query("DELETE FROM carrito c WHERE c.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);
}
