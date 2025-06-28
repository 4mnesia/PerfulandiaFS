package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.*;
import com.perfulandia.Perfulandia.model.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetalleOrdenModelAssembler implements RepresentationModelAssembler<DetalleOrden, EntityModel<DetalleOrden>> {
    @Override
    public EntityModel<DetalleOrden> toModel(DetalleOrden detalle) {
        return EntityModel.of(detalle,
                linkTo(methodOn(DetalleOrdenController.class).getDetalleById(detalle.getId())).withSelfRel(),
                // Ajusta el nombre del método según exista en tu DetalleOrdenController: getAllDetalleOrdenes() o getAllDetallesOrden()
                linkTo(methodOn(DetalleOrdenController.class).getAllDetalles()).withRel("detallesOrden")
        );
    }
}