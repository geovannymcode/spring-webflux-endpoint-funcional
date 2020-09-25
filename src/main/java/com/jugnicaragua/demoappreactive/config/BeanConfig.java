package com.jugnicaragua.demoappreactive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jugnicaragua.demoappreactive.handler.ClienteHandler;
import com.jugnicaragua.demoappreactive.repositorio.IClienteRepositorio;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class BeanConfig {
	
	@Autowired
	private IClienteRepositorio clienteRepositorio;
	
	@Bean
    public RouterFunction<ServerResponse> route() {
        ClienteHandler clienteHandler = new ClienteHandler(clienteRepositorio);
        return RouterFunctions
                .route(POST("/clientes").and(contentType(APPLICATION_JSON)), clienteHandler::createCliete)
                .andRoute(GET("/clientes").and(accept(APPLICATION_JSON)), clienteHandler::listCliente)
                .andRoute(GET("/clientes/{clienteId}").and(accept(APPLICATION_JSON)), clienteHandler::getClienteById)
                .andRoute(PUT("/clientes/{id}").and(accept(APPLICATION_JSON)), clienteHandler :: createCliete)
                .andRoute(DELETE("/clientes/id").and(accept(APPLICATION_JSON)), clienteHandler :: deleteCliente)
                .andRoute(GET("/clientes/events/stream").and(accept(APPLICATION_JSON)), clienteHandler :: streamEvents);
    }
}
