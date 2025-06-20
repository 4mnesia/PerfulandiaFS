package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.perfulandia.Perfulandia.service.CarritoService;
import com.perfulandia.Perfulandia.model.Carrito;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    // Obtener todos los carritos
    @GetMapping("/carrito")
    public List<Carrito> getAllCarritos() {
        try {
            return carritoService.getAllCarritos();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los carritos: " + e.getMessage());
        }
    }

    // carritos por id
    @GetMapping("/carrito/{id}")
    public Carrito getCarritoById(@PathVariable Long id) {
        try {
            return carritoService.getCarritoById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el carrito por ID: " + e.getMessage());
        }
    }

    // crear carrito
    @PostMapping("/carrito")
    public Carrito createCarrito(@RequestBody Carrito nuevoCarrito) {
        try {
            return carritoService.saveCarrito(nuevoCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el carrito: " + e.getMessage());
        }
    }

    // crear varios carritos
    @PostMapping("/carrito/batch")
    public List<Carrito> createCarritos(@RequestBody List<Carrito> nuevosCarritos) {
        try {
            return nuevosCarritos.stream()
                    .map(carritoService::saveCarrito)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear los carritos: " + e.getMessage());
        }
    }

    // actualizar carrito
    @PutMapping("/carrito/{id}")
    public Carrito updateCarrito(@PathVariable Long id, @RequestBody Carrito carritoActualizado) {
        try {
            Carrito existente = carritoService.getCarritoById(id);
            if (existente == null) {
                throw new RuntimeException("Carrito no encontrado con id: " + id);
            }
            existente.setUsuario(carritoActualizado.getUsuario());
            existente.setEstado(carritoActualizado.isEstado());
            // Solución: no reemplazar la lista, sino modificarla
            existente.getItem().clear();
            if (carritoActualizado.getItem() != null) {
                existente.getItem().addAll(carritoActualizado.getItem());
            }
            return carritoService.updateCarrito(existente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el carrito: " + e.getMessage());
        }
    }

    // eliminar un carrito por su ID
    @DeleteMapping("/carrito/{id}")
    public void deleteCarrito(@PathVariable Long id) {
        try {
            carritoService.deleteCarrito(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el carrito: " + e.getMessage());
        }
    }

    // borrar todos los carritos
    @DeleteMapping("/carrito")
    public void deleteAllCarritos() {
        try {
            carritoService.deleteAllCarritos();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar todos los carritos: " + e.getMessage());
        }
    }
}
