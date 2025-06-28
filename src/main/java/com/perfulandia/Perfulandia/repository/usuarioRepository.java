package com.perfulandia.Perfulandia.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Filtra usuarios por rol
      List<Usuario> findByRol(RolUsuario rol);
      // Busca un usuario por su email único
      Usuario findByEmail(String email);
      // Busca usuarios cuyo nombre contenga la cadena, sin distinguir mayúsculas
      List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}
