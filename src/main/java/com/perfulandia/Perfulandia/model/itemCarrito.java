package com.perfulandia.Perfulandia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Item individual dentro de un carrito, enlazado a un producto y con cantidad")
public class ItemCarrito {
    @Schema(description = "Identificador único del item", example = "10")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación unidireccional a Producto
    @Schema(description = "Producto referenciado por este item")
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Schema(description = "Cantidad de unidades del producto en el carrito", example = "2")
    @Column(nullable = false)
    private Integer cantidad;
}
