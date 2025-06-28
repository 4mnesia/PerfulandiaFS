package com.perfulandia.Perfulandia.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detalle de línea de una orden, enlazado a un producto con precios y totales")
public class DetalleOrden {

    @Schema(description = "Identificador único del detalle", example = "100")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Cantidad pedida de este producto", example = "3")
    @Column(nullable = false)
    private Integer cantidad;

    @Schema(description = "Precio unitario al momento de la orden", example = "19.99")
    @Column(precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Schema(description = "Total de la línea de detalle", example = "59.97")
    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    // Relación bidireccional a Orden
    @Schema(description = "Orden a la que pertenece este detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id")
    @JsonIgnore
    private Orden orden;
    // Relación unidireccional a Producto
    @Schema(description = "Producto al que corresponde este detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
