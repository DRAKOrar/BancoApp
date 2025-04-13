package com.tecnica.tecnica.service;

import com.tecnica.tecnica.dto.CrearTransaccionDTO;
import com.tecnica.tecnica.entity.Transaccion;

import java.util.List;

public interface TransaccionService {
    Transaccion realizarTransaccion(CrearTransaccionDTO transaccionDTO);
    List<Transaccion> obtenerHistorialTransacciones(String numeroCuenta);
    void eliminarTransaccion(Long id);

}
