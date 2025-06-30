package com.perfulandia.Perfulandia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(OrdenController.class) // Solo cargamos el controlador que queremos probar
public class OrdenControllerTest {

        @Autowired
        private MockMvc mockMvc; // Para simular peticiones HTTP

        @MockBean
        private OrdenService ordenService; // Mock del servicio

        @Autowired
        private ObjectMapper objectMapper; // Para serializar/deserializar JSON

        private Orden orden;

        @BeforeEach
        void setUp() {
                // Creamos una orden de ejemplo con todos los campos requeridos
                Producto producto = Producto.builder()
                                .id(1L)
                                .nombre("Perfume Test")
                                .descripcion("Aroma de prueba")
                                .precio(BigDecimal.valueOf(50.00))
                                .stock(10)
                                .fechaCreacion(java.util.Date.from(LocalDateTime.now()
                                                .atZone(java.time.ZoneId.systemDefault()).toInstant()))
                                .build();

                // 2) Creamos un detalle con ese producto
                DetalleOrden detalle = DetalleOrden.builder()
                                .producto(producto)
                                .cantidad(2)
                                .precioUnitario(producto.getPrecio())
                                .total(producto.getPrecio().multiply(BigDecimal.valueOf(2)))
                                .build();

                // 3) Ahora el orden con lista NO vacía
                orden = Orden.builder()
                                .id(1L)
                                .detalles(List.of(detalle)) // ¡aquí ya no está vacío!
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
                                .andExpect(status().isOk()) // 200 OK
                                .andExpect(jsonPath("$[0].id").value(1)) // id = 1
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
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Orden creada correctamente"));
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
                                .andExpect(status().isNoContent());

                verify(ordenService, times(1)).deleteOrden(1L);
        }

        @Test
        public void testDeleteAllOrdenes() throws Exception {
                doNothing().when(ordenService).deleteAllOrdenes();

                mockMvc.perform(delete("/api/perfulandia/orden"))
                                .andExpect(status().isOk());

                verify(ordenService, times(1)).deleteAllOrdenes();
        }

        @Test
        public void testCreateOrdenesBatch() throws Exception {
                // Creamos dos órdenes de ejemplo (dejar demás campos null está bien, el
                // controller no los valida)
                Orden o1 = new Orden();
                o1.setId(1L);
                Orden o2 = new Orden();
                o2.setId(2L);
                List<Orden> batch = List.of(o1, o2);

                // Stub: cuando llegue ANY lista de Órdenes, devuelve 'batch'
                when(ordenService.saveOrdenes(anyList())).thenReturn(batch);

                // POST a /api/perfulandia/orden/batch
                mockMvc.perform(post("/api/perfulandia/orden/batch")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(batch)))
                                .andExpect(status().isCreated()) // 201 CREATED
                                .andExpect(jsonPath("$", hasSize(2))) // Array de tamaño 2
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[1].id").value(2));

                // Verificamos que el servicio fue invocado exactamente una vez
                verify(ordenService, times(1)).saveOrdenes(anyList());
        }
}
