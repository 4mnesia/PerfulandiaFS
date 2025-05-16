package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "carrito_detalle")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor



public class carritoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer carritoId;

    @Column(nullable = false)
    private Integer productoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precio;

}
