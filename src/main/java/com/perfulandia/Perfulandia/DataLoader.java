package com.perfulandia.Perfulandia;

import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
import com.perfulandia.Perfulandia.repository.*;

@Profile("dev")
@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private CarritoRepository carritoRepository;
    @Autowired private OrdenRepository ordenRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        RolUsuario[] roles    = RolUsuario.values();
        EstadoOrden[] estados = EstadoOrden.values();

        // 0) Limpiar tablas
        ordenRepository.deleteAll();
        carritoRepository.deleteAll();
        usuarioRepository.deleteAll();
        productoRepository.deleteAll();

        // 1) Crear Productos
        for (int i = 0; i < 10; i++) {
            long raw = faker.number().numberBetween(1000, 20000);
            BigDecimal precio = BigDecimal.valueOf(raw, 2);
            
            LocalDateTime randDate = LocalDateTime.now()
                .minusDays(faker.number().numberBetween(1, 90));
            Date fechaCreacion = Date.from(randDate.atZone(ZoneId.systemDefault()).toInstant());

            Producto p = Producto.builder()
                .nombre(faker.commerce().productName())
                .descripcion(faker.lorem().sentence())
                .categoria(faker.commerce().department())
                .marca(faker.company().name())
                .modelo(faker.commerce().material())
                .precio(precio)
                .stock(faker.number().numberBetween(5, 50))
                .fechaCreacion(fechaCreacion)
                .build();
            productoRepository.save(p);
        }

        // 2) Crear Usuarios
        for (int i = 0; i < 5; i++) {
            Usuario u = Usuario.builder()
                .nombre(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .direccion(faker.address().fullAddress())
                .telefono(faker.phoneNumber().phoneNumber())
                .rol(roles[random.nextInt(roles.length)])
                .contraseña(faker.internet().password(8, 12))
                .build();
            usuarioRepository.save(u);
        }

        List<Usuario> usuarios   = usuarioRepository.findAll();
        List<Producto> productos = productoRepository.findAll();
        List<Carrito> carritos   = new ArrayList<>();

        // 3) Para cada Usuario: crear un Carrito con Items
        for (Usuario usuario : usuarios) {
            Carrito carrito = Carrito.builder()
                .usuario(usuario)
                .estado(random.nextBoolean())
                .build();

            // Genero entre 1 y 4 items dentro del carrito
            int numItems = faker.number().numberBetween(1, 4);
            for (int j = 0; j < numItems; j++) {
                Producto prod = productos.get(random.nextInt(productos.size()));
                int qty = faker.number().numberBetween(1, 3);

                ItemCarrito item = ItemCarrito.builder()
                    .producto(prod)
                    .cantidad(qty)
                    .build();

                carrito.getItem().add(item);
            }

            Carrito savedCarrito = carritoRepository.save(carrito);
            carritos.add(savedCarrito);
        }

        // 4) Para cada Carrito: crear una Orden con DetalleOrden
        for (Carrito carrito : carritos) {
            // Armo la lista de detalles
            List<DetalleOrden> detalles = new ArrayList<>();
            int numDetalles = faker.number().numberBetween(1, 3);
            for (int j = 0; j < numDetalles; j++) {
                Producto prod = productos.get(random.nextInt(productos.size()));
                int qty = faker.number().numberBetween(1, 2);

                DetalleOrden d = DetalleOrden.builder()
                    .producto(prod)
                    .cantidad(qty)
                    .precioUnitario(prod.getPrecio())
                    .total(prod.getPrecio().multiply(BigDecimal.valueOf(qty)))
                    .build();

                detalles.add(d);
            }

            // Fechas aleatorias de creación/actualización
            LocalDateTime created = LocalDateTime.now()
                .minusDays(faker.number().numberBetween(1, 30));
            long maxSecs = ChronoUnit.SECONDS.between(created, LocalDateTime.now());
            LocalDateTime updated = created.plusSeconds(faker.number().numberBetween(0, (int)maxSecs));

            Orden orden = Orden.builder()
                .detalles(detalles)
                .estado(estados[random.nextInt(estados.length)])
                .fechaCreacion(created)
                .fechaActualizacion(updated)
                .direccionEnvio(faker.address().fullAddress())
                .build();

            ordenRepository.save(orden);
        }
    }
}
