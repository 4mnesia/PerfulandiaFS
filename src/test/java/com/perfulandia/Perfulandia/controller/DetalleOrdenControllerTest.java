package com.perfulandia.Perfulandia.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.math.BigDecimal;
import java.util.List;
import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Pruebas de integración ligeras para DetalleOrdenController.
 * Usa MockMvc para simular peticiones HTTP y un @MockBean del servicio.
 */
@WebMvcTest(DetalleOrdenController.class)  // :contentReference[oaicite:0]{index=0}
public class DetalleOrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;             // Para enviar peticiones HTTP simuladas

    @MockBean
    private DetalleOrdenService detalleOrdenService;  // Mock del servicio :contentReference[oaicite:1]{index=1}

    @Autowired
    private ObjectMapper objectMapper;   // Para serializar/deserializar JSON

    private DetalleOrden detalleOrden;

    @BeforeEach
    void setUp() {
        // Creamos un DetalleOrden de ejemplo
        detalleOrden = DetalleOrden.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("15.50"))
                .total(new BigDecimal("31.00"))
                .producto(null)
                .carrito(null)
                .build();
    }

    @Test
    public void testGetAllDetalleOrden() throws Exception {
        // Configuramos el mock para devolver una lista con nuestro detalle
        when(detalleOrdenService.getAllDetalles()).thenReturn(List.of(detalleOrden));

        mockMvc.perform(get("/api/perfulandia/detalleOrden"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].precioUnitario").value(15.50))
                .andExpect(jsonPath("$[0].total").value(31.00));
    }

    @Test
    public void testGetDetalleOrdenById() throws Exception {
        when(detalleOrdenService.getDetalleOrdenById(1L)).thenReturn(detalleOrden);

        mockMvc.perform(get("/api/perfulandia/detalleOrden/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    public void testCreateDetalleOrden() throws Exception {
        when(detalleOrdenService.saveDetalleOrden(any(DetalleOrden.class)))
                .thenReturn(detalleOrden);

        mockMvc.perform(post("/api/perfulandia/detalleOrden")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleOrden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    public void testUpdateDetalleOrden() throws Exception {
        when(detalleOrdenService.getDetalleOrdenById(1L)).thenReturn(detalleOrden);
        when(detalleOrdenService.updateDetalleOrden(any(DetalleOrden.class)))
                .thenReturn(detalleOrden);

        mockMvc.perform(put("/api/perfulandia/detalleOrden/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleOrden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    public void testDeleteDetalleOrden() throws Exception {
        doNothing().when(detalleOrdenService).deleteDetalleOrden(1L);

        mockMvc.perform(delete("/api/perfulandia/detalleOrden/1"))
                .andExpect(status().isOk());

        verify(detalleOrdenService, times(1)).deleteDetalleOrden(1L);
    }
}
