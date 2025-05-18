package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.producto;
import com.perfulandia.Perfulandia.service.productoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private productoService productoService;


    @GetMapping
    public List<producto> getAllProductos() {
        return productoService.findAll();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<producto> getProductoById(@PathVariable Long id) {
        Optional<producto> optionalProducto = productoService.findById(id);
        return optionalProducto.map(ResponseEntity::ok)
                               .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public producto createProducto(@RequestBody producto p) {
        return productoService.save(p);
    }


    @PutMapping("/{id}")
    public ResponseEntity<producto> updateProducto(@PathVariable Long id, @RequestBody producto updatedProducto) {
        Optional<producto> optionalProducto = productoService.findById(id);
        if (optionalProducto.isPresent()) {
            producto existing = optionalProducto.get();
            existing.setNombre(updatedProducto.getNombre());
            existing.setDescripcion(updatedProducto.getDescripcion());
            existing.setPrecio(updatedProducto.getPrecio());
            existing.setStock(updatedProducto.getStock());
            return ResponseEntity.ok(productoService.save(existing));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (productoService.findById(id).isPresent()) {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
