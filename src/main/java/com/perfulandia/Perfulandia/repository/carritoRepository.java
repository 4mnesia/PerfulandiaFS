package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.carrito;

@Repository
public interface carritoRepository extends JpaRepository<carrito, Long> {
}
