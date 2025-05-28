package com.perfulandia.Perfulandia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@ToString(exclude = { "carrito", "ordenes" })
@EqualsAndHashCode(exclude = { "carrito", "ordenes" })
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private carrito carrito;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<orden> ordenes;

}
