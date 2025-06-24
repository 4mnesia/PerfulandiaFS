package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.CarritoModelAssembler;
import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.service.CarritoService;
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
@RequestMapping("/api/v2/perfulandia/carrito")
public class CarritoControllerV2 {

    @Autowired
    private CarritoService service;
    @Autowired
    private CarritoModelAssembler assembler;

    // Filtrar por usuarioId
    @GetMapping(params = "usuarioId")
    public CollectionModel<EntityModel<Carrito>> getByUsuario(
            @RequestParam Long usuarioId) {
        List<EntityModel<Carrito>> list = service
                .findByUsuarioId(usuarioId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(getClass()).getByUsuario(usuarioId)).withSelfRel());
    }

    // Filtrar por estado
    @GetMapping(params = "estado")
    public CollectionModel<EntityModel<Carrito>> getByEstado(
            @RequestParam boolean estado) {
        List<EntityModel<Carrito>> list = service
                .findByEstado(estado).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(getClass()).getByEstado(estado)).withSelfRel());
    }

    // Carrito activo de un usuario
    @GetMapping(value = "/active", params = "usuarioId")
    public EntityModel<Carrito> getActiveCarrito(@RequestParam Long usuarioId) {
        Carrito c = service.findByUsuarioIdAndEstadoTrue(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(c);
    }

    // Resto de endpoints ya conocidos...
    @GetMapping
    public CollectionModel<EntityModel<Carrito>> getAllCarritos() {
        List<EntityModel<Carrito>> list = service.getAllCarritos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(getClass()).getAllCarritos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Carrito> getCarritoById(@PathVariable Long id) {
        Carrito c = service.getCarritoById(id);
        if (c == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return assembler.toModel(c);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Carrito>> createCarrito(@RequestBody Carrito nuevo) {
        Carrito saved = service.saveCarrito(nuevo);
        EntityModel<Carrito> res = assembler.toModel(saved);
        return ResponseEntity.created(res.getRequiredLink(SELF).toUri()).body(res);
    }

    @PutMapping("/{id}")
    public EntityModel<Carrito> updateCarrito(@PathVariable Long id,
            @RequestBody Carrito actualizado) {
        actualizado.setId(id);
        Carrito saved = service.updateCarrito(actualizado);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        service.deleteCarrito(id);
        return ResponseEntity.noContent().build();
    }
}