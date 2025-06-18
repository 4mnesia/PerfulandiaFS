package com.perfulandia.Perfulandia.Controller;

import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.controller.UsuarioController;
import com.perfulandia.Perfulandia.model.RolUsuario;
import com.perfulandia.Perfulandia.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("GET /users → lista de usuarios")
    void cuandoGetUsers_thenDevuelveLista() throws Exception {
        // Given
        Usuario u1 = Usuario.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .email("juan@example.com")
                .contraseña("password123")
                .direccion("Calle Falsa 123")
                .telefono("555-1234")
                .rol(RolUsuario.EMPLEADOVENTA)
                .build();

        Usuario u2 = Usuario.builder()
                .id(2L)
                .nombre("María Gómez")
                .email("maria@example.com")
                .contraseña("pass45678")
                .direccion("Avenida Siempre Viva 742")
                .telefono("555-5678")
                .rol(RolUsuario.LOGISTICA)
                .build();

        List<Usuario> todos = Arrays.asList(u1, u2);
        given(usuarioService.getAllUsuarios()).willReturn(todos);

        // When & Then
        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[1].email").value("maria@example.com"));
    }
}