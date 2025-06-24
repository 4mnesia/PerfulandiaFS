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

    // Relación unidireccional a Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Unidireccional a ItemCarrito: crea FK carrito_id en item_carrito
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrito_id")
    @Builder.Default
    private List<ItemCarrito> item = new ArrayList<>();

    // Unidireccional a DetalleOrden: crea FK carrito_id en detalle_orden
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrito_id")
    @Builder.Default
    private List<DetalleOrden> detalles = new ArrayList<>();

    @Column(nullable = false)
    private boolean estado;
}
