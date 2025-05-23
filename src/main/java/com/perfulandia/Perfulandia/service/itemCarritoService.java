package com.perfulandia.Perfulandia.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.perfulandia.Perfulandia.model.itemCarrito;
import com.perfulandia.Perfulandia.repository.itemCarritoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class itemCarritoService {
    @Autowired
    private itemCarritoRepository itemCarritoRepository;
    // leer itemCarrito por id
    public itemCarrito getItemCarritoById(Long id) {
        return itemCarritoRepository.findById(id).orElse(null);
    }
    // leer todos los itemCarrito
    public List<itemCarrito> getAllItemCarritos() {
        return itemCarritoRepository.findAll();
    }
    // guardar un itemCarrito
    public itemCarrito saveItemCarrito(itemCarrito itemCarrito) {
        return itemCarritoRepository.save(itemCarrito);
    }
    // eliminar un itemCarrito
    public void deleteItemCarrito(Long id) {
        itemCarritoRepository.deleteById(id);
    }
    // actualizar un itemCarrito
    public itemCarrito updateItemCarrito(itemCarrito itemCarrito) {
        return itemCarritoRepository.save(itemCarrito);
    }
    // eliminar todos los itemCarrito
    public void deleteAllItemCarritos() {
        itemCarritoRepository.deleteAll();
    }
}
