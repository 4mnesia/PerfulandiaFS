package com.perfulandia.Perfulandia.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class detalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private orden orden;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;

    @ManyToOne//* */
    @JoinColumn(name = "carrito_id", nullable = true)
    @JsonIgnore
    private carrito carrito;
    

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Transient
    public BigDecimal getSubtotal() {
        if (this.precioUnitario == null || this.cantidad == null) {
            return BigDecimal.ZERO;
        }
        return this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }

}
