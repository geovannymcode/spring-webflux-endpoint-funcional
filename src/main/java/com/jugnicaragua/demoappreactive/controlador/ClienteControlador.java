package com.jugnicaragua.demoappreactive.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jugnicaragua.demoappreactive.modelo.Cliente;
import com.jugnicaragua.demoappreactive.modelo.ClienteEvent;
import com.jugnicaragua.demoappreactive.repositorio.IClienteRepositorio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class ClienteControlador {
	
	@Autowired
	private IClienteRepositorio clienteRepositorio;
	
	@PostMapping
    public Mono<ResponseEntity<Cliente>> create(@RequestBody Cliente cliente){
        return clienteRepositorio.save(cliente)
                .map(savedCliente -> ResponseEntity.ok(savedCliente));
    }
	
	@GetMapping
    public Flux<Cliente> listClientes(){
        return clienteRepositorio.findAll();
    }
	
	 @GetMapping("/{id}")
		public Mono<ResponseEntity<Cliente>> getClienteById(@PathVariable String clienteId){
	        return clienteRepositorio.findById(clienteId)
	                .map(cliente -> ResponseEntity.ok(cliente))
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }

	    @PutMapping("/{id}")
	    public Mono<ResponseEntity<Cliente>> updateCliente(@PathVariable String clienteId, @RequestBody Cliente cliente){
	        return clienteRepositorio.findById(clienteId)
	               .flatMap(existingCliente -> {
	            	   existingCliente.setEdad(cliente.getEdad());
	            	   existingCliente.setSalario(cliente.getSalario());
	                   return clienteRepositorio.save(existingCliente);
	               })
	                .map(updatedCliente -> ResponseEntity.ok(updatedCliente))
	                .defaultIfEmpty(ResponseEntity.badRequest().build());
	    }

	    @DeleteMapping("/{id}")
	    public Mono<ResponseEntity<Void>> deleteClienteById(@PathVariable String clienteId){
	        return clienteRepositorio.findById(clienteId)
	                .flatMap(existingCliente ->
	                clienteRepositorio.delete(existingCliente)
	                            .then(Mono.just(ResponseEntity.ok().<Void>build()))
	                )
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }

	    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	    public Flux<ClienteEvent> emitEvents(){
	        return Flux.interval(Duration.ofSeconds(1))
	                .map(val -> new ClienteEvent("" + val, "JugNicaragua Eventos Clientes"));
	    }

}
