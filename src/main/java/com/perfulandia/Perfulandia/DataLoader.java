package com.perfulandia.Perfulandia;

import net.datafaker.Faker;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.perfulandia.Perfulandia.repository.*;
import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ItemCarritoRepository itemCarritoRepository;
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        RolUsuario[] roles = RolUsuario.values();
        EstadoOrden[] estados = EstadoOrden.values();

        // 0) Su deleteall 
        ordenRepository.deleteAll();
        detalleOrdenRepository.deleteAll();
        itemCarritoRepository.deleteAll();
        carritoRepository.deleteAll();
        usuarioRepository.deleteAll();
        productoRepository.deleteAll();

        // 1) Creo 10 productos
        Date fechaCreacionDate = faker.date().past(90, TimeUnit.DAYS);
        for (int i = 0; i < 10; i++) {
            BigDecimal precio = BigDecimal
                    .valueOf(faker.number().numberBetween(1000, 20000))
                    .divide(BigDecimal.valueOf(100));
            Producto p = Producto.builder()
                    .nombre(faker.commerce().productName())
                    .descripcion(faker.lorem().sentence())
                    .precio(precio)
                    .stock(faker.number().numberBetween(5, 50))
                    .marca(faker.company().name())
                    .modelo(faker.commerce().material())
                    .categoria(faker.commerce().department())
                    .fechaCreacion(fechaCreacionDate)
                    .build();
            productoRepository.save(p);
        }

        // 2) Creo 5 usuarios
        for (int i = 0; i < 5; i++) {
            Usuario u = new Usuario();
            u.setNombre(faker.name().fullName());
            u.setEmail(faker.internet().emailAddress());
            u.setDireccion(faker.address().fullAddress());
            u.setTelefono(faker.phoneNumber().phoneNumber());
            u.setRol(roles[random.nextInt(roles.length)]);
            u.setContraseña(faker.internet().password(8, 12));
            usuarioRepository.save(u);
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        // 3) Para cada usuario: creo 1 carrito + ítems
        for (Usuario usuario : usuarios) {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            carritoRepository.save(carrito);

            int numItems = faker.number().numberBetween(1, 4);
            for (int j = 0; j < numItems; j++) {
                Producto prod = productos.get(random.nextInt(productos.size()));
                int qty = faker.number().numberBetween(1, 3);
                ItemCarrito item = new ItemCarrito();
                item.setCarrito(carrito);
                item.setProducto(prod);
                item.setCantidad(qty);
                itemCarritoRepository.save(item);
            }
        }

        // 4) Para cada carrito: creo una orden con sus detalles
        List<Carrito> carritos = carritoRepository.findAll();
        for (Carrito carrito : carritos) {
            // detalles
            List<DetalleOrden> detalles = new ArrayList<>();
            int numDetalles = faker.number().numberBetween(1, 3);
            for (int j = 0; j < numDetalles; j++) {
                Producto prod = productos.get(random.nextInt(productos.size()));
                int qty = faker.number().numberBetween(1, 2);
                DetalleOrden d = new DetalleOrden();
                d.setProducto(prod);
                d.setCarrito(carrito);
                d.setCantidad(qty);
                d.setPrecioUnitario(prod.getPrecio());
                d.setTotal(prod.getPrecio().multiply(BigDecimal.valueOf(qty)));
                detalles.add(d);
            }

            // fechas aleatorias
            Date past = faker.date().past(30, TimeUnit.DAYS);
            LocalDateTime created = LocalDateTime.ofInstant(past.toInstant(), ZoneId.systemDefault());
            Date between = faker.date().between(past, new Date());
            LocalDateTime updated = LocalDateTime.ofInstant(between.toInstant(), ZoneId.systemDefault());

            // orden
            Orden o = Orden.builder()
                    .usuario(carrito.getUsuario())
                    .carrito(carrito)
                    .detalles(detalles)
                    .estado(estados[random.nextInt(estados.length)])
                    .fechaCreacion(created)
                    .fechaActualizacion(updated)
                    .direccionEnvio(faker.address().fullAddress())
                    .build();
            ordenRepository.save(o);
        }
    }
}
