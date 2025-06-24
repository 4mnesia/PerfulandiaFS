package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.ProductoControllerV2;
import com.perfulandia.Perfulandia.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler
        implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
    @Override
    @NonNull
    public EntityModel<Producto> toModel(@NonNull Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class)
                     .getProductoById(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class)
                     .getAllProductos()).withRel("productos")
        );
    }
}

