package com.jugnicaragua.demoappreactive.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.BodyInserters;

import com.jugnicaragua.demoappreactive.modelo.Cliente;
import com.jugnicaragua.demoappreactive.modelo.ClienteEvent;
import com.jugnicaragua.demoappreactive.repositorio.IClienteRepositorio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.time.Duration;

import org.springframework.http.MediaType;

public class ClienteHandler {
	
	private IClienteRepositorio clienteRepositorio;

	public ClienteHandler(IClienteRepositorio clienteRepositorio) {
		this.clienteRepositorio = clienteRepositorio;
	}
	
	public Mono<ServerResponse> createCliete(ServerRequest request) {
        Mono<Cliente> userMono = request.bodyToMono(Cliente.class).flatMap(user -> clienteRepositorio.save(user));
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userMono, Cliente.class);
    }

    public Mono<ServerResponse> listCliente(ServerRequest request) {
        Flux<Cliente> cliente = clienteRepositorio.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(cliente, Cliente.class);
    }
    
	public Mono<ServerResponse> getClienteById(ServerRequest request) {
        String clienteId = request.pathVariable("clienteId");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Cliente> clienteMono = clienteRepositorio.findById(clienteId);
        return clienteMono.flatMap(cliente -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(cliente)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteCliente(ServerRequest request) {
        String clienteId = request.pathVariable("clienteId");
        clienteRepositorio.deleteById(clienteId);
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> streamEvents(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1))
                .map(val -> new ClienteEvent("" + val, "JugNicaragua Eventos Clientes")), ClienteEvent.class);
    }

}
