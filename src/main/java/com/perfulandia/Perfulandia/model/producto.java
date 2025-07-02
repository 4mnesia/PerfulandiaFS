package com.perfulandia.Perfulandia.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Schema(description = "Producto disponible en catálogo")

public class Producto {
    @Schema(description = "Identificador único del producto", example = "101")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre comercial del producto", example = "Perfume Floral")
    @Column(name = "nombre")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Un perfume floral fresco y elegante")
    @Column(name = "descripcion")
    private String descripcion;

    @Schema(description = "Categoría del producto", example = "Perfumes")
    @Column(name = "categoria")
    private String categoria;

    @Schema(description = "Marca del producto", example = "Chanel")
    @Column(name = "marca")
    private String marca;

    @Schema(description = "Modelo del producto", example = "N°5")
    @Column(name = "modelo")
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

    @Schema(description = "Detalles del producto" + " (opcional)", 
    example = "Perfume de alta calidad con notas florales")
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<DetalleOrden> detalles = new ArrayList<>();

    @Schema(description = "Items en el carrito", example = "[{\"cantidad\": 2, \"precio\": 99.99}]")
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<ItemCarrito> itemsCarrito = new ArrayList<>();

}
