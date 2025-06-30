package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.ProductoModelAssembler;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(ProductoController.class)
@Import(ProductoModelAssembler.class)
public class ProductoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ProductoService productoService;

    private Producto p1;

    @BeforeEach
    void setup() {
        p1 = Producto.builder()
                .id(1L)
                .nombre("Test Perfume")
                .descripcion("Descripción")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .build();
    }

    @Test
    void testGetAllProductos_noContent() throws Exception {
        when(productoService.getAllProductos()).thenReturn(List.of());

        mockMvc.perform(get("/api/perfulandia/productos"))
            .andExpect(status().isNoContent())
            .andExpect(content().string("No hay productos disponibles."));

        verify(productoService, times(1)).getAllProductos();
    }

    @Test
    void testGetAllProductos_withContent() throws Exception {
        when(productoService.getAllProductos()).thenReturn(List.of(p1));

        mockMvc.perform(get("/api/perfulandia/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.productoList", hasSize(1)))
            .andExpect(jsonPath("$._embedded.productoList[0].id").value(1))
            .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testGetProductoById_found() throws Exception {
        when(productoService.getProductoById(1L)).thenReturn(p1);

        mockMvc.perform(get("/api/perfulandia/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testGetProductoById_notFound() throws Exception {
        when(productoService.getProductoById(42L)).thenReturn(null);

        mockMvc.perform(get("/api/perfulandia/productos/42"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProducto_success() throws Exception {
        when(productoService.saveProducto(any(Producto.class))).thenReturn(p1);

        mockMvc.perform(post("/api/perfulandia/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(p1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreateProducto_badRequest() throws Exception {
        when(productoService.saveProducto(any(Producto.class))).thenReturn(null);

        mockMvc.perform(post("/api/perfulandia/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(p1)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProductosBatch_empty() throws Exception {
        mockMvc.perform(post("/api/perfulandia/productos/batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(List.of())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProductosBatch_success() throws Exception {
        List<Producto> batch = List.of(p1);
        when(productoService.saveAllProductos(anyList())).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/productos/batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(batch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testDeleteProducto() throws Exception {
        doNothing().when(productoService).deleteProducto(1L);

        mockMvc.perform(delete("/api/perfulandia/productos/1"))
            .andExpect(status().isOk());

        verify(productoService, times(1)).deleteProducto(1L);
    }

    @Test
    void testDeleteAllProductos_success() throws Exception {
        doNothing().when(productoService).deleteAllProductos();

        mockMvc.perform(delete("/api/perfulandia/productos"))
            .andExpect(status().isOk())
            .andExpect(content().string("All products deleted successfully"));
    }

    @Test
    void testDeleteAllProductos_integrityViolation() throws Exception {
        doThrow(new DataIntegrityViolationException("in use")).when(productoService).deleteAllProductos();

        mockMvc.perform(delete("/api/perfulandia/productos"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No se pueden eliminar los productos porque están en uso."));
    }

    @Test
    void testDeleteAllProductos_exception() throws Exception {
        doThrow(new RuntimeException("fail")).when(productoService).deleteAllProductos();

        mockMvc.perform(delete("/api/perfulandia/productos"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Error al eliminar los productos: fail"));
    }

    @Test
    void testUpdateProducto() throws Exception {
        Producto updated = Producto.builder().id(1L).nombre("Updated").build();
        when(productoService.saveProducto(any(Producto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/perfulandia/productos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Updated"));
    }
}
