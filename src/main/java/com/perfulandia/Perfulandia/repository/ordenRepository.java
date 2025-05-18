package com.perfulandia.Perfulandia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.orden;

import jakarta.transaction.Transactional;

@Repository
public interface ordenRepository extends JpaRepository<orden, Long> {
    // Leer
    Optional<orden> findById(Long id);
    @Query("SELECT o FROM orden o WHERE o.usuario.id = :usuarioId ORDER BY o.fecha_creacion DESC")
    List<orden> findByUsuarioId(Long usuarioId);

    @Query("SELECT o FROM Orden o where o.estado = :estado")
    List<orden> findByEstado(String estado);

    //leer todo
    @Query("SELECT o FROM orden o ORDER BY o.fecha_creacion DESC")
    List<orden> findAllOrdenes();
    

    //actualizar
    @Transactional
    @Modifying
    
    @Query("UPDATE orden o SET o.estado = :estado WHERE o.id = :id")
    void actualizarEstado(@Param("id") Long id, @Param("estado") String estado);
    
    @Transactional
    @Modifying
    @Query("UPDATE Orden o SET o.total = :total WHERE o.id = :id")
    void actualizarTotal(@Param("id") Long id, @Param("total") Integer total);

    //eliminar
    void deleteById(Long id);//Heredado

    @Transactional
    @Modifying
    @Query("DELETE FROM orden o WHERE o.usuario.id = :usuarioId")
    void deleteByUsuarioId(Long usuarioId);

}
