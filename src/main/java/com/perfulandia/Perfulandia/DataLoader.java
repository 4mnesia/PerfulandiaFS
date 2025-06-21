package com.perfulandia.Perfulandia;
import net.datafaker.Faker;
import java.math.BigDecimal;
import java.time.*;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.perfulandia.Perfulandia.repository.*;
import com.perfulandia.Perfulandia.model.*;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
import com.perfulandia.Perfulandia.model.Enums.RolUsuario;
@Profile("dev")
@Component
@Transactional
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

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        RolUsuario[] roles = RolUsuario.values();
        EstadoOrden[] estados = EstadoOrden.values();

        // 0) Limpiar tablas
        ordenRepository.deleteAll();
        itemCarritoRepository.deleteAll();
        carritoRepository.deleteAll();
        usuarioRepository.deleteAll();
        productoRepository.deleteAll();

        // 1) Crear Productos
        LocalDateTime randomProdDate = LocalDateTime.now()
                .minusDays(faker.number().numberBetween(1, 90));
        Date fechaCreacionDate = Date.from(
                randomProdDate.atZone(ZoneId.systemDefault())
                        .toInstant());

        for (int i = 0; i < 10; i++) {
            BigDecimal precio = BigDecimal.valueOf(faker.number().numberBetween(1000, 20000))
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

        // 2) Crear Usuarios
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
        List<Carrito> carritos = new ArrayList<>();

        // 3)crear Carrito + Items
        for (Usuario usuario : usuarios) {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setEstado(random.nextBoolean());
            carrito = carritoRepository.save(carrito);
            carritos.add(carrito);

            int numItems = faker.number().numberBetween(1, 4);
            for (int j = 0; j < numItems; j++) {
                Producto prod = productos.get(random.nextInt(productos.size()));
                int qty = faker.number().numberBetween(1, 3);

                ItemCarrito item = ItemCarrito.builder()
                        .producto(prod)
                        .cantidad(qty)
                        .carrito(carrito)
                        .build();
                itemCarritoRepository.save(item);
            }
        }

        // 4) Para cada Carrito: crear DetalleOrden + Orden
        for (Carrito carrito : carritos) {
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

            // Fechas aleatorias
            LocalDateTime created = LocalDateTime.now()
                    .minusDays(faker.number().numberBetween(1, 30));
            long maxSecs = ChronoUnit.SECONDS.between(created, LocalDateTime.now());
            LocalDateTime updated = created.plusSeconds(
                    faker.number().numberBetween(0, (int) maxSecs));

            //cascade ALL de Orden se encargará de persistir los DetalleOrden
            Orden o = Orden.builder()
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
