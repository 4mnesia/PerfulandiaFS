package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.*;
import com.perfulandia.Perfulandia.model.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {
    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).getCarritoById(carrito.getId())).withSelfRel(),
                linkTo(methodOn(CarritoController.class).getAllCarritos()).withRel("carritos")
        );
    }
}