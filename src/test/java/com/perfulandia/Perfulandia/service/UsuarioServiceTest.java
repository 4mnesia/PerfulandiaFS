package com.perfulandia.Perfulandia.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
import com.perfulandia.Perfulandia.repository.UsuarioRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder()
                .id(1L)
                .nombre("Ana")
                .email("ana@test.com")
                .contraseña("secret123")
                .direccion("Dir")
                .telefono("123")
                .rol(RolUsuario.LOGISTICA)
                .build();
    }

    @Test
    void testGetAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> lista = usuarioService.getAllUsuarios();
        assertEquals(1, lista.size());
    }

    @Test
    void testGetUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario u = usuarioService.getUsuarioById(1L);
        assertEquals("Ana", u.getNombre());
    }

    @Test
    void testSaveUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.saveUsuario(usuario);
        assertEquals("secret123", guardado.getContraseña());
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        assertDoesNotThrow(() -> usuarioService.deleteUsuario(1L));
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSaveUsuariosBatch() {
        Usuario usuario2 = Usuario.builder()
                .id(2L)
                .nombre("Luis")
                .email("luis@test.com")
                .contraseña("clave4567")
                .direccion("Otra")
                .telefono("456")
                .rol(RolUsuario.EMPLEADOVENTA)
                .build();
        List<Usuario> batch = List.of(usuario, usuario2);
        when(usuarioRepository.saveAll(batch)).thenReturn(batch);

        List<Usuario> guardados = usuarioService.saveUsuarios(batch);
        assertEquals(2, guardados.size());
        assertEquals("Luis", guardados.get(1).getNombre());
    }

    @Test
    void testDeleteUsuarioPorId() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.deleteUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
