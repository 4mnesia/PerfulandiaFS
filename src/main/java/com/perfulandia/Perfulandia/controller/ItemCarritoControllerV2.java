
package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.ItemCarritoModelAssembler;
import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.service.ItemCarritoService;
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
@RequestMapping("/api/v2/perfulandia/itemcarrito")
public class ItemCarritoControllerV2 {

    @Autowired private ItemCarritoService service;
    @Autowired private ItemCarritoModelAssembler assembler;

    @GetMapping(params = "carritoId")
    public CollectionModel<EntityModel<ItemCarrito>> getByCarritoId(
            @RequestParam Long carritoId) {
        List<EntityModel<ItemCarrito>> list = service
            .findByCarritoId(carritoId).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByCarritoId(carritoId)).withSelfRel());
    }

    @GetMapping(params = "productoId")
    public CollectionModel<EntityModel<ItemCarrito>> getByProductoId(
            @RequestParam Long productoId) {
        List<EntityModel<ItemCarrito>> list = service
            .findByProductoId(productoId).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByProductoId(productoId)).withSelfRel());
    }

    // resto de CRUD...
    @GetMapping
    public CollectionModel<EntityModel<ItemCarrito>> getAllItemCarritos() {
        List<EntityModel<ItemCarrito>> list = service.getAllItemCarritos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getAllItemCarritos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ItemCarrito> getItemCarritoById(@PathVariable Long id) {
        ItemCarrito item = service.getItemCarritoById(id);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return assembler.toModel(item);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ItemCarrito>> createItemCarrito(@RequestBody ItemCarrito nuevo) {
        ItemCarrito saved = service.saveItemCarrito(nuevo);
        EntityModel<ItemCarrito> resource = assembler.toModel(saved);
        return ResponseEntity
            .created(resource.getRequiredLink(SELF).toUri())
            .body(resource);
    }

    @PutMapping("/{id}")
    public EntityModel<ItemCarrito> updateItemCarrito(
            @PathVariable Long id,
            @RequestBody ItemCarrito actualizado) {
        actualizado.setId(id);
        ItemCarrito saved = service.saveItemCarrito(actualizado);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemCarrito(@PathVariable Long id) {
        service.deleteItemCarrito(id);
        return ResponseEntity.noContent().build();
    }
}
