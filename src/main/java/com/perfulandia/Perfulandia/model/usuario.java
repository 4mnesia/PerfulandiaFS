package com.perfulandia.Perfulandia.model;

import com.perfulandia.Perfulandia.model.Enums.RolUsuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contraseña", nullable = false)
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contraseña;

    @Column(name = "direccion", nullable = false)
    private String direccion;
    
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

}
