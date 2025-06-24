package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación unidireccional a Producto
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;
}
