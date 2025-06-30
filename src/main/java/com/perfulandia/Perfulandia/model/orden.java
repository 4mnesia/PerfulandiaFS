package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


import com.fasterxml.jackson.annotation.*;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Schema(description = "Orden de compra, con sus detalles, estado y fechas")
public class Orden {

    // Identificador único de la orden
    @Schema(description = "Identificador de la orden", example = "500")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Fecha de creación automática")
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Schema(description = "Fecha de última modificación automática")
    @Builder.Default
    private LocalDateTime fechaActualizacion = null;

    @Schema(description = "Dirección de envío de la orden", example = "Av. Siempre Viva 742")
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    // DetalleOrden
    @Schema(description = "Líneas de detalle de la orden")
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<DetalleOrden> detalles;

    // estadoOrden
    @Schema(description = "Estado actual de la orden", example = "PENDIENTE")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_orden")
    private EstadoOrden estado;

    //Usuario
    @Schema(description = "Usuario que realizó la orden")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
}
