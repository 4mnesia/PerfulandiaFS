package com.perfulandia.Perfulandia.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Perfulandia API",
    version = "v1",
    description = "Documentación de los servicios REST de Perfulandia hecho por Flavio Henriquez, Estefano Yogui, Francisco albornoz",
    contact = @Contact(name = "Equipo Dev", email = "Fullstack002D@perfulandia.com")
  ),
  servers = @Server(url = "/")
)
public class SpringDocConfig {
}