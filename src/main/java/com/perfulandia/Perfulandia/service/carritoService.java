package com.perfulandia.Perfulandia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.carrito;
import com.perfulandia.Perfulandia.repository.carritoRepository;

@Service
public class carritoService {
    @Autowired
    private carritoRepository carritoRepository;
    // Implementar métodos de servicio según sea necesario
    // Por ejemplo, puedes agregar métodos para crear, leer, actualizar y eliminar carritos
    // Ejemplo de método para obtener un carrito por su ID
    public carrito getCarritoById(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }

    // Ejemplo de método para eliminar un carrito por su ID
    public void deleteCarritoById(Long id) {
        carritoRepository.deleteById(id);
    }
    // Ejemplo de método para guardar un carrito
    public carrito saveCarrito(carrito carrito) {
        return carritoRepository.save(carrito);
    }
    // Ejemplo de método para obtener un carrito por el ID del usuario
    public carrito getCarritoByUsuarioId(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId).orElse(null);
    }
    // Ejemplo de método para eliminar un carrito por el ID del usuario
    public void deleteCarritoByUsuarioId(Long usuarioId) {
        carritoRepository.deleteByUsuarioId(usuarioId);
    }
    // Ejemplo de método para actualizar un carrito
    public void updateCarrito(Long carritoId, Long usuarioId) {
        carritoRepository.updateCarrito(carritoId, usuarioId);
    }
    // Otros métodos de servicio según sea necesario
    // Puedes agregar más métodos según tus necesidades
    // Por ejemplo, métodos para agregar productos al carrito, calcular el total,
    // etc.
    // calcular total
    public Integer calcularTotal(Long carritoId) {
        return carritoRepository.calcularTotal(carritoId);
    }
    // vaciar carrito
    public void vaciarCarrito(Long carritoId) {
        carritoRepository.vaciarCarrito(carritoId);
    }


}
