package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Carrito;


@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
