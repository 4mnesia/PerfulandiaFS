package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private usuario usuario;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<detalleOrden> detalles;
    
    @Column(nullable = false)
    private String estado;
    
    @Column(nullable = false)
    private Date fechaCreacion;
    
    @Column(nullable = false)
    private Date fechaActualizacion;
    
    @Column(nullable = false)
    private String direccionEnvio;

    @Column(nullable = false)
    private Integer total;
    
}
