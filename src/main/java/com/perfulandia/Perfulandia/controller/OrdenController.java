package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.model.orden;
import com.perfulandia.Perfulandia.service.ordenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private ordenService ordenService;

    @GetMapping
    public List<orden> getAllOrdenes() {
        return ordenService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<orden> getOrdenById(@PathVariable Long id) {
        Optional<orden> o = ordenService.findById(id);
        return o.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public orden createOrden(@RequestBody orden o) {
        return ordenService.save(o);
    }

    @PutMapping("/{id}")
    public ResponseEntity<orden> updateOrden(@PathVariable Long id, @RequestBody orden o) {
        Optional<orden> optionalOrden = ordenService.findById(id);
        if (optionalOrden.isPresent()) {
            orden existing = optionalOrden.get();
            existing.setFecha(o.getFecha());
            existing.setEstado(o.getEstado());
            return ResponseEntity.ok(ordenService.save(existing));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
        if (ordenService.findById(id).isPresent()) {
            ordenService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
