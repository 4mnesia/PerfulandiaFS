package com.perfulandia.Perfulandia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.model.Producto;
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
        private MockMvc mockMvc;

        @MockBean
        private ItemCarritoService service;

        @Autowired
        private ObjectMapper objectMapper;

        private ItemCarrito item1;
        private ItemCarrito item2;
        private Producto productoEjemplo;

        @BeforeEach
        void setUp() {
                productoEjemplo = Producto.builder()
                                .id(100L)
                                .nombre("Perfume Test")
                                .descripcion("Aroma de prueba")
                                .categoria("Fragancia")
                                .marca("MarcaTest")
                                .modelo("ModeloX")
                                .precio(new java.math.BigDecimal("99.99"))
                                .build();
                item1 = ItemCarrito.builder()
                                .id(1L)
                                .cantidad(2)
                                .producto(productoEjemplo)
                                .build();
                item2 = ItemCarrito.builder()
                                .id(2L)
                                .cantidad(5)
                                .producto(productoEjemplo)
                                .build();
        }

        @Test
        void testGetAllItems() throws Exception {
                when(service.getAllItemCarritos()).thenReturn(List.of(item1, item2));

                mockMvc.perform(get("/api/perfulandia/itemcarrito"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[1].cantidad").value(5));

                verify(service, times(1)).getAllItemCarritos();
        }

        @Test
        void testGetItemById() throws Exception {
                when(service.getItemCarritoById(1L)).thenReturn(item1);

                mockMvc.perform(get("/api/perfulandia/itemcarrito/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.cantidad").value(2));

                verify(service, times(1)).getItemCarritoById(1L);
        }

        @Test
        void testCreateItem() throws Exception {
                when(service.saveItemCarrito(any(ItemCarrito.class))).thenReturn(item1);

                mockMvc.perform(post("/api/perfulandia/itemcarrito")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(item1)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.cantidad").value(2));

                verify(service, times(1)).saveItemCarrito(any(ItemCarrito.class));
        }

        @Test
        void testDeleteItem() throws Exception {
                doNothing().when(service).deleteItemCarrito(1L);

                mockMvc.perform(delete("/api/perfulandia/itemcarrito/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Item eliminado correctamente"));

                verify(service, times(1)).deleteItemCarrito(1L);
        }

        @Test
        void testCreateItemsBatch() throws Exception {
                // dado un par de items
                ItemCarrito item1 = ItemCarrito.builder()
                                .id(1L)
                                .cantidad(3)
                                .build();
                ItemCarrito item2 = ItemCarrito.builder()
                                .id(2L)
                                .cantidad(5)
                                .build();
                List<ItemCarrito> batch = List.of(item1, item2);

                // mockeamos el servicio
                when(service.saveAllItemCarritos(batch)).thenReturn(batch);

                // llamamos al endpoint
                mockMvc.perform(post("/api/perfulandia/itemcarrito/batch")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(batch)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].cantidad").value(3))
                                .andExpect(jsonPath("$[1].id").value(2))
                                .andExpect(jsonPath("$[1].cantidad").value(5));

                // verificamos que el servicio se llamó exactamente una vez
                verify(service, times(1)).saveAllItemCarritos(batch);
        }

        @Test
        void testUpdateItem() throws Exception {
                ItemCarrito updatedItem = ItemCarrito.builder()
                                .id(1L)
                                .cantidad(10)
                                .producto(productoEjemplo)
                                .build();

                when(service.getItemCarritoById(1L)).thenReturn(item1);
                when(service.saveItemCarrito(any(ItemCarrito.class))).thenReturn(updatedItem);

                mockMvc.perform(put("/api/perfulandia/itemcarrito/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedItem)))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Item actualizado correctamente"));

                verify(service, times(1)).getItemCarritoById(1L);
                verify(service, times(1)).saveItemCarrito(any(ItemCarrito.class));
        }
}
