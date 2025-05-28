package com.perfulandia.Perfulandia.config;

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

public class DataSeeder implements CommandLineRunner {
    private final usuarioRepository usuarioRepo;
    private final productoRepository productoRepo;
    private final carritoRepository carritoRepo;
    private final itemCarritoRepository itemCarritoRepo;
    private final ordenRepository ordenRepo;
    private final detalleOrdenRepository detalleOrdenRepo;

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

        Usuario user2 = Usuario.builder()
                .nombre("Stephano")
                .email("Stephano@perfulandia.com")
                .contraseña("password123")
                .direccion("Calle Arturo prat")
                .telefono("333333333")
                .rol(RolUsuario.GERENTESUCURSAL)
                .build();
        usuarioRepo.save(user2);

        Usuario user3 = Usuario.builder()
                .nombre("Francisco")
                .email("pancholoco@perfulandia.com")
                .contraseña("password123")
                .direccion("Calle las calilas")
                .telefono("999999999")
                .rol(RolUsuario.EMPLEADOVENTA)
                .build();
        usuarioRepo.save(user3);

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

        Producto prod2 = Producto.builder()
                .nombre("La Vie Est Belle")
                .descripcion("Descripción del perfume 2")
                .categoria("Fragancia")
                .marca("NoirLine")
                .modelo("MOD0002")
                .precio(new BigDecimal("11345.71"))
                .stock(46)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod2);

        Producto prod3 = Producto.builder()
                .nombre("Good Girl")
                .descripcion("Descripción del perfume 3")
                .categoria("Fragancia")
                .marca("CitrusFresh")
                .modelo("MOD0003")
                .precio(new BigDecimal("34144.34"))
                .stock(11)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod3);

        Producto prod4 = Producto.builder()
                .nombre("J'adore")
                .descripcion("Descripción del perfume 4")
                .categoria("Fragancia")
                .marca("ForestLeaf")
                .modelo("MOD0004")
                .precio(new BigDecimal("12632.69"))
                .stock(31)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod4);

        Producto prod5 = Producto.builder()
                .nombre("Black Opium")
                .descripcion("Descripción del perfume 5")
                .categoria("Fragancia")
                .marca("AmberCo")
                .modelo("MOD0005")
                .precio(new BigDecimal("46169.36"))
                .stock(25)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod5);

        Producto prod6 = Producto.builder()
                .nombre("Black Opium")
                .descripcion("Descripción del perfume 6")
                .categoria("Fragancia")
                .marca("VanillaEssence")
                .modelo("MOD0006")
                .precio(new BigDecimal("37503.64"))
                .stock(10)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod6);

        Producto prod7 = Producto.builder()
                .nombre("Acqua di Gio Profumo")
                .descripcion("Descripción del perfume 7")
                .categoria("Fragancia")
                .marca("SpiceCraft")
                .modelo("MOD0007")
                .precio(new BigDecimal("14933.96"))
                .stock(16)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod7);

        Producto prod8 = Producto.builder()
                .nombre("Dior Sauvage ")
                .descripcion("Descripción del perfume 8")
                .categoria("Fragancia")
                .marca("MintCool")
                .modelo("MOD0008")
                .precio(new BigDecimal("22724.81"))
                .stock(43)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod8);

        Producto prod9 = Producto.builder()
                .nombre("1 Million")
                .descripcion("Descripción del perfume 9")
                .categoria("Fragancia")
                .marca("LavenderLux")
                .modelo("MOD0009")
                .precio(new BigDecimal("12595.8"))
                .stock(44)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod9);

        Producto prod10 = Producto.builder()
                .nombre("Le Male")
                .descripcion("Descripción del perfume 10")
                .categoria("Fragancia")
                .marca("BlueWave")
                .modelo("MOD0010")
                .precio(new BigDecimal("40317.4"))
                .stock(15)
                .fechaCreacion(new Date())
                .build();
        productoRepo.save(prod10);

        // CARRITOS

        carrito cart1 = carrito.builder()
                .usuario(user1)
                .estado(true)
                .build();
        carritoRepo.save(cart1);
        itemCarritoRepo.save(itemCarrito.builder().carrito(cart1).producto(prod3).cantidad(2).build());

        carrito cart2 = carrito.builder()
                .usuario(user2)
                .estado(true)
                .build();
        carritoRepo.save(cart2);
        itemCarritoRepo.save(itemCarrito.builder().carrito(cart2).producto(prod2).cantidad(5).build());

        carrito cart3 = carrito.builder()
                .usuario(user3)
                .estado(true)
                .build();
        carritoRepo.save(cart3);
        itemCarritoRepo.save(itemCarrito.builder().carrito(cart3).producto(prod1).cantidad(1).build());

        // ITEMS

        itemCarrito item1 = itemCarrito.builder()
                .carrito(cart1)
                .producto(prod1)
                .cantidad(1)
                .build();
        itemCarritoRepo.save(item1);

        itemCarrito item2 = itemCarrito.builder()
                .carrito(cart2)
                .producto(prod2)
                .cantidad(2)
                .build();
        itemCarritoRepo.save(item2);

        // ORDENES

        orden orden1 = orden.builder()
                .usuario(user1)
                .carrito(cart1)
                .estado(estadoOrden.PENDIENTE)
                .fechaCreacion(new Date())
                .fechaActualizacion(new Date())
                .direccionEnvio(user1.getDireccion())
                .build();
        ordenRepo.save(orden1);

        orden orden2 = orden.builder()
                .usuario(user2)
                .carrito(cart2)
                .estado(estadoOrden.PROCESANDO)
                .fechaCreacion(new Date())
                .fechaActualizacion(new Date())
                .direccionEnvio(user2.getDireccion())
                .build();
        ordenRepo.save(orden2);

        // DETALLES ORDEN

        detalleOrden d1 = detalleOrden.builder()
                .orden(orden1)
                .producto(prod1)
                .cantidad(1)
                .precioUnitario(prod1.getPrecio())
                .total(prod1.getPrecio().multiply(BigDecimal.valueOf(1))) // total = precioUnitario * cantidad
                .carrito(cart1)
                .build();
        detalleOrdenRepo.save(d1);

        detalleOrden d2 = detalleOrden.builder()
                .orden(orden2)
                .producto(prod2)
                .cantidad(2)
                .precioUnitario(prod2.getPrecio())
                .total(prod2.getPrecio().multiply(BigDecimal.valueOf(2))) // total = precioUnitario * cantidad
                .carrito(cart2)
                .build();
        detalleOrdenRepo.save(d2);

    }
}
