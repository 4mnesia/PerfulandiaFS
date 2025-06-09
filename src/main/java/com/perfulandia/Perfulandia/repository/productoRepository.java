
package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Producto;

import jakarta.transaction.Transactional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Transactional
    //eliminar todos los productos
    @Query("DELETE FROM Producto")
    void deleteAllProductos();
}
