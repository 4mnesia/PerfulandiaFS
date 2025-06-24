package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.UsuarioModelAssembler;
import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
import com.perfulandia.Perfulandia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.IanaLinkRelations.SELF;

@RestController
@RequestMapping("/api/v2/perfulandia/users")
public class UsuarioControllerV2 {

    @Autowired private UsuarioService service;
    @Autowired private UsuarioModelAssembler assembler;

    @GetMapping(params = "email")
    public EntityModel<Usuario> getByEmail(@RequestParam String email) {
        Usuario u = service.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(u);
    }

    @GetMapping(params = "rol")
    public CollectionModel<EntityModel<Usuario>> getByRol(
            @RequestParam RolUsuario rol) {
        List<EntityModel<Usuario>> list = service
            .findByRol(rol).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByRol(rol)).withSelfRel());
    }

    @GetMapping(params = "nombre")
    public CollectionModel<EntityModel<Usuario>> getByNombreContaining(
            @RequestParam String nombre) {
        List<EntityModel<Usuario>> list = service
            .findByNombreContainingIgnoreCase(nombre).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByNombreContaining(nombre)).withSelfRel());
    }

    // resto de CRUD...
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = service.getAllUsuarios().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
            linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario u = service.getUsuarioById(id);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return assembler.toModel(u);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario nuevo) {
        Usuario saved = service.saveUsuario(nuevo);
        EntityModel<Usuario> resource = assembler.toModel(saved);
        return ResponseEntity
            .created(resource.getRequiredLink(SELF).toUri())
            .body(resource);
    }

    @PutMapping("/{id}")
    public EntityModel<Usuario> updateUsuario(@PathVariable Long id,
                                              @RequestBody Usuario actualizado) {
        actualizado.setId(id);
        Usuario saved = service.updateUsuario(actualizado);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        service.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
