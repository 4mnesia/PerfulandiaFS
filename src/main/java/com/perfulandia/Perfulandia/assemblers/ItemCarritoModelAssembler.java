package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.*;
import com.perfulandia.Perfulandia.model.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Component
public class ItemCarritoModelAssembler implements RepresentationModelAssembler<ItemCarrito, EntityModel<ItemCarrito>> {
    @Override
    public EntityModel<ItemCarrito> toModel(ItemCarrito item) {
        return EntityModel.of(item,
            linkTo(methodOn(ItemCarritoController.class)
                    .getItemById(item.getId()))
                .withSelfRel(),
            linkTo(methodOn(ItemCarritoController.class)
                    .getAllItems())
                .withRel("itemsCarrito")
        );
    }

    public CollectionModel<EntityModel<ItemCarrito>> toCollectionModel(List<ItemCarrito> list) {
        List<EntityModel<ItemCarrito>> items = list.stream()
            .map(this::toModel)
            .toList();
        return CollectionModel.of(
            items,
            linkTo(methodOn(ItemCarritoController.class)
                    .getAllItems())
                .withSelfRel()
        );
    }
}