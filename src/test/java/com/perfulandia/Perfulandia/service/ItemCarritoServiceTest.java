package com.perfulandia.Perfulandia.service;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.repository.ItemCarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCarritoServiceTest {

    @Mock
    private ItemCarritoRepository repository;

    @InjectMocks
    private ItemCarritoService service;

    private ItemCarrito item1;
    private ItemCarrito item2;

    @BeforeEach
    void setUp() {
        item1 = ItemCarrito.builder()
                .id(1L)
                .cantidad(2)
                .build();
        item2 = ItemCarrito.builder()
                .id(2L)
                .cantidad(5)
                .build();
    }

    @Test
    void testGetAllItemCarritos() {
        when(repository.findAll()).thenReturn(List.of(item1, item2));

        List<ItemCarrito> result = service.getAllItemCarritos();

        assertThat(result).containsExactly(item1, item2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetItemCarritoById() {
        when(repository.findById(1L)).thenReturn(Optional.of(item1));

        ItemCarrito result = service.getItemCarritoById(1L);

        assertThat(result).isEqualTo(item1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSaveItemCarrito() {
        when(repository.save(item1)).thenReturn(item1);

        ItemCarrito result = service.saveItemCarrito(item1);

        assertThat(result).isEqualTo(item1);
        verify(repository, times(1)).save(item1);
    }

    @Test
    void testSaveAllItemCarritos() {
        List<ItemCarrito> batch = List.of(item1, item2);
        when(repository.saveAll(batch)).thenReturn(batch);

        List<ItemCarrito> result = service.saveAllItemCarritos(batch);

        assertThat(result).containsExactly(item1, item2);
        verify(repository, times(1)).saveAll(batch);
    }


    @Test
    void testDeleteItemCarrito() {
        doNothing().when(repository).deleteById(1L);

        service.deleteItemCarrito(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateItemCarrito() {
        // Preparamos un item existente
        ItemCarrito existing = ItemCarrito.builder()
                .id(1L)
                .cantidad(2)
                .build();

        // El item modificado que queremos guardar
        ItemCarrito modified = ItemCarrito.builder()
                .id(1L)
                .cantidad(10)
                .build();

        // Stub: al buscar por ID devolvemos el existente
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        // Stub: al guardar devolvemos el objeto que se le pase
        when(repository.save(any(ItemCarrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llamada al método real que toma (item)
        ItemCarrito result = service.updateItemCarrito(modified);

        // Verificaciones
        verify(repository, times(1)).save(any(ItemCarrito.class));
        assertThat(result.getCantidad()).isEqualTo(10);
    }
}
