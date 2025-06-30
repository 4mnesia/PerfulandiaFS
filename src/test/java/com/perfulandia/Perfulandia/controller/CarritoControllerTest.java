package com.perfulandia.Perfulandia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.Perfulandia.assemblers.CarritoModelAssembler;
import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.service.CarritoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoController.class)
@Import(CarritoModelAssembler.class)
public class CarritoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarritoService carritoService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCarritos() throws Exception {
        Carrito c1 = new Carrito(); c1.setId(1L);
        Carrito c2 = new Carrito(); c2.setId(2L);
        when(carritoService.getAllCarritos()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/perfulandia/carrito"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.carritoList", hasSize(2)))
            .andExpect(jsonPath("$._embedded.carritoList[0].id").value(1))
            .andExpect(jsonPath("$._embedded.carritoList[1].id").value(2))
            .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testGetCarritoById() throws Exception {
        Carrito c = new Carrito(); c.setId(1L);
        when(carritoService.getCarritoById(1L)).thenReturn(c);

        mockMvc.perform(get("/api/perfulandia/carrito/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$._links.self.href").exists())
            .andExpect(jsonPath("$._links.carritos.href").exists());
    }

    @Test
    void testCreateCarrito() throws Exception {
        Carrito input = new Carrito();
        Carrito saved = new Carrito(); saved.setId(1L);
        when(carritoService.saveCarrito(any(Carrito.class))).thenReturn(saved);

        mockMvc.perform(post("/api/perfulandia/carrito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreateCarritosBatch() throws Exception {
        Carrito c1 = new Carrito(); c1.setId(1L);
        Carrito c2 = new Carrito(); c2.setId(2L);
        List<Carrito> batch = List.of(c1, c2);
        when(carritoService.saveCarritos(anyList())).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/carrito/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testUpdateCarrito() throws Exception {
        Carrito existing = new Carrito(); existing.setId(1L);
        Carrito updated = new Carrito(); updated.setId(1L);
        when(carritoService.getCarritoById(1L)).thenReturn(existing);
        when(carritoService.updateCarrito(any(Carrito.class))).thenReturn(updated);

        mockMvc.perform(put("/api/perfulandia/carrito/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteCarrito() throws Exception {
        doNothing().when(carritoService).deleteCarrito(1L);

        mockMvc.perform(delete("/api/perfulandia/carrito/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllCarritos() throws Exception {
        doNothing().when(carritoService).deleteAllCarritos();

        mockMvc.perform(delete("/api/perfulandia/carrito"))
            .andExpect(status().isNoContent());
    }
}
