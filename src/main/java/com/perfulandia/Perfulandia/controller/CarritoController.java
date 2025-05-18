package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.carrito;
import com.perfulandia.Perfulandia.service.carritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private carritoService carritoService;

    @GetMapping
    public List<carrito> getAllCarritos() {
        return carritoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<carrito> getCarritoById(@PathVariable Long id) {
        Optional<carrito> c = carritoService.findById(id);
        return c.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public carrito createCarrito(@RequestBody carrito c) {
        return carritoService.save(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<carrito> updateCarrito(@PathVariable Long id, @RequestBody carrito c) {
        Optional<carrito> optional = carritoService.findById(id);
        if (optional.isPresent()) {
            carrito existing = optional.get();
            existing.setUsuario(c.getUsuario());
            existing.setItems(c.getItems());
            return ResponseEntity.ok(carritoService.save(existing));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        if (carritoService.findById(id).isPresent()) {
            carritoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
