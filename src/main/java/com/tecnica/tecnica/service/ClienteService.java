package com.tecnica.tecnica.service;

import com.tecnica.tecnica.dto.CrearClienteDTO;
import com.tecnica.tecnica.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClienteService {
    Cliente crearCliente(CrearClienteDTO crearClienteDTO);
    List<Cliente> listarClientes();
    void eliminarCliente(Long id);
    Cliente actualizarCliente(Long id, CrearClienteDTO actualizarClienteDTO);
}
