package com.perfulandia.Perfulandia.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.repository.OrdenRepository;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    // leer orden por id
    public Orden getOrdenById(Long id) {
        return ordenRepository.findById(id).orElse(null);
    }

    // leer todos los ordenes
    public List<Orden> getAllOrdenes() {
        return ordenRepository.findAll();
    }
    //guardar orden
    public Orden saveOrden(Orden orden) {
        return ordenRepository.save(orden);
    }
    //modificar orden
    public Orden updateOrden(Long id, Orden orden) {
        if (ordenRepository.existsById(id)) {
            orden.setId(id);
            return ordenRepository.save(orden);
        }
        return null;
    }

    // eliminar un orden
    public void deleteOrden(Long id) {
        ordenRepository.deleteById(id);
    }

    // eliminar todos los ordenes
    public void deleteAllOrdenes() {
        ordenRepository.deleteAll();
    }
}
