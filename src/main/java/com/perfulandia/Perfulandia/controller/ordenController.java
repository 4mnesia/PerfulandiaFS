package com.perfulandia.Perfulandia.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.perfulandia.Perfulandia.model.orden;
import com.perfulandia.Perfulandia.service.ordenService;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class ordenController {
    @Autowired
    private ordenService ordenService;

    // listar todo
    @GetMapping("/orden")
    public ResponseEntity<List<orden>> getAllOrden() {
        try {
            List<orden> ordenes = ordenService.getAllOrdenes();
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // listar por id
    @GetMapping("/orden/{id}")
    public ResponseEntity<orden> getOrdenById(@PathVariable Long id) {
        try {
            orden ordenEncontrada = ordenService.getOrdenById(id);
            if (ordenEncontrada != null) {
                return ResponseEntity.ok(ordenEncontrada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // listar por id de usuario
    @GetMapping("/orden/usuario/{usuarioId}")
    public ResponseEntity<List<orden>> getOrdenByUsuarioId(@PathVariable Long usuarioId) {
        try {
            List<orden> ordenes = ordenService.getOrdenByUsuarioId(usuarioId);
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // listar por id de carrito
    @GetMapping("/orden/carrito/{carritoId}")
    public ResponseEntity<List<orden>> getOrdenByCarritoId(@PathVariable Long carritoId) {
        try {
            List<orden> ordenes = ordenService.getOrdenByCarritoId(carritoId);
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // crear orden
    @PostMapping("/orden")
    public ResponseEntity<orden> createOrden(@RequestBody orden nuevaOrden) {
        try {
            if (nuevaOrden == null) {
                return ResponseEntity.badRequest().body(null);
            }
            // Puedes agregar más validaciones aquí, por ejemplo:
            if (nuevaOrden.getUsuario() == null || nuevaOrden.getCarrito() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            orden creada = ordenService.saveOrden(nuevaOrden);
            return ResponseEntity.ok(creada);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // crear varios ordenes
    @PostMapping("/orden/batch")
    public ResponseEntity<List<orden>> createOrdenes(@RequestBody List<orden> nuevasOrdenes) {
        try {
            if (nuevasOrdenes == null || nuevasOrdenes.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            // Validar cada orden
            for (orden o : nuevasOrdenes) {
                if (o.getUsuario() == null || o.getCarrito() == null) {
                    return ResponseEntity.badRequest().body(null);
                }
            }
            List<orden> creadas = nuevasOrdenes.stream()
                    .map(ordenService::saveOrden)
                    .toList();
            return ResponseEntity.ok(creadas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // eliminar orden por id
    @DeleteMapping("/orden/{id}")
    public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
        try {
            orden ordenExistente = ordenService.getOrdenById(id);
            if (ordenExistente != null) {
                ordenService.deleteOrden(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // eliminar todos los ordenes
    @DeleteMapping("/orden")
    public ResponseEntity<Void> deleteAllOrdenes() {
        try {
            ordenService.deleteAllOrdenes();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // actualizar orden por id
    @PutMapping("/orden/{id}")
    public ResponseEntity<orden> updateOrden(@PathVariable Long id, @RequestBody orden ordenActualizada) {
        try {
            orden ordenExistente = ordenService.getOrdenById(id);
            if (ordenExistente != null) {
                ordenExistente.setEstado(ordenActualizada.getEstado());
                ordenExistente.setDireccionEnvio(ordenActualizada.getDireccionEnvio());
                ordenExistente.setCarrito(ordenActualizada.getCarrito());
                ordenExistente.setUsuario(ordenActualizada.getUsuario());
                ordenExistente.setDetalles(ordenActualizada.getDetalles());
                return ResponseEntity.ok(ordenService.updateOrden(ordenExistente));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
