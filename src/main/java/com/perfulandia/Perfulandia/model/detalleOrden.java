package com.perfulandia.Perfulandia.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;  

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = true)
    private Carrito carrito;
    
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


//oAuth sirve para autenticar y autorizar a los usuarios en aplicaciones web y móviles, permitiendo el acceso seguro a recursos protegidos sin necesidad de compartir credenciales directamente. Es un estándar abierto que facilita la delegación de permisos entre aplicaciones y servicios, mejorando la seguridad y la experiencia del usuario al evitar el manejo directo de contraseñas.