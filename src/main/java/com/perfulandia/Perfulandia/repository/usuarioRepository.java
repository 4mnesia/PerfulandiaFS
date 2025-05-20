package com.perfulandia.Perfulandia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.perfulandia.Perfulandia.model.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {

}
