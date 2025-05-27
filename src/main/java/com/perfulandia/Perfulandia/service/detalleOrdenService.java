package com.perfulandia.Perfulandia.service;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.detalleOrden;
import com.perfulandia.Perfulandia.repository.detalleOrdenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class detalleOrdenService {
    @Autowired
    private detalleOrdenRepository detalleOrdenRepository;

    // leer detalles por id
    public detalleOrden getDetalleOrdenById(Long id) {
        return detalleOrdenRepository.findById(id).orElse(null);
    }

    // leer detalles por producto
    public List<detalleOrden> getDetallesByProductoId(Long productoId) {
        return detalleOrdenRepository.findByProductoId(productoId)
                .map(List::of)
                .orElseGet(List::of);
    }

    // leer todos los detalles
    public List<detalleOrden> getAllDetalles() {
        return detalleOrdenRepository.findAll();
    }

    // guardar un detalle
    public detalleOrden saveDetalleOrden(detalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    // eliminar un detalle
    public void deleteDetalleOrden(Long id) {
        detalleOrdenRepository.deleteById(id);
    }

    // actualizar un detalle
    public detalleOrden updateDetalleOrden(detalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    // eliminar todos los detalles
    public void deleteAllDetalles() {
        detalleOrdenRepository.deleteAll();
    }
    
    
    //subtotal de un detalle
    

    public BigDecimal calcularSubtotal(detalleOrden detalleOrden) {
        if (detalleOrden.getCantidad() == null || detalleOrden.getPrecioUnitario() == null) {
            return BigDecimal.ZERO;
        }
        return detalleOrden.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleOrden.getCantidad()));
    }
    // calcular total de un detalle
    public BigDecimal calcularTotal(List<detalleOrden> detalles) {
        return detalles.stream()
                .map(this::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}