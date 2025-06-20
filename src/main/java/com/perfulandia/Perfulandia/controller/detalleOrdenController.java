package com.perfulandia.Perfulandia.controller;

import com.perfulandia.Perfulandia.service.DetalleOrdenService;
import com.perfulandia.Perfulandia.model.DetalleOrden;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/perfulandia")

public class DetalleOrdenController {
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    // listar todo
    @GetMapping("/detalleOrden")
    public List<DetalleOrden> getAllDetalleOrden() {
        try {
            return detalleOrdenService.getAllDetalles();
        } catch (Exception e) {
            // Puedes personalizar el manejo de errores según tus necesidades
            throw new RuntimeException("Error al obtener los detalles de la orden: " + e.getMessage(), e);
        }
    }

    // listar por id
    @GetMapping("/detalleOrden/{id}")
    public DetalleOrden getDetalleOrdenById(@PathVariable Long id) {
        try {
            return detalleOrdenService.getDetalleOrdenById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el detalle de la orden por id: " + e.getMessage(), e);
        }
    }

    // crear detalleOrden
    @PostMapping("/detalleOrden")
    public DetalleOrden createDetalleOrden(@RequestBody DetalleOrden nuevoDetalleOrden) {
        try {
            return detalleOrdenService.saveDetalleOrden(nuevoDetalleOrden);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el detalle de la orden: " + e.getMessage(), e);
        }
    }

    // crear varios detalleOrden
    @PostMapping("/detalleOrden/batch")
    public List<DetalleOrden> createDetallesOrden(@RequestBody List<DetalleOrden> nuevosDetallesOrden) {
        try {
            return nuevosDetallesOrden.stream()
                    .map(detalleOrdenService::saveDetalleOrden)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear los detalles de la orden: " + e.getMessage(), e);
        }
    }

    // actualizar detalleorden por id
    @PutMapping("/detalleOrden/{id}")
    public DetalleOrden updateDetalleOrden(@PathVariable Long id, @RequestBody DetalleOrden detalleActualizado) {
        try {
            DetalleOrden existente = detalleOrdenService.getDetalleOrdenById(id);
            if (existente == null) {
                throw new RuntimeException("DetalleOrden no encontrado con id: " + id);
            }
            existente.setProducto(detalleActualizado.getProducto());
            existente.setCantidad(detalleActualizado.getCantidad());
            existente.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
            existente.setTotal(detalleActualizado.getTotal());
            return detalleOrdenService.updateDetalleOrden(existente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el detalle de la orden: " + e.getMessage(), e);
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
