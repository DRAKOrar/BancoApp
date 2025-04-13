package com.tecnica.tecnica.service;

import com.tecnica.tecnica.dto.CrearProductoDTO;
import com.tecnica.tecnica.entity.Producto;

import java.util.List;

public interface ProductoService {
    Producto agregarProducto(Long clienteId, CrearProductoDTO productoDTO);
    List<Producto> obtenerProductosPorCliente(Long clienteId);
    void eliminarProducto(Long productoId);
    Producto actualizarEstadoProducto(Long productoId, String nuevoEstado);
}
