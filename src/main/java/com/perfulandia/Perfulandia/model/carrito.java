package com.perfulandia.Perfulandia.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "Carrito de compra de un usuario, con sus items y detalles de orden")
@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Builder
public class Carrito {

    @Schema(description = "Identificador único del carrito", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación unidireccional a Usuario
    @Schema(description = "Usuario propietario del carrito")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    // Unidireccional a ItemCarrito: crea FK carrito_id en item_carrito
    @Schema(description = "Items añadidos en el carrito")
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ItemCarrito> item = new ArrayList<>();

    @Schema(description = "Estado del carrito (activo/inactivo)", example = "true")
    @Column(nullable = false)
    private boolean estado;
}
