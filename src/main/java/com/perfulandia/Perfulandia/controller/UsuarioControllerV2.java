package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
import com.perfulandia.Perfulandia.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired private UsuarioService usuarioService;

    // Filtrar usuarios por rol
    @Operation(summary = "Filtrar usuarios por rol", description = "Obtiene los usuarios filtrados por el rol proporcionado")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios filtrados por rol")
    @ApiResponse(responseCode = "404", description = "No se encontraron usuarios para el rol proporcionado")
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> getByRol(@PathVariable String rol) {
        RolUsuario rolUsuario;
        try {
            rolUsuario = RolUsuario.valueOf(rol.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
        List<Usuario> usuarios = usuarioService.findByRol(rolUsuario);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    //filtrar por email
    @Operation(summary = "Filtrar usuario por email", description = "Obtiene un usuario por su email único")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "No se encontró un usuario con el email proporcionado")
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getByEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }
    // filtrar por nombre ignorando mayúsculas y minúsculas 
    @Operation(summary = "Filtrar usuarios por nombre", description = "Obtiene los usuarios cuyo nombre contiene la cadena proporcionada, ignorando mayúsculas y minúsculas")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios filtrados por nombre")
    @ApiResponse(responseCode = "404", description = "No se encontraron usuarios para el nombre proporcionado")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> getByNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombreContainingIgnoreCase(nombre);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }
}
