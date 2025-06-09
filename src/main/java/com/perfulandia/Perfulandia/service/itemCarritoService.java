package com.perfulandia.Perfulandia.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.perfulandia.Perfulandia.model.ItemCarrito;
import com.perfulandia.Perfulandia.repository.ItemCarritoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCarritoService {
    @Autowired
    private ItemCarritoRepository itemCarritoRepository;
    // leer itemCarrito por id
    public ItemCarrito getItemCarritoById(Long id) {
        return itemCarritoRepository.findById(id).orElse(null);
    }
    // leer todos los itemCarrito
    public List<ItemCarrito> getAllItemCarritos() {
        return itemCarritoRepository.findAll();
    }
    // guardar un itemCarrito
    public ItemCarrito saveItemCarrito(ItemCarrito itemCarrito) {
        return itemCarritoRepository.save(itemCarrito);
    }
    // eliminar un itemCarrito
    public void deleteItemCarrito(Long id) {
        itemCarritoRepository.deleteById(id);
    }
    // actualizar un itemCarrito
    public ItemCarrito updateItemCarrito(ItemCarrito itemCarrito) {
        return itemCarritoRepository.save(itemCarrito);
    }
    // eliminar todos los itemCarrito
    public void deleteAllItemCarritos() {
        itemCarritoRepository.deleteAll();
    }
}
