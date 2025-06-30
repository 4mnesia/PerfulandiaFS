package com.perfulandia.Perfulandia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.Perfulandia.assemblers.DetalleOrdenModelAssembler;
import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DetalleOrdenController.class)
@Import(DetalleOrdenModelAssembler.class)
class DetalleOrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DetalleOrdenService service;

    @Nested
    @DisplayName("GET /detalleorden endpoints")
    class GetEndpoints {
        @Test
        @DisplayName("GET all detalles returns HATEOAS collection")
        void testGetAllDetalles() throws Exception {
            DetalleOrden d = new DetalleOrden(); d.setId(1L);
            when(service.getAllDetalles()).thenReturn(List.of(d));

            mockMvc.perform(get("/api/perfulandia/detalleorden"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.detalleOrdenList").isArray())
                .andExpect(jsonPath("$._embedded.detalleOrdenList[0].id").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        @DisplayName("GET detalle by id returns HATEOAS entity when exists")
        void testGetDetalleByIdExists() throws Exception {
            DetalleOrden d = new DetalleOrden(); d.setId(2L);
            when(service.getDetalleOrdenById(2L)).thenReturn(d);

            mockMvc.perform(get("/api/perfulandia/detalleorden/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        @DisplayName("GET detalle by id returns 404 when missing")
        void testGetDetalleByIdNotFound() throws Exception {
            when(service.getDetalleOrdenById(99L)).thenReturn(null);

            mockMvc.perform(get("/api/perfulandia/detalleorden/99"))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /detalleorden endpoints")
    class PostEndpoints {
        @Test
        @DisplayName("POST createDetalle returns 200 and body when valid")
        void testCreateDetalleValid() throws Exception {
            DetalleOrden input = new DetalleOrden();
            DetalleOrden saved = new DetalleOrden(); saved.setId(3L);
            when(service.saveDetalleOrden(any(DetalleOrden.class))).thenReturn(saved);

            mockMvc.perform(post("/api/perfulandia/detalleorden")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
        }

        @Test
        @DisplayName("POST createDetalle returns 400 when body null")
        void testCreateDetalleNull() throws Exception {
            mockMvc.perform(post("/api/perfulandia/detalleorden")
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("POST batch returns 400 on empty list")
        void testCreateDetallesBatchEmpty() throws Exception {
            mockMvc.perform(post("/api/perfulandia/detalleorden/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of())))
                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("POST batch returns 200 and list when valid")
        void testCreateDetallesBatchValid() throws Exception {
            DetalleOrden d1 = new DetalleOrden(); d1.setId(4L);
            DetalleOrden d2 = new DetalleOrden(); d2.setId(5L);
            List<DetalleOrden> list = List.of(d1, d2);
            when(service.saveDetallesOrden(eq(list))).thenReturn(list);

            mockMvc.perform(post("/api/perfulandia/detalleorden/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(5));
        }
    }

    @Nested
    @DisplayName("PUT /detalleorden/{id}")
    class PutEndpoint {
        @Test
        @DisplayName("PUT updateDetalle returns 200 and updated entity")
        void testUpdateDetalleValid() throws Exception {
            DetalleOrden input = new DetalleOrden(); input.setId(6L);
            when(service.saveDetalleOrden(any(DetalleOrden.class))).thenReturn(input);

            mockMvc.perform(put("/api/perfulandia/detalleorden/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6));
        }
    }

    @Nested
    @DisplayName("DELETE endpoints")
    class DeleteEndpoints {
        @Test
        @DisplayName("DELETE detalle returns 200 and message when valid")
        void testDeleteDetalle() throws Exception {
            doNothing().when(service).deleteDetalleOrden(7L);

            mockMvc.perform(delete("/api/perfulandia/detalleorden/7"))
                .andExpect(status().isOk())
                .andExpect(content().string("Detalle eliminado correctamente"));
        }

        @Test
        @DisplayName("DELETE all returns 204")
        void testDeleteAllDetalles() throws Exception {
            doNothing().when(service).deleteAllDetalles();

            mockMvc.perform(delete("/api/perfulandia/detalleorden"))
                .andExpect(status().isNoContent());
        }
    }
}
