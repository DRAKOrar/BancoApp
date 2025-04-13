package com.tecnica.tecnica.controller;

import com.tecnica.tecnica.dto.CrearTransaccionDTO;
import com.tecnica.tecnica.entity.Transaccion;
import com.tecnica.tecnica.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping("/realizar")
    public ResponseEntity<Transaccion> realizarTransaccion(@RequestBody CrearTransaccionDTO transaccionDTO) {
        return ResponseEntity.ok(transaccionService.realizarTransaccion(transaccionDTO));
    }

    // Obtener historial de transacciones por número de cuenta
    @GetMapping("/historial/{numeroCuenta}")
    public ResponseEntity<List<Transaccion>> obtenerHistorialTransacciones(@PathVariable String numeroCuenta) {
        List<Transaccion> transacciones = transaccionService.obtenerHistorialTransacciones(numeroCuenta);
        return ResponseEntity.ok(transacciones);
    }

    // Eliminar transacción
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTransaccion(@PathVariable Long id) {
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.ok("Transacción eliminada correctamente.");
    }
}


