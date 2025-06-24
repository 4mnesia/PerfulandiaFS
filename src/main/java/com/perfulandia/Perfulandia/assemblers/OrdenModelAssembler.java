package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.OrdenControllerV2;
import com.perfulandia.Perfulandia.model.Orden;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrdenModelAssembler
        implements RepresentationModelAssembler<Orden, EntityModel<Orden>> {
    @Override
    @NonNull
    public EntityModel<Orden> toModel(@NonNull Orden orden) {
        return EntityModel.of(orden,
                linkTo(methodOn(OrdenControllerV2.class)
                        .getOrdenById(orden.getId())).withSelfRel(),
                linkTo(methodOn(OrdenControllerV2.class)
                        .getAllOrdenes()).withRel("ordenes"));
    }
}
