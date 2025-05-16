package com.perfulandia.Perfulandia.model;

import lombok.*;
import org.springframework.data.annotation.Id;


import jakarta.persistence.*;
import java.sql.Date;


@Table(name = "orden")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private Integer total;

}
