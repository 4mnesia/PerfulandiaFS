
package com.perfulandia.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.service.ItemCarritoService;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")
public class ItemCarritoController {
    @Autowired
    private ItemCarritoService itemCarritoService;

    // listar todo
    @GetMapping("/itemCarrito")
    public List<ItemCarrito> getAllItemCarrito() {
        List<ItemCarrito> items = itemCarritoService.getAllItemCarritos();
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("No se encontraron items en el carrito.");
        }
        return items;
    }

    // listar por id
    @GetMapping("/itemCarrito/{id}")
    public ItemCarrito getItemCarritoById(@PathVariable Long id) {
        try {
            ItemCarrito item = itemCarritoService.getItemCarritoById(id);
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
    public ItemCarrito createItemCarrito(@RequestBody ItemCarrito nuevoItemCarrito) {
        try {
            return itemCarritoService.saveItemCarrito(nuevoItemCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el itemCarrito: " + e.getMessage());
        }
    }

    // crear varios itemCarrito
    @PostMapping("/itemCarrito/batch")
    public List<ItemCarrito> createItemsCarrito(@RequestBody List<ItemCarrito> nuevosItemsCarrito) {
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
    public ItemCarrito updateItemCarrito(@RequestParam Long id, @RequestBody ItemCarrito itemCarrito) {
        // verificar si existe el itemCarrito
        ItemCarrito existingItemCarrito = itemCarritoService.getItemCarritoById(id);
        if (existingItemCarrito == null) {
            throw new RuntimeException("ItemCarrito not found with id: " + id);
        }
        itemCarrito.setId(id);
        return itemCarritoService.updateItemCarrito(itemCarrito);
    }
}
