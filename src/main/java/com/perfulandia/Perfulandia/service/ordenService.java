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

    // eliminar un orden
    public void deleteOrden(Long id) {
        ordenRepository.deleteById(id);
    }

    // eliminar todos los ordenes
    public void deleteAllOrdenes() {
        ordenRepository.deleteAll();
    }
}
