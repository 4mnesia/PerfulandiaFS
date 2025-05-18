package com.perfulandia.Perfulandia.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.perfulandia.Perfulandia.model.itemCarrito;
import com.perfulandia.Perfulandia.repository.itemCarritoRepository;

@Service

public class itemCarritoService {
    @Autowired
    private itemCarritoRepository itemCarritoRepository;

    /**
     * Guarda un nuevo itemCarrito o actualiza uno existente
     * 
     * @param itemCarrito ItemCarrito a guardar
     * @return ItemCarrito guardado con su ID asignado
     */
    public List<itemCarrito> getAllItems() {
        return itemCarritoRepository.findAll();
    }

    /**
     * Busca un itemCarrito por su ID
     * 
     * @param id ID del itemCarrito a buscar
     * @return ItemCarrito encontrado o null si no existe
     */
    public itemCarrito getItemCarritoById(Long id) {
        return itemCarritoRepository.findById(id).orElse(null);

    //para guardar un itemCarrito
    public itemCarrito saveItemCarrito(itemCarrito itemCarrito) {
        return itemCarritoRepository.save(itemCarrito);

    //
    }
}
/*
 * public detalleOrden getDetalleOrdenById(Long id) {
 * return detalleOrdenRepository.findById(id).orElse(null);
 * }
 */