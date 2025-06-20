package com.perfulandia.Perfulandia.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.perfulandia.Perfulandia.model.Orden; // <-- IMPORTA LA CLASE CON MAYÚSCULA
import com.perfulandia.Perfulandia.service.OrdenService;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class OrdenController {
    @Autowired
    private OrdenService ordenService; // <-- minúscula la variable

    // listar todo
    @GetMapping("/orden")
    public ResponseEntity<?> getAllOrdenes() {
        try {
            List<Orden> ordenes = ordenService.getAllOrdenes(); // <-- Orden, no orden
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener las ordenes: " + e.getMessage());
        }
    }

    // listar por id
    @GetMapping("/orden/{id}")
    public ResponseEntity<?> getOrdenById(@PathVariable Long id) {
        try {
            Orden orden = ordenService.getOrdenById(id); // <-- tipo corregido a 'orden'
            if (orden != null) {
                return ResponseEntity.ok(orden);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener la orden por id: " + e.getMessage());
        }
    }
    // guardar

    @PostMapping("/orden")
    public ResponseEntity<?> saveOrden(@RequestBody Orden orden) {
        try {
            if (orden == null) {
                return ResponseEntity.badRequest().body("La orden no puede ser nula");
            }
            if (orden.getDetalles() == null || orden.getDetalles().isEmpty()) {
                return ResponseEntity.badRequest().body("La orden debe tener al menos un detalle");
            }
            Orden ordenGuardada = ordenService.saveOrden(orden);
            return ResponseEntity.ok(ordenGuardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la orden: " + e.getMessage());
        }
    }

    // modificar
    @PutMapping("/orden/{id}")
    public ResponseEntity<?> updateOrden(@PathVariable Long id, @RequestBody Orden orden) {
        try {
            Orden ordenActualizada = ordenService.updateOrden(id, orden);
            if (ordenActualizada != null) {
                return ResponseEntity.ok(ordenActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al modificar la orden: " + e.getMessage());
        }
    }

    // delet por id
    @DeleteMapping("/orden/{id}")
    public ResponseEntity<?> deleteOrden(@PathVariable Long id) {
        try {
            ordenService.deleteOrden(id);
            return ResponseEntity.ok("Orden eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la orden: " + e.getMessage());
        }
    }

    // delet all
    @DeleteMapping("/orden")
    public ResponseEntity<?> deleteAllOrdenes() {
        try {
            ordenService.deleteAllOrdenes();
            return ResponseEntity.ok("Todas las ordenes han sido eliminadas correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar todas las ordenes: " + e.getMessage());
        }
    }

    // Endpoint de salud
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
