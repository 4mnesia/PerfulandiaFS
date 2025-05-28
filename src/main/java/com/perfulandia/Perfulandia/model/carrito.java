package com.perfulandia.Perfulandia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"usuario", "items"}) 
@EqualsAndHashCode(exclude = {"usuario", "items"})

public class carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<itemCarrito> items;

    

    @Column(nullable = false)
    private boolean estado;

}

