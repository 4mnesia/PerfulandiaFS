package com.perfulandia.Perfulandia.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.*;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Filtra productos por categoría exacta
    List<Producto> findByCategoria(String categoria);
    // Busca productos cuyo nombre contenga la cadena, sin distinguir mayúsculas
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    // Filtra productos cuyo precio esté en un rango
    List<Producto> findByPrecioBetween(BigDecimal min, BigDecimal max);
    // Ejemplo de consulta @Query: productos con stock mayor a un valor
    @Query("SELECT p FROM Producto p WHERE p.stock > ?1")
    List<Producto> findByStockGreaterThan(int stock);
    // Ejemplo de consulta @Query: productos por marca
    @Query("SELECT p FROM Producto p WHERE p.marca = ?1")
    List<Producto> findByMarca(String marca);
}