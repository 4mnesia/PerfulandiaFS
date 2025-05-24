
package com.perfulandia.Perfulandia.config;

import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Date;

@Component
@RequiredArgsConstructor
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

        usuario user1 = usuario.builder()
            .nombre("Usuario1")
            .email("user1@perfulandia.com")
            .password("pass1")
            .direccion("Calle 1 de los Perfumes")
            .telefono("912340001")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user1);
    
        usuario user2 = usuario.builder()
            .nombre("Usuario2")
            .email("user2@perfulandia.com")
            .password("pass2")
            .direccion("Calle 2 de los Perfumes")
            .telefono("912340002")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user2);
    
        usuario user3 = usuario.builder()
            .nombre("Usuario3")
            .email("user3@perfulandia.com")
            .password("pass3")
            .direccion("Calle 3 de los Perfumes")
            .telefono("912340003")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user3);
    
        usuario user4 = usuario.builder()
            .nombre("Usuario4")
            .email("user4@perfulandia.com")
            .password("pass4")
            .direccion("Calle 4 de los Perfumes")
            .telefono("912340004")
            .rol(rolUsuario.CLIENTE)
            .build();
        usuarioRepo.save(user4);
    
        usuario user5 = usuario.builder()
            .nombre("Usuario5")
            .email("user5@perfulandia.com")
            .password("pass5")
            .direccion("Calle 5 de los Perfumes")
            .telefono("912340005")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user5);
    
        usuario user6 = usuario.builder()
            .nombre("Usuario6")
            .email("user6@perfulandia.com")
            .password("pass6")
            .direccion("Calle 6 de los Perfumes")
            .telefono("912340006")
            .rol(rolUsuario.CLIENTE)
            .build();
        usuarioRepo.save(user6);
    
        usuario user7 = usuario.builder()
            .nombre("Usuario7")
            .email("user7@perfulandia.com")
            .password("pass7")
            .direccion("Calle 7 de los Perfumes")
            .telefono("912340007")
            .rol(rolUsuario.CLIENTE)
            .build();
        usuarioRepo.save(user7);
    
        usuario user8 = usuario.builder()
            .nombre("Usuario8")
            .email("user8@perfulandia.com")
            .password("pass8")
            .direccion("Calle 8 de los Perfumes")
            .telefono("912340008")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user8);
    
        usuario user9 = usuario.builder()
            .nombre("Usuario9")
            .email("user9@perfulandia.com")
            .password("pass9")
            .direccion("Calle 9 de los Perfumes")
            .telefono("912340009")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user9);
    
        usuario user10 = usuario.builder()
            .nombre("Usuario10")
            .email("user10@perfulandia.com")
            .password("pass10")
            .direccion("Calle 10 de los Perfumes")
            .telefono("912340010")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user10);
    
        usuario user11 = usuario.builder()
            .nombre("Usuario11")
            .email("user11@perfulandia.com")
            .password("pass11")
            .direccion("Calle 11 de los Perfumes")
            .telefono("912340011")
            .rol(rolUsuario.CLIENTE)
            .build();
        usuarioRepo.save(user11);
    
        usuario user12 = usuario.builder()
            .nombre("Usuario12")
            .email("user12@perfulandia.com")
            .password("pass12")
            .direccion("Calle 12 de los Perfumes")
            .telefono("912340012")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user12);
    
        usuario user13 = usuario.builder()
            .nombre("Usuario13")
            .email("user13@perfulandia.com")
            .password("pass13")
            .direccion("Calle 13 de los Perfumes")
            .telefono("912340013")
            .rol(rolUsuario.ADMINISTRADOR)
            .build();
        usuarioRepo.save(user13);
    
        usuario user14 = usuario.builder()
            .nombre("Usuario14")
            .email("user14@perfulandia.com")
            .password("pass14")
            .direccion("Calle 14 de los Perfumes")
            .telefono("912340014")
            .rol(rolUsuario.CLIENTE)
            .build();
        usuarioRepo.save(user14);
    
        usuario user15 = usuario.builder()
            .nombre("Usuario15")
            .email("user15@perfulandia.com")
            .password("pass15")
            .direccion("Calle 15 de los Perfumes")
            .telefono("912340015")
            .rol(rolUsuario.CLIENTE)
            .build();
        usuarioRepo.save(user15);
    
// PRODUCTOS

        producto prod1 = producto.builder()
            .nombre("Perfume Rose 1")
            .descripcion("Descripción del perfume 1")
            .categoria("Fragancia")
            .marca("RoseArt")
            .modelo("MOD0001")
            .precio(new BigDecimal("23983.48"))
            .stock(42)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod1);
    
        producto prod2 = producto.builder()
            .nombre("Perfume Noir 2")
            .descripcion("Descripción del perfume 2")
            .categoria("Fragancia")
            .marca("NoirLine")
            .modelo("MOD0002")
            .precio(new BigDecimal("11345.71"))
            .stock(46)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod2);
    
        producto prod3 = producto.builder()
            .nombre("Perfume Citrus 3")
            .descripcion("Descripción del perfume 3")
            .categoria("Fragancia")
            .marca("CitrusFresh")
            .modelo("MOD0003")
            .precio(new BigDecimal("34144.34"))
            .stock(11)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod3);
    
        producto prod4 = producto.builder()
            .nombre("Perfume Forest 4")
            .descripcion("Descripción del perfume 4")
            .categoria("Fragancia")
            .marca("ForestLeaf")
            .modelo("MOD0004")
            .precio(new BigDecimal("12632.69"))
            .stock(31)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod4);
    
        producto prod5 = producto.builder()
            .nombre("Perfume Amber 5")
            .descripcion("Descripción del perfume 5")
            .categoria("Fragancia")
            .marca("AmberCo")
            .modelo("MOD0005")
            .precio(new BigDecimal("46169.36"))
            .stock(25)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod5);
    
        producto prod6 = producto.builder()
            .nombre("Perfume Vanilla 6")
            .descripcion("Descripción del perfume 6")
            .categoria("Fragancia")
            .marca("VanillaEssence")
            .modelo("MOD0006")
            .precio(new BigDecimal("37503.64"))
            .stock(10)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod6);
    
        producto prod7 = producto.builder()
            .nombre("Perfume Spice 7")
            .descripcion("Descripción del perfume 7")
            .categoria("Fragancia")
            .marca("SpiceCraft")
            .modelo("MOD0007")
            .precio(new BigDecimal("14933.96"))
            .stock(16)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod7);
    
        producto prod8 = producto.builder()
            .nombre("Perfume Mint 8")
            .descripcion("Descripción del perfume 8")
            .categoria("Fragancia")
            .marca("MintCool")
            .modelo("MOD0008")
            .precio(new BigDecimal("22724.81"))
            .stock(43)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod8);
    
        producto prod9 = producto.builder()
            .nombre("Perfume Lavender 9")
            .descripcion("Descripción del perfume 9")
            .categoria("Fragancia")
            .marca("LavenderLux")
            .modelo("MOD0009")
            .precio(new BigDecimal("12595.8"))
            .stock(44)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod9);
    
        producto prod10 = producto.builder()
            .nombre("Perfume Ocean 10")
            .descripcion("Descripción del perfume 10")
            .categoria("Fragancia")
            .marca("BlueWave")
            .modelo("MOD0010")
            .precio(new BigDecimal("40317.4"))
            .stock(15)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod10);
    
        producto prod11 = producto.builder()
            .nombre("Perfume Rose 11")
            .descripcion("Descripción del perfume 11")
            .categoria("Fragancia")
            .marca("RoseArt")
            .modelo("MOD0011")
            .precio(new BigDecimal("23959.0"))
            .stock(39)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod11);
    
        producto prod12 = producto.builder()
            .nombre("Perfume Noir 12")
            .descripcion("Descripción del perfume 12")
            .categoria("Fragancia")
            .marca("NoirLine")
            .modelo("MOD0012")
            .precio(new BigDecimal("23645.64"))
            .stock(15)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod12);
    
        producto prod13 = producto.builder()
            .nombre("Perfume Citrus 13")
            .descripcion("Descripción del perfume 13")
            .categoria("Fragancia")
            .marca("CitrusFresh")
            .modelo("MOD0013")
            .precio(new BigDecimal("37382.74"))
            .stock(27)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod13);
    
        producto prod14 = producto.builder()
            .nombre("Perfume Forest 14")
            .descripcion("Descripción del perfume 14")
            .categoria("Fragancia")
            .marca("ForestLeaf")
            .modelo("MOD0014")
            .precio(new BigDecimal("47855.95"))
            .stock(39)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod14);
    
        producto prod15 = producto.builder()
            .nombre("Perfume Amber 15")
            .descripcion("Descripción del perfume 15")
            .categoria("Fragancia")
            .marca("AmberCo")
            .modelo("MOD0015")
            .precio(new BigDecimal("28136.39"))
            .stock(38)
            .fechaCreacion(new Date())
            .build();
        productoRepo.save(prod15);
    
// CARRITOS

        carrito cart1 = carrito.builder()
            .usuario(user1)
            .estado(false)
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
            .estado(false)
            .build();
            carritoRepo.save(cart3);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart3).producto(prod1).cantidad(1).build());
        

        carrito cart4 = carrito.builder()
            .usuario(user4)
            .estado(true)
            .build();
            carritoRepo.save(cart4);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart4).producto(prod4).cantidad(3).build());
        

        carrito cart5 = carrito.builder()
            .usuario(user5)
            .estado(true)
            .build();
            carritoRepo.save(cart5);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart5).producto(prod5).cantidad(2).build());
        

        carrito cart6 = carrito.builder()
            .usuario(user6)
            .estado(true)
            .build();
            carritoRepo.save(cart6);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart6).producto(prod6).cantidad(4).build());
        

        carrito cart7 = carrito.builder()
            .usuario(user7)
            .estado(false)
            .build();
            carritoRepo.save(cart7);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart7).producto(prod7).cantidad(2).build());
        

        carrito cart8 = carrito.builder()
            .usuario(user8)
            .estado(true)
            .build();
            carritoRepo.save(cart8);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart8).producto(prod8).cantidad(3).build());
        

        carrito cart9 = carrito.builder()
            .usuario(user9)
            .estado(true)
            .build();
            carritoRepo.save(cart9);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart9).producto(prod9).cantidad(3).build());
        

        carrito cart10 = carrito.builder()
            .usuario(user10)
            .estado(false)
            .build();
            carritoRepo.save(cart10);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart10).producto(prod10).cantidad(4).build());
        

        carrito cart11 = carrito.builder()
            .usuario(user11)
            .estado(true)
            .build();
            carritoRepo.save(cart11);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart11).producto(prod11).cantidad(5).build());
        

        carrito cart12 = carrito.builder()
            .usuario(user12)
            .estado(false)
            .build();
            carritoRepo.save(cart12);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart12).producto(prod12).cantidad(2).build());
        

        carrito cart13 = carrito.builder()
            .usuario(user13)
            .estado(true)
            .build();
            carritoRepo.save(cart13);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart13).producto(prod13).cantidad(4).build());
        

        carrito cart14 = carrito.builder()
            .usuario(user14)
            .estado(true)
            .build();
            carritoRepo.save(cart14);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart14).producto(prod14).cantidad(3).build());
        

        carrito cart15 = carrito.builder()
            .usuario(user15)
            .estado(true)
            .build();
            carritoRepo.save(cart15);
            itemCarritoRepo.save(itemCarrito.builder().carrito(cart15).producto(prod15).cantidad(5).build());
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
            .cantidad(5)
            .build();
        itemCarritoRepo.save(item2);
    
        itemCarrito item3 = itemCarrito.builder()
            .carrito(cart3)
            .producto(prod3)
            .cantidad(4)
            .build();
        itemCarritoRepo.save(item3);
    
        itemCarrito item4 = itemCarrito.builder()
            .carrito(cart4)
            .producto(prod4)
            .cantidad(3)
            .build();
        itemCarritoRepo.save(item4);
    
        itemCarrito item5 = itemCarrito.builder()
            .carrito(cart5)
            .producto(prod5)
            .cantidad(2)
            .build();
        itemCarritoRepo.save(item5);
    
        itemCarrito item6 = itemCarrito.builder()
            .carrito(cart6)
            .producto(prod6)
            .cantidad(4)
            .build();
        itemCarritoRepo.save(item6);
    
        itemCarrito item7 = itemCarrito.builder()
            .carrito(cart7)
            .producto(prod7)
            .cantidad(2)
            .build();
        itemCarritoRepo.save(item7);
    
        itemCarrito item8 = itemCarrito.builder()
            .carrito(cart8)
            .producto(prod8)
            .cantidad(3)
            .build();
        itemCarritoRepo.save(item8);
    
        itemCarrito item9 = itemCarrito.builder()
            .carrito(cart9)
            .producto(prod9)
            .cantidad(3)
            .build();
        itemCarritoRepo.save(item9);
    
        itemCarrito item10 = itemCarrito.builder()
            .carrito(cart10)
            .producto(prod10)
            .cantidad(4)
            .build();
        itemCarritoRepo.save(item10);
    
        itemCarrito item11 = itemCarrito.builder()
            .carrito(cart11)
            .producto(prod11)
            .cantidad(5)
            .build();
        itemCarritoRepo.save(item11);
    
        itemCarrito item12 = itemCarrito.builder()
            .carrito(cart12)
            .producto(prod12)
            .cantidad(2)
            .build();
        itemCarritoRepo.save(item12);
    
        itemCarrito item13 = itemCarrito.builder()
            .carrito(cart13)
            .producto(prod13)
            .cantidad(4)
            .build();
        itemCarritoRepo.save(item13);
    
        itemCarrito item14 = itemCarrito.builder()
            .carrito(cart14)
            .producto(prod14)
            .cantidad(3)
            .build();
        itemCarritoRepo.save(item14);
    
        itemCarrito item15 = itemCarrito.builder()
            .carrito(cart15)
            .producto(prod15)
            .cantidad(5)
            .build();
        itemCarritoRepo.save(item15);
    
// ORDENES

        orden orden1 = orden.builder()
            .usuario(user1)
            .carrito(cart1)
            .estado(estadoOrden.PROCESANDO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user1.getDireccion())
            .total(prod1.getPrecio())
            .build();
        ordenRepo.save(orden1);
    
        orden orden2 = orden.builder()
            .usuario(user2)
            .carrito(cart2)
            .estado(estadoOrden.PROCESANDO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user2.getDireccion())
            .total(prod2.getPrecio())
            .build();
        ordenRepo.save(orden2);
    
        orden orden3 = orden.builder()
            .usuario(user3)
            .carrito(cart3)
            .estado(estadoOrden.PENDIENTE)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user3.getDireccion())
            .total(prod3.getPrecio())
            .build();
        ordenRepo.save(orden3);
    
        orden orden4 = orden.builder()
            .usuario(user4)
            .carrito(cart4)
            .estado(estadoOrden.PROCESANDO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user4.getDireccion())
            .total(prod4.getPrecio())
            .build();
        ordenRepo.save(orden4);
    
        orden orden5 = orden.builder()
            .usuario(user5)
            .carrito(cart5)
            .estado(estadoOrden.PENDIENTE)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user5.getDireccion())
            .total(prod5.getPrecio())
            .build();
        ordenRepo.save(orden5);
    
        orden orden6 = orden.builder()
            .usuario(user6)
            .carrito(cart6)
            .estado(estadoOrden.ENVIADO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user6.getDireccion())
            .total(prod6.getPrecio())
            .build();
        ordenRepo.save(orden6);
    
        orden orden7 = orden.builder()
            .usuario(user7)
            .carrito(cart7)
            .estado(estadoOrden.CANCELADO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user7.getDireccion())
            .total(prod7.getPrecio())
            .build();
        ordenRepo.save(orden7);
    
        orden orden8 = orden.builder()
            .usuario(user8)
            .carrito(cart8)
            .estado(estadoOrden.ENTREGADO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user8.getDireccion())
            .total(prod8.getPrecio())
            .build();
        ordenRepo.save(orden8);
    
        orden orden9 = orden.builder()
            .usuario(user9)
            .carrito(cart9)
            .estado(estadoOrden.PENDIENTE)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user9.getDireccion())
            .total(prod9.getPrecio())
            .build();
        ordenRepo.save(orden9);
    
        orden orden10 = orden.builder()
            .usuario(user10)
            .carrito(cart10)
            .estado(estadoOrden.PENDIENTE)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user10.getDireccion())
            .total(prod10.getPrecio())
            .build();
        ordenRepo.save(orden10);
    
        orden orden11 = orden.builder()
            .usuario(user11)
            .carrito(cart11)
            .estado(estadoOrden.PROCESANDO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user11.getDireccion())
            .total(prod11.getPrecio())
            .build();
        ordenRepo.save(orden11);
    
        orden orden12 = orden.builder()
            .usuario(user12)
            .carrito(cart12)
            .estado(estadoOrden.CANCELADO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user12.getDireccion())
            .total(prod12.getPrecio())
            .build();
        ordenRepo.save(orden12);
    
        orden orden13 = orden.builder()
            .usuario(user13)
            .carrito(cart13)
            .estado(estadoOrden.ENTREGADO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user13.getDireccion())
            .total(prod13.getPrecio())
            .build();
        ordenRepo.save(orden13);
    
        orden orden14 = orden.builder()
            .usuario(user14)
            .carrito(cart14)
            .estado(estadoOrden.PROCESANDO)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user14.getDireccion())
            .total(prod14.getPrecio())
            .build();
        ordenRepo.save(orden14);
    
        orden orden15 = orden.builder()
            .usuario(user15)
            .carrito(cart15)
            .estado(estadoOrden.PENDIENTE)
            .fechaCreacion(new Date())
            .fechaActualizacion(new Date())
            .direccionEnvio(user15.getDireccion())
            .total(prod15.getPrecio())
            .build();
        ordenRepo.save(orden15);
    
// DETALLES ORDEN

        detalleOrden d1 = detalleOrden.builder()
            .orden(orden1)
            .producto(prod1)
            .cantidad(1)
            .precioUnitario(prod1.getPrecio())
            .build();
        detalleOrdenRepo.save(d1);
    
        detalleOrden d2 = detalleOrden.builder()
            .orden(orden2)
            .producto(prod2)
            .cantidad(1)
            .precioUnitario(prod2.getPrecio())
            .build();
        detalleOrdenRepo.save(d2);
    
        detalleOrden d3 = detalleOrden.builder()
            .orden(orden3)
            .producto(prod3)
            .cantidad(5)
            .precioUnitario(prod3.getPrecio())
            .build();
        detalleOrdenRepo.save(d3);
    
        detalleOrden d4 = detalleOrden.builder()
            .orden(orden4)
            .producto(prod4)
            .cantidad(1)
            .precioUnitario(prod4.getPrecio())
            .build();
        detalleOrdenRepo.save(d4);
    
        detalleOrden d5 = detalleOrden.builder()
            .orden(orden5)
            .producto(prod5)
            .cantidad(5)
            .precioUnitario(prod5.getPrecio())
            .build();
        detalleOrdenRepo.save(d5);
    
        detalleOrden d6 = detalleOrden.builder()
            .orden(orden6)
            .producto(prod6)
            .cantidad(1)
            .precioUnitario(prod6.getPrecio())
            .build();
        detalleOrdenRepo.save(d6);
    
        detalleOrden d7 = detalleOrden.builder()
            .orden(orden7)
            .producto(prod7)
            .cantidad(5)
            .precioUnitario(prod7.getPrecio())
            .build();
        detalleOrdenRepo.save(d7);
    
        detalleOrden d8 = detalleOrden.builder()
            .orden(orden8)
            .producto(prod8)
            .cantidad(2)
            .precioUnitario(prod8.getPrecio())
            .build();
        detalleOrdenRepo.save(d8);
    
        detalleOrden d9 = detalleOrden.builder()
            .orden(orden9)
            .producto(prod9)
            .cantidad(1)
            .precioUnitario(prod9.getPrecio())
            .build();
        detalleOrdenRepo.save(d9);
    
        detalleOrden d10 = detalleOrden.builder()
            .orden(orden10)
            .producto(prod10)
            .cantidad(3)
            .precioUnitario(prod10.getPrecio())
            .build();
        detalleOrdenRepo.save(d10);
    
        detalleOrden d11 = detalleOrden.builder()
            .orden(orden11)
            .producto(prod11)
            .cantidad(1)
            .precioUnitario(prod11.getPrecio())
            .build();
        detalleOrdenRepo.save(d11);
    
        detalleOrden d12 = detalleOrden.builder()
            .orden(orden12)
            .producto(prod12)
            .cantidad(2)
            .precioUnitario(prod12.getPrecio())
            .build();
        detalleOrdenRepo.save(d12);
    
        detalleOrden d13 = detalleOrden.builder()
            .orden(orden13)
            .producto(prod13)
            .cantidad(4)
            .precioUnitario(prod13.getPrecio())
            .build();
        detalleOrdenRepo.save(d13);
    
        detalleOrden d14 = detalleOrden.builder()
            .orden(orden14)
            .producto(prod14)
            .cantidad(3)
            .precioUnitario(prod14.getPrecio())
            .build();
        detalleOrdenRepo.save(d14);
    
        detalleOrden d15 = detalleOrden.builder()
            .orden(orden15)
            .producto(prod15)
            .cantidad(5)
            .precioUnitario(prod15.getPrecio())
            .build();
        detalleOrdenRepo.save(d15);
    
    }
}
