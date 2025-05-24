package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.service.detalleOrdenService;
import com.perfulandia.Perfulandia.model.detalleOrden;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")

public class detalleOrdenController {
    @Autowired
    private detalleOrdenService detalleOrdenService;

    // listar todo
    @GetMapping("/detalleOrden")
    public List<detalleOrden> getAllDetalleOrden() {
        return detalleOrdenService.getAllDetalles();
    }
    // listar por id de producto
    @GetMapping("/detalleOrden/producto/{productoId}")
    public List<detalleOrden> getDetallesByProductoId(@PathVariable Long productoId) {
        return detalleOrdenService.getDetallesByProductoId(productoId);
    }
    // listar por id
    @GetMapping("/detalleOrden/{id}")
    public detalleOrden getDetalleOrdenById(@PathVariable Long id) {
        return detalleOrdenService.getDetalleOrdenById(id);
    }

    // crear detalleOrden
    @PostMapping("/detalleOrden")
    public detalleOrden createDetalleOrden(@RequestBody detalleOrden nuevoDetalleOrden) {
        return detalleOrdenService.saveDetalleOrden(nuevoDetalleOrden);
    }

    // crear varios detalleOrden
    @PostMapping("/detalleOrden/batch")
    public List<detalleOrden> createDetallesOrden(@RequestBody List<detalleOrden> nuevosDetallesOrden) {
        return nuevosDetallesOrden.stream()
                .map(detalleOrdenService::saveDetalleOrden)
                .toList();
    }

    // eliminar detalleOrden por id
    @DeleteMapping("/detalleOrden/{id}")
    public void deleteDetalleOrden(@PathVariable Long id) {
        detalleOrdenService.deleteDetalleOrden(id);
    }

    // eliminar todos los detalleOrden
    @DeleteMapping("/detalleOrden")
    public void deleteAllDetallesOrden() {
        detalleOrdenService.deleteAllDetalles();
    }

    // actualizar detalleOrden por id
    @PutMapping("/detalleOrden/{id}")
    public detalleOrden updateDetalleOrden(@RequestParam Long id, @RequestBody detalleOrden detalleOrden) {
        // verificar si existe el detalleOrden
        detalleOrden existingDetalleOrden = detalleOrdenService.getDetalleOrdenById(id);
        if (existingDetalleOrden == null) {
            throw new RuntimeException("Detalle de orden no encontrado con id: " + id);
        }
        detalleOrden.setId(id);
        return detalleOrdenService.updateDetalleOrden(detalleOrden);
    }

}
