package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.CarritoController;
import com.perfulandia.Perfulandia.model.Carrito;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                // Enlace self: GET /api/perfulandia/carrito/{id}
                linkTo(methodOn(CarritoController.class)
                        .getCarritoById(carrito.getId()))
                        .withSelfRel(),
                // Enlace a la colección: GET /api/perfulandia/carrito
                linkTo(methodOn(CarritoController.class)
                        .getAllCarritos())
                        .withRel("carritos"));
    }

    //colección de carritos en un CollectionModel
    public CollectionModel<EntityModel<Carrito>> toCollectionModel(List<Carrito> carritos) {
        List<EntityModel<Carrito>> items = carritos.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                items,
                linkTo(methodOn(CarritoController.class)
                        .getAllCarritos())
                        .withSelfRel());
    }
}