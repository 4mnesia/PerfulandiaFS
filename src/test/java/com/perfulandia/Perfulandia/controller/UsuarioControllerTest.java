package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.UsuarioModelAssembler;
import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(UsuarioController.class)
@Import(UsuarioModelAssembler.class)
public class UsuarioControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UsuarioService usuarioService;

    private Usuario u1;
    private Usuario u2;

    @BeforeEach
    void setUp() {
        u1 = new Usuario();
        u1.setId(1L);
        u1.setNombre("Alice");
        u1.setEmail("alice@example.com");

        u2 = new Usuario();
        u2.setId(2L);
        u2.setNombre("Bob");
        u2.setEmail("bob@example.com");
    }

    @Test
    void testGetUsuarioById_found() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(u1);

        mockMvc.perform(get("/api/perfulandia/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Alice"))
            .andExpect(jsonPath("$._links.self.href").exists());

        verify(usuarioService, times(1)).getUsuarioById(1L);
    }

    @Test
    void testGetUsuarioById_notFound() throws Exception {
        when(usuarioService.getUsuarioById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/perfulandia/users/99"))
            .andExpect(status().isNotFound());

        verify(usuarioService, times(1)).getUsuarioById(99L);
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/perfulandia/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.usuarioList", hasSize(2)))
            .andExpect(jsonPath("$._embedded.usuarioList[0].id").value(1))
            .andExpect(jsonPath("$._embedded.usuarioList[1].id").value(2))
            .andExpect(jsonPath("$._links.self.href").exists());

        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    void testSaveUsuario() throws Exception {
        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(u1);

        mockMvc.perform(post("/api/perfulandia/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(u1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Alice"));

        verify(usuarioService, times(1)).saveUsuario(any(Usuario.class));
    }

    @Test
    void testSaveUsuariosBatch() throws Exception {
        List<Usuario> batch = List.of(u1, u2);
        when(usuarioService.saveUsuarios(anyList())).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/users/batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(batch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2));

        verify(usuarioService, times(1)).saveUsuarios(anyList());
    }

    @Test
    void testDeleteAllUsuarios() throws Exception {
        doNothing().when(usuarioService).deleteAllUsuarios();

        mockMvc.perform(delete("/api/perfulandia/users"))
            .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deleteAllUsuarios();
    }

    @Test
    void testDeleteUsuario_success() throws Exception {
        doNothing().when(usuarioService).deleteUsuario(1L);

        mockMvc.perform(delete("/api/perfulandia/users/1"))
            .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deleteUsuario(1L);
    }

    @Test
    void testDeleteUsuario_failure() throws Exception {
        doThrow(new RuntimeException()).when(usuarioService).deleteUsuario(1L);

        mockMvc.perform(delete("/api/perfulandia/users/1"))
            .andExpect(status().isBadRequest());

        verify(usuarioService, times(1)).deleteUsuario(1L);
    }

    @Test
    void testUpdateUsuario() throws Exception {
        Usuario updated = new Usuario();
        updated.setId(1L);
        updated.setNombre("Alice Updated");
        updated.setEmail("alice.updated@example.com");
        when(usuarioService.updateUsuario(any(Usuario.class))).thenReturn(updated);

        mockMvc.perform(put("/api/perfulandia/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Alice Updated"));

        verify(usuarioService, times(1)).updateUsuario(any(Usuario.class));
    }
}
