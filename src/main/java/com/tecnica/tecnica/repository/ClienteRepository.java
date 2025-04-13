package com.tecnica.tecnica.repository;

import com.tecnica.tecnica.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    // Consulta para obtener los clientes ordenados alfabéticamente por su nombre
    @Query(value = "SELECT u FROM Cliente u ORDER BY u.nombres ASC")
    List<Cliente> nombreListado();
}
