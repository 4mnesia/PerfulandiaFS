package com.perfulandia.Perfulandia.service;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.Perfulandia.model.DetalleOrden;
import com.perfulandia.Perfulandia.repository.DetalleOrdenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleOrdenService {
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    // leer detalles por id
    public DetalleOrden getDetalleOrdenById(Long id) {
        return detalleOrdenRepository.findById(id).orElse(null);
    }
    // leer todos los detalles
    public List<DetalleOrden> getAllDetalles() {
        return detalleOrdenRepository.findAll();
    }

    // guardar un detalle
    public DetalleOrden saveDetalleOrden(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
    //guardar varios detalles
    public List<DetalleOrden> saveDetallesOrden(List<DetalleOrden> detallesOrden) {
        return detalleOrdenRepository.saveAll(detallesOrden);
    }   

    // eliminar un detalle
    public void deleteDetalleOrden(Long id) {
        detalleOrdenRepository.deleteById(id);
    }

    // actualizar un detalle
    public DetalleOrden updateDetalleOrden(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    // eliminar todos los detalles
    public void deleteAllDetalles() {
        detalleOrdenRepository.deleteAll();
    }
    
    
    //subtotal de un detalle
    public static BigDecimal calcularSubtotal(DetalleOrden detalleOrden) {
        if (detalleOrden.getCantidad() == null || detalleOrden.getPrecioUnitario() == null) {
            return BigDecimal.ZERO;
        }
        return detalleOrden.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleOrden.getCantidad()));
    }
    // calcular total de un detalle
    public BigDecimal calcularTotal(List<DetalleOrden> detalles) {
        return detalles.stream()
                .map(DetalleOrdenService::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //v2

    /** Filtra detalles por orden */
    public List<DetalleOrden> findByOrdenId(Long ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId);
    }

    /** Filtra detalles por carrito */
    public List<DetalleOrden> findByCarritoId(Long carritoId) {
        return detalleOrdenRepository.findByCarritoId(carritoId);
    }

    /** Suma el total vendido de un producto */
    public BigDecimal sumTotalByProductoId(Long productoId) {
        return detalleOrdenRepository.sumTotalByProductoId(productoId);
    }
}