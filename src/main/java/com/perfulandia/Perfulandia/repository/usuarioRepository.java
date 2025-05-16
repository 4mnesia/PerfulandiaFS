package com.perfulandia.Perfulandia.repository;


import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perfulandia.Perfulandia.model.usuario;


public interface usuarioRepository extends JpaRepository<usuario, Long> {
    
    // Crear
    usuario save(usuario usuario);

    //leer
    Optional<usuario> findById(Long id);
    List<usuario> findAll();

    //leer por email
    Optional<usuario> findByEmail(String email);
    Boolean existsByEmail(String email);

    //leer administrador
    @Query("SELECT u FROM usuario u WHERE u.rol = 'ADMINISTRADOR'")
    List<usuario> findAllAdministradores();
    //leer cliente
    @Query("SELECT u FROM usuario u WHERE u.rol = 'CLIENTE'")
    List<usuario> findAllClientes();

    //actualizar

    @Transactional
    @Modifying
    @Query("UPDATE usuario u SET u.nombre = :nombre, u.email = :email Where u.id = :id")
    void actualizarUsuario( @Param("id") Long id,
                            @Param("nombre") String nombre,
                            @Param("email") String email);
    //actualizr password
    @Transactional
    @Modifying
    @Query("UPDATE usuario u SET u.password = :password Where u.id = :id")
    void actualizarPassword(@Param("id") Long id,
                            @Param("password") String password);


    //eliminar
    void deleteById(Long id);//Heredado

    @Transactional
    @Modifying
    @Query("DELETE FROM usuario u WHERE u.email = :email")
    void deleteByEmail(@Param("email") String email);

}
