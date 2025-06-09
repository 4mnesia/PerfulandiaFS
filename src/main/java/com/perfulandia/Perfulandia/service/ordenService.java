package com.perfulandia.Perfulandia.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.repository.OrdenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenService { 
    @Autowired
    private OrdenRepository ordenRepository;
    // leer orden por id
    public Orden getOrdenById(Long id) {
        return ordenRepository.findById(id).orElse(null);
    }
    // leer orden por id de usuario 
    public List<Orden> getOrdenByUsuarioId(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }
    //leer por id de carrito
    public List<Orden> getOrdenByCarritoId(Long carritoId) {
        return ordenRepository.findByCarritoId(carritoId);
    }
    // leer todos los ordenes
    public List<Orden> getAllOrdenes() {
        return ordenRepository.findAll();
    }
    // guardar un orden
    public Orden saveOrden(Orden orden) {
        return ordenRepository.save(orden);
    }
    // eliminar un orden
    public void deleteOrden(Long id) {
        if (!ordenRepository.existsById(id)) {
            throw new IllegalArgumentException("Orden con ID " + id + " no existe.");
        }
        ordenRepository.deleteById(id);
    }
    // actualizar un orden
    public Orden updateOrden(Orden orden) {
        return ordenRepository.save(orden);
    }
    // eliminar todos los ordenes
    public void deleteAllOrdenes() {
        ordenRepository.deleteAll();
    }
    
}
