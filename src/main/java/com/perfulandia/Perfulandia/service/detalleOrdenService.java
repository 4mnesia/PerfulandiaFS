package com.perfulandia.Perfulandia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.detalleOrden;
import com.perfulandia.Perfulandia.repository.detalleOrdenRepository;

@Service
public class detalleOrdenService {
    @Autowired
    private detalleOrdenRepository detalleOrdenRepository;

   //leer detalles por id
    public detalleOrden getDetalleOrdenById(Long id) {
        return detalleOrdenRepository.findById(id).orElse(null);
    }
    //leer todos los detalles
    public List<detalleOrden> getAllDetalles() {
        return detalleOrdenRepository.findAll();
    }

    //guardar un detalle
    public detalleOrden saveDetalleOrden(detalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
    //eliminar un detalle
    public void deleteDetalleOrden(Long id) {
        detalleOrdenRepository.deleteById(id);
    }
    //actualizar un detalle
    public detalleOrden updateDetalleOrden(detalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
