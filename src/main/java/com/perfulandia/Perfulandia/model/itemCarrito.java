package com.perfulandia.Perfulandia.model;

import com.fasterxml.jackson.annotation.*;  
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Schema(description = "Item individual dentro de un carrito, enlazado a un producto y con cantidad")
public class ItemCarrito {
    
    @Schema(description = "Identificador único del item", example = "10")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación unidireccional a Producto
    @Schema(description = "Producto referenciado por este item")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;

    @Schema(description = "Cantidad de unidades del producto en el carrito", example = "2")
    @Column(name = "cantidad")
    private Integer cantidad;

    @Schema(description = "Identificador del carrito al que pertenece este item", example = "5")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    @JsonIgnore   
    private Carrito carrito;
}
