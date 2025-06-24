
package com.perfulandia.Perfulandia.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Filtrar por categoría, marca o modelo
    List<Producto> findByCategoria(String categoria);

    List<Producto> findByMarca(String marca);

    List<Producto> findByModeloContaining(String fragmento);

    // Rango de precios
    List<Producto> findByPrecioBetween(BigDecimal min, BigDecimal max);

    // Productos con stock menor que X
    List<Producto> findByStockLessThan(int nivel);

    // Paginación y ordenamiento
    Page<Producto> findAll(Pageable pageable);
}
