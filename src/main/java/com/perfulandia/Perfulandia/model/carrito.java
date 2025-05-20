package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<itemCarrito> Item;

<<<<<<< HEAD
    @Column(nullable = false)
    private boolean estado;
    
=======
>>>>>>> 727d080d268454922c4720bba29a9c64347ec713
}
