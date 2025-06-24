package com.perfulandia.Perfulandia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
import com.perfulandia.Perfulandia.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(UsuarioController.class) // Prueba de UsuarioController
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;
    // Eliminado el uso de usuarioRepository porque no se puede usar con WebMvcTest

    private Long usuarioIdLimpio = 1L; // Valor dummy para pruebas de delete

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Usuario de ejemplo
        usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan Perez")
                .email("juan@example.com")
                .contraseña("password123")
                .direccion("Calle Falsa 123")
                .telefono("555-1234")
                .rol(RolUsuario.EMPLEADOVENTA)
                .build();
    }

    @Test
    public void testGetAllUsuarios() throws Exception {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/perfulandia/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("juan@example.com"));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/perfulandia/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    public void testCreateUsuario() throws Exception {
        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/perfulandia/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono").value("555-1234"));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);
        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/perfulandia/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rol").value("EMPLEADOVENTA"));
    }

    @Test
    void testDeleteUsuario() throws Exception {
        // Simula que el usuario existe
        when(usuarioService.getUsuarioById(usuarioIdLimpio)).thenReturn(usuario);
        doNothing().when(usuarioService).deleteUsuario(usuarioIdLimpio);

        mockMvc.perform(delete("/api/perfulandia/users/{id}", usuarioIdLimpio)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 204

        verify(usuarioService, times(1)).getUsuarioById(usuarioIdLimpio);
        verify(usuarioService, times(1)).deleteUsuario(usuarioIdLimpio);
    }

    @Test
    public void testCreateUsuariosBatch() throws Exception {
        Usuario usuario2 = Usuario.builder()
                .id(2L)
                .nombre("Maria Lopez")
                .email("maria@example.com")
                .contraseña("passhrh456757")
                .direccion("Calle Nueva 789")
                .telefono("555-5678")
                .rol(RolUsuario.LOGISTICA)
                .build();
        List<Usuario> batch = List.of(usuario, usuario2);
        when(usuarioService.saveUsuarios(batch)).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/users/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Maria Lopez"));

        verify(usuarioService, times(1)).saveUsuarios(batch);
    }
}
