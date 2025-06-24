package com.perfulandia.Perfulandia.repository;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Búsqueda por email
    Optional<Usuario> findByEmail(String email);

    // Listar por rol
    List<Usuario> findByRol(RolUsuario rol);

    // Buscar usuarios cuyo nombre contenga texto
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    // Paginado de usuarios
    Page<Usuario> findAll(Pageable pageable);
}
