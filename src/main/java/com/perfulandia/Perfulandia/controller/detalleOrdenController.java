package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService service;

    // Endpoints para DetalleOrden
    // Listar todos los detalles
    @GetMapping("/detalleorden")
    public ResponseEntity<?> getAllDetalles() {
        try {
            List<DetalleOrden> list = service.getAllDetalles();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al obtener detalles: " + e.getMessage());
        }
    }
    // Listar detalle por ID
    @GetMapping("/detalleorden/{id}")
    public ResponseEntity<?> getDetalleById(@PathVariable Long id) {
        try {
            DetalleOrden d = service.getDetalleOrdenById(id);
            if (d != null)
                return ResponseEntity.ok(d);
            else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al obtener detalle: " + e.getMessage());
        }
    }
    // Crear un nuevo detalle
    @PostMapping("/detalleorden")
    public ResponseEntity<?> createDetalle(@RequestBody DetalleOrden detalle) {
        try {
            if (detalle == null) {
                return ResponseEntity.badRequest().body("Detalle no puede ser nulo");
            }
            DetalleOrden saved = service.saveDetalleOrden(detalle);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al guardar detalle: " + e.getMessage());
        }
    }
    // Crear varios detalles en lote
    @PostMapping("/detalleorden/batch")
    public ResponseEntity<?> createDetallesBatch(@RequestBody List<DetalleOrden> detalles) {
        try {
            if (detalles == null || detalles.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista no puede estar vacía");
            }
            List<DetalleOrden> saved = service.saveDetallesOrden(detalles);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al guardar detalles en lote: " + e.getMessage());
        }
    }
    
    @PutMapping("/detalleorden/{id}")
    public ResponseEntity<?> updateDetalle(@PathVariable Long id,
            @RequestBody DetalleOrden detalle) {
        try {
            detalle.setId(id);
            DetalleOrden updated = service.saveDetalleOrden(detalle);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al actualizar detalle: " + e.getMessage());
        }
    }
    // Eliminar un detalle por su ID
    @DeleteMapping("/detalleorden/{id}")
    public ResponseEntity<?> deleteDetalle(@PathVariable Long id) {
        try {
            service.deleteDetalleOrden(id);
            return ResponseEntity.ok("Detalle eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al eliminar detalle: " + e.getMessage());
        }
    }
    // Eliminar todos los detalles
    @DeleteMapping("/detalleorden")
    public ResponseEntity<?> deleteAllDetalles() {
        try {
            service.deleteAllDetalles();
            return ResponseEntity.ok("Todos los detalles eliminados correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al eliminar todos los detalles: " + e.getMessage());
        }
    }
}
