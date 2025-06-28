package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.*;
import com.perfulandia.Perfulandia.model.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrdenModelAssembler implements RepresentationModelAssembler<Orden, EntityModel<Orden>> {
    @Override
    public EntityModel<Orden> toModel(Orden orden) {
        return EntityModel.of(orden,
                linkTo(methodOn(OrdenController.class).getOrdenById(orden.getId())).withSelfRel(),
                linkTo(methodOn(OrdenController.class).getAllOrdenes()).withRel("ordenes")
        );
    }
}