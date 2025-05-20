
package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.producto;

@Repository
public interface productoRepository extends JpaRepository<producto, Long> {

}
