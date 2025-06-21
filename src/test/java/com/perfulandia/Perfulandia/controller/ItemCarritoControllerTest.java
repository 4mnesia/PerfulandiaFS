package com.perfulandia.Perfulandia.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.service.ItemCarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@WebMvcTest(ItemCarritoController.class) 
public class ItemCarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;              // Para simular peticiones HTTP

    @MockBean
    private ItemCarritoService service;    // Mock del servicio

    @Autowired
    private ObjectMapper objectMapper;     // Para convertir objetos a JSON

    private ItemCarrito item;

    @BeforeEach
    void setUp() {
        // Creamos un ItemCarrito de ejemplo
        item = ItemCarrito.builder()
                .id(1L)
                .cantidad(2)
                // Puedes añadir aquí un Producto simulado si tu controlador lo serializa
                .build();
    }

    @Test
    void testGetAllItems() throws Exception {
        // Arranque: el servicio devuelve una lista con nuestro ejemplo
        when(service.getAllItemCarritos()).thenReturn(List.of(item));

        // Ejecución y verificación de código HTTP y JSON
        mockMvc.perform(get("/api/perfulandia/itemcarrito"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2));
    }

    @Test
    void testGetItemById() throws Exception {
        when(service.getItemCarritoById(1L)).thenReturn(item);

        mockMvc.perform(get("/api/perfulandia/itemcarrito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void testCreateItem() throws Exception {
        when(service.saveItemCarrito(any(ItemCarrito.class))).thenReturn(item);

        mockMvc.perform(post("/api/perfulandia/itemcarrito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void testUpdateItem() throws Exception {
        when(service.saveItemCarrito(any(ItemCarrito.class))).thenReturn(item);

        mockMvc.perform(put("/api/perfulandia/itemcarrito/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void testDeleteItem() throws Exception {
        // No esperamos retorno, solo que no falle
        doNothing().when(service).deleteItemCarrito(1L);

        mockMvc.perform(delete("/api/perfulandia/itemcarrito/1"))
                .andExpect(status().isOk());

        // Verifica que el servicio fue invocado correctamente
        verify(service, times(1)).deleteItemCarrito(1L);
    }
}
