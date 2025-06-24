package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Orden de compra, con sus detalles, estado y fechas")
public class Orden {
    @Schema(description = "Identificador de la orden", example = "500")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unidireccional a DetalleOrden: crea FK orden_id en detalle_orden
    @Schema(description = "Líneas de detalle de la orden")    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id")
    private List<DetalleOrden> detalles;

    @Schema(description = "Estado actual de la orden", example = "PENDIENTE")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado;

    @Schema(description = "Fecha de creación automática")
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última modificación automática")
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Dirección de envío de la orden", example = "Av. Siempre Viva 742")
    @Column(nullable = false)
    private String direccionEnvio;
}
