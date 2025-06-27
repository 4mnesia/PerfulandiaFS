package com.perfulandia.Perfulandia.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perfulandia.Perfulandia.model.Carrito;
import com.perfulandia.Perfulandia.repository.CarritoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
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
    // guardar varios carritos
    public List<Carrito> saveCarritos(List<Carrito> carritos) {
        return carritoRepository.saveAll(carritos);
    }

    // eliminar un carrito
    @Transactional
    public void deleteCarrito(Long id) {
        Carrito c = carritoRepository.findById(id)
           .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        carritoRepository.delete(c);
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




    //V2
    /** Filtra carritos por usuario */
    public List<Carrito> findByUsuarioId(Long usuarioId) {
        return carritoRepository.findByUsuario_Id(usuarioId);
    }



}
