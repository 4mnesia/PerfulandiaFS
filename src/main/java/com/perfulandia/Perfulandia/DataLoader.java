package com.perfulandia.Perfulandia;

import net.datafaker.Faker;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.perfulandia.Perfulandia.repository.*;
import com.perfulandia.Perfulandia.model.*;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

        private final UsuarioRepository usuarioRepository;
        private final ProductoRepository productoRepository;
        private final DetalleOrdenRepository detalleOrdenRepository;
        //private final ItemCarritoRepository itemCarritoRepository;
        //private final OrdenRepository ordenRepository;
        private final CarritoRepository carritoRepository;

        @Override
        public void run(String... args) throws Exception {
                Faker faker = new Faker();
                Random random = new Random();
                

             
                // 1. Productos (10)
                for (int i = 0; i < 10; i++) {
                        Producto producto = Producto.builder()
                                .nombre(faker.commerce().productName())
                                .descripcion(faker.lorem().sentence())
                                .categoria(faker.commerce().department())
                                .marca(faker.company().name()) 
                                .modelo(faker.bothify("Modelo-####"))
                                .precio(new BigDecimal(faker.commerce().price()))
                                .stock(faker.number().numberBetween(5, 100))
                                .fechaCreacion(new java.util.Date())
                                .build();
                        productoRepository.save(producto);
                }
                // 2. Usuarios (50)
                for (int i = 0; i < 10; i++) {
                        Usuario usuario = Usuario.builder()
                                        .nombre(faker.name().fullName())
                                        .email(faker.internet().emailAddress())
                                        .contraseña(faker.internet().password())
                                        .direccion(faker.address().fullAddress())
                                        .telefono(faker.phoneNumber().cellPhone())
                                        .rol(RolUsuario.values()[faker.random().nextInt(RolUsuario.values().length)])
                                        .build();
                        usuarioRepository.save(usuario);
                }
                
                // 3. Carritos (5) - relacionados a usuarios
                List<Usuario> usuarios = usuarioRepository.findAll();
                Collections.shuffle(usuarios); // Mezcla la lista para que sea aleatoria
                for (int i = 0; i < 5 && i < usuarios.size(); i++) {
                        Usuario usuario = usuarios.get(i);
                        Carrito carrito = Carrito.builder()
                                        .usuario(usuario)
                                        .estado(random.nextBoolean())
                                        .build();
                        carritoRepository.save(carrito);
                }
                // 4. Detalles de orden (20) - relacionados a productos y carritos
                List<Producto> productos = productoRepository.findAll();
                List<Carrito> carritos = carritoRepository.findAll(); 
                for (int i = 0; i < 20; i++) {
                        Producto producto = productos.get(random.nextInt(productos.size()));
                        Carrito carrito = carritos.get(random.nextInt(carritos.size()));
                        Integer cantidad = faker.number().numberBetween(1, producto.getStock() + 1);
                        BigDecimal precioUnitario = producto.getPrecio();
                        BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));


                        DetalleOrden detalleOrden = DetalleOrden.builder()
                                        .producto(producto)
                                        .carrito(carrito)
                                        .cantidad(cantidad)
                                        .precioUnitario(precioUnitario)
                                        .total(total)
                                        .build();

                        detalleOrdenRepository.save(detalleOrden);
                }
               

        }
}