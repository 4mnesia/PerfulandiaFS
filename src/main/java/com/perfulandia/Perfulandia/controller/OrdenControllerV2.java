// OrdenControllerV2.java
package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.OrdenModelAssembler;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.IanaLinkRelations.SELF;

@RestController
@RequestMapping("/api/v2/perfulandia/orden")
public class OrdenControllerV2 {

    @Autowired private OrdenService service;
    @Autowired private OrdenModelAssembler assembler;

    @GetMapping(params = "estado")
    public CollectionModel<EntityModel<Orden>> getByEstado(
            @RequestParam EstadoOrden estado) {
        List<EntityModel<Orden>> list = service
            .findByEstado(estado).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByEstado(estado)).withSelfRel());
    }

    @GetMapping("/between")
    public CollectionModel<EntityModel<Orden>> getByFechaBetween(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta) {
        List<EntityModel<Orden>> list = service
            .findByFechaCreacionBetween(desde, hasta).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByFechaBetween(desde, hasta)).withSelfRel());
    }

    @GetMapping("/latest")
    public CollectionModel<EntityModel<Orden>> getLatest() {
        List<EntityModel<Orden>> list = service
            .findTop10ByOrderByFechaCreacionDesc().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getLatest()).withSelfRel());
    }

    // resto de CRUD...
    @GetMapping
    public CollectionModel<EntityModel<Orden>> getAllOrdenes() {
        List<EntityModel<Orden>> ordenes = service.getAllOrdenes().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(ordenes,
            linkTo(methodOn(OrdenControllerV2.class).getAllOrdenes()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Orden> getOrdenById(@PathVariable Long id) {
        Orden o = service.getOrdenById(id);
        if (o == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return assembler.toModel(o);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Orden>> createOrden(@RequestBody Orden nuevo) {
        Orden saved = service.saveOrden(nuevo);
        EntityModel<Orden> resource = assembler.toModel(saved);
        return ResponseEntity
            .created(resource.getRequiredLink(SELF).toUri())
            .body(resource);
    }

    @PutMapping("/{id}")
    public EntityModel<Orden> updateOrden(@PathVariable Long id,
                                         @RequestBody Orden actualizado) {
        actualizado.setId(id);
        Orden saved = service.updateOrden(id, actualizado);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
        service.deleteOrden(id);
        return ResponseEntity.noContent().build();
    }
}
