package com.perfulandia.Perfulandia.model;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Producto disponible en catálogo")

public class Producto {
    @Schema(description = "Identificador único del producto", example = "101")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre comercial del producto", example = "Perfume Floral")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Un perfume floral fresco y elegante")
    @Column(nullable = false)
    private String descripcion;

    @Schema(description = "Categoría del producto", example = "Perfumes")
    @Column(nullable = false)
    private String categoria;

    @Schema(description = "Marca del producto", example = "Chanel")
    @Column(nullable = false)
    private String marca;

    @Schema(description = "Modelo del producto", example = "N°5")
    @Column(nullable = false)
    private String modelo;

    @Schema(description = "Precio del producto", example = "99.99")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Schema(description = "Cantidad disponible en stock", example = "50")
    @Column(nullable = false)
    private Integer stock;

    @Schema(description = "Fecha de creación del producto", example = "2023-10-01T12:00:00Z")
    @Column(nullable = false)
    private Date fechaCreacion;

}
