package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.service.usuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.perfulandia.Perfulandia.model.Usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/perfulandia")
@Validated
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    // LISTAR POR ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener el usuario: " + e.getMessage());
        }
    }

    // LISTAR *
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    // CREAR USUARIO
    @PostMapping("/users")
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioService.saveUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/users/batch")
    public ResponseEntity<?> saveUsuarios(@Valid @RequestBody List<Usuario> usuarios) {
        try {
            List<Usuario> nuevos = usuarios.stream()
                    .map(usuarioService::saveUsuario)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(nuevos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ELIMINAR *
    @DeleteMapping("/users")
    public ResponseEntity<?> deleteAllUsuarios() {
        try {
            usuarioService.deleteAllUsuarios();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ELIMINAR POR ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            if (usuarioService.getUsuarioById(id) == null) {
                return ResponseEntity.notFound().build();
            }
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // actualizar
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario existente = usuarioService.getUsuarioById(id);
            if (existente == null) return ResponseEntity.notFound().build();
            existente.setNombre(usuario.getNombre());
            existente.setEmail(usuario.getEmail());
            existente.setContraseña(usuario.getContraseña());
            existente.setDireccion(usuario.getDireccion());
            existente.setTelefono(usuario.getTelefono());
            existente.setRol(usuario.getRol());
            return ResponseEntity.ok(usuarioService.saveUsuario(existente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
