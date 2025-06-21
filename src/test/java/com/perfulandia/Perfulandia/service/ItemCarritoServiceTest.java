// src/test/java/com/perfulandia/perfulandia/service/ItemCarritoServiceTest.java
package com.perfulandia.Perfulandia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.repository.ItemCarritoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemCarritoServiceTest {

    @Mock
    private ItemCarritoRepository repo;            // Mock del repositorio

    @InjectMocks
    private ItemCarritoService service;            // Servicio con el repo inyectado

    private ItemCarrito item;

    @BeforeEach
    void setUp() {
        // Objeto de prueba
        item = ItemCarrito.builder()
                .id(1L)
                .cantidad(2)
                .build();
    }

    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(List.of(item));

        var result = service.getAllItemCarritos();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    void testGetById() {
        when(repo.findById(1L)).thenReturn(Optional.of(item));

        var result = service.getItemCarritoById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repo).findById(1L);
    }

    @Test
    void testSave() {
        when(repo.save(any(ItemCarrito.class))).thenReturn(item);

        var saved = service.saveItemCarrito(item);
        assertNotNull(saved);
        assertEquals(item.getId(), saved.getId());
        verify(repo).save(item);
    }

    @Test
    void testDelete() {
        doNothing().when(repo).deleteById(1L);

        service.deleteItemCarrito(1L);
        verify(repo).deleteById(1L);
    }
}
