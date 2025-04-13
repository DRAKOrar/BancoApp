package com.tecnica.tecnica.service.implementation;

import com.tecnica.tecnica.dto.CrearTransaccionDTO;
import com.tecnica.tecnica.entity.Producto;
import com.tecnica.tecnica.entity.Transaccion;
import com.tecnica.tecnica.repository.ProductoRepository;
import com.tecnica.tecnica.repository.TransaccionRepository;
import com.tecnica.tecnica.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Override
    public Transaccion realizarTransaccion(CrearTransaccionDTO transaccionDTO) {
        Optional<Producto> origenOpt = productoRepository.findByNumeroCuenta(transaccionDTO.getNumeroCuenta());
        Optional<Producto> destinoOpt = productoRepository.findByNumeroCuenta(transaccionDTO.getNumeroCuentaDestino());

        if (origenOpt.isEmpty() || destinoOpt.isEmpty()) {
            throw new IllegalArgumentException("Una de las cuentas no existe");
        }

        Producto cuentaOrigen = origenOpt.get();
        Producto cuentaDestino = destinoOpt.get();

        if (cuentaOrigen.getNumeroCuenta().equals(cuentaDestino.getNumeroCuenta())) {
            throw new IllegalArgumentException("No puedes transferir a la misma cuenta");
        }

        if (transaccionDTO.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }

        if (transaccionDTO.getTipoTransaccion().equalsIgnoreCase("retiro") ||
                transaccionDTO.getTipoTransaccion().equalsIgnoreCase("deposito")) {

            if (cuentaOrigen.getSaldo() < transaccionDTO.getMonto()) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta origen");
            }

            // Restar saldo a la cuenta origen
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - transaccionDTO.getMonto());

            // Sumar saldo a la cuenta destino
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + transaccionDTO.getMonto());
        } else {
            throw new IllegalArgumentException("Tipo de transacción inválido");
        }


        productoRepository.save(cuentaOrigen);
        productoRepository.save(cuentaDestino);

        // Registrar la transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setTipoTransaccion(transaccionDTO.getTipoTransaccion());
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setFechaTransaccion(LocalDateTime.now());
        transaccion.setProducto(cuentaOrigen);

        return transaccionRepository.save(transaccion);
    }

    @Override
    public List<Transaccion> obtenerHistorialTransacciones(String numeroCuenta) {
        return transaccionRepository.findByProductoNumeroCuenta(numeroCuenta);
    }

    @Override
    public void eliminarTransaccion(Long id) {
        if (!transaccionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transacción no encontrada.");
        }
        transaccionRepository.deleteById(id);
    }
}

