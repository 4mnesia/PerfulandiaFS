package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.Perfulandia.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

}
