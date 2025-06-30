package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.*;
import com.perfulandia.Perfulandia.model.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Component
public class DetalleOrdenModelAssembler implements RepresentationModelAssembler<DetalleOrden, EntityModel<DetalleOrden>> {
    @Override
    public EntityModel<DetalleOrden> toModel(DetalleOrden detalle) {
        return EntityModel.of(detalle,
            linkTo(methodOn(DetalleOrdenController.class)
                    .getDetalleById(detalle.getId()))
                .withSelfRel(),
            linkTo(methodOn(DetalleOrdenController.class)
                    .getAllDetalles())
                .withRel("detallesOrden")
        );
    }

    public CollectionModel<EntityModel<DetalleOrden>> toCollectionModel(List<DetalleOrden> list) {
        List<EntityModel<DetalleOrden>> items = list.stream()
            .map(this::toModel)
            .toList();
        return CollectionModel.of(
            items,
            linkTo(methodOn(DetalleOrdenController.class)
                    .getAllDetalles())
                .withSelfRel()
        );
    }
}
