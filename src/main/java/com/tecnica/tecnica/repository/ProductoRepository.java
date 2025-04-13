package com.tecnica.tecnica.repository;

import com.tecnica.tecnica.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findByClienteId(Long clienteId);
    boolean existsByNumeroCuenta(String numeroCuenta); // Verifica unicidad del número de cuenta
    Optional<Producto> findByNumeroCuenta(String numeroCuenta); // Buscar por número de cuenta
}