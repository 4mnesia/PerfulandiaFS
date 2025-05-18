package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.usuario;
import com.perfulandia.Perfulandia.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public List<usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuario> getUsuarioById(@PathVariable Long id) {
        Optional<usuario> u = usuarioService.findById(id);
        return u.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public usuario createUsuario(@RequestBody usuario u) {
        return usuarioService.save(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<usuario> updateUsuario(@PathVariable Long id, @RequestBody usuario u) {
        Optional<usuario> optionalUsuario = usuarioService.findById(id);
        if (optionalUsuario.isPresent()) {
            usuario existing = optionalUsuario.get();
            existing.setNombre(u.getNombre());
            existing.setEmail(u.getEmail());
            existing.setPassword(u.getPassword());
            return ResponseEntity.ok(usuarioService.save(existing));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
