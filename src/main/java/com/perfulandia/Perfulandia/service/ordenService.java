package com.perfulandia.Perfulandia.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.orden;
import com.perfulandia.Perfulandia.repository.ordenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ordenService {
    @Autowired
    private ordenRepository ordenRepository;
    // leer orden por id
    public orden getOrdenById(Long id) {
        return ordenRepository.findById(id).orElse(null);
    }
    // leer orden por id de usuario 
    public List<orden> getOrdenByUsuarioId(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId)
                .map(List::of)
                .orElse(List.of());
    }
    //leer por id de carrito
    public List<orden> getOrdenByCarritoId(Long carritoId) {
        return ordenRepository.findByCarritoId(carritoId)
                .map(List::of)
                .orElse(List.of());
    }
    // leer todos los ordenes
    public List<orden> getAllOrdenes() {
        return ordenRepository.findAll();
    }
    // guardar un orden
    public orden saveOrden(orden orden) {
        return ordenRepository.save(orden);
    }
    // eliminar un orden
    public void deleteOrden(Long id) {
        ordenRepository.deleteById(id);
    }
    // actualizar un orden
    public orden updateOrden(orden orden) {
        return ordenRepository.save(orden);
    }
    // eliminar todos los ordenes
    public void deleteAllOrdenes() {
        ordenRepository.deleteAll();
    }
    
}
