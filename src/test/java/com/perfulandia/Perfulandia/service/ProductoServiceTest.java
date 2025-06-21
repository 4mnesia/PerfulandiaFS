package com.perfulandia.Perfulandia.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.perfulandia.Perfulandia.model.Producto;
import com.perfulandia.Perfulandia.repository.ProductoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Date;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = Producto.builder()
                .id(1L)
                .nombre("Test")
                .descripcion("Desc")
                .categoria("Cat")
                .marca("Marca")
                .modelo("Model")
                .precio(new BigDecimal("50.00"))
                .stock(5)
                .fechaCreacion(new Date())
                .build();
    }

    @Test
    void testGetAllProductos() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> lista = productoService.getAllProductos();
        assertEquals(1, lista.size());
        assertEquals("Test", lista.get(0).getNombre());
    }

    @Test
    void testGetProductoById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto p = productoService.getProductoById(1L);
        assertNotNull(p);
        assertEquals(1L, p.getId());
    }

    @Test
    void testSaveProducto() {
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto guardado = productoService.saveProducto(producto);
        assertEquals("Desc", guardado.getDescripcion());
    }

    @Test
    void testDeleteProducto() {
        doNothing().when(productoRepository).deleteById(1L);

        assertDoesNotThrow(() -> productoService.deleteProducto(1L));
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
