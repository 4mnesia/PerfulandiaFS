package com.perfulandia.Perfulandia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.carrito;
import com.perfulandia.Perfulandia.repository.carritoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class carritoService {
    
    @Autowired
    private carritoRepository carritoRepository;

    // leer carrito por id
    public carrito getCarritoById(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }

    // leer el carrito por id de usuario
    public carrito getCarritoByUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId).orElse(null);
    }

    // leer todos los carritos
    public List<carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    // guardar un carrito
    public carrito saveCarrito(carrito carrito) {
        return carritoRepository.save(carrito);
    }
    

    // eliminar un carrito
    public void deleteCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    // actualizar un carrito
    public carrito updateCarrito(carrito carrito) {
        if (carritoRepository.findById(carrito.getId()).isEmpty()) {
            return null;
        }
        return carritoRepository.save(carrito);
    }

    // elimianr todos losc arritos
    public void deleteAllCarritos() {
        carritoRepository.deleteAll();
    }


    // crear varios carritos
    public List<carrito> createCarritos(List<carrito> nuevosCarritos) {
        return nuevosCarritos.stream()
                .map(carritoRepository::save)
                .toList();
    }

}
