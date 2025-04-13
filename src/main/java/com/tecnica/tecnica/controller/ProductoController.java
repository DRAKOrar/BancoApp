package com.tecnica.tecnica.controller;

import com.tecnica.tecnica.dto.CrearProductoDTO;
import com.tecnica.tecnica.entity.Producto;
import com.tecnica.tecnica.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/agregar/{clienteId}")
    public ResponseEntity<Producto> agregarProducto(@PathVariable Long clienteId, @RequestBody CrearProductoDTO productoDTO) {
        Producto producto = productoService.agregarProducto(clienteId, productoDTO);
        return ResponseEntity.ok(producto);
    }
    @PatchMapping("/{productoId}/estado")
    public ResponseEntity<Producto> actualizarEstadoProducto(
            @PathVariable Long productoId,
            @RequestParam String nuevoEstado) {
        Producto productoActualizado = productoService.actualizarEstadoProducto(productoId, nuevoEstado);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long productoId) {
        productoService.eliminarProducto(productoId);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/listar/{clienteId}")
    public ResponseEntity<List<Producto>> listarProductosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(productoService.obtenerProductosPorCliente(clienteId));
    }
}
