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

    /**
     * Obtiene un detalle de orden por su ID
     * @param id ID del detalle de orden a buscar
     * @return Detalle de orden encontrado o null si no existe
     */
    public detalleOrden getDetalleOrdenById(Long id) {
        return detalleOrdenRepository.findById(id).orElse(null);
    }

    /**
     * Elimina un detalle de orden por su ID
     * @param id ID del detalle de orden a eliminar
     */
    public void deleteDetalleOrdenById(Long id) {
        detalleOrdenRepository.deleteById(id);
    }

    /**
     * Guarda un nuevo detalle de orden o actualiza uno existente
     * @param detalleOrden Detalle de orden a guardar
     * @return Detalle de orden guardado con su ID asignado
     */
    public detalleOrden saveDetalleOrden(detalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    /**
     * Obtiene todos los detalles de una orden específica
     * @param ordenId ID de la orden
     * @return Lista de detalles de la orden
     */
    public List<detalleOrden> getDetalleOrdenByOrdenId(Long ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId);
    }

    /**
     * Elimina todos los detalles asociados a una orden
     * @param ordenId ID de la orden
     */
    public void deleteDetalleOrdenByOrdenId(Long ordenId) {
        detalleOrdenRepository.deleteByOrdenId(ordenId);
    }

    /**
     * Actualiza la cantidad y precio de un detalle de orden
     * @param detalleOrdenId ID del detalle de orden a actualizar
     * @param cantidad Nueva cantidad
     * @param precio Nuevo precio
     */
    public void updateDetalleOrden(Long detalleOrdenId, Integer cantidad, Double precio) {
        detalleOrdenRepository.actualizarCantidadYPrecio(detalleOrdenId, cantidad, precio);
    }

    /**
     * Calcula el total de una orden sumando todos sus detalles
     * @param ordenId ID de la orden
     * @return Total calculado de la orden
     */
    public Integer calcularTotal(Long ordenId) {
        return detalleOrdenRepository.calcularTotal(ordenId);
    }

    /**
     * Elimina todos los detalles de una orden específica
     * @param ordenId ID de la orden a vaciar
     */
    public void vaciarDetalle(Long ordenId) {
        detalleOrdenRepository.vaciarDetalle(ordenId);
    }
}
