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

    /**
     * Obtiene todos los usuarios registrados en el sistema
     * @return Lista de todos los usuarios
     */
    public List<usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente
     * @param usuario Usuario a guardar
     * @return Usuario guardado con su ID asignado
     */
    public usuario saveUsuario(usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     */
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su email
     * @param email Email del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    /**
     * Verifica si existe un usuario con el email especificado
     * @param email Email a verificar
     * @return true si existe un usuario con ese email, false en caso contrario
     */
    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Obtiene todos los usuarios con rol de administrador
     * @return Lista de usuarios administradores
     */
    public List<usuario> findAllAdministradores() {
        return usuarioRepository.findAllAdministradores();
    }

    /**
     * Obtiene todos los usuarios con rol de cliente
     * @return Lista de usuarios clientes
     */
    public List<usuario> findAllClientes() {
        return usuarioRepository.findAllClientes();
    }

    /**
     * Actualiza los datos básicos de un usuario
     * @param id ID del usuario a actualizar
     * @param nombre Nuevo nombre del usuario
     * @param email Nuevo email del usuario
     */
    public void actualizarUsuario(Long id, String nombre, String email) {
        usuarioRepository.actualizarUsuario(id, nombre, email);
    }

    /**
     * Actualiza la contraseña de un usuario
     * @param id ID del usuario
     * @param password Nueva contraseña
     */
    public void actualizarPassword(Long id, String password) {
        usuarioRepository.actualizarPassword(id, password);
    }

    /**
     * Elimina un usuario por su email
     * @param email Email del usuario a eliminar
     */
    public void deleteByEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}

