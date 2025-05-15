package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class producto {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String genero;
}
