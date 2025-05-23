package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.itemCarrito;

@Repository
public interface itemCarritoRepository extends JpaRepository<itemCarrito, Long> {
}
