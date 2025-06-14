package com.perfulandia.Perfulandia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;

    // leer carrito por id
    public Carrito getCarritoById(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }


    // leer todos los carritos
    public List<Carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    // guardar un carrito
    public Carrito saveCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }
    

    // eliminar un carrito
    public void deleteCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    // actualizar un carrito
    public Carrito updateCarrito(Carrito carrito) {
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
    public List<Carrito> createCarritos(List<Carrito> nuevosCarritos) {
        return nuevosCarritos.stream()
                .map(carritoRepository::save)
                .toList();
    }

}
