package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.DetalleOrdenControllerV2;
import com.perfulandia.Perfulandia.model.DetalleOrden;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetalleOrdenModelAssembler
        implements RepresentationModelAssembler<DetalleOrden, EntityModel<DetalleOrden>> {
    @Override
    @NonNull
    public EntityModel<DetalleOrden> toModel(@NonNull DetalleOrden detalle) {
        return EntityModel.of(detalle,
                linkTo(methodOn(DetalleOrdenControllerV2.class)
                        .getDetalleById(detalle.getId())).withSelfRel(),
                linkTo(methodOn(DetalleOrdenControllerV2.class)
                        .getAllDetalles()).withRel("detalleordenes"));
    }
}
