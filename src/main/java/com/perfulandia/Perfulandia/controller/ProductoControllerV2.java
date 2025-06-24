package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.ProductoModelAssembler;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.service.ProductoService;
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
@RequestMapping("/api/v2/perfulandia/productos")
public class ProductoControllerV2 {

    @Autowired private ProductoService service;
    @Autowired private ProductoModelAssembler assembler;

    @GetMapping(params = "categoria")
    public CollectionModel<EntityModel<Producto>> getByCategoria(
            @RequestParam String categoria) {
        List<EntityModel<Producto>> list = service
            .findByCategoria(categoria).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByCategoria(categoria)).withSelfRel());
    }

    @GetMapping(params = "marca")
    public CollectionModel<EntityModel<Producto>> getByMarca(
            @RequestParam String marca) {
        List<EntityModel<Producto>> list = service
            .findByMarca(marca).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByMarca(marca)).withSelfRel());
    }

    @GetMapping(params = "modelo")
    public CollectionModel<EntityModel<Producto>> getByModelo(
            @RequestParam String modelo) {
        List<EntityModel<Producto>> list = service
            .findByModeloContaining(modelo).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByModelo(modelo)).withSelfRel());
    }

    @GetMapping("/pricerange")
    public CollectionModel<EntityModel<Producto>> getByPrecioBetween(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<EntityModel<Producto>> list = service
            .findByPrecioBetween(min, max).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(list,
            linkTo(methodOn(getClass()).getByPrecioBetween(min, max)).withSelfRel());
    }

    // resto de CRUD...
    @GetMapping
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = service.getAllProductos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(productos,
            linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Producto> getProductoById(@PathVariable Long id) {
        Producto p = service.getProductoById(id);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return assembler.toModel(p);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto nuevo) {
        Producto saved = service.saveProducto(nuevo);
        EntityModel<Producto> resource = assembler.toModel(saved);
        return ResponseEntity
            .created(resource.getRequiredLink(SELF).toUri())
            .body(resource);
    }

    @PutMapping("/{id}")
    public EntityModel<Producto> updateProducto(@PathVariable Long id,
                                               @RequestBody Producto actualizado) {
        actualizado.setId(id);
        Producto saved = service.saveProducto(actualizado);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        service.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
