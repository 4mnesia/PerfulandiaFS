/*package com.perfulandia.Perfulandia;

import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.repository.*;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Builder

public class DataLoader implements CommandLineRunner {
    private final UsuarioRepository usuarioRepo;
    private final ProductoRepository productoRepo;
    private final CarritoRepository carritoRepo;
    private final ItemCarritoRepository itemCarritoRepo;
    private final OrdenRepository ordenRepo;
    private final DetalleOrdenRepository detalleOrdenRepo;

    @Override
    public void run(String... args) throws Exception {

        detalleOrdenRepo.deleteAll();
        ordenRepo.deleteAll();
        itemCarritoRepo.deleteAll();
        carritoRepo.deleteAll();
        productoRepo.deleteAll();
        usuarioRepo.deleteAll();

        // USUARIOS

        Usuario user1 = Usuario.builder()
                .nombre("Flavio")
                .email("flavio@gmail.com")
                .contraseña("password123")
                .direccion("Calle Falsa 123")
                .telefono("123456789")
                .rol(RolUsuario.ADMINSYSTEM)
                .build();
        usuarioRepo.save(user1);


        // PRODUCTOS

        Producto prod1 = Producto.builder()
                .nombre("Chanel No. 5")
                .descripcion("Descripción del perfume 1")
                .categoria("Fragancia")
                .marca("RoseArt")
                .modelo("MOD0001")
                .precio(new BigDecimal("23983.48"))
                .stock(42)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod1);

        

        // CARRITOS

        Carrito cart1 = Carrito.builder()
                .usuario(user1)
                .estado(true)
                .build();
        carritoRepo.save(cart1);
        itemCarritoRepo.save(ItemCarrito.builder().carrito(cart1).producto(prod1).cantidad(2).build());

        // ITEMS

        ItemCarrito item1 = ItemCarrito.builder()
                .carrito(cart1)
                .producto(prod1)
                .cantidad(1)
                .build();
        itemCarritoRepo.save(item1);

        // ORDENES

        Orden orden1 = Orden.builder()
                .usuario(user1)
                .carrito(cart1)
                .estado(EstadoOrden.PENDIENTE)
                .fechaCreacion(new Date())
                .fechaActualizacion(new Date())
                .direccionEnvio(user1.getDireccion())
                .build();
        ordenRepo.save(orden1);

        // DETALLES ORDEN

        DetalleOrden d1 = DetalleOrden.builder()
                .producto(prod1)
                .cantidad(1)
                .precioUnitario(prod1.getPrecio())
                .total(prod1.getPrecio().multiply(BigDecimal.valueOf(1))) // total = precioUnitario * cantidad
                .carrito(cart1)
                .build();
        detalleOrdenRepo.save(d1);
    }
}
*/