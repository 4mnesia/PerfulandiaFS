package com.perfulandia.Perfulandia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    // leer usuario por id
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    // leer todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    // guardar un usuario
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    // eliminar un usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    //eliminar todos los usuarios
    public void deleteAllUsuarios() {
        usuarioRepository.deleteAll();
    }
    // actualizar un usuario
    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}
