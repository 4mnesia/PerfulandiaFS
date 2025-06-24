package com.perfulandia.Perfulandia.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perfulandia.Perfulandia.model.Orden;
import com.perfulandia.Perfulandia.model.Enums.EstadoOrden;
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
    //guardar varias ordenes
    public List<Orden> saveOrdenes(List<Orden> ordenes) {
        return ordenRepository.saveAll(ordenes);
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

    //v2

     /** Filtra órdenes por estado */
    public List<Orden> findByEstado(EstadoOrden estado) {
        return ordenRepository.findByEstado(estado);
    }

    /** Filtra órdenes por rango de fechas */
    public List<Orden> findByFechaCreacionBetween(LocalDateTime desde, LocalDateTime hasta) {
        return ordenRepository.findByFechaCreacionBetween(desde, hasta);
    }

    /** Obtiene las últimas 10 órdenes */
    public List<Orden> findTop10ByOrderByFechaCreacionDesc() {
        return ordenRepository.findTop10ByOrderByFechaCreacionDesc();
    }
}
