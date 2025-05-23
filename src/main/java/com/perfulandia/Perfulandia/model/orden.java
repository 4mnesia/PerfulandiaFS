package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private usuario usuario;

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private carrito carrito;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<detalleOrden> detalles;
    
    @Enumerated(EnumType.STRING)
    private estadoOrden estado;
    
    @Column(nullable = false)
    private Date fechaCreacion;
    
    @Column(nullable = false)
    private Date fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
    fechaCreacion = new Date();
    fechaActualizacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    fechaActualizacion = new Date();    
    }
    
    @Column(nullable = false)
    private String direccionEnvio;

    @Column(nullable = false)
    private BigDecimal total;
    
}
