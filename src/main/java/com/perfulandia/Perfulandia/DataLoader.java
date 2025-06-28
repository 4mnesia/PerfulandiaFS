
package com.perfulandia.Perfulandia;

import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
import com.perfulandia.Perfulandia.repository.*;

import net.datafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
    private OrdenRepository ordenRepository;
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es"));
        Random random = new Random();

        // (Opcional) Limpiar tablas si ya existen datos
        detalleOrdenRepository.deleteAll();
        itemCarritoRepository.deleteAll();
        ordenRepository.deleteAll();
        carritoRepository.deleteAll();
        productoRepository.deleteAll();
        usuarioRepository.deleteAll();

        // 1. Crear Usuarios :contentReference[oaicite:0]{index=0}
        List<Usuario> usuarios = new ArrayList<>();
        IntStream.range(1, 11).forEach(i -> {
            Usuario u = Usuario.builder()
                    .nombre(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .contraseña(faker.internet().password(8, 16))
                    .direccion(faker.address().fullAddress())
                    .telefono(faker.phoneNumber().phoneNumber())
                    .rol(RolUsuario.values()[faker.number().numberBetween(
                            0, RolUsuario.values().length)])
                    .build();
            usuarios.add(usuarioRepository.save(u));
        });

        // 2. Crear Productos :contentReference[oaicite:1]{index=1}
        List<Producto> productos = new ArrayList<>();
        IntStream.range(1, 21).forEach(i -> {
            Date fecha = faker.date().past(365, TimeUnit.DAYS);
            BigDecimal precio = BigDecimal.valueOf(
                    faker.number().randomDouble(2, 5, 500));
            Producto p = Producto.builder()
                    .nombre(faker.commerce().productName())
                    .descripcion(faker.lorem().sentence(3))
                    .categoria(faker.commerce().department())
                    .marca(faker.company().name())
                    .modelo(faker.bothify("??-###"))
                    .precio(precio)
                    .stock(faker.number().numberBetween(0, 100))
                    .fechaCreacion(fecha)
                    .build();
            productos.add(productoRepository.save(p));
        });

        // 3. Crear Carritos con Items :contentReference[oaicite:2]{index=2}
        // :contentReference[oaicite:3]{index=3}
        for (Usuario u : usuarios) {
            Carrito c = Carrito.builder()
                    .usuario(u)
                    .estado(random.nextBoolean())
                    .build();
            final Carrito savedCarrito = carritoRepository.save(c);

            int itemsCount = faker.number().numberBetween(1, 5);
            IntStream.range(0, itemsCount).forEach(j -> {
                Producto prod = productos.get(
                        faker.number().numberBetween(0, productos.size()));
                ItemCarrito item = ItemCarrito.builder()
                        .producto(prod)
                        .cantidad(faker.number().numberBetween(1, 10))
                        .carrito(savedCarrito)
                        .build();
                itemCarritoRepository.save(item);
            });
        }

        // 4. Crear Órdenes con Detalles :contentReference[oaicite:4]{index=4}
        // :contentReference[oaicite:5]{index=5}
        
        for (Usuario u : usuarios) {
            if (faker.bool().bool()) {
                LocalDateTime created = LocalDateTime.now()
                        .minusDays(faker.number().numberBetween(1, 30));
                LocalDateTime updated = created.plusDays(
                        faker.number().numberBetween(0, 5));
                // elige un estado aleatorio:
                EstadoOrden randomEstado = EstadoOrden.values()[random.nextInt(EstadoOrden.values().length)];

                Orden ordenToSave = Orden.builder()
                        .usuario(u)
                        .direccionEnvio(faker.address().fullAddress())
                        .estado(randomEstado)
                        .fechaCreacion(created)
                        .fechaActualizacion(updated)
                        .build();
                Orden savedOrden = ordenRepository.save(ordenToSave);

                int detallesCount = faker.number().numberBetween(1, 5);
                IntStream.range(0, detallesCount).forEach(k -> {
                    Producto prod = productos.get(
                            faker.number().numberBetween(0, productos.size()));
                    int qty = faker.number().numberBetween(1, 5);
                    BigDecimal unitPrice = prod.getPrecio();
                    BigDecimal total = unitPrice.multiply(
                            BigDecimal.valueOf(qty));
                    DetalleOrden det = DetalleOrden.builder()
                            .orden(savedOrden)
                            .producto(prod)
                            .cantidad(qty)
                            .precioUnitario(unitPrice)
                            .total(total)
                            .build();
                    detalleOrdenRepository.save(det);
                });
            }
        }
    }
}