package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.CarritoControllerV2;
import com.perfulandia.Perfulandia.model.Carrito;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoModelAssembler 
    implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    @NonNull
    public EntityModel<Carrito> toModel(@NonNull Carrito carrito) {
        return EntityModel.of(carrito,
            linkTo(methodOn(CarritoControllerV2.class)
                     .getCarritoById(carrito.getId()))
                .withSelfRel(),
            linkTo(methodOn(CarritoControllerV2.class)
                     .getAllCarritos())
                .withRel("carritos")
        );
    }
}
