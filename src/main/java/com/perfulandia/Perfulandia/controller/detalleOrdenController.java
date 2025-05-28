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
        try {
            return detalleOrdenService.getAllDetalles();
        } catch (Exception e) {
            // Puedes personalizar el manejo de errores según tus necesidades
            throw new RuntimeException("Error al obtener los detalles de la orden: " + e.getMessage(), e);
        }
    }
    // listar por id de producto
    @GetMapping("/detalleOrden/producto/{productoId}")
    public List<detalleOrden> getDetallesByProductoId(@PathVariable Long productoId) {
        try {
            return detalleOrdenService.getDetallesByProductoId(productoId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los detalles por productoId: " + e.getMessage(), e);
        }
    }
    // listar por id
    @GetMapping("/detalleOrden/{id}")
    public detalleOrden getDetalleOrdenById(@PathVariable Long id) {
        try {
            return detalleOrdenService.getDetalleOrdenById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el detalle de la orden por id: " + e.getMessage(), e);
        }
    }

    // crear detalleOrden
    @PostMapping("/detalleOrden")
    public detalleOrden createDetalleOrden(@RequestBody detalleOrden nuevoDetalleOrden) {
        try {
            return detalleOrdenService.saveDetalleOrden(nuevoDetalleOrden);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el detalle de la orden: " + e.getMessage(), e);
        }
    }

    // crear varios detalleOrden
    @PostMapping("/detalleOrden/batch")
    public List<detalleOrden> createDetallesOrden(@RequestBody List<detalleOrden> nuevosDetallesOrden) {
        try {
            return nuevosDetallesOrden.stream()
                    .map(detalleOrdenService::saveDetalleOrden)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear los detalles de la orden: " + e.getMessage(), e);
        }
    }

    // eliminar detalleOrden por id
    @DeleteMapping("/detalleOrden/{id}")
    public void deleteDetalleOrden(@PathVariable Long id) {
        try {
            detalleOrdenService.deleteDetalleOrden(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el detalle de la orden: " + e.getMessage(), e);
        }
    }

    // eliminar todos los detalleOrden
    @DeleteMapping("/detalleOrden")
    public void deleteAllDetallesOrden() {
        try {
            detalleOrdenService.deleteAllDetalles();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar todos los detalles de la orden: " + e.getMessage(), e);
        }
    }
}
