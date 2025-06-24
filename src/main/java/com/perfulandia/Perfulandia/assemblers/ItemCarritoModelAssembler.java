package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.ItemCarritoControllerV2;
import com.perfulandia.Perfulandia.model.ItemCarrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ItemCarritoModelAssembler
        implements RepresentationModelAssembler<ItemCarrito, EntityModel<ItemCarrito>> {
    @Override
    @NonNull
    public EntityModel<ItemCarrito> toModel(@NonNull ItemCarrito item) {
        return EntityModel.of(item,
            linkTo(methodOn(ItemCarritoControllerV2.class)
                     .getItemCarritoById(item.getId())).withSelfRel(),
            linkTo(methodOn(ItemCarritoControllerV2.class)
                     .getAllItemCarritos()).withRel("itemcarritos")
        );
    }
}

