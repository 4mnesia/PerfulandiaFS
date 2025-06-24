package com.perfulandia.Perfulandia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(DetalleOrdenController.class)
public class DetalleOrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleOrdenService service;

    @Autowired
    private ObjectMapper objectMapper;

    private DetalleOrden detalle;

    @BeforeEach
    void setUp() {
        detalle = DetalleOrden.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("50.00"))
                .total(new BigDecimal("100.00"))
                .build();
    }

    @Test
    void testGetAllDetalles() throws Exception {
        when(service.getAllDetalles()).thenReturn(List.of(detalle));

        mockMvc.perform(get("/api/perfulandia/detalleorden"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2));

        verify(service, times(1)).getAllDetalles();
    }

    @Test
    void testGetDetalleById() throws Exception {
        when(service.getDetalleOrdenById(1L)).thenReturn(detalle);

        mockMvc.perform(get("/api/perfulandia/detalleorden/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));

        verify(service, times(1)).getDetalleOrdenById(1L);
    }

    @Test
    void testCreateDetalle() throws Exception {
        when(service.saveDetalleOrden(any(DetalleOrden.class))).thenReturn(detalle);

        mockMvc.perform(post("/api/perfulandia/detalleorden")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));

        verify(service, times(1)).saveDetalleOrden(any(DetalleOrden.class));
    }

    @Test
    void testCreateDetallesBatch() throws Exception {
        DetalleOrden d2 = DetalleOrden.builder()
                .id(2L)
                .cantidad(3)
                .precioUnitario(new BigDecimal("30.00"))
                .total(new BigDecimal("90.00"))
                .build();
        List<DetalleOrden> batch = List.of(detalle, d2);

        when(service.saveDetallesOrden(batch)).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/detalleorden/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].cantidad").value(3));

        verify(service, times(1)).saveDetallesOrden(batch);
    }

    @Test
    void testUpdateDetalle() throws Exception {
        when(service.saveDetalleOrden(any(DetalleOrden.class))).thenReturn(detalle);

        mockMvc.perform(put("/api/perfulandia/detalleorden/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));

        verify(service, times(1)).saveDetalleOrden(any(DetalleOrden.class));
    }

    @Test
    void testDeleteDetalle() throws Exception {
        doNothing().when(service).deleteDetalleOrden(1L);

        mockMvc.perform(delete("/api/perfulandia/detalleorden/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteDetalleOrden(1L);
    }
}
