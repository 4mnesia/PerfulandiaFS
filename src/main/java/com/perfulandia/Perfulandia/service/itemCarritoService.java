package com.perfulandia.Perfulandia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // guardar varios itemCarrito
    public List<ItemCarrito> saveAllItemCarritos(List<ItemCarrito> items) {
        return itemCarritoRepository.saveAll(items);
    }

    // eliminar un itemCarrito
    public void deleteItemCarrito(Long id) {
        itemCarritoRepository.deleteById(id);
    }

    // actualizar un itemCarrito
    public ItemCarrito updateItemCarrito(ItemCarrito item) {
        Optional<ItemCarrito> existente = itemCarritoRepository.findById(item.getId());
        if (existente.isPresent()) {
            // Actualiza los campos necesarios aquí si hace falta
            return itemCarritoRepository.save(item);
        } else {
            throw new RuntimeException("No existe el item");
        }
    }

    // eliminar todos los itemCarrito
    public void deleteAllItemCarritos() {
        itemCarritoRepository.deleteAll();
    }
    //v2

    //Filtrar por carrito id
    public List<ItemCarrito> findByCarritoId(Long carritoId) {
        return itemCarritoRepository.findByCarrito_Id(carritoId);
    }
    //Filtrar por producto id
    public List<ItemCarrito> findByProductoId(Long productoId) {
        return itemCarritoRepository.findByProductoId(productoId);
    }
    //Filtrar por greater than cantidad
    public List<ItemCarrito> findByCantidadGreaterThan(int cantidad) {
        return itemCarritoRepository.findByCantidadGreaterThan(cantidad);
    }

}
