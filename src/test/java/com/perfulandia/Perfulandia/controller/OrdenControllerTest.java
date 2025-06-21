// src/test/java/com/perfulandia/Perfulandia/controller/OrdenControllerTest.java
package com.perfulandia.Perfulandia.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.service.OrdenService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(OrdenController.class)  // Solo cargamos el controlador que queremos probar
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;           // Para simular peticiones HTTP

    @MockBean
    private OrdenService ordenService; // Mock del servicio

    @Autowired
    private ObjectMapper objectMapper; // Para serializar/deserializar JSON

    private Orden orden;

    @BeforeEach
    void setUp() {
        // Creamos una orden de ejemplo
        orden = Orden.builder()
                .id(1L)
                .detalles(List.of())  // lista vacía para simplificar
                .estado(EstadoOrden.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .direccionEnvio("Calle Falsa 123")
                .build();
    }

    @Test
    public void testGetAllOrdenes() throws Exception {
        // Cuando el servicio devuelva nuestra lista con la orden
        when(ordenService.getAllOrdenes()).thenReturn(List.of(orden));

        mockMvc.perform(get("/api/perfulandia/orden"))
                .andExpect(status().isOk())                                         // 200 OK
                .andExpect(jsonPath("$[0].id").value(1))                             // id = 1
                .andExpect(jsonPath("$[0].direccionEnvio").value("Calle Falsa 123")); // dirección
    }

    @Test
    public void testGetOrdenById() throws Exception {
        when(ordenService.getOrdenById(1L)).thenReturn(orden);

        mockMvc.perform(get("/api/perfulandia/orden/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    public void testCreateOrden() throws Exception {
        when(ordenService.saveOrden(any(Orden.class))).thenReturn(orden);

        mockMvc.perform(post("/api/perfulandia/orden")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccionEnvio").value("Calle Falsa 123"));
    }

    @Test
    public void testUpdateOrden() throws Exception {
        when(ordenService.updateOrden(eq(1L), any(Orden.class))).thenReturn(orden);

        mockMvc.perform(put("/api/perfulandia/orden/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    public void testDeleteOrden() throws Exception {
        doNothing().when(ordenService).deleteOrden(1L);

        mockMvc.perform(delete("/api/perfulandia/orden/1"))
                .andExpect(status().isOk());

        verify(ordenService, times(1)).deleteOrden(1L);
    }

    @Test
    public void testDeleteAllOrdenes() throws Exception {
        doNothing().when(ordenService).deleteAllOrdenes();

        mockMvc.perform(delete("/api/perfulandia/orden"))
                .andExpect(status().isOk());

        verify(ordenService, times(1)).deleteAllOrdenes();
    }
}
