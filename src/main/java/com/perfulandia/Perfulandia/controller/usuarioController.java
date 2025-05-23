package com.perfulandia.Perfulandia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.perfulandia.Perfulandia.model.usuario;

import com.perfulandia.Perfulandia.service.usuarioService;

@RestController
@RequestMapping("/api/perfulandia")
// Ejemplo de uso de @Query en un repositorio, no en el controlador.
// Aquí solo puedes usarlo como comentario o referencia.
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    
    // LISTAR POR ID
    @GetMapping("/users/{id}")
    public usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    // LISTAR TODO
    @GetMapping("/users")
    public List<usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // CREAR USUARIO
    @PostMapping("/users")
    public ResponseEntity<usuario> createUsuario(@RequestBody usuario usuario) {
        try {
            usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // CREAR VARIOS USUARIOS
    @PostMapping("users/batch")
    public ResponseEntity<List<usuario>> createUsuarios(@RequestBody List<usuario> usuarios) {
        try {
            List<usuario> nuevosUsuarios = usuarios.stream()
                    .map(usuarioService::saveUsuario)
                    .toList();
            return ResponseEntity.ok(nuevosUsuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ELIMINAR TODO
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
    public ResponseEntity<usuario> updateUsuario(@PathVariable Long id, @RequestBody usuario usuario) {
        try {
            usuario usuarioExistente = usuarioService.getUsuarioById(id);
            if (usuarioExistente == null) {
                return ResponseEntity.notFound().build();
            }
            // Actualiza los campos necesarios
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setPassword(usuario.getPassword());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setRol(usuario.getRol());
            usuario usuarioActualizado = usuarioService.saveUsuario(usuarioExistente);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    
    }
            
}
