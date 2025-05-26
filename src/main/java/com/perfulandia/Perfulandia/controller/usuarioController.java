package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.service.usuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.perfulandia.Perfulandia.model.Usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfulandia")
// Ejemplo de uso de @Query en un repositorio, no en el controlador.
// Aquí solo puedes usarlo como comentario o referencia.
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    // LISTAR POR ID
    @GetMapping("/users/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    // LISTAR *
    @GetMapping("/users")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // CREAR USUARIO
    @PostMapping("/users")
    public ResponseEntity<Usuario> saveUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // CREAR VARIOS USUARIOS
    @PostMapping("/users/batch")
    public ResponseEntity<List<Usuario>> saveUsuarios(@Valid @RequestBody List<Usuario> usuarios) {
        try {
            List<Usuario> nuevosUsuarios = usuarios.stream()
                    .map(usuarioService::saveUsuario)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(nuevosUsuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ELIMINAR *
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteAllUsuarios() {
        try {
            usuarioService.deleteAllUsuarios();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ELIMINAR POR ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // actualizar
    @PutMapping("/users/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.getUsuarioById(id);
            if (usuarioExistente == null) {
                return ResponseEntity.notFound().build();
            }
            // Actualiza los campos necesarios
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setContraseña(usuario.getContraseña());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setRol(usuario.getRol());
            Usuario usuarioActualizado = usuarioService.saveUsuario(usuarioExistente);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
