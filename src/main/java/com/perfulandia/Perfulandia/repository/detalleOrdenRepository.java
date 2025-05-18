package com.perfulandia.Perfulandia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.detalleOrden;

import jakarta.transaction.Transactional;

@Repository
public interface detalleOrdenRepository extends JpaRepository<detalleOrden, Long> {

    // Leer
    Optional<detalleOrden> findById(Long id);

    @Query("SELECT d FROM detalleOrden d WHERE d.orden.id = :ordenId")
    List<detalleOrden> findByOrdenId(Long ordenId);

    @Query("SELECT d FROM detalleOrden d WHERE d.producto.id = :productoId")
    List<detalleOrden> findByProductoId(Long productoId);

    // Actualizar

    @Transactional
    @Modifying
    @Query("UPDATE DetalleOrden d SET d.cantidad = :cantidad, d.precioUnitario = :precio WHERE d.id = :id")
    void actualizarCantidadYPrecio( @Param("id") Long id,
                                    @Param("cantidad") Integer cantidad,
                                    @Param("precio") Double precio);
    
    //eliminar
    void deleteById(Long id);//Heredado

    @Transactional
    @Modifying
    @Query("DELETE FROM detalleOrden d WHERE d.orden.id = :ordenId")
    void deleteByOrdenId(@Param("ordenId") Long ordenId);

    //calular todo detalle
    @Query("SELECT SUM(d.cantidad * d.precioUnitario) FROM detalleOrden d WHERE d.orden.id = :ordenId")
    Integer calcularTotal(@Param("ordenId") Long ordenId);
    
    //vaciar detalle
    @Transactional
    @Modifying
    @Query("DELETE FROM detalleOrden d WHERE d.orden.id = :ordenId")
    void vaciarDetalle(@Param("ordenId") Long ordenId);


}
