
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
        return itemCarritoService.getAllItemCarritos();
    }

    // listar por id
    @GetMapping("/itemCarrito/{id}")
    public itemCarrito getItemCarritoById(@PathVariable Long id) {
        return itemCarritoService.getItemCarritoById(id);
    }

    // crear itemCarrito
    @PostMapping("/itemCarrito")
    public itemCarrito createItemCarrito(@RequestBody itemCarrito nuevoItemCarrito) {
        return itemCarritoService.saveItemCarrito(nuevoItemCarrito);
    }

    // crear varios itemCarrito
    @PostMapping("/itemCarrito/batch")
    public List<itemCarrito> createItemsCarrito(@RequestBody List<itemCarrito> nuevosItemsCarrito) {
        return nuevosItemsCarrito.stream()
                .map(itemCarritoService::saveItemCarrito)
                .toList();
    }

    // eliminar itemCarrito por id
    @DeleteMapping("/itemCarrito/{id}")
    public void deleteItemCarrito(@RequestParam Long id) {
        itemCarritoService.deleteItemCarrito(id);
    }

    // eliminar todos los itemCarrito
    @DeleteMapping("/itemCarrito")
    public void deleteAllItemsCarrito() {
        itemCarritoService.deleteAllItemCarritos();
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
