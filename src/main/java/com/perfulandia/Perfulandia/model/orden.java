package com.perfulandia.Perfulandia.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;


@Table(name = "orden")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private Integer total;

}
