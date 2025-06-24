package com.perfulandia.Perfulandia.model;

import java.math.BigDecimal;

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

    // Relación unidireccional a Producto
    @Schema(description = "Producto al que corresponde este detalle")
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Schema(description = "Cantidad pedida de este producto", example = "3")
    @Column(nullable = false)
    private Integer cantidad;

    @Schema(description = "Precio unitario al momento de la orden", example = "19.99")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Schema(description = "Total de la línea de detalle", example = "59.97")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Transient
        @Schema(description = "Subtotal calculado dinámicamente", example = "59.97")
    public BigDecimal getSubtotal() {
        if (this.precioUnitario == null || this.cantidad == null) {
            return BigDecimal.ZERO;
        }
        return this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
}
