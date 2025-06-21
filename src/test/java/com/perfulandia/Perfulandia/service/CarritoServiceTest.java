package com.perfulandia.Perfulandia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.repository.CarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @InjectMocks
    private CarritoService carritoService;

    @Mock
    private CarritoRepository carritoRepository;

    private Carrito carrito;

    @BeforeEach
    void setUp() {
        carrito = Carrito.builder()
                 .id(1L)
                 .estado(true)
                 .build();
    }

    @Test
    public void testGetCarritoByIdFound() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        Carrito result = carritoService.getCarritoById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetCarritoByIdNotFound() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(carritoService.getCarritoById(1L));
    }

    @Test
    public void testGetAllCarritos() {
        when(carritoRepository.findAll()).thenReturn(List.of(carrito));
        List<Carrito> all = carritoService.getAllCarritos();
        assertEquals(1, all.size());
    }

    @Test
    public void testSaveCarrito() {
        when(carritoRepository.save(carrito)).thenReturn(carrito);
        Carrito saved = carritoService.saveCarrito(carrito);
        assertEquals(carrito, saved);
    }

    @Test
    public void testDeleteCarritoExists() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        doNothing().when(carritoRepository).delete(carrito);

        assertDoesNotThrow(() -> carritoService.deleteCarrito(1L));
        verify(carritoRepository).delete(carrito);
    }

    @Test
    public void testDeleteCarritoNotFound() {
        when(carritoRepository.findById(1L))
            .thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
            EntityNotFoundException.class,
            () -> carritoService.deleteCarrito(1L)
        );
        assertTrue(ex.getMessage().contains("Carrito no encontrado"));
    }

    @Test
    public void testUpdateCarritoExists() {
        Carrito updated = Carrito.builder().id(1L).estado(false).build();
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(updated)).thenReturn(updated);

        Carrito result = carritoService.updateCarrito(updated);
        assertEquals(false, result.isEstado());
    }

    @Test
    public void testUpdateCarritoNotExists() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(carritoService.updateCarrito(carrito));
    }

    @Test
    public void testDeleteAllCarritos() {
        doNothing().when(carritoRepository).deleteAll();
        assertDoesNotThrow(() -> carritoService.deleteAllCarritos());
    }
}
