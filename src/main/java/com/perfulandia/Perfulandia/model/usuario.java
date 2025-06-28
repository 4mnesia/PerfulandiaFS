package com.perfulandia.Perfulandia.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Usuario registrado en el sistema")

public class Usuario {
    @Schema(description = "Identificador único del usuario", example = "2")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre completo", example = "Juan Pérez")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    @Column(name = "email", nullable = false)
    private String email;

    @Schema(description = "Contraseña (mínimo 8 caracteres)")
    @Column(name = "contraseña", nullable = false)
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contraseña;

    @Schema(description = "Estado de verificación del correo electrónico", example = "false")
    @Column(name = "direccion")
    private String direccion;

    @Schema(description = "Número de teléfono del usuario", example = "+1234567890")
    @Column(name = "telefono")
    private String telefono;

    @Schema(description = "Rol asignado al usuario", example = "EMPLEADOVENTA")
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    // Un Usuario tiene 1 Carrito
    @Schema(description = "Carrito asociado al usuario")
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Carrito carrito;

    // Un Usuario puede tener muchas Órdenes
    @Schema(description = "Órdenes asociadas al usuario")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Orden> ordenes = new ArrayList<>();
}
