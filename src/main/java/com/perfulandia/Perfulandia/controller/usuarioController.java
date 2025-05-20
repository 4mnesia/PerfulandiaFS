package com.perfulandia.Perfulandia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.Perfulandia.model.usuario;
import com.perfulandia.Perfulandia.service.usuarioService;

@RestController
@RequestMapping("/api/v1")
// Ejemplo de uso de @Query en un repositorio, no en el controlador.
// Aquí solo puedes usarlo como comentario o referencia.
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping("/users/{id}")
    public usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @GetMapping("/users")
    public List<usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @PostMapping
    public usuario create(@RequestBody usuario usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @PutMapping("/users/{id}")
    public usuario updateUsuario(@PathVariable Long id, @RequestBody usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }

    @DeleteMapping("/users")
    public void deleteAllUsuarios() {
        usuarioService.deleteAllUsuarios();
    }

    // Ejemplo de cómo se usaría @Query en un repositorio:
    // @Repository
    // public interface UsuarioRepository extends JpaRepository<usuario, Long> {
    //     @Query("SELECT u FROM usuario u WHERE u.email = :email")
    //     usuario findByEmail(@Param("email") String email);
    // }
}
    
