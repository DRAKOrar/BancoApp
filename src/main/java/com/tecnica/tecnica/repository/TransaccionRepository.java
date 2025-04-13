package com.tecnica.tecnica.repository;

import com.tecnica.tecnica.entity.Transaccion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends CrudRepository<Transaccion, Long> {
    List<Transaccion> findByProductoNumeroCuenta(String numeroCuenta);
}

