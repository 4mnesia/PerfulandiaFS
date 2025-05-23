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

    //listar todo
    @GetMapping("/orden")
    public List<orden> getAllOrden() {
        return ordenService.getAllOrdenes();
    }
    //listar por id
    @GetMapping("/orden/{id}")
    public orden getOrdenById(@PathVariable Long id) {
        return ordenService.getOrdenById(id);
    }
    //crear orden
    @PostMapping("/orden")
    public orden createOrden(@RequestBody orden nuevaOrden) {
        return ordenService.saveOrden(nuevaOrden);
    }
    //crear varios ordenes
    @PostMapping("/orden/batch")
    public List<orden> createOrdenes(@RequestBody List<orden> nuevasOrdenes) {
        return nuevasOrdenes.stream()
                .map(ordenService::saveOrden)
                .toList();
    }
    //eliminar orden por id
    @DeleteMapping("/orden/{id}")
    public void deleteOrden(@RequestParam Long id) {
        ordenService.deleteOrden(id);
    }
    //eliminar todos los ordenes
    @DeleteMapping("/orden")
    public void deleteAllOrdenes() {
        ordenService.deleteAllOrdenes();
    }
    //actualizar orden por id
    @PutMapping("/orden/{id}")
    public ResponseEntity<orden> updateOrden(@PathVariable Long id, @RequestBody orden ordenActualizada) {
    orden ordenExistente = ordenService.getOrdenById(id);
    if (ordenExistente != null) {
        ordenExistente.setEstado(ordenActualizada.getEstado());
        ordenExistente.setDireccionEnvio(ordenActualizada.getDireccionEnvio());
        ordenExistente.setTotal(ordenActualizada.getTotal());
        ordenExistente.setCarrito(ordenActualizada.getCarrito());
        ordenExistente.setUsuario(ordenActualizada.getUsuario());
        ordenExistente.setDetalles(ordenActualizada.getDetalles());
        return ResponseEntity.ok(ordenService.updateOrden(ordenExistente));
    } else {
        return ResponseEntity.notFound().build();
    }
}
}
