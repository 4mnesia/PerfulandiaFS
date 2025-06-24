package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unidireccional a DetalleOrden: crea FK orden_id en detalle_orden
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id")
    private List<DetalleOrden> detalles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private String direccionEnvio;
}
