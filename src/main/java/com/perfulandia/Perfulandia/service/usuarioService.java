package com.perfulandia.Perfulandia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.usuario;
import com.perfulandia.Perfulandia.repository.usuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class usuarioService {
    @Autowired
    private usuarioRepository usuarioRepository;

    // leer usuario por id
    public usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // leer todos los usuarios
    public List<usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // guardar un usuario
    public usuario saveUsuario(usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // eliminar un usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // actualizar un usuario
    public usuario updateUsuario(usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}
