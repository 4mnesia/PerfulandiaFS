
package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.Perfulandia.model.itemCarrito;
import com.perfulandia.Perfulandia.service.itemCarritoService;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class itemCarritoController {
    @Autowired
    private itemCarritoService itemCarritoService;

    // listar todo
    @GetMapping("/itemCarrito")
    public List<itemCarrito> getAllItemCarrito() {
        List<itemCarrito> items = itemCarritoService.getAllItemCarritos();
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("No se encontraron items en el carrito.");
        }
        return items;
    }

    // listar por id
    @GetMapping("/itemCarrito/{id}")
    public itemCarrito getItemCarritoById(@PathVariable Long id) {
        try {
            itemCarrito item = itemCarritoService.getItemCarritoById(id);
            if (item == null) {
                throw new RuntimeException("ItemCarrito not found with id: " + id);
            }
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving ItemCarrito: " + e.getMessage());
        }
    }

    // crear itemCarrito
    @PostMapping("/itemCarrito")
    public itemCarrito createItemCarrito(@RequestBody itemCarrito nuevoItemCarrito) {
        try {
            return itemCarritoService.saveItemCarrito(nuevoItemCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el itemCarrito: " + e.getMessage());
        }
    }

    // crear varios itemCarrito
    @PostMapping("/itemCarrito/batch")
    public List<itemCarrito> createItemsCarrito(@RequestBody List<itemCarrito> nuevosItemsCarrito) {
        try {
            return nuevosItemsCarrito.stream()
                    .map(itemCarritoService::saveItemCarrito)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear los itemsCarrito: " + e.getMessage());
        }
    }

    // eliminar itemCarrito por id
    @DeleteMapping("/itemCarrito/{id}")
    public void deleteItemCarrito(@PathVariable Long id) {
        try {
            itemCarritoService.deleteItemCarrito(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el itemCarrito: " + e.getMessage());
        }
    }

    // eliminar todos los itemCarrito
    @DeleteMapping("/itemCarrito")
    public void deleteAllItemsCarrito() {
        try {
            itemCarritoService.deleteAllItemCarritos();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar todos los itemsCarrito: " + e.getMessage());
        }
    }

    // actualizar itemCarrito por id
    @PutMapping("/itemCarrito/{id}")
    public itemCarrito updateItemCarrito(@RequestParam Long id, @RequestBody itemCarrito itemCarrito) {
        // verificar si existe el itemCarrito
        itemCarrito existingItemCarrito = itemCarritoService.getItemCarritoById(id);
        if (existingItemCarrito == null) {
            throw new RuntimeException("ItemCarrito not found with id: " + id);
        }
        itemCarrito.setId(id);
        return itemCarritoService.updateItemCarrito(itemCarrito);
    }
}
