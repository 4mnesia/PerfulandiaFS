// DetalleOrdenControllerV2.java
package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.DetalleOrdenModelAssembler;
import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.IanaLinkRelations.SELF;

@RestController
@RequestMapping("/api/v2/perfulandia/detalleorden")
public class DetalleOrdenControllerV2 {

    @Autowired private DetalleOrdenService service;
    @Autowired private DetalleOrdenModelAssembler assembler;

    @GetMapping(params = "ordenId")
    public CollectionModel<EntityModel<DetalleOrden>> getByOrdenId(
            @RequestParam Long ordenId) {
        List<EntityModel<DetalleOrden>> list = service
            .findByOrdenId(ordenId).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByOrdenId(ordenId)).withSelfRel());
    }

    @GetMapping(params = "carritoId")
    public CollectionModel<EntityModel<DetalleOrden>> getByCarritoId(
            @RequestParam Long carritoId) {
        List<EntityModel<DetalleOrden>> list = service
            .findByCarritoId(carritoId).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByCarritoId(carritoId)).withSelfRel());
    }

    @GetMapping("/sumTotal")
    public EntityModel<BigDecimal> sumTotalByProductoId(
            @RequestParam Long productoId) {
        BigDecimal total = service.sumTotalByProductoId(productoId);
        return EntityModel.of(total,
            linkTo(methodOn(getClass()).sumTotalByProductoId(productoId)).withSelfRel());
    }

    // resto de CRUD...
    @GetMapping
    public CollectionModel<EntityModel<DetalleOrden>> getAllDetalles() {
        List<EntityModel<DetalleOrden>> detalles = service.getAllDetalles().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(detalles,
            linkTo(methodOn(DetalleOrdenControllerV2.class).getAllDetalles()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<DetalleOrden> getDetalleById(@PathVariable Long id) {
        DetalleOrden det = service.getDetalleOrdenById(id);
        if (det == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return assembler.toModel(det);
    }

    @PostMapping
    public ResponseEntity<EntityModel<DetalleOrden>> createDetalleOrden(
            @RequestBody DetalleOrden nuevo) {
        DetalleOrden saved = service.saveDetalleOrden(nuevo);
        EntityModel<DetalleOrden> resource = assembler.toModel(saved);
        return ResponseEntity
            .created(resource.getRequiredLink(SELF).toUri())
            .body(resource);
    }

    @PutMapping("/{id}")
    public EntityModel<DetalleOrden> updateDetalleOrden(
            @PathVariable Long id,
            @RequestBody DetalleOrden actualizado) {
        actualizado.setId(id);
        DetalleOrden saved = service.saveDetalleOrden(actualizado);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleOrden(@PathVariable Long id) {
        service.deleteDetalleOrden(id);
        return ResponseEntity.noContent().build();
    }
}
