package com.perfulandia.Perfulandia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.Perfulandia.model.carrito;

import com.perfulandia.Perfulandia.service.carritoService;

@RestController
@RequestMapping("/api/perfulandia")
public class carritoController {
    @Autowired
    private carritoService carritoService;

    // Aquí puedes agregar los métodos para manejar las operaciones CRUD de carrito
    // Ejemplo de un método para obtener todos los carritos
    @GetMapping("/carrito")
    public List<carrito> getAllCarritos() {
        return carritoService.getAllCarritos();
    }

    // carritos por id
    @GetMapping("/carrito/{id}")
    public carrito getCarritoById(@PathVariable Long id) {
        return carritoService.getCarritoById(id);
    }

    // crear carrito
    @PostMapping("/carrito")
    public carrito createCarrito(@RequestBody carrito nuevoCarrito) {
        return carritoService.saveCarrito(nuevoCarrito);
    }

    // crear varios carritos
    @PostMapping("/carrito/batch")
    public List<carrito> createCarritos(List<carrito> nuevosCarritos) {
        return nuevosCarritos.stream()
                .map(carritoService::saveCarrito)
                .toList();
    }

    // Ejemplo de un método para eliminar un carrito por su ID
    @DeleteMapping("/carrito/{id}")
    public void deleteCarrito(@PathVariable Long id) {
        carritoService.deleteCarrito(id);
    }

    // borrar todos los carritos
    @DeleteMapping("/carrito")
    public void deleteAllCarritos() {
        carritoService.deleteAllCarritos();
    }

    // actualizar carrito por id
    @PutMapping("/carrito/{id}")
    public carrito updateCarrito(@PathVariable Long id, @RequestBody carrito carrito) {
        // verificar si existe el carrito
        carrito carritoExistente = carritoService.getCarritoById(id);
        if (carritoExistente == null) {
            throw new RuntimeException("Carrito no encontrado con id: " + id);
        }
        carrito.setId(id);
        return carritoService.updateCarrito(carrito);
    }
}
