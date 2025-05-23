package com.perfulandia.Perfulandia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.Perfulandia.model.carrito;
import com.perfulandia.Perfulandia.service.carritoService;

@RestController
@RequestMapping("/api/perfulandia")
public class carritoController {
    @Autowired
    private carritoService carritoService;
    // Aquí puedes agregar los métodos para manejar las operaciones CRUD de carrito
    // Ejemplo de un método para obtener todos los carritos
    public List<carrito> getAllCarritos() {
        return carritoService.getAllCarritos();
    }
    //carritos por id
    public carrito getCarritoById(Long id) {
        return carritoService.getCarritoById(id);
    }
    //crear carrito
    public carrito createCarrito(carrito nuevoCarrito) {
        return carritoService.saveCarrito(nuevoCarrito);
    }
    //crear varios carritos
    public List<carrito> createCarritos(List<carrito> nuevosCarritos) {
        return nuevosCarritos.stream()
                .map(carritoService::saveCarrito)
                .toList();
    }
    // Ejemplo de un método para eliminar un carrito por su ID
    public void deleteCarrito(Long id) {
        carritoService.deleteCarrito(id);
    }
    //borrar todos los carritos
    public void deleteAllCarritos() {
        carritoService.deleteAllCarritos();
    }
    


}
