package com.perfulandia.Perfulandia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import com.perfulandia.Perfulandia.model.producto;
@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private producto producto;
    private Integer cantidad;




}
