
package com.perfulandia.Perfulandia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perfulandia.Perfulandia.model.producto;

import jakarta.transaction.Transactional;


public interface productoRepository extends JpaRepository<producto, Long> {

    //crear 
    producto save(producto producto);

    //leer
    @Query("select p from producto p where p.nombre like %:nombre%")
    List<producto> findByNombre(String nombre);

    //leer categoria
    @Query("select p from producto p where p.categoria like %:categoria%")
    List<producto> findByCategoria(String categoria);

    //leer marca
    @Query("select p from producto p where p.marca like %:marca%")
    List<producto> findByMarca(String marca);
    
    //leer por precio entre
    @Query("select p from producto p where p.precio between :min and :max")
    List<producto> findByPrecioBetween(Integer min, Integer max);

    //actualizar
    @Transactional
    @Modifying
    @Query("update producto p set p.nombre = :nombre, p.descripcion = :descripcion, p.categoria = :categoria, p.marca = :marca, p.modelo = :modelo, p.precio = :precio, p.stock = :stock where p.id = :id")
    void actualizrProducto( @Param("id") Long id,
                            @Param("nombre") String nombre,
                            @Param("descripcion") String descripcion,
                            @Param("categoria") String categoria,
                            @Param("marca") String marca,
                            @Param("modelo") String modelo,
                            @Param("precio") Integer precio,
                            @Param("stock") Integer stock);

    @Transactional
    @Modifying
    @Query("UPDATE producto p SET p.stock = p.stock + :cantidad WHERE p.id = :id")
    void actualizarStock(@Param("id") Long id, @Param("cantidad") Integer cantidad);

    //eliminar  
    void deleteById(Long id);//Heredado
    
    @Transactional
    @Modifying
    @Query("DELETE FROM producto p WHERE p.marca = :marca")
    void deleteByMarca(@Param("marca") String marca);



}
