// src/test/java/com/perfulandia/Perfulandia/service/DetalleOrdenServiceTest.java
package com.perfulandia.Perfulandia.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.repository.DetalleOrdenRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Pruebas unitarias de DetalleOrdenService usando Mockito.
 * Se prueba la lógica de negocio y utilitarios (subtotal/total).
 */
@ExtendWith(MockitoExtension.class)  // :contentReference[oaicite:2]{index=2}
public class DetalleOrdenServiceTest {

    @Mock
    private DetalleOrdenRepository detalleOrdenRepository;  // Repositorio simulado

    @InjectMocks
    private DetalleOrdenService detalleOrdenService;       // Servicio bajo prueba

    private DetalleOrden detalleOrden;

    @BeforeEach
    void setUp() {
        // Creamos un DetalleOrden de ejemplo
        detalleOrden = DetalleOrden.builder()
                .id(1L)
                .cantidad(3)
                .precioUnitario(BigDecimal.valueOf(10))
                .total(BigDecimal.valueOf(30))
                .producto(null)
                .carrito(null)
                .build();
    }

    @Test
    void testGetDetalleOrdenById() {
        when(detalleOrdenRepository.findById(1L))
                .thenReturn(Optional.of(detalleOrden));

        DetalleOrden result = detalleOrdenService.getDetalleOrdenById(1L);
        assertEquals(detalleOrden, result);
    }

    @Test
    void testGetAllDetalles() {
        when(detalleOrdenRepository.findAll())
                .thenReturn(List.of(detalleOrden));

        List<DetalleOrden> list = detalleOrdenService.getAllDetalles();
        assertEquals(1, list.size());
        assertEquals(detalleOrden, list.get(0));
    }

    @Test
    void testSaveDetalleOrden() {
        when(detalleOrdenRepository.save(detalleOrden))
                .thenReturn(detalleOrden);

        DetalleOrden saved = detalleOrdenService.saveDetalleOrden(detalleOrden);
        assertEquals(detalleOrden, saved);
    }

    @Test
    void testUpdateDetalleOrden() {
        when(detalleOrdenRepository.save(detalleOrden))
                .thenReturn(detalleOrden);

        DetalleOrden updated = detalleOrdenService.updateDetalleOrden(detalleOrden);
        assertEquals(detalleOrden, updated);
    }

    @Test
    void testDeleteDetalleOrden() {
        doNothing().when(detalleOrdenRepository).deleteById(1L);

        detalleOrdenService.deleteDetalleOrden(1L);
        verify(detalleOrdenRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAllDetalles() {
        doNothing().when(detalleOrdenRepository).deleteAll();

        detalleOrdenService.deleteAllDetalles();
        verify(detalleOrdenRepository, times(1)).deleteAll();
    }

    @Test
    void testCalcularSubtotal() {
        // Subtotal = precioUnitario * cantidad
        BigDecimal expected = BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(3));
        BigDecimal subtotal = DetalleOrdenService.calcularSubtotal(detalleOrden);
        assertEquals(expected, subtotal);
    }

    @Test
    void testCalcularTotal() {
        DetalleOrden otro = DetalleOrden.builder()
                .cantidad(2)
                .precioUnitario(BigDecimal.valueOf(5))
                .build();

        // Total = subtotal(detalleOrden) + subtotal(otro)
        BigDecimal expected = DetalleOrdenService.calcularSubtotal(detalleOrden)
                .add(DetalleOrdenService.calcularSubtotal(otro));

        BigDecimal total = detalleOrdenService.calcularTotal(List.of(detalleOrden, otro));
        assertEquals(expected, total);
    }
}
