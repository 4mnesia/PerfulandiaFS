// src/test/java/com/perfulandia/Perfulandia/service/OrdenServiceTest.java
package com.perfulandia.Perfulandia.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.repository.OrdenRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Inicializa Mockito
public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenService ordenService; // Servicio con el repo mockeado

    private Orden orden;

    @BeforeEach
    void setUp() {
        orden = Orden.builder()
                .id(1L)
                .detalles(List.of())
                .estado(EstadoOrden.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .direccionEnvio("Av. Siempre Viva 742")
                .build();
    }

    @Test
    public void testGetOrdenByIdFound() {
        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        Orden resultado = ordenService.getOrdenById(1L);
        assertEquals(orden, resultado);
    }

    @Test
    public void testGetOrdenByIdNotFound() {
        when(ordenRepository.findById(1L)).thenReturn(Optional.empty());

        Orden resultado = ordenService.getOrdenById(1L);
        assertNull(resultado);
    }

    @Test
    public void testGetAllOrdenes() {
        when(ordenRepository.findAll()).thenReturn(List.of(orden));

        List<Orden> lista = ordenService.getAllOrdenes();
        assertEquals(1, lista.size());
        assertEquals(orden, lista.get(0));
    }

    @Test
    public void testSaveOrden() {
        when(ordenRepository.save(orden)).thenReturn(orden);

        Orden guardada = ordenService.saveOrden(orden);
        assertEquals(orden, guardada);
    }

    @Test
    public void testUpdateOrdenExists() {
        when(ordenRepository.existsById(1L)).thenReturn(true);
        when(ordenRepository.save(any(Orden.class))).thenReturn(orden);

        Orden actualizada = ordenService.updateOrden(1L, orden);
        assertNotNull(actualizada);
        assertEquals(1L, actualizada.getId());
    }

    @Test
    public void testUpdateOrdenNotExists() {
        when(ordenRepository.existsById(1L)).thenReturn(false);

        Orden actualizada = ordenService.updateOrden(1L, orden);
        assertNull(actualizada);
    }

    @Test
    public void testDeleteOrden() {
        doNothing().when(ordenRepository).deleteById(1L);

        assertDoesNotThrow(() -> ordenService.deleteOrden(1L));
        verify(ordenRepository).deleteById(1L);
    }

    @Test
    public void testDeleteAllOrdenes() {
        doNothing().when(ordenRepository).deleteAll();

        assertDoesNotThrow(() -> ordenService.deleteAllOrdenes());
        verify(ordenRepository).deleteAll();
    }
}
