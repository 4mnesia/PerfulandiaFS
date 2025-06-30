package com.perfulandia.Perfulandia.assemblers;

import com.perfulandia.Perfulandia.controller.UsuarioController;
import com.perfulandia.Perfulandia.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler
                implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
                // enlace self → GET /api/perfulandia/users/{id}
                Link self = linkTo(methodOn(UsuarioController.class)
                        .getUsuarioById(usuario.getId()))
                        .withSelfRel();
                // enlace colección → GET /api/perfulandia/users
                Link all = linkTo(methodOn(UsuarioController.class)
                        .getAllUsuarios())
                        .withRel("usuarios");
        return EntityModel.of(usuario,self,all);
    }

        //Ensambla un CollectionModel de Usuario
        @Override
        public CollectionModel<EntityModel<Usuario>> toCollectionModel(
                        Iterable<? extends Usuario> usuarios) {

                List<EntityModel<Usuario>> items = StreamSupport.stream(usuarios.spliterator(), false)
                                .map(this::toModel)
                                .toList();

                return CollectionModel.of(
                                items,
                                // self link de la colección
                                linkTo(methodOn(UsuarioController.class)
                                                .getAllUsuarios())
                                                .withSelfRel());
        }
}
