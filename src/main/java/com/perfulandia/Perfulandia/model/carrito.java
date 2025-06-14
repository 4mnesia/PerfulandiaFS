package com.perfulandia.Perfulandia.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrito_id")
    // Relaciona los items con el carrito
    //el cascade es para que al eliminar el carrito, se eliminen los items asociados
    //el orphanRemoval es para que al eliminar un item del carrito, se elimine de la base de datos
    //builder.default es para que al crear un carrito, se cree una lista vacía de items
    @Builder.Default
    private List<ItemCarrito> item = new ArrayList<>();

    @Column(nullable = false)
    private boolean estado;

}

