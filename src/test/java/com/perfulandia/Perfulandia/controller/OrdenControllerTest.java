package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.assemblers.OrdenModelAssembler;
import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.service.OrdenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenController.class)
@Import(OrdenModelAssembler.class)
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    @Autowired
    private ObjectMapper objectMapper;

    private Orden orden;

    @BeforeEach
    void setUp() {
        // Producto de ejemplo
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Perfume Test")
                .descripcion("Aroma de prueba")
                .precio(new BigDecimal("50.00"))
                .stock(10)
                .fechaCreacion(new Date())
                .build();
        // DetalleOrden con producto
        DetalleOrden detalle = DetalleOrden.builder()
                .producto(producto)
                .cantidad(2)
                .precioUnitario(producto.getPrecio())
                .total(producto.getPrecio().multiply(BigDecimal.valueOf(2)))
                .build();
        // Orden completa
        orden = Orden.builder()
                .id(1L)
                .detalles(List.of(detalle))
                .estado(EstadoOrden.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .direccionEnvio("Calle Falsa 123")
                .build();
    }

    @Test
    void testGetAllOrdenes() throws Exception {
        when(ordenService.getAllOrdenes()).thenReturn(List.of(orden));

        mockMvc.perform(get("/api/perfulandia/orden"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ordenList", hasSize(1)))
                .andExpect(jsonPath("$._embedded.ordenList[0].direccionEnvio").value("Calle Falsa 123"));

        verify(ordenService, times(1)).getAllOrdenes();
    }

    @Test
    void testGetOrdenByIdExists() throws Exception {
        when(ordenService.getOrdenById(1L)).thenReturn(orden);

        mockMvc.perform(get("/api/perfulandia/orden/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        verify(ordenService, times(1)).getOrdenById(1L);
    }

    @Test
    void testGetOrdenByIdNotFound() throws Exception {
        when(ordenService.getOrdenById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/perfulandia/orden/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveOrdenValid() throws Exception {
        // saveOrden returns void, so just doNothing
        doNothing().when(ordenService).saveOrden(any(Orden.class));

        mockMvc.perform(post("/api/perfulandia/orden")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Orden creada correctamente"));

        verify(ordenService, times(1)).saveOrden(any(Orden.class));
    }

    @Test
    void testSaveOrdenInvalidNoDetalles() throws Exception {
        Orden bad = Orden.builder().detalles(List.of()).build();
        mockMvc.perform(post("/api/perfulandia/orden")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveOrdenesBatch() throws Exception {
        Orden o1 = new Orden(); o1.setId(2L);
        Orden o2 = new Orden(); o2.setId(3L);
        List<Orden> batch = List.of(o1, o2);
        when(ordenService.saveOrdenes(anyList())).thenReturn(batch);

        mockMvc.perform(post("/api/perfulandia/orden/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batch)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[1].id").value(3));

        verify(ordenService, times(1)).saveOrdenes(anyList());
    }

    @Test
    void testUpdateOrdenExists() throws Exception {
        when(ordenService.updateOrden(eq(1L), any(Orden.class))).thenReturn(orden);

        mockMvc.perform(put("/api/perfulandia/orden/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        verify(ordenService, times(1)).updateOrden(eq(1L), any(Orden.class));
    }

    @Test
    void testUpdateOrdenNotFound() throws Exception {
        when(ordenService.updateOrden(eq(1L), any(Orden.class))).thenReturn(null);

        mockMvc.perform(put("/api/perfulandia/orden/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOrden() throws Exception {
        doNothing().when(ordenService).deleteOrden(1L);

        mockMvc.perform(delete("/api/perfulandia/orden/1"))
                .andExpect(status().isNoContent());

        verify(ordenService, times(1)).deleteOrden(1L);
    }

    @Test
    void testDeleteAllOrdenes() throws Exception {
        doNothing().when(ordenService).deleteAllOrdenes();

        mockMvc.perform(delete("/api/perfulandia/orden"))
                .andExpect(status().isOk());

        verify(ordenService, times(1)).deleteAllOrdenes();
    }
}