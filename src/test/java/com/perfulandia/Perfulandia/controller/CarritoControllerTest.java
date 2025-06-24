package com.perfulandia.Perfulandia.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarritoService carritoService;
    @Autowired
    private ObjectMapper objectMapper;
    private Carrito carrito;

    @BeforeEach
    void setUp(){
        carrito = Carrito.builder()
                 .id(1L)
                 // completa con los campos mínimos necesarios, p.ej. estado, usuario, lista vacía de ítems...
                 .estado(true)
                 .build();
    }
    // Test para obtener todos los carritos
    @Test
    public void testGetAllCarritos() throws Exception {
        when(carritoService.getAllCarritos()).thenReturn(List.of(carrito));

        mockMvc.perform(get("/api/perfulandia/carrito"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1));
        
        verify(carritoService, times(1)).getAllCarritos();
    }
    // Test para obtener un carrito por ID
    @Test
    public void testGetCarritoById() throws Exception {
        when(carritoService.getCarritoById(1L)).thenReturn(carrito);

        mockMvc.perform(get("/api/perfulandia/carrito/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).getCarritoById(1L);
    }
    // Test para crear un carrito
    @Test
    public void testCreateCarrito() throws Exception {
        when(carritoService.saveCarrito(any(Carrito.class))).thenReturn(carrito);

        mockMvc.perform(post("/api/perfulandia/carrito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carrito)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).saveCarrito(any(Carrito.class));
    }
    
    // Test para modificar un carrito
    @Test
    public void testUpdateCarrito() throws Exception {
        when(carritoService.getCarritoById(1L)).thenReturn(carrito);
        when(carritoService.updateCarrito(any(Carrito.class))).thenReturn(carrito);

        mockMvc.perform(put("/api/perfulandia/carrito/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carrito)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).getCarritoById(1L);
        verify(carritoService, times(1)).updateCarrito(any(Carrito.class));
    }
    //test para eliminar todos los carritos
    @Test
    public void testDeleteCarrito() throws Exception {
        doNothing().when(carritoService).deleteCarrito(1L);

        mockMvc.perform(delete("/api/perfulandia/carrito/1"))
               .andExpect(status().isOk());

        verify(carritoService, times(1)).deleteCarrito(1L);
    }
    //test para crear varios carritos
    @Test
    public void testCreateCarritosBatch() throws Exception {
        List<Carrito> carritos = List.of(carrito);
        when(carritoService.saveCarritos(anyList())).thenReturn(carritos);

        mockMvc.perform(post("/api/perfulandia/carrito/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carritos)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1));

        verify(carritoService, times(1)).saveCarritos(anyList());
    }
}
