package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private boolean estado;

}
