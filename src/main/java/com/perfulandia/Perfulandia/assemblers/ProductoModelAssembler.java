package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.*;
import com.perfulandia.Perfulandia.model.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
    @Override
    public EntityModel<Producto> toModel(Producto producto) {

        Link self = linkTo(methodOn(ProductoController.class)
                .getProductoById(producto.getId()))
                .withSelfRel();

        Link all = linkTo(methodOn(ProductoController.class)
                .getAllProductos())
                .withRel("productos");

        return EntityModel.of(producto, self, all);
    }
    
    public CollectionModel<EntityModel<Producto>> toCollectionModel(List<Producto> list) {
        List<EntityModel<Producto>> items = list.stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(
                items,
                linkTo(methodOn(ProductoController.class)
                        .getAllProductos())
                        .withSelfRel());
    }
}
