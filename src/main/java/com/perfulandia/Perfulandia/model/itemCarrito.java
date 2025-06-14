
package com.perfulandia.Perfulandia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    // <-- Ignoramos todas las propiedades de Producto excepto "id" y "nombre"
    @JsonIgnoreProperties({
            "descripcion", "categoria", "marca", "modelo",
            "precio", "stock", "fechaCreacion"
    })
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

}
