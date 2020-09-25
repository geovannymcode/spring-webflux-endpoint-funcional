package com.jugnicaragua.demoappreactive.repositorio;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.jugnicaragua.demoappreactive.modelo.Cliente;

@Repository
public interface IClienteRepositorio extends ReactiveCrudRepository<Cliente, String>{

}
