package com.tecnica.tecnica.service.implementation;

import com.tecnica.tecnica.dto.CrearProductoDTO;
import com.tecnica.tecnica.entity.Cliente;
import com.tecnica.tecnica.entity.Producto;
import com.tecnica.tecnica.entity.Transaccion;
import com.tecnica.tecnica.repository.ClienteRepository;
import com.tecnica.tecnica.repository.ProductoRepository;
import com.tecnica.tecnica.repository.TransaccionRepository;
import com.tecnica.tecnica.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public Producto agregarProducto(Long clienteId, CrearProductoDTO productoDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        Cliente cliente = clienteOpt.get();
        Producto producto = new Producto();
        producto.setTipoCuenta(productoDTO.getTipoCuenta());
        producto.setNumeroCuenta(generarNumeroCuenta(productoDTO.getTipoCuenta())); // Generación automática
        producto.setEstado(productoDTO.getTipoCuenta().equalsIgnoreCase("AHORRO") ? "ACTIVA" : productoDTO.getEstado());
        producto.setSaldo(productoDTO.getSaldo());
        producto.setExentaGMF(productoDTO.isExentaGMF());
        producto.setFechaCreacion(LocalDate.now());
        producto.setCliente(cliente);

        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> obtenerProductosPorCliente(Long clienteId) {
        return productoRepository.findByClienteId(clienteId);
    }

    @Override
    public void eliminarProducto(Long productoId) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();

        // Validar que el saldo sea 0
        if (producto.getSaldo() > 0) {
            throw new IllegalArgumentException("No se puede eliminar una cuenta con saldo mayor a $0");
        }

        // Eliminar transacciones asociadas (si es necesario)
        List<Transaccion> transacciones = transaccionRepository.findByProductoNumeroCuenta(producto.getNumeroCuenta());
        transaccionRepository.deleteAll(transacciones);

        // Eliminar el producto
        productoRepository.delete(producto);
    }

    @Override
    public Producto actualizarEstadoProducto(Long productoId, String nuevoEstado) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        producto.setEstado(nuevoEstado);
        producto.setFechaModificacion(LocalDate.now()); // Actualizar fecha de modificación

        return productoRepository.save(producto);
    }

    /**
     * Genera un número de cuenta único basado en el tipo de cuenta.
     */
    private String generarNumeroCuenta(String tipoCuenta) {
        String prefijo = tipoCuenta.equalsIgnoreCase("AHORRO") ? "53" : "33";
        String numeroGenerado;
        do {
            numeroGenerado = prefijo + String.format("%08d", new Random().nextInt(100000000));
        } while (productoRepository.existsByNumeroCuenta(numeroGenerado)); // Asegura unicidad
        return numeroGenerado;
    }
}

