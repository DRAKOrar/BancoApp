package com.tecnica.tecnica.controller;

import com.tecnica.tecnica.dto.CrearClienteDTO;
import com.tecnica.tecnica.entity.Cliente;
import com.tecnica.tecnica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearCliente(@RequestBody CrearClienteDTO crearClienteDTO) {
        try {
            Cliente cliente = clienteService.crearCliente(crearClienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Retorna el mensaje de error directamente
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarCliente(
            @PathVariable Long id,
            @RequestBody CrearClienteDTO actualizarClienteDTO
    ) {
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(id, actualizarClienteDTO);
            return ResponseEntity.ok(clienteActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
