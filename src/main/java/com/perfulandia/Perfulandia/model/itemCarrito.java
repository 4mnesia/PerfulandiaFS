
package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class itemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private carrito carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private producto producto;

    @Column(nullable = false)
    private Integer cantidad;

}
