package com.perfulandia.Perfulandia;

import net.datafaker.Faker;

import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.perfulandia.Perfulandia.repository.CarritoRepository;
/*import com.perfulandia.Perfulandia.repository.DetalleOrdenRepository;
import com.perfulandia.Perfulandia.repository.ItemCarritoRepository;
import com.perfulandia.Perfulandia.repository.OrdenRepository;*/
import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.model.Producto;

import com.perfulandia.Perfulandia.repository.ProductoRepository;
import com.perfulandia.Perfulandia.model.RolUsuario;
import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

        private final UsuarioRepository usuarioRepository;
        private final ProductoRepository productoRepository;
        /*private final DetalleOrdenRepository detalleOrdenRepository;
        private final ItemCarritoRepository itemCarritoRepository;
        private final OrdenRepository ordenRepository;*/
        private final CarritoRepository carritoRepository;

        @Override
        public void run(String... args) throws Exception {
                Faker faker = new Faker();
                Random random = new Random();

                for (int i = 0; i < 50; i++) {
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
                for (int i = 0; i < 10; i++) {
                        Producto producto = Producto.builder()
                                        .nombre(faker.commerce().productName())
                                        .descripcion(faker.lorem().sentence())
                                        .precio(Double.valueOf(faker.commerce().price()))
                                        .stock(faker.number().numberBetween(5, 100))
                                        .build();
                        productoRepository.save(producto);
                }
                // 3. Carritos (5) - relacionados a usuarios
                List<Usuario> usuarios = usuarioRepository.findAll();
                for (int i = 0; i < 5; i++) {
                        Usuario usuario = usuarios.get(random.nextInt(usuarios.size()));
                        Carrito carrito = Carrito.builder()
                                        .usuario(usuario)
                                        .build();
                        carritoRepository.save(carrito);
                }

        }
}