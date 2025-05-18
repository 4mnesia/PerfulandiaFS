package com.perfulandia.Perfulandia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.carrito;

import jakarta.transaction.Transactional;

@Repository
public interface carritoRepository extends JpaRepository<carrito, Long> {
    //leer por id
    Optional<carrito> findById(Long id);

    @Query("SELECT c FROM carrito c WHERE c.usuario.id = :usuarioId")
    Optional<carrito> findByUsuarioId(Long usuarioId);
    //leer todo
    

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
    //calcular total
    
    @Query("SELECT SUM(i.precioUnitario * i.cantidad) FROM itemCarrito i WHERE i.carrito.id = :carritoId")
    Integer calcularTotal(@Param("carritoId") Long carritoId);
    //vaciar carrito
    @Transactional
    @Modifying
    @Query("DELETE FROM itemCarrito i WHERE i.carrito.id = :carritoId")
    void vaciarCarrito(@Param("carritoId") Long carritoId);
    

}
