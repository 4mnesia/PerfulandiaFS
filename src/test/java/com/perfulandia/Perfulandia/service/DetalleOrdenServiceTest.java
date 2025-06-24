package com.perfulandia.Perfulandia.service;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.repository.DetalleOrdenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleOrdenServiceTest {

    @Mock
    private DetalleOrdenRepository repository;

    @InjectMocks
    private DetalleOrdenService service;

    private DetalleOrden detalle;

    @BeforeEach
    void setUp() {
        detalle = DetalleOrden.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(new BigDecimal("50.00"))
                .total(new BigDecimal("100.00"))
                .build();
    }

    @Test
    void testGetAllDetalles() {
        when(repository.findAll()).thenReturn(List.of(detalle));

        List<DetalleOrden> result = service.getAllDetalles();

        assertThat(result).containsExactly(detalle);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetDetalleOrdenByIdFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(detalle));

        DetalleOrden result = service.getDetalleOrdenById(1L);

        assertThat(result).isEqualTo(detalle);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetDetalleOrdenByIdNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        DetalleOrden result = service.getDetalleOrdenById(2L);

        assertThat(result).isNull();
        verify(repository, times(1)).findById(2L);
    }

    @Test
    void testSaveDetalleOrden() {
        when(repository.save(detalle)).thenReturn(detalle);

        DetalleOrden result = service.saveDetalleOrden(detalle);

        assertThat(result).isEqualTo(detalle);
        verify(repository, times(1)).save(detalle);
    }

    @Test
    void testSaveDetallesOrden() {
        DetalleOrden otro = DetalleOrden.builder()
                .id(2L)
                .cantidad(3)
                .precioUnitario(new BigDecimal("30.00"))
                .total(new BigDecimal("90.00"))
                .build();

        List<DetalleOrden> input = List.of(detalle, otro);
        when(repository.saveAll(input)).thenReturn(input);

        List<DetalleOrden> result = service.saveDetallesOrden(input);

        assertThat(result).containsExactlyElementsOf(input);
        verify(repository, times(1)).saveAll(input);
    }

    @Test
    void testDeleteDetalleOrden() {
        doNothing().when(repository).deleteById(1L);

        service.deleteDetalleOrden(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
