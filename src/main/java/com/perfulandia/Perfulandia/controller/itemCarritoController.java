package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.service.ItemCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia/itemcarrito")
public class ItemCarritoController {

    @Autowired
    private ItemCarritoService service;

    // GET /api/perfulandia/itemcarrito
    @GetMapping
    public ResponseEntity<?> getAllItems() {
        try {
            return ResponseEntity.ok(service.getAllItemCarritos());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al obtener items: " + e.getMessage());
        }
    }

    // GET /api/perfulandia/itemcarrito/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            ItemCarrito item = service.getItemCarritoById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound()
                        .build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al obtener item por id: " + e.getMessage());
        }
    }

    // POST /api/perfulandia/itemcarrito  (un solo item)
    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody ItemCarrito item) {
        try {
            if (item == null) {
                return ResponseEntity.badRequest()
                        .body("El item no puede ser nulo");
            }
            ItemCarrito saved = service.saveItemCarrito(item);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al crear item: " + e.getMessage());
        }
    }

    // POST /api/perfulandia/itemcarrito/batch  (varios items de golpe)
    @PostMapping("/batch")
    public ResponseEntity<?> createItems(@RequestBody List<ItemCarrito> items) {
        try {
            if (items == null || items.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("La lista de items no puede estar vacía");
            }
            List<ItemCarrito> saved = service.saveAllItemCarritos(items);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al crear items en lote: " + e.getMessage());
        }
    }

    // PUT /api/perfulandia/itemcarrito/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemCarrito item) {
        try {
            ItemCarrito existing = service.getItemCarritoById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            // Validaciones básicas
            if (item.getCantidad() == null || item.getCantidad() < 1) {
                return ResponseEntity.badRequest().body("La cantidad debe ser mayor a 0");
            }
            if (item.getProducto() == null) {
                return ResponseEntity.badRequest().body("El producto no puede ser nulo");
            }
            // Actualiza solo los campos necesarios
            existing.setCantidad(item.getCantidad());
            existing.setProducto(item.getProducto());
            // Agrega aquí otros campos que quieras actualizar

            service.saveItemCarrito(existing);
            return ResponseEntity.ok("Item actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar item: " + e.getMessage());
        }
    }

    // DELETE /api/perfulandia/itemcarrito/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            service.deleteItemCarrito(id);
            return ResponseEntity.ok("Item eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al eliminar item: " + e.getMessage());
        }
    }
}
