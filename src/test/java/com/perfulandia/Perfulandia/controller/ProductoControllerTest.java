package com.perfulandia.Perfulandia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@WebMvcTest(ProductoController.class) // Indica que se prueba ProductoController
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permite simular peticiones HTTP

    @MockBean
    private ProductoService productoService; // Servicio mockeado

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        // Creamos un producto de ejemplo
        producto1 = Producto.builder()
                .id(1L)
                .nombre("Test Producto")
                .descripcion("Descripción de prueba")
                .categoria("Categoría")
                .marca("Marca")
                .modelo("Modelo")
                .precio(new BigDecimal("123.45"))
                .stock(10)
                .fechaCreacion(new Date())
                .build();

        producto2 = Producto.builder()
                .id(2L)
                .nombre("Prod A")
                .descripcion("Descripción de prueba 2")
                .categoria("Categoría 2")
                .marca("Marca 2")
                .modelo("Modelo 2")
                .precio(new BigDecimal("99.99"))
                .stock(5)
                .fechaCreacion(new Date())
                .build();
    }

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.getAllProductos()).thenReturn(List.of(producto1));

        mockMvc.perform(get("/api/perfulandia/productos"))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test Producto"));
    }

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.getProductoById(1L)).thenReturn(producto1);

        mockMvc.perform(get("/api/perfulandia/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.precio").value(123.45));
    }

    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.saveProducto(any(Producto.class))).thenReturn(producto1);

        mockMvc.perform(post("/api/perfulandia/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto1)))
                .andExpect(status().isCreated()) // Código 201 en creación
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        when(productoService.getProductoById(1L)).thenReturn(producto1);
        when(productoService.saveProducto(any(Producto.class))).thenReturn(producto1);

        mockMvc.perform(put("/api/perfulandia/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marca").value("Marca"));
    }

    @Test
    public void testDeleteProducto() throws Exception {
        doNothing().when(productoService).deleteProducto(1L);

        mockMvc.perform(delete("/api/perfulandia/productos/1"))
                .andExpect(status().isOk());

        verify(productoService, times(1)).deleteProducto(1L);
    }

    @Test
    public void testCreateProductosBatch() throws Exception {
        List<Producto> batch = List.of(producto1, producto2);
        when(productoService.saveAllProductos(batch)).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/productos/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].nombre").value("Prod A"));

        verify(productoService, times(1)).saveAllProductos(batch);
    }
}
