package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import com.perfulandia.Perfulandia.model.Usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.validation.annotation.Validated;

@Tag(name = "Usuario", description = "Operaciones sobre usuarios")
@RestController
@RequestMapping("/api/perfulandia")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // LISTAR POR ID
    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @Parameter(description = "ID del usuario", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        Usuario u = usuarioService.getUsuarioById(id);
        return u != null ? ResponseEntity.ok(u) : ResponseEntity.notFound().build();

    }

    // LISTAR *
    @Operation(summary = "Listar todos los usuarios")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))))
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // CREAR USUARIO
    @Operation(summary = "Crear un usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(schema = @Schema(implementation = Usuario.class)))
    @PostMapping("/users")
    public ResponseEntity<?> saveUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a crear", required = true, content = @Content(schema = @Schema(implementation = Usuario.class))) @RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.saveUsuario(usuario));
    }

    // CREAR VARIOS USUARIOS
    @Operation(summary = "Crear usuarios en lote")
    @ApiResponse(responseCode = "200", description = "Usuarios guardados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))))
    @PostMapping("/users/batch")
    public ResponseEntity<List<Usuario>> saveUsuarios(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lista de usuarios a crear", required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))) @RequestBody List<Usuario> usuarios) {
        return ResponseEntity.ok(usuarioService.saveUsuarios(usuarios));
    }

    // ELIMINAR *
    @Operation(summary = "Eliminar todos los usuarios", description = "Borra todos los registros de Usuario del sistema")
    @ApiResponse(responseCode = "204", description = "Todos los usuarios eliminados")
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteAllUsuarios() {
        usuarioService.deleteAllUsuarios();
        return ResponseEntity.noContent().build();
    }

    // ELIMINAR POR ID
    @Operation(summary = "Eliminar un usuario")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario", required = true, in = ParameterIn.PATH) @PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // actualizar
    @Operation(summary = "Actualizar un usuario")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(schema = @Schema(implementation = Usuario.class)))
    @PutMapping("/users/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @Parameter(description = "ID del usuario", required = true, in = ParameterIn.PATH) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a actualizar", required = true, content = @Content(schema = @Schema(implementation = Usuario.class))) @RequestBody Usuario usuario) {
        usuario.setId(id);
        return ResponseEntity.ok(usuarioService.updateUsuario(usuario));

    }
}
